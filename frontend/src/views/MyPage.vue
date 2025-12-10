<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">마이페이지</h1>
          <p class="text-gray-600 mt-1">회원 정보 및 주문 내역을 확인하세요</p>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-4 gap-6">
          <!-- Sidebar -->
          <div class="lg:col-span-1">
            <div class="bg-white rounded-xl shadow-sm p-6">
              <!-- User Info -->
              <div class="text-center mb-6">
                <div class="w-20 h-20 rounded-full bg-teal-100 flex items-center justify-center mx-auto mb-3">
                  <span class="text-3xl font-bold text-teal-600">{{ authStore.user?.name?.[0] || 'U' }}</span>
                </div>
                <h3 class="font-bold text-gray-900">{{ authStore.user?.name }}</h3>
                <p class="text-sm text-gray-500">{{ authStore.user?.email }}</p>
                <span class="inline-block mt-2 px-3 py-1 bg-teal-100 text-teal-700 text-xs font-medium rounded-full">
                  {{ memberGrade }}
                </span>
              </div>

              <!-- Menu -->
              <nav class="space-y-1">
                <router-link to="/mypage" class="flex items-center px-4 py-2 text-sm font-medium rounded-lg bg-teal-50 text-teal-700">
                  <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                  </svg>
                  마이페이지 홈
                </router-link>
                <router-link to="/orders" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                  </svg>
                  주문/배송 조회
                </router-link>
                <router-link to="/wishlist" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                  </svg>
                  위시리스트
                </router-link>
                <router-link to="/my-reviews" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z" />
                  </svg>
                  내 리뷰
                </router-link>
                <router-link to="/coupons" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z" />
                  </svg>
                  쿠폰함
                </router-link>
                <router-link to="/points" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  포인트
                </router-link>
                <router-link to="/profile" class="flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 rounded-lg">
                  <svg class="w-5 h-5 mr-3 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  회원정보 수정
                </router-link>
              </nav>
            </div>
          </div>

          <!-- Main Content -->
          <div class="lg:col-span-3 space-y-6">
            <!-- Stats -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
              <div class="bg-white rounded-xl shadow-sm p-4 text-center">
                <div class="text-2xl font-bold text-teal-600">{{ stats.orderCount }}</div>
                <div class="text-sm text-gray-500">총 주문</div>
              </div>
              <div class="bg-white rounded-xl shadow-sm p-4 text-center">
                <div class="text-2xl font-bold text-teal-600">{{ formatPrice(stats.points) }}</div>
                <div class="text-sm text-gray-500">보유 포인트</div>
              </div>
              <div class="bg-white rounded-xl shadow-sm p-4 text-center">
                <div class="text-2xl font-bold text-teal-600">{{ stats.couponCount }}</div>
                <div class="text-sm text-gray-500">쿠폰</div>
              </div>
              <div class="bg-white rounded-xl shadow-sm p-4 text-center">
                <div class="text-2xl font-bold text-teal-600">{{ stats.reviewCount }}</div>
                <div class="text-sm text-gray-500">작성 리뷰</div>
              </div>
            </div>

            <!-- Recent Orders -->
            <div class="bg-white rounded-xl shadow-sm">
              <div class="p-4 border-b flex justify-between items-center">
                <h3 class="font-bold text-gray-900">최근 주문</h3>
                <router-link to="/orders" class="text-sm text-teal-600 hover:underline">전체보기</router-link>
              </div>
              <div class="divide-y">
                <div v-for="order in recentOrders" :key="order.id" class="p-4 hover:bg-gray-50">
                  <div class="flex justify-between items-start">
                    <div>
                      <div class="font-medium text-gray-900">{{ order.productName }}</div>
                      <div class="text-sm text-gray-500">{{ order.date }} | {{ order.quantity }}개</div>
                    </div>
                    <div class="text-right">
                      <div class="font-bold text-gray-900">{{ formatPrice(order.total) }}원</div>
                      <span :class="['text-xs px-2 py-1 rounded-full', getStatusClass(order.status)]">
                        {{ order.statusText }}
                      </span>
                    </div>
                  </div>
                </div>
                <div v-if="recentOrders.length === 0" class="p-8 text-center text-gray-500">
                  주문 내역이 없습니다.
                </div>
              </div>
            </div>

            <!-- Quick Actions -->
            <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
              <router-link to="/orders" class="bg-white rounded-xl shadow-sm p-4 hover:shadow-md transition-shadow text-center">
                <div class="w-12 h-12 rounded-full bg-blue-100 flex items-center justify-center mx-auto mb-2">
                  <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4" />
                  </svg>
                </div>
                <div class="text-sm font-medium text-gray-900">배송조회</div>
              </router-link>
              <router-link to="/inquiry" class="bg-white rounded-xl shadow-sm p-4 hover:shadow-md transition-shadow text-center">
                <div class="w-12 h-12 rounded-full bg-green-100 flex items-center justify-center mx-auto mb-2">
                  <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
                  </svg>
                </div>
                <div class="text-sm font-medium text-gray-900">1:1 문의</div>
              </router-link>
              <router-link to="/faq" class="bg-white rounded-xl shadow-sm p-4 hover:shadow-md transition-shadow text-center">
                <div class="w-12 h-12 rounded-full bg-yellow-100 flex items-center justify-center mx-auto mb-2">
                  <svg class="w-6 h-6 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                </div>
                <div class="text-sm font-medium text-gray-900">자주묻는질문</div>
              </router-link>
              <router-link to="/notice" class="bg-white rounded-xl shadow-sm p-4 hover:shadow-md transition-shadow text-center">
                <div class="w-12 h-12 rounded-full bg-purple-100 flex items-center justify-center mx-auto mb-2">
                  <svg class="w-6 h-6 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5.882V19.24a1.76 1.76 0 01-3.417.592l-2.147-6.15M18 13a3 3 0 100-6M5.436 13.683A4.001 4.001 0 017 6h1.832c4.1 0 7.625-1.234 9.168-3v14c-1.543-1.766-5.067-3-9.168-3H7a3.988 3.988 0 01-1.564-.317z" />
                  </svg>
                </div>
                <div class="text-sm font-medium text-gray-900">공지사항</div>
              </router-link>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useAuthStore } from '../stores/auth'
import Layout from '../components/Layout.vue'

const authStore = useAuthStore()

const memberGrade = computed(() => {
  const grades = ['브론즈', '실버', '골드', 'VIP', 'VVIP']
  return grades[Math.floor(Math.random() * 3)]
})

const stats = ref({
  orderCount: 12,
  points: 15000,
  couponCount: 3,
  reviewCount: 8
})

const recentOrders = ref([
  { id: 1, productName: '프리미엄 무선 이어폰', date: '2025-01-15', quantity: 1, total: 89000, status: 'delivered', statusText: '배송완료' },
  { id: 2, productName: '스마트 워치 프로', date: '2025-01-12', quantity: 1, total: 299000, status: 'shipping', statusText: '배송중' },
  { id: 3, productName: '노트북 파우치 15인치', date: '2025-01-10', quantity: 2, total: 45000, status: 'preparing', statusText: '상품준비중' }
])

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function getStatusClass(status) {
  const classes = {
    delivered: 'bg-green-100 text-green-700',
    shipping: 'bg-blue-100 text-blue-700',
    preparing: 'bg-yellow-100 text-yellow-700',
    cancelled: 'bg-red-100 text-red-700'
  }
  return classes[status] || 'bg-gray-100 text-gray-700'
}
</script>
