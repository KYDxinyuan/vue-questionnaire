<!-- <script setup>
import { ref, watch } from 'vue';

// 控制界面显示
const showCover = ref(true); // 是否显示封面
const showSurvey = ref(false); // 是否显示问卷页面
const selectedOption = ref(null); // 用户选择的选项

// 问卷选项
const options = [
  { id: 1, text: "经典纸质书效果" },
  { id: 2, text: "护眼模式" },
  { id: 3, text: "夜间模式" },
  { id: 4, text: "个性化设置" },
];

// 默认设置
const fontSize = ref(16);
const fontFamily = ref("Arial, sans-serif");
const backgroundColor = ref("#ffffff");
const textColor = ref("#333"); // 添加文本颜色

// 不同选项对应的样式设置
const applyOption = (optionId) => {
  switch (optionId) {
    case 1: // 经典纸质书效果
      fontSize.value = getRandomFontSize(16, 18);
      fontFamily.value = "'Songti SC', 'Bookerly', serif";
      backgroundColor.value = "#FAF9DE";
      textColor.value = "#333";
      break;
    case 2: // 护眼模式
      fontSize.value = getRandomFontSize(15, 17);
      fontFamily.value = "Arial, Helvetica, sans-serif";
      backgroundColor.value = "#E3EDCD";
      textColor.value = "#333";
      break;
    case 3: // 夜间模式
      fontSize.value = getRandomFontSize(14, 16);
      fontFamily.value = "Georgia, 'Times New Roman', serif";
      backgroundColor.value = "#2C2C2C";
      textColor.value = "#EAEAEA";
      break;
    default:
      alert("无效选项");
  }
};

// 随机字体大小函数
const getRandomFontSize = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;

// 监听用户选择的选项，实时更新样式
watch(selectedOption, (newValue) => {
  if (newValue && newValue <= 3) {
    applyOption(newValue);
  }
});

// 开始问卷
const startSurvey = () => {
  showCover.value = false;
  showSurvey.value = true;
};

// 提交问卷
const submitSurvey = () => {
  if (selectedOption.value) {
    alert(`您选择了：${options.find((opt) => opt.id === selectedOption.value).text}`);
  } else {
    alert("请选择一个选项！");
  }
};
</script> -->
<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';

// 控制界面显示
const showCover = ref(true); // 是否显示封面
const showSurvey = ref(false); // 是否显示问卷页面
const selectedOption = ref(null); // 用户选择的选项

// 用户 ID
const userId = ref(null);

// 问卷选项
const options = [
  { id: 1, text: "经典纸质书效果" },
  { id: 2, text: "护眼模式" },
  { id: 3, text: "夜间模式" },
  { id: 4, text: "个性化设置" },
];

// 默认设置
const fontSize = ref(16);
const fontFamily = ref("Arial, sans-serif");
const backgroundColor = ref("#ffffff");
const textColor = ref("#333"); // 添加文本颜色

// 不同选项对应的样式设置
const applyOption = (optionId) => {
  switch (optionId) {
    case 1: // 经典纸质书效果
      fontSize.value = getRandomFontSize(16, 18);
      fontFamily.value = "'Songti SC', 'Bookerly', serif";
      backgroundColor.value = "#FAF9DE";
      textColor.value = "#333";
      break;
    case 2: // 护眼模式
      fontSize.value = getRandomFontSize(15, 17);
      fontFamily.value = "Arial, Helvetica, sans-serif";
      backgroundColor.value = "#E3EDCD";
      textColor.value = "#333";
      break;
    case 3: // 夜间模式
      fontSize.value = getRandomFontSize(14, 16);
      fontFamily.value = "Georgia, 'Times New Roman', serif";
      backgroundColor.value = "#2C2C2C";
      textColor.value = "#EAEAEA";
      break;
    default:
      alert("无效选项");
  }
};

// 随机字体大小函数
const getRandomFontSize = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;

// 监听用户选择的选项，实时更新样式
watch(selectedOption, (newValue) => {
  if (newValue && newValue <= 3) {
    applyOption(newValue);
  }
});

// 格式化时间为用户ID
const generateUserId = () => {
  const now = new Date();
  const formattedTime = `${now.getFullYear()}-${(now.getMonth() + 1).toString().padStart(2, '0')}-${now.getDate().toString().padStart(2, '0')}-${now.getHours().toString().padStart(2, '0')}${now.getMinutes().toString().padStart(2, '0')}`;
  return formattedTime;
};

// 开始问卷
const startSurvey = async () => {
  showCover.value = false;
  showSurvey.value = true;

  try {
    // 生成用户 ID（使用点击时间）
    const generatedUserId = generateUserId();
    userId.value = generatedUserId; // 设置为生成的用户 ID
    console.log(`用户已创建，ID：${userId.value}`);

    // 将生成的用户 ID 传递给后端创建用户
    const response = await axios.post('/api/home/getuserId', {
      userId: userId.value, // 传递生成的用户 ID
    });
    console.log(response.data); // 打印后端响应，确保用户已创建

  } catch (error) {
    console.error("用户创建失败：", error);
    alert("用户创建失败，请稍后重试。");
  }
};

// 提交问卷
const submitSurvey = async () => {
  if (!selectedOption.value) {
    alert("请选择一个选项！");
    return;
  }

  try {
    // 提交问卷内容
    await axios.post('/api/home/getuserContent', {
      userId: userId.value,
      selectedOption: selectedOption.value,
      selectedOptionText: options.find((opt) => opt.id === selectedOption.value).text,
    });
    alert("问卷提交成功！");
  } catch (error) {
    console.error("问卷提交失败：", error);
    alert("问卷提交失败，请稍后重试。");
  }
};
</script>



<template>
  <!-- 封面 -->
  <div v-if="showCover" class="cover">
    <h1>欢迎参加调查问卷</h1>
    <p>点击下方按钮开始调查。</p>
    <button @click="startSurvey" class="start-button">开始</button>
  </div>

  <!-- 阅读界面 -->
  <div v-else-if="showSurvey" class="survey">
    <div
      class="content"
      :style="{ fontSize: fontSize + 'px', fontFamily, backgroundColor, color: textColor }"
    >
      <h2>小说片段：</h2>
      <p>
        山间薄雾弥漫，清晨的第一缕阳光穿过树林的缝隙洒在地上，鸟儿的啼鸣声不绝于耳。主角迈着轻快的步伐，沿着弯曲的小路向山顶走去。随着海拔的升高，视野逐渐开阔，远处的湖泊宛如一面明镜，映照着蓝天白云。
      </p>
    </div>
    <div class="options">
      <h3>请选择您最喜欢的阅读模式：</h3>
      <ul>
        <li v-for="option in options" :key="option.id">
          <label>
            <input type="radio" :value="option.id" v-model="selectedOption" />
            {{ option.text }}
          </label>
        </li>
      </ul>
      <div v-if="selectedOption === 4" class="settings">
        <h3>个性化设置：</h3>
        <label>
          字体大小：
          <input type="number" v-model.number="fontSize" min="10" max="50" /> px
        </label>
        <label>
          字体类型：
          <select v-model="fontFamily">
            <option value="'Songti SC', 'Bookerly', serif">经典纸质书效果(Songti SC)</option>
            <option value="Arial, Helvetica, sans-serif">护眼模式(Arial)</option>
            <option value="Georgia, 'Times New Roman', serif">夜间模式(Georgia)</option>
            <option value="'Courier New', monospace">打字机风格(Courier New)</option>
            <option value="'Comic Sans MS', cursive, sans-serif">趣味卡通风格(Comic Sans MS)</option>
          </select>
        </label>
        <label>
          背景颜色：
          <input type="color" v-model="backgroundColor" />
        </label>
      </div>
      <button @click="submitSurvey" class="submit-button">提交</button>
    </div>
  </div>
</template>

<style scoped>
/* 封面样式 */
.cover {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(135deg, #7f7fd5, #86a8e7, #91eae4);
  color: white;
  text-align: center;
}

.cover h1 {
  font-size: 3rem;
  margin-bottom: 20px;
}

.cover p {
  font-size: 1.5rem;
  margin-bottom: 30px;
}

.start-button {
  padding: 10px 20px;
  font-size: 1.2rem;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.start-button:hover {
  background-color: #555;
}

/* 阅读界面样式 */
.survey {
  padding: 20px;
  background: #f5f5f5;
  color: #333;
}

.settings {
  margin-bottom: 20px;
  display: flex;
  gap: 20px;
  align-items: center;
  font-size: 1rem;
}

.settings label {
  display: flex;
  align-items: center;
  gap: 5px;
}

.settings input[type="number"] {
  width: 60px;
}

.content {
  margin-bottom: 30px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.content h2 {
  font-size: 2rem;
  margin-bottom: 10px;
}

.options h3 {
  margin-bottom: 10px;
}

.options ul {
  list-style: none;
  padding: 0;
}

.options li {
  margin: 10px 0;
}

.options input[type="radio"] {
  margin-right: 10px;
}

.submit-button {
  margin-top: 20px;
  padding: 10px 20px;
  font-size: 1rem;
  background-color: #333;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-button:hover {
  background-color: #555;
}
</style>



