<template>
  <div class="download-page">
    <h1>Driver Downloads</h1>
    <div v-for="download in downloadList" :key="download.downloadId">
      <DownloadCard :download="download" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import DownloadCard from '@/components/DownloadCard.vue';

export default {
  components: {
    DownloadCard,
  },
  data() {
    return {
      downloadList: [],
    };
  },
  mounted() {
    axios
      .get('http://localhost:8080/api/download')  // 示例请求，替换为实际后端地址
      .then((response) => {
        this.downloadList = response.data;
      })
      .catch((error) => {
        console.error('There was an error fetching the download data:', error);
      });
  },
};
</script>

<style scoped>
.download-page {
  padding: 20px;
}
</style>
