<template>
  <div class="article-page">
    <h1>Popular Science Articles</h1>
    <div v-for="article in articleList" :key="article.articleId">
      <ArticleCard :article="article" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import ArticleCard from '@/components/ArticleCard.vue';

export default {
  components: {
    ArticleCard,
  },
  data() {
    return {
      articleList: [],
    };
  },
  mounted() {
    axios
      .get('http://localhost:8080/api/article/GPU Knowledge')  // 示例请求，替换为实际后端地址
      .then((response) => {
        this.articleList = response.data;
      })
      .catch((error) => {
        console.error('There was an error fetching the article data:', error);
      });
  },
};
</script>

<style scoped>
.article-page {
  padding: 20px;
}
</style>
