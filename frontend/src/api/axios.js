import axios from 'axios'
import router from '../router'

const instance = axios.create({
  baseURL: '/api',
    // baseURL: 'http://localhost:9832/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Request interceptor - JWT 토큰 추가
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwt_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor - 401 처리
instance.interceptors.response.use(
  (response) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('jwt_token')
      localStorage.removeItem('user_info')
      router.push('/login')
    }
    return Promise.reject(error)
  }
)

export default instance
