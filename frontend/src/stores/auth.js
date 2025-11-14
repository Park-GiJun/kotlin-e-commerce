import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authAPI } from '../api/auth'
import router from '../router'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem('jwt_token') || null)
  const user = ref(JSON.parse(localStorage.getItem('user_info') || 'null'))

  const isAuthenticated = computed(() => !!token.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  async function login(email, password) {
    try {
      const response = await authAPI.login(email, password)

      if (response.success && response.data) {
        token.value = response.data.token
        user.value = response.data.user

        localStorage.setItem('jwt_token', response.data.token)
        localStorage.setItem('user_info', JSON.stringify(response.data.user))

        return { success: true }
      }

      return { success: false, message: response.message || '로그인 실패' }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || '로그인 중 오류가 발생했습니다.'
      }
    }
  }

  async function register(email, name, password, role = 'USER') {
    try {
      const response = await authAPI.register(email, name, password, role)

      if (response.success) {
        return { success: true }
      }

      return { success: false, message: response.message || '회원가입 실패' }
    } catch (error) {
      return {
        success: false,
        message: error.response?.data?.message || '회원가입 중 오류가 발생했습니다.'
      }
    }
  }

  function logout() {
    token.value = null
    user.value = null
    localStorage.removeItem('jwt_token')
    localStorage.removeItem('user_info')
    router.push('/login')
  }

  return {
    token,
    user,
    isAuthenticated,
    isAdmin,
    login,
    register,
    logout
  }
})
