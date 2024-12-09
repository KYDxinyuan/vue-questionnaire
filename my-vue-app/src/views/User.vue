

<!-- <script setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

// 表格数据
const tableData = ref([]);

// 分页信息
const pagination = reactive({
  currentPage: 1, // 当前页
  pageSize: 10,   // 每页条数
  total: 0        // 总记录数
});

// 表格列标签
const tableLabel = reactive([
  { prop: 'id', label: '编号' },
  { prop: 'selectedOption', label: '用户选择 ID' },
  { prop: 'selectedOptionContent', label: '用户选择内容' },
  { prop: 'userId', label: '用户 ID' },
]);

// 查询表单
const fromInline = reactive({
  keyWord: '',
});

// 查询配置
const config = reactive({
  name: '',
});

// 获取表格数据（带分页参数和查询条件）
const getTableData = async () => {
  try {
    const response = await axios.get('/api/home/getUserContent', {
      params: {
        page: pagination.currentPage,
        pageSize: pagination.pageSize,
        name: config.name
      }
    });
    if (response.data.code === 200) {
      tableData.value = response.data.data.list.map((item) => ({
        id: item.id,
        selectedOption: item.selectedOption,
        selectedOptionContent: item.selectedOptionText,
        userId: item.userId,
      }));
      pagination.total = response.data.data.total;
    }
  } catch (error) {
    console.error('获取表格数据时出错:', error);
  }
};

// 查询功能
const handleSearch = () => {
  config.name = fromInline.keyWord;
  pagination.currentPage = 1;
  getTableData();
};

// 编辑功能
const handleEdit = (row) => {
  console.log('编辑:', row);
};

// 删除功能
const handleDelete = async (row) => {
  try {
    console.log('准备发送删除请求，ID:', row.id);
    const response = await axios.delete('/api/home/UserContent', {
      params: { id: row.id }
    });
    if (response.data.code === 200) {
      alert('删除成功');
      getTableData(); // 重新获取数据
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error('删除时出错:', error.response || error.message);
    alert('删除失败');
  }
};

// 处理分页页码变化
const handlePageChange = (page) => {
  pagination.currentPage = page;
  getTableData();
};

// 处理每页条数变化
const handlePageSizeChange = (size) => {
  pagination.pageSize = size;
  pagination.currentPage = 1;
  getTableData();
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
        <el-input placeholder="请输入用户选择或 ID" v-model="fromInline.keyWord"></el-input>
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
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">
            编辑
          </el-button>
          <el-button class="delete" type="primary" size="small" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table> -->
    <!-- 分页组件 -->
    <!-- <el-pagination
      :current-page.sync="pagination.currentPage"
      :page-size.sync="pagination.pageSize"
      :total="pagination.total"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="handlePageChange"
      @size-change="handlePageSizeChange"
    />
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

.el-pagination {
  margin-top: 20px;
  text-align: right;
}
</style> -->


<script setup>
import { ref, reactive, onMounted } from 'vue';
import axios from 'axios';

// 表格数据
const tableData = ref([]);

// 分页信息
const pagination = reactive({
  currentPage: 1, // 当前页
  pageSize: 10,   // 每页条数
  total: 0        // 总记录数
});

// 表格列标签
const tableLabel = reactive([
  { prop: 'id', label: '编号' },
  { prop: 'selectedOption', label: '用户选择 ID' },
  { prop: 'selectedOptionContent', label: '用户选择内容' },
  { prop: 'userId', label: '用户 ID' },
]);

// 查询表单
const fromInline = reactive({
  keyWord: '',
});

// 查询配置
const config = reactive({
  selectedOption: '',
  selectedOptionContent: ''
});

// 获取表格数据（带分页参数和查询条件）
const getTableData = async () => {
  try {
    const response = await axios.get('/api/home/getUserContent', {
      params: {
        page: pagination.currentPage,
        pageSize: pagination.pageSize,
        selectedOptionText: config.selectedOptionText?.slice(0, 10),  // 截取前10个字符
        selectedOption: config.selectedOption?.slice(0, 10)           // 如果 selectedOption 也需要模糊查询，截取前10个字符
      }
    });
    if (response.data.code === 200) {
      tableData.value = response.data.data.list.map((item) => ({
        id: item.id,
        selectedOption: item.selectedOption,
        selectedOptionContent: item.selectedOptionText,
        userId: item.userId,
      }));
      pagination.total = response.data.data.total;
    }
  } catch (error) {
    console.error('获取表格数据时出错:', error);
  }
};

// 查询功能
const handleSearch = () => {
  // config.selectedOptionText = fromInline.keyWord.slice(0, 10);  // 截取前10个字符
  config.selectedOption = fromInline.keyWord.slice(0, 10);      // 截取前10个字符
  pagination.currentPage = 1;
  getTableData();
};


// 编辑功能
const handleEdit = (row) => {
  console.log('编辑:', row);
};

// 删除功能
const handleDelete = async (row) => {
  try {
    console.log('准备发送删除请求，ID:', row.id);
    const response = await axios.delete('/api/home/UserContent', {
      params: { id: row.id }
    });
    if (response.data.code === 200) {
      alert('删除成功');
      getTableData(); // 重新获取数据
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error('删除时出错:', error.response || error.message);
    alert('删除失败');
  }
};

// 处理分页页码变化
const handlePageChange = (page) => {
  pagination.currentPage = page;
  getTableData();
};

// 处理每页条数变化
const handlePageSizeChange = (size) => {
  pagination.pageSize = size;
  pagination.currentPage = 1;
  getTableData();
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
        <el-input placeholder="请输入用户选择 ID " v-model="fromInline.keyWord"></el-input>
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
      <el-table-column fixed="right" label="操作" min-width="120">
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
    <!-- 分页组件 -->
    <el-pagination
      :current-page.sync="pagination.currentPage"
      :page-size.sync="pagination.pageSize"
      :total="pagination.total"
      layout="total, sizes, prev, pager, next, jumper"
      @current-change="handlePageChange"
      @size-change="handlePageSizeChange"
    />
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

.el-pagination {
  margin-top: 20px;
  text-align: right;
}
</style>
