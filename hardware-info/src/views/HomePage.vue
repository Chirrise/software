<template>
  <div>
    <nav>
      <router-link to="/home/hardware">硬件页面</router-link>
      <router-link to="/home/article">文章页面</router-link>
      <router-link to="/home/download">下载页面</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'HomePage',
};
</script>

<style scoped>
nav {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
</style>
