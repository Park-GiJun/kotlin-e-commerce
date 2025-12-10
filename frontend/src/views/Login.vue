<template>
  <div class="h-screen flex bg-gray-100">
    <!-- Left: Branding -->
    <div class="hidden lg:flex lg:w-1/2 bg-gradient-to-br from-teal-600 to-cyan-700 items-center justify-center p-12">
      <div class="text-white text-center">
        <svg class="w-20 h-20 mx-auto mb-6" fill="currentColor" viewBox="0 0 20 20">
          <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
        </svg>
        <h1 class="text-4xl font-bold mb-4">MintShop</h1>
        <p class="text-teal-100 text-lg">당신을 위한 특별한 쇼핑</p>
        <div class="mt-12 grid grid-cols-3 gap-4 text-center">
          <div class="bg-white/10 rounded-lg p-4">
            <div class="text-2xl font-bold">10K+</div>
            <div class="text-xs text-teal-200">상품</div>
          </div>
          <div class="bg-white/10 rounded-lg p-4">
            <div class="text-2xl font-bold">50K+</div>
            <div class="text-xs text-teal-200">고객</div>
          </div>
          <div class="bg-white/10 rounded-lg p-4">
            <div class="text-2xl font-bold">99%</div>
            <div class="text-xs text-teal-200">만족도</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Right: Login Form -->
    <div class="flex-1 flex items-center justify-center p-8">
      <div class="w-full max-w-md">
        <!-- Mobile Logo -->
        <div class="lg:hidden text-center mb-8">
          <svg class="w-12 h-12 mx-auto text-teal-600" fill="currentColor" viewBox="0 0 20 20">
            <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
          </svg>
          <h2 class="text-2xl font-bold text-gray-900 mt-2">MintShop</h2>
        </div>

        <div class="bg-white rounded-2xl shadow-xl p-8">
          <h2 class="text-2xl font-bold text-gray-900 mb-2">다시 오신 것을 환영합니다</h2>
          <p class="text-gray-500 mb-6">계정에 로그인하세요</p>

          <form @submit.prevent="handleLogin" class="space-y-5">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">이메일</label>
              <input
                v-model="form.email"
                type="email"
                required
                placeholder="이메일을 입력하세요"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent transition-all"
              />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">비밀번호</label>
              <input
                v-model="form.password"
                type="password"
                required
                placeholder="비밀번호를 입력하세요"
                class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent transition-all"
              />
            </div>

            <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg text-sm">
              {{ error }}
            </div>

            <button
              type="submit"
              :disabled="loading"
              class="w-full py-3 bg-teal-600 hover:bg-teal-700 text-white font-semibold rounded-lg transition-colors disabled:opacity-50"
            >
              {{ loading ? '로그인 중...' : '로그인' }}
            </button>

            <div class="relative">
              <div class="absolute inset-0 flex items-center">
                <div class="w-full border-t border-gray-200"></div>
              </div>
              <div class="relative flex justify-center text-sm">
                <span class="px-3 bg-white text-gray-500">또는</span>
              </div>
            </div>

            <button
              type="button"
              @click="handleAdminLogin"
              :disabled="loading"
              class="w-full py-3 border-2 border-gray-800 text-gray-900 font-semibold rounded-lg hover:bg-gray-50 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
              관리자 로그인
            </button>

            <div class="text-center pt-2">
              <router-link to="/register" class="text-teal-600 hover:text-teal-700 font-medium transition-colors">
                계정이 없으신가요? <span class="underline">회원가입</span>
              </router-link>
            </div>
          </form>
        </div>
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

const form = ref({ email: '', password: '' })
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
  const result = await authStore.login('admin@admin.com', 'admin1234')
  if (result.success) {
    router.push('/dashboard')
  } else {
    error.value = result.message
  }
  loading.value = false
}
</script>
