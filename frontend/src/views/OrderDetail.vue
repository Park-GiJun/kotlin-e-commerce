<template>
  <Layout>
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Back Button -->
      <button @click="router.back()" class="mb-6 flex items-center text-gray-600 hover:text-gray-900">
        <svg class="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
        </svg>
        목록으로 돌아가기
      </button>

      <div v-if="!order" class="text-center py-16">
        <p class="text-gray-600">주문을 찾을 수 없습니다.</p>
      </div>

      <div v-else class="space-y-6">
        <!-- Order Header -->
        <div class="bg-white rounded-xl shadow-md p-6">
          <div class="flex items-start justify-between">
            <div>
              <h1 class="text-2xl font-bold text-gray-900">주문 상세</h1>
              <p class="mt-2 text-sm text-gray-600">주문번호: {{ order.orderNumber }}</p>
              <p class="text-sm text-gray-600">주문일자: {{ formatDate(order.createdAt) }}</p>
            </div>
            <span class="inline-flex px-4 py-2 rounded-full text-sm font-medium"
              :class="getStatusClass(order.status)">
              {{ getStatusLabel(order.status) }}
            </span>
          </div>
        </div>

        <!-- Delivery Tracking -->
        <div class="bg-white rounded-xl shadow-md p-6">
          <h2 class="text-lg font-bold text-gray-900 mb-6">배송 추적</h2>
          <div class="relative">
            <div class="absolute left-4 top-0 bottom-0 w-0.5 bg-gray-200"></div>
            <div class="space-y-6">
              <div v-for="(step, idx) in deliverySteps" :key="idx" class="relative flex items-start">
                <div class="flex-shrink-0">
                  <div class="w-8 h-8 rounded-full flex items-center justify-center"
                    :class="step.completed ? 'bg-primary-600' : 'bg-gray-300'">
                    <svg v-if="step.completed" class="w-5 h-5 text-white" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clip-rule="evenodd" />
                    </svg>
                  </div>
                </div>
                <div class="ml-4 flex-1">
                  <p class="text-sm font-medium" :class="step.completed ? 'text-gray-900' : 'text-gray-500'">
                    {{ step.title }}
                  </p>
                  <p v-if="step.date" class="mt-1 text-sm text-gray-500">{{ step.date }}</p>
                  <p v-if="step.description" class="mt-1 text-sm text-gray-600">{{ step.description }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Items -->
        <div class="bg-white rounded-xl shadow-md p-6">
          <h2 class="text-lg font-bold text-gray-900 mb-4">주문 상품</h2>
          <div class="space-y-4">
            <div v-for="(item, idx) in order.items" :key="idx" class="flex items-center pb-4"
              :class="{ 'border-b border-gray-200': idx < order.items.length - 1 }">
              <div class="w-20 h-20 bg-gray-100 rounded-lg flex-shrink-0">
                <div class="w-full h-full flex items-center justify-center text-gray-400">
                  <svg class="w-10 h-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>
              </div>
              <div class="ml-4 flex-1">
                <h3 class="text-sm font-medium text-gray-900">{{ item.name }}</h3>
                <p class="mt-1 text-sm text-gray-500">{{ item.price.toLocaleString('ko-KR') }}원 × {{ item.quantity }}개</p>
              </div>
              <div class="text-right">
                <p class="text-sm font-bold text-gray-900">{{ (item.price * item.quantity).toLocaleString('ko-KR') }}원</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Delivery Info -->
        <div class="bg-white rounded-xl shadow-md p-6">
          <h2 class="text-lg font-bold text-gray-900 mb-4">배송 정보</h2>
          <div class="space-y-3">
            <div class="flex">
              <span class="w-24 text-sm text-gray-600">받는 사람</span>
              <span class="text-sm text-gray-900">{{ order.receiver.name }}</span>
            </div>
            <div class="flex">
              <span class="w-24 text-sm text-gray-600">연락처</span>
              <span class="text-sm text-gray-900">{{ order.receiver.phone }}</span>
            </div>
            <div class="flex">
              <span class="w-24 text-sm text-gray-600">배송 주소</span>
              <span class="text-sm text-gray-900">
                [{{ order.receiver.zipCode }}] {{ order.receiver.address }}
              </span>
            </div>
            <div v-if="order.deliveryRequest" class="flex">
              <span class="w-24 text-sm text-gray-600">배송 요청</span>
              <span class="text-sm text-gray-900">{{ order.deliveryRequest }}</span>
            </div>
          </div>
        </div>

        <!-- Payment Info -->
        <div class="bg-white rounded-xl shadow-md p-6">
          <h2 class="text-lg font-bold text-gray-900 mb-4">결제 정보</h2>
          <div class="space-y-3">
            <div class="flex justify-between text-gray-600">
              <span>상품 금액</span>
              <span>{{ calculateProductTotal().toLocaleString('ko-KR') }}원</span>
            </div>
            <div class="flex justify-between text-gray-600">
              <span>배송비</span>
              <span>{{ calculateShippingFee().toLocaleString('ko-KR') }}원</span>
            </div>
            <div class="pt-3 border-t border-gray-200">
              <div class="flex justify-between text-lg font-bold text-gray-900">
                <span>최종 결제 금액</span>
                <span class="text-primary-600">{{ order.payment.amount.toLocaleString('ko-KR') }}원</span>
              </div>
            </div>
            <div class="pt-3 border-t border-gray-200">
              <div class="flex justify-between text-sm text-gray-600">
                <span>결제 방법</span>
                <span>{{ getPaymentMethodLabel(order.payment.method) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Actions -->
        <div class="flex space-x-3">
          <button
            v-if="order.status === 'pending' || order.status === 'processing'"
            @click="cancelOrder"
            class="flex-1 px-6 py-3 border border-red-300 text-red-600 rounded-lg font-medium hover:bg-red-50">
            주문 취소
          </button>
          <button
            v-if="order.status === 'delivered'"
            @click="writeReview"
            class="flex-1 px-6 py-3 bg-primary-600 text-white rounded-lg font-medium hover:bg-primary-700">
            리뷰 작성
          </button>
          <button
            @click="contactSupport"
            class="flex-1 px-6 py-3 border border-gray-300 text-gray-700 rounded-lg font-medium hover:bg-gray-50">
            고객센터 문의
          </button>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { faker } from '@faker-js/faker/locale/ko'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, info, confirm } = useAlert()

const router = useRouter()
const route = useRoute()

const order = ref(null)

const statusLabels = {
  pending: '결제 대기',
  processing: '배송 준비중',
  shipping: '배송중',
  delivered: '배송 완료',
  cancelled: '취소',
  refunded: '반품 완료'
}

const paymentMethodLabels = {
  card: '신용/체크카드',
  transfer: '실시간 계좌이체',
  virtual: '가상계좌',
  phone: '휴대폰 결제'
}

const deliverySteps = computed(() => {
  if (!order.value) return []

  const steps = [
    {
      title: '주문 접수',
      date: formatDate(order.value.createdAt),
      description: '주문이 정상적으로 접수되었습니다.',
      completed: true
    },
    {
      title: '결제 완료',
      date: order.value.status !== 'pending' ? formatDate(order.value.createdAt) : null,
      description: '결제가 완료되었습니다.',
      completed: order.value.status !== 'pending'
    },
    {
      title: '상품 준비중',
      date: order.value.status === 'processing' || order.value.status === 'shipping' || order.value.status === 'delivered' ? faker.date.recent({ days: 2 }).toLocaleDateString('ko-KR') : null,
      description: '판매자가 상품을 준비하고 있습니다.',
      completed: order.value.status === 'processing' || order.value.status === 'shipping' || order.value.status === 'delivered'
    },
    {
      title: '배송 시작',
      date: order.value.status === 'shipping' || order.value.status === 'delivered' ? faker.date.recent({ days: 1 }).toLocaleDateString('ko-KR') : null,
      description: order.value.status === 'shipping' || order.value.status === 'delivered' ? `택배사: CJ대한통운 (송장번호: ${faker.string.numeric(12)})` : null,
      completed: order.value.status === 'shipping' || order.value.status === 'delivered'
    },
    {
      title: '배송 완료',
      date: order.value.status === 'delivered' ? faker.date.recent().toLocaleDateString('ko-KR') : null,
      description: order.value.status === 'delivered' ? '상품이 배송 완료되었습니다.' : null,
      completed: order.value.status === 'delivered'
    }
  ]

  return steps
})

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

function getPaymentMethodLabel(method) {
  return paymentMethodLabels[method] || method
}

function formatDate(dateString) {
  const date = new Date(dateString)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

function calculateProductTotal() {
  return order.value.items.reduce((sum, item) => sum + (item.price * item.quantity), 0)
}

function calculateShippingFee() {
  const productTotal = calculateProductTotal()
  return productTotal >= 50000 ? 0 : 3000
}

async function cancelOrder() {
  const confirmed = await confirm('주문을 취소하시겠습니까?', '주문 취소')
  if (confirmed) {
    order.value.status = 'cancelled'
    success('주문이 취소되었습니다.')
    router.push('/orders')
  }
}

function writeReview() {
  info('리뷰 작성 페이지로 이동합니다.')
}

function contactSupport() {
  info('고객센터(1234-5678)로 문의 부탁드립니다.')
}

function loadOrder() {
  const orderId = parseInt(route.params.id)

  // Load from localStorage first
  const orders = JSON.parse(localStorage.getItem('orders') || '[]')
  let foundOrder = orders.find(o => o.id === orderId)

  // If not found, generate a dummy order
  if (!foundOrder) {
    const itemCount = faker.number.int({ min: 1, max: 5 })
    const items = Array.from({ length: itemCount }, () => ({
      id: faker.number.int({ min: 1, max: 100 }),
      name: faker.commerce.productName(),
      price: faker.number.int({ min: 10000, max: 500000 }),
      quantity: faker.number.int({ min: 1, max: 3 })
    }))

    const totalAmount = items.reduce((sum, item) => sum + (item.price * item.quantity), 0)

    foundOrder = {
      id: orderId,
      orderNumber: `ORD${orderId}`,
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
      deliveryRequest: faker.helpers.arrayElement(['문 앞에 놓아주세요', '경비실에 맡겨주세요', '배송 전 연락주세요', '']),
      status: faker.helpers.arrayElement(['pending', 'processing', 'shipping', 'delivered']),
      createdAt: faker.date.recent({ days: 30 }).toISOString()
    }
  }

  order.value = foundOrder
}

onMounted(() => {
  loadOrder()
})
</script>
