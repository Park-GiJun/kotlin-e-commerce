<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-4xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">공지사항</h1>
          <p class="text-gray-600 mt-1">MintShop의 새로운 소식을 확인하세요</p>
        </div>

        <!-- Tabs -->
        <div class="flex gap-2 mb-6">
          <button
            v-for="tab in tabs"
            :key="tab.id"
            @click="activeTab = tab.id"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === tab.id ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            {{ tab.label }}
          </button>
        </div>

        <!-- Notice List -->
        <div class="bg-white rounded-xl shadow-sm overflow-hidden">
          <div class="divide-y">
            <div v-for="notice in filteredNotices" :key="notice.id" class="p-4 hover:bg-gray-50 cursor-pointer" @click="openNotice(notice)">
              <div class="flex items-start justify-between">
                <div class="flex-1">
                  <div class="flex items-center gap-2 mb-1">
                    <span :class="['px-2 py-0.5 text-xs font-medium rounded', getCategoryClass(notice.category)]">
                      {{ notice.categoryText }}
                    </span>
                    <span v-if="notice.isNew" class="px-2 py-0.5 bg-red-100 text-red-600 text-xs font-medium rounded">NEW</span>
                  </div>
                  <h3 class="font-medium text-gray-900">{{ notice.title }}</h3>
                  <p class="text-sm text-gray-500 mt-1">{{ notice.date }}</p>
                </div>
                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div class="flex justify-center mt-6 gap-1">
          <button class="px-3 py-2 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50" disabled>
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

        <!-- Modal -->
        <div v-if="selectedNotice" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4" @click.self="selectedNotice = null">
          <div class="bg-white rounded-xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
            <div class="p-6 border-b">
              <div class="flex items-center gap-2 mb-2">
                <span :class="['px-2 py-0.5 text-xs font-medium rounded', getCategoryClass(selectedNotice.category)]">
                  {{ selectedNotice.categoryText }}
                </span>
              </div>
              <h2 class="text-xl font-bold text-gray-900">{{ selectedNotice.title }}</h2>
              <p class="text-sm text-gray-500 mt-1">{{ selectedNotice.date }}</p>
            </div>
            <div class="p-6">
              <div class="prose prose-sm max-w-none text-gray-700 whitespace-pre-line">
                {{ selectedNotice.content }}
              </div>
            </div>
            <div class="p-4 border-t bg-gray-50 flex justify-end">
              <button @click="selectedNotice = null" class="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300">
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

const activeTab = ref('all')
const selectedNotice = ref(null)

const tabs = [
  { id: 'all', label: '전체' },
  { id: 'notice', label: '공지' },
  { id: 'event', label: '이벤트' },
  { id: 'update', label: '업데이트' }
]

const notices = ref([
  { id: 1, category: 'notice', categoryText: '공지', title: '[안내] 설 연휴 배송 일정 안내', date: '2025-01-20', isNew: true, content: '안녕하세요, MintShop입니다.\n\n설 연휴 기간 배송 일정을 안내드립니다.\n\n■ 연휴 기간: 2025년 1월 28일(화) ~ 1월 30일(목)\n■ 배송 일정\n- 1월 26일(일) 이전 결제 완료 건: 연휴 전 발송\n- 1월 27일(월) 이후 결제 건: 1월 31일(금)부터 순차 발송\n\n감사합니다.' },
  { id: 2, category: 'event', categoryText: '이벤트', title: '[이벤트] 신규회원 가입 시 10% 할인 쿠폰 증정!', date: '2025-01-18', isNew: true, content: '신규 회원 가입 시 즉시 사용 가능한 10% 할인 쿠폰을 드립니다!\n\n■ 이벤트 기간: 상시\n■ 쿠폰 유효기간: 발급일로부터 30일\n■ 사용 조건: 2만원 이상 구매 시\n\n지금 바로 가입하고 혜택을 받아보세요!' },
  { id: 3, category: 'notice', categoryText: '공지', title: '[공지] 개인정보처리방침 개정 안내', date: '2025-01-15', isNew: false, content: '개인정보처리방침이 개정되었습니다.\n\n■ 시행일: 2025년 2월 1일\n■ 주요 변경사항:\n- 개인정보 수집 항목 명확화\n- 제3자 제공 현황 업데이트\n\n자세한 내용은 개인정보처리방침 페이지에서 확인하실 수 있습니다.' },
  { id: 4, category: 'update', categoryText: '업데이트', title: '[업데이트] 앱 버전 2.5.0 출시 안내', date: '2025-01-12', isNew: false, content: 'MintShop 앱이 업데이트되었습니다.\n\n■ 버전: 2.5.0\n■ 주요 변경사항:\n- 상품 검색 기능 개선\n- 장바구니 UI 개선\n- 버그 수정\n\n최신 버전으로 업데이트하여 더 편리한 쇼핑을 즐겨보세요!' },
  { id: 5, category: 'event', categoryText: '이벤트', title: '[이벤트] 겨울 시즌 특가 세일 최대 50% OFF', date: '2025-01-10', isNew: false, content: '겨울 시즌 특가 세일을 진행합니다!\n\n■ 기간: 2025년 1월 10일 ~ 1월 31일\n■ 할인: 최대 50%\n■ 대상 상품: 겨울 의류, 잡화, 가전 등\n\n이 기회를 놓치지 마세요!' },
  { id: 6, category: 'notice', categoryText: '공지', title: '[공지] 고객센터 운영시간 변경 안내', date: '2025-01-08', isNew: false, content: '고객센터 운영시간이 변경됩니다.\n\n■ 변경일: 2025년 2월 1일부터\n■ 변경 전: 09:00 ~ 18:00\n■ 변경 후: 09:00 ~ 20:00\n\n더 나은 서비스를 제공하기 위해 노력하겠습니다.' }
])

const filteredNotices = computed(() => {
  if (activeTab.value === 'all') return notices.value
  return notices.value.filter(n => n.category === activeTab.value)
})

function getCategoryClass(category) {
  const classes = {
    notice: 'bg-blue-100 text-blue-700',
    event: 'bg-pink-100 text-pink-700',
    update: 'bg-green-100 text-green-700'
  }
  return classes[category] || 'bg-gray-100 text-gray-700'
}

function openNotice(notice) {
  selectedNotice.value = notice
}
</script>
