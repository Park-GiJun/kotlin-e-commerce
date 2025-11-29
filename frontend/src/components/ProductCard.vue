<template>
  <router-link
    :to="`/products/${product.productId}`"
    class="bg-white rounded-lg shadow hover:shadow-lg transition-shadow duration-200 overflow-hidden group cursor-pointer block"
  >
    <div class="aspect-square bg-gray-100 flex items-center justify-center relative overflow-hidden">
      <img
        v-if="product.imageUrl"
        :src="product.imageUrl"
        :alt="product.productName"
        :class="['w-full h-full object-cover group-hover:scale-105 transition-transform duration-200', !product.isOnSale && 'opacity-50']"
      />
      <div v-else :class="['text-gray-400 text-6xl', !product.isOnSale && 'opacity-50']">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-24 h-24">
          <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z" />
        </svg>
      </div>
      <!-- 품절 표시 -->
      <div v-if="!product.isOnSale" class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40">
        <span class="bg-gray-800 text-white px-4 py-2 rounded-lg font-bold text-lg">품절</span>
      </div>
      <span v-if="product.discount && product.isOnSale" class="absolute top-2 right-2 bg-red-500 text-white px-2 py-1 rounded text-sm font-bold">
        -{{ product.discount }}%
      </span>
    </div>
    <div class="p-4">
      <div class="text-xs text-gray-500 mb-1">
        {{ getCategoryPath(product) }}
      </div>
      <h3 class="font-medium text-gray-900 mb-2 line-clamp-2 min-h-[2.5rem]">
        {{ product.productName }}
      </h3>
      <div class="flex items-baseline gap-2 mb-2">
        <span class="text-2xl font-bold text-primary-600">
          {{ formatPrice(product.productPrice) }}
        </span>
        <span class="text-sm text-gray-500">원</span>
      </div>
      <div class="flex items-center gap-1 text-sm text-gray-600 mb-3">
        <div class="flex text-yellow-400">
          <svg v-for="i in 5" :key="i" class="w-4 h-4" :fill="i <= Math.round((product.averageRating || 0) / 2) ? 'currentColor' : 'none'" :stroke="i <= Math.round((product.averageRating || 0) / 2) ? 'none' : 'currentColor'" viewBox="0 0 20 20">
            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
          </svg>
        </div>
        <span class="ml-1">{{ ((product.averageRating || 0) / 2).toFixed(1) }} ({{ product.reviewCount || 0 }})</span>
      </div>
      <button
        @click.prevent="addToCart"
        :disabled="isLoading || !product.isOnSale"
        class="w-full bg-primary-500 hover:bg-primary-600 disabled:bg-gray-400 text-white py-2 rounded-lg transition-colors duration-200 font-medium"
      >
        <span v-if="!product.isOnSale">품절</span>
        <span v-else-if="isLoading">추가 중...</span>
        <span v-else>장바구니에 담기</span>
      </button>
    </div>
  </router-link>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'

const props = defineProps({
  product: {
    type: Object,
    required: true
  }
})

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()
const isLoading = ref(false)

const getCategoryPath = (product) => {
  const parts = [product.largeClassName]
  if (product.mediumClassName) parts.push(product.mediumClassName)
  if (product.smallClassName) parts.push(product.smallClassName)
  return parts.join(' > ')
}

const formatPrice = (price) => {
  return (price || 0).toLocaleString('ko-KR')
}

const addToCart = async () => {
  if (!authStore.isLoggedIn) {
    alert('로그인이 필요합니다.')
    router.push('/login')
    return
  }

  isLoading.value = true
  try {
    await cartStore.addToCart({
      id: props.product.productId,
      productId: props.product.productId
    }, 1)
    alert('장바구니에 담았습니다!')
  } catch (e) {
    alert('장바구니 추가에 실패했습니다: ' + (e.response?.data?.message || e.message))
  } finally {
    isLoading.value = false
  }
}
</script>
