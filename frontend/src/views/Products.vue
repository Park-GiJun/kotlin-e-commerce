<template>
  <Layout>
    <h2 class="text-2xl font-bold mb-6">상품 목록</h2>
    <div class="bg-white shadow rounded-md">
      <ul v-if="products.length" class="divide-y divide-gray-200">
        <li v-for="product in products" :key="product.id" class="px-6 py-4">
          <p class="text-sm font-medium text-indigo-600">{{ product.name }}</p>
          <p class="text-sm text-gray-500">카테고리 ID: {{ product.categoryId }} | ID: {{ product.id }}</p>
        </li>
      </ul>
      <p v-else class="px-6 py-4 text-center text-gray-500">{{ loading ? '로딩 중...' : '등록된 상품이 없습니다.' }}</p>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { productAPI } from '../api/product'
import Layout from '../components/Layout.vue'

const products = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const res = await productAPI.getAll()
    products.value = res.data || []
  } catch (error) {
    console.error('상품 로드 실패:', error)
  }
  loading.value = false
})
</script>
