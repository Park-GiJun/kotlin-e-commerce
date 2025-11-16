<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6" style="background-color: #e5e7eb; border-radius: 1rem; min-height: calc(100vh - 120px);">
      <!-- Hero Banner -->
      <div class="relative bg-gradient-to-r from-primary-500 to-primary-600 rounded-2xl overflow-hidden mb-8 shadow-xl">
        <div class="px-8 py-16 md:py-20">
          <div class="max-w-2xl">
            <h1 class="text-4xl md:text-5xl font-bold mb-4" style="color: white;">
              <span style="color: #fef3c7; text-shadow: 0 2px 4px rgba(0,0,0,0.1);">Mint</span>Shop에 오신 것을 환영합니다!
            </h1>
            <p class="text-xl mb-8" style="color: rgba(255, 255, 255, 0.95);">
              <span class="font-semibold">{{ authStore.user?.name }}</span>님을 위한 특별한 혜택이 준비되어 있습니다
            </p>
            <router-link
              to="/products"
              class="inline-block px-8 py-3 rounded-lg font-bold hover:opacity-90 transition-all shadow-lg"
              style="background-color: white; color: #0d9488;"
            >
              쇼핑 시작하기
            </router-link>
          </div>
        </div>
        <div class="absolute right-0 top-0 h-full w-1/3 hidden md:block">
          <div class="h-full flex items-center justify-center text-white/20">
            <svg class="w-64 h-64" fill="currentColor" viewBox="0 0 20 20">
              <path d="M3 1a1 1 0 000 2h1.22l.305 1.222a.997.997 0 00.01.042l1.358 5.43-.893.892C3.74 11.846 4.632 14 6.414 14H15a1 1 0 000-2H6.414l1-1H14a1 1 0 00.894-.553l3-6A1 1 0 0017 3H6.28l-.31-1.243A1 1 0 005 1H3zM16 16.5a1.5 1.5 0 11-3 0 1.5 1.5 0 013 0zM6.5 18a1.5 1.5 0 100-3 1.5 1.5 0 000 3z" />
            </svg>
          </div>
        </div>
      </div>

      <!-- Quick Stats -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
        <div class="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6 border-l-4" style="border-color: #0d9488;">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-14 h-14 rounded-full flex items-center justify-center" style="background-color: #f0fdf9;">
                <svg class="w-8 h-8" style="color: #0d9488;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">총 상품</p>
              <p class="text-3xl font-bold text-gray-900">{{ stats.totalProducts }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6 border-l-4 border-blue-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-14 h-14 rounded-full bg-blue-50 flex items-center justify-center">
                <svg class="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">카테고리</p>
              <p class="text-3xl font-bold text-gray-900">{{ stats.totalCategories }}</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6 border-l-4 border-yellow-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-14 h-14 rounded-full bg-yellow-50 flex items-center justify-center">
                <svg class="w-8 h-8 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">오늘의 할인</p>
              <p class="text-3xl font-bold text-gray-900">15%</p>
            </div>
          </div>
        </div>

        <div class="bg-white rounded-lg shadow-md hover:shadow-lg transition-shadow p-6 border-l-4 border-red-500">
          <div class="flex items-center">
            <div class="flex-shrink-0">
              <div class="w-14 h-14 rounded-full bg-red-50 flex items-center justify-center">
                <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
                </svg>
              </div>
            </div>
            <div class="ml-4">
              <p class="text-sm font-medium text-gray-500">장바구니</p>
              <p class="text-3xl font-bold text-gray-900">0</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Categories Grid -->
      <div class="mb-8 bg-gradient-to-br from-gray-50 to-white rounded-2xl p-8 shadow-sm">
        <div class="flex items-center justify-between mb-6">
          <h2 class="text-2xl font-bold text-gray-900">인기 카테고리</h2>
          <span class="text-sm text-gray-500">카테고리를 선택하여 둘러보세요</span>
        </div>
        <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
          <div v-for="category in dummyCategories" :key="category.name" class="bg-white rounded-xl shadow-sm hover:shadow-md transition-all p-6 cursor-pointer group border border-gray-100">
            <div class="text-center">
              <div class="w-16 h-16 mx-auto rounded-full flex items-center justify-center mb-3 transition-all" style="background-color: #f0fdf9;">
                <svg class="w-8 h-8" style="color: #0d9488;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                </svg>
              </div>
              <h3 class="font-semibold text-gray-900 text-sm">{{ category.name }}</h3>
              <p class="text-xs text-gray-500 mt-1">{{ category.count }}개 상품</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Today's Deals -->
      <div class="mb-8 rounded-2xl p-8 shadow-sm" style="background: linear-gradient(135deg, #f0fdf9 0%, #ffffff 100%);">
        <div class="flex items-center justify-between mb-6">
          <div>
            <h2 class="text-2xl font-bold text-gray-900">오늘의 특가</h2>
            <p class="text-sm text-gray-600 mt-1">놓치면 후회할 특별한 할인 상품</p>
          </div>
          <router-link to="/products" class="px-4 py-2 rounded-lg font-medium text-white transition-all hover:opacity-90" style="background-color: #0d9488;">
            전체보기 →
          </router-link>
        </div>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          <ProductCard
            v-for="product in recentProducts"
            :key="product.productId"
            :product="product"
          />
        </div>
      </div>

      <!-- Benefits Section -->
      <div class="bg-white rounded-2xl shadow-lg p-8 border border-gray-100">
        <h2 class="text-2xl font-bold text-gray-900 mb-8 text-center">MintShop의 특별한 혜택</h2>
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="text-center p-6 rounded-xl hover:bg-gray-50 transition-colors">
            <div class="w-20 h-20 mx-auto rounded-full flex items-center justify-center mb-4" style="background-color: #f0fdf9;">
              <svg class="w-10 h-10" style="color: #0d9488;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4" />
              </svg>
            </div>
            <h3 class="font-bold text-lg mb-2 text-gray-900">무료 배송</h3>
            <p class="text-gray-600 text-sm">30,000원 이상 구매시<br>무료 배송 혜택</p>
          </div>

          <div class="text-center p-6 rounded-xl hover:bg-gray-50 transition-colors">
            <div class="w-20 h-20 mx-auto rounded-full flex items-center justify-center mb-4" style="background-color: #f0fdf9;">
              <svg class="w-10 h-10" style="color: #0d9488;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h3 class="font-bold text-lg mb-2 text-gray-900">안전 결제</h3>
            <p class="text-gray-600 text-sm">믿을 수 있는<br>결제 시스템</p>
          </div>

          <div class="text-center p-6 rounded-xl hover:bg-gray-50 transition-colors">
            <div class="w-20 h-20 mx-auto rounded-full flex items-center justify-center mb-4" style="background-color: #f0fdf9;">
              <svg class="w-10 h-10" style="color: #0d9488;" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6" />
              </svg>
            </div>
            <h3 class="font-bold text-lg mb-2 text-gray-900">간편 반품</h3>
            <p class="text-gray-600 text-sm">7일 이내<br>무료 반품 가능</p>
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
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'
import ProductCard from '../components/ProductCard.vue'

const authStore = useAuthStore()
const stats = ref({ totalProducts: 0, totalCategories: 0 })
const products = ref([])

const dummyCategories = [
  { name: '전자기기', count: 120 },
  { name: '의류', count: 85 },
  { name: '식품', count: 64 },
  { name: '생활용품', count: 92 },
  { name: '도서', count: 156 },
  { name: '스포츠', count: 43 },
]

const recentProducts = computed(() => {
  return products.value.slice(0, 4).map(product => ({
    ...product,
    imageUrl: null,
    discount: Math.floor(Math.random() * 30 + 10),
    reviewCount: Math.floor(Math.random() * 500)
  }))
})

onMounted(async () => {
  try {
    const [productsRes, categoriesRes] = await Promise.all([
      productAPI.getAll(0, 10),
      categoryAPI.getAll()
    ])

    const pageResponse = productsRes.data
    if (pageResponse) {
      stats.value.totalProducts = pageResponse.totalElements || 0
      products.value = pageResponse.content || []
    }

    stats.value.totalCategories = categoriesRes.data?.length || 0
  } catch (error) {
    console.error('통계 로드 실패:', error)
  }
})
</script>
