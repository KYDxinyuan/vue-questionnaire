import request from "./request";

export default {
    // 获取用户ID
    getUserId() {
        return request({
            url: "/home/getuserId",
            method: "post", // 使用 POST 方法
        });
    },
    
    // 提交用户内容
    getUserContent(data) {
        if (!data || typeof data !== 'object') {
            throw new Error("Invalid data provided for getUserContent");
        }
        return request({
            url: "/home/getuserContent",
            method: "post", // 使用 POST 方法
            data, // 传递参数
        });
    }
};