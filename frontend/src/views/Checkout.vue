<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">주문/결제</h1>
        <div class="mt-4 flex items-center space-x-4">
          <div class="flex items-center">
            <span class="w-8 h-8 flex items-center justify-center rounded-full bg-primary-600 text-white font-bold">1</span>
            <span class="ml-2 text-primary-600 font-medium">장바구니</span>
          </div>
          <svg class="w-5 h-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
          </svg>
          <div class="flex items-center">
            <span class="w-8 h-8 flex items-center justify-center rounded-full bg-primary-600 text-white font-bold">2</span>
            <span class="ml-2 text-primary-600 font-medium">주문/결제</span>
          </div>
          <svg class="w-5 h-5 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
          </svg>
          <div class="flex items-center">
            <span class="w-8 h-8 flex items-center justify-center rounded-full bg-gray-300 text-gray-600 font-bold">3</span>
            <span class="ml-2 text-gray-400">완료</span>
          </div>
        </div>
      </div>

      <div class="lg:grid lg:grid-cols-12 lg:gap-8">
        <!-- Main Form -->
        <div class="lg:col-span-8 space-y-6">
          <!-- Delivery Information -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-4">배송 정보</h2>
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">받는 사람</label>
                <input v-model="form.receiverName" type="text" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">연락처</label>
                <input v-model="form.receiverPhone" type="tel" placeholder="010-0000-0000" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">주소</label>
                <div class="space-y-2">
                  <div class="flex space-x-2">
                    <input v-model="form.zipCode" type="text" placeholder="우편번호" class="w-32 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                    <button @click="searchAddress" class="px-4 py-2 bg-gray-600 text-white rounded-lg hover:bg-gray-700">주소 검색</button>
                  </div>
                  <input v-model="form.address" type="text" placeholder="기본 주소" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                  <input v-model="form.addressDetail" type="text" placeholder="상세 주소" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                </div>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">배송 요청사항</label>
                <select v-model="form.deliveryRequest" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                  <option value="">선택해주세요</option>
                  <option value="door">문 앞에 놓아주세요</option>
                  <option value="security">경비실에 맡겨주세요</option>
                  <option value="call">배송 전 연락주세요</option>
                  <option value="direct">직접 받겠습니다</option>
                  <option value="custom">직접 입력</option>
                </select>
                <input v-if="form.deliveryRequest === 'custom'" v-model="form.customRequest" type="text" placeholder="요청사항을 입력하세요" class="mt-2 w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
            </div>
          </div>

          <!-- Payment Method -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-4">결제 방법</h2>
            <div class="space-y-3">
              <label v-for="method in paymentMethods" :key="method.value" class="flex items-center p-4 border rounded-lg cursor-pointer hover:border-primary-500" :class="{ 'border-primary-600 bg-primary-50': form.paymentMethod === method.value }">
                <input v-model="form.paymentMethod" type="radio" :value="method.value" class="w-4 h-4 text-primary-600">
                <div class="ml-3 flex-1">
                  <div class="flex items-center">
                    <svg class="w-6 h-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="method.icon" />
                    </svg>
                    <span class="ml-2 font-medium text-gray-900">{{ method.label }}</span>
                  </div>
                  <p class="mt-1 text-sm text-gray-500">{{ method.description }}</p>
                </div>
              </label>
            </div>

            <!-- Credit Card Form (shown when card payment selected) -->
            <div v-if="form.paymentMethod === 'card'" class="mt-4 pt-4 border-t space-y-3">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">카드 번호</label>
                <input v-model="form.cardNumber" type="text" placeholder="0000-0000-0000-0000" maxlength="19" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
              </div>
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">유효기간</label>
                  <input v-model="form.cardExpiry" type="text" placeholder="MM/YY" maxlength="5" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                </div>
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">CVC</label>
                  <input v-model="form.cardCvc" type="text" placeholder="000" maxlength="3" class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500">
                </div>
              </div>
            </div>
          </div>

          <!-- Order Items -->
          <div class="bg-white rounded-xl shadow-md p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-4">주문 상품 ({{ cartStore.itemCount }}개)</h2>
            <div class="space-y-4">
              <div v-for="item in cartStore.items" :key="item.id" class="flex items-center">
                <div class="w-16 h-16 bg-gray-100 rounded-lg flex-shrink-0">
                  <div class="w-full h-full flex items-center justify-center text-gray-400">
                    <svg class="w-8 h-8" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                  </div>
                </div>
                <div class="ml-4 flex-1">
                  <h3 class="text-sm font-medium text-gray-900">{{ item.name }}</h3>
                  <p class="text-sm text-gray-500">수량: {{ item.quantity }}개</p>
                </div>
                <div class="text-right">
                  <p class="text-sm font-medium text-gray-900">{{ (item.price * item.quantity).toLocaleString('ko-KR') }}원</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Order Summary -->
        <div class="mt-6 lg:mt-0 lg:col-span-4">
          <div class="bg-white rounded-xl shadow-md p-6 sticky top-4">
            <h2 class="text-lg font-bold text-gray-900 mb-4">결제 금액</h2>

            <div class="space-y-3">
              <div class="flex justify-between text-gray-600">
                <span>상품 금액</span>
                <span>{{ (cartStore.totalPrice + cartStore.totalDiscount).toLocaleString('ko-KR') }}원</span>
              </div>
              <div v-if="cartStore.totalDiscount > 0" class="flex justify-between text-red-600">
                <span>할인 금액</span>
                <span>-{{ cartStore.totalDiscount.toLocaleString('ko-KR') }}원</span>
              </div>
              <div class="flex justify-between text-gray-600">
                <span>배송비</span>
                <span>{{ shippingFee.toLocaleString('ko-KR') }}원</span>
              </div>

              <div class="border-t border-gray-200 pt-3">
                <div class="flex justify-between text-xl font-bold text-gray-900">
                  <span>최종 결제 금액</span>
                  <span class="text-primary-600">{{ (cartStore.totalPrice + shippingFee).toLocaleString('ko-KR') }}원</span>
                </div>
              </div>
            </div>

            <div class="mt-6 p-4 bg-gray-50 rounded-lg">
              <label class="flex items-center">
                <input v-model="agreeToTerms" type="checkbox" class="w-4 h-4 text-primary-600 rounded">
                <span class="ml-2 text-sm text-gray-700">주문 내용을 확인하였으며, 결제에 동의합니다.</span>
              </label>
            </div>

            <button
              @click="processOrder"
              :disabled="!canProcessOrder"
              class="w-full mt-4 px-6 py-3 bg-primary-600 text-white font-bold rounded-lg hover:bg-primary-700 transition-colors disabled:bg-gray-300 disabled:cursor-not-allowed">
              {{ (cartStore.totalPrice + shippingFee).toLocaleString('ko-KR') }}원 결제하기
            </button>

            <div class="mt-4 p-3 bg-blue-50 rounded-lg">
              <p class="text-xs text-blue-800">
                <svg class="inline w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z" clip-rule="evenodd" />
                </svg>
                안전한 결제를 위해 SSL 암호화가 적용됩니다.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import Layout from '../components/Layout.vue'

const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()

const form = ref({
  receiverName: authStore.user?.name || '',
  receiverPhone: '',
  zipCode: '',
  address: '',
  addressDetail: '',
  deliveryRequest: '',
  customRequest: '',
  paymentMethod: 'card',
  cardNumber: '',
  cardExpiry: '',
  cardCvc: ''
})

const agreeToTerms = ref(false)

const paymentMethods = [
  {
    value: 'card',
    label: '신용/체크카드',
    description: '모든 카드 사용 가능',
    icon: 'M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z'
  },
  {
    value: 'transfer',
    label: '실시간 계좌이체',
    description: '은행 계좌에서 즉시 출금',
    icon: 'M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3'
  },
  {
    value: 'virtual',
    label: '가상계좌',
    description: '입금 확인 후 배송 시작',
    icon: 'M9 14l6-6m-5.5.5h.01m4.99 5h.01M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16l3.5-2 3.5 2 3.5-2 3.5 2z'
  },
  {
    value: 'phone',
    label: '휴대폰 결제',
    description: '휴대폰 소액 결제',
    icon: 'M12 18h.01M8 21h8a2 2 0 002-2V5a2 2 0 00-2-2H8a2 2 0 00-2 2v14a2 2 0 002 2z'
  }
]

const shippingFee = computed(() => {
  return cartStore.totalPrice >= 50000 ? 0 : 3000
})

const canProcessOrder = computed(() => {
  return agreeToTerms.value &&
         form.value.receiverName &&
         form.value.receiverPhone &&
         form.value.address &&
         form.value.paymentMethod &&
         cartStore.items.length > 0
})

function searchAddress() {
  // Dummy address search
  form.value.zipCode = '06234'
  form.value.address = '서울특별시 강남구 테헤란로 123'
  alert('주소 검색 기능은 실제 API 연동이 필요합니다. (카카오 주소 API 등)')
}

function processOrder() {
  if (!canProcessOrder.value) {
    alert('필수 항목을 모두 입력해주세요.')
    return
  }

  // Create order (dummy - would call API in real implementation)
  const order = {
    id: Date.now(),
    orderNumber: `ORD${Date.now()}`,
    items: [...cartStore.items],
    receiver: {
      name: form.value.receiverName,
      phone: form.value.receiverPhone,
      address: `${form.value.address} ${form.value.addressDetail}`,
      zipCode: form.value.zipCode
    },
    payment: {
      method: form.value.paymentMethod,
      amount: cartStore.totalPrice + shippingFee.value
    },
    deliveryRequest: form.value.deliveryRequest === 'custom' ? form.value.customRequest : form.value.deliveryRequest,
    status: 'pending',
    createdAt: new Date().toISOString()
  }

  // Save to localStorage (in real app, this would be saved to backend)
  const orders = JSON.parse(localStorage.getItem('orders') || '[]')
  orders.unshift(order)
  localStorage.setItem('orders', JSON.stringify(orders))

  // Clear cart
  cartStore.clearCart()

  // Redirect to order complete page
  alert('주문이 완료되었습니다!')
  router.push(`/orders/${order.id}`)
}
</script>
