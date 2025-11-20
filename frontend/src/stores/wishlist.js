import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useWishlistStore = defineStore('wishlist', () => {
  const items = ref([])

  // Load wishlist from localStorage
  const savedWishlist = localStorage.getItem('wishlist_items')
  if (savedWishlist) {
    items.value = JSON.parse(savedWishlist)
  }

  // Computed properties
  const itemCount = computed(() => items.value.length)

  const isInWishlist = computed(() => {
    return (productId) => items.value.some(item => item.id === productId)
  })

  // Actions
  function addToWishlist(product) {
    const exists = items.value.some(item => item.id === product.id)

    if (!exists) {
      items.value.push({
        id: product.id,
        name: product.name,
        price: product.price,
        originalPrice: product.originalPrice,
        image: product.image,
        categoryName: product.categoryName,
        addedAt: new Date().toISOString()
      })
      saveToLocalStorage()
      return true
    }
    return false
  }

  function removeFromWishlist(productId) {
    const index = items.value.findIndex(item => item.id === productId)
    if (index > -1) {
      items.value.splice(index, 1)
      saveToLocalStorage()
      return true
    }
    return false
  }

  function toggleWishlist(product) {
    if (isInWishlist.value(product.id)) {
      removeFromWishlist(product.id)
      return false
    } else {
      addToWishlist(product)
      return true
    }
  }

  function clearWishlist() {
    items.value = []
    saveToLocalStorage()
  }

  function saveToLocalStorage() {
    localStorage.setItem('wishlist_items', JSON.stringify(items.value))
  }

  return {
    items,
    itemCount,
    isInWishlist,
    addToWishlist,
    removeFromWishlist,
    toggleWishlist,
    clearWishlist
  }
})
