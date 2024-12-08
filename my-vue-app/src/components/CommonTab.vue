
<template>
    <div class="tags" v-show="showAside">
        <el-tag
        v-for="(tag, index) in tags"
        :key="tag.name"
        :closable="tag.name !== 'home'"
        :effect="route.name === tag.name ? 'dark' : 'plain'"
        @click="handleMenu(tag)"
        @close="handleClose(tag, index)"
        >
            {{ tag.label }}
        </el-tag>
    </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useAllDataStore } from "@/stores";

const store = useAllDataStore();
const tags = computed(() => store.tags);
const showAside = computed(() => store.showAside);
const route = useRoute();
const router = useRouter();

const handleMenu = (tag) => {
    router.push(tag.name);
    store.selectMenu(tag);
};

const handleClose = (tag, index) => {
    store.updateTags(tag);

    if (tag.name !== route.name) return;

    if (index === store.tags.length) {
        store.selectMenu(tags.value[index - 1]);
        router.push(tags.value[index - 1].name);
    } else {
        store.selectMenu(tags.value[index]);
        router.push(tags.value[index].name);
    }
};
</script>

<style lang="less" scoped>
.tags {
    margin: 20px 0 0 20px;
}
.el-tag {
    margin-right: 10px;
}
</style>
