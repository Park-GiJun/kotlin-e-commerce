<template>
  <Layout>
    <div class="h-full flex flex-col bg-gray-50">
      <!-- Header -->
      <div class="bg-white border-b px-6 py-4 flex-shrink-0">
        <div class="max-w-7xl mx-auto">
          <div class="flex items-center justify-between">
            <div>
              <div class="text-sm text-gray-500 mb-1">
                <router-link to="/dashboard" class="hover:text-teal-600">홈</router-link>
                <span class="mx-2">/</span>
                <span class="text-gray-900">전체 상품</span>
              </div>
              <h1 class="text-xl font-bold text-gray-900">전체 상품</h1>
            </div>
            <div class="flex items-center gap-4">
              <span class="text-sm text-gray-600">{{ totalElements }}개 상품</span>
              <select class="border rounded-lg px-3 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-teal-500">
                <option>추천순</option>
                <option>낮은 가격순</option>
                <option>높은 가격순</option>
                <option>최신순</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex-1 overflow-hidden">
        <div class="max-w-7xl mx-auto px-6 py-4 h-full flex gap-6">
          <!-- Sidebar -->
          <aside class="w-56 flex-shrink-0 bg-white rounded-xl shadow-sm overflow-hidden flex flex-col">
            <div class="p-3 border-b bg-gray-50">
              <button
                @click="clearCategoryFilter"
                :class="['w-full flex items-center justify-between px-3 py-2 rounded-lg transition-colors text-sm font-medium', selectedCategoryId === null ? 'bg-teal-600 text-white' : 'bg-white text-gray-700 hover:bg-gray-100']"
              >
                <span>전체 보기</span>
                <span class="text-xs">{{ totalElements }}</span>
              </button>
            </div>
            <div class="flex-1 overflow-y-auto p-2">
              <CategoryFilter
                v-for="category in categories"
                :key="category.id"
                :category="category"
                :selectedId="selectedCategoryId"
                @select="filterByCategory"
              />
            </div>
          </aside>

          <!-- Products Grid -->
          <div class="flex-1 flex flex-col overflow-hidden">
            <!-- Loading -->
            <div v-if="loading" class="flex-1 flex items-center justify-center">
              <div class="text-center">
                <div class="inline-block animate-spin rounded-full h-10 w-10 border-4 border-teal-500 border-t-transparent"></div>
                <p class="mt-3 text-gray-600">로딩 중...</p>
              </div>
            </div>

            <!-- Products -->
            <div v-else-if="products.length" class="flex-1 overflow-y-auto">
              <div class="grid grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4 pb-4">
                <router-link
                  v-for="product in productsWithDummyData"
                  :key="product.productId"
                  :to="`/products/${product.productId}`"
                  class="bg-white rounded-xl shadow-sm overflow-hidden hover:shadow-md transition-all group"
                >
                  <div class="aspect-square bg-gray-100 flex items-center justify-center relative">
                    <svg :class="['w-16 h-16 text-gray-300 group-hover:scale-110 transition-transform', !product.isOnSale && 'opacity-50']" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                    <!-- 품절 표시 -->
                    <div v-if="!product.isOnSale" class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40">
                      <span class="bg-gray-800 text-white px-3 py-1 rounded-lg font-bold text-sm">품절</span>
                    </div>
                    <span v-if="product.discount && product.isOnSale" class="absolute top-2 left-2 bg-rose-500 text-white text-xs font-bold px-2 py-0.5 rounded">
                      -{{ product.discount }}%
                    </span>
                  </div>
                  <div class="p-3">
                    <div class="text-xs text-gray-500 mb-1">{{ product.largeClassName }}</div>
                    <div class="font-medium text-gray-900 text-sm truncate mb-2">{{ product.productName }}</div>
                    <div class="flex items-center gap-2">
                      <span class="text-teal-600 font-bold">{{ formatPrice(product.productPrice) }}</span>
                      <span v-if="product.discount" class="text-xs text-gray-400 line-through">{{ formatPrice(product.productPrice * (100 / (100 - product.discount))) }}</span>
                    </div>
                    <div class="flex items-center gap-1 mt-2">
                      <div class="flex text-yellow-400">
                        <svg v-for="i in 5" :key="i" class="w-3 h-3" :fill="i <= Math.round(product.averageRating / 2) ? 'currentColor' : 'none'" :stroke="i <= Math.round(product.averageRating / 2) ? 'none' : 'currentColor'" viewBox="0 0 20 20"><path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"/></svg>
                      </div>
                      <span class="text-xs text-gray-500">{{ (product.averageRating / 2).toFixed(1) }} ({{ product.reviewCount }}개 후기)</span>
                    </div>
                  </div>
                </router-link>
              </div>
            </div>

            <!-- Empty -->
            <div v-else class="flex-1 flex items-center justify-center">
              <div class="text-center">
                <svg class="w-20 h-20 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
                </svg>
                <h3 class="text-lg font-medium text-gray-900 mb-2">상품이 없습니다</h3>
                <p class="text-gray-600">다른 카테고리를 선택해보세요</p>
              </div>
            </div>

            <!-- Pagination -->
            <div v-if="totalPages > 1 && !loading" class="flex-shrink-0 bg-white border-t py-3 px-4 flex items-center justify-between">
              <span class="text-sm text-gray-600">{{ currentPage + 1 }} / {{ totalPages }} 페이지</span>
              <div class="flex gap-2">
                <button
                  @click="prevPage"
                  :disabled="currentPage === 0"
                  class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
                >
                  이전
                </button>
                <button
                  v-for="page in visiblePages"
                  :key="page"
                  @click="goToPage(page - 1)"
                  :class="['px-3 py-1.5 rounded-lg transition-colors text-sm', currentPage === page - 1 ? 'bg-teal-600 text-white' : 'bg-white border hover:bg-gray-50']"
                >
                  {{ page }}
                </button>
                <button
                  @click="nextPage"
                  :disabled="currentPage >= totalPages - 1"
                  class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm"
                >
                  다음
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { productAPI } from '../api/product'
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'
import CategoryFilter from '../components/CategoryFilter.vue'

const products = ref([])
const categories = ref([])
const loading = ref(true)
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)
const pageSize = ref(12)
const selectedCategoryId = ref(null)

const productsWithDummyData = computed(() => {
  return products.value.map(product => ({
    ...product,
    discount: Math.random() > 0.7 ? Math.floor(Math.random() * 30 + 10) : null
  }))
})

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 3)
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  return pages
})

onMounted(async () => {
  await loadCategories()
  await loadProducts()
})

async function loadCategories() {
  try {
    const res = await categoryAPI.getHierarchies()
    categories.value = res.data || []
  } catch (error) {
    console.error('Failed to load categories:', error)
  }
}

async function loadProducts() {
  try {
    loading.value = true
    let res
    if (selectedCategoryId.value) {
      res = await productAPI.getByCategory(selectedCategoryId.value, currentPage.value, pageSize.value)
    } else {
      res = await productAPI.getAll(currentPage.value, pageSize.value)
    }
    const pageResponse = res.data
    if (pageResponse) {
      products.value = pageResponse.content || []
      currentPage.value = pageResponse.page
      totalPages.value = pageResponse.totalPages
      totalElements.value = pageResponse.totalElements
    }
  } catch (error) {
    console.error('Failed to load products:', error)
  } finally {
    loading.value = false
  }
}

function filterByCategory(categoryId) {
  selectedCategoryId.value = categoryId
  currentPage.value = 0
  loadProducts()
}

function clearCategoryFilter() {
  selectedCategoryId.value = null
  currentPage.value = 0
  loadProducts()
}

function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++
    loadProducts()
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value--
    loadProducts()
  }
}

function goToPage(page) {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page
    loadProducts()
  }
}

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR') + '원'
}
</script>
