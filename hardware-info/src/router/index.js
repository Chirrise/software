import { createRouter, createWebHistory } from 'vue-router';
import HardwarePage from '../views/HardwarePage.vue';
import ArticlePage from '../views/ArticlePage.vue';
import DownloadPage from '../views/DownloadPage.vue';
import HomePage from '../views/HomePage.vue';
import LoginPage from '../views/LoginPage.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: LoginPage,
  },
  {
    path: '/home',
    name: 'Home',
    component: HomePage,
    children: [
      {
        path: 'hardware',
        name: 'Hardware',
        component: HardwarePage,
      },
      {
        path: 'article',
        name: 'Article',
        component: ArticlePage,
      },
      {
        path: 'download',
        name: 'Download',
        component: DownloadPage,
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// 路由守卫：检查是否已认证
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('token'); // 从 localStorage 获取 token 判断是否已登录

  if (to.path.startsWith('/home') && !isAuthenticated) {
    // 如果用户尝试访问需要登录的页面且未认证，则重定向到登录页面
    next({ name: 'Login', query: { redirect: to.fullPath } });
  } else {
    next(); // 如果已经认证或访问其他页面，允许访问
  }
});

export default router;
