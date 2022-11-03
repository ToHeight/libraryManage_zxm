import {createRouter, createWebHashHistory} from "vue-router";
import allHome from "../components/allHome";

const routes = [
    {
        path: '/',
        redirect: '/login'
    },{
        path: "/",
        name: "home",
        component:allHome,
        children:[
            {
            path:'home',
            name:"home",
            components:{
            table:() => import ("@/view/viewHome"),
            meta:{showTag:true},
            children:[
                {
                    path:'content1',
                    name:"content1",
                    components:() => import("@/view/show/showContent1.vue"),
                    meta:{showTag:false},
            },]
               }
            },
            {
                path: 'book',
                name: "book",
                components: {
                    table: () => import ("@/view/bookHome"),
                }
            },
            {
                path: '/bookReservation',
                name: "bookReservation",
                components: {
                    table: () => import ("@/view/bookReservation/bookReservation"),
                },
            },
            {
                path: '/bookRecommend',
                name: "bookRecommend",
                components: {
                    table: () => import ("@/view/bookRecommend"),
                },
            },
            {
                path: '/bookShelf',
                name: "bookShelf",
                components: {
                    table: () => import ("@/view/bookShelf"),
                },
            },
            {
                path: '/bookBorrow',
                name: "bookBorrow",
                components: {
                    table: () => import ("@/view/bookBorrow"),
                },
            },
            {
                path: '/chooseSeat',
                name: "chooseSeat",
                components: {
                    table: () => import ("@/view/chooseSeat"),
                },
            },
            {
                path: '/appointmentInformation',
                name: "appointmentInformation",
                components: {
                    table: () => import ("@/view/appointmentInformation/appointmentInformation"),
                },
            },
            {
                path: '/activityCenter',
                name: "activityCenter",
                components: {
                    table: () => import ("@/view/activityCenter/activityCenter"),
                },
            },
           ]
    },
    {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../view/myLogin")
    }

];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});
export default router;