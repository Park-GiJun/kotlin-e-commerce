<template>
  <div>
    <div class="relative">
      <!-- Toggle button for categories with children -->
      <button
        v-if="hasChildren"
        @click.stop="toggleExpanded"
        class="absolute left-2 top-1/2 -translate-y-1/2 z-10 p-1 hover:bg-gray-200 rounded transition-colors"
        :style="{ left: `${category.depth * 1.2 + 0.5}rem` }"
      >
        <svg
          class="w-3 h-3 text-gray-500 transition-transform"
          :class="{ 'rotate-90': isExpanded }"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
        </svg>
      </button>

      <!-- Category button -->
      <button
        @click="$emit('select', category.id)"
        :class="[
          'w-full py-2.5 text-left transition-colors border-l-4',
          selectedId === category.id
            ? 'bg-primary-50 border-primary-600 text-primary-700 font-medium'
            : 'border-transparent hover:bg-gray-50 text-gray-700'
        ]"
        :style="{ paddingLeft: `${(category.depth * 1.2) + (hasChildren ? 2.5 : 1)}rem`, paddingRight: '1rem' }"
      >
        <div class="flex items-center justify-between">
          <span class="flex items-center gap-2">
            <span v-if="category.depth > 0 && !hasChildren" class="text-gray-400 text-xs">└─</span>
            <span class="text-sm">{{ category.name }}</span>
            <span v-if="hasChildren" class="text-xs text-gray-400">({{ category.children.length }})</span>
          </span>
          <svg
            v-if="selectedId === category.id"
            class="w-4 h-4 text-primary-600 flex-shrink-0"
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path
              fill-rule="evenodd"
              d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
              clip-rule="evenodd"
            />
          </svg>
        </div>
      </button>
    </div>

    <!-- 자식 카테고리들을 재귀적으로 렌더링 (펼쳐진 경우에만) -->
    <div
      v-if="hasChildren && isExpanded"
      class="transition-all duration-200"
    >
      <CategoryFilter
        v-for="child in category.children"
        :key="child.id"
        :category="child"
        :selectedId="selectedId"
        @select="(id) => $emit('select', id)"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
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

// 대분류(depth 0)는 기본으로 펼쳐져 있고, 하위는 접혀있음
const isExpanded = ref(props.category.depth === 0)

const hasChildren = computed(() => {
  return props.category.children && props.category.children.length > 0
})

function toggleExpanded() {
  isExpanded.value = !isExpanded.value
}
</script>