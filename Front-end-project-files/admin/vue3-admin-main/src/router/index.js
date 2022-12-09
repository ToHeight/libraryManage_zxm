import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(), // hash模式：createWebHashHistory，history模式：createWebHistory
  routes: [
    {
      path: '/',
      redirect: '/introduce'
    },
    {
      path: '/introduce',
      name: 'introduce',
      component: () => import(/* webpackChunkName: "introduce" */'../views/Introduce.vue')
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import(/* webpackChunkName: "dashboard" */ '../views/Index.vue')
    },
    {
      path: '/userManage',
      name: 'userManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/User/User.vue')
    },
    {
      path: '/adminManage',
      name: 'adminManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/User/Admin.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import(/* webpackChunkName: "login" */ '../views/Login/Login.vue')
    },
    {
      path: '/bookManage',
      name: 'bookManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Book/Book.vue')
    },
    {
      path: '/bookTagManage',
      name: 'bookTagManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Book/BookTag.vue')
    },
    {
      path: '/bookMessageManage',
      name: 'bookMessageManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Book/BookMessage.vue')
    },
    {
      path: '/bookScheduledManage',
      name: 'bookScheduledManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Book/BookScheduled.vue')
    },
    {
      path: '/bookBorrowManage',
      name: 'bookBorrowManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Book/BookBorrow.vue')
    },
    {
      path: '/activityManage',
      name: 'activityManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Activity/Activity.vue')
    },
    {
      path: '/codeManage',
      name: 'codeManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/ConstantCode/ConstantCode.vue')
    },
    {
      path: '/applicationManage',
      name: 'applicationManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Activity/Application.vue')
    },
    {
      path: '/floorManage',
      name: 'floorManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Appointment/Floor.vue')
    },
    {
      path: '/appointmentManage',
      name: 'appointmentManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Appointment/Appointment.vue')
    },
    {
      path: '/roleManage',
      name: 'roleManage',
      component: () => import(/* webpackChunkName: "bookManage" */ '../views/Role/Role.vue')
    },

  ]
})

export default router