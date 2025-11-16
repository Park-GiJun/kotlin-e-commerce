<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Top Header -->
    <header class="bg-gray-900 text-white shadow-md">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex items-center justify-between h-16">
          <!-- Logo -->
          <router-link to="/dashboard" class="flex items-center space-x-2 hover:opacity-90 transition-opacity">
            <svg class="w-8 h-8" fill="currentColor" viewBox="0 0 20 20" style="color: #2dd4b8;">
              <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
            </svg>
            <span class="text-2xl font-bold"><span style="color: #2dd4b8;">Mint</span>Shop</span>
          </router-link>

          <!-- Search Bar -->
          <div class="hidden md:flex flex-1 max-w-2xl mx-8">
            <div class="relative w-full">
              <input
                type="text"
                placeholder="상품 검색..."
                class="w-full px-4 py-2 rounded-l-md text-gray-900 focus:outline-none focus:ring-2 focus:ring-primary-500"
              />
              <button class="absolute right-0 top-0 h-full px-6 rounded-r-md transition-colors hover:opacity-90" style="background-color: #14b8a0;">
                <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
              </button>
            </div>
          </div>

          <!-- Right Menu -->
          <div class="flex items-center space-x-4">
            <div class="hidden md:flex items-center space-x-2 hover:bg-gray-800 px-3 py-2 rounded-lg transition-colors cursor-pointer">
              <svg class="w-6 h-6 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
              </svg>
              <div class="flex flex-col text-xs">
                <span class="text-gray-400">안녕하세요</span>
                <span class="font-bold text-primary-400">{{ authStore.user?.name }}</span>
              </div>
            </div>

            <router-link to="/cart" class="flex items-center space-x-2 hover:bg-gray-800 px-3 py-2 rounded-lg transition-colors">
              <div class="relative">
                <svg class="w-7 h-7 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
                <span class="absolute -top-1 -right-1 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center font-bold shadow-lg" style="background-color: #14b8a0;">0</span>
              </div>
              <span class="text-sm font-medium hidden lg:block text-gray-300">장바구니</span>
            </router-link>
          </div>
        </div>
      </div>
    </header>

    <!-- Navigation Bar -->
    <nav class="bg-teal-600 text-white shadow-md" style="background-color: #0d9488;">
      <div class="max-w-7xl mx-auto px-4">
        <div class="flex items-center space-x-2 h-12 text-sm font-medium">
          <router-link to="/dashboard" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
            홈
          </router-link>
          <router-link to="/products" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
            전체 상품
          </router-link>
          <div class="relative group">
            <button class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors flex items-center">
              카테고리
              <svg class="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
              </svg>
            </button>
          </div>

          <template v-if="authStore.isAdmin">
            <div class="border-l border-teal-400 h-6 mx-2"></div>
            <span class="text-teal-900 text-xs font-bold bg-white px-3 py-1.5 rounded-full shadow-sm">관리자</span>
            <router-link to="/admin/users" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
              회원 관리
            </router-link>
            <router-link to="/admin/products" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
              상품 관리
            </router-link>
            <router-link to="/admin/product-prices" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
              가격 관리
            </router-link>
            <router-link to="/admin/categories" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors">
              카테고리 관리
            </router-link>
          </template>

          <div class="flex-1"></div>
          <button @click="authStore.logout()" class="text-white hover:bg-teal-500 px-4 py-2 rounded transition-colors flex items-center">
            <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
            </svg>
            로그아웃
          </button>
        </div>
      </div>
    </nav>

    <!-- Main Content -->
    <main class="min-h-[calc(100vh-180px)]">
      <slot />
    </main>

    <!-- Footer -->
    <footer class="bg-gray-900 text-white mt-12 border-t border-gray-800">
      <div class="max-w-7xl mx-auto px-4 py-8">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div>
            <h3 class="font-bold text-lg mb-4 text-primary-400">고객 서비스</h3>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><a href="#" class="hover:text-primary-400 transition-colors">고객센터</a></li>
              <li><a href="#" class="hover:text-primary-400 transition-colors">배송 조회</a></li>
              <li><a href="#" class="hover:text-primary-400 transition-colors">반품/교환</a></li>
            </ul>
          </div>
          <div>
            <h3 class="font-bold text-lg mb-4 text-primary-400">회사 정보</h3>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><a href="#" class="hover:text-primary-400 transition-colors">회사 소개</a></li>
              <li><a href="#" class="hover:text-primary-400 transition-colors">채용</a></li>
              <li><a href="#" class="hover:text-primary-400 transition-colors">제휴 문의</a></li>
            </ul>
          </div>
          <div>
            <h3 class="font-bold text-lg mb-4 text-primary-400">이용 약관</h3>
            <ul class="space-y-2 text-sm text-gray-400">
              <li><a href="#" class="hover:text-primary-400 transition-colors">이용약관</a></li>
              <li><a href="#" class="hover:text-primary-400 transition-colors">개인정보처리방침</a></li>
            </ul>
          </div>
          <div>
            <h3 class="font-bold text-lg mb-4"><span class="text-primary-400">Mint</span>Shop</h3>
            <p class="text-sm text-gray-400">
              최고의 쇼핑 경험을 제공합니다.
            </p>
          </div>
        </div>
        <div class="border-t border-gray-800 mt-8 pt-8 text-center text-sm text-gray-500">
          <p>&copy; 2025 <span class="text-primary-400">Mint</span>Shop. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
</script>
