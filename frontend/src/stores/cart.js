import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])

  // Load cart from localStorage
  const savedCart = localStorage.getItem('cart_items')
  if (savedCart) {
    items.value = JSON.parse(savedCart)
  }

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

  // Actions
  function addToCart(product, quantity = 1) {
    const existingItem = items.value.find(item => item.id === product.id)

    if (existingItem) {
      existingItem.quantity += quantity
    } else {
      items.value.push({
        id: product.id,
        name: product.name,
        price: product.price,
        originalPrice: product.originalPrice,
        image: product.image,
        quantity: quantity,
        categoryName: product.categoryName
      })
    }

    saveToLocalStorage()
  }

  function removeFromCart(productId) {
    const index = items.value.findIndex(item => item.id === productId)
    if (index > -1) {
      items.value.splice(index, 1)
      saveToLocalStorage()
    }
  }

  function updateQuantity(productId, quantity) {
    const item = items.value.find(item => item.id === productId)
    if (item) {
      if (quantity <= 0) {
        removeFromCart(productId)
      } else {
        item.quantity = quantity
        saveToLocalStorage()
      }
    }
  }

  function clearCart() {
    items.value = []
    saveToLocalStorage()
  }

  function saveToLocalStorage() {
    localStorage.setItem('cart_items', JSON.stringify(items.value))
  }

  return {
    items,
    itemCount,
    totalPrice,
    totalDiscount,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart
  }
})
