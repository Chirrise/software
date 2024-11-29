<template>
  <div class="download-card">
    <h3>{{ download.name }}</h3>
    <p><strong>Version:</strong> {{ download.version }}</p>
    <p><a :href="download.url" target="_blank">Download</a></p>
  </div>
</template>

<script>
export default {
  props: {
    download: Object,
  },
};
</script>

<style scoped>
.download-card {
  border: 1px solid #ddd;
  padding: 20px;
  margin-bottom: 20px;
}
</style>
