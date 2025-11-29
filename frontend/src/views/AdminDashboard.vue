<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">관리자 대시보드</h1>
          <p class="text-gray-600 mt-1">{{ today }} 기준</p>
        </div>

        <!-- Stats Cards -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">오늘 매출</p>
                <p class="text-2xl font-bold text-gray-900 mt-1">{{ formatPrice(stats.todaySales) }}원</p>
                <p :class="['text-sm mt-1', stats.salesGrowth >= 0 ? 'text-green-600' : 'text-red-600']">
                  {{ stats.salesGrowth >= 0 ? '+' : '' }}{{ stats.salesGrowth }}% 전일 대비
                </p>
              </div>
              <div class="w-12 h-12 rounded-full bg-teal-100 flex items-center justify-center">
                <svg class="w-6 h-6 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">오늘 주문</p>
                <p class="text-2xl font-bold text-gray-900 mt-1">{{ stats.todayOrders }}건</p>
                <p class="text-sm text-gray-500 mt-1">처리 대기 {{ stats.pendingOrders }}건</p>
              </div>
              <div class="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center">
                <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">신규 회원</p>
                <p class="text-2xl font-bold text-gray-900 mt-1">{{ stats.newMembers }}명</p>
                <p class="text-sm text-gray-500 mt-1">총 {{ formatPrice(stats.totalMembers) }}명</p>
              </div>
              <div class="w-12 h-12 rounded-full bg-purple-100 flex items-center justify-center">
                <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">미답변 문의</p>
                <p class="text-2xl font-bold text-gray-900 mt-1">{{ stats.pendingInquiries }}건</p>
                <p class="text-sm text-gray-500 mt-1">미처리 리뷰 {{ stats.pendingReviews }}건</p>
              </div>
              <div class="w-12 h-12 rounded-full bg-yellow-100 flex items-center justify-center">
                <svg class="w-6 h-6 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- Chart Area -->
          <div class="lg:col-span-2 space-y-6">
            <!-- Sales Chart -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="flex justify-between items-center mb-6">
                <h3 class="font-bold text-gray-900">매출 현황</h3>
                <div class="flex gap-2">
                  <button
                    v-for="period in ['일별', '주별', '월별']"
                    :key="period"
                    :class="['px-3 py-1 text-sm rounded-lg', activePeriod === period ? 'bg-teal-600 text-white' : 'bg-gray-100 text-gray-600 hover:bg-gray-200']"
                    @click="activePeriod = period"
                  >
                    {{ period }}
                  </button>
                </div>
              </div>
              <!-- Simple Chart Visualization -->
              <div class="h-64 flex items-end justify-between gap-2">
                <div v-for="(value, index) in chartData" :key="index" class="flex-1 flex flex-col items-center">
                  <div
                    :style="{ height: (value / maxChartValue * 100) + '%' }"
                    class="w-full bg-teal-500 rounded-t-lg hover:bg-teal-600 transition-colors cursor-pointer relative group"
                  >
                    <div class="absolute -top-8 left-1/2 -translate-x-1/2 bg-gray-800 text-white text-xs px-2 py-1 rounded opacity-0 group-hover:opacity-100 transition-opacity whitespace-nowrap">
                      {{ formatPrice(value) }}원
                    </div>
                  </div>
                  <span class="text-xs text-gray-500 mt-2">{{ chartLabels[index] }}</span>
                </div>
              </div>
            </div>

            <!-- Recent Orders -->
            <div class="bg-white rounded-xl shadow-sm">
              <div class="p-4 border-b flex justify-between items-center">
                <h3 class="font-bold text-gray-900">최근 주문</h3>
                <router-link to="/admin/orders" class="text-sm text-teal-600 hover:underline">전체보기</router-link>
              </div>
              <div class="overflow-x-auto">
                <table class="w-full">
                  <thead class="bg-gray-50 text-sm">
                    <tr>
                      <th class="px-4 py-3 text-left font-medium text-gray-500">주문번호</th>
                      <th class="px-4 py-3 text-left font-medium text-gray-500">고객</th>
                      <th class="px-4 py-3 text-left font-medium text-gray-500">상품</th>
                      <th class="px-4 py-3 text-right font-medium text-gray-500">금액</th>
                      <th class="px-4 py-3 text-center font-medium text-gray-500">상태</th>
                    </tr>
                  </thead>
                  <tbody class="divide-y">
                    <tr v-for="order in recentOrders" :key="order.id" class="hover:bg-gray-50">
                      <td class="px-4 py-3 text-sm font-medium text-gray-900">{{ order.orderNumber }}</td>
                      <td class="px-4 py-3 text-sm text-gray-600">{{ order.customer }}</td>
                      <td class="px-4 py-3 text-sm text-gray-600">{{ order.product }}</td>
                      <td class="px-4 py-3 text-sm text-gray-900 text-right">{{ formatPrice(order.amount) }}원</td>
                      <td class="px-4 py-3 text-center">
                        <span :class="['px-2 py-1 text-xs font-medium rounded-full', getStatusClass(order.status)]">
                          {{ order.statusText }}
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <!-- Side Panel -->
          <div class="space-y-6">
            <!-- Top Products -->
            <div class="bg-white rounded-xl shadow-sm">
              <div class="p-4 border-b">
                <h3 class="font-bold text-gray-900">인기 상품 TOP 5</h3>
              </div>
              <div class="divide-y">
                <div v-for="(product, index) in topProducts" :key="product.id" class="p-4 flex items-center gap-3">
                  <span :class="['w-6 h-6 rounded-full flex items-center justify-center text-xs font-bold', index < 3 ? 'bg-teal-600 text-white' : 'bg-gray-200 text-gray-600']">
                    {{ index + 1 }}
                  </span>
                  <div class="flex-1 min-w-0">
                    <p class="text-sm font-medium text-gray-900 truncate">{{ product.name }}</p>
                    <p class="text-xs text-gray-500">{{ product.sales }}개 판매</p>
                  </div>
                  <span class="text-sm font-medium text-gray-900">{{ formatPrice(product.revenue) }}원</span>
                </div>
              </div>
            </div>

            <!-- Quick Links -->
            <div class="bg-white rounded-xl shadow-sm p-4">
              <h3 class="font-bold text-gray-900 mb-4">빠른 메뉴</h3>
              <div class="grid grid-cols-2 gap-2">
                <router-link to="/admin/orders" class="p-3 bg-gray-50 rounded-lg hover:bg-gray-100 text-center">
                  <svg class="w-6 h-6 mx-auto text-gray-600 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                  </svg>
                  <span class="text-xs text-gray-700">주문관리</span>
                </router-link>
                <router-link to="/admin/products" class="p-3 bg-gray-50 rounded-lg hover:bg-gray-100 text-center">
                  <svg class="w-6 h-6 mx-auto text-gray-600 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                  <span class="text-xs text-gray-700">상품관리</span>
                </router-link>
                <router-link to="/admin/reviews" class="p-3 bg-gray-50 rounded-lg hover:bg-gray-100 text-center">
                  <svg class="w-6 h-6 mx-auto text-gray-600 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z" />
                  </svg>
                  <span class="text-xs text-gray-700">리뷰관리</span>
                </router-link>
                <router-link to="/admin/inquiries" class="p-3 bg-gray-50 rounded-lg hover:bg-gray-100 text-center">
                  <svg class="w-6 h-6 mx-auto text-gray-600 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
                  </svg>
                  <span class="text-xs text-gray-700">문의관리</span>
                </router-link>
              </div>
            </div>

            <!-- Alerts -->
            <div class="bg-white rounded-xl shadow-sm p-4">
              <h3 class="font-bold text-gray-900 mb-4">알림</h3>
              <div class="space-y-3">
                <div class="flex items-start gap-3 p-3 bg-red-50 rounded-lg">
                  <svg class="w-5 h-5 text-red-600 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                  </svg>
                  <div>
                    <p class="text-sm font-medium text-red-800">재고 부족 상품 3개</p>
                    <p class="text-xs text-red-600">확인이 필요합니다</p>
                  </div>
                </div>
                <div class="flex items-start gap-3 p-3 bg-yellow-50 rounded-lg">
                  <svg class="w-5 h-5 text-yellow-600 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  <div>
                    <p class="text-sm font-medium text-yellow-800">답변 대기 문의 5건</p>
                    <p class="text-xs text-yellow-600">24시간 이내 답변 필요</p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import Layout from '../components/Layout.vue'

const today = new Date().toLocaleDateString('ko-KR', { year: 'numeric', month: 'long', day: 'numeric' })
const activePeriod = ref('일별')

const stats = ref({
  todaySales: 2450000,
  salesGrowth: 12.5,
  todayOrders: 45,
  pendingOrders: 12,
  newMembers: 23,
  totalMembers: 15234,
  pendingInquiries: 5,
  pendingReviews: 8
})

const chartData = ref([1200000, 1800000, 1500000, 2100000, 1900000, 2450000, 2200000])
const chartLabels = ref(['1/24', '1/25', '1/26', '1/27', '1/28', '1/29', '1/30'])
const maxChartValue = computed(() => Math.max(...chartData.value) * 1.1)

const recentOrders = ref([
  { id: 1, orderNumber: '20250130-001', customer: '김민수', product: '프리미엄 무선 이어폰 외 1건', amount: 125000, status: 'pending', statusText: '결제완료' },
  { id: 2, orderNumber: '20250130-002', customer: '이영희', product: '스마트 워치 프로', amount: 299000, status: 'preparing', statusText: '상품준비' },
  { id: 3, orderNumber: '20250130-003', customer: '박철수', product: '노트북 파우치', amount: 45000, status: 'shipping', statusText: '배송중' },
  { id: 4, orderNumber: '20250130-004', customer: '최지현', product: '블루투스 스피커', amount: 89000, status: 'delivered', statusText: '배송완료' },
  { id: 5, orderNumber: '20250130-005', customer: '정현우', product: '무선 충전기', amount: 35000, status: 'pending', statusText: '결제완료' }
])

const topProducts = ref([
  { id: 1, name: '프리미엄 무선 이어폰', sales: 156, revenue: 13884000 },
  { id: 2, name: '스마트 워치 프로', sales: 89, revenue: 26611000 },
  { id: 3, name: '블루투스 스피커', sales: 75, revenue: 6675000 },
  { id: 4, name: '무선 충전기', sales: 68, revenue: 2380000 },
  { id: 5, name: '노트북 파우치', sales: 52, revenue: 2340000 }
])

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function getStatusClass(status) {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-700',
    preparing: 'bg-blue-100 text-blue-700',
    shipping: 'bg-purple-100 text-purple-700',
    delivered: 'bg-green-100 text-green-700',
    cancelled: 'bg-red-100 text-red-700'
  }
  return classes[status] || 'bg-gray-100 text-gray-700'
}
</script>
