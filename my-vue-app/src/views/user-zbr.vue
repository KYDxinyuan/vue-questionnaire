<template>
    <div id="main" style="width: 100%; height: 90%;"></div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  export default {
    name: 'EChartsBarChart',
    mounted() {
      this.initChart();
    },
    methods: {
      initChart() {
        const chartDom = document.getElementById('main');
        const myChart = echarts.init(chartDom);
  
        const posList = [
          'left',
          'right',
          'top',
          'bottom',
          'inside',
          'insideTop',
          'insideLeft',
          'insideRight',
          'insideBottom',
          'insideTopLeft',
          'insideTopRight',
          'insideBottomLeft',
          'insideBottomRight'
        ];
  
        const app = {
          config: {
            rotate: 90,
            align: 'left',
            verticalAlign: 'middle',
            position: 'insideBottom',
            distance: 15
          }
        };
  
        const labelOption = {
          show: true,
          position: app.config.position,
          distance: app.config.distance,
          align: app.config.align,
          verticalAlign: app.config.verticalAlign,
          rotate: app.config.rotate,
          formatter: '{c}  {name|{a}}',
          fontSize: 16,
          rich: {
            name: {}
          }
        };
  
        const option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            data: [
              'Dark background',
              'Medium brightness background',
              'Light background'
            ]
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar', 'stack'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {
              type: 'category',
              axisTick: { show: false },
              data: ['size 12', 'size 14', 'size 16', 'size 18', 'size 20']
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: 'Dark background',
              type: 'bar',
              barGap: 0,
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              data: [100, 132, 355, 204, 170]
            },
            {
              name: 'Medium brightness background',
              type: 'bar',
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              data: [140, 152, 321, 154, 150]
            },
            {
              name: 'Light background',
              type: 'bar',
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              data: [90, 192, 451, 164, 130]
            }
          ]
        };
  
        myChart.setOption(option);
      }
    }
  };
  </script>
  
  <style scoped>
  #main {
    width: 100%;
    height: 400px;
  }
  </style>