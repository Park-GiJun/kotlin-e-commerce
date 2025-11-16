import axios from './axios'

export const productPriceAPI = {
  async getAll() {
    const response = await axios.get('/product-prices')
    return response.data
  },

  async getById(id) {
    const response = await axios.get(`/product-prices/${id}`)
    return response.data
  },

  async getByProductId(productId) {
    const response = await axios.get(`/product-prices/product/${productId}`)
    return response.data
  },

  async create(productId, price, startDate, endDate) {
    const response = await axios.post('/product-prices', {
      productId,
      price: price.toString(),
      startDate,
      endDate
    })
    return response.data
  },

  async update(id, productId, price, startDate, endDate) {
    const response = await axios.put(`/product-prices/${id}`, {
      productPriceId: id,
      productId,
      price: price.toString(),
      startDate,
      endDate
    })
    return response.data
  },

  async delete(id) {
    const response = await axios.delete(`/product-prices/${id}`)
    return response.data
  }
}
