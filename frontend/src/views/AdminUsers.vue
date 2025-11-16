<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6">
      <div class="mb-6">
        <h2 class="text-3xl font-bold text-gray-900">회원 관리</h2>
        <p class="text-gray-600 mt-1">회원을 조회하고 관리할 수 있습니다</p>
      </div>

      <!-- Master-Detail Layout -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Master: 회원 목록 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden flex flex-col" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b">
            <h3 class="text-lg font-semibold text-gray-900">회원 목록</h3>
          </div>

          <div class="flex-1 overflow-y-auto">
            <div v-if="users.length" class="divide-y divide-gray-200">
              <div
                v-for="u in users"
                :key="u.id || u.email"
                @click="selectUser(u)"
                :class="[
                  'px-6 py-4 cursor-pointer transition-colors',
                  selectedUser?.email === u.email
                    ? 'bg-teal-50 border-l-4 border-teal-600'
                    : 'hover:bg-gray-50'
                ]">
                <div class="flex items-center space-x-3">
                  <div class="w-10 h-10 rounded-full flex items-center justify-center text-white text-sm font-bold" style="background-color: #0d9488;">
                    {{ u.name?.charAt(0).toUpperCase() }}
                  </div>
                  <div class="flex-1">
                    <div class="font-medium text-gray-900">{{ u.name }}</div>
                    <div class="text-sm text-gray-600">{{ u.email }}</div>
                  </div>
                  <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold"
                        :class="u.role === 'ADMIN' ? 'text-white' : 'bg-gray-200 text-gray-800'"
                        :style="u.role === 'ADMIN' ? 'background-color: #0d9488;' : ''">
                    {{ u.role === 'ADMIN' ? '관리자' : '일반' }}
                  </span>
                </div>
              </div>
            </div>
            <div v-else class="px-6 py-12 text-center text-gray-500">
              <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
              <p class="text-lg">등록된 회원이 없습니다</p>
            </div>
          </div>

          <div v-if="totalPages > 1" class="bg-gray-50 px-6 py-3 border-t flex items-center justify-between">
            <div class="text-sm text-gray-600">
              페이지 {{ currentPage + 1 }} / {{ totalPages }}
            </div>
            <div class="flex gap-2">
              <button @click="prevPage" :disabled="currentPage === 0" class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm">
                이전
              </button>
              <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm">
                다음
              </button>
            </div>
          </div>
        </div>

        <!-- Detail: 회원 상세 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b" style="background-color: #f0fdf9;">
            <h3 class="text-lg font-semibold" style="color: #0d9488;">회원 정보</h3>
          </div>

          <div v-if="selectedUser" class="p-6 overflow-y-auto" style="max-height: calc(100vh - 320px);">
            <div class="space-y-6">
              <!-- User Info -->
              <div class="flex items-center space-x-4">
                <div class="w-16 h-16 rounded-full flex items-center justify-center text-white text-2xl font-bold" style="background-color: #0d9488;">
                  {{ selectedUser.name?.charAt(0).toUpperCase() }}
                </div>
                <div>
                  <h4 class="text-xl font-bold text-gray-900">{{ selectedUser.name }}</h4>
                  <p class="text-sm text-gray-500">{{ selectedUser.email }}</p>
                </div>
              </div>

              <div class="space-y-3">
                <div class="flex items-center justify-between py-3 border-b border-gray-100">
                  <span class="text-sm font-medium text-gray-600">이메일</span>
                  <span class="text-sm text-gray-900">{{ selectedUser.email }}</span>
                </div>
                <div class="flex items-center justify-between py-3 border-b border-gray-100">
                  <span class="text-sm font-medium text-gray-600">이름</span>
                  <span class="text-sm text-gray-900">{{ selectedUser.name }}</span>
                </div>
                <div class="flex items-center justify-between py-3 border-b border-gray-100">
                  <span class="text-sm font-medium text-gray-600">역할</span>
                  <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold"
                        :class="selectedUser.role === 'ADMIN' ? 'text-white' : 'bg-gray-200 text-gray-800'"
                        :style="selectedUser.role === 'ADMIN' ? 'background-color: #0d9488;' : ''">
                    {{ selectedUser.role === 'ADMIN' ? '관리자' : '일반 사용자' }}
                  </span>
                </div>
              </div>

              <!-- Actions -->
              <div class="bg-gray-50 rounded-lg p-6 border border-gray-200">
                <h5 class="font-semibold text-gray-900 mb-4">관리 작업</h5>
                <div class="space-y-3">
                  <button
                    @click="confirmDelete"
                    class="w-full flex items-center justify-center gap-2 hover:opacity-90 px-4 py-3 rounded-lg font-medium transition-all"
                    style="background-color: #ef4444; color: white;">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                    </svg>
                    회원 삭제
                  </button>
                  <button
                    @click="clearSelection"
                    class="w-full flex items-center justify-center gap-2 hover:bg-gray-300 px-4 py-3 rounded-lg font-medium transition-colors"
                    style="background-color: #e5e7eb; color: #1f2937;">
                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                    </svg>
                    선택 해제
                  </button>
                </div>
              </div>

              <div class="bg-yellow-50 border border-yellow-200 rounded-lg p-4">
                <div class="flex">
                  <svg class="w-5 h-5 text-yellow-600 mr-2 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clip-rule="evenodd" />
                  </svg>
                  <div>
                    <h6 class="text-sm font-semibold text-yellow-800">주의사항</h6>
                    <p class="text-xs text-yellow-700 mt-1">회원 삭제는 복구할 수 없습니다. 신중하게 진행하세요.</p>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div v-else class="p-12 text-center text-gray-500">
            <svg class="w-20 h-20 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
            </svg>
            <h3 class="text-lg font-medium text-gray-900 mb-2">회원을 선택하세요</h3>
            <p class="text-gray-500">좌측 목록에서 회원을 선택하면<br>상세 정보가 표시됩니다</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div class="bg-white rounded-xl shadow-2xl max-w-md w-full transform transition-all">
        <div class="p-6">
          <div class="flex items-center justify-center w-12 h-12 mx-auto bg-red-100 rounded-full mb-4">
            <svg class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
          </div>
          <h3 class="text-lg font-bold text-gray-900 text-center mb-2">회원 삭제 확인</h3>
          <p class="text-sm text-gray-600 text-center mb-6">
            <span class="font-semibold">{{ selectedUser?.name }}</span> ({{ selectedUser?.email }}) 회원을 정말 삭제하시겠습니까?<br>
            <span class="text-red-600">이 작업은 되돌릴 수 없습니다.</span>
          </p>
          <div class="flex gap-3">
            <button
              @click="showDeleteModal = false"
              class="flex-1 hover:bg-gray-300 px-4 py-2.5 rounded-lg font-medium transition-colors"
              style="background-color: #e5e7eb; color: #1f2937;">
              취소
            </button>
            <button
              @click="deleteUser"
              class="flex-1 hover:opacity-90 px-4 py-2.5 rounded-lg font-medium transition-all"
              style="background-color: #ef4444; color: white;">
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { userAPI } from '../api/user'
import Layout from '../components/Layout.vue'

const users = ref([])
const selectedUser = ref(null)
const showDeleteModal = ref(false)
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = ref(10)

onMounted(loadUsers)

async function loadUsers() {
  try {
    const res = await userAPI.getAll(currentPage.value, pageSize.value)
    const pageResponse = res.data
    if (pageResponse) {
      users.value = pageResponse.content || []
      currentPage.value = pageResponse.page
      totalPages.value = pageResponse.totalPages
    }
  } catch (error) {
    console.error('Failed to load users:', error)
    alert('회원 목록을 불러오는데 실패했습니다: ' + error.message)
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++
    loadUsers()
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value--
    loadUsers()
  }
}

function selectUser(user) {
  selectedUser.value = user
}

function clearSelection() {
  selectedUser.value = null
}

function confirmDelete() {
  showDeleteModal.value = true
}

async function deleteUser() {
  try {
    await userAPI.delete(selectedUser.value.email)
    showDeleteModal.value = false
    alert('회원이 삭제되었습니다.')
    clearSelection()
    await loadUsers()
  } catch (error) {
    showDeleteModal.value = false
    alert('삭제 실패: ' + error.message)
  }
}
</script>