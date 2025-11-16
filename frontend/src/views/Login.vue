<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full">
      <!-- Logo and Title -->
      <div class="text-center mb-8">
        <div class="flex items-center justify-center mb-4">
          <svg class="w-12 h-12 text-primary-500" fill="currentColor" viewBox="0 0 20 20">
            <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
          </svg>
        </div>
        <h2 class="text-4xl font-bold text-gray-900">
          <span class="text-primary-500">Mint</span>Shop
        </h2>
        <p class="mt-2 text-gray-600">계정에 로그인하세요</p>
      </div>

      <!-- Form Card -->
      <div class="bg-white rounded-xl shadow-lg p-8">
        <form class="space-y-6" @submit.prevent="handleLogin">
          <div>
            <label for="email" class="block text-sm font-medium text-gray-700 mb-2">이메일</label>
            <input
              id="email"
              v-model="form.email"
              type="email"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-colors"
              placeholder="이메일을 입력하세요">
          </div>
          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">비밀번호</label>
            <input
              id="password"
              v-model="form.password"
              type="password"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-colors"
              placeholder="비밀번호를 입력하세요">
          </div>

          <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg text-sm">
            {{ error }}
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-lg text-white bg-primary-600 hover:bg-primary-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors shadow-md">
            {{ loading ? '로그인 중...' : '로그인' }}
          </button>

          <!-- Divider -->
          <div class="relative">
            <div class="absolute inset-0 flex items-center">
              <div class="w-full border-t border-gray-300"></div>
            </div>
            <div class="relative flex justify-center text-sm">
              <span class="px-2 bg-white text-gray-500">또는</span>
            </div>
          </div>

          <!-- Admin Login Button -->
          <button
            type="button"
            @click="handleAdminLogin"
            :disabled="loading"
            class="w-full flex justify-center items-center gap-2 py-3 px-4 border-2 border-gray-800 text-sm font-semibold rounded-lg text-gray-900 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
            </svg>
            {{ loading ? '로그인 중...' : '어드민 로그인' }}
          </button>

          <div class="text-center pt-4">
            <router-link to="/register" class="font-medium text-primary-600 hover:text-primary-500 transition-colors">
              계정이 없으신가요? <span class="underline">회원가입</span>
            </router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const form = ref({
  email: '',
  password: ''
})

const loading = ref(false)
const error = ref('')

async function handleLogin() {
  loading.value = true
  error.value = ''

  const result = await authStore.login(form.value.email, form.value.password)

  if (result.success) {
    router.push('/dashboard')
  } else {
    error.value = result.message
  }

  loading.value = false
}

async function handleAdminLogin() {
  loading.value = true
  error.value = ''

  const result = await authStore.login('admin@adminn.com', 'admin1234')

  if (result.success) {
    router.push('/dashboard')
  } else {
    error.value = result.message
  }

  loading.value = false
}
</script>
