import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
    {
        path:'/',
        name:'main',
        component:()=>import('@/views/Main.vue'),
        redirect:'/home',
        children:[
            {
                path:'home',
                name:'home',
                component:()=>import('@/views/Home.vue'),
            },
            {
                path:'user',
                name:'user',
                component:()=>import('@/views/User.vue'),
            },
            {
                path:'user-analy',
                name:'user-analy',
                component:()=>import('@/views/User-analysis.vue'),
            },
            {
                path:'user-sdt',
                name:'user-sdt',
                component:()=>import('@/views/User-sdt.vue'),
            },
            {
                path:'user-zt',
                name:'user-zt',
                component:()=>import('@/views/User-zt.vue'),
            }
        ]
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;
