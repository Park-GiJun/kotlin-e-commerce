<template>
  <Layout>
    <div class="max-w-7xl mx-auto px-4 py-6">
      <div class="mb-6">
        <h2 class="text-3xl font-bold text-gray-900">카테고리 관리</h2>
        <p class="text-gray-600 mt-1">카테고리를 추가, 수정, 삭제할 수 있습니다 (최대 3단계)</p>
      </div>

      <!-- Master-Detail Layout -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <!-- Master: 카테고리 목록 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden flex flex-col" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b flex justify-between items-center">
            <h3 class="text-lg font-semibold text-gray-900">카테고리 목록</h3>
            <button @click="createNewCategory()" class="hover:opacity-90 px-4 py-2 rounded-lg font-medium text-sm transition-all" style="background-color: #0d9488; color: white;">
              <span class="flex items-center gap-1">
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                </svg>
                추가
              </span>
            </button>
          </div>

          <div class="flex-1 overflow-y-auto">
            <div v-if="categories.length" class="divide-y divide-gray-200">
              <CategoryItem
                v-for="cat in categories"
                :key="cat.id"
                :category="cat"
                :selectedId="selectedCategory?.id"
                @select="selectCategory"
              />
            </div>
            <div v-else class="px-6 py-12 text-center text-gray-500">
              <svg class="w-16 h-16 mx-auto mb-4 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
              </svg>
              <p class="text-lg">등록된 카테고리가 없습니다</p>
            </div>
          </div>
        </div>

        <!-- Detail: 카테고리 상세 -->
        <div class="bg-white shadow-lg rounded-lg overflow-hidden" style="max-height: calc(100vh - 200px);">
          <div class="px-6 py-4 bg-gray-50 border-b">
            <h3 class="text-lg font-semibold text-gray-900">{{ editMode ? '카테고리 수정' : '카테고리 추가' }}</h3>
          </div>

          <div v-if="selectedCategory || isCreating" class="p-6 overflow-y-auto" style="max-height: calc(100vh - 320px);">
            <form @submit.prevent="saveCategory" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">카테고리명</label>
                <input
                  v-model="form.name"
                  type="text"
                  required
                  placeholder="카테고리명을 입력하세요"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent">
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">부모 카테고리</label>
                <select
                  v-model="form.parentId"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
                >
                  <option :value="null">없음 (최상위 카테고리)</option>
                  <option
                    v-for="cat in availableParentCategories"
                    :key="cat.id"
                    :value="cat.id"
                    :disabled="cat.disabled"
                  >
                    {{ '　'.repeat(cat.depth) }}{{ cat.depth > 0 ? '└─ ' : '' }}{{ cat.name }}
                    {{ cat.disabled ? '(선택 불가)' : '' }}
                  </option>
                </select>
                <p class="text-xs text-gray-500 mt-1">최대 3단계까지 생성할 수 있습니다</p>
              </div>

              <div v-if="selectedCategory && selectedCategory.depth > 0" class="bg-gray-50 rounded-lg p-4">
                <div class="text-sm text-gray-600">
                  <span class="font-medium">현재 경로:</span> {{ getCategoryPath(selectedCategory) }}
                </div>
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
                  @click="deleteCategory(selectedCategory.id)"
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
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z" />
            </svg>
            <h3 class="text-lg font-medium text-gray-900 mb-2">카테고리를 선택하세요</h3>
            <p class="text-gray-500">좌측 목록에서 카테고리를 선택하거나<br>새 카테고리를 추가하세요</p>
          </div>
        </div>
      </div>
    </div>
  </Layout>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { categoryAPI } from '../api/category'
import Layout from '../components/Layout.vue'
import CategoryItem from '../components/CategoryItem.vue'

const categories = ref([])
const selectedCategory = ref(null)
const isCreating = ref(false)
const editMode = ref(false)
const form = ref({ id: null, name: '', parentId: null })

// 부모 카테고리로 선택 가능한 카테고리 목록 (평면화)
const availableParentCategories = computed(() => {
  const flatList = []

  const flatten = (cats, excludeIds = []) => {
    cats.forEach(cat => {
      // 자기 자신과 자신의 자식들은 제외
      const isExcluded = excludeIds.includes(cat.id)
      // depth가 2인 경우 자식을 가질 수 없음 (최대 3단계)
      const isMaxDepth = cat.depth >= 2

      flatList.push({
        id: cat.id,
        name: cat.name,
        depth: cat.depth,
        disabled: isExcluded || isMaxDepth
      })

      if (cat.children && cat.children.length > 0) {
        flatten(cat.children, excludeIds)
      }
    })
  }

  // 수정 모드일 때: 선택된 카테고리와 그 하위 카테고리들을 제외
  if (editMode.value && selectedCategory.value) {
    const excludeIds = getDescendantIds(selectedCategory.value)
    excludeIds.push(selectedCategory.value.id)
    flatten(categories.value, excludeIds)
  } else {
    flatten(categories.value, [])
  }

  return flatList
})

onMounted(loadCategories)

async function loadCategories() {
  const res = await categoryAPI.getHierarchies()
  categories.value = res.data || []
}

// 특정 카테고리의 모든 하위 카테고리 ID 가져오기
function getDescendantIds(category) {
  const ids = []
  if (category.children && category.children.length > 0) {
    category.children.forEach(child => {
      ids.push(child.id)
      ids.push(...getDescendantIds(child))
    })
  }
  return ids
}

function selectCategory(cat) {
  selectedCategory.value = cat
  isCreating.value = false
  editMode.value = true
  form.value = { id: cat.id, name: cat.name, parentId: cat.parentId || null }
}

function createNewCategory() {
  selectedCategory.value = null
  isCreating.value = true
  editMode.value = false
  form.value = { id: null, name: '', parentId: null }
}

function clearSelection() {
  selectedCategory.value = null
  isCreating.value = false
  editMode.value = false
  form.value = { id: null, name: '', parentId: null }
}

function getCategoryPath(category) {
  const path = []
  let current = category
  while (current) {
    path.unshift(current.name)
    current = current.parent
  }
  return path.join(' > ')
}

async function saveCategory() {
  try {
    if (editMode.value) {
      await categoryAPI.update(form.value.id, form.value.name, form.value.parentId)
    } else {
      await categoryAPI.create(form.value.name, form.value.parentId)
    }
    clearSelection()
    await loadCategories()
  } catch (error) {
    alert('저장 실패: ' + error.message)
  }
}

async function deleteCategory(id) {
  if (!confirm('삭제하시겠습니까?')) return
  try {
    await categoryAPI.delete(id)
    clearSelection()
    await loadCategories()
  } catch (error) {
    alert('삭제 실패: ' + error.message)
  }
}
</script>