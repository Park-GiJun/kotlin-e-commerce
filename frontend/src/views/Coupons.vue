<template>
  <Layout>
    <div class="min-h-full bg-gray-50 py-8">
      <div class="max-w-4xl mx-auto px-6">
        <!-- Header -->
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900">쿠폰함</h1>
          <p class="text-gray-600 mt-1">보유 쿠폰 {{ availableCoupons.length }}장</p>
        </div>

        <!-- Coupon Registration -->
        <div class="bg-white rounded-xl shadow-sm p-4 mb-6">
          <div class="flex gap-3">
            <input
              v-model="couponCode"
              type="text"
              placeholder="쿠폰 코드를 입력하세요"
              class="flex-1 border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-teal-500 focus:border-transparent"
            />
            <button @click="registerCoupon" class="px-6 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 font-medium">
              쿠폰 등록
            </button>
          </div>
        </div>

        <!-- Tabs -->
        <div class="flex gap-4 mb-6">
          <button
            @click="activeTab = 'available'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'available' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            사용 가능 ({{ availableCoupons.length }})
          </button>
          <button
            @click="activeTab = 'used'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'used' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            사용 완료
          </button>
          <button
            @click="activeTab = 'expired'"
            :class="['px-4 py-2 rounded-lg text-sm font-medium transition-colors', activeTab === 'expired' ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
          >
            기간 만료
          </button>
        </div>

        <!-- Coupon List -->
        <div class="space-y-4">
          <div v-for="coupon in displayedCoupons" :key="coupon.id" :class="['bg-white rounded-xl shadow-sm overflow-hidden', coupon.status !== 'available' && 'opacity-60']">
            <div class="flex">
              <!-- Left Side - Discount -->
              <div :class="['w-32 flex-shrink-0 flex flex-col items-center justify-center p-4', coupon.status === 'available' ? 'bg-teal-600' : 'bg-gray-400']">
                <div class="text-white text-2xl font-bold">
                  {{ coupon.discountType === 'percent' ? coupon.discountValue + '%' : formatPrice(coupon.discountValue) }}
                </div>
                <div class="text-teal-100 text-sm">
                  {{ coupon.discountType === 'percent' ? '할인' : '원 할인' }}
                </div>
              </div>
              <!-- Right Side - Info -->
              <div class="flex-1 p-4">
                <div class="flex justify-between items-start">
                  <div>
                    <h3 class="font-bold text-gray-900">{{ coupon.name }}</h3>
                    <p class="text-sm text-gray-500 mt-1">{{ coupon.description }}</p>
                    <div class="flex gap-2 mt-2">
                      <span class="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded">
                        {{ coupon.minOrderAmount > 0 ? formatPrice(coupon.minOrderAmount) + '원 이상 구매 시' : '금액 제한 없음' }}
                      </span>
                      <span v-if="coupon.maxDiscount" class="text-xs bg-gray-100 text-gray-600 px-2 py-1 rounded">
                        최대 {{ formatPrice(coupon.maxDiscount) }}원 할인
                      </span>
                    </div>
                  </div>
                  <button
                    v-if="coupon.status === 'available'"
                    @click="downloadCoupon(coupon)"
                    class="px-3 py-1 bg-teal-100 text-teal-700 rounded text-sm font-medium hover:bg-teal-200"
                  >
                    사용하기
                  </button>
                </div>
                <div class="mt-3 pt-3 border-t border-dashed flex justify-between items-center">
                  <span class="text-xs text-gray-400">{{ coupon.expireDate }} 까지</span>
                  <span v-if="coupon.status === 'used'" class="text-xs text-gray-500">사용일: {{ coupon.usedDate }}</span>
                  <span v-if="coupon.status === 'expired'" class="text-xs text-red-500">만료됨</span>
                </div>
              </div>
            </div>
          </div>

          <div v-if="displayedCoupons.length === 0" class="bg-white rounded-xl shadow-sm p-8 text-center text-gray-500">
            <svg class="w-16 h-16 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z" />
            </svg>
            <p>{{ activeTab === 'available' ? '사용 가능한 쿠폰이 없습니다.' : activeTab === 'used' ? '사용한 쿠폰이 없습니다.' : '만료된 쿠폰이 없습니다.' }}</p>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const router = useRouter()
const { success, error } = useAlert()

const activeTab = ref('available')
const couponCode = ref('')

const coupons = ref([
  { id: 1, name: '신규가입 환영 쿠폰', description: '첫 구매 시 사용 가능', discountType: 'percent', discountValue: 10, minOrderAmount: 20000, maxDiscount: 5000, expireDate: '2025-02-28', status: 'available' },
  { id: 2, name: '겨울 시즌 특별 할인', description: '전 상품 사용 가능', discountType: 'amount', discountValue: 3000, minOrderAmount: 30000, maxDiscount: null, expireDate: '2025-01-31', status: 'available' },
  { id: 3, name: 'VIP 전용 쿠폰', description: 'VIP 회원 전용 특별 혜택', discountType: 'percent', discountValue: 15, minOrderAmount: 50000, maxDiscount: 10000, expireDate: '2025-03-31', status: 'available' },
  { id: 4, name: '생일 축하 쿠폰', description: '생일 기념 특별 할인', discountType: 'amount', discountValue: 5000, minOrderAmount: 0, maxDiscount: null, expireDate: '2025-01-15', usedDate: '2025-01-10', status: 'used' },
  { id: 5, name: '첫 리뷰 작성 감사 쿠폰', description: '첫 리뷰 작성 감사 혜택', discountType: 'percent', discountValue: 5, minOrderAmount: 10000, maxDiscount: 2000, expireDate: '2024-12-31', status: 'expired' }
])

const availableCoupons = computed(() => coupons.value.filter(c => c.status === 'available'))

const displayedCoupons = computed(() => {
  return coupons.value.filter(c => c.status === activeTab.value)
})

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function registerCoupon() {
  if (!couponCode.value.trim()) {
    error('쿠폰 코드를 입력해주세요.')
    return
  }
  success('쿠폰이 등록되었습니다!')
  couponCode.value = ''
}

function downloadCoupon(coupon) {
  router.push('/products')
}
</script>
