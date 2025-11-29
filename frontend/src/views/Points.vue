<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-4xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">포인트</h1>
          <p class="text-gray-600 mt-1">적립 및 사용 내역을 확인하세요</p>
        </div>

        <!-- Point Summary -->
        <div class="bg-gradient-to-r from-teal-600 to-teal-500 rounded-xl shadow-sm p-6 mb-6 text-white">
          <div class="flex justify-between items-center">
            <div>
              <p class="text-teal-100 text-sm">보유 포인트</p>
              <p class="text-4xl font-bold mt-1">{{ formatPrice(totalPoints) }} <span class="text-xl">P</span></p>
            </div>
            <div class="text-right">
              <p class="text-teal-100 text-sm">소멸 예정 포인트</p>
              <p class="text-xl font-bold mt-1">{{ formatPrice(expiringPoints) }} P</p>
              <p class="text-xs text-teal-200">30일 이내</p>
            </div>
          </div>
          <div class="mt-6 pt-4 border-t border-teal-400 grid grid-cols-3 gap-4 text-center">
            <div>
              <p class="text-teal-100 text-xs">이번 달 적립</p>
              <p class="font-bold mt-1">+{{ formatPrice(monthlyEarned) }} P</p>
            </div>
            <div>
              <p class="text-teal-100 text-xs">이번 달 사용</p>
              <p class="font-bold mt-1">-{{ formatPrice(monthlyUsed) }} P</p>
            </div>
            <div>
              <p class="text-teal-100 text-xs">회원 등급</p>
              <p class="font-bold mt-1">{{ memberGrade }}</p>
            </div>
          </div>
        </div>

        <!-- Point Info -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="flex items-start gap-3">
            <svg class="w-5 h-5 text-teal-600 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <div class="text-sm text-gray-600">
              <p class="font-medium text-gray-900 mb-1">포인트 적립 안내</p>
              <ul class="list-disc list-inside space-y-1 text-gray-500">
                <li>상품 구매 시 결제 금액의 {{ earnRate }}% 적립</li>
                <li>리뷰 작성 시 최대 500P 적립</li>
                <li>포인트는 적립일로부터 1년간 유효합니다</li>
                <li>1,000P 이상부터 사용 가능 (1P = 1원)</li>
              </ul>
            </div>
          </div>
        </div>

        <!-- Tabs -->
        <div class="flex gap-4 mb-6">
          <button
            @click="activeTab = 'all'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'all' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            전체
          </button>
          <button
            @click="activeTab = 'earned'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'earned' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            적립
          </button>
          <button
            @click="activeTab = 'used'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'used' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            사용
          </button>
          <button
            @click="activeTab = 'expired'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'expired' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            소멸
          </button>
        </div>

        <!-- Point History -->
        <div class="bg-white rounded-xl shadow-sm overflow-hidden">
          <div class="divide-y">
            <div v-for="item in filteredHistory" :key="item.id" class="p-4 hover:bg-gray-50">
              <div class="flex justify-between items-start">
                <div>
                  <div class="flex items-center gap-2">
                    <span :class="['w-2 h-2 rounded-full', getStatusColor(item.type)]"></span>
                    <span class="font-medium text-gray-900">{{ item.description }}</span>
                  </div>
                  <p class="text-sm text-gray-500 mt-1">{{ item.date }}</p>
                </div>
                <div class="text-right">
                  <span :class="['text-lg font-bold', item.type === 'earned' ? 'text-teal-600' : item.type === 'used' ? 'text-gray-900' : 'text-red-500']">
                    {{ item.type === 'earned' ? '+' : '-' }}{{ formatPrice(item.amount) }} P
                  </span>
                  <p class="text-xs text-gray-400 mt-1">잔액 {{ formatPrice(item.balance) }} P</p>
                </div>
              </div>
            </div>
            <div v-if="filteredHistory.length === 0" class="p-8 text-center text-gray-500">
              내역이 없습니다.
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div class="flex justify-center mt-6 gap-1">
          <button class="px-3 py-2 bg-white border rounded-lg hover:bg-gray-50">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
            </svg>
          </button>
          <button class="px-4 py-2 bg-teal-600 text-white rounded-lg">1</button>
          <button class="px-4 py-2 bg-white border rounded-lg hover:bg-gray-50">2</button>
          <button class="px-3 py-2 bg-white border rounded-lg hover:bg-gray-50">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
            </svg>
          </button>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import Layout from '../components/Layout.vue'

const activeTab = ref('all')
const totalPoints = ref(15000)
const expiringPoints = ref(2000)
const monthlyEarned = ref(3500)
const monthlyUsed = ref(5000)
const memberGrade = ref('골드')
const earnRate = ref(2)

const history = ref([
  { id: 1, type: 'earned', description: '상품 구매 적립', amount: 890, balance: 15000, date: '2025-01-20' },
  { id: 2, type: 'used', description: '상품 구매 시 사용', amount: 5000, balance: 14110, date: '2025-01-18' },
  { id: 3, type: 'earned', description: '리뷰 작성 적립', amount: 500, balance: 19110, date: '2025-01-17' },
  { id: 4, type: 'earned', description: '상품 구매 적립', amount: 2990, balance: 18610, date: '2025-01-15' },
  { id: 5, type: 'expired', description: '유효기간 만료 소멸', amount: 1000, balance: 15620, date: '2025-01-01' },
  { id: 6, type: 'earned', description: '이벤트 참여 적립', amount: 1000, balance: 16620, date: '2024-12-25' },
  { id: 7, type: 'used', description: '상품 구매 시 사용', amount: 3000, balance: 15620, date: '2024-12-20' }
])

const filteredHistory = computed(() => {
  if (activeTab.value === 'all') return history.value
  return history.value.filter(h => h.type === activeTab.value)
})

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function getStatusColor(type) {
  const colors = {
    earned: 'bg-teal-500',
    used: 'bg-gray-400',
    expired: 'bg-red-500'
  }
  return colors[type] || 'bg-gray-400'
}
</script>
