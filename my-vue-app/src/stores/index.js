

import { defineStore } from "pinia";
import { reactive } from "vue";

function initState() {
    return {
        isCollapse: false,
        showAside: true, // 控制侧边栏和标签区域的显示与隐藏
        tags: [
            {
                path: "/home",
                name: "home",
                label: "首页",
                icon: "home",
            },
        ],
        currentMenu: null,
    };
}

export const useAllDataStore = defineStore("alldata", () => {
    const state = reactive(initState());

    function selectMenu(val) {
        if (val.name === "home") {
            state.currentMenu = null;
        } else {
            let index = state.tags.findIndex((item) => item.name === val.name);
            if (index === -1) {
                state.tags.push(val);
            }
            state.currentMenu = val;
        }
    }

    function updateTags(tag) {
        let index = state.tags.findIndex((item) => item.name === tag.name);
        if (index !== -1) {
            state.tags.splice(index, 1);
        }
    }

    return {
        ...state,
        selectMenu,
        updateTags,
    };
});
