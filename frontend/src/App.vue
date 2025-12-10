<template>
  <router-view />
  <AlertModal
    :visible="alertState.visible"
    :type="alertState.type"
    :title="alertState.title"
    :message="alertState.message"
    :confirm-text="alertState.confirmText"
    :cancel-text="alertState.cancelText"
    :show-cancel="alertState.showCancel"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    @close="closeAlert"
  />
</template>

<script setup>
import AlertModal from './components/AlertModal.vue'
import { useAlert } from './composables/useAlert'

const { alertState, handleConfirm, handleCancel, closeAlert } = useAlert()
</script>
