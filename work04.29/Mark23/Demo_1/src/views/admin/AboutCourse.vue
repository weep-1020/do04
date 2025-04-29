<template>
	<div style="width: 100%; height: 80px;text-align: center;">
		<h1>课程管理</h1>
	</div>
	<div>
		<el-table :data="courses" width="100%" max-height="450">
			<el-table-column prop="cno" label="班级编号" width="150" />
			<el-table-column prop="cname" label="班级名称" width="200" />
			<el-table-column label="操作">
				<template #default="scope">
					<el-button link size="small" type="danger" @click="del(scope.row.cno)">
						删除
					</el-button>
					<el-button link size="small" type="primary" @click="goupdate(scope.row)">
						编辑
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<div style="height: 100px;padding-top: 20px;">
			<div>
				<el-button @click="go2add()">添加课程</el-button>
			</div>
		</div>
		
		<el-dialog v-model="bShow" title="课程信息" width="30%">
			<el-form :model="newCourse" label-width="80px">
				<el-form-item label="课程编号">
					<el-input v-model="newCourse.cno" :readonly="orx() === '更改课程'" />
				</el-form-item>
				<el-form-item label="课程名称">
					<el-input v-model="newCourse.cname" />
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="cancel">取消</el-button>
					<el-button type="primary" @click="save">保存</el-button>
				</span>
			</template>
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


	let courses = ref([]);

	let bShow = ref(false);
	const cv = ref(false);
	let newCourse = ref({
		cno: '',
		cname: ''
	});

	function orx() {
		if (cv.value === false) {
			return "更改课程";
		}
		if (cv.value === true) {
			return "添加课程";
		}
	}

	function cancel() {
		bShow.value = false;
	}

	function save() {
		// 更改
		if (cv.value === false) {
			$postj("/api/adm/course/update", newCourse.value)
				.then((resp) => {
					console.log(resp.data);
					courses.value = resp.data.data;
					ElMessage.success("更改成功!");
				})
				.catch((error) => {
					ElMessage.error("更改失败!");
				});
			bShow.value = false;
		}
		// 添加
		if (cv.value === true) {
			$postj("/api/adm/course/add", newCourse.value)
				.then((resp) => {
					console.log(resp.data);
					courses.value = resp.data.data;
					ElMessage.success("添加成功!");
				})
				.catch((error) => {
					ElMessage.error("添加失败!");
				});
			bShow.value = false;
		}
	}

	// 准备添加
	function go2add() {
		newCourse.value = {
			cno: '',
			cname: ''
		};
		cv.value = true;
		bShow.value = true;
	}

	// 准备更改
	function goupdate(obj) {
		bShow.value = true;
		newCourse.value = {
			...obj
		};
		cv.value = false;
	}

	// 删除
	function del(cno) {
		$get("/api/adm/course/del?cno=" + cno)
			.then((resp) => {
				console.log(resp.data);
				courses.value = resp.data.data;
				ElMessage.success("删除成功!");
			})
			.catch((error) => {
				ElMessage.error("删除失败!");
			});
	}

	onMounted(() => {
		let token = window.localStorage.getItem("token");
		console.log(token);
		$get("/api/public/getCourses")
			.then((resp) => {
				courses.value = resp.data.data;
				console.log(courses.value);
			})
			.catch((error) => {
				console.error("获取课程列表失败:", error);
			});
	});
</script>

<style scoped>

</style>