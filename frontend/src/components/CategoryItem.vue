<template>
  <div>
    <div
      @click="$emit('select', category)"
      :class="[
        'px-6 py-3.5 cursor-pointer transition-all duration-200 border-l-4',
        selectedId === category.id
          ? 'bg-primary-50 border-primary-600 shadow-sm'
          : 'border-transparent hover:bg-gray-50 hover:border-gray-200'
      ]"
      :style="{ paddingLeft: `${(category.depth * 1.5) + 1.5}rem` }">
      <div class="flex items-center gap-3">
        <!-- 아이콘 -->
        <div :class="[
          'w-8 h-8 rounded-lg flex items-center justify-center flex-shrink-0',
          selectedId === category.id
            ? 'bg-primary-100'
            : 'bg-gray-100'
        ]">
          <svg
            :class="[
              'w-4 h-4',
              selectedId === category.id ? 'text-primary-600' : 'text-gray-600'
            ]"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24">
            <path
              v-if="category.depth === 0"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z"
            />
            <path
              v-else-if="category.depth === 1"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
            />
            <path
              v-else
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M7 7h.01M7 3h5c.512 0 1.024.195 1.414.586l7 7a2 2 0 010 2.828l-7 7a2 2 0 01-2.828 0l-7-7A1.994 1.994 0 013 12V7a4 4 0 014-4z"
            />
          </svg>
        </div>

        <!-- 카테고리 정보 -->
        <div class="flex-1 min-w-0">
          <div class="flex items-center gap-2">
            <span v-if="category.depth > 0" class="text-gray-400 text-xs">{{ '└─'.repeat(1) }}</span>
            <p :class="[
              'font-medium truncate',
              selectedId === category.id ? 'text-primary-600' : 'text-gray-900'
            ]">
              {{ category.name }}
            </p>
          </div>
          <div class="flex items-center gap-2 mt-0.5">
            <span :class="[
              'inline-flex items-center px-2 py-0.5 rounded text-xs font-medium',
              category.depth === 0 ? 'bg-blue-100 text-blue-800' :
              category.depth === 1 ? 'bg-green-100 text-green-800' :
              'bg-purple-100 text-purple-800'
            ]">
              {{ category.depth === 0 ? '대분류' : category.depth === 1 ? '중분류' : '소분류' }}
            </span>
            <span v-if="category.children && category.children.length > 0" class="text-xs text-gray-500">
              하위 {{ category.children.length }}개
            </span>
          </div>
        </div>

        <!-- 선택 표시 -->
        <div v-if="selectedId === category.id" class="flex-shrink-0">
          <svg class="w-5 h-5 text-primary-600" fill="currentColor" viewBox="0 0 20 20">
            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
          </svg>
        </div>
      </div>
    </div>

    <!-- 자식 카테고리들을 재귀적으로 렌더링 -->
    <CategoryItem
      v-for="child in category.children"
      :key="child.id"
      :category="child"
      :selectedId="selectedId"
      @select="(cat) => $emit('select', cat)"
    />
  </div>
</template>

<script setup>
defineProps({
  category: {
    type: Object,
    required: true
  },
  selectedId: {
    type: Number,
    default: null
  }
})

defineEmits(['select'])
</script>