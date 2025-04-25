<template>
	<div style="display: flex; justify-content: space-between;">
		<div style="width: 100%;">
			<h3>已有的教学安排</h3>
			<el-table :data="tasks" border style="width: 100%">
				<el-table-column prop="userAccount.uid" label="教师编号" />
				<el-table-column prop="userAccount.uname" label="教师姓名" />
				<el-table-column prop="clz.clzno" label="班级编号" />
				<el-table-column prop="clz.clzname" label="班级名称" />
				<el-table-column prop="course.cno" label="课程编号" />
				<el-table-column prop="course.cname" label="课程名称" />
			</el-table>
		</div>
	</div>
</template>

<script setup>
	import {
		ref,
		reactive,
		onMounted
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		Store
	} from "vuex";
	import {
		store
	} from "../../store";
	import router from "../../router";
	import {
		$get,
		$postx,
		$postj,
		$postf
	} from "../../ajax";
	// 定义响应式数据
	const clzs = ref([]);
	const courses = ref([]);
	const teas = ref([]);
	const formTask = ref({
		userAccount: {
			uid: ''
		},
		clz: {
			clzno: ''
		},
		course: {
			cno: ''
		}
	});
	const tasks = ref([]);
	onMounted(() => {
		$get("/api/fore/tea/findMyTask").then((resp) => {
			console.log(resp.data.data);
			tasks.value = resp.data.data;
		}).catch((error) => {
			console.error("获取任务列表失败:", error);
		});

		$get("/api/public/getClzs").then((resp) => {
			console.log(resp.data.data);
			clzs.value = resp.data.data;
		}).catch((error) => {
			console.error("获取班级列表失败:", error);
		});

		$get("/api/public/getCourses").then((resp) => {
			console.log(resp.data.data);
			courses.value = resp.data.data;
		}).catch((error) => {
			console.error("获取课程列表失败:", error);
		});

		$get("/api/public/getTeachers").then((resp) => {
			console.log(resp.data.data);
			teas.value = resp.data.data;
		}).catch((error) => {
			console.error("获取教师列表失败:", error);
		});
	});
</script>

<style>
</style>