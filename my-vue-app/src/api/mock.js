import Mock from "mockjs";
import homeApi from './mockData/home';
import userApi from './mockData/user';

// Mock API 配置
Mock.mock("/api/home/getuserId", "post", userApi.getUserId); // 新增
Mock.mock("/api/home/getuserContent", "post", userApi.getUserContent); // 新增
Mock.mock("/api/user/getUserContent", "get", userApi.getUserContent);
