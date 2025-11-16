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
        <p class="mt-2 text-gray-600">새 계정을 만드세요</p>
      </div>

      <!-- Form Card -->
      <div class="bg-white rounded-xl shadow-lg p-8">
        <form class="space-y-6" @submit.prevent="handleRegister">
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
            <label for="name" class="block text-sm font-medium text-gray-700 mb-2">이름</label>
            <input
              id="name"
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-colors"
              placeholder="이름을 입력하세요">
          </div>

          <div>
            <label for="password" class="block text-sm font-medium text-gray-700 mb-2">비밀번호</label>
            <input
              id="password"
              v-model="form.password"
              type="password"
              required
              minlength="8"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-colors"
              placeholder="비밀번호 (최소 8자)">
          </div>

          <div>
            <label for="role" class="block text-sm font-medium text-gray-700 mb-2">계정 유형</label>
            <select
              id="role"
              v-model="form.role"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-colors bg-white">
              <option value="USER">일반 사용자</option>
              <option value="ADMIN">관리자</option>
            </select>
          </div>

          <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg text-sm">
            {{ error }}
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full flex justify-center py-3 px-4 border border-transparent text-sm font-semibold rounded-lg text-white bg-primary-600 hover:bg-primary-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 disabled:opacity-50 disabled:cursor-not-allowed transition-colors shadow-md">
            {{ loading ? '처리 중...' : '회원가입' }}
          </button>

          <div class="text-center pt-4">
            <router-link to="/login" class="font-medium text-primary-600 hover:text-primary-500 transition-colors">
              이미 계정이 있으신가요? <span class="underline">로그인</span>
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
const form = ref({ email: '', name: '', password: '', role: 'USER' })
const loading = ref(false)
const error = ref('')

async function handleRegister() {
  loading.value = true
  error.value = ''
  const result = await authStore.register(form.value.email, form.value.name, form.value.password, form.value.role)
  if (result.success) {
    alert('회원가입 성공!')
    router.push('/login')
  } else {
    error.value = result.message
  }
  loading.value = false
}
</script>
