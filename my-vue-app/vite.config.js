import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: [
      {
        find: "@",
        replacement: "/src",
      },
    ],
  },
  // 配置开发服务器的代理
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // 后端 Spring Boot 服务地址
        changeOrigin: true,
        secure: false,
      },
    },
  },
});

