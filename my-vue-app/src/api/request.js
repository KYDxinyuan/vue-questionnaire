import axios from "axios";
import { ElMessage } from "element-plus";
import config from "@/config";

// 创建 axios 实例
const service = axios.create({
    baseURL: config.baseApi,
    timeout: 5000, // 设置请求超时时间
});

const NETWORK_ERROR = '网络错误...';

// 请求拦截器
service.interceptors.request.use(
    (config) => {
        // 在这里可以添加请求头或其他配置
        return config;
    },
    (error) => {
        // 处理请求错误
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    (response) => {
        const { code, data, msg } = response.data;
        if (code === 200) {
            return data;
        } else {
            ElMessage.error(msg || NETWORK_ERROR);
            return Promise.reject(msg || NETWORK_ERROR);
        }
    },
    (error) => {
        ElMessage.error(NETWORK_ERROR);
        return Promise.reject(error);
    }
);

// 请求函数
function request(options) {
    options.method = options.method || "get";
    if (options.method.toLowerCase() === "get") {
        options.params = options.data;
    }

    let isMock = config.mock;
    if (typeof options.mock !== "undefined") {
        isMock = options.mock;
    }

    if (config.env === 'prod') {
        service.defaults.baseURL = config.baseApi;
    } else {
        service.defaults.baseURL = isMock ? config.mockApi : config.baseApi;
    }

    return service(options);
}

export default request;