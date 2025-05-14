import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/order'
    },
    {
        path: '/login',
        name: 'AuthPage',
        component: () => import('@/views/AuthPage.vue')
    },
    {
        path: '/order',
        name: 'OrderPage',
        component: () => import('@/views/OrderPage.vue'),
        meta: { requiresAuth: true } // 👈 需要登录才能访问
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// 登录守卫逻辑
router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem('token') // or your auth state

    if (to.meta.requiresAuth && !isLoggedIn) {
        // 没登录就重定向到登录页
        next('/login')
    } else {
        next()
    }
})

export default router
