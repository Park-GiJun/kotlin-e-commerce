<template>
  <Layout>
    <!-- Hero Banner Carousel -->
    <div class="relative bg-gradient-to-br from-primary-600 via-primary-500 to-teal-400 overflow-hidden">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="relative py-16 md:py-24">
          <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 items-center">
            <div class="text-white">
              <div class="inline-block px-4 py-2 bg-white/20 rounded-full text-sm font-medium mb-4 backdrop-blur-sm">
                🎉 {{ authStore.user?.name }}님 환영합니다
              </div>
              <h1 class="text-4xl md:text-6xl font-extrabold mb-6 leading-tight">
                특별한 쇼핑,<br/>
                <span class="text-yellow-300">특별한 순간</span>
              </h1>
              <p class="text-xl text-white/90 mb-8">
                지금 바로 최대 50% 할인 혜택을 만나보세요
              </p>
              <div class="flex flex-wrap gap-4">
                <router-link to="/products" class="px-8 py-4 bg-white font-bold rounded-xl hover:scale-105 transition-transform shadow-xl" style="color: #0d9488;">
                  쇼핑 시작하기
                </router-link>
                <router-link to="/products" class="px-8 py-4 bg-white/10 text-white font-bold rounded-xl hover:bg-white/20 transition-colors backdrop-blur-sm border-2 border-white/30">
                  이벤트 보기
                </router-link>
              </div>
            </div>
            <div class="hidden lg:block">
              <div class="relative">
                <div class="absolute inset-0 bg-gradient-to-br from-yellow-300/20 to-pink-300/20 rounded-3xl rotate-6"></div>
                <div class="relative bg-white/10 backdrop-blur-lg rounded-3xl p-8 border border-white/20">
                  <div class="grid grid-cols-2 gap-4">
                    <div class="bg-white/20 rounded-2xl p-6 backdrop-blur-sm">
                      <div class="text-4xl mb-2">🔥</div>
                      <div class="text-white font-bold">HOT DEAL</div>
                      <div class="text-white/80 text-sm">최대 50% 할인</div>
                    </div>
                    <div class="bg-white/20 rounded-2xl p-6 backdrop-blur-sm">
                      <div class="text-4xl mb-2">🚚</div>
                      <div class="text-white font-bold">무료 배송</div>
                      <div class="text-white/80 text-sm">3만원 이상</div>
                    </div>
                    <div class="bg-white/20 rounded-2xl p-6 backdrop-blur-sm">
                      <div class="text-4xl mb-2">⚡</div>
                      <div class="text-white font-bold">빠른 배송</div>
                      <div class="text-white/80 text-sm">당일 발송</div>
                    </div>
                    <div class="bg-white/20 rounded-2xl p-6 backdrop-blur-sm">
                      <div class="text-4xl mb-2">💝</div>
                      <div class="text-white font-bold">신규 회원</div>
                      <div class="text-white/80 text-sm">첫 구매 혜택</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="absolute bottom-0 left-0 right-0 h-16 bg-gradient-to-t from-gray-50 to-transparent"></div>
    </div>

    <div class="bg-gray-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <!-- Event Cards -->
        <div class="mb-16">
          <div class="text-center mb-10">
            <h2 class="text-3xl font-extrabold text-gray-900 mb-2">🎯 진행중인 이벤트</h2>
            <p class="text-gray-600">놓치면 후회할 특별한 기회</p>
          </div>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <!-- Event 1 -->
            <div class="group relative bg-gradient-to-br from-red-500 to-pink-600 rounded-2xl overflow-hidden shadow-xl hover:shadow-2xl transition-all hover:scale-105">
              <div class="absolute inset-0 bg-black/10"></div>
              <div class="relative p-8 text-white">
                <div class="text-5xl mb-4">⏰</div>
                <div class="inline-block px-3 py-1 bg-white/20 rounded-full text-xs font-bold mb-3 backdrop-blur-sm">
                  24시간 한정
                </div>
                <h3 class="text-2xl font-extrabold mb-2">플래시 세일</h3>
                <p class="text-white/90 mb-4">최대 60% 할인</p>
                <div class="flex items-center text-sm">
                  <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-12a1 1 0 10-2 0v4a1 1 0 00.293.707l2.828 2.829a1 1 0 101.415-1.415L11 9.586V6z" clip-rule="evenodd" />
                  </svg>
                  <span>23:45:12 남음</span>
                </div>
              </div>
            </div>

            <!-- Event 2 -->
            <div class="group relative bg-gradient-to-br from-blue-500 to-cyan-600 rounded-2xl overflow-hidden shadow-xl hover:shadow-2xl transition-all hover:scale-105">
              <div class="absolute inset-0 bg-black/10"></div>
              <div class="relative p-8 text-white">
                <div class="text-5xl mb-4">🎁</div>
                <div class="inline-block px-3 py-1 bg-white/20 rounded-full text-xs font-bold mb-3 backdrop-blur-sm">
                  신규 회원
                </div>
                <h3 class="text-2xl font-extrabold mb-2">웰컴 쿠폰</h3>
                <p class="text-white/90 mb-4">10,000원 할인</p>
                <div class="flex items-center text-sm">
                  <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M2 6a2 2 0 012-2h12a2 2 0 012 2v2a2 2 0 100 4v2a2 2 0 01-2 2H4a2 2 0 01-2-2v-2a2 2 0 100-4V6z" />
                  </svg>
                  <span>첫 구매시 사용</span>
                </div>
              </div>
            </div>

            <!-- Event 3 -->
            <div class="group relative bg-gradient-to-br from-purple-500 to-indigo-600 rounded-2xl overflow-hidden shadow-xl hover:shadow-2xl transition-all hover:scale-105">
              <div class="absolute inset-0 bg-black/10"></div>
              <div class="relative p-8 text-white">
                <div class="text-5xl mb-4">🚚</div>
                <div class="inline-block px-3 py-1 bg-white/20 rounded-full text-xs font-bold mb-3 backdrop-blur-sm">
                  무료 배송
                </div>
                <h3 class="text-2xl font-extrabold mb-2">배송비 0원</h3>
                <p class="text-white/90 mb-4">30,000원 이상</p>
                <div class="flex items-center text-sm">
                  <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M8 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM15 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0z" />
                    <path d="M3 4a1 1 0 00-1 1v10a1 1 0 001 1h1.05a2.5 2.5 0 014.9 0H10a1 1 0 001-1V5a1 1 0 00-1-1H3zM14 7a1 1 0 00-1 1v6.05A2.5 2.5 0 0115.95 16H17a1 1 0 001-1v-5a1 1 0 00-.293-.707l-2-2A1 1 0 0015 7h-1z" />
                  </svg>
                  <span>전 상품 적용</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Product Showcase: New Arrivals -->
        <div class="mb-16">
          <div class="flex items-center justify-between mb-8">
            <div>
              <h2 class="text-3xl font-extrabold text-gray-900 flex items-center">
                <span class="text-4xl mr-3">✨</span>
                신상품
              </h2>
              <p class="text-gray-600 mt-1">새로 들어온 따끈따끈한 상품</p>
            </div>
            <router-link to="/products" class="flex items-center text-primary-600 font-bold hover:text-primary-700 group">
              전체보기
              <svg class="w-5 h-5 ml-1 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </router-link>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
            <ProductCard
              v-for="product in newArrivals"
              :key="product.productId"
              :product="product"
            />
          </div>
        </div>

        <!-- Mid Banner -->
        <div class="mb-16 relative rounded-3xl overflow-hidden shadow-2xl" style="background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);">
          <div class="absolute inset-0 bg-black/20"></div>
          <div class="relative px-8 py-16 text-center text-white">
            <div class="text-6xl mb-4">🎊</div>
            <h2 class="text-4xl font-extrabold mb-4">겨울 시즌 대전</h2>
            <p class="text-xl text-white/90 mb-8">모든 상품 최대 40% 할인</p>
            <router-link to="/products" class="inline-block px-8 py-4 bg-white text-purple-600 font-bold rounded-xl hover:scale-105 transition-transform shadow-xl">
              지금 쇼핑하기
            </router-link>
          </div>
        </div>

        <!-- Product Showcase: Best Sellers -->
        <div class="mb-16">
          <div class="flex items-center justify-between mb-8">
            <div>
              <h2 class="text-3xl font-extrabold text-gray-900 flex items-center">
                <span class="text-4xl mr-3">🔥</span>
                베스트셀러
              </h2>
              <p class="text-gray-600 mt-1">지금 가장 인기있는 상품</p>
            </div>
            <router-link to="/products" class="flex items-center text-primary-600 font-bold hover:text-primary-700 group">
              전체보기
              <svg class="w-5 h-5 ml-1 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </router-link>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
            <ProductCard
              v-for="product in bestSellers"
              :key="product.productId"
              :product="product"
            />
          </div>
        </div>

        <!-- Categories Showcase -->
        <div class="mb-16">
          <div class="text-center mb-10">
            <h2 class="text-3xl font-extrabold text-gray-900 mb-2">카테고리별 쇼핑</h2>
            <p class="text-gray-600">원하는 카테고리를 선택하세요</p>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
            <router-link
              v-for="category in categories"
              :key="category.name"
              to="/products"
              class="group bg-white rounded-2xl p-6 shadow-md hover:shadow-xl transition-all hover:scale-105 text-center">
              <div class="text-4xl mb-3 group-hover:scale-110 transition-transform">{{ category.icon }}</div>
              <h3 class="font-bold text-gray-900 mb-1">{{ category.name }}</h3>
              <p class="text-sm text-gray-500">{{ category.count }}개</p>
            </router-link>
          </div>
        </div>

        <!-- Product Showcase: Recommended -->
        <div class="mb-16">
          <div class="flex items-center justify-between mb-8">
            <div>
              <h2 class="text-3xl font-extrabold text-gray-900 flex items-center">
                <span class="text-4xl mr-3">💎</span>
                {{ authStore.user?.name }}님을 위한 추천
              </h2>
              <p class="text-gray-600 mt-1">맞춤형 상품을 만나보세요</p>
            </div>
            <router-link to="/products" class="flex items-center text-primary-600 font-bold hover:text-primary-700 group">
              전체보기
              <svg class="w-5 h-5 ml-1 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </router-link>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
            <ProductCard
              v-for="product in recommended"
              :key="product.productId"
              :product="product"
            />
          </div>
        </div>

        <!-- Newsletter Section -->
        <div class="relative rounded-3xl overflow-hidden shadow-2xl bg-gradient-to-r from-primary-600 to-teal-500">
          <div class="absolute inset-0 bg-black/10"></div>
          <div class="relative px-8 py-12 text-center text-white">
            <div class="text-5xl mb-4">📧</div>
            <h2 class="text-3xl font-extrabold mb-3">특별한 혜택을 놓치지 마세요</h2>
            <p class="text-white/90 mb-8 max-w-2xl mx-auto">
              이메일을 등록하고 최신 할인 정보와 신상품 소식을 가장 먼저 받아보세요
            </p>
            <div class="max-w-md mx-auto flex gap-3">
              <input
                type="email"
                placeholder="이메일 주소를 입력하세요"
                class="flex-1 px-6 py-4 rounded-xl text-gray-900 focus:outline-none focus:ring-4 focus:ring-white/50"
              />
              <button class="px-8 py-4 bg-white text-primary-600 font-bold rounded-xl hover:scale-105 transition-transform shadow-xl whitespace-nowrap">
                구독하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useAuthStore } from '../stores/auth'
import { productAPI } from '../api/product'
import Layout from '../components/Layout.vue'
import ProductCard from '../components/ProductCard.vue'

const authStore = useAuthStore()
const products = ref([])

const categories = [
  { name: '전자기기', icon: '📱', count: 120 },
  { name: '패션', icon: '👔', count: 85 },
  { name: '식품', icon: '🍎', count: 64 },
  { name: '생활용품', icon: '🏠', count: 92 },
  { name: '도서', icon: '📚', count: 156 },
  { name: '스포츠', icon: '⚽', count: 43 },
]

const newArrivals = computed(() => {
  return products.value.slice(0, 5).map(product => ({
    ...product,
    imageUrl: null,
    discount: Math.floor(Math.random() * 30 + 10),
    reviewCount: Math.floor(Math.random() * 500)
  }))
})

const bestSellers = computed(() => {
  return products.value.slice(2, 7).map(product => ({
    ...product,
    imageUrl: null,
    discount: Math.floor(Math.random() * 40 + 20),
    reviewCount: Math.floor(Math.random() * 1000 + 500)
  }))
})

const recommended = computed(() => {
  return products.value.slice(4, 9).map(product => ({
    ...product,
    imageUrl: null,
    discount: Math.floor(Math.random() * 25 + 15),
    reviewCount: Math.floor(Math.random() * 300)
  }))
})

onMounted(async () => {
  try {
    const productsRes = await productAPI.getAll(0, 10)
    const pageResponse = productsRes.data
    if (pageResponse) {
      products.value = pageResponse.content || []
    }
  } catch (error) {
    console.error('상품 로드 실패:', error)
  }
})
</script>
