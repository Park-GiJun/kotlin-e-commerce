<template>
  <Layout>
    <h2 class="text-2xl font-bold text-gray-900 mb-6">대시보드</h2>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <div class="bg-white p-6 rounded-lg shadow">
        <dt class="text-sm font-medium text-gray-500">총 상품 수</dt>
        <dd class="mt-1 text-3xl font-semibold text-gray-900">{{ stats.totalProducts }}</dd>
      </div>
      <div class="bg-white p-6 rounded-lg shadow">
        <dt class="text-sm font-medium text-gray-500">총 카테고리 수</dt>
        <dd class="mt-1 text-3xl font-semibold text-gray-900">{{ stats.totalCategories }}</dd>
      </div>
      <div class="bg-white p-6 rounded-lg shadow">
        <dt class="text-sm font-medium text-gray-500">사용자 역할</dt>
        <dd class="mt-1 text-3xl font-semibold text-gray-900">{{ authStore.user?.role === 'ADMIN' ? '관리자' : '일반 사용자' }}</dd>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import { productAPI } from '../api/product'
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'

const authStore = useAuthStore()
const stats = ref({ totalProducts: 0, totalCategories: 0 })

onMounted(async () => {
  try {
    const [productsRes, categoriesRes] = await Promise.all([productAPI.getAll(), categoryAPI.getAll()])
    stats.value.totalProducts = productsRes.data?.length || 0
    stats.value.totalCategories = categoriesRes.data?.length || 0
  } catch (error) {
    console.error('통계 로드 실패:', error)
  }
})
</script>
