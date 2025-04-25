<template>
	<div style="display: flex; justify-content: space-between;">
		<div style="width: 100%;">
			<h3>已有的教学安排</h3>
			<el-table :data="tasks" border style="width: 100%; max-height: 300px; overflow-y: auto;">
				<el-table-column prop="clz.clzno" label="班级编号" />
				<el-table-column prop="clz.clzname" label="班级名称" />
				<el-table-column prop="course.cno" label="课程编号" />
				<el-table-column prop="course.cname" label="课程名称" />
				<el-table-column label="操作">
					<template #default="scope">
						<el-button type="primary" @click="goToMarkPage(scope.row)">打分</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted,
		onBeforeUnmount
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		useRoute,
		useRouter
	} from "vue-router";
	import {
		$get
	} from "../../ajax";

	// 定义响应式数据
	const clzs = ref([]);
	const courses = ref([]);
	const teas = ref([]);
	const tasks = ref([]);

	const router = useRouter();

	let requests = [];

	onMounted(() => {
		const taskRequest = $get("/api/fore/tea/findMyTask").then((resp) => {
			console.log(resp.data.data);
			tasks.value = resp.data.data;
		}).catch((error) => {
			console.error("获取任务列表失败:", error);
		});
		requests.push(taskRequest);

		const clzRequest = $get("/api/public/getClzs").then((resp) => {
			console.log(resp.data.data);
			clzs.value = resp.data.data;
		}).catch((error) => {
			console.error("获取班级列表失败:", error);
		});
		requests.push(clzRequest);

		const courseRequest = $get("/api/public/getCourses").then((resp) => {
			console.log(resp.data.data);
			courses.value = resp.data.data;
		}).catch((error) => {
			console.error("获取课程列表失败:", error);
		});
		requests.push(courseRequest);

		const teaRequest = $get("/api/public/getTeachers").then((resp) => {
			console.log(resp.data.data);
			teas.value = resp.data.data;
		}).catch((error) => {
			console.error("获取教师列表失败:", error);
		});
		requests.push(teaRequest);
	});

	onBeforeUnmount(() => {
		// 取消所有未完成的请求
		requests.forEach(request => {
			if (request.cancel) {
				request.cancel();
			}
		});
		requests = [];
	});

	// 跳转到打分页面的方法
	const goToMarkPage = (task) => {
		console.log("clzno:"+task.clz.clzno);
		console.log("cno:"+task.course.cno);
		router.push({
			path: '/tea/addMark',
			query: {
				clzno: task.clz.clzno,
				cno: task.course.cno
			}
		});
	};
</script>

<style>
</style>