import request from "./request";

export default {
    getUserId() {
        return request({
            url: "/home/getuserId",
            method: "post", // 改为 POST 方法
        });
    },
    getUserContent(data) {
        return request({
            url: "/home/getuserContent",
            method: "post", // 使用 POST 方法
            data, // 传递参数
        });
    },
    getUserContent(){
        return request({
            url: "/user/getUserContent",
            method:"get",
        })
    }
};
