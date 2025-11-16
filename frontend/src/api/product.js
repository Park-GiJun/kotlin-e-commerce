import axios from './axios'

export const productAPI = {
  async getAll(page = 0, size = 10) {
    const response = await axios.get('/products', {
      params: { page, size }
    })
    return response.data
  },

  async getByCategory(categoryId, page = 0, size = 10) {
    const response = await axios.get(`/products/category/${categoryId}`, {
      params: { page, size }
    })
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
