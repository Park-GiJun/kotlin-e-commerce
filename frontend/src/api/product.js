import axios from './axios'

export const productAPI = {
  async getAll() {
    const response = await axios.get('/products')
    return response.data
  },

  async getById(id) {
    const response = await axios.get(`/products/${id}`)
    return response.data
  },

  async create(categoryId, name) {
    const response = await axios.post('/products', { categoryId, name })
    return response.data
  },

  async update(id, categoryId, name) {
    const response = await axios.put(`/products/${id}`, { id, categoryId, name })
    return response.data
  },

  async delete(id) {
    const response = await axios.delete(`/products/${id}`)
    return response.data
  }
}
