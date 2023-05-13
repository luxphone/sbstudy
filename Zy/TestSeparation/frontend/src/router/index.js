import Vue from 'vue'
import VueRouter from 'vue-router'
import BooktypeList from "@/views/BooktypeList";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'BooktypeList',
        component: BooktypeList
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
