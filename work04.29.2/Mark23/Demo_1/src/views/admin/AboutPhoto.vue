<template>
	<div>
		<el-image v-if="selectedStudent.pic"
			v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${selectedStudent.pic}`"
			style="width: 50px; height:50px" />
	</div>
</template>

<script setup>
	
</script>

<style>
</style>