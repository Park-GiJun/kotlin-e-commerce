<template>
  <Layout>
    <div class="flex justify-between mb-6">
      <h2 class="text-2xl font-bold">카테고리 관리</h2>
      <button @click="showModal()" class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded">+ 카테고리 추가</button>
    </div>
    <div class="bg-white shadow rounded-md">
      <ul v-if="categories.length" class="divide-y">
        <li v-for="cat in categories" :key="cat.id" class="px-6 py-4 flex justify-between items-center">
          <div>
            <p class="font-medium text-indigo-600">{{ cat.name }}</p>
            <p class="text-sm text-gray-500">ID: {{ cat.id }}</p>
          </div>
          <div class="flex gap-2">
            <button @click="showModal(cat)" class="bg-blue-500 text-white px-3 py-1 rounded text-sm">수정</button>
            <button @click="deleteCategory(cat.id)" class="bg-red-500 text-white px-3 py-1 rounded text-sm">삭제</button>
          </div>
        </li>
      </ul>
    </div>

    <!-- Modal -->
    <div v-if="modalOpen" class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center">
      <div class="bg-white rounded-lg p-6 max-w-md w-full">
        <h3 class="text-lg font-medium mb-4">{{ editMode ? '카테고리 수정' : '카테고리 추가' }}</h3>
        <form @submit.prevent="saveCategory" class="space-y-4">
          <input v-model="form.name" type="text" required placeholder="카테고리명" class="w-full px-3 py-2 border rounded-md">
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
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'

const categories = ref([])
const modalOpen = ref(false)
const editMode = ref(false)
const form = ref({ id: null, name: '' })

onMounted(loadCategories)

async function loadCategories() {
  const res = await categoryAPI.getAll()
  categories.value = res.data || []
}

function showModal(cat = null) {
  if (cat) {
    editMode.value = true
    form.value = { id: cat.id, name: cat.name }
  } else {
    editMode.value = false
    form.value = { id: null, name: '' }
  }
  modalOpen.value = true
}

async function saveCategory() {
  try {
    if (editMode.value) {
      await categoryAPI.update(form.value.id, form.value.name)
    } else {
      await categoryAPI.create(form.value.name)
    }
    modalOpen.value = false
    loadCategories()
  } catch (error) {
    alert('저장 실패: ' + error.message)
  }
}

async function deleteCategory(id) {
  if (!confirm('삭제하시겠습니까?')) return
  await categoryAPI.delete(id)
  loadCategories()
}
</script>
