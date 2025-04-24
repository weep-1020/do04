<template>
	<div style="height: 80vh;">
		<h3>教务处-教学安排</h3>
		<el-divider />
		<div style="display: flex; justify-content: space-between;">
			<div style="width: 100%;">
				<h3>已有的教学安排</h3>
				<el-table :data="tasks" border style="width: 100%" max-height="450">
					<el-table-column prop="userAccount.uid" label="教师编号" />
					<el-table-column prop="userAccount.uname" label="教师姓名" />
					<el-table-column prop="clz.clzno" label="班级编号" />
					<el-table-column prop="clz.clzname" label="班级名称" />
					<el-table-column prop="course.cno" label="课程编号" />
					<el-table-column prop="course.cname" label="课程名称" />
					<el-table-column label="操作">
						<template #default="scope">
							<el-button link size="small" type="danger" @click="del(scope.row.kid)">删除</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
		</div>
		<hr />
		<el-button @click="go2add()">添加课程</el-button>
		<el-dialog v-model="bShow" title="课程信息" width="30%">
			<h3>添加新的课程安排</h3>
			教师:
			<el-select v-model="formTask.userAccount.uid" placeholder="请选择教师">
				<el-option v-for="tea in teas" :key="tea.uid" :label="tea.uname" :value="tea.uid" />
			</el-select>
			<el-divider />
			班级:
			<el-select v-model="formTask.clz.clzno" placeholder="请选择班级">
				<el-option v-for="clz in clzs" :key="clz.clzno" :label="clz.clzname" :value="clz.clzno" />
			</el-select>
			<el-divider />
			课程:
			<el-select v-model="formTask.course.cno" placeholder="请选择课程">
				<el-option v-for="course in courses" :key="course.cno" :label="course.cname" :value="course.cno" />
			</el-select>
			<el-divider />
			<el-button type="primary" @click="add">添加</el-button>
			<el-button @click="cancel()">取消</el-button>
		</el-dialog>
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
	let bShow = ref(false);
	const cv = ref(false);

	// 定义方法
	function del(kid) {
		$get("/api/adm/task/del?kid=" + kid).then((resp) => {
			console.log(resp.data);
			tasks.value = resp.data.data;
			ElMessage.success("删除成功!");
		}).catch((error) => {
			ElMessage.error("删除失败!");
		});
	}

	function cancel() {
		bShow.value = false;
	}
	// 准备添加
	function go2add() {
		cv.value = true;
		bShow.value = true;
	}
	const add = () => {
		console.log(formTask.value)
		$postj("/api/adm/task/add", formTask.value).then((resp) => {
			console.log(resp.data);
			tasks.value = resp.data.data;
			ElMessage.success("添加成功!");
		}).catch((error) => {
			ElMessage.error("添加失败!");
		});
	};

	// 生命周期钩子
	onMounted(() => {
		$get("/api/adm/task/findAll").then((resp) => {
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

<style scoped>

</style>