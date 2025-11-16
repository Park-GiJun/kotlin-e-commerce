import axios from './axios'

export const categoryAPI = {
  async getAll() {
    const response = await axios.get('/product-categories')
    return response.data
  },

  async getHierarchies() {
    const response = await axios.get('/product-categories/hierarchies')
    return response.data
  },

  async getById(id) {
    const response = await axios.get(`/product-categories/${id}`)
    return response.data
  },

  async create(name, parentId = null) {
    const response = await axios.post('/product-categories', { name, parentId })
    return response.data
  },

  async update(id, name, parentId = null) {
    const response = await axios.put(`/product-categories/${id}`, { id, name, parentId })
    return response.data
  },

  async delete(id) {
    const response = await axios.delete(`/product-categories/${id}`)
    return response.data
  }
}
