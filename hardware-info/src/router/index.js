import { createRouter, createWebHistory } from 'vue-router';
import HardwarePage from '../views/HardwarePage.vue';
import ArticlePage from '../views/ArticlePage.vue';
import DownloadPage from '../views/DownloadPage.vue';
import HomePage from '../views/HomePage.vue';
import LoginPage from '../views/LoginPage.vue';
import RegisterPage from '../views/RegisterPage.vue'; // 导入注册页面

const routes = [
  {
    path: '/',
    name: 'Login',  // 登录页面
    component: LoginPage,  // 使用 LoginPage 组件
  },
  {
    path: '/register',
    name: 'Register', // 注册页面
    component: RegisterPage, // 使用 RegisterPage 组件
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

// 路由守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('token');
  console.log('Route Guard:', to.name, isAuthenticated);

  if (to.path.startsWith('/home') && !isAuthenticated) {
    next({ name: 'Login', query: { redirect: to.fullPath } });
  } else if ((to.name === 'Login' || to.name === 'Register') && isAuthenticated) {
    next({ name: 'Home' });
  } else {
    next();
  }
});

export default router;
