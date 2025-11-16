import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  server: {
    host: true,
    allowedHosts: [
      'localhost',
      '127.0.0.1',
      'gijun.net',
      '.gijun.net'  // 서브도메인 포함
    ],
    proxy: {
      '/api': {
        target: 'http://210.121.177.150:9832',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
