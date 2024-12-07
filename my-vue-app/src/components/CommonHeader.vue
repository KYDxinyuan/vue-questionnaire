
<template>
    <div class="header">
        <div class="l-content">
            <el-button size="small" @click="handleCollapse">
                <component class="icons" is="menu"></component>
            </el-button>
            <el-breadcrumb separator="/" class="bread">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown>
                <span class="el-dropdown-link">
                    <img :src="getImageUrl('user')" class="user" />
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="hideAside">
                            调查问卷界面
                        </el-dropdown-item>
                        <el-dropdown-item @click="showAside">
                            返回
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
    </div>
</template>

<script setup>
import { useAllDataStore } from "@/stores";

const store = useAllDataStore();

const getImageUrl = (user) => {
    return new URL(`../assets/images/${user}.png`, import.meta.url).href;
};

const handleCollapse = () => {
    store.isCollapse = !store.isCollapse;
};

const hideAside = () => {
    store.showAside = false; // 隐藏侧边栏和标签区域
};

const showAside = () => {
    store.showAside = true; // 显示侧边栏和标签区域
};
</script>

<style lang="less" scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: #333;
}
.icons {
    width: 20px;
    height: 20px;
}
.l-content {
    display: flex;
    align-items: center;
    .el-button {
        margin-right: 20px;
    }
}
.r-content {
    .user {
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }
}
:deep(.bread span) {
    color: #fff !important;
    cursor: pointer !important;
}
</style>
