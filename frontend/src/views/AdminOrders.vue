<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-6 flex justify-between items-center">
          <div>
            <h1 class="text-2xl font-bold text-gray-900">주문 관리</h1>
            <p class="text-gray-600 mt-1">총 {{ formatPrice(totalOrders) }}건의 주문</p>
          </div>
          <button class="px-4 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 flex items-center gap-2">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
            </svg>
            엑셀 다운로드
          </button>
        </div>

        <!-- Filters -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="grid grid-cols-1 md:grid-cols-5 gap-4">
            <div>
              <label class="block text-sm text-gray-600 mb-1">주문 상태</label>
              <select v-model="filters.status" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="pending">결제완료</option>
                <option value="preparing">상품준비</option>
                <option value="shipping">배송중</option>
                <option value="delivered">배송완료</option>
                <option value="cancelled">취소</option>
              </select>
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">시작일</label>
              <input v-model="filters.startDate" type="date" class="w-full border border-gray-300 rounded-lg p-2 text-sm" />
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">종료일</label>
              <input v-model="filters.endDate" type="date" class="w-full border border-gray-300 rounded-lg p-2 text-sm" />
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">검색</label>
              <input v-model="filters.search" type="text" placeholder="주문번호, 고객명" class="w-full border border-gray-300 rounded-lg p-2 text-sm" />
            </div>
            <div class="flex items-end">
              <button @click="applyFilters" class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg hover:bg-gray-900">
                검색
              </button>
            </div>
          </div>
        </div>

        <!-- Status Tabs -->
        <div class="flex gap-2 mb-4 overflow-x-auto pb-2">
          <button
            v-for="tab in statusTabs"
            :key="tab.value"
            @click="filters.status = tab.value"
            :class="['px-4 py-2 rounded-lg text-sm font-medium whitespace-nowrap transition-colors', filters.status === tab.value ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            {{ tab.label }} ({{ tab.count }})
          </button>
        </div>

        <!-- Orders Table -->
        <div class="bg-white rounded-xl shadow-sm overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead class="bg-gray-50">
                <tr>
                  <th class="px-4 py-3 text-left">
                    <input type="checkbox" v-model="selectAll" class="rounded border-gray-300" />
                  </th>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">주문번호</th>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">주문일시</th>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">고객</th>
                  <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">상품</th>
                  <th class="px-4 py-3 text-right text-sm font-medium text-gray-500">금액</th>
                  <th class="px-4 py-3 text-center text-sm font-medium text-gray-500">상태</th>
                  <th class="px-4 py-3 text-center text-sm font-medium text-gray-500">관리</th>
                </tr>
              </thead>
              <tbody class="divide-y">
                <tr v-for="order in orders" :key="order.id" class="hover:bg-gray-50">
                  <td class="px-4 py-3">
                    <input type="checkbox" :value="order.id" v-model="selectedOrders" class="rounded border-gray-300" />
                  </td>
                  <td class="px-4 py-3">
                    <span class="font-medium text-gray-900">{{ order.orderNumber }}</span>
                  </td>
                  <td class="px-4 py-3 text-sm text-gray-600">{{ order.date }}</td>
                  <td class="px-4 py-3">
                    <div class="text-sm font-medium text-gray-900">{{ order.customer }}</div>
                    <div class="text-xs text-gray-500">{{ order.phone }}</div>
                  </td>
                  <td class="px-4 py-3">
                    <div class="text-sm text-gray-900">{{ order.product }}</div>
                    <div class="text-xs text-gray-500">수량: {{ order.quantity }}개</div>
                  </td>
                  <td class="px-4 py-3 text-right">
                    <div class="text-sm font-medium text-gray-900">{{ formatPrice(order.amount) }}원</div>
                    <div class="text-xs text-gray-500">{{ order.paymentMethod }}</div>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <select
                      :value="order.status"
                      @change="updateOrderStatus(order.id, $event.target.value)"
                      :class="['text-xs font-medium px-2 py-1 rounded-lg border-0', getStatusClass(order.status)]"
                    >
                      <option value="pending">결제완료</option>
                      <option value="preparing">상품준비</option>
                      <option value="shipping">배송중</option>
                      <option value="delivered">배송완료</option>
                      <option value="cancelled">취소</option>
                    </select>
                  </td>
                  <td class="px-4 py-3 text-center">
                    <button @click="viewOrderDetail(order)" class="text-teal-600 hover:text-teal-800 text-sm font-medium">
                      상세
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- Bulk Actions -->
          <div v-if="selectedOrders.length > 0" class="p-4 bg-gray-50 border-t flex items-center gap-4">
            <span class="text-sm text-gray-600">{{ selectedOrders.length }}건 선택</span>
            <select class="border border-gray-300 rounded-lg px-3 py-1 text-sm">
              <option>상태 일괄 변경</option>
              <option value="preparing">상품준비로 변경</option>
              <option value="shipping">배송중으로 변경</option>
              <option value="delivered">배송완료로 변경</option>
            </select>
            <button class="px-3 py-1 bg-gray-800 text-white rounded-lg text-sm">적용</button>
          </div>

          <!-- Pagination -->
          <div class="p-4 border-t flex items-center justify-between">
            <span class="text-sm text-gray-600">{{ currentPage }} / {{ totalPages }} 페이지</span>
            <div class="flex gap-1">
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50" :disabled="currentPage === 1">이전</button>
              <button v-for="page in visiblePages" :key="page" :class="['px-3 py-1 rounded', page === currentPage ? 'bg-teal-600 text-white' : 'bg-white border hover:bg-gray-50']">
                {{ page }}
              </button>
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50" :disabled="currentPage === totalPages">다음</button>
            </div>
          </div>
        </div>

        <!-- Order Detail Modal -->
        <div v-if="selectedOrder" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="selectedOrder = null">
          <div class="bg-white rounded-xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div class="p-6 border-b flex justify-between items-center">
              <h2 class="text-xl font-bold">주문 상세</h2>
              <button @click="selectedOrder = null" class="text-gray-400 hover:text-gray-600">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
            <div class="p-6 space-y-6">
              <div class="grid grid-cols-2 gap-4">
                <div>
                  <p class="text-sm text-gray-500">주문번호</p>
                  <p class="font-medium">{{ selectedOrder.orderNumber }}</p>
                </div>
                <div>
                  <p class="text-sm text-gray-500">주문일시</p>
                  <p class="font-medium">{{ selectedOrder.date }}</p>
                </div>
              </div>
              <div class="border-t pt-4">
                <h3 class="font-medium mb-3">주문자 정보</h3>
                <div class="grid grid-cols-2 gap-4 text-sm">
                  <div><span class="text-gray-500">이름:</span> {{ selectedOrder.customer }}</div>
                  <div><span class="text-gray-500">연락처:</span> {{ selectedOrder.phone }}</div>
                  <div class="col-span-2"><span class="text-gray-500">주소:</span> {{ selectedOrder.address }}</div>
                </div>
              </div>
              <div class="border-t pt-4">
                <h3 class="font-medium mb-3">상품 정보</h3>
                <div class="bg-gray-50 rounded-lg p-4">
                  <div class="flex justify-between">
                    <span>{{ selectedOrder.product }} x {{ selectedOrder.quantity }}</span>
                    <span class="font-medium">{{ formatPrice(selectedOrder.amount) }}원</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="p-4 border-t bg-gray-50 flex justify-end gap-2">
              <button @click="selectedOrder = null" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                닫기
              </button>
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

const filters = ref({
  status: '',
  startDate: '',
  endDate: '',
  search: ''
})

const selectAll = ref(false)
const selectedOrders = ref([])
const selectedOrder = ref(null)
const currentPage = ref(1)
const totalPages = ref(10)
const totalOrders = ref(458)

const statusTabs = ref([
  { value: '', label: '전체', count: 458 },
  { value: 'pending', label: '결제완료', count: 25 },
  { value: 'preparing', label: '상품준비', count: 18 },
  { value: 'shipping', label: '배송중', count: 42 },
  { value: 'delivered', label: '배송완료', count: 365 },
  { value: 'cancelled', label: '취소', count: 8 }
])

const visiblePages = computed(() => {
  const pages = []
  for (let i = Math.max(1, currentPage.value - 2); i <= Math.min(totalPages.value, currentPage.value + 2); i++) {
    pages.push(i)
  }
  return pages
})

const orders = ref([
  { id: 1, orderNumber: '20250130-001', date: '2025-01-30 14:23', customer: '김민수', phone: '010-1234-5678', address: '서울시 강남구 테헤란로 123', product: '프리미엄 무선 이어폰', quantity: 1, amount: 89000, paymentMethod: '카드', status: 'pending' },
  { id: 2, orderNumber: '20250130-002', date: '2025-01-30 13:45', customer: '이영희', phone: '010-2345-6789', address: '경기도 성남시 분당구 판교로 456', product: '스마트 워치 프로', quantity: 1, amount: 299000, paymentMethod: '카카오페이', status: 'preparing' },
  { id: 3, orderNumber: '20250130-003', date: '2025-01-30 11:20', customer: '박철수', phone: '010-3456-7890', address: '부산시 해운대구 해운대로 789', product: '노트북 파우치 15인치', quantity: 2, amount: 45000, paymentMethod: '네이버페이', status: 'shipping' },
  { id: 4, orderNumber: '20250129-015', date: '2025-01-29 16:30', customer: '최지현', phone: '010-4567-8901', address: '대구시 수성구 범어로 321', product: '블루투스 스피커', quantity: 1, amount: 89000, paymentMethod: '카드', status: 'delivered' },
  { id: 5, orderNumber: '20250129-014', date: '2025-01-29 15:10', customer: '정현우', phone: '010-5678-9012', address: '인천시 연수구 송도로 654', product: '무선 충전기', quantity: 1, amount: 35000, paymentMethod: '토스', status: 'pending' },
  { id: 6, orderNumber: '20250129-013', date: '2025-01-29 14:05', customer: '강민지', phone: '010-6789-0123', address: '광주시 서구 상무로 987', product: 'USB-C 허브', quantity: 1, amount: 55000, paymentMethod: '카드', status: 'cancelled' }
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

function applyFilters() {
  console.log('Applying filters:', filters.value)
}

function updateOrderStatus(orderId, newStatus) {
  const order = orders.value.find(o => o.id === orderId)
  if (order) {
    order.status = newStatus
  }
}

function viewOrderDetail(order) {
  selectedOrder.value = order
}
</script>
