export default {
    getUserId: () => {
        return {
            code: 200,
            data: {
                id: Mock.mock("@guid"), // 使用 Mock.js 生成随机 GUID 作为用户 ID
            }
        };
    },
    getUserContent: (options) => {
        const { userId, selectedOption, selectedOptionText } = JSON.parse(options.body);
        return {
            code: 200,
            message: "提交成功",
            data: {
                userId,
                selectedOption,
                selectedOptionText,
            }
        };
    },
};
