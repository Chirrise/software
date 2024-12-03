<template>
  <div>
    <h1>登录页面</h1>
    <form @submit.prevent="handleLogin">
      <label for="username">用户名：</label>
      <input id="username" v-model="username" type="text" required />

      <label for="password">密码：</label>
      <input id="password" v-model="password" type="password" required />

      <button type="submit" :disabled="loading">
        {{ loading ? '登录中...' : '登录' }}
      </button>
    </form>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

// 创建一个 Axios 实例，用于与后端进行交互
const api = axios.create({
  baseURL: 'http://localhost:8080', // 后端的基础 URL 地址
  timeout: 5000,
});

// 请求拦截器，自动附加 token（如果存在）
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default {
  name: 'LoginPage',
  setup() {
    const router = useRouter();
    const route = useRoute(); // 获取当前路由信息
    const username = ref('');
    const password = ref('');
    const loading = ref(false);
    const error = ref(null);

    const handleLogin = async () => {
      loading.value = true;
      error.value = null;

      try {
        const response = await api.post('/api/v1/login', {
          username: username.value,
          password: password.value,
        });

        if (response.data && response.data.token) {
          localStorage.setItem('token', response.data.token); // 保存 token

          // 登录成功后跳转到用户请求的页面，若无请求页面则跳转到 /home
          const redirect = route.query.redirect || '/home'; // 获取重定向地址
          router.push(redirect); // 跳转到目标页面
        } else {
          error.value = response.data.message || '用户名或密码错误';
        }
      } catch (err) {
        error.value = '登录失败，请稍后重试';
        console.error(err);
      } finally {
        loading.value = false;
      }
    };

    return {
      username,
      password,
      loading,
      error,
      handleLogin,
    };
  },
};
</script>

<style scoped>
/* 这里可以根据需要自定义样式 */
form {
  display: flex;
  flex-direction: column;
  width: 300px;
  margin: 0 auto;
}

label {
  margin-top: 10px;
}

input {
  padding: 8px;
  margin-top: 5px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
}

p {
  margin-top: 10px;
}
</style>
