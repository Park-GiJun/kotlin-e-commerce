<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-6">
          <h1 class="text-2xl font-bold text-gray-900">리뷰 관리</h1>
          <p class="text-gray-600 mt-1">총 {{ formatPrice(totalReviews) }}개의 리뷰</p>
        </div>

        <!-- Stats -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
          <div class="bg-white rounded-xl shadow-sm p-4 text-center">
            <div class="text-2xl font-bold text-teal-600">{{ stats.averageRating }}</div>
            <div class="flex justify-center text-yellow-400 my-1">
              <svg v-for="i in 5" :key="i" class="w-4 h-4" :fill="i <= Math.round(stats.averageRating) ? 'currentColor' : 'none'" :stroke="i <= Math.round(stats.averageRating) ? 'none' : 'currentColor'" viewBox="0 0 20 20">
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
              </svg>
            </div>
            <div class="text-sm text-gray-500">평균 평점</div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4 text-center">
            <div class="text-2xl font-bold text-gray-900">{{ stats.todayReviews }}</div>
            <div class="text-sm text-gray-500 mt-1">오늘 등록</div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4 text-center">
            <div class="text-2xl font-bold text-yellow-600">{{ stats.pendingReviews }}</div>
            <div class="text-sm text-gray-500 mt-1">답변 대기</div>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-4 text-center">
            <div class="text-2xl font-bold text-red-600">{{ stats.reportedReviews }}</div>
            <div class="text-sm text-gray-500 mt-1">신고된 리뷰</div>
          </div>
        </div>

        <!-- Filters -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="grid grid-cols-1 md:grid-cols-5 gap-4">
            <div>
              <label class="block text-sm text-gray-600 mb-1">평점</label>
              <select v-model="filters.rating" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="5">5점</option>
                <option value="4">4점</option>
                <option value="3">3점</option>
                <option value="2">2점</option>
                <option value="1">1점</option>
              </select>
            </div>
            <div>
              <label class="block text-sm text-gray-600 mb-1">상태</label>
              <select v-model="filters.status" class="w-full border border-gray-300 rounded-lg p-2 text-sm">
                <option value="">전체</option>
                <option value="pending">답변대기</option>
                <option value="replied">답변완료</option>
                <option value="reported">신고됨</option>
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
              <input v-model="filters.search" type="text" placeholder="상품명, 작성자" class="w-full border border-gray-300 rounded-lg p-2 text-sm" />
            </div>
            <div class="flex items-end">
              <button class="w-full px-4 py-2 bg-gray-800 text-white rounded-lg hover:bg-gray-900">검색</button>
            </div>
          </div>
        </div>

        <!-- Reviews List -->
        <div class="space-y-4">
          <div v-for="review in reviews" :key="review.id" class="bg-white rounded-xl shadow-sm p-6">
            <div class="flex justify-between items-start mb-4">
              <div class="flex items-center gap-4">
                <div class="w-12 h-12 rounded-lg bg-gray-100 flex items-center justify-center">
                  <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>
                <div>
                  <h3 class="font-medium text-gray-900">{{ review.productName }}</h3>
                  <div class="flex items-center gap-2 mt-1">
                    <div class="flex text-yellow-400">
                      <svg v-for="i in 5" :key="i" class="w-4 h-4" :fill="i <= review.rating ? 'currentColor' : 'none'" :stroke="i <= review.rating ? 'none' : 'currentColor'" viewBox="0 0 20 20">
                        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                      </svg>
                    </div>
                    <span class="text-sm text-gray-500">{{ review.rating }}점</span>
                  </div>
                </div>
              </div>
              <div class="flex items-center gap-2">
                <span :class="['px-2 py-1 text-xs font-medium rounded-full', getStatusClass(review.status)]">
                  {{ review.statusText }}
                </span>
                <span v-if="review.isReported" class="px-2 py-1 text-xs font-medium rounded-full bg-red-100 text-red-700">
                  신고됨
                </span>
              </div>
            </div>

            <div class="bg-gray-50 rounded-lg p-4 mb-4">
              <div class="flex justify-between items-center mb-2">
                <div class="flex items-center gap-2">
                  <span class="font-medium text-gray-900">{{ review.author }}</span>
                  <span class="text-sm text-gray-500">{{ review.date }}</span>
                </div>
              </div>
              <p class="text-gray-700">{{ review.content }}</p>
              <div v-if="review.images && review.images.length > 0" class="flex gap-2 mt-3">
                <div v-for="(img, idx) in review.images" :key="idx" class="w-16 h-16 bg-gray-200 rounded-lg"></div>
              </div>
            </div>

            <!-- Reply Section -->
            <div v-if="review.reply" class="bg-teal-50 rounded-lg p-4 mb-4">
              <div class="flex items-center gap-2 mb-2">
                <span class="text-teal-700 font-medium">관리자 답변</span>
                <span class="text-sm text-gray-500">{{ review.replyDate }}</span>
              </div>
              <p class="text-gray-700">{{ review.reply }}</p>
            </div>

            <!-- Actions -->
            <div class="flex justify-between items-center pt-4 border-t">
              <div class="flex gap-2">
                <button @click="openReplyModal(review)" class="px-3 py-1 text-sm bg-teal-600 text-white rounded-lg hover:bg-teal-700">
                  {{ review.reply ? '답변 수정' : '답변 작성' }}
                </button>
                <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                  상품 보기
                </button>
              </div>
              <div class="flex gap-2">
                <button v-if="review.isReported" class="px-3 py-1 text-sm text-gray-600 hover:text-gray-900">
                  신고 해제
                </button>
                <button class="px-3 py-1 text-sm text-red-600 hover:text-red-800">
                  삭제
                </button>
              </div>
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
          <button class="px-4 py-2 bg-white border rounded-lg hover:bg-gray-50">3</button>
          <button class="px-3 py-2 bg-white border rounded-lg hover:bg-gray-50">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
            </svg>
          </button>
        </div>

        <!-- Reply Modal -->
        <div v-if="replyModal.show" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="replyModal.show = false">
          <div class="bg-white rounded-xl max-w-lg w-full">
            <div class="p-6 border-b">
              <h2 class="text-xl font-bold">답변 작성</h2>
            </div>
            <div class="p-6">
              <div class="mb-4 p-4 bg-gray-50 rounded-lg">
                <p class="text-sm text-gray-500 mb-1">리뷰 내용</p>
                <p class="text-gray-700">{{ replyModal.review?.content }}</p>
              </div>
              <label class="block text-sm font-medium text-gray-700 mb-2">답변</label>
              <textarea
                v-model="replyModal.content"
                rows="4"
                placeholder="답변 내용을 입력해주세요"
                class="w-full border border-gray-300 rounded-lg p-3 focus:ring-2 focus:ring-teal-500"
              ></textarea>
            </div>
            <div class="p-4 border-t bg-gray-50 flex justify-end gap-2">
              <button @click="replyModal.show = false" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
                취소
              </button>
              <button @click="submitReply" class="px-4 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700">
                등록
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

const totalReviews = ref(1256)
const filters = ref({ rating: '', status: '', period: '', search: '' })
const replyModal = ref({ show: false, review: null, content: '' })

const stats = ref({
  averageRating: 4.3,
  todayReviews: 12,
  pendingReviews: 8,
  reportedReviews: 2
})

const reviews = ref([
  { id: 1, productName: '프리미엄 무선 이어폰', rating: 5, author: '김민수', date: '2025-01-30', content: '음질이 정말 좋아요! 노이즈 캔슬링 기능도 완벽합니다. 배송도 빨랐고 포장도 꼼꼼하게 되어 있었어요. 강력 추천합니다!', status: 'pending', statusText: '답변대기', images: [1, 2] },
  { id: 2, productName: '스마트 워치 프로', rating: 4, author: '이영희', date: '2025-01-29', content: '디자인이 예쁘고 기능도 다양해요. 배터리가 조금 아쉽지만 전체적으로 만족합니다.', status: 'replied', statusText: '답변완료', reply: '소중한 리뷰 감사합니다. 배터리 관련하여 펌웨어 업데이트로 개선될 예정입니다. 앞으로도 좋은 제품으로 보답하겠습니다.', replyDate: '2025-01-29' },
  { id: 3, productName: '블루투스 스피커', rating: 2, author: '박철수', date: '2025-01-28', content: '음질이 기대에 못 미쳐요. 가격 대비 별로입니다.', status: 'pending', statusText: '답변대기', isReported: true },
  { id: 4, productName: '노트북 파우치', rating: 5, author: '최지현', date: '2025-01-27', content: '딱 맞아요! 쿠션감도 좋고 디자인도 깔끔해서 마음에 듭니다.', status: 'replied', statusText: '답변완료', reply: '리뷰 감사합니다! 만족스러운 쇼핑이 되셨다니 기쁩니다. 다음에도 좋은 상품으로 찾아뵙겠습니다.', replyDate: '2025-01-28' }
])

function formatPrice(num) {
  return (num || 0).toLocaleString('ko-KR')
}

function getStatusClass(status) {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-700',
    replied: 'bg-green-100 text-green-700'
  }
  return classes[status] || 'bg-gray-100 text-gray-700'
}

function openReplyModal(review) {
  replyModal.value = {
    show: true,
    review: review,
    content: review.reply || ''
  }
}

function submitReply() {
  const review = replyModal.value.review
  if (review) {
    review.reply = replyModal.value.content
    review.replyDate = new Date().toISOString().split('T')[0]
    review.status = 'replied'
    review.statusText = '답변완료'
  }
  replyModal.value.show = false
  success('답변이 등록되었습니다.')
}
</script>
