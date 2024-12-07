import { createApp } from 'vue';
import App from './App.vue';
import "@/assets/less/index.less";
import router from "./router";
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import { createPinia } from "pinia";
import "@/api/mock.js";
import api from "@/api/api";

const pinia = createPinia();
const app = createApp(App);

// 将 api 添加到全局属性
app.config.globalProperties.$api = api;

// 使用插件
app.use(ElementPlus);
app.use(pinia);
app.use(router);

// 注册 Element Plus 图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

// 最后挂载应用
app.mount('#app');