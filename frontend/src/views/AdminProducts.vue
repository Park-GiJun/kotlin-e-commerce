<template>
  <Layout>
    <div class="flex justify-between mb-6">
      <h2 class="text-2xl font-bold">상품 관리</h2>
      <button @click="showModal()" class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded">+ 상품 추가</button>
    </div>
    <div class="bg-white shadow rounded-md">
      <ul v-if="products.length" class="divide-y">
        <li v-for="product in products" :key="product.id" class="px-6 py-4 flex justify-between items-center">
          <div>
            <p class="font-medium text-indigo-600">{{ product.name }}</p>
            <p class="text-sm text-gray-500">카테고리: {{ product.categoryId }} | ID: {{ product.id }}</p>
          </div>
          <div class="flex gap-2">
            <button @click="showModal(product)" class="bg-blue-500 text-white px-3 py-1 rounded text-sm">수정</button>
            <button @click="deleteProduct(product.id)" class="bg-red-500 text-white px-3 py-1 rounded text-sm">삭제</button>
          </div>
        </li>
      </ul>
    </div>

    <!-- Modal -->
    <div v-if="modalOpen" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center">
      <div class="bg-white rounded-lg p-6 max-w-md w-full">
        <h3 class="text-lg font-medium mb-4">{{ editMode ? '상품 수정' : '상품 추가' }}</h3>
        <form @submit.prevent="saveProduct" class="space-y-4">
          <input v-model="form.name" type="text" required placeholder="상품명" class="w-full px-3 py-2 border rounded-md">
          <select v-model="form.categoryId" required class="w-full px-3 py-2 border rounded-md">
            <option value="">카테고리 선택</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
          </select>
          <div class="flex gap-2">
            <button type="submit" class="flex-1 bg-indigo-600 text-white px-4 py-2 rounded">저장</button>
            <button type="button" @click="modalOpen = false" class="flex-1 bg-gray-300 px-4 py-2 rounded">취소</button>
          </div>
        </form>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { productAPI } from '../api/product'
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'

const products = ref([])
const categories = ref([])
const modalOpen = ref(false)
const editMode = ref(false)
const form = ref({ id: null, name: '', categoryId: '' })

onMounted(async () => {
  await loadProducts()
  const res = await categoryAPI.getAll()
  categories.value = res.data || []
})

async function loadProducts() {
  const res = await productAPI.getAll()
  products.value = res.data || []
}

function showModal(product = null) {
  if (product) {
    editMode.value = true
    form.value = { id: product.id, name: product.name, categoryId: product.categoryId }
  } else {
    editMode.value = false
    form.value = { id: null, name: '', categoryId: '' }
  }
  modalOpen.value = true
}

async function saveProduct() {
  try {
    if (editMode.value) {
      await productAPI.update(form.value.id, form.value.categoryId, form.value.name)
    } else {
      await productAPI.create(form.value.categoryId, form.value.name)
    }
    modalOpen.value = false
    loadProducts()
  } catch (error) {
    alert('저장 실패: ' + error.message)
  }
}

async function deleteProduct(id) {
  if (!confirm('삭제하시겠습니까?')) return
  await productAPI.delete(id)
  loadProducts()
}
</script>
