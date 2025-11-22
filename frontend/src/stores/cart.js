import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { cartAPI } from '../api/cart'
import { productAPI } from '../api/product'
import { useAuthStore } from './auth'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])
  const loading = ref(false)
  const error = ref(null)

  // Computed properties
  const itemCount = computed(() => {
    return items.value.reduce((total, item) => total + item.quantity, 0)
  })

  const totalPrice = computed(() => {
    return items.value.reduce((total, item) => {
      return total + (item.price * item.quantity)
    }, 0)
  })

  const totalDiscount = computed(() => {
    return items.value.reduce((total, item) => {
      if (item.originalPrice && item.originalPrice > item.price) {
        return total + ((item.originalPrice - item.price) * item.quantity)
      }
      return total
    }, 0)
  })

  // 서버에서 장바구니 조회
  async function fetchCart() {
    const authStore = useAuthStore()
    if (!authStore.isLoggedIn) {
      items.value = []
      return
    }

    loading.value = true
    error.value = null
    try {
      const response = await cartAPI.getMyCart()
      if (response.success && response.data) {
        // CartModel에는 productId, quantity만 있으므로 상품 정보 조회 필요
        const cartItems = await Promise.all(
          response.data.map(async (cartItem) => {
            try {
              const productRes = await productAPI.getById(cartItem.productId)
              const product = productRes.data
              return {
                id: cartItem.productId,
                cartId: cartItem.id,
                name: product.productName,
                price: Number(product.productPrice) || 0,
                originalPrice: product.originalPrice || null,
                image: product.imageUrl || null,
                quantity: cartItem.quantity,
                categoryName: product.largeClassNAme || ''
              }
            } catch (e) {
              console.error(`상품 정보 조회 실패 (ID: ${cartItem.productId}):`, e)
              return {
                id: cartItem.productId,
                cartId: cartItem.id,
                name: '상품 정보 로드 실패',
                price: 0,
                originalPrice: null,
                image: null,
                quantity: cartItem.quantity,
                categoryName: ''
              }
            }
          })
        )
        items.value = cartItems
      }
    } catch (e) {
      console.error('장바구니 조회 실패:', e)
      error.value = '장바구니를 불러오는데 실패했습니다.'
    } finally {
      loading.value = false
    }
  }

  // 장바구니에 상품 추가
  async function addToCart(product, quantity = 1) {
    const authStore = useAuthStore()
    if (!authStore.isLoggedIn) {
      throw new Error('로그인이 필요합니다.')
    }

    loading.value = true
    error.value = null
    try {
      const response = await cartAPI.addToCart(product.id || product.productId, quantity)
      if (response.success) {
        await fetchCart() // 서버에서 최신 데이터 다시 가져오기
      }
      return response
    } catch (e) {
      console.error('장바구니 추가 실패:', e)
      error.value = '장바구니에 추가하는데 실패했습니다.'
      throw e
    } finally {
      loading.value = false
    }
  }

  // 장바구니에서 상품 제거
  async function removeFromCart(productId) {
    const authStore = useAuthStore()
    if (!authStore.isLoggedIn) {
      return
    }

    // 현재 수량 찾기
    const item = items.value.find(item => item.id === productId)
    if (!item) return

    loading.value = true
    error.value = null
    try {
      // 전체 수량 제거
      const response = await cartAPI.removeFromCart(productId, item.quantity)
      if (response.success) {
        await fetchCart()
      }
      return response
    } catch (e) {
      console.error('장바구니 제거 실패:', e)
      error.value = '장바구니에서 제거하는데 실패했습니다.'
      throw e
    } finally {
      loading.value = false
    }
  }

  // 수량 업데이트
  async function updateQuantity(productId, newQuantity) {
    const authStore = useAuthStore()
    if (!authStore.isLoggedIn) {
      return
    }

    const item = items.value.find(item => item.id === productId)
    if (!item) return

    if (newQuantity <= 0) {
      return removeFromCart(productId)
    }

    loading.value = true
    error.value = null
    try {
      const response = await cartAPI.updateQuantity(productId, newQuantity, item.quantity)
      if (response.success) {
        await fetchCart()
      }
      return response
    } catch (e) {
      console.error('수량 변경 실패:', e)
      error.value = '수량을 변경하는데 실패했습니다.'
      throw e
    } finally {
      loading.value = false
    }
  }

  // 장바구니 비우기
  async function clearCart() {
    const authStore = useAuthStore()
    if (!authStore.isLoggedIn) {
      return
    }

    loading.value = true
    error.value = null
    try {
      // 모든 아이템 제거
      await Promise.all(
        items.value.map(item => cartAPI.removeFromCart(item.id, item.quantity))
      )
      items.value = []
    } catch (e) {
      console.error('장바구니 비우기 실패:', e)
      error.value = '장바구니를 비우는데 실패했습니다.'
      throw e
    } finally {
      loading.value = false
    }
  }

  return {
    items,
    loading,
    error,
    itemCount,
    totalPrice,
    totalDiscount,
    fetchCart,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart
  }
})
