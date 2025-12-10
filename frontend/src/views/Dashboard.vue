<template>
  <Layout>
    <div class="h-full flex flex-col">
      <!-- Hero Section -->
      <div class="bg-gradient-to-r from-teal-600 to-cyan-600 px-6 py-8 flex-shrink-0">
        <div class="max-w-7xl mx-auto">
          <div class="flex items-center justify-between">
            <div class="text-white">
              <p class="text-teal-100 text-sm mb-1">안녕하세요, {{ authStore.user?.name || '고객' }}님</p>
              <h1 class="text-3xl font-bold mb-2">특별한 상품을 만나보세요</h1>
              <p class="text-teal-100">최대 50% 할인 진행 중</p>
            </div>
            <div class="hidden md:flex gap-3">
              <div class="bg-white/20 backdrop-blur rounded-lg px-4 py-3 text-center">
                <div class="text-2xl font-bold text-white">50%</div>
                <div class="text-xs text-teal-100">최대 할인</div>
              </div>
              <div class="bg-white/20 backdrop-blur rounded-lg px-4 py-3 text-center">
                <div class="text-2xl font-bold text-white">무료</div>
                <div class="text-xs text-teal-100">3만원 이상 배송</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex-1 overflow-y-auto bg-gray-50">
        <div class="max-w-7xl mx-auto px-6 py-6">
          <!-- Quick Categories -->
          <div class="grid grid-cols-6 gap-3 mb-6">
            <router-link
              v-for="category in categories"
              :key="category.name"
              to="/products"
              class="bg-white rounded-xl p-3 shadow-sm hover:shadow-md transition-all hover:scale-105 text-center group"
            >
              <div class="text-2xl mb-1 group-hover:scale-110 transition-transform">{{ category.icon }}</div>
              <div class="text-xs font-medium text-gray-700">{{ category.name }}</div>
            </router-link>
          </div>

          <!-- Products Grid -->
          <div class="grid grid-cols-2 lg:grid-cols-3 gap-6">
            <!-- New Arrivals -->
            <div class="bg-white rounded-xl shadow-sm overflow-hidden">
              <div class="px-4 py-3 border-b flex items-center justify-between">
                <h2 class="font-bold text-gray-900 flex items-center gap-2">
                  <span class="text-lg">신상품</span>
                </h2>
                <router-link to="/products" class="text-teal-600 text-sm hover:underline">전체보기</router-link>
              </div>
              <div class="divide-y max-h-64 overflow-y-auto">
                <router-link
                  v-for="product in newArrivals"
                  :key="product.productId"
                  :to="`/products/${product.productId}`"
                  class="flex items-center gap-3 p-3 hover:bg-gray-50 transition-colors"
                >
                  <div class="w-12 h-12 bg-gray-100 rounded-lg flex items-center justify-center flex-shrink-0">
                    <svg class="w-6 h-6 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="font-medium text-gray-900 text-sm truncate">{{ product.productName }}</div>
                    <div class="text-teal-600 font-bold text-sm">{{ formatPrice(product.productPrice) }}</div>
                  </div>
                  <span class="text-xs bg-teal-100 text-teal-700 px-2 py-0.5 rounded font-medium">신규</span>
                </router-link>
              </div>
            </div>

            <!-- Best Sellers -->
            <div class="bg-white rounded-xl shadow-sm overflow-hidden">
              <div class="px-4 py-3 border-b flex items-center justify-between">
                <h2 class="font-bold text-gray-900 flex items-center gap-2">
                  <span class="text-lg">베스트셀러</span>
                </h2>
                <router-link to="/products" class="text-teal-600 text-sm hover:underline">전체보기</router-link>
              </div>
              <div class="divide-y max-h-64 overflow-y-auto">
                <router-link
                  v-for="(product, index) in bestSellers"
                  :key="product.productId"
                  :to="`/products/${product.productId}`"
                  class="flex items-center gap-3 p-3 hover:bg-gray-50 transition-colors"
                >
                  <div class="w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0 font-bold text-sm"
                    :class="index === 0 ? 'bg-yellow-100 text-yellow-700' : index === 1 ? 'bg-gray-100 text-gray-600' : index === 2 ? 'bg-orange-100 text-orange-700' : 'bg-gray-50 text-gray-500'">
                    {{ index + 1 }}
                  </div>
                  <div class="flex-1 min-w-0">
                    <div class="font-medium text-gray-900 text-sm truncate">{{ product.productName }}</div>
                    <div class="text-teal-600 font-bold text-sm">{{ formatPrice(product.productPrice) }}</div>
                  </div>
                  <div class="text-xs text-gray-500">{{ Math.floor(Math.random() * 500 + 100) }}개 판매</div>
                </router-link>
              </div>
            </div>

            <!-- Promotions -->
            <div class="space-y-4">
              <div class="bg-gradient-to-br from-rose-500 to-pink-600 rounded-xl p-4 text-white">
                <div class="text-2xl mb-1">타임세일</div>
                <div class="text-rose-100 text-sm mb-3">최대 60% 할인 - 23:45:12 남음</div>
                <router-link to="/products" class="inline-block bg-white text-rose-600 px-4 py-1.5 rounded-lg text-sm font-bold hover:bg-rose-50 transition-colors">
                  쇼핑하기
                </router-link>
              </div>
              <div class="bg-gradient-to-br from-violet-500 to-purple-600 rounded-xl p-4 text-white">
                <div class="text-2xl mb-1">신규 회원</div>
                <div class="text-violet-100 text-sm mb-3">10,000원 쿠폰 증정</div>
                <router-link to="/register" class="inline-block bg-white text-violet-600 px-4 py-1.5 rounded-lg text-sm font-bold hover:bg-violet-50 transition-colors">
                  가입하기
                </router-link>
              </div>
            </div>
          </div>

          <!-- Recommended Section -->
          <div class="mt-6">
            <div class="flex items-center justify-between mb-4">
              <h2 class="text-lg font-bold text-gray-900">추천 상품</h2>
              <router-link to="/products" class="text-teal-600 text-sm hover:underline">전체보기</router-link>
            </div>
            <div class="grid grid-cols-5 gap-4">
              <router-link
                v-for="product in recommended"
                :key="product.productId"
                :to="`/products/${product.productId}`"
                class="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all group"
              >
                <div class="aspect-square bg-gray-100 flex items-center justify-center">
                  <svg class="w-12 h-12 text-gray-300 group-hover:scale-110 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                  </svg>
                </div>
                <div class="p-3">
                  <div class="font-medium text-gray-900 text-sm truncate">{{ product.productName }}</div>
                  <div class="flex items-center gap-2 mt-1">
                    <span class="text-teal-600 font-bold">{{ formatPrice(product.productPrice) }}</span>
                    <span class="text-xs text-gray-400 line-through">{{ formatPrice(product.productPrice * 1.3) }}</span>
                  </div>
                </div>
              </router-link>
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

const authStore = useAuthStore()
const products = ref([])

const categories = [
  { name: '전자기기', icon: '📱' },
  { name: '패션', icon: '👔' },
  { name: '식품', icon: '🍎' },
  { name: '생활용품', icon: '🏠' },
  { name: '도서', icon: '📚' },
  { name: '스포츠', icon: '⚽' },
]

const newArrivals = computed(() => products.value.slice(0, 5))
const bestSellers = computed(() => products.value.slice(2, 7))
const recommended = computed(() => products.value.slice(0, 5))

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR') + '원'
}

onMounted(async () => {
  try {
    const res = await productAPI.getAll(0, 10)
    products.value = res.data?.content || []
  } catch (error) {
    console.error('Failed to load products:', error)
  }
})
</script>
