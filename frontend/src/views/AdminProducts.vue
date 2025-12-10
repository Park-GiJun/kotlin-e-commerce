<template>
  <Layout>
    <div class="h-full flex flex-col bg-gray-50">
      <!-- Header -->
      <div class="bg-white border-b px-6 py-4 flex-shrink-0">
        <div class="max-w-7xl mx-auto flex items-center justify-between">
          <div>
            <h1 class="text-xl font-bold text-gray-900">상품 관리</h1>
            <p class="text-sm text-gray-500">상품, 가격, 재고를 관리합니다</p>
          </div>
          <button @click="createNewProduct()" class="bg-teal-600 hover:bg-teal-700 text-white px-4 py-2 rounded-lg font-medium text-sm transition-colors flex items-center gap-2">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
            </svg>
            상품 추가
          </button>
        </div>
      </div>

      <!-- Main Content -->
      <div class="flex-1 overflow-hidden">
        <div class="max-w-7xl mx-auto px-6 py-4 h-full">
          <div class="grid grid-cols-12 gap-6 h-full">
            <!-- Left: Product List -->
            <div class="col-span-4 bg-white rounded-xl shadow-sm overflow-hidden flex flex-col">
              <div class="px-4 py-3 border-b bg-gray-50">
                <input
                  type="text"
                  v-model="searchQuery"
                  placeholder="상품 검색..."
                  class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                />
              </div>
              <div class="flex-1 overflow-y-auto divide-y">
                <div
                  v-for="product in filteredProducts"
                  :key="product.productId"
                  @click="selectProduct(product)"
                  :class="['px-4 py-3 cursor-pointer transition-colors', selectedProduct?.productId === product.productId ? 'bg-teal-50 border-l-4 border-teal-600' : 'hover:bg-gray-50']"
                >
                  <div class="font-medium text-gray-900 text-sm">{{ product.productName }}</div>
                  <div class="text-xs text-gray-500 mt-1">{{ product.largeClassNAme }}</div>
                  <div class="text-sm font-semibold text-teal-600 mt-1">{{ formatPrice(product.productPrice) }}</div>
                </div>
                <div v-if="!filteredProducts.length" class="px-4 py-8 text-center text-gray-500">
                  <p class="text-sm">상품이 없습니다</p>
                </div>
              </div>
              <div v-if="totalPages > 1" class="px-4 py-2 border-t bg-gray-50 flex items-center justify-between text-sm">
                <span class="text-gray-600">{{ currentPage + 1 }} / {{ totalPages }} 페이지</span>
                <div class="flex gap-2">
                  <button @click="prevPage" :disabled="currentPage === 0" class="px-2 py-1 bg-white border rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">이전</button>
                  <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="px-2 py-1 bg-white border rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">다음</button>
                </div>
              </div>
            </div>

            <!-- Right: Product Detail / Form -->
            <div class="col-span-8 bg-white rounded-xl shadow-sm overflow-hidden flex flex-col">
              <!-- Tabs -->
              <div class="flex border-b bg-gray-50">
                <button
                  v-for="tab in tabs"
                  :key="tab.id"
                  @click="activeTab = tab.id"
                  :class="['px-6 py-3 text-sm font-medium transition-colors', activeTab === tab.id ? 'text-teal-600 border-b-2 border-teal-600 bg-white' : 'text-gray-600 hover:text-gray-900']"
                >
                  {{ tab.label }}
                </button>
              </div>

              <!-- Tab Content -->
              <div class="flex-1 overflow-y-auto p-6">
                <!-- Product Info Tab -->
                <div v-if="activeTab === 'info'">
                  <div v-if="selectedProduct || isCreating" class="space-y-4">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">상품명</label>
                      <input
                        v-model="form.name"
                        type="text"
                        placeholder="상품명을 입력하세요"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">카테고리</label>
                      <select
                        v-model="form.categoryId"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      >
                        <option value="">카테고리 선택</option>
                        <option v-for="cat in flatCategories" :key="cat.id" :value="cat.id">
                          {{ '  '.repeat(cat.depth) }}{{ cat.depth > 0 ? '└ ' : '' }}{{ cat.name }}
                        </option>
                      </select>
                    </div>
                    <div class="flex gap-3 pt-4">
                      <button @click="saveProduct" class="flex-1 bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium transition-colors">
                        저장
                      </button>
                      <button v-if="editMode" @click="deleteProduct(selectedProduct.productId)" class="flex-1 bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg font-medium transition-colors">
                        삭제
                      </button>
                      <button @click="clearSelection" class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 py-2 rounded-lg font-medium transition-colors">
                        취소
                      </button>
                    </div>
                  </div>
                  <div v-else class="text-center py-12 text-gray-500">
                    <svg class="w-16 h-16 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                    <p>상품을 선택하거나 새로 추가하세요</p>
                  </div>
                </div>

                <!-- Price Settings Tab -->
                <div v-if="activeTab === 'price'">
                  <div v-if="selectedProduct" class="space-y-4">
                    <div class="bg-teal-50 rounded-lg p-4 mb-4">
                      <div class="text-sm text-teal-700">선택된 상품</div>
                      <div class="font-bold text-teal-900">{{ selectedProduct.productName }}</div>
                    </div>

                    <!-- Current Price -->
                    <div class="bg-gray-50 rounded-lg p-4">
                      <div class="text-sm text-gray-600 mb-1">현재 가격</div>
                      <div class="text-2xl font-bold text-gray-900">{{ formatPrice(selectedProduct.productPrice) }}</div>
                    </div>

                    <!-- Add New Price -->
                    <div class="border rounded-lg p-4 space-y-3">
                      <h3 class="font-medium text-gray-900">새 가격 설정</h3>
                      <div class="grid grid-cols-3 gap-4">
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">가격</label>
                          <input
                            v-model.number="priceForm.price"
                            type="number"
                            placeholder="Price"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">시작일</label>
                          <input
                            v-model="priceForm.startDate"
                            type="date"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">종료일</label>
                          <input
                            v-model="priceForm.endDate"
                            type="date"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                      </div>
                      <button @click="savePrice" class="w-full bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium text-sm transition-colors">
                        가격 저장
                      </button>
                    </div>

                    <!-- Price History (Dummy) -->
                    <div>
                      <h3 class="font-medium text-gray-900 mb-3">가격 이력</h3>
                      <div class="border rounded-lg overflow-hidden">
                        <table class="w-full text-sm">
                          <thead class="bg-gray-50">
                            <tr>
                              <th class="px-4 py-2 text-left text-gray-600">가격</th>
                              <th class="px-4 py-2 text-left text-gray-600">시작일</th>
                              <th class="px-4 py-2 text-left text-gray-600">종료일</th>
                              <th class="px-4 py-2 text-left text-gray-600">상태</th>
                            </tr>
                          </thead>
                          <tbody class="divide-y">
                            <tr v-for="history in priceHistory" :key="history.id">
                              <td class="px-4 py-2 font-medium">{{ formatPrice(history.price) }}</td>
                              <td class="px-4 py-2 text-gray-600">{{ history.startDate }}</td>
                              <td class="px-4 py-2 text-gray-600">{{ history.endDate }}</td>
                              <td class="px-4 py-2">
                                <span :class="['px-2 py-0.5 rounded text-xs font-medium', history.active ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-600']">
                                  {{ history.active ? '적용중' : '만료' }}
                                </span>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                  <div v-else class="text-center py-12 text-gray-500">
                    <p>가격을 관리할 상품을 선택하세요</p>
                  </div>
                </div>

                <!-- Stock Settings Tab -->
                <div v-if="activeTab === 'stock'">
                  <div v-if="selectedProduct" class="space-y-4">
                    <div class="bg-teal-50 rounded-lg p-4 mb-4">
                      <div class="text-sm text-teal-700">선택된 상품</div>
                      <div class="font-bold text-teal-900">{{ selectedProduct.productName }}</div>
                    </div>

                    <!-- Current Stock -->
                    <div class="grid grid-cols-3 gap-4">
                      <div class="bg-gray-50 rounded-lg p-4">
                        <div class="text-sm text-gray-600 mb-1">가용 재고</div>
                        <div class="text-2xl font-bold text-gray-900">{{ stockForm.quantity }}</div>
                      </div>
                      <div class="bg-yellow-50 rounded-lg p-4">
                        <div class="text-sm text-yellow-700 mb-1">예약됨</div>
                        <div class="text-2xl font-bold text-yellow-900">{{ stockForm.reserved }}</div>
                      </div>
                      <div class="bg-blue-50 rounded-lg p-4">
                        <div class="text-sm text-blue-700 mb-1">전체</div>
                        <div class="text-2xl font-bold text-blue-900">{{ stockForm.quantity + stockForm.reserved }}</div>
                      </div>
                    </div>

                    <!-- Update Stock -->
                    <div class="border rounded-lg p-4 space-y-3">
                      <h3 class="font-medium text-gray-900">재고 수정</h3>
                      <div class="grid grid-cols-2 gap-4">
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">조정 유형</label>
                          <select v-model="stockForm.adjustType" class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm">
                            <option value="set">수량 설정</option>
                            <option value="add">재고 추가</option>
                            <option value="subtract">재고 차감</option>
                          </select>
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">수량</label>
                          <input
                            v-model.number="stockForm.adjustQuantity"
                            type="number"
                            min="0"
                            placeholder="수량"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                      </div>
                      <div>
                        <label class="block text-xs text-gray-600 mb-1">메모 (선택)</label>
                        <input
                          v-model="stockForm.note"
                          type="text"
                          placeholder="예: 신규 입고"
                          class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                        />
                      </div>
                      <button @click="saveStock" class="w-full bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium text-sm transition-colors">
                        재고 저장
                      </button>
                    </div>

                    <!-- Stock History (Dummy) -->
                    <div>
                      <h3 class="font-medium text-gray-900 mb-3">재고 이력</h3>
                      <div class="border rounded-lg overflow-hidden">
                        <table class="w-full text-sm">
                          <thead class="bg-gray-50">
                            <tr>
                              <th class="px-4 py-2 text-left text-gray-600">날짜</th>
                              <th class="px-4 py-2 text-left text-gray-600">유형</th>
                              <th class="px-4 py-2 text-left text-gray-600">수량</th>
                              <th class="px-4 py-2 text-left text-gray-600">메모</th>
                            </tr>
                          </thead>
                          <tbody class="divide-y">
                            <tr v-for="history in dummyStockHistory" :key="history.id">
                              <td class="px-4 py-2 text-gray-600">{{ history.date }}</td>
                              <td class="px-4 py-2">
                                <span :class="['px-2 py-0.5 rounded text-xs font-medium', history.type === 'add' ? 'bg-green-100 text-green-700' : history.type === 'subtract' ? 'bg-red-100 text-red-700' : 'bg-blue-100 text-blue-700']">
                                  {{ history.type === 'add' ? '+' : history.type === 'subtract' ? '-' : '=' }}{{ history.quantity }}
                                </span>
                              </td>
                              <td class="px-4 py-2 font-medium">{{ history.result }}</td>
                              <td class="px-4 py-2 text-gray-500">{{ history.note }}</td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                  <div v-else class="text-center py-12 text-gray-500">
                    <p>재고를 관리할 상품을 선택하세요</p>
                  </div>
                </div>
              </div>
            </div>
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
import { productStockAPI } from '../api/productStock'
import { productPriceAPI } from '../api/productPrice'
import { useAlert } from '../composables/useAlert'
import Layout from '../components/Layout.vue'

const { success, error, warning, confirm } = useAlert()

const products = ref([])
const categories = ref([])
const selectedProduct = ref(null)
const isCreating = ref(false)
const editMode = ref(false)
const searchQuery = ref('')
const activeTab = ref('info')
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = ref(20)

const tabs = [
  { id: 'info', label: '상품 정보' },
  { id: 'price', label: '가격 설정' },
  { id: 'stock', label: '재고 관리' }
]

const form = ref({ id: null, name: '', categoryId: '' })

const priceForm = ref({
  price: 0,
  startDate: new Date().toISOString().split('T')[0],
  endDate: '2099-12-31'
})

const stockForm = ref({
  quantity: 100,
  reserved: 5,
  adjustType: 'set',
  adjustQuantity: 0,
  note: ''
})

// Price history from API
const priceHistory = ref([])

// Dummy data for stock history
const dummyStockHistory = [
  { id: 1, date: '2025-01-15', type: 'add', quantity: 50, result: 100, note: '신규 입고' },
  { id: 2, date: '2025-01-10', type: 'subtract', quantity: 10, result: 50, note: '주문 #1234' },
  { id: 3, date: '2025-01-05', type: 'set', quantity: 60, result: 60, note: '재고 실사' }
]

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

const filteredProducts = computed(() => {
  if (!searchQuery.value) return products.value
  return products.value.filter(p =>
    p.productName.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
})

onMounted(async () => {
  await loadProducts()
  const res = await categoryAPI.getHierarchies()
  categories.value = res.data || []
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
  } catch (error) {
    console.error('Failed to load products:', error)
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
  isCreating.value = false
  editMode.value = true
  const categoryId = product.smallClassId || product.mediumClassId || product.largeClassId
  form.value = { id: product.productId, name: product.productName, categoryId: categoryId }
  priceForm.value.price = product.productPrice

  // Load price history from API
  await loadPriceHistory(product.productId)

  // Load stock from API
  try {
    const stockRes = await productStockAPI.getByProductId(product.productId)
    if (stockRes.data) {
      stockForm.value.quantity = stockRes.data.quantity || 0
      stockForm.value.reserved = 0
    } else {
      stockForm.value.quantity = 0
      stockForm.value.reserved = 0
    }
  } catch (error) {
    console.error('Failed to load stock:', error)
    stockForm.value.quantity = 0
    stockForm.value.reserved = 0
  }
}

async function loadPriceHistory(productId) {
  try {
    const res = await productPriceAPI.getByProductId(productId)
    const prices = res.data || []
    const today = new Date().toISOString().split('T')[0]

    priceHistory.value = prices.map(p => ({
      id: p.id,
      price: parseFloat(p.price),
      startDate: p.startDate,
      endDate: p.endDate,
      active: p.startDate <= today && p.endDate >= today
    }))
  } catch (error) {
    console.error('Failed to load price history:', error)
    priceHistory.value = []
  }
}

function createNewProduct() {
  selectedProduct.value = null
  isCreating.value = true
  editMode.value = false
  activeTab.value = 'info'
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
    success('상품이 저장되었습니다!')
  } catch (err) {
    error('저장 실패: ' + err.message)
  }
}

async function deleteProduct(id) {
  const confirmed = await confirm('이 상품을 삭제하시겠습니까?', '삭제 확인')
  if (!confirmed) return
  try {
    await productAPI.delete(id)
    clearSelection()
    await loadProducts()
    success('상품이 삭제되었습니다!')
  } catch (err) {
    error('삭제 실패: ' + err.message)
  }
}

async function savePrice() {
  if (!selectedProduct.value) return

  try {
    await productPriceAPI.create(
      selectedProduct.value.productId,
      priceForm.value.price,
      priceForm.value.startDate,
      priceForm.value.endDate
    )

    // Reload price history
    await loadPriceHistory(selectedProduct.value.productId)

    // Reset form
    priceForm.value.price = 0
    priceForm.value.startDate = new Date().toISOString().split('T')[0]
    priceForm.value.endDate = '2099-12-31'

    success('가격이 저장되었습니다!')
  } catch (err) {
    console.error('Failed to save price:', err)
    error('가격 저장 실패: ' + (err.response?.data?.message || err.message))
  }
}

async function saveStock() {
  if (!selectedProduct.value) return

  try {
    let action = ''
    let quantity = stockForm.value.adjustQuantity

    switch (stockForm.value.adjustType) {
      case 'set':
        // For set, calculate the difference
        const diff = quantity - stockForm.value.quantity
        if (diff > 0) {
          action = 'ADD'
          quantity = diff
        } else if (diff < 0) {
          action = 'REMOVE'
          quantity = Math.abs(diff)
        } else {
          warning('변경사항이 없습니다.')
          return
        }
        break
      case 'add':
        action = 'ADD'
        break
      case 'subtract':
        action = 'REMOVE'
        break
    }

    await productStockAPI.adjust(selectedProduct.value.productId, quantity, action)

    // Reload stock
    const stockRes = await productStockAPI.getByProductId(selectedProduct.value.productId)
    if (stockRes.data) {
      stockForm.value.quantity = stockRes.data.quantity || 0
    }

    success('재고가 저장되었습니다!')
  } catch (err) {
    console.error('Failed to save stock:', err)
    error('재고 저장 실패: ' + (err.response?.data?.message || err.message))
  }
}

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR') + '원'
}
</script>
