<template>
  <div class="article-card">
    <h3>{{ article.title }}</h3>
    <p><strong>Category:</strong> {{ article.category }}</p>
    <p>{{ article.content }}</p>
    <p><small>Author: {{ article.author }} | Published: {{ article.publishDate }}</small></p>
  </div>
</template>

<script>
export default {
  props: {
    article: Object,
  },
};
</script>

<style scoped>
.article-card {
  border: 1px solid #ddd;
  padding: 20px;
  margin-bottom: 20px;
}
</style>
