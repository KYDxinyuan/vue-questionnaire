
<template>
    <el-aside v-show="showAside" :width="width">
        <el-menu
            background-color="#545c64"
            text-color="#fff"
            :collapse="isCollapse"
            :collapse-transition="false"
            :default-active="activeMenu"
        >
            <h3 v-show="!isCollapse">问卷调查系统网站</h3>
            <h3 v-show="isCollapse">问卷</h3>
            <el-menu-item
                v-for="item in noChildren"
                :index="item.path"
                :key="item.path"
                @click="handleMenu(item)"
            >
                <component class="icons" :is="item.icon"></component>
                <span>{{ item.label }}</span>
            </el-menu-item>
            <el-sub-menu
                v-for="item in hasChildren"
                :index="item.path"
                :key="item.path"
            >
                <template #title>
                    <component class="icons" :is="item.icon"></component>
                    <span>{{ item.label }}</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item
                        v-for="(subItem, subIndex) in item.children"
                        :index="subItem.path"
                        :key="subItem.path"
                        @click="handleMenu(subItem)"
                    >
                        <component class="icons" :is="subItem.icon"></component>
                        <span>{{ subItem.label }}</span>
                    </el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
        </el-menu>
    </el-aside>
</template>

<script setup>
import { ref, computed } from "vue";
import { useAllDataStore } from "@/stores";
import { useRouter, useRoute } from "vue-router";

const store = useAllDataStore();

const list = ref([
    {
        path: '/home',
        name: 'home',
        label: '首页-问卷',
        icon: 'house',
        url: 'Home'
    },
    {
        path: '/user',
        name: 'user',
        label: '用户信息管理',
        icon: 'video-play',
        url: 'User'
    },
    {
        path: '/user-analy',
        name: 'user-analy',
        label: '用户数据分析',
        icon: 'user',
        url: 'User-analysis'
    },
    {
        path: '/other',
        label: '其他',
        icon: 'location',
        children: [
            {
                path: '/page1',
                name: 'page1',
                label: '页面1',
                icon: 'setting',
                url: 'Page1'
            },
            {
                path: '/page2',
                name: 'page2',
                label: '页面2',
                icon: 'setting',
                url: 'Page2'
            }
        ]
    }
]);

const noChildren = computed(() => list.value.filter((item) => !item.children));
const hasChildren = computed(() => list.value.filter((item) => item.children));
const isCollapse = computed(() => store.isCollapse);
const width = computed(() => (store.isCollapse ? "64px" : "180px"));
const router = useRouter();
const route = useRoute();
const activeMenu = computed(() => route.path);
const showAside = computed(() => store.showAside);

const handleMenu = (item) => {
    router.push(item.path);
    store.selectMenu(item);
};
</script>

<style lang="less" scoped>
.icons {
    width: 20px;
    height: 20px;
    margin-right: 5px;
}
.el-menu {
    border-right: none;
    h3 {
        line-height: 48px;
        color: #fff;
        text-align: center;
    }
}
.el-aside {
    height: 100%;
    background-color: #545c64 !important;
}
</style>
