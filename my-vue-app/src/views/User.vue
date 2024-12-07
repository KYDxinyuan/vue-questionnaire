<!-- <script setup>

</script>

<template>
    <div>用户信息管理</div>

</template>

<style scoped lang="less">
</style>  -->

<script setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

// 表格数据
const tableData = ref([]);

// 获取表格数据
const getTableData = async () => {
  try {
    const response = await axios.get('/api/user/getUserContent');
    if (response.data.code === 200) {
      tableData.value = response.data.data.list.map((item) => ({
        userId: item.userId,
        userChoice: item.userChoice,
      }));
    }
  } catch (error) {
    console.error('Error fetching table data:', error);
  }
};

// 表格列标签
const tableLabel = reactive([
  { prop: 'userId', label: '用户 ID' },
  { prop: 'userChoice', label: '用户选择' },
]);

// 查询表单
const fromInline = reactive({
  keyWord: ''
});

// 查询配置
const config = reactive({
  name: ''
});

// 查询功能
const handleSearch = () => {
  config.name = fromInline.keyWord;
  getTableData();
};

// 编辑功能
const handleEdit = (row) => {
  console.log('Edit:', row);
};

// 删除功能
const handleDelete = (row) => {
  console.log('Delete:', row);
};

// 初始化加载数据
onMounted(() => {
  getTableData();
});
</script>

<template>
  <div class="user-header">
    <el-button type="primary">新增</el-button>
    <el-form :inline="true" :model="fromInline">
      <el-form-item label="请输入">
        <el-input placeholder="请输入用户ID" v-model="fromInline.keyWord"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="table">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column
        v-for="item in tableLabel"
        :key="item.prop"
        :prop="item.prop"
        :label="item.label"
      />
      <el-table-column fixed="right" label="Operations" min-width="120">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">
            编辑
          </el-button>
          <el-button class="delete" type="primary" size="small" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped lang="less">
.delete {
  background-color: red;
  border-color: red;
  color: white;
}

.user-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.table {
  margin: 20px;
}
</style>

