import axios from './axios'

export const authAPI = {
  async login(email, password) {
    const response = await axios.post('/users/login', { email, password })
    return response.data
  },

  async register(email, name, password, role = 'USER') {
    const response = await axios.post('/users/register', { email, name, password, role })
    return response.data
  }
}
