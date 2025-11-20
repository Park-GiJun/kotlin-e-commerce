<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">찜 목록</h1>
        <p class="mt-2 text-gray-600">총 {{ wishlistStore.itemCount }}개의 상품</p>
      </div>

      <!-- Empty State -->
      <div v-if="wishlistStore.items.length === 0" class="text-center py-16">
        <svg class="mx-auto h-24 w-24 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
        </svg>
        <h3 class="mt-4 text-lg font-medium text-gray-900">찜한 상품이 없습니다</h3>
        <p class="mt-2 text-gray-600">마음에 드는 상품을 찜 목록에 추가해보세요</p>
        <router-link to="/products" class="mt-6 inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-lg text-white bg-primary-600 hover:bg-primary-700">
          상품 둘러보기
        </router-link>
      </div>

      <!-- Wishlist Grid -->
      <div v-else>
        <div class="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
          <div v-for="item in wishlistStore.items" :key="item.id" class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow group">
            <router-link :to="`/products/${item.id}`" class="block">
              <div class="relative aspect-square bg-gray-100">
                <img v-if="item.image" :src="item.image" :alt="item.name" class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300">
                <div v-else class="w-full h-full flex items-center justify-center text-gray-400">
                  <svg class="w-20 h-20" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>

                <!-- Remove Button -->
                <button
                  @click.prevent="removeItem(item.id)"
                  class="absolute top-2 right-2 w-8 h-8 flex items-center justify-center bg-white rounded-full shadow-md hover:bg-red-50 transition-colors z-10">
                  <svg class="w-5 h-5 text-red-600" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd" />
                  </svg>
                </button>

                <!-- Discount Badge -->
                <div v-if="item.originalPrice && item.originalPrice > item.price" class="absolute top-2 left-2 bg-red-500 text-white text-xs font-bold px-2 py-1 rounded">
                  {{ Math.round(((item.originalPrice - item.price) / item.originalPrice) * 100) }}% OFF
                </div>
              </div>
            </router-link>

            <div class="p-4">
              <router-link :to="`/products/${item.id}`">
                <h3 class="text-sm font-medium text-gray-900 line-clamp-2 hover:text-primary-600">{{ item.name }}</h3>
                <p v-if="item.categoryName" class="mt-1 text-xs text-gray-500">{{ item.categoryName }}</p>

                <div class="mt-2">
                  <p v-if="item.originalPrice && item.originalPrice > item.price" class="text-xs text-gray-400 line-through">
                    {{ item.originalPrice.toLocaleString('ko-KR') }}원
                  </p>
                  <p class="text-lg font-bold text-gray-900">{{ item.price.toLocaleString('ko-KR') }}원</p>
                </div>
              </router-link>

              <button
                @click="addToCart(item)"
                class="mt-3 w-full px-4 py-2 bg-primary-600 text-white text-sm font-medium rounded-lg hover:bg-primary-700 transition-colors">
                장바구니 담기
              </button>
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="mt-8 flex justify-center">
          <button
            @click="clearAll"
            class="px-6 py-2 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50">
            전체 삭제
          </button>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useWishlistStore } from '../stores/wishlist'
import { useCartStore } from '../stores/cart'
import Layout from '../components/Layout.vue'

const router = useRouter()
const wishlistStore = useWishlistStore()
const cartStore = useCartStore()

function removeItem(productId) {
  if (confirm('찜 목록에서 삭제하시겠습니까?')) {
    wishlistStore.removeFromWishlist(productId)
  }
}

function addToCart(item) {
  cartStore.addToCart(item, 1)
  alert('장바구니에 추가되었습니다.')
}

function clearAll() {
  if (confirm('찜 목록을 전체 삭제하시겠습니까?')) {
    wishlistStore.clearWishlist()
  }
}
</script>
