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
            }
        ]
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;
