import { ref } from 'vue'

const alertState = ref({
  visible: false,
  type: 'info',
  title: '알림',
  message: '',
  confirmText: '확인',
  cancelText: '취소',
  showCancel: false,
  onConfirm: null,
  onCancel: null
})

export function useAlert() {
  function showAlert(options) {
    return new Promise((resolve) => {
      alertState.value = {
        visible: true,
        type: options.type || 'info',
        title: options.title || '알림',
        message: options.message || '',
        confirmText: options.confirmText || '확인',
        cancelText: options.cancelText || '취소',
        showCancel: options.showCancel || false,
        onConfirm: () => resolve(true),
        onCancel: () => resolve(false)
      }
    })
  }

  function success(message, title = '성공') {
    return showAlert({ type: 'success', title, message })
  }

  function error(message, title = '오류') {
    return showAlert({ type: 'error', title, message })
  }

  function warning(message, title = '경고') {
    return showAlert({ type: 'warning', title, message })
  }

  function info(message, title = '알림') {
    return showAlert({ type: 'info', title, message })
  }

  function confirm(message, title = '확인') {
    return showAlert({
      type: 'warning',
      title,
      message,
      showCancel: true,
      confirmText: '확인',
      cancelText: '취소'
    })
  }

  function closeAlert() {
    alertState.value.visible = false
  }

  function handleConfirm() {
    if (alertState.value.onConfirm) {
      alertState.value.onConfirm()
    }
    closeAlert()
  }

  function handleCancel() {
    if (alertState.value.onCancel) {
      alertState.value.onCancel()
    }
    closeAlert()
  }

  return {
    alertState,
    showAlert,
    success,
    error,
    warning,
    info,
    confirm,
    closeAlert,
    handleConfirm,
    handleCancel
  }
}
