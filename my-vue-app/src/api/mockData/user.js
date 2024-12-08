import Mock from 'mockjs';

// 定义用户选择列表
const userChoices = [
  "经典纸质书效果", 
  "护眼模式", 
  "夜间模式", 
  "个性化设置"
];

// 定义用户数据
const userData = Mock.mock({
  'list|100': [{
    'userId': '@date("yyyy-MM-dd-HHmm")', // 使用日期时间作为用户ID，例如：2024-12-09-1123
    'userChoice|1': userChoices,           // 从用户选择列表中随机选择一个
  }]
});

export default {
  getUserContent: () => {
    // 这里模拟请求返回用户ID和用户选择
    const userContent = userData.list.map(item => ({
      userId: item.userId,        // 用户ID
      userChoice: item.userChoice  // 用户选择
    }));

    return {
      code: 200,
      data: {
        list: userContent,  // 返回用户数据列表
      },
      msg: '获取用户选择成功'
    };
  }
};

