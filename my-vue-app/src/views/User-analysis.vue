
<script setup>
import * as echarts from 'echarts';
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

const chartContainer = ref(null); // 图表 DOM 的引用
let chartInstance = null; // ECharts 实例

// 数据源
const chartData = reactive({
  labels: ['经典纸质书效果', '护眼模式', '夜间模式', '个性化设置'], // 类别
  values: [0, 0, 0, 0] // 数据值
});

// 获取图表数据并分类统计
const getChartData = async () => {
  try {
    const response = await axios.get('/api/home/getUserContent', {
      params: { page: 1, pageSize: 100 }
    });
    if (response.data.code === 200) {
      const data = response.data.data.list;

      // 统计每个类别的数量
      const categoryCounts = {
        '经典纸质书效果': 0,
        '护眼模式': 0,
        '夜间模式': 0,
        '个性化设置': 0
      };

      // 模糊匹配规则
      const matchRules = {
        '经典纸质书效果': ['经典', '纸质'],
        '护眼模式': ['护眼', '保护'],
        '夜间模式': ['夜间', '黑暗'],
        '个性化设置': ['个性', '自定义']
      };

      // 遍历数据并分类
      data.forEach(item => {
        const option = item.selectedOptionText || ''; // 假设选项名称存在
        for (const [category, keywords] of Object.entries(matchRules)) {
          if (keywords.some(keyword => option.includes(keyword))) {
            categoryCounts[category]++;
            break; // 一个选项只统计到一个分类中
          }
        }
      });

      // 更新 chartData
      chartData.values = chartData.labels.map(label => categoryCounts[label] || 0);
      updateChart(); // 更新图表
    }
  } catch (error) {
    console.error('获取图表数据时出错:', error);
  }
};

// 初始化图表
const initChart = () => {
  if (chartContainer.value) {
    chartInstance = echarts.init(chartContainer.value); // 初始化图表实例
    const options = {
      title: {
        text: '用户选择内容统计',
        subtext: '高级图表展示',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        left: 'center',
        top: 'bottom',
        data: chartData.labels
      },
      toolbox: {
        show: true,
        feature: {
          dataView: { show: true, readOnly: false },
          saveAsImage: { show: true },
          restore: { show: true }
        }
      },
      series: [
        {
          name: '选项统计',
          type: 'pie',
          radius: [20, 140],
          center: ['50%', '50%'],
          roseType: 'area',
          itemStyle: {
            borderRadius: 5
          },
          data: chartData.labels.map((label, index) => ({
            value: chartData.values[index],
            name: label
          }))
        }
      ]
    };
    chartInstance.setOption(options);
  }
};

// 更新图表
const updateChart = () => {
  if (chartInstance) {
    chartInstance.setOption({
      legend: { data: chartData.labels },
      series: [
        {
          data: chartData.labels.map((label, index) => ({
            value: chartData.values[index],
            name: label
          }))
        }
      ]
    });
  }
};

onMounted(() => {
  initChart(); // 初始化图表
  getChartData(); // 获取数据并更新图表
});
</script>

<template>
  <div class="chart-container">
    <div ref="chartContainer" class="chart"></div>
  </div>
</template>

<style scoped lang="less">
.chart-container {
  width: 100%;
  max-width: 600px; /* 限制最大宽度 */
  height: auto;
  aspect-ratio: 4 / 3; /* 保持比例 */
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  animation: flow 2s infinite;

  position: relative;
  left: 25%; /* 向左移动 */
  top: 10%;  /* 向下移动 */
}

.chart {
  width: 100%;
  height: 100%;
}

/* 动画效果 */
// @keyframes flow {
//   0% {
//     transform: scale(1);
//     opacity: 1;
//   }
//   50% {
//     transform: scale(1.05);
//     opacity: 0.9;
//   }
//   100% {
//     transform: scale(1);
//     opacity: 1;
//   }
// }
</style>






