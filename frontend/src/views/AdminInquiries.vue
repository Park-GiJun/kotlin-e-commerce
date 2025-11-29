<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-6">
          <h1 class="text-2xl font-bold text-gray-900">문의 관리</h1>
          <p class="text-gray-600 mt-1">총 {{ formatPrice(totalInquiries) }}건의 문의</p>
        </div>

        <!-- Stats -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
          <div class="bg-white rounded-xl shadow-sm p-4">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">전체 문의</p>
                <p class="text-2xl font-bold text-gray-900">{{ stats.total }}</p>
              </div>
              <div class="w-10 h-10 rounded-full bg-gray-100 flex items-center justify-center">
                <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">답변 대기</p>
                <p class="text-2xl font-bold text-yellow-600">{{ stats.pending }}</p>
              </div>
              <div class="w-10 h-10 rounded-full bg-yellow-100 flex items-center justify-center">
                <svg class="w-5 h-5 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">오늘 접수</p>
                <p class="text-2xl font-bold text-blue-600">{{ stats.today }}</p>
              </div>
              <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center">
                <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                </svg>
              </div>
            </div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4">
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500">평균 응답시간</p>
                <p class="text-2xl font-bold text-teal-600">{{ stats.avgResponseTime }}</p>
              </div>
              <div class="w-10 h-10 rounded-full bg-teal-100 flex items-center justify-center">
                <svg class="w-5 h-5 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Filters -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="grid grid-cols-1 md:grid-cols-5 gap-4">
            <div>
              <label class="block text-sm text-gray-600 mb-1">문의 유형</label>
              <select v-model="filters.category" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="order">주문/결제</option>
                <option value="shipping">배송</option>
                <option value="return">교환/반품</option>
                <option value="product">상품문의</option>
                <option value="etc">기타</option>
              </select>
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">상태</label>
              <select v-model="filters.status" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="pending">답변대기</option>
                <option value="answered">답변완료</option>
              </select>
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">기간</label>
              <select v-model="filters.period" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="today">오늘</option>
                <option value="week">최근 7일</option>
                <option value="month">최근 30일</option>
              </select>
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">검색</label>
              <input v-model="filters.search" type="text" placeholder="제목, 작성자" class="w-full border border-gray-300 rounded-lg p-2 text-sm" />
            </div>
            <div class="flex items-end">
              <button class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg hover:bg-gray-900">검색</button>
            </div>
          </div>
        </div>

        <!-- Inquiries List -->
        <div class="bg-white rounded-xl shadow-sm overflow-hidden">
          <table class="w-full">
            <thead class="bg-gray-50">
              <tr>
                <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">번호</th>
                <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">유형</th>
                <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">제목</th>
                <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">작성자</th>
                <th class="px-4 py-3 text-left text-sm font-medium text-gray-500">등록일</th>
                <th class="px-4 py-3 text-center text-sm font-medium text-gray-500">상태</th>
                <th class="px-4 py-3 text-center text-sm font-medium text-gray-500">관리</th>
              </tr>
            </thead>
            <tbody class="divide-y">
              <tr v-for="inquiry in inquiries" :key="inquiry.id" class="hover:bg-gray-50">
                <td class="px-4 py-3 text-sm text-gray-500">{{ inquiry.id }}</td>
                <td class="px-4 py-3">
                  <span class="px-2 py-1 text-xs bg-gray-100 text-gray-700 rounded">{{ inquiry.categoryText }}</span>
                </td>
                <td class="px-4 py-3">
                  <span class="font-medium text-gray-900 cursor-pointer hover:text-teal-600" @click="openInquiry(inquiry)">
                    {{ inquiry.title }}
                  </span>
                </td>
                <td class="px-4 py-3 text-sm text-gray-600">{{ inquiry.author }}</td>
                <td class="px-4 py-3 text-sm text-gray-500">{{ inquiry.date }}</td>
                <td class="px-4 py-3 text-center">
                  <span :class="['px-2 py-1 text-xs font-medium rounded-full', getStatusClass(inquiry.status)]">
                    {{ inquiry.statusText }}
                  </span>
                </td>
                <td class="px-4 py-3 text-center">
                  <button @click="openInquiry(inquiry)" class="text-teal-600 hover:text-teal-800 text-sm font-medium">
                    {{ inquiry.status === 'pending' ? '답변하기' : '상세보기' }}
                  </button>
                </td>
              </tr>
            </tbody>
          </table>

          <!-- Pagination -->
          <div class="p-4 border-t flex items-center justify-between">
            <span class="text-sm text-gray-600">1 / 10 페이지</span>
            <div class="flex gap-1">
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50">이전</button>
              <button class="px-3 py-1 bg-teal-600 text-white rounded">1</button>
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50">2</button>
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50">3</button>
              <button class="px-3 py-1 bg-white border rounded hover:bg-gray-50">다음</button>
            </div>
          </div>
        </div>

        <!-- Inquiry Detail Modal -->
        <div v-if="selectedInquiry" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="selectedInquiry = null">
          <div class="bg-white rounded-xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div class="p-6 border-b flex justify-between items-center">
              <div>
                <div class="flex items-center gap-2 mb-1">
                  <span class="px-2 py-1 text-xs bg-gray-100 text-gray-700 rounded">{{ selectedInquiry.categoryText }}</span>
                  <span :class="['px-2 py-1 text-xs font-medium rounded-full', getStatusClass(selectedInquiry.status)]">
                    {{ selectedInquiry.statusText }}
                  </span>
                </div>
                <h2 class="text-xl font-bold">{{ selectedInquiry.title }}</h2>
              </div>
              <button @click="selectedInquiry = null" class="text-gray-400 hover:text-gray-600">
                <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
              </button>
            </div>
            <div class="p-6 space-y-6">
              <!-- Info -->
              <div class="grid grid-cols-2 gap-4 text-sm">
                <div><span class="text-gray-500">작성자:</span> {{ selectedInquiry.author }}</div>
                <div><span class="text-gray-500">이메일:</span> {{ selectedInquiry.email }}</div>
                <div><span class="text-gray-500">등록일:</span> {{ selectedInquiry.date }}</div>
                <div v-if="selectedInquiry.orderId"><span class="text-gray-500">관련 주문:</span> {{ selectedInquiry.orderId }}</div>
              </div>

              <!-- Question -->
              <div class="bg-gray-50 rounded-lg p-4">
                <div class="flex items-center gap-2 mb-2">
                  <span class="text-teal-600 font-bold">Q</span>
                  <span class="text-sm text-gray-500">문의 내용</span>
                </div>
                <p class="text-gray-700 whitespace-pre-line">{{ selectedInquiry.content }}</p>
              </div>

              <!-- Answer -->
              <div v-if="selectedInquiry.answer" class="bg-teal-50 rounded-lg p-4">
                <div class="flex items-center gap-2 mb-2">
                  <span class="text-teal-600 font-bold">A</span>
                  <span class="text-sm text-gray-500">답변 | {{ selectedInquiry.answerDate }}</span>
                </div>
                <p class="text-gray-700 whitespace-pre-line">{{ selectedInquiry.answer }}</p>
              </div>

              <!-- Reply Form -->
              <div v-if="selectedInquiry.status === 'pending' || isEditing">
                <label class="block text-sm font-medium text-gray-700 mb-2">답변 작성</label>
                <textarea
                  v-model="replyContent"
                  rows="5"
                  placeholder="답변 내용을 입력해주세요"
                  class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500"
                ></textarea>
              </div>
            </div>
            <div class="p-4 border-t bg-gray-50 flex justify-between">
              <button v-if="selectedInquiry.answer && !isEditing" @click="isEditing = true" class="px-4 py-2 text-gray-600 hover:text-gray-900">
                답변 수정
              </button>
              <div class="flex gap-2 ml-auto">
                <button @click="selectedInquiry = null; isEditing = false" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                  닫기
                </button>
                <button v-if="selectedInquiry.status === 'pending' || isEditing" @click="submitAnswer" class="px-4 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700">
                  답변 등록
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
import { ref } from 'vue'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success } = useAlert()

const totalInquiries = ref(156)
const filters = ref({ category: '', status: '', period: '', search: '' })
const selectedInquiry = ref(null)
const replyContent = ref('')
const isEditing = ref(false)

const stats = ref({
  total: 156,
  pending: 8,
  today: 5,
  avgResponseTime: '4.2시간'
})

const inquiries = ref([
  { id: 156, category: 'order', categoryText: '주문/결제', title: '주문 취소 요청드립니다', author: '김민수', email: 'kim@example.com', date: '2025-01-30', status: 'pending', statusText: '답변대기', content: '주문번호 20250130-001 건의 주문 취소를 요청드립니다.\n결제 후 동일 상품을 더 저렴하게 발견하여 취소 후 재주문하고 싶습니다.', orderId: '20250130-001' },
  { id: 155, category: 'shipping', categoryText: '배송', title: '배송지 변경 가능한가요?', author: '이영희', email: 'lee@example.com', date: '2025-01-30', status: 'pending', statusText: '답변대기', content: '주문한 상품의 배송지를 변경하고 싶습니다.\n기존: 서울시 강남구\n변경: 경기도 성남시 분당구\n\n변경이 가능할까요?', orderId: '20250129-015' },
  { id: 154, category: 'return', categoryText: '교환/반품', title: '사이즈 교환 신청', author: '박철수', email: 'park@example.com', date: '2025-01-29', status: 'answered', statusText: '답변완료', content: '구매한 티셔츠 사이즈가 맞지 않아 교환을 원합니다.', answer: '안녕하세요, 고객님.\n교환 신청이 접수되었습니다. 상품 수거 후 새 사이즈로 재발송해드리겠습니다.\n감사합니다.', answerDate: '2025-01-29' },
  { id: 153, category: 'product', categoryText: '상품문의', title: '상품 재입고 일정 문의', author: '최지현', email: 'choi@example.com', date: '2025-01-28', status: 'answered', statusText: '답변완료', content: '품절된 무선 이어폰 재입고 일정이 궁금합니다.', answer: '안녕하세요, 고객님.\n해당 상품은 2월 첫째 주 재입고 예정입니다.\n재입고 알림 신청을 해주시면 입고 시 알림을 받으실 수 있습니다.', answerDate: '2025-01-28' },
  { id: 152, category: 'etc', categoryText: '기타', title: '영수증 재발급 요청', author: '정현우', email: 'jung@example.com', date: '2025-01-27', status: 'pending', statusText: '답변대기', content: '지난 달 구매한 상품의 영수증 재발급이 필요합니다.\n회사 경비 처리를 위해 세금계산서 발급이 가능할까요?' }
])

function formatPrice(num) {
  return (num || 0).toLocaleString('ko-KR')
}

function getStatusClass(status) {
  return status === 'answered' ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'
}

function openInquiry(inquiry) {
  selectedInquiry.value = inquiry
  replyContent.value = inquiry.answer || ''
  isEditing.value = false
}

function submitAnswer() {
  if (!replyContent.value.trim()) return

  selectedInquiry.value.answer = replyContent.value
  selectedInquiry.value.answerDate = new Date().toISOString().split('T')[0]
  selectedInquiry.value.status = 'answered'
  selectedInquiry.value.statusText = '답변완료'
  isEditing.value = false

  success('답변이 등록되었습니다.')
}
</script>
