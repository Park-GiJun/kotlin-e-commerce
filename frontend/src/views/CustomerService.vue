<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-7xl mx-auto px-6">
        <!-- Header -->
        <div class="text-center mb-8">
          <h1 class="text-3xl font-bold text-gray-900">고객센터</h1>
          <p class="text-gray-600 mt-2">무엇을 도와드릴까요?</p>
        </div>

        <!-- Quick Contact -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div class="bg-white rounded-xl shadow-sm p-6 text-center">
            <div class="w-16 h-16 rounded-full bg-teal-100 flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
              </svg>
            </div>
            <h3 class="font-bold text-gray-900 mb-2">전화 상담</h3>
            <p class="text-2xl font-bold text-teal-600 mb-2">1588-1234</p>
            <p class="text-sm text-gray-500">평일 09:00 - 18:00</p>
            <p class="text-sm text-gray-500">점심시간 12:00 - 13:00</p>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-6 text-center">
            <div class="w-16 h-16 rounded-full bg-blue-100 flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
              </svg>
            </div>
            <h3 class="font-bold text-gray-900 mb-2">카카오톡 상담</h3>
            <p class="text-lg font-medium text-blue-600 mb-2">@MintShop</p>
            <p class="text-sm text-gray-500">24시간 문의 가능</p>
            <button class="mt-3 px-4 py-2 bg-yellow-400 text-gray-900 rounded-lg text-sm font-medium hover:bg-yellow-500">
              카카오톡 상담하기
            </button>
          </div>
          <div class="bg-white rounded-xl shadow-sm p-6 text-center">
            <div class="w-16 h-16 rounded-full bg-purple-100 flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
              </svg>
            </div>
            <h3 class="font-bold text-gray-900 mb-2">이메일 문의</h3>
            <p class="text-lg font-medium text-purple-600 mb-2">help@mintshop.com</p>
            <p class="text-sm text-gray-500">24시간 이내 답변</p>
            <button class="mt-3 px-4 py-2 bg-purple-600 text-white rounded-lg text-sm font-medium hover:bg-purple-700">
              이메일 보내기
            </button>
          </div>
        </div>

        <!-- Main Content -->
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <!-- FAQ Section -->
          <div class="lg:col-span-2">
            <div class="bg-white rounded-xl shadow-sm">
              <div class="p-4 border-b">
                <h3 class="font-bold text-gray-900">자주 묻는 질문</h3>
              </div>
              <div class="divide-y">
                <div v-for="(faq, index) in faqs" :key="index" class="p-4">
                  <button
                    @click="toggleFaq(index)"
                    class="w-full flex justify-between items-center text-left"
                  >
                    <div class="flex items-center">
                      <span class="text-teal-600 font-bold mr-3">Q</span>
                      <span class="font-medium text-gray-900">{{ faq.question }}</span>
                    </div>
                    <svg :class="['w-5 h-5 text-gray-400 transition-transform', openFaq === index && 'rotate-180']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                    </svg>
                  </button>
                  <div v-show="openFaq === index" class="mt-3 pl-8 text-gray-600 text-sm">
                    {{ faq.answer }}
                  </div>
                </div>
              </div>
              <div class="p-4 border-t bg-gray-50 text-center">
                <router-link to="/faq" class="text-teal-600 hover:underline text-sm font-medium">
                  더 많은 FAQ 보기 →
                </router-link>
              </div>
            </div>
          </div>

          <!-- Side Menu -->
          <div class="space-y-6">
            <!-- Quick Links -->
            <div class="bg-white rounded-xl shadow-sm p-4">
              <h3 class="font-bold text-gray-900 mb-4">바로가기</h3>
              <div class="space-y-2">
                <router-link to="/inquiry" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100">
                  <span class="text-sm font-medium text-gray-700">1:1 문의하기</span>
                  <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </router-link>
                <router-link to="/inquiry/list" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100">
                  <span class="text-sm font-medium text-gray-700">내 문의 내역</span>
                  <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </router-link>
                <router-link to="/notice" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100">
                  <span class="text-sm font-medium text-gray-700">공지사항</span>
                  <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </router-link>
                <router-link to="/policy/shipping" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100">
                  <span class="text-sm font-medium text-gray-700">배송 안내</span>
                  <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </router-link>
                <router-link to="/policy/return" class="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100">
                  <span class="text-sm font-medium text-gray-700">교환/반품 안내</span>
                  <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                  </svg>
                </router-link>
              </div>
            </div>

            <!-- Notice -->
            <div class="bg-white rounded-xl shadow-sm p-4">
              <h3 class="font-bold text-gray-900 mb-4">공지사항</h3>
              <div class="space-y-3">
                <div v-for="notice in notices" :key="notice.id" class="text-sm">
                  <router-link :to="`/notice/${notice.id}`" class="text-gray-700 hover:text-teal-600 line-clamp-1">
                    {{ notice.title }}
                  </router-link>
                  <div class="text-xs text-gray-400">{{ notice.date }}</div>
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
import { ref } from 'vue'
import Layout from '../components/Layout.vue'

const openFaq = ref(null)

const faqs = ref([
  { question: '주문 후 배송까지 얼마나 걸리나요?', answer: '주문 후 1-3일 이내에 발송되며, 발송 후 1-2일 이내에 도착합니다. 도서/산간 지역은 1-2일 추가 소요될 수 있습니다.' },
  { question: '주문 취소는 어떻게 하나요?', answer: '마이페이지 > 주문내역에서 배송 전 상품에 한해 취소가 가능합니다. 배송이 시작된 경우 고객센터로 문의해주세요.' },
  { question: '교환/반품 절차가 어떻게 되나요?', answer: '상품 수령 후 7일 이내에 마이페이지에서 교환/반품 신청이 가능합니다. 단순 변심의 경우 반품 배송비가 부과됩니다.' },
  { question: '포인트 적립률은 어떻게 되나요?', answer: '구매 금액의 1%가 포인트로 적립되며, 회원 등급에 따라 추가 적립 혜택이 제공됩니다.' },
  { question: '결제 수단은 무엇이 있나요?', answer: '신용카드, 체크카드, 무통장입금, 카카오페이, 네이버페이, 토스 등 다양한 결제 수단을 지원합니다.' }
])

const notices = ref([
  { id: 1, title: '[안내] 설 연휴 배송 안내', date: '2025-01-20' },
  { id: 2, title: '[이벤트] 신규회원 10% 할인 쿠폰', date: '2025-01-18' },
  { id: 3, title: '[공지] 개인정보처리방침 개정 안내', date: '2025-01-15' }
])

function toggleFaq(index) {
  openFaq.value = openFaq.value === index ? null : index
}
</script>
