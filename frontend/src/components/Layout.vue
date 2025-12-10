<template>
  <div class="h-screen flex flex-col bg-gray-100">
    <!-- Top Header -->
    <header class="bg-gray-900 text-white shadow-md flex-shrink-0">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex items-center justify-between h-14">
          <!-- Logo -->
          <router-link to="/dashboard" class="flex items-center space-x-2 hover:opacity-90 transition-opacity">
            <svg class="w-7 h-7" fill="currentColor" viewBox="0 0 20 20" style="color: #2dd4b8;">
              <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
            </svg>
            <span class="text-xl font-bold"><span style="color: #2dd4b8;">Mint</span>Shop</span>
          </router-link>

          <!-- Search Bar -->
          <div class="hidden md:flex flex-1 max-w-xl mx-6">
            <div class="relative w-full">
              <input
                type="text"
                placeholder="상품 검색..."
                class="w-full px-4 py-2 rounded-lg text-gray-900 text-sm focus:outline-none focus:ring-2 focus:ring-teal-500 bg-gray-100"
              />
              <button class="absolute right-0 top-0 h-full px-4 rounded-r-lg transition-colors hover:opacity-90" style="background-color: #14b8a0;">
                <svg class="w-4 h-4 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </button>
            </div>
          </div>

          <!-- Right Menu -->
          <div class="flex items-center space-x-2">
            <!-- 로그인 상태일 때 -->
            <template v-if="authStore.isLoggedIn">
              <!-- Wishlist -->
              <router-link to="/wishlist" class="hidden md:flex items-center hover:bg-gray-800 p-2 rounded-lg transition-colors">
                <div class="relative">
                  <svg class="w-5 h-5 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                  </svg>
                  <span v-if="wishlistStore.itemCount > 0" class="absolute -top-1 -right-1 text-white text-xs rounded-full w-4 h-4 flex items-center justify-center font-bold" style="background-color: #14b8a0; font-size: 10px;">{{ wishlistStore.itemCount }}</span>
                </div>
              </router-link>

              <!-- User Menu -->
              <div class="relative group hidden md:block">
                <button class="flex items-center space-x-2 hover:bg-gray-800 px-3 py-2 rounded-lg transition-colors cursor-pointer">
                  <div class="w-7 h-7 rounded-full bg-teal-600 flex items-center justify-center">
                    <span class="text-white text-xs font-bold">{{ authStore.user?.name?.[0] || 'U' }}</span>
                  </div>
                  <span class="text-sm text-gray-300">{{ authStore.user?.name }}</span>
                  <svg class="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                  </svg>
                </button>

                <!-- Dropdown Menu -->
                <div class="absolute right-0 mt-1 w-48 bg-white rounded-lg shadow-lg py-1 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all z-50">
                  <router-link to="/profile" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                    </svg>
                    내 정보
                  </router-link>
                  <router-link to="/orders" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                    </svg>
                    주문 내역
                  </router-link>
                  <router-link to="/coupons" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z" />
                    </svg>
                    쿠폰함
                  </router-link>
                  <router-link to="/points" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    포인트
                  </router-link>
                  <router-link to="/inquiry" class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
                    </svg>
                    1:1 문의
                  </router-link>
                  <div class="border-t border-gray-200 my-1"></div>
                  <button @click="authStore.logout()" class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100 flex items-center">
                    <svg class="w-4 h-4 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                    </svg>
                    로그아웃
                  </button>
                </div>
              </div>

              <!-- Cart -->
              <router-link to="/cart" class="flex items-center space-x-1 hover:bg-gray-800 px-3 py-2 rounded-lg transition-colors">
                <div class="relative">
                  <svg class="w-5 h-5 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                  </svg>
                  <span v-if="cartStore.itemCount > 0" class="absolute -top-1 -right-1 text-white text-xs rounded-full w-4 h-4 flex items-center justify-center font-bold" style="background-color: #14b8a0; font-size: 10px;">{{ cartStore.itemCount }}</span>
                </div>
                <span class="text-xs text-gray-300 hidden lg:block">장바구니</span>
              </router-link>
            </template>

            <!-- 비로그인 상태일 때 -->
            <template v-else>
              <router-link to="/login" class="text-gray-300 hover:bg-gray-800 px-3 py-1.5 rounded-lg transition-colors text-sm">
                로그인
              </router-link>
              <router-link to="/register" class="text-white px-3 py-1.5 rounded-lg transition-colors text-sm font-medium" style="background-color: #14b8a0;">
                회원가입
              </router-link>
            </template>
          </div>
        </div>
      </div>
    </header>

    <!-- Navigation Bar -->
    <nav class="bg-white border-b border-gray-200 shadow-sm flex-shrink-0">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex items-center justify-between h-10 text-sm">
          <div class="flex items-center">
            <router-link to="/dashboard" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors font-medium">
              홈
            </router-link>
            <router-link to="/products" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors font-medium">
              전체 상품
            </router-link>
            <router-link to="/notice" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
              공지사항
            </router-link>
            <router-link to="/customer-service" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
              고객센터
            </router-link>

            <!-- 관리자 메뉴 -->
            <template v-if="authStore.isAdmin">
              <div class="border-l border-gray-300 h-4 mx-2"></div>
              <span class="text-teal-700 text-xs font-bold bg-teal-100 px-2 py-0.5 rounded">관리자</span>
              <router-link to="/admin/dashboard" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
                대시보드
              </router-link>
              <router-link to="/admin/orders" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
                주문관리
              </router-link>
              <router-link to="/admin/products" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
                상품관리
              </router-link>
              <router-link to="/admin/reviews" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
                리뷰관리
              </router-link>
              <router-link to="/admin/inquiries" class="text-gray-700 hover:text-teal-600 hover:bg-gray-50 px-3 py-1.5 rounded transition-colors">
                문의관리
              </router-link>
            </template>
          </div>
        </div>
      </div>
    </nav>

    <!-- Main Content - scrollable area -->
    <main class="flex-1 overflow-y-auto">
      <slot />
    </main>
  </div>
</template>

<script setup>
import { onMounted, watch } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useCartStore } from '../stores/cart'
import { useWishlistStore } from '../stores/wishlist'

const authStore = useAuthStore()
const cartStore = useCartStore()
const wishlistStore = useWishlistStore()

// 로그인 상태일 때 장바구니 로드
onMounted(async () => {
  if (authStore.isLoggedIn) {
    await cartStore.fetchCart()
  }
})

// 로그인 상태 변경 감지하여 장바구니 로드/클리어
watch(() => authStore.isLoggedIn, async (isLoggedIn) => {
  if (isLoggedIn) {
    await cartStore.fetchCart()
  } else {
    cartStore.items = []
  }
})
</script>
