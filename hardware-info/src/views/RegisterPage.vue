<template>
  <div>
    <h1>注册页面</h1>
    <form @submit.prevent="handleRegister">
      <label for="username">用户名：</label>
      <input id="username" v-model="username" type="text" required />

      <label for="password">密码：</label>
      <input id="password" v-model="password" type="password" required />

      <button type="submit" :disabled="loading">
        {{ loading ? '注册中...' : '注册' }}
      </button>
    </form>

    <!-- 错误信息 -->
    <p v-if="error" style="color: red;">{{ error }}</p>

    <!-- 跳转到登录页面的链接 -->
    <div style="margin-top: 20px;">
      <p>已经有账号？<router-link to="/">去登录</router-link></p>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080', // 后端的基础 URL 地址
  timeout: 5000,
});

export default {
  name: 'RegisterPage',
  setup() {
    const router = useRouter();
    const username = ref('');
    const password = ref('');
    const loading = ref(false);
    const error = ref(null);

    const handleRegister = async () => {
      loading.value = true;
      error.value = null;

      try {
        const response = await api.post('/api/v1/register', {
          username: username.value,
          password: password.value,
        });

        if (response.status === 201) {
          console.log('Registration successful, redirecting to login...');
          alert('注册成功！点击确认跳转到登录页面。');
          router.push('/');  // 确保跳转
        } else {
          error.value = response.data.message || '注册失败，请稍后重试';
        }
      } catch (err) {
        error.value = '注册失败，请稍后重试';
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
      handleRegister,
    };
  },
};
</script>

<style scoped>
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

div {
  text-align: center;
  margin-top: 10px;
}
</style>
