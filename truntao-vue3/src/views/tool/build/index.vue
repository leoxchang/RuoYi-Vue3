<template>
  <form-design @onSave="onSave" />
</template>
<script setup>

const onSave = (schema) => {
  console.log(schema)
}
</script>
