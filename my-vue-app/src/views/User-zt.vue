<script setup>
import * as echarts from 'echarts';
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

const chartContainer = ref(null); // 图表 DOM 的引用
let chartInstance = null; // ECharts 实例

// 数据源
const chartData = reactive({
  labels: ['16-18', '13-15', '10-12 and 19-21', 'others'], // 类别
  values: [0, 0, 0, 0] // 数据值
});

// 每个类别对应的颜色
const chartColors = {
  '16-18': '#E3D8B0', // 深化的旧黄色
  '13-15': '#32CD32',  // 绿色
  '10-12 and 19-21': '#000000', // 黑色
  'others': '#FF69B4'   // 粉红色
};

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
        '16-18': 0,
        '13-15': 0,
        '10-12 and 19-21': 0,
        'others': 0
      };

      // 模糊匹配规则，根据数字范围进行分类
      const matchRules = {
        '16-18': ['16', '17', '18','经典','纸质'],
        '13-15': ['13', '14', '15','护眼', '保护'],
        '10-12 and 19-21': ['10', '11', '12', '19', '20', '21','夜间', '黑暗'],
        'others': ['个性', '自定义']
      };

      // 遍历数据并分类
      data.forEach(item => {
        const option = item.selectedOptionText || ''; // 假设选项名称存在
        let matchedCategory = 'others'; // 默认分类为 "others"
        
        // 匹配选项和数字
        for (const [category, keywords] of Object.entries(matchRules)) {
          if (keywords.some(keyword => option.includes(keyword))) {
            matchedCategory = category;
            break; // 一旦匹配，跳出循环
          }
        }

        // 增加对应分类的数量
        categoryCounts[matchedCategory]++;
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
        text: '用户选择字体大小-颜色统计',
        subtext: '高级图表展示',
        left: 'center',
        textStyle: {
          fontSize: 18,
          fontWeight: 'bold'
        },
        subtextStyle: {
          fontSize: 14,
          fontStyle: 'italic'
        }
      },
      tooltip: {
        trigger: 'axis',
        formatter: '{b} : {c}'
      },
      xAxis: {
        type: 'category',
        data: chartData.labels,
        axisLabel: {
          fontSize: 12,
          fontWeight: 'bold'
        }
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          fontSize: 12,
          fontWeight: 'bold'
        }
      },
      series: [
        {
          name: '数量',
          type: 'bar',
          data: chartData.labels.map((label, index) => ({
            value: chartData.values[index],
            itemStyle: {
              color: chartColors[label]
            }
          })),
          label: {
            show: true,
            position: 'top',
            fontSize: 12
          }
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
      series: [
        {
          data: chartData.labels.map((label, index) => ({
            value: chartData.values[index],
            itemStyle: {
              color: chartColors[label]
            }
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
  max-width: 800px; /* 限制最大宽度 */
  height: 500px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px auto;
}

.chart {
  width: 100%;
  height: 100%;
}
</style>

