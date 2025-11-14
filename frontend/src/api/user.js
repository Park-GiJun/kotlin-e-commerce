import axios from './axios'

export const userAPI = {
  async getByEmail(email) {
    const response = await axios.get(`/users/${email}`)
    return response.data
  },

  async delete(userId) {
    const response = await axios.delete(`/users/${userId}`)
    return response.data
  }
}
