<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">장바구니</h1>
        <p class="mt-2 text-gray-600">총 {{ cartStore.itemCount }}개의 상품</p>
      </div>

      <!-- Empty State -->
      <div v-if="cartStore.items.length === 0" class="text-center py-16">
        <svg class="mx-auto h-24 w-24 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
        </svg>
        <h3 class="mt-4 text-lg font-medium text-gray-900">장바구니가 비어있습니다</h3>
        <p class="mt-2 text-gray-600">원하시는 상품을 장바구니에 담아보세요</p>
        <router-link to="/products" class="mt-6 inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-lg text-white bg-primary-600 hover:bg-primary-700">
          상품 둘러보기
        </router-link>
      </div>

      <!-- Cart Items -->
      <div v-else class="lg:grid lg:grid-cols-12 lg:gap-8">
        <!-- Items List -->
        <div class="lg:col-span-8">
          <div class="bg-white rounded-xl shadow-md overflow-hidden">
            <div class="divide-y divide-gray-200">
              <div v-for="item in cartStore.items" :key="item.id" class="p-6">
                <div class="flex items-center">
                  <!-- Product Image -->
                  <div class="flex-shrink-0 w-24 h-24 bg-gray-100 rounded-lg overflow-hidden">
                    <img v-if="item.image" :src="item.image" :alt="item.name" class="w-full h-full object-cover">
                    <div v-else class="w-full h-full flex items-center justify-center text-gray-400">
                      <svg class="w-12 h-12" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                      </svg>
                    </div>
                  </div>

                  <!-- Product Info -->
                  <div class="ml-6 flex-1">
                    <div class="flex items-start justify-between">
                      <div>
                        <h3 class="text-lg font-medium text-gray-900">{{ item.name }}</h3>
                        <p v-if="item.categoryName" class="mt-1 text-sm text-gray-500">{{ item.categoryName }}</p>
                      </div>
                      <button @click="cartStore.removeFromCart(item.id)" class="ml-4 text-gray-400 hover:text-red-600">
                        <svg class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                        </svg>
                      </button>
                    </div>

                    <div class="mt-4 flex items-center justify-between">
                      <!-- Quantity Controls -->
                      <div class="flex items-center space-x-3">
                        <button
                          @click="cartStore.updateQuantity(item.id, item.quantity - 1)"
                          class="w-8 h-8 flex items-center justify-center border border-gray-300 rounded-md hover:bg-gray-50">
                          <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                          </svg>
                        </button>
                        <span class="text-gray-900 font-medium w-12 text-center">{{ item.quantity }}</span>
                        <button
                          @click="cartStore.updateQuantity(item.id, item.quantity + 1)"
                          class="w-8 h-8 flex items-center justify-center border border-gray-300 rounded-md hover:bg-gray-50">
                          <svg class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                          </svg>
                        </button>
                      </div>

                      <!-- Price -->
                      <div class="text-right">
                        <p v-if="item.originalPrice && item.originalPrice > item.price" class="text-sm text-gray-400 line-through">
                          {{ (item.originalPrice * item.quantity).toLocaleString('ko-KR') }}원
                        </p>
                        <p class="text-lg font-bold text-gray-900">
                          {{ (item.price * item.quantity).toLocaleString('ko-KR') }}원
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="mt-8 lg:mt-0 lg:col-span-4">
          <div class="bg-white rounded-xl shadow-md p-6 sticky top-4">
            <h2 class="text-lg font-bold text-gray-900 mb-4">주문 요약</h2>

            <div class="space-y-3">
              <div class="flex justify-between text-gray-600">
                <span>상품 금액</span>
                <span>{{ (cartStore.totalPrice + cartStore.totalDiscount).toLocaleString('ko-KR') }}원</span>
              </div>
              <div v-if="cartStore.totalDiscount > 0" class="flex justify-between text-red-600">
                <span>할인 금액</span>
                <span>-{{ cartStore.totalDiscount.toLocaleString('ko-KR') }}원</span>
              </div>
              <div class="flex justify-between text-gray-600">
                <span>배송비</span>
                <span>{{ shippingFee.toLocaleString('ko-KR') }}원</span>
              </div>

              <div class="border-t border-gray-200 pt-3">
                <div class="flex justify-between text-lg font-bold text-gray-900">
                  <span>총 결제 금액</span>
                  <span class="text-primary-600">{{ (cartStore.totalPrice + shippingFee).toLocaleString('ko-KR') }}원</span>
                </div>
              </div>
            </div>

            <button
              @click="goToCheckout"
              class="w-full mt-6 px-6 py-3 bg-primary-600 text-white font-medium rounded-lg hover:bg-primary-700 transition-colors">
              주문하기
            </button>

            <button
              @click="continueShopping"
              class="w-full mt-3 px-6 py-3 border border-gray-300 text-gray-700 font-medium rounded-lg hover:bg-gray-50 transition-colors">
              쇼핑 계속하기
            </button>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import Layout from '../components/Layout.vue'

const router = useRouter()
const cartStore = useCartStore()

// Shipping fee calculation (free shipping over 50,000 won)
const shippingFee = computed(() => {
  return cartStore.totalPrice >= 50000 ? 0 : 3000
})

function goToCheckout() {
  router.push('/checkout')
}

function continueShopping() {
  router.push('/products')
}
</script>
