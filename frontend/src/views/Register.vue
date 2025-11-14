<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100 py-12 px-4">
    <div class="max-w-md w-full space-y-8">
      <h2 class="text-3xl font-extrabold text-gray-900 text-center">회원가입</h2>
      <form @submit.prevent="handleRegister" class="mt-8 space-y-4">
        <input v-model="form.email" type="email" required placeholder="이메일" class="w-full px-3 py-2 border rounded-md">
        <input v-model="form.name" type="text" required placeholder="이름" class="w-full px-3 py-2 border rounded-md">
        <input v-model="form.password" type="password" required minlength="8" placeholder="비밀번호 (최소 8자)" class="w-full px-3 py-2 border rounded-md">
        <select v-model="form.role" class="w-full px-3 py-2 border rounded-md">
          <option value="USER">일반 사용자</option>
          <option value="ADMIN">관리자</option>
        </select>
        <div v-if="error" class="text-red-500 text-sm">{{ error }}</div>
        <button type="submit" :disabled="loading" class="w-full py-2 px-4 bg-indigo-600 hover:bg-indigo-700 text-white rounded-md disabled:opacity-50">
          {{ loading ? '처리 중...' : '회원가입' }}
        </button>
        <div class="text-center">
          <router-link to="/login" class="text-indigo-600 hover:text-indigo-500">이미 계정이 있으신가요? 로그인</router-link>
        </div>
      </form>
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
