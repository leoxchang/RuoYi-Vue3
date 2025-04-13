<template>
  <div class="hamburger" @click="toggleClick">
    <svg :class="{ 'is-active': isActive }" class="hamburger-icon" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"
      width="64" height="64">
      <path
        d="M408 442h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8zm-8 204c0 4.4 3.6 8 8 8h480c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8H408c-4.4 0-8 3.6-8 8v56zm504-486H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8zm0 632H120c-4.4 0-8 3.6-8 8v56c0 4.4 3.6 8 8 8h784c4.4 0 8-3.6 8-8v-56c0-4.4-3.6-8-8-8z" />
    </svg>
  </div>
</template>

<script setup lang="ts">
import type { HamburgerProps, HamburgerEmits } from './index.d';

const props = withDefaults(defineProps<HamburgerProps>(), {
  isActive: false
});

const emit = defineEmits<HamburgerEmits>();

const toggleClick = () => {
  emit('toggleClick');
};
</script>

<style scoped lang="scss">
.hamburger {
  display: inline-block;
  vertical-align: middle;
  width: 20px;
  height: 20px;
  cursor: pointer;
  fill: #bfbfbf;
  transform: rotate(90deg);
  transition: .3s;
  &.is-active {
    transform: rotate(0deg);
  }
  &:hover {
    fill: #409EFF;
  }
}
</style>
