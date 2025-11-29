<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-4xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">1:1 문의</h1>
          <p class="text-gray-600 mt-1">궁금한 점을 문의해주세요</p>
        </div>

        <!-- Tabs -->
        <div class="flex gap-4 mb-6">
          <button
            @click="activeTab = 'write'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'write' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            문의하기
          </button>
          <button
            @click="activeTab = 'list'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'list' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            내 문의 내역
          </button>
        </div>

        <!-- Write Form -->
        <div v-if="activeTab === 'write'" class="bg-white rounded-xl shadow-sm p-6">
          <form @submit.prevent="submitInquiry" class="space-y-6">
            <!-- Category -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">문의 유형</label>
              <select v-model="form.category" class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500 focus:border-transparent">
                <option value="">선택해주세요</option>
                <option value="order">주문/결제</option>
                <option value="shipping">배송</option>
                <option value="return">교환/반품</option>
                <option value="product">상품 문의</option>
                <option value="etc">기타</option>
              </select>
            </div>

            <!-- Order Select (Optional) -->
            <div v-if="form.category === 'order' || form.category === 'shipping' || form.category === 'return'">
              <label class="block text-sm font-medium text-gray-700 mb-2">관련 주문 선택 (선택사항)</label>
              <select v-model="form.orderId" class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500 focus:border-transparent">
                <option value="">선택해주세요</option>
                <option v-for="order in recentOrders" :key="order.id" :value="order.id">
                  {{ order.date }} - {{ order.productName }} ({{ formatPrice(order.total) }}원)
                </option>
              </select>
            </div>

            <!-- Title -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">제목</label>
              <input
                v-model="form.title"
                type="text"
                placeholder="문의 제목을 입력해주세요"
                class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500 focus:border-transparent"
              />
            </div>

            <!-- Content -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">문의 내용</label>
              <textarea
                v-model="form.content"
                rows="6"
                placeholder="문의 내용을 자세히 입력해주세요"
                class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500 focus:border-transparent"
              ></textarea>
            </div>

            <!-- File Upload -->
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">파일 첨부 (선택사항)</label>
              <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-teal-500 transition-colors cursor-pointer">
                <svg class="w-10 h-10 mx-auto text-gray-400 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
                <p class="text-sm text-gray-500">이미지를 드래그하거나 클릭하여 업로드</p>
                <p class="text-xs text-gray-400 mt-1">최대 5MB, JPG/PNG/GIF</p>
              </div>
            </div>

            <!-- Submit -->
            <div class="flex justify-end gap-3">
              <button type="button" class="px-6 py-3 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 font-medium">
                취소
              </button>
              <button type="submit" :disabled="isSubmitting" class="px-6 py-3 bg-teal-600 text-white rounded-lg hover:bg-teal-700 font-medium disabled:opacity-50">
                {{ isSubmitting ? '등록 중...' : '문의 등록' }}
              </button>
            </div>
          </form>
        </div>

        <!-- Inquiry List -->
        <div v-else class="bg-white rounded-xl shadow-sm overflow-hidden">
          <div class="divide-y">
            <div v-for="inquiry in inquiries" :key="inquiry.id" class="p-4 hover:bg-gray-50 cursor-pointer" @click="openInquiry(inquiry)">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center gap-2 mb-1">
                    <span :class="['px-2 py-0.5 text-xs font-medium rounded', getStatusClass(inquiry.status)]">
                      {{ inquiry.statusText }}
                    </span>
                    <span class="text-xs text-gray-500">{{ inquiry.categoryText }}</span>
                  </div>
                  <h3 class="font-medium text-gray-900">{{ inquiry.title }}</h3>
                  <p class="text-sm text-gray-500 mt-1">{{ inquiry.date }}</p>
                </div>
                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </div>
            </div>
            <div v-if="inquiries.length === 0" class="p-8 text-center text-gray-500">
              문의 내역이 없습니다.
            </div>
          </div>
        </div>

        <!-- Inquiry Detail Modal -->
        <div v-if="selectedInquiry" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="selectedInquiry = null">
          <div class="bg-white rounded-xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div class="p-6 border-b">
              <div class="flex items-center gap-2 mb-2">
                <span :class="['px-2 py-0.5 text-xs font-medium rounded', getStatusClass(selectedInquiry.status)]">
                  {{ selectedInquiry.statusText }}
                </span>
                <span class="text-xs text-gray-500">{{ selectedInquiry.categoryText }}</span>
              </div>
              <h2 class="text-xl font-bold text-gray-900">{{ selectedInquiry.title }}</h2>
              <p class="text-sm text-gray-500 mt-1">{{ selectedInquiry.date }}</p>
            </div>
            <div class="p-6 space-y-6">
              <!-- Question -->
              <div class="bg-gray-50 rounded-lg p-4">
                <div class="flex items-center gap-2 mb-2">
                  <span class="text-teal-600 font-bold">Q</span>
                  <span class="text-sm text-gray-500">내 문의</span>
                </div>
                <p class="text-gray-700 whitespace-pre-line">{{ selectedInquiry.content }}</p>
              </div>
              <!-- Answer -->
              <div v-if="selectedInquiry.answer" class="bg-teal-50 rounded-lg p-4">
                <div class="flex items-center gap-2 mb-2">
                  <span class="text-teal-600 font-bold">A</span>
                  <span class="text-sm text-gray-500">MintShop 답변 | {{ selectedInquiry.answerDate }}</span>
                </div>
                <p class="text-gray-700 whitespace-pre-line">{{ selectedInquiry.answer }}</p>
              </div>
              <div v-else class="text-center py-4 text-gray-500">
                <svg class="w-12 h-12 mx-auto text-gray-300 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <p>답변 대기 중입니다.</p>
                <p class="text-sm">영업일 기준 1-2일 내 답변드리겠습니다.</p>
              </div>
            </div>
            <div class="p-4 border-t bg-gray-50 flex justify-end">
              <button @click="selectedInquiry = null" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
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
import { ref } from 'vue'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success } = useAlert()

const activeTab = ref('write')
const isSubmitting = ref(false)
const selectedInquiry = ref(null)

const form = ref({
  category: '',
  orderId: '',
  title: '',
  content: ''
})

const recentOrders = ref([
  { id: 1, date: '2025-01-15', productName: '프리미엄 무선 이어폰', total: 89000 },
  { id: 2, date: '2025-01-12', productName: '스마트 워치 프로', total: 299000 },
  { id: 3, date: '2025-01-10', productName: '노트북 파우치 15인치', total: 45000 }
])

const inquiries = ref([
  { id: 1, category: 'shipping', categoryText: '배송', title: '배송 예정일 문의드립니다', date: '2025-01-18', status: 'answered', statusText: '답변완료', content: '주문번호 20250118-001234 건의 배송 예정일이 궁금합니다.\n현재 상품준비중으로 표시되어 있는데 언제쯤 발송될까요?', answer: '안녕하세요, 고객님.\n\n해당 주문 건은 금일 발송 예정이며, 내일 중으로 배송 완료될 예정입니다.\n\n배송 출발 시 카카오톡 알림을 드리니 참고 부탁드립니다.\n\n감사합니다.', answerDate: '2025-01-18' },
  { id: 2, category: 'return', categoryText: '교환/반품', title: '사이즈 교환 요청', date: '2025-01-15', status: 'pending', statusText: '답변대기', content: '주문한 옷의 사이즈가 맞지 않아 교환을 요청드립니다.\nL 사이즈로 교환 가능한지 확인 부탁드립니다.' },
  { id: 3, category: 'product', categoryText: '상품문의', title: '상품 재입고 문의', date: '2025-01-10', status: 'answered', statusText: '답변완료', content: '품절된 상품의 재입고 일정이 궁금합니다.', answer: '안녕하세요, 고객님.\n\n해당 상품은 1월 말 재입고 예정입니다.\n재입고 알림 신청을 해주시면 입고 시 알림을 받으실 수 있습니다.\n\n감사합니다.', answerDate: '2025-01-11' }
])

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function getStatusClass(status) {
  return status === 'answered' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'
}

function openInquiry(inquiry) {
  selectedInquiry.value = inquiry
}

function submitInquiry() {
  if (!form.value.category || !form.value.title || !form.value.content) {
    return
  }
  isSubmitting.value = true
  setTimeout(() => {
    isSubmitting.value = false
    success('문의가 등록되었습니다.')
    form.value = { category: '', orderId: '', title: '', content: '' }
    activeTab.value = 'list'
  }, 1000)
}
</script>
