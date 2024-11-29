<template>
  <div class="hardware-card">
    <h3>{{ hardware.name }} ({{ hardware.type }})</h3>
    <p><strong>Brand:</strong> {{ hardware.brand }}</p>
    <p><strong>Model:</strong> {{ hardware.model }}</p>
    <p><strong>Specifications:</strong> {{ hardware.specifications }}</p>
    <p><strong>Price:</strong> ${{ hardware.price }}</p>
  </div>
</template>

<script>
export default {
  props: {
    hardware: Object,
  },
};
</script>

<style scoped>
.hardware-card {
  border: 1px solid #ddd;
  padding: 20px;
  margin-bottom: 20px;
}
</style>
