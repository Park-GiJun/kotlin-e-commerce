<template>
  <div class="min-h-screen bg-gray-100">
    <nav class="bg-white shadow-lg">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between h-16">
          <div class="flex">
            <div class="flex-shrink-0 flex items-center">
              <h1 class="text-xl font-bold text-indigo-600">E-Commerce</h1>
            </div>
            <div class="hidden sm:ml-6 sm:flex sm:space-x-8">
              <router-link to="/dashboard" class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                대시보드
              </router-link>
              <router-link to="/products" class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                상품 목록
              </router-link>
              <template v-if="authStore.isAdmin">
                <router-link to="/admin/users" class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                  회원 관리
                </router-link>
                <router-link to="/admin/products" class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                  상품 관리
                </router-link>
                <router-link to="/admin/categories" class="border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700 inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium">
                  카테고리 관리
                </router-link>
              </template>
            </div>
          </div>
          <div class="flex items-center">
            <span class="text-gray-700 mr-4">{{ authStore.user?.name }}</span>
            <span class="text-xs bg-indigo-100 text-indigo-800 px-2 py-1 rounded mr-4">{{ authStore.user?.role }}</span>
            <button @click="authStore.logout()" class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded text-sm">
              로그아웃
            </button>
          </div>
        </div>
      </div>
    </nav>

    <div class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <div class="px-4 py-6 sm:px-0">
        <slot />
      </div>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
</script>
