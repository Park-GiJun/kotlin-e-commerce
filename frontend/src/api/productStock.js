import axios from './axios'

export const productStockAPI = {
  async getAll() {
    const response = await axios.get('/product-stocks')
    return response.data
  },

  async getByProductId(productId) {
    const response = await axios.get(`/product-stocks/product/${productId}`)
    return response.data
  },

  async adjust(productId, quantity, action) {
    const response = await axios.post('/product-stocks/adjust', {
      productId,
      quantity,
      action
    })
    return response.data
  }
}
