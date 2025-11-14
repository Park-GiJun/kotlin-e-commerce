<template>
  <Layout>
    <h2 class="text-2xl font-bold mb-6">회원 관리</h2>
    <div class="bg-white shadow rounded-lg p-6">
      <div class="flex gap-4 mb-4">
        <input v-model="searchEmail" type="email" placeholder="이메일을 입력하세요" class="flex-1 px-3 py-2 border rounded-md">
        <button @click="searchUser" class="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-2 rounded-md">검색</button>
      </div>
      <div v-if="user" class="mt-4 p-4 border rounded-md flex justify-between">
        <div>
          <p class="text-sm font-medium">이름: {{ user.name }}</p>
          <p class="text-sm text-gray-500 mt-1">이메일: {{ user.email }}</p>
          <p class="text-sm text-gray-500 mt-1">역할: {{ user.role }}</p>
        </div>
        <button @click="deleteUser" class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded">삭제</button>
      </div>
      <p v-else-if="searched" class="mt-4 text-center text-gray-500">검색 결과가 없습니다.</p>
    </div>
  </Layout>
</template>

<script setup>
import { ref } from 'vue'
import { userAPI } from '../api/user'
import Layout from '../components/Layout.vue'

const searchEmail = ref('')
const user = ref(null)
const searched = ref(false)

async function searchUser() {
  try {
    const res = await userAPI.getByEmail(searchEmail.value)
    user.value = res.data
    searched.value = true
  } catch (error) {
    user.value = null
    searched.value = true
  }
}

async function deleteUser() {
  if (!confirm('정말 삭제하시겠습니까?')) return
  try {
    await userAPI.delete(searchEmail.value)
    alert('삭제되었습니다.')
    user.value = null
    searchEmail.value = ''
  } catch (error) {
    alert('삭제 실패: ' + error.message)
  }
}
</script>
