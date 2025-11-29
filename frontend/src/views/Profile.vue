<template>
  <Layout>
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-8">내 정보</h1>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Profile Sidebar -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-md p-6">
            <div class="text-center">
              <div class="w-24 h-24 mx-auto bg-gradient-to-br from-primary-400 to-primary-600 rounded-full flex items-center justify-center text-white text-3xl font-bold">
                {{ user?.name?.charAt(0).toUpperCase() }}
              </div>
              <h2 class="mt-4 text-xl font-bold text-gray-900">{{ user?.name }}</h2>
              <p class="text-sm text-gray-600">{{ user?.email }}</p>
              <span class="mt-3 inline-flex px-3 py-1 rounded-full text-xs font-medium bg-primary-100 text-primary-800">
                {{ user?.role === 'ADMIN' ? '관리자' : '일반 회원' }}
              </span>
            </div>

            <div class="mt-6 space-y-2">
              <router-link to="/orders" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50">
                <div class="flex items-center">
                  <svg class="w-5 h-5 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                  </svg>
                  <span class="ml-3 text-sm font-medium text-gray-900">주문 내역</span>
                </div>
                <svg class="w-5 h-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </router-link>

              <router-link to="/wishlist" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50">
                <div class="flex items-center">
                  <svg class="w-5 h-5 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                  </svg>
                  <span class="ml-3 text-sm font-medium text-gray-900">찜 목록</span>
                </div>
                <svg class="w-5 h-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </router-link>
            </div>
          </div>
        </div>

        <!-- Profile Content -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Personal Information -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <div class="flex items-center justify-between mb-6">
              <h2 class="text-xl font-bold text-gray-900">기본 정보</h2>
              <button v-if="!isEditing" @click="isEditing = true" class="px-4 py-2 text-sm font-medium text-primary-600 hover:text-primary-700">
                수정
              </button>
            </div>

            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">이름</label>
                <input v-model="form.name" :disabled="!isEditing" type="text"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 disabled:bg-gray-50 disabled:text-gray-500">
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">이메일</label>
                <input v-model="form.email" disabled type="email"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg bg-gray-50 text-gray-500">
                <p class="mt-1 text-xs text-gray-500">이메일은 변경할 수 없습니다.</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">전화번호</label>
                <input v-model="form.phone" :disabled="!isEditing" type="tel" placeholder="010-0000-0000"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 disabled:bg-gray-50 disabled:text-gray-500">
              </div>

              <div v-if="isEditing" class="flex space-x-3 pt-4">
                <button @click="saveProfile" class="flex-1 px-4 py-2 bg-primary-600 text-white rounded-lg font-medium hover:bg-primary-700">
                  저장
                </button>
                <button @click="cancelEdit" class="flex-1 px-4 py-2 border border-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-50">
                  취소
                </button>
              </div>
            </div>
          </div>

          <!-- Change Password -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-6">비밀번호 변경</h2>
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">현재 비밀번호</label>
                <input v-model="passwordForm.current" type="password"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">새 비밀번호</label>
                <input v-model="passwordForm.new" type="password"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">새 비밀번호 확인</label>
                <input v-model="passwordForm.confirm" type="password"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <button @click="changePassword" class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg font-medium hover:bg-gray-900">
                비밀번호 변경
              </button>
            </div>
          </div>

          <!-- Statistics -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-6">활동 통계</h2>
            <div class="grid grid-cols-3 gap-4">
              <div class="text-center p-4 bg-gray-50 rounded-lg">
                <p class="text-2xl font-bold text-primary-600">{{ stats.orders }}</p>
                <p class="text-sm text-gray-600 mt-1">총 주문</p>
              </div>
              <div class="text-center p-4 bg-gray-50 rounded-lg">
                <p class="text-2xl font-bold text-primary-600">{{ stats.reviews }}</p>
                <p class="text-sm text-gray-600 mt-1">작성 리뷰</p>
              </div>
              <div class="text-center p-4 bg-gray-50 rounded-lg">
                <p class="text-2xl font-bold text-primary-600">{{ stats.wishlist }}</p>
                <p class="text-sm text-gray-600 mt-1">찜 목록</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useAuthStore } from '../stores/auth'
import { useWishlistStore } from '../stores/wishlist'
import { faker } from '@faker-js/faker/locale/ko'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, warning } = useAlert()

const authStore = useAuthStore()
const wishlistStore = useWishlistStore()

const user = computed(() => authStore.user)

const isEditing = ref(false)

const form = reactive({
  name: user.value?.name || '',
  email: user.value?.email || '',
  phone: faker.phone.number('010-####-####')
})

const passwordForm = reactive({
  current: '',
  new: '',
  confirm: ''
})

const stats = reactive({
  orders: JSON.parse(localStorage.getItem('orders') || '[]').length + faker.number.int({ min: 5, max: 20 }),
  reviews: faker.number.int({ min: 0, max: 10 }),
  wishlist: wishlistStore.itemCount
})

function saveProfile() {
  // Update user info
  const updatedUser = { ...user.value, name: form.name }
  localStorage.setItem('user_info', JSON.stringify(updatedUser))
  authStore.user = updatedUser
  isEditing.value = false
  success('프로필이 업데이트되었습니다.')
}

function cancelEdit() {
  form.name = user.value?.name || ''
  form.phone = faker.phone.number('010-####-####')
  isEditing.value = false
}

function changePassword() {
  if (!passwordForm.current || !passwordForm.new || !passwordForm.confirm) {
    warning('모든 필드를 입력해주세요.')
    return
  }

  if (passwordForm.new !== passwordForm.confirm) {
    warning('새 비밀번호가 일치하지 않습니다.')
    return
  }

  if (passwordForm.new.length < 6) {
    warning('비밀번호는 최소 6자 이상이어야 합니다.')
    return
  }

  success('비밀번호가 변경되었습니다.')
  passwordForm.current = ''
  passwordForm.new = ''
  passwordForm.confirm = ''
}
</script>
