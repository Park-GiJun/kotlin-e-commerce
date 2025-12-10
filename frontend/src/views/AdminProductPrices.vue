<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6">
      <div class="mb-6">
        <h2 class="text-3xl font-bold text-gray-900">상품 가격 관리</h2>
        <p class="text-gray-600 mt-1">상품별 가격 이력을 관리할 수 있습니다</p>
      </div>

      <!-- Master-Detail Layout -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Master: 상품 목록 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden flex flex-col" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b">
            <h3 class="text-lg font-semibold text-gray-900">상품 목록</h3>
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
                <div class="text-sm font-semibold text-gray-900 mt-1">현재 가격: {{ product.productPrice.toLocaleString() }}원</div>
              </div>
            </div>
            <div v-else class="px-6 py-12 text-center text-gray-500">
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

        <!-- Detail: 가격 이력 및 관리 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden flex flex-col" style="max-height: calc(100vh - 200px);">
          <div v-if="selectedProduct">
            <div class="px-6 py-4 bg-gray-50 border-b">
              <h3 class="text-lg font-semibold text-gray-900">{{ selectedProduct.productName }} - 가격 이력</h3>
            </div>

            <!-- 가격 이력 목록 -->
            <div class="flex-1 overflow-y-auto" style="max-height: 300px;">
              <div v-if="productPrices.length" class="divide-y divide-gray-200">
                <div
                  v-for="price in productPrices"
                  :key="price.id"
                  @click="selectPrice(price)"
                  :class="[
                    'px-6 py-3 cursor-pointer transition-colors',
                    selectedPrice?.id === price.id
                      ? 'bg-blue-50 border-l-4 border-blue-600'
                      : 'hover:bg-gray-50'
                  ]">
                  <div class="flex justify-between items-center">
                    <div class="font-semibold text-lg text-gray-900">{{ price.price.toLocaleString() }}원</div>
                    <div v-if="isCurrentPrice(price)" class="text-xs font-bold px-2 py-1 rounded-full" style="background-color: #dcfce7; color: #166534;">
                      현재 가격
                    </div>
                  </div>
                  <div class="text-sm text-gray-600 mt-1">
                    {{ formatDate(price.startDate) }} ~ {{ formatDate(price.endDate) }}
                  </div>
                </div>
              </div>
              <div v-else class="px-6 py-8 text-center text-gray-500">
                <p>가격 이력이 없습니다</p>
              </div>
            </div>

            <!-- 가격 추가/수정 폼 -->
            <div class="border-t bg-gray-50">
              <div class="px-6 py-4">
                <h4 class="font-semibold text-gray-900 mb-4">{{ editMode ? '가격 수정' : '가격 추가' }}</h4>
                <form @submit.prevent="savePrice" class="space-y-3">
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-1">가격</label>
                    <input
                      v-model.number="form.price"
                      type="number"
                      required
                      min="0"
                      placeholder="가격을 입력하세요"
                      class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent text-sm"
                    />
                  </div>
                  <div class="grid grid-cols-2 gap-3">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">시작일</label>
                      <input
                        v-model="form.startDate"
                        type="date"
                        required
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent text-sm"
                      />
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">종료일</label>
                      <input
                        v-model="form.endDate"
                        type="date"
                        required
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent text-sm"
                      />
                    </div>
                  </div>
                  <div class="flex gap-2 pt-2">
                    <button
                      type="submit"
                      class="flex-1 hover:opacity-90 px-4 py-2 rounded-lg font-medium transition-all text-sm"
                      style="background-color: #0d9488; color: white;"
                    >
                      {{ editMode ? '수정' : '추가' }}
                    </button>
                    <button
                      v-if="editMode"
                      type="button"
                      @click="deletePrice"
                      class="flex-1 hover:opacity-90 px-4 py-2 rounded-lg font-medium transition-all text-sm"
                      style="background-color: #ef4444; color: white;"
                    >
                      삭제
                    </button>
                    <button
                      type="button"
                      @click="clearPriceSelection"
                      class="flex-1 hover:bg-gray-300 px-4 py-2 rounded-lg font-medium transition-colors text-sm"
                      style="background-color: #e5e7eb; color: #1f2937;"
                    >
                      취소
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>

          <div v-else class="p-12 text-center text-gray-500">
            <svg class="w-20 h-20 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <h3 class="text-lg font-medium text-gray-900 mb-2">상품을 선택하세요</h3>
            <p class="text-gray-500">좌측 목록에서 상품을 선택하여<br>가격 이력을 관리하세요</p>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { productAPI } from '../api/product'
import { productPriceAPI } from '../api/productPrice'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, error, confirm } = useAlert()

const products = ref([])
const productPrices = ref([])
const selectedProduct = ref(null)
const selectedPrice = ref(null)
const editMode = ref(false)
const form = ref({ id: null, price: 0, startDate: '', endDate: '9999-12-31' })
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = ref(10)

onMounted(async () => {
  await loadProducts()
})

async function loadProducts() {
  try {
    const res = await productAPI.getAll(currentPage.value, pageSize.value)
    const pageResponse = res.data

    if (pageResponse) {
      products.value = pageResponse.content || []
      currentPage.value = pageResponse.page
      totalPages.value = pageResponse.totalPages
    }
  } catch (err) {
    console.error('Failed to load products:', err)
    error('상품 목록을 불러오는데 실패했습니다: ' + err.message)
  }
}

async function loadProductPrices(productId) {
  try {
    const res = await productPriceAPI.getByProductId(productId)
    productPrices.value = res.data || []
    productPrices.value.sort((a, b) => new Date(b.startDate) - new Date(a.startDate))
  } catch (err) {
    console.error('Failed to load product prices:', err)
    error('가격 이력을 불러오는데 실패했습니다: ' + err.message)
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

async function selectProduct(product) {
  selectedProduct.value = product
  selectedPrice.value = null
  editMode.value = false
  form.value = { id: null, price: 0, startDate: getTodayDate(), endDate: '9999-12-31' }
  await loadProductPrices(product.productId)
}

function selectPrice(price) {
  selectedPrice.value = price
  editMode.value = true
  form.value = {
    id: price.id,
    price: price.price,
    startDate: price.startDate,
    endDate: price.endDate
  }
}

function clearPriceSelection() {
  selectedPrice.value = null
  editMode.value = false
  form.value = { id: null, price: 0, startDate: getTodayDate(), endDate: '9999-12-31' }
}

async function savePrice() {
  if (!selectedProduct.value) return

  try {
    if (editMode.value && selectedPrice.value) {
      await productPriceAPI.update(
        form.value.id,
        selectedProduct.value.productId,
        form.value.price,
        form.value.startDate,
        form.value.endDate
      )
    } else {
      await productPriceAPI.create(
        selectedProduct.value.productId,
        form.value.price,
        form.value.startDate,
        form.value.endDate
      )
    }
    clearPriceSelection()
    await loadProductPrices(selectedProduct.value.productId)
    await loadProducts() // 현재 가격이 변경될 수 있으므로 상품 목록도 다시 로드
    success('가격이 저장되었습니다!')
  } catch (err) {
    error('저장 실패: ' + err.message)
  }
}

async function deletePrice() {
  if (!selectedPrice.value) return
  const confirmed = await confirm('이 가격 정보를 삭제하시겠습니까?', '삭제 확인')
  if (!confirmed) return

  try {
    await productPriceAPI.delete(selectedPrice.value.id)
    clearPriceSelection()
    await loadProductPrices(selectedProduct.value.productId)
    await loadProducts()
    success('가격이 삭제되었습니다!')
  } catch (err) {
    error('삭제 실패: ' + err.message)
  }
}

function isCurrentPrice(price) {
  return price.endDate === '9999-12-31'
}

function formatDate(dateString) {
  if (dateString === '9999-12-31') return '현재'
  return dateString
}

function getTodayDate() {
  const today = new Date()
  const year = today.getFullYear()
  const month = String(today.getMonth() + 1).padStart(2, '0')
  const day = String(today.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}
</script>
