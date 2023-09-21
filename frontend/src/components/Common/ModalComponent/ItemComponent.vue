<script setup>
</script>

<template>
  <div class="container basic">
    <span>부품 이름</span>
  </div>
</template>

<style scoped>
.container {
  border : 0;
  width: 100rem;
  height: 3.4375rem;
  flex-shrink: 0;
  text-align: left;
  display: flex;
  align-items : center;
  justify-content : start;
}

.basic {
  border-radius: var(--Number, 0rem);
  border: 1px solid rgba(0, 0, 0, 0.30);
  background: rgba(217, 217, 217, 0.00);
  color: black;
}

.selected {
  border-radius: var(--Number, 0rem);
  border: 1px solid rgba(0, 0, 0, 0.30);
  background: #4599FC;
  color: white;
}
</style>
