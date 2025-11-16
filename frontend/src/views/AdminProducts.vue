<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6">
      <div class="mb-6">
        <h2 class="text-3xl font-bold text-gray-900">상품 관리</h2>
        <p class="text-gray-600 mt-1">상품을 추가, 수정, 삭제할 수 있습니다</p>
      </div>

      <!-- Master-Detail Layout -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Master: 상품 목록 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden flex flex-col" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b flex justify-between items-center">
            <h3 class="text-lg font-semibold text-gray-900">상품 목록</h3>
            <button @click="createNewProduct()" class="bg-primary-600 hover:bg-primary-700 px-4 py-2 rounded-lg font-medium text-sm transition-all" style="color: white; background-color: #0d9488;">
              <span class="flex items-center gap-1">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                </svg>
                추가
              </span>
            </button>
          </div>

          <div class="flex-1 overflow-y-auto">
            <div v-if="products.length" class="divide-y divide-gray-200">
              <div
                v-for="product in products"
                :key="product.productId"
                @click="selectProduct(product)"
                :class="[
                  'px-6 py-4 cursor-pointer transition-colors',
                  selectedProduct?.productId === product.productId
                    ? 'bg-primary-50 border-l-4 border-primary-600'
                    : 'hover:bg-gray-50'
                ]">
                <div class="font-medium text-gray-900">{{ product.productName }}</div>
                <div class="text-sm text-gray-600 mt-1">
                  {{ product.largeClassNAme }}
                  <span v-if="product.mediumClassName" class="text-gray-400"> > {{ product.mediumClassName }}</span>
                  <span v-if="product.smallClassName" class="text-gray-400"> > {{ product.smallClassName }}</span>
                </div>
                <div class="text-sm font-semibold text-gray-900 mt-1">{{ product.productPrice.toLocaleString() }}원</div>
              </div>
            </div>
            <div v-else class="px-6 py-12 text-center text-gray-500">
              <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
              </svg>
              <p class="text-lg">등록된 상품이 없습니다</p>
            </div>
          </div>

          <div v-if="totalPages > 1" class="bg-gray-50 px-6 py-3 border-t flex items-center justify-between">
            <div class="text-sm text-gray-600">
              페이지 {{ currentPage + 1 }} / {{ totalPages }}
            </div>
            <div class="flex gap-2">
              <button @click="prevPage" :disabled="currentPage === 0" class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm">
                이전
              </button>
              <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="px-3 py-1.5 bg-white border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed transition-colors text-sm">
                다음
              </button>
            </div>
          </div>
        </div>

        <!-- Detail: 상품 상세 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b">
            <h3 class="text-lg font-semibold text-gray-900">{{ editMode ? '상품 수정' : '상품 추가' }}</h3>
          </div>

          <div v-if="selectedProduct || isCreating" class="p-6 overflow-y-auto" style="max-height: calc(100vh - 320px);">
            <form @submit.prevent="saveProduct" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">상품명</label>
                <input
                  v-model="form.name"
                  type="text"
                  required
                  placeholder="상품명을 입력하세요"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">카테고리</label>
                <select
                  v-model="form.categoryId"
                  required
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                >
                  <option value="">카테고리 선택</option>
                  <option v-for="cat in flatCategories" :key="cat.id" :value="cat.id">
                    {{ '　'.repeat(cat.depth) }}{{ cat.depth > 0 ? '└─ ' : '' }}{{ cat.name }}
                  </option>
                </select>
              </div>
              <div class="flex gap-3 pt-4">
                <button
                  type="submit"
                  class="flex-1 hover:opacity-90 px-4 py-2.5 rounded-lg font-medium transition-all"
                  style="background-color: #0d9488; color: white;"
                >
                  저장
                </button>
                <button
                  v-if="editMode"
                  type="button"
                  @click="deleteProduct(selectedProduct.productId)"
                  class="flex-1 hover:opacity-90 px-4 py-2.5 rounded-lg font-medium transition-all"
                  style="background-color: #ef4444; color: white;"
                >
                  삭제
                </button>
                <button
                  type="button"
                  @click="clearSelection"
                  class="flex-1 hover:bg-gray-300 px-4 py-2.5 rounded-lg font-medium transition-colors"
                  style="background-color: #e5e7eb; color: #1f2937;"
                >
                  취소
                </button>
              </div>
            </form>
          </div>

          <div v-else class="p-12 text-center text-gray-500">
            <svg class="w-20 h-20 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 15l-2 5L9 9l11 4-5 2zm0 0l5 5M7.188 2.239l.777 2.897M5.136 7.965l-2.898-.777M13.95 4.05l-2.122 2.122m-5.657 5.656l-2.12 2.122" />
            </svg>
            <h3 class="text-lg font-medium text-gray-900 mb-2">상품을 선택하세요</h3>
            <p class="text-gray-500">좌측 목록에서 상품을 선택하거나<br>새 상품을 추가하세요</p>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { productAPI } from '../api/product'
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'

const products = ref([])
const categories = ref([])
const selectedProduct = ref(null)
const isCreating = ref(false)
const editMode = ref(false)
const form = ref({ id: null, name: '', categoryId: '' })
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = ref(10)

// 계층 구조를 평면화하는 computed 속성
const flatCategories = computed(() => {
  const flatten = (cats, result = []) => {
    cats.forEach(cat => {
      result.push({ id: cat.id, name: cat.name, depth: cat.depth })
      if (cat.children && cat.children.length > 0) {
        flatten(cat.children, result)
      }
    })
    return result
  }
  return flatten(categories.value)
})

onMounted(async () => {
  await loadProducts()
  const res = await categoryAPI.getHierarchies()
  categories.value = res.data || []
})

async function loadProducts() {
  try {
    const res = await productAPI.getAll(currentPage.value, pageSize.value)
    console.log('API Response:', res)

    // product.js에서 이미 response.data를 반환하므로 res가 ApiResponse
    const pageResponse = res.data  // ApiResponse.data에서 PageResponse 추출
    console.log('PageResponse:', pageResponse)

    if (pageResponse) {
      products.value = pageResponse.content || []
      currentPage.value = pageResponse.page
      totalPages.value = pageResponse.totalPages
      console.log('Products set:', products.value)
    }
  } catch (error) {
    console.error('Failed to load products:', error)
    alert('상품 목록을 불러오는데 실패했습니다: ' + error.message)
  }
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

function selectProduct(product) {
  selectedProduct.value = product
  isCreating.value = false
  editMode.value = true
  const categoryId = product.smallClassId || product.mediumClassId || product.largeClassId
  form.value = { id: product.productId, name: product.productName, categoryId: categoryId }
}

function createNewProduct() {
  selectedProduct.value = null
  isCreating.value = true
  editMode.value = false
  form.value = { id: null, name: '', categoryId: '' }
}

function clearSelection() {
  selectedProduct.value = null
  isCreating.value = false
  editMode.value = false
  form.value = { id: null, name: '', categoryId: '' }
}

async function saveProduct() {
  try {
    if (editMode.value) {
      await productAPI.update(form.value.id, form.value.categoryId, form.value.name)
    } else {
      await productAPI.create(form.value.categoryId, form.value.name)
    }
    clearSelection()
    await loadProducts()
  } catch (error) {
    alert('저장 실패: ' + error.message)
  }
}

async function deleteProduct(id) {
  if (!confirm('삭제하시겠습니까?')) return
  try {
    await productAPI.delete(id)
    clearSelection()
    await loadProducts()
  } catch (error) {
    alert('삭제 실패: ' + error.message)
  }
}
</script>
