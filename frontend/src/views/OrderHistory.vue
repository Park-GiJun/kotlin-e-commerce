<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">주문 내역</h1>
        <p class="mt-2 text-gray-600">총 {{ orders.length }}개의 주문</p>
      </div>

      <!-- Filter Tabs -->
      <div class="mb-6 border-b border-gray-200">
        <nav class="flex space-x-8">
          <button
            v-for="tab in tabs"
            :key="tab.value"
            @click="selectedTab = tab.value"
            class="py-4 px-1 border-b-2 font-medium text-sm transition-colors"
            :class="selectedTab === tab.value
              ? 'border-primary-600 text-primary-600'
              : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'">
            {{ tab.label }}
            <span class="ml-2 py-0.5 px-2 rounded-full text-xs"
              :class="selectedTab === tab.value ? 'bg-primary-100 text-primary-600' : 'bg-gray-100 text-gray-600'">
              {{ getOrderCountByStatus(tab.value) }}
            </span>
          </button>
        </nav>
      </div>

      <!-- Empty State -->
      <div v-if="filteredOrders.length === 0" class="text-center py-16">
        <svg class="mx-auto h-24 w-24 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
        </svg>
        <h3 class="mt-4 text-lg font-medium text-gray-900">주문 내역이 없습니다</h3>
        <p class="mt-2 text-gray-600">첫 주문을 시작해보세요</p>
        <router-link to="/products" class="mt-6 inline-flex items-center px-6 py-3 border border-transparent text-base font-medium rounded-lg text-white bg-primary-600 hover:bg-primary-700">
          상품 둘러보기
        </router-link>
      </div>

      <!-- Order List -->
      <div v-else class="space-y-4">
        <div v-for="order in filteredOrders" :key="order.id" class="bg-white rounded-xl shadow-md overflow-hidden hover:shadow-lg transition-shadow">
          <!-- Order Header -->
          <div class="bg-gray-50 px-6 py-4 border-b border-gray-200">
            <div class="flex items-center justify-between">
              <div class="flex items-center space-x-4">
                <span class="text-sm text-gray-600">주문번호: {{ order.orderNumber }}</span>
                <span class="text-sm text-gray-600">{{ formatDate(order.createdAt) }}</span>
              </div>
              <div>
                <span class="inline-flex px-3 py-1 rounded-full text-sm font-medium"
                  :class="getStatusClass(order.status)">
                  {{ getStatusLabel(order.status) }}
                </span>
              </div>
            </div>
          </div>

          <!-- Order Items -->
          <div class="p-6">
            <div class="space-y-4">
              <div v-for="(item, idx) in order.items" :key="idx" class="flex items-center">
                <div class="w-20 h-20 bg-gray-100 rounded-lg flex-shrink-0">
                  <div class="w-full h-full flex items-center justify-center text-gray-400">
                    <svg class="w-10 h-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                  </div>
                </div>
                <div class="ml-4 flex-1">
                  <h3 class="text-sm font-medium text-gray-900">{{ item.name }}</h3>
                  <p class="text-sm text-gray-500">{{ item.price.toLocaleString('ko-KR') }}원 / {{ item.quantity }}개</p>
                </div>
                <div class="text-right">
                  <p class="text-sm font-medium text-gray-900">{{ (item.price * item.quantity).toLocaleString('ko-KR') }}원</p>
                </div>
              </div>
            </div>

            <!-- Order Footer -->
            <div class="mt-6 pt-6 border-t border-gray-200 flex items-center justify-between">
              <div class="text-sm text-gray-600">
                총 {{ order.items.reduce((sum, item) => sum + item.quantity, 0) }}개
                <span class="mx-2">|</span>
                결제 금액: <span class="font-bold text-gray-900">{{ order.payment.amount.toLocaleString('ko-KR') }}원</span>
              </div>
              <div class="flex space-x-3">
                <router-link
                  :to="`/orders/${order.id}`"
                  class="px-4 py-2 border border-gray-300 rounded-lg text-sm font-medium text-gray-700 hover:bg-gray-50">
                  상세보기
                </router-link>
                <button
                  v-if="order.status === 'delivered'"
                  @click="writeReview(order.id)"
                  class="px-4 py-2 bg-primary-600 text-white rounded-lg text-sm font-medium hover:bg-primary-700">
                  리뷰 작성
                </button>
                <button
                  v-if="order.status === 'pending' || order.status === 'processing'"
                  @click="cancelOrder(order.id)"
                  class="px-4 py-2 border border-red-300 text-red-600 rounded-lg text-sm font-medium hover:bg-red-50">
                  주문 취소
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { faker } from '@faker-js/faker/locale/ko'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, info, confirm } = useAlert()

const router = useRouter()

const selectedTab = ref('all')
const orders = ref([])

const tabs = [
  { label: '전체', value: 'all' },
  { label: '결제 대기', value: 'pending' },
  { label: '배송 준비중', value: 'processing' },
  { label: '배송중', value: 'shipping' },
  { label: '배송 완료', value: 'delivered' },
  { label: '취소/반품', value: 'cancelled' }
]

const statusLabels = {
  pending: '결제 대기',
  processing: '배송 준비중',
  shipping: '배송중',
  delivered: '배송 완료',
  cancelled: '취소',
  refunded: '반품 완료'
}

const filteredOrders = computed(() => {
  if (selectedTab.value === 'all') {
    return orders.value
  }
  return orders.value.filter(order => order.status === selectedTab.value)
})

function getOrderCountByStatus(status) {
  if (status === 'all') {
    return orders.value.length
  }
  return orders.value.filter(order => order.status === status).length
}

function getStatusLabel(status) {
  return statusLabels[status] || status
}

function getStatusClass(status) {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-800',
    processing: 'bg-blue-100 text-blue-800',
    shipping: 'bg-purple-100 text-purple-800',
    delivered: 'bg-green-100 text-green-800',
    cancelled: 'bg-red-100 text-red-800',
    refunded: 'bg-gray-100 text-gray-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

function formatDate(dateString) {
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

function writeReview(orderId) {
  info(`주문 ${orderId}의 리뷰 작성 페이지로 이동합니다.`)
}

async function cancelOrder(orderId) {
  const confirmed = await confirm('주문을 취소하시겠습니까?', '주문 취소')
  if (confirmed) {
    const order = orders.value.find(o => o.id === orderId)
    if (order) {
      order.status = 'cancelled'
      success('주문이 취소되었습니다.')
    }
  }
}

// Generate dummy orders
function generateDummyOrders() {
  // Load real orders from localStorage first
  const realOrders = JSON.parse(localStorage.getItem('orders') || '[]')

  // Generate some dummy orders for demonstration
  const statuses = ['pending', 'processing', 'shipping', 'delivered', 'cancelled']
  const dummyOrders = Array.from({ length: 10 }, (_, i) => {
    const itemCount = faker.number.int({ min: 1, max: 5 })
    const items = Array.from({ length: itemCount }, () => ({
      id: faker.number.int({ min: 1, max: 100 }),
      name: faker.commerce.productName(),
      price: faker.number.int({ min: 10000, max: 500000 }),
      quantity: faker.number.int({ min: 1, max: 3 })
    }))

    const totalAmount = items.reduce((sum, item) => sum + (item.price * item.quantity), 0)

    return {
      id: Date.now() - i * 100000,
      orderNumber: `ORD${Date.now() - i * 100000}`,
      items,
      receiver: {
        name: faker.person.fullName(),
        phone: faker.phone.number('010-####-####'),
        address: faker.location.streetAddress(true),
        zipCode: faker.location.zipCode('#####')
      },
      payment: {
        method: faker.helpers.arrayElement(['card', 'transfer', 'virtual', 'phone']),
        amount: totalAmount + (totalAmount >= 50000 ? 0 : 3000)
      },
      status: statuses[i % statuses.length],
      createdAt: faker.date.recent({ days: 90 }).toISOString()
    }
  })

  // Combine real and dummy orders, sort by date
  orders.value = [...realOrders, ...dummyOrders].sort((a, b) =>
    new Date(b.createdAt) - new Date(a.createdAt)
  )
}

onMounted(() => {
  generateDummyOrders()
})
</script>
