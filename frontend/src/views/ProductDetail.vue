<template>
  <Layout>
    <div class="h-full flex flex-col bg-gray-50">
      <!-- Loading -->
      <div v-if="loading" class="flex-1 flex items-center justify-center">
        <div class="text-center">
          <div class="inline-block animate-spin rounded-full h-10 w-10 border-4 border-teal-500 border-t-transparent"></div>
          <p class="mt-3 text-gray-600">로딩 중...</p>
        </div>
      </div>

      <!-- Product Content -->
      <div v-else-if="product" class="flex-1 overflow-y-auto">
        <div class="max-w-7xl mx-auto px-6 py-6">
          <!-- Breadcrumb -->
          <div class="mb-4 text-sm text-gray-500 flex items-center gap-2">
            <router-link to="/dashboard" class="hover:text-teal-600">홈</router-link>
            <span>/</span>
            <router-link to="/products" class="hover:text-teal-600">전체 상품</router-link>
            <span>/</span>
            <span class="text-gray-900">{{ product.productName }}</span>
          </div>

          <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <!-- Left: Product Image -->
            <div class="bg-white rounded-xl shadow-sm p-6">
              <div class="aspect-square bg-gray-100 rounded-lg flex items-center justify-center mb-4 relative">
                <svg :class="['w-32 h-32 text-gray-300', !product.isOnSale && 'opacity-50']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                </svg>
                <!-- 품절 표시 -->
                <div v-if="!product.isOnSale" class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 rounded-lg">
                  <span class="bg-gray-800 text-white px-6 py-3 rounded-lg font-bold text-xl">품절</span>
                </div>
              </div>
              <div class="grid grid-cols-4 gap-2">
                <div v-for="i in 4" :key="i" class="aspect-square bg-gray-100 rounded-lg cursor-pointer hover:ring-2 hover:ring-teal-500 transition-all"></div>
              </div>
            </div>

            <!-- Right: Product Info -->
            <div class="space-y-6">
              <!-- Basic Info -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <div class="text-sm text-gray-500 mb-2">{{ getCategoryPath(product) }}</div>
                <h1 class="text-2xl font-bold text-gray-900 mb-4">{{ product.productName }}</h1>

                <!-- Rating -->
                <div class="flex items-center gap-3 mb-4">
                  <div class="flex text-yellow-400">
                    <svg v-for="i in 5" :key="i" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                    </svg>
                  </div>
                  <span class="text-sm text-gray-600">4.5 (247개 후기)</span>
                </div>

                <!-- Price -->
                <div class="border-t border-b py-4 mb-4">
                  <div class="text-3xl font-bold text-rose-600">{{ formatPrice(product.productPrice) }}</div>
                  <div class="flex items-center gap-2 mt-2 text-sm">
                    <span class="text-green-600 font-medium">무료 배송</span>
                    <span class="text-gray-400">3만원 이상 구매 시</span>
                  </div>
                </div>

                <!-- Quantity & Buttons -->
                <div class="space-y-4">
                  <div class="flex items-center gap-4">
                    <span class="text-sm font-medium text-gray-700">수량</span>
                    <div class="flex items-center gap-2">
                      <button @click="quantity > 1 && quantity--" class="w-8 h-8 rounded-lg border border-gray-300 hover:bg-gray-50 flex items-center justify-center">-</button>
                      <input v-model.number="quantity" type="number" min="1" class="w-16 h-8 text-center border border-gray-300 rounded-lg" />
                      <button @click="quantity++" class="w-8 h-8 rounded-lg border border-gray-300 hover:bg-gray-50 flex items-center justify-center">+</button>
                    </div>
                    <div class="text-sm text-gray-500">총액: <span class="font-bold text-gray-900">{{ formatPrice(product.productPrice * quantity) }}</span></div>
                  </div>

                  <div class="flex gap-3">
                    <button
                      @click="addToCart"
                      :disabled="isAddingToCart || !product.isOnSale"
                      class="flex-1 py-3 rounded-lg font-bold text-gray-900 bg-yellow-400 hover:bg-yellow-500 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      <span v-if="!product.isOnSale">품절된 상품입니다</span>
                      <span v-else>{{ isAddingToCart ? '담는 중...' : '장바구니 담기' }}</span>
                    </button>
                    <button
                      @click="buyNow"
                      :disabled="isAddingToCart || !product.isOnSale"
                      class="flex-1 py-3 rounded-lg font-bold text-white bg-teal-600 hover:bg-teal-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      <span v-if="!product.isOnSale">구매 불가</span>
                      <span v-else>{{ isAddingToCart ? '처리 중...' : '바로 구매' }}</span>
                    </button>
                  </div>
                </div>
              </div>

              <!-- Features -->
              <div class="bg-white rounded-xl shadow-sm p-6">
                <div class="grid grid-cols-3 gap-4 text-center">
                  <div class="p-3 bg-gray-50 rounded-lg">
                    <svg class="w-6 h-6 mx-auto text-teal-600 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                    </svg>
                    <div class="text-xs text-gray-700">안전 결제</div>
                  </div>
                  <div class="p-3 bg-gray-50 rounded-lg">
                    <svg class="w-6 h-6 mx-auto text-teal-600 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                    </svg>
                    <div class="text-xs text-gray-700">7일 반품</div>
                  </div>
                  <div class="p-3 bg-gray-50 rounded-lg">
                    <svg class="w-6 h-6 mx-auto text-teal-600 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                    </svg>
                    <div class="text-xs text-gray-700">정품 보증</div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Tabs: Description & Reviews -->
          <div class="mt-8 bg-white rounded-xl shadow-sm overflow-hidden">
            <div class="flex border-b">
              <button
                v-for="tab in tabs"
                :key="tab.id"
                @click="activeTab = tab.id"
                :class="['flex-1 py-3 text-sm font-medium transition-colors', activeTab === tab.id ? 'text-teal-600 border-b-2 border-teal-600 bg-teal-50' : 'text-gray-600 hover:bg-gray-50']"
              >
                {{ tab.label }}
              </button>
            </div>
            <div class="p-6">
              <!-- Description -->
              <div v-show="activeTab === 'description'" class="space-y-4">
                <h3 class="font-bold text-gray-900">상품 설명</h3>
                <p class="text-gray-600">{{ product.productName }}은(는) 고객 만족을 위해 엄선된 고품질 상품입니다.</p>
                <div class="grid grid-cols-2 gap-4 mt-4">
                  <div class="bg-gray-50 p-3 rounded-lg">
                    <div class="text-xs text-gray-500">카테고리</div>
                    <div class="font-medium">{{ getCategoryPath(product) }}</div>
                  </div>
                  <div class="bg-gray-50 p-3 rounded-lg">
                    <div class="text-xs text-gray-500">브랜드</div>
                    <div class="font-medium">MintShop</div>
                  </div>
                </div>
              </div>
              <!-- Reviews -->
              <div v-show="activeTab === 'reviews'" class="space-y-4">
                <div class="flex items-center gap-4 mb-6">
                  <div class="text-4xl font-bold">4.5</div>
                  <div>
                    <div class="flex text-yellow-400">
                      <svg v-for="i in 5" :key="i" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                      </svg>
                    </div>
                    <div class="text-sm text-gray-500">247개 후기</div>
                  </div>
                </div>
                <div v-for="review in dummyReviews" :key="review.id" class="border-b pb-4">
                  <div class="flex items-center gap-2 mb-2">
                    <div class="w-8 h-8 rounded-full bg-teal-100 flex items-center justify-center text-teal-700 font-bold text-sm">{{ review.author[0] }}</div>
                    <span class="font-medium">{{ review.author }}</span>
                    <div class="flex text-yellow-400">
                      <svg v-for="i in review.rating" :key="i" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                      </svg>
                    </div>
                    <span class="text-xs text-gray-500">{{ review.date }}</span>
                  </div>
                  <p class="text-gray-600 text-sm">{{ review.content }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Not Found -->
      <div v-else class="flex-1 flex items-center justify-center">
        <div class="text-center">
          <svg class="w-20 h-20 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
          </svg>
          <h3 class="text-lg font-medium text-gray-900 mb-2">상품을 찾을 수 없습니다</h3>
          <router-link to="/products" class="text-teal-600 hover:underline">쇼핑 계속하기</router-link>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { productAPI } from '../api/product'
import { useCartStore } from '../stores/cart'
import { useAuthStore } from '../stores/auth'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, error, warning } = useAlert()

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()
const authStore = useAuthStore()
const product = ref(null)
const loading = ref(true)
const quantity = ref(1)
const activeTab = ref('description')
const isAddingToCart = ref(false)

const tabs = [
  { id: 'description', label: '상품 정보' },
  { id: 'reviews', label: '후기' }
]

const dummyReviews = [
  { id: 1, author: '김**', rating: 5, date: '2025-01-10', content: '정말 좋은 상품이에요! 품질이 뛰어나고 배송도 빨랐습니다.' },
  { id: 2, author: '이**', rating: 4, date: '2025-01-08', content: '가성비 좋아요. 추천합니다.' },
  { id: 3, author: '박**', rating: 5, date: '2025-01-05', content: '선물용으로 샀는데 포장도 예쁘고 기대 이상이에요!' }
]

onMounted(async () => {
  try {
    loading.value = true
    const res = await productAPI.getById(route.params.id)
    product.value = res.data
  } catch (error) {
    console.error('Failed to load product:', error)
  } finally {
    loading.value = false
  }
})

function getCategoryPath(product) {
  const parts = [product.largeClassName]
  if (product.mediumClassName) parts.push(product.mediumClassName)
  if (product.smallClassName) parts.push(product.smallClassName)
  return parts.join(' > ')
}

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR') + '원'
}

async function addToCart() {
  if (!authStore.isLoggedIn) {
    warning('로그인이 필요합니다.')
    router.push('/login')
    return
  }
  isAddingToCart.value = true
  try {
    await cartStore.addToCart({ id: product.value.productId, productId: product.value.productId }, quantity.value)
    success(`${quantity.value}개 상품을 장바구니에 담았습니다!`)
  } catch (e) {
    error('장바구니 담기 실패: ' + (e.response?.data?.message || e.message))
  } finally {
    isAddingToCart.value = false
  }
}

async function buyNow() {
  if (!authStore.isLoggedIn) {
    warning('로그인이 필요합니다.')
    router.push('/login')
    return
  }
  isAddingToCart.value = true
  try {
    await cartStore.addToCart({ id: product.value.productId, productId: product.value.productId }, quantity.value)
    router.push('/checkout')
  } catch (e) {
    error('실패: ' + (e.response?.data?.message || e.message))
  } finally {
    isAddingToCart.value = false
  }
}
</script>
