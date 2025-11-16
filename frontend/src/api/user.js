import axios from './axios'

export const userAPI = {
  async getAll(page = 0, size = 10) {
    const response = await axios.get('/users', { params: { page, size } })
    return response.data
  },

  async getByEmail(email) {
    const response = await axios.get(`/users/${email}`)
    return response.data
  },

  async delete(userId) {
    const response = await axios.delete(`/users/${userId}`)
    return response.data
  }
}
