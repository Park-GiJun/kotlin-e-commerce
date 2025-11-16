<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6">
      <!-- Breadcrumb -->
      <div class="mb-4 text-sm text-gray-600">
        <router-link to="/dashboard" class="hover:text-primary-600">홈</router-link>
        <span class="mx-2">/</span>
        <span class="text-gray-900">전체 상품</span>
      </div>

      <!-- Page Header -->
      <div class="mb-6">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">전체 상품</h1>
        <p class="text-gray-600">{{ totalElements }}개의 상품이 있습니다</p>
      </div>

      <div class="flex gap-6">
        <!-- Sidebar Filter -->
        <aside class="hidden lg:block w-64 flex-shrink-0">
          <div class="bg-white rounded-lg shadow sticky top-4 overflow-hidden">
            <!-- 전체 보기 -->
            <div class="p-4 border-b">
              <button
                @click="clearCategoryFilter"
                :class="[
                  'w-full flex items-center justify-between px-4 py-2.5 rounded-lg transition-colors font-medium',
                  selectedCategoryId === null
                    ? 'bg-primary-600 text-white'
                    : 'bg-gray-50 text-gray-700 hover:bg-gray-100'
                ]"
              >
                <span class="flex items-center gap-2">
                  <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z" />
                  </svg>
                  전체 보기
                </span>
                <span class="text-sm">{{ totalElements }}</span>
              </button>
            </div>

            <!-- 카테고리 목록 -->
            <div class="max-h-96 overflow-y-auto">
              <div v-if="categories.length">
                <CategoryFilter
                  v-for="category in categories"
                  :key="category.id"
                  :category="category"
                  :selectedId="selectedCategoryId"
                  @select="filterByCategory"
                />
              </div>
              <div v-else class="p-6 text-center text-gray-500 text-sm">
                카테고리가 없습니다
              </div>
            </div>
          </div>
        </aside>

        <!-- Products Grid -->
        <div class="flex-1">
          <!-- Sort Options -->
          <div class="bg-white rounded-lg shadow px-4 py-3 mb-4 flex items-center justify-between">
            <div class="text-sm text-gray-600">
              페이지 {{ currentPage + 1 }} / {{ totalPages }}
            </div>
            <select class="border rounded-md px-3 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-primary-500">
              <option>추천순</option>
              <option>낮은 가격순</option>
              <option>높은 가격순</option>
              <option>최신순</option>
            </select>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="text-center py-12">
            <div class="inline-block animate-spin rounded-full h-12 w-12 border-4 border-primary-500 border-t-transparent"></div>
            <p class="mt-4 text-gray-600">로딩 중...</p>
          </div>

          <!-- Products Grid -->
          <div v-else-if="products.length" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
            <ProductCard
              v-for="product in productsWithDummyData"
              :key="product.productId"
              :product="product"
            />
          </div>

          <!-- Empty State -->
          <div v-else class="bg-white rounded-lg shadow p-12 text-center">
            <svg class="w-24 h-24 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
            </svg>
            <h3 class="text-xl font-medium text-gray-900 mb-2">상품이 없습니다</h3>
            <p class="text-gray-600">등록된 상품이 없습니다.</p>
          </div>

          <!-- Pagination -->
          <div v-if="totalPages > 1" class="mt-8 flex items-center justify-center gap-2">
            <button
              @click="prevPage"
              :disabled="currentPage === 0"
              class="px-4 py-2 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
              </svg>
            </button>

            <div class="flex gap-1">
              <button
                v-for="page in visiblePages"
                :key="page"
                @click="goToPage(page - 1)"
                :class="[
                  'px-4 py-2 rounded-lg transition-colors',
                  currentPage === page - 1
                    ? 'bg-primary-600 text-white'
                    : 'bg-white border hover:bg-gray-50'
                ]"
              >
                {{ page }}
              </button>
            </div>

            <button
              @click="nextPage"
              :disabled="currentPage >= totalPages - 1"
              class="px-4 py-2 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </button>
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
import ProductCard from '../components/ProductCard.vue'
import CategoryFilter from '../components/CategoryFilter.vue'

const products = ref([])
const categories = ref([])
const loading = ref(true)
const currentPage = ref(0)
const totalPages = ref(0)
const totalElements = ref(0)
const pageSize = ref(12)
const selectedCategoryId = ref(null)

// Add dummy data for missing fields
const productsWithDummyData = computed(() => {
  return products.value.map(product => ({
    ...product,
    imageUrl: null, // 더미 이미지는 ProductCard에서 처리
    discount: Math.random() > 0.7 ? Math.floor(Math.random() * 30 + 10) : null,
    reviewCount: Math.floor(Math.random() * 500)
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
    console.error('카테고리 로드 실패:', error)
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
    console.error('상품 로드 실패:', error)
  } finally {
    loading.value = false
  }
}

function filterByCategory(categoryId) {
  selectedCategoryId.value = categoryId
  currentPage.value = 0
  loadProducts()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function clearCategoryFilter() {
  selectedCategoryId.value = null
  currentPage.value = 0
  loadProducts()
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value++
    loadProducts()
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value--
    loadProducts()
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
}

function goToPage(page) {
  if (page >= 0 && page < totalPages.value) {
    currentPage.value = page
    loadProducts()
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
}
</script>
