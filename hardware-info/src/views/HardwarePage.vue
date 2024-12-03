<template>
  <div class="hardware-page">
    <h1>Hardware Information</h1>
    <div v-for="hardware in hardwareList" :key="hardware.hardwareId">
      <HardwareCard :hardware="hardware" />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import HardwareCard from '@/components/HardwareCard.vue';

export default {
  components: {
    HardwareCard,
  },
  data() {
    return {
      hardwareList: [],
    };
  },
  mounted() {
    axios
      .get('http://localhost:8080/api/hardware/GPU')  // 示例请求，替换为实际后端地址
      .then((response) => {
        this.hardwareList = response.data;
      })
      .catch((error) => {
        console.error('There was an error fetching the hardware data:', error);
      });
  },
};
</script>

<style scoped>
.hardware-page {
  padding: 20px;
}
</style>
