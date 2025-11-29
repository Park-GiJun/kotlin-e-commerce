<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="visible" class="fixed inset-0 z-50 flex items-center justify-center">
        <!-- Backdrop -->
        <div class="absolute inset-0 bg-black/50" @click="handleClose"></div>

        <!-- Modal -->
        <div class="relative bg-white rounded-xl shadow-2xl max-w-md w-full mx-4 overflow-hidden transform transition-all">
          <!-- Header -->
          <div :class="['px-6 py-4 flex items-center gap-3', headerClass]">
            <!-- Icon -->
            <div :class="['w-10 h-10 rounded-full flex items-center justify-center', iconBgClass]">
              <!-- Success Icon -->
              <svg v-if="type === 'success'" class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
              </svg>
              <!-- Error Icon -->
              <svg v-else-if="type === 'error'" class="w-6 h-6 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
              <!-- Warning Icon -->
              <svg v-else-if="type === 'warning'" class="w-6 h-6 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
              </svg>
              <!-- Info Icon -->
              <svg v-else class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <h3 class="text-lg font-semibold text-gray-900">{{ title }}</h3>
          </div>

          <!-- Body -->
          <div class="px-6 py-4">
            <p class="text-gray-600 whitespace-pre-line">{{ message }}</p>
          </div>

          <!-- Footer -->
          <div class="px-6 py-4 bg-gray-50 flex justify-end gap-3">
            <button
              v-if="showCancel"
              @click="handleCancel"
              class="px-4 py-2 text-gray-700 bg-white border border-gray-300 rounded-lg hover:bg-gray-50 font-medium transition-colors"
            >
              {{ cancelText }}
            </button>
            <button
              @click="handleConfirm"
              :class="['px-4 py-2 text-white rounded-lg font-medium transition-colors', confirmButtonClass]"
            >
              {{ confirmText }}
            </button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  visible: {
    type: Boolean,
    default: false
  },
  type: {
    type: String,
    default: 'info', // 'success', 'error', 'warning', 'info'
    validator: (value) => ['success', 'error', 'warning', 'info'].includes(value)
  },
  title: {
    type: String,
    default: '알림'
  },
  message: {
    type: String,
    default: ''
  },
  confirmText: {
    type: String,
    default: '확인'
  },
  cancelText: {
    type: String,
    default: '취소'
  },
  showCancel: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['confirm', 'cancel', 'close'])

const headerClass = computed(() => {
  return 'border-b'
})

const iconBgClass = computed(() => {
  switch (props.type) {
    case 'success': return 'bg-green-100'
    case 'error': return 'bg-red-100'
    case 'warning': return 'bg-yellow-100'
    default: return 'bg-blue-100'
  }
})

const confirmButtonClass = computed(() => {
  switch (props.type) {
    case 'success': return 'bg-green-600 hover:bg-green-700'
    case 'error': return 'bg-red-600 hover:bg-red-700'
    case 'warning': return 'bg-yellow-600 hover:bg-yellow-700'
    default: return 'bg-teal-600 hover:bg-teal-700'
  }
})

function handleConfirm() {
  emit('confirm')
  emit('close')
}

function handleCancel() {
  emit('cancel')
  emit('close')
}

function handleClose() {
  if (!props.showCancel) {
    handleConfirm()
  }
}
</script>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.2s ease;
}

.modal-enter-active .relative,
.modal-leave-active .relative {
  transition: transform 0.2s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .relative {
  transform: scale(0.95);
}

.modal-leave-to .relative {
  transform: scale(0.95);
}
</style>
