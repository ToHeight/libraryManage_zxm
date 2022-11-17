import {createRouter, createWebHashHistory} from "vue-router";
import allHome from "../components/allHome";

const routes = [
    {
        path: '/',
        redirect: '/login'
    }, {
        path: "/",
        name: "home",
        component: allHome,
        children: [
            {
                path: 'home',
                name: "home",
                components: {
                    table: () => import ("@/view/viewHome"),
                    meta: {showTag: true},
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
                path: '/personInfo',
                name: "personInfo",
                components: {
                    table: () => import ("@/view/personInfo/personInfo"),
                },
            }
        ]
    },
    {
        path: '/bookInfo/:bookId',
        name: "bookInfo",
        component: () => import ("../view/bookInfo/bookInfo"),
    },
    {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../view/myLogin")
    },
    {
        path: "/register",
        name: "register",
        meta: {
            title: '注册'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../view/myRegister")
    },
    {
        path: "/activateAccount/:code",
        name: "activateAccount",
        meta: {
            title: '激活'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../view/activation/activationShow")
    },
    {
        path: "/forgotPassword/:code",
        name: "forgotPassword",
        meta: {
            title: '忘记密码'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../view/forgotPassword/forgotPassword")
    }

];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});
export default router;