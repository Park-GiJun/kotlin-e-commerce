<template>
  <Layout>
    <div v-if="loading" class="max-w-7xl mx-auto px-4 py-12 text-center">
      <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-primary-500 border-t-transparent"></div>
      <p class="mt-4 text-gray-600">로딩 중...</p>
    </div>

    <div v-else-if="product" class="max-w-7xl mx-auto px-4 py-6">
      <!-- Breadcrumb -->
      <div class="mb-4 text-sm text-gray-600">
        <router-link to="/dashboard" class="hover:text-primary-600">홈</router-link>
        <span class="mx-2">/</span>
        <router-link to="/products" class="hover:text-primary-600">전체 상품</router-link>
        <span class="mx-2">/</span>
        <span class="text-gray-900">{{ product.productName }}</span>
      </div>

      <!-- Product Main Section -->
      <div class="bg-white rounded-lg shadow-lg overflow-hidden mb-6">
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 p-6 lg:p-8">
          <!-- Product Images -->
          <div class="space-y-4">
            <div class="aspect-square bg-gray-100 rounded-lg flex items-center justify-center overflow-hidden border-2 border-gray-200">
              <img
                v-if="product.imageUrl"
                :src="product.imageUrl"
                :alt="product.productName"
                class="w-full h-full object-cover"
              />
              <div v-else class="text-gray-300">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1" stroke="currentColor" class="w-48 h-48">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z" />
                </svg>
              </div>
            </div>
            <!-- Thumbnail images (dummy) -->
            <div class="grid grid-cols-4 gap-2">
              <div v-for="i in 4" :key="i" class="aspect-square bg-gray-100 rounded-lg border-2 border-gray-200 hover:border-primary-500 cursor-pointer transition-colors"></div>
            </div>
          </div>

          <!-- Product Info -->
          <div class="space-y-6">
            <div>
              <div class="text-sm text-gray-600 mb-2">
                {{ getCategoryPath(product) }}
              </div>
              <h1 class="text-3xl font-bold text-gray-900 mb-3">
                {{ product.productName }}
              </h1>

              <!-- Rating -->
              <div class="flex items-center gap-3 mb-4">
                <div class="flex items-center">
                  <div class="flex text-yellow-400">
                    <svg v-for="i in 5" :key="i" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                    </svg>
                  </div>
                  <span class="ml-2 text-sm font-medium text-gray-700">4.5</span>
                </div>
                <span class="text-sm text-gray-500">{{ dummyReviewCount }}개 후기</span>
              </div>
            </div>

            <!-- Price -->
            <div class="border-t border-b py-6">
              <div class="flex items-baseline gap-3 mb-2">
                <span class="text-4xl font-bold text-red-600">
                  {{ formatPrice(Number(product.productPrice)) }}원
                </span>
              </div>
              <div class="flex items-center gap-2 text-sm">
                <span class="text-green-600 font-semibold">무료 배송</span>
                <span class="text-gray-500">• 30,000원 이상 구매 시</span>
              </div>
            </div>

            <!-- Stock Info -->
            <div class="space-y-3">
              <div class="flex items-center gap-2 text-sm">
                <span class="text-gray-700 font-medium">재고 상태:</span>
                <span class="text-green-600 font-semibold">재고 있음</span>
              </div>
              <div class="flex items-center gap-2 text-sm">
                <span class="text-gray-700 font-medium">배송:</span>
                <span class="text-gray-900">오늘 주문 시 내일 도착 예정</span>
              </div>
            </div>

            <!-- Quantity Selector -->
            <div class="space-y-3">
              <label class="block text-sm font-medium text-gray-700">수량</label>
              <div class="flex items-center gap-3">
                <button
                  @click="decreaseQuantity"
                  class="w-10 h-10 rounded-lg border-2 border-gray-300 hover:border-gray-400 flex items-center justify-center transition-colors"
                >
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                  </svg>
                </button>
                <input
                  v-model.number="quantity"
                  type="number"
                  min="1"
                  class="w-20 h-10 text-center border-2 border-gray-300 rounded-lg font-semibold"
                />
                <button
                  @click="increaseQuantity"
                  class="w-10 h-10 rounded-lg border-2 border-gray-300 hover:border-gray-400 flex items-center justify-center transition-colors"
                >
                  <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                  </svg>
                </button>
              </div>
            </div>

            <!-- Total Price -->
            <div class="bg-gray-50 rounded-lg p-4">
              <div class="flex items-center justify-between text-lg">
                <span class="font-medium text-gray-700">총 금액:</span>
                <span class="text-2xl font-bold text-primary-600">
                  {{ formatPrice(Number(product.productPrice) * quantity) }}원
                </span>
              </div>
            </div>

            <!-- Action Buttons -->
            <div class="space-y-3">
              <button
                @click="addToCart"
                class="w-full py-3 rounded-lg font-bold text-white transition-all hover:opacity-90 shadow-md"
                style="background-color: #fbbf24;"
              >
                <span class="flex items-center justify-center gap-2">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                  </svg>
                  장바구니에 담기
                </span>
              </button>
              <button
                @click="buyNow"
                class="w-full py-3 rounded-lg font-bold text-white transition-all hover:opacity-90 shadow-md"
                style="background-color: #f97316;"
              >
                지금 구매하기
              </button>
            </div>

            <!-- Features -->
            <div class="border-t pt-6 space-y-3">
              <div class="flex items-start gap-3 text-sm">
                <svg class="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                </svg>
                <span class="text-gray-700">안전한 결제 시스템</span>
              </div>
              <div class="flex items-start gap-3 text-sm">
                <svg class="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                </svg>
                <span class="text-gray-700">7일 이내 무료 반품</span>
              </div>
              <div class="flex items-start gap-3 text-sm">
                <svg class="w-5 h-5 text-green-600 flex-shrink-0 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                </svg>
                <span class="text-gray-700">정품 보증</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Product Details & Reviews Tabs -->
      <div class="bg-white rounded-lg shadow-lg overflow-hidden">
        <!-- Tab Headers -->
        <div class="border-b">
          <div class="flex">
            <button
              v-for="tab in tabs"
              :key="tab.id"
              @click="activeTab = tab.id"
              :class="[
                'flex-1 py-4 px-6 font-semibold transition-colors',
                activeTab === tab.id
                  ? 'text-primary-600 border-b-2 border-primary-600 bg-primary-50'
                  : 'text-gray-600 hover:text-gray-900 hover:bg-gray-50'
              ]"
            >
              {{ tab.label }}
            </button>
          </div>
        </div>

        <!-- Tab Content -->
        <div class="p-6 lg:p-8">
          <!-- Product Description Tab -->
          <div v-show="activeTab === 'description'" class="prose max-w-none">
            <h3 class="text-2xl font-bold text-gray-900 mb-4">상품 설명</h3>
            <div class="space-y-4 text-gray-700 leading-relaxed">
              <p>{{ product.productName }}은(는) 최고 품질의 제품으로, 고객님의 만족을 위해 엄선된 상품입니다.</p>

              <h4 class="text-lg font-semibold text-gray-900 mt-6 mb-3">주요 특징</h4>
              <ul class="list-disc list-inside space-y-2">
                <li>뛰어난 품질과 내구성</li>
                <li>세련된 디자인</li>
                <li>사용하기 편리한 구조</li>
                <li>안전성 테스트 완료</li>
                <li>친환경 소재 사용</li>
              </ul>

              <h4 class="text-lg font-semibold text-gray-900 mt-6 mb-3">제품 사양</h4>
              <table class="min-w-full border">
                <tbody class="divide-y">
                  <tr>
                    <td class="px-4 py-3 bg-gray-50 font-medium text-gray-900">카테고리</td>
                    <td class="px-4 py-3 text-gray-700">{{ getCategoryPath(product) }}</td>
                  </tr>
                  <tr>
                    <td class="px-4 py-3 bg-gray-50 font-medium text-gray-900">브랜드</td>
                    <td class="px-4 py-3 text-gray-700">MintShop</td>
                  </tr>
                  <tr>
                    <td class="px-4 py-3 bg-gray-50 font-medium text-gray-900">원산지</td>
                    <td class="px-4 py-3 text-gray-700">대한민국</td>
                  </tr>
                  <tr>
                    <td class="px-4 py-3 bg-gray-50 font-medium text-gray-900">제조사</td>
                    <td class="px-4 py-3 text-gray-700">MintShop Co.</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Reviews Tab -->
          <div v-show="activeTab === 'reviews'">
            <div class="mb-6">
              <h3 class="text-2xl font-bold text-gray-900 mb-4">고객 후기</h3>
              <div class="flex items-center gap-6 bg-gray-50 rounded-lg p-6">
                <div class="text-center">
                  <div class="text-5xl font-bold text-gray-900 mb-2">4.5</div>
                  <div class="flex text-yellow-400 mb-2">
                    <svg v-for="i in 5" :key="i" class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
                      <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                    </svg>
                  </div>
                  <div class="text-sm text-gray-600">{{ dummyReviewCount }}개 후기</div>
                </div>
                <div class="flex-1 space-y-2">
                  <div v-for="star in [5,4,3,2,1]" :key="star" class="flex items-center gap-3">
                    <span class="text-sm text-gray-600 w-12">{{ star }}점</span>
                    <div class="flex-1 bg-gray-200 rounded-full h-2">
                      <div
                        class="bg-yellow-400 h-2 rounded-full"
                        :style="{ width: getReviewPercentage(star) + '%' }"
                      ></div>
                    </div>
                    <span class="text-sm text-gray-600 w-12 text-right">{{ getReviewPercentage(star) }}%</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Review List -->
            <div class="space-y-6">
              <div v-for="review in dummyReviews" :key="review.id" class="border-b pb-6">
                <div class="flex items-start gap-4">
                  <div class="w-12 h-12 rounded-full bg-primary-100 flex items-center justify-center flex-shrink-0">
                    <span class="text-primary-600 font-bold">{{ review.author[0] }}</span>
                  </div>
                  <div class="flex-1">
                    <div class="flex items-center gap-3 mb-2">
                      <span class="font-semibold text-gray-900">{{ review.author }}</span>
                      <div class="flex text-yellow-400">
                        <svg v-for="i in review.rating" :key="i" class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                          <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                        </svg>
                      </div>
                      <span class="text-sm text-gray-500">{{ review.date }}</span>
                    </div>
                    <p class="text-gray-700 leading-relaxed">{{ review.content }}</p>
                    <div v-if="review.verified" class="mt-2">
                      <span class="inline-flex items-center gap-1 text-xs text-green-600 font-medium">
                        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20">
                          <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                        </svg>
                        구매 확인됨
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="max-w-7xl mx-auto px-4 py-12 text-center">
      <svg class="w-24 h-24 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
      </svg>
      <h3 class="text-xl font-medium text-gray-900 mb-2">상품을 찾을 수 없습니다</h3>
      <router-link to="/products" class="text-primary-600 hover:underline">쇼핑 계속하기</router-link>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { productAPI } from '../api/product'
import Layout from '../components/Layout.vue'

const route = useRoute()
const product = ref(null)
const loading = ref(true)
const quantity = ref(1)
const activeTab = ref('description')

const tabs = [
  { id: 'description', label: '상품 설명' },
  { id: 'reviews', label: '고객 후기' }
]

const dummyReviewCount = 247

const dummyReviews = [
  {
    id: 1,
    author: '김민수',
    rating: 5,
    date: '2025-01-10',
    content: '정말 만족스러운 제품입니다! 품질도 좋고 배송도 빨라서 감사합니다. 다음에도 재구매 의향 있습니다.',
    verified: true
  },
  {
    id: 2,
    author: '이지은',
    rating: 4,
    date: '2025-01-08',
    content: '가격 대비 훌륭한 제품이에요. 기대했던 것보다 더 좋네요. 다만 배송이 조금 늦어져서 별 하나 뺐습니다.',
    verified: true
  },
  {
    id: 3,
    author: '박철수',
    rating: 5,
    date: '2025-01-05',
    content: '가족 선물로 샀는데 너무 좋아해요. 포장도 깔끔하고 제품 상태도 완벽했습니다. 강력 추천!',
    verified: true
  },
  {
    id: 4,
    author: '정수진',
    rating: 5,
    date: '2024-12-28',
    content: '오랜만에 이렇게 만족스러운 쇼핑이었어요. 상품 설명 그대로고 품질도 우수합니다.',
    verified: false
  },
  {
    id: 5,
    author: '최동욱',
    rating: 4,
    date: '2024-12-20',
    content: '전반적으로 만족합니다. 가성비가 좋은 제품인 것 같아요.',
    verified: true
  }
]

onMounted(async () => {
  await loadProduct()
})

async function loadProduct() {
  try {
    loading.value = true
    const productId = route.params.id
    console.log('Loading product with ID:', productId)

    const res = await productAPI.getById(productId)
    console.log('API Response:', res)

    // productAPI.getById는 response.data를 반환하므로 res는 ApiResponse 객체
    // ApiResponse = { success: true, data: ProductModel, message: null }
    product.value = res.data
    console.log('Product loaded:', product.value)
  } catch (error) {
    console.error('상품 로드 실패:', error)
    alert('상품 정보를 불러오는데 실패했습니다: ' + (error.response?.data?.message || error.message))
  } finally {
    loading.value = false
  }
}

function getCategoryPath(product) {
  const parts = [product.largeClassNAme]
  if (product.mediumClassName) parts.push(product.mediumClassName)
  if (product.smallClassName) parts.push(product.smallClassName)
  return parts.join(' > ')
}

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR')
}

function increaseQuantity() {
  quantity.value++
}

function decreaseQuantity() {
  if (quantity.value > 1) {
    quantity.value--
  }
}

function addToCart() {
  alert(`${quantity.value}개를 장바구니에 담았습니다!\n\n(장바구니 기능은 추후 구현 예정입니다)`)
}

function buyNow() {
  alert(`${quantity.value}개 구매하기\n총 ${formatPrice(Number(product.value.productPrice) * quantity.value)}원\n\n(구매 기능은 추후 구현 예정입니다)`)
}

function getReviewPercentage(star) {
  const percentages = {
    5: 65,
    4: 25,
    3: 7,
    2: 2,
    1: 1
  }
  return percentages[star] || 0
}
</script>
