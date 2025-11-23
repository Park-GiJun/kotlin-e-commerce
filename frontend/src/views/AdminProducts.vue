<template>
  <Layout>
    <div class="h-full flex flex-col bg-gray-50">
      <!-- Header -->
      <div class="bg-white border-b px-6 py-4 flex-shrink-0">
        <div class="max-w-7xl mx-auto flex items-center justify-between">
          <div>
            <h1 class="text-xl font-bold text-gray-900">Product Management</h1>
            <p class="text-sm text-gray-500">Manage products, prices, and inventory</p>
          </div>
          <button @click="createNewProduct()" class="bg-teal-600 hover:bg-teal-700 text-white px-4 py-2 rounded-lg font-medium text-sm transition-colors flex items-center gap-2">
            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
            </svg>
            Add Product
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
                  placeholder="Search products..."
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
                  <p class="text-sm">No products found</p>
                </div>
              </div>
              <div v-if="totalPages > 1" class="px-4 py-2 border-t bg-gray-50 flex items-center justify-between text-sm">
                <span class="text-gray-600">Page {{ currentPage + 1 }} / {{ totalPages }}</span>
                <div class="flex gap-2">
                  <button @click="prevPage" :disabled="currentPage === 0" class="px-2 py-1 bg-white border rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">Prev</button>
                  <button @click="nextPage" :disabled="currentPage >= totalPages - 1" class="px-2 py-1 bg-white border rounded hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed">Next</button>
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
                      <label class="block text-sm font-medium text-gray-700 mb-1">Product Name</label>
                      <input
                        v-model="form.name"
                        type="text"
                        placeholder="Enter product name"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      />
                    </div>
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-1">Category</label>
                      <select
                        v-model="form.categoryId"
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-teal-500 focus:border-transparent"
                      >
                        <option value="">Select Category</option>
                        <option v-for="cat in flatCategories" :key="cat.id" :value="cat.id">
                          {{ '  '.repeat(cat.depth) }}{{ cat.depth > 0 ? '└ ' : '' }}{{ cat.name }}
                        </option>
                      </select>
                    </div>
                    <div class="flex gap-3 pt-4">
                      <button @click="saveProduct" class="flex-1 bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium transition-colors">
                        Save
                      </button>
                      <button v-if="editMode" @click="deleteProduct(selectedProduct.productId)" class="flex-1 bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg font-medium transition-colors">
                        Delete
                      </button>
                      <button @click="clearSelection" class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 py-2 rounded-lg font-medium transition-colors">
                        Cancel
                      </button>
                    </div>
                  </div>
                  <div v-else class="text-center py-12 text-gray-500">
                    <svg class="w-16 h-16 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
                    </svg>
                    <p>Select a product or create new one</p>
                  </div>
                </div>

                <!-- Price Settings Tab -->
                <div v-if="activeTab === 'price'">
                  <div v-if="selectedProduct" class="space-y-4">
                    <div class="bg-teal-50 rounded-lg p-4 mb-4">
                      <div class="text-sm text-teal-700">Current Product</div>
                      <div class="font-bold text-teal-900">{{ selectedProduct.productName }}</div>
                    </div>

                    <!-- Current Price -->
                    <div class="bg-gray-50 rounded-lg p-4">
                      <div class="text-sm text-gray-600 mb-1">Current Price</div>
                      <div class="text-2xl font-bold text-gray-900">{{ formatPrice(selectedProduct.productPrice) }}</div>
                    </div>

                    <!-- Add New Price -->
                    <div class="border rounded-lg p-4 space-y-3">
                      <h3 class="font-medium text-gray-900">Set New Price</h3>
                      <div class="grid grid-cols-3 gap-4">
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">Price</label>
                          <input
                            v-model.number="priceForm.price"
                            type="number"
                            placeholder="Price"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">Start Date</label>
                          <input
                            v-model="priceForm.startDate"
                            type="date"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">End Date</label>
                          <input
                            v-model="priceForm.endDate"
                            type="date"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                      </div>
                      <button @click="savePrice" class="w-full bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium text-sm transition-colors">
                        Save Price
                      </button>
                    </div>

                    <!-- Price History (Dummy) -->
                    <div>
                      <h3 class="font-medium text-gray-900 mb-3">Price History</h3>
                      <div class="border rounded-lg overflow-hidden">
                        <table class="w-full text-sm">
                          <thead class="bg-gray-50">
                            <tr>
                              <th class="px-4 py-2 text-left text-gray-600">Price</th>
                              <th class="px-4 py-2 text-left text-gray-600">Start</th>
                              <th class="px-4 py-2 text-left text-gray-600">End</th>
                              <th class="px-4 py-2 text-left text-gray-600">Status</th>
                            </tr>
                          </thead>
                          <tbody class="divide-y">
                            <tr v-for="history in dummyPriceHistory" :key="history.id">
                              <td class="px-4 py-2 font-medium">{{ formatPrice(history.price) }}</td>
                              <td class="px-4 py-2 text-gray-600">{{ history.startDate }}</td>
                              <td class="px-4 py-2 text-gray-600">{{ history.endDate }}</td>
                              <td class="px-4 py-2">
                                <span :class="['px-2 py-0.5 rounded text-xs font-medium', history.active ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-600']">
                                  {{ history.active ? 'Active' : 'Expired' }}
                                </span>
                              </td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                  <div v-else class="text-center py-12 text-gray-500">
                    <p>Select a product to manage prices</p>
                  </div>
                </div>

                <!-- Stock Settings Tab -->
                <div v-if="activeTab === 'stock'">
                  <div v-if="selectedProduct" class="space-y-4">
                    <div class="bg-teal-50 rounded-lg p-4 mb-4">
                      <div class="text-sm text-teal-700">Current Product</div>
                      <div class="font-bold text-teal-900">{{ selectedProduct.productName }}</div>
                    </div>

                    <!-- Current Stock -->
                    <div class="grid grid-cols-3 gap-4">
                      <div class="bg-gray-50 rounded-lg p-4">
                        <div class="text-sm text-gray-600 mb-1">Available Stock</div>
                        <div class="text-2xl font-bold text-gray-900">{{ stockForm.quantity }}</div>
                      </div>
                      <div class="bg-yellow-50 rounded-lg p-4">
                        <div class="text-sm text-yellow-700 mb-1">Reserved</div>
                        <div class="text-2xl font-bold text-yellow-900">{{ stockForm.reserved }}</div>
                      </div>
                      <div class="bg-blue-50 rounded-lg p-4">
                        <div class="text-sm text-blue-700 mb-1">Total</div>
                        <div class="text-2xl font-bold text-blue-900">{{ stockForm.quantity + stockForm.reserved }}</div>
                      </div>
                    </div>

                    <!-- Update Stock -->
                    <div class="border rounded-lg p-4 space-y-3">
                      <h3 class="font-medium text-gray-900">Update Stock</h3>
                      <div class="grid grid-cols-2 gap-4">
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">Adjustment Type</label>
                          <select v-model="stockForm.adjustType" class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm">
                            <option value="set">Set Quantity</option>
                            <option value="add">Add Stock</option>
                            <option value="subtract">Remove Stock</option>
                          </select>
                        </div>
                        <div>
                          <label class="block text-xs text-gray-600 mb-1">Quantity</label>
                          <input
                            v-model.number="stockForm.adjustQuantity"
                            type="number"
                            min="0"
                            placeholder="Quantity"
                            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                          />
                        </div>
                      </div>
                      <div>
                        <label class="block text-xs text-gray-600 mb-1">Note (optional)</label>
                        <input
                          v-model="stockForm.note"
                          type="text"
                          placeholder="e.g., New shipment arrived"
                          class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm"
                        />
                      </div>
                      <button @click="saveStock" class="w-full bg-teal-600 hover:bg-teal-700 text-white py-2 rounded-lg font-medium text-sm transition-colors">
                        Update Stock
                      </button>
                    </div>

                    <!-- Stock History (Dummy) -->
                    <div>
                      <h3 class="font-medium text-gray-900 mb-3">Stock History</h3>
                      <div class="border rounded-lg overflow-hidden">
                        <table class="w-full text-sm">
                          <thead class="bg-gray-50">
                            <tr>
                              <th class="px-4 py-2 text-left text-gray-600">Date</th>
                              <th class="px-4 py-2 text-left text-gray-600">Type</th>
                              <th class="px-4 py-2 text-left text-gray-600">Qty</th>
                              <th class="px-4 py-2 text-left text-gray-600">Note</th>
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
                    <p>Select a product to manage stock</p>
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
import Layout from '../components/Layout.vue'

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
  { id: 'info', label: 'Product Info' },
  { id: 'price', label: 'Price Settings' },
  { id: 'stock', label: 'Stock Management' }
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

// Dummy data for price history
const dummyPriceHistory = computed(() => {
  if (!selectedProduct.value) return []
  return [
    { id: 1, price: selectedProduct.value.productPrice, startDate: '2025-01-01', endDate: '2099-12-31', active: true },
    { id: 2, price: selectedProduct.value.productPrice * 1.1, startDate: '2024-10-01', endDate: '2024-12-31', active: false },
    { id: 3, price: selectedProduct.value.productPrice * 1.2, startDate: '2024-07-01', endDate: '2024-09-30', active: false }
  ]
})

// Dummy data for stock history
const dummyStockHistory = [
  { id: 1, date: '2025-01-15', type: 'add', quantity: 50, result: 100, note: 'New shipment' },
  { id: 2, date: '2025-01-10', type: 'subtract', quantity: 10, result: 50, note: 'Order #1234' },
  { id: 3, date: '2025-01-05', type: 'set', quantity: 60, result: 60, note: 'Inventory count' }
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

function selectProduct(product) {
  selectedProduct.value = product
  isCreating.value = false
  editMode.value = true
  const categoryId = product.smallClassId || product.mediumClassId || product.largeClassId
  form.value = { id: product.productId, name: product.productName, categoryId: categoryId }
  priceForm.value.price = product.productPrice
  stockForm.value.quantity = Math.floor(Math.random() * 200) + 10
  stockForm.value.reserved = Math.floor(Math.random() * 10)
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
    alert('Product saved successfully!')
  } catch (error) {
    alert('Failed to save: ' + error.message)
  }
}

async function deleteProduct(id) {
  if (!confirm('Are you sure you want to delete this product?')) return
  try {
    await productAPI.delete(id)
    clearSelection()
    await loadProducts()
    alert('Product deleted successfully!')
  } catch (error) {
    alert('Failed to delete: ' + error.message)
  }
}

function savePrice() {
  // TODO: Implement actual API call
  alert(`Price saved: ${formatPrice(priceForm.value.price)} (${priceForm.value.startDate} ~ ${priceForm.value.endDate})\n\nNote: This is a dummy implementation. API not yet connected.`)
}

function saveStock() {
  // TODO: Implement actual API call
  let message = ''
  switch (stockForm.value.adjustType) {
    case 'set':
      message = `Stock set to ${stockForm.value.adjustQuantity}`
      break
    case 'add':
      message = `Added ${stockForm.value.adjustQuantity} to stock`
      break
    case 'subtract':
      message = `Removed ${stockForm.value.adjustQuantity} from stock`
      break
  }
  alert(`${message}\n${stockForm.value.note ? 'Note: ' + stockForm.value.note : ''}\n\nNote: This is a dummy implementation. API not yet connected.`)
}

function formatPrice(price) {
  return (price || 0).toLocaleString('ko-KR') + '원'
}
</script>
