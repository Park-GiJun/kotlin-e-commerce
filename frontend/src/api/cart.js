import axios from './axios'

export const cartAPI = {
  // 내 장바구니 조회
  getMyCart() {
    return axios.get('/carts').then(res => res.data)
  },

  // 장바구니에 상품 추가
  addToCart(productId, qty = 1) {
    return axios.post('/carts', {
      productId,
      qty,
      action: 'ADD'
    }).then(res => res.data)
  },

  // 장바구니에서 상품 제거
  removeFromCart(productId, qty = 1) {
    return axios.post('/carts', {
      productId,
      qty,
      action: 'REMOVE'
    }).then(res => res.data)
  },

  // 수량 업데이트 (절대값 설정을 위해 현재 수량과 비교 필요)
  updateQuantity(productId, newQty, currentQty) {
    const diff = newQty - currentQty
    if (diff === 0) return Promise.resolve({ success: true, data: [] })

    return axios.post('/carts', {
      productId,
      qty: Math.abs(diff),
      action: diff > 0 ? 'ADD' : 'REMOVE'
    }).then(res => res.data)
  }
}

export default cartAPI
