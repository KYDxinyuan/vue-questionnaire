<script setup>
import 'echarts-gl'; // 引入 echarts-gl 扩展以支持 3D 图表
import * as echarts from 'echarts';
import { ref, reactive, onMounted } from 'vue';

// 定义数据字段和索引
const schema = [
  { name: 'fontSize', index: 0 },
  { name: 'fontStyle', index: 1 },
  { name: 'bgColor', index: 2 },
  { name: 'count', index: 3 }
];

const fieldIndices = schema.reduce((obj, item) => {
  obj[item.name] = item.index;
  return obj;
}, {});

// 生成虚拟数据
function generateRandomData() {
  const fontStyles = [
    { style: "Comic Sans MS", weight: 40 },
    { style: "Arial", weight: 30 },
    { style: "Times New Roman", weight: 30 },
    { style: "Times Bold", weight: 10 }
  ];

  const data = [];
  for (let i = 0; i < 100; i++) {
    const randomFontStyle = getRandomByWeight(fontStyles);
    const fontSize = generateFontSize();

    data.push({
      fontSize,
      fontStyle: randomFontStyle,
      bgColor: getRandomColor(),
      count: Math.floor(Math.random() * 50) + 1
    });
  }
  return data;
}

// 按权重随机选择
function getRandomByWeight(options) {
  const totalWeight = options.reduce((sum, option) => sum + option.weight, 0);
  const randomWeight = Math.random() * totalWeight;

  let weightSum = 0;
  for (const option of options) {
    weightSum += option.weight;
    if (randomWeight <= weightSum) {
      return option.style;
    }
  }
}

// 生成字体大小
function generateFontSize() {
  const random = Math.random();
  if (random < 0.7) {
    return Math.random() * (18 - 16) + 16; // 大多数 16-18
  } else if (random < 0.9) {
    return Math.random() * (20 - 18) + 18; // 少数 18-20
  } else if (random < 0.95) {
    return Math.random() * (16 - 13) + 13; // 少数 13-16
  } else if (random < 0.975) {
    return Math.random() * (30 - 20) + 20; // 极少数 20-30
  } else {
    return Math.random() * (13 - 5) + 5; // 极少数 5-13
  }
}

// 随机生成颜色
function getRandomColor() {
  const letters = "0123456789ABCDEF";
  let color = "#";
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}
// 随机生成颜色（按比例分布）
// function getRandomColor() {
//   const random = Math.random(); // 生成 [0, 1) 的随机数

//   if (random < 0.55) {
//     return "FAF9DE"; // 55% 的概率返回 FAF9DE
//   } else if (random < 0.55 + 0.30) {
//     return "2C2C2C"; // 30% 的概率返回 2C2C2C
//   } else if (random < 0.55 + 0.30 + 0.10) {
//     return "E3EDCD"; // 10% 的概率返回 E3EDCD
//   } else {
//     // 剩余 5% 的概率返回随机颜色
//     const letters = "0123456789ABCDEF";
//     let color = "";
//     for (let i = 0; i < 6; i++) {
//       color += letters[Math.floor(Math.random() * 16)];
//     }
//     return color;
//   }
// }


// 数据初始化
const data = generateRandomData();

// 获取最大值用于视觉映射
function getMaxOnExtent(data, config) {
  let colorMax = -Infinity;
  let symbolSizeMax = -Infinity;
  for (const item of data) {
    const colorVal = parseInt(item.bgColor.replace('#', ''), 16);
    const symbolSizeVal = item[config.symbolSize];
    colorMax = Math.max(colorVal, colorMax);
    symbolSizeMax = Math.max(symbolSizeVal, symbolSizeMax);
  }
  return { color: colorMax, symbolSize: symbolSizeMax };
}

// 响应式配置
const config = reactive({
  xAxis3D: 'fontSize',
  yAxis3D: 'fontStyle',
  zAxis3D: 'bgColor',
  color: 'bgColor',
  symbolSize: 'count'
});

// 图表容器的引用
const chartContainer = ref(null);
let myChart = null; // 存储 ECharts 实例

onMounted(() => {
  if (chartContainer.value) {
    // 初始化 ECharts 实例
    myChart = echarts.init(chartContainer.value);

    const max = getMaxOnExtent(data, config);

    // 设置图表选项
    myChart.setOption({
      tooltip: {},
      visualMap: [
        {
          top: 10,
          calculable: true,
          dimension: 3,
          max: max.color,
          inRange: {
            color: ['#1710c0', '#0b9df0', '#00fea8', '#00ff0d', '#f5f811', '#f09a09', '#fe0300']
          }
        },
        {
          bottom: 10,
          calculable: true,
          dimension: 4,
          max: max.symbolSize,
          inRange: {
            symbolSize: [10, 40]
          }
        }
      ],
      xAxis3D: {
        name: config.xAxis3D,
        type: 'value'
      },
      yAxis3D: {
        name: config.yAxis3D,
        type: 'category',
        data: Array.from(new Set(data.map(item => item.fontStyle)))
      },
      zAxis3D: {
        name: config.zAxis3D,
        type: 'value'
      },
      grid3D: {
        axisLine: {
          lineStyle: {
            color: '#333'
          }
        },
        axisPointer: {
          lineStyle: {
            color: '#ffbd67'
          }
        }
      },
      series: [
        {
          type: 'scatter3D',
          dimensions: [
            config.xAxis3D,
            config.yAxis3D,
            config.zAxis3D,
            config.color,
            config.symbolSize
          ],
          data: data.map(item => [
            item[config.xAxis3D],
            item[config.yAxis3D],
            parseInt(item.bgColor.replace('#', ''), 16),
            parseInt(item.bgColor.replace('#', ''), 16),
            item[config.symbolSize]
          ]),
          symbolSize: 12,
          itemStyle: {
            borderWidth: 1,
            borderColor: 'rgba(255,255,255,0.8)'
          },
          emphasis: {
            itemStyle: {
              color: '#fff'
            }
          }
        }
      ]
    });
  }
});
</script>

<template>
  <div ref="chartContainer" id="chart" style="width: 100%; height: 70%;"></div>
</template>

<style scoped>
#chart {
  width: 100%;
  height: 100%;
}
</style>