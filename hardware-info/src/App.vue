<template>
  <div id="app">
    <header>
      <h1>机佬♂妙♂妙♂屋（装机速成宝典）</h1>
      <nav>
        <ul>
          <li><router-link to="/home/hardware">硬件展示</router-link></li>
          <li><router-link to="/home/article">科普文章</router-link></li>
          <li><router-link to="/home/download">驱动下载</router-link></li>
          <li v-if="!isLoggedIn"><router-link to="/">登录</router-link></li>
          <li v-if="isLoggedIn"><a href="#" @click="logout">登出</a></li>
        </ul>
      </nav>
    </header>

    <main>
      <router-view></router-view>
    </main>

    <footer>
      <p>&copy; 2024 Hardware Info Portal</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      isLoggedIn: false, // 用于存储登录状态
    };
  },
  methods: {
    logout() {
      // 清除 localStorage 中的 token
      localStorage.removeItem('token');
      // 更新登录状态
      this.isLoggedIn = false;
      // 提示用户
      alert('您已成功登出');
      // 跳转到登录页
      this.$router.push('/');
    },
    checkLoginStatus() {
      // 检查是否存在 token
      const token = localStorage.getItem('token');
      this.isLoggedIn = !!token; // 如果有 token 就表示已登录
    },
  },
  mounted() {
    // 在组件加载时检查登录状态
    this.checkLoginStatus();
  },
  watch: {
    // 监听路由变化，确保登录状态实时更新
    $route() {
      this.checkLoginStatus();
    },
  },
};
</script>

<style scoped>
#app {
  font-family: 'Arial', sans-serif;
  text-align: center;
  padding: 0;
  margin: 0;
}

header {
  background-color: #4CAF50;
  color: white;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

h1 {
  font-size: 1.8rem;
}

nav ul {
  list-style: none;
  padding: 0;
  display: flex;
  gap: 15px;
}

nav ul li {
  display: inline;
}

nav ul li a {
  color: white;
  text-decoration: none;
  font-size: 1rem;
  transition: color 0.3s;
}

nav ul li a:hover {
  color: #f1f1f1;
}

footer {
  background-color: #f1f1f1;
  padding: 10px;
  position: fixed;
  width: 100%;
  bottom: 0;
}

main {
  padding: 20px;
  min-height: 80vh;
}
</style>
