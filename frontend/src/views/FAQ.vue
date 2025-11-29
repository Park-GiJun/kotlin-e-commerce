<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-4xl mx-auto px-6">
        <!-- Header -->
        <div class="text-center mb-8">
          <h1 class="text-2xl font-bold text-gray-900">자주 묻는 질문</h1>
          <p class="text-gray-600 mt-1">궁금한 점을 빠르게 찾아보세요</p>
        </div>

        <!-- Search -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="relative">
            <input
              v-model="searchQuery"
              type="text"
              placeholder="질문을 검색해보세요"
              class="w-full border border-gray-300 rounded-lg px-4 py-3 pl-12 focus:ring-2 focus:ring-teal-500 focus:border-transparent"
            />
            <svg class="w-5 h-5 text-gray-400 absolute left-4 top-1/2 -translate-y-1/2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
        </div>

        <!-- Category Tabs -->
        <div class="flex flex-wrap gap-2 mb-6">
          <button
            v-for="category in categories"
            :key="category.id"
            @click="activeCategory = category.id"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeCategory === category.id ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            {{ category.label }}
          </button>
        </div>

        <!-- FAQ List -->
        <div class="bg-white rounded-xl shadow-sm overflow-hidden">
          <div class="divide-y">
            <div v-for="(faq, index) in filteredFaqs" :key="faq.id">
              <button
                @click="toggleFaq(faq.id)"
                class="w-full p-4 text-left hover:bg-gray-50 flex justify-between items-start"
              >
                <div class="flex-1 pr-4">
                  <div class="flex items-center gap-2 mb-1">
                    <span class="text-teal-600 font-bold">Q</span>
                    <span class="text-xs bg-gray-100 text-gray-600 px-2 py-0.5 rounded">{{ faq.categoryText }}</span>
                  </div>
                  <span class="font-medium text-gray-900">{{ faq.question }}</span>
                </div>
                <svg :class="['w-5 h-5 text-gray-400 transition-transform flex-shrink-0', openFaq === faq.id && 'rotate-180']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
              </button>
              <div v-show="openFaq === faq.id" class="px-4 pb-4">
                <div class="bg-gray-50 rounded-lg p-4 ml-6">
                  <div class="flex items-start gap-2">
                    <span class="text-teal-600 font-bold">A</span>
                    <p class="text-gray-700 whitespace-pre-line">{{ faq.answer }}</p>
                  </div>
                </div>
              </div>
            </div>
            <div v-if="filteredFaqs.length === 0" class="p-8 text-center text-gray-500">
              검색 결과가 없습니다.
            </div>
          </div>
        </div>

        <!-- Contact -->
        <div class="mt-8 bg-teal-50 rounded-xl p-6 text-center">
          <h3 class="font-bold text-gray-900 mb-2">원하는 답변을 찾지 못하셨나요?</h3>
          <p class="text-gray-600 mb-4">1:1 문의를 통해 도움을 받으실 수 있습니다.</p>
          <router-link to="/inquiry" class="inline-block px-6 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 font-medium">
            1:1 문의하기
          </router-link>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import Layout from '../components/Layout.vue'

const searchQuery = ref('')
const activeCategory = ref('all')
const openFaq = ref(null)

const categories = [
  { id: 'all', label: '전체' },
  { id: 'order', label: '주문/결제' },
  { id: 'shipping', label: '배송' },
  { id: 'return', label: '교환/반품' },
  { id: 'member', label: '회원/포인트' },
  { id: 'etc', label: '기타' }
]

const faqs = ref([
  { id: 1, category: 'order', categoryText: '주문/결제', question: '주문은 어떻게 하나요?', answer: '1. 원하는 상품을 장바구니에 담습니다.\n2. 장바구니에서 주문할 상품을 확인합니다.\n3. 주문서 작성 페이지에서 배송지와 결제 정보를 입력합니다.\n4. 결제 완료 후 주문이 완료됩니다.' },
  { id: 2, category: 'order', categoryText: '주문/결제', question: '주문 취소는 어떻게 하나요?', answer: '마이페이지 > 주문내역에서 배송 전 상품에 한해 직접 취소가 가능합니다.\n\n배송이 시작된 경우 고객센터(1588-1234)로 문의해주세요.' },
  { id: 3, category: 'order', categoryText: '주문/결제', question: '결제 수단은 무엇이 있나요?', answer: '신용카드, 체크카드, 무통장입금, 실시간 계좌이체, 카카오페이, 네이버페이, 토스 등 다양한 결제 수단을 지원합니다.' },
  { id: 4, category: 'shipping', categoryText: '배송', question: '배송비는 얼마인가요?', answer: '3만원 이상 구매 시 무료배송입니다.\n3만원 미만 구매 시 배송비 3,000원이 부과됩니다.\n\n도서/산간 지역은 추가 배송비가 발생할 수 있습니다.' },
  { id: 5, category: 'shipping', categoryText: '배송', question: '배송은 얼마나 걸리나요?', answer: '주문 후 1-3일 이내에 발송되며, 발송 후 1-2일 이내에 도착합니다.\n도서/산간 지역은 1-2일 추가 소요될 수 있습니다.\n\n주말 및 공휴일은 배송이 진행되지 않습니다.' },
  { id: 6, category: 'shipping', categoryText: '배송', question: '배송 조회는 어떻게 하나요?', answer: '마이페이지 > 주문내역에서 배송 현황을 확인할 수 있습니다.\n\n발송 완료 시 카카오톡 또는 SMS로 송장번호와 함께 안내드립니다.' },
  { id: 7, category: 'return', categoryText: '교환/반품', question: '교환/반품은 어떻게 하나요?', answer: '마이페이지 > 주문내역에서 교환/반품 신청이 가능합니다.\n\n상품 수령 후 7일 이내에 신청해주세요.\n단순 변심의 경우 반품 배송비가 부과됩니다.' },
  { id: 8, category: 'return', categoryText: '교환/반품', question: '교환/반품이 불가능한 경우는?', answer: '- 상품 수령 후 7일이 경과한 경우\n- 사용 또는 소비된 경우\n- 고객 부주의로 상품이 훼손된 경우\n- 상품 택(TAG)이 제거된 경우\n- 복제가 가능한 상품의 포장을 훼손한 경우' },
  { id: 9, category: 'member', categoryText: '회원/포인트', question: '포인트 적립률은 어떻게 되나요?', answer: '구매 금액의 1%가 기본 적립됩니다.\n\n회원 등급에 따라 추가 적립 혜택이 제공됩니다.\n- 실버: 1.5%\n- 골드: 2%\n- VIP: 3%' },
  { id: 10, category: 'member', categoryText: '회원/포인트', question: '포인트 유효기간은 얼마인가요?', answer: '포인트는 적립일로부터 1년간 유효합니다.\n\n유효기간이 임박한 포인트는 마이페이지 > 포인트에서 확인하실 수 있습니다.' },
  { id: 11, category: 'member', categoryText: '회원/포인트', question: '회원 등급은 어떻게 결정되나요?', answer: '최근 6개월간 구매 금액을 기준으로 매월 1일 등급이 산정됩니다.\n\n- 브론즈: 기본\n- 실버: 10만원 이상\n- 골드: 30만원 이상\n- VIP: 50만원 이상' },
  { id: 12, category: 'etc', categoryText: '기타', question: '탈퇴는 어떻게 하나요?', answer: '마이페이지 > 회원정보 수정에서 회원 탈퇴를 진행할 수 있습니다.\n\n탈퇴 시 보유 포인트와 쿠폰은 모두 소멸되며, 재가입 시에도 복구되지 않습니다.' }
])

const filteredFaqs = computed(() => {
  let result = faqs.value

  if (activeCategory.value !== 'all') {
    result = result.filter(f => f.category === activeCategory.value)
  }

  if (searchQuery.value.trim()) {
    const query = searchQuery.value.toLowerCase()
    result = result.filter(f =>
      f.question.toLowerCase().includes(query) ||
      f.answer.toLowerCase().includes(query)
    )
  }

  return result
})

function toggleFaq(id) {
  openFaq.value = openFaq.value === id ? null : id
}
</script>
