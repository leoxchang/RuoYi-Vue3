<template>
  <div v-loading="loading" :style="'height:' + height">
    <iframe 
      :src="url" 
      frameborder="no" 
      style="width: 100%; height: 100%" 
      scrolling="auto" />
  </div>
</template>

<script setup lang="ts">
interface Props {
  src: string
}

const props = defineProps<Props>()

const height = ref<string>(document.documentElement.clientHeight - 94.5 + "px;")
const loading = ref<boolean>(true)
const url = computed<string>(() => props.src)

onMounted(() => {
  setTimeout(() => {
    loading.value = false;
  }, 300);
  window.onresize = function temp() {
    height.value = document.documentElement.clientHeight - 94.5 + "px;";
  };
})
</script>
