<template>
	<div style="width: 100%; height: 80px;text-align: center;">
		<h1>班级管理</h1>
	</div>
	<div>
		<el-table :data="clzs" width="100%" max-height="450">
			<el-table-column prop="clzno" label="班级编号" width="150%" />
			<el-table-column prop="clzname" label="班级名称" width="150%" />
			<!-- <el-table-column prop="grade" label="学期" width="130%" /> -->
			<el-table-column label="操作">
				<template #default="scope">
					<el-button link size="small" type="danger" @click="del(scope.row.clzno)">
						删除
					</el-button>
					<el-button link size="small" type="primary" @click="goupdate(scope.row)">
						编辑
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-button @click="go2add()">添加班级</el-button>
		<el-dialog v-model="bShow" title="班级信息" width="30%">
			<el-form :model="newClz" label-width="80px">
				<el-form-item label="班级编号">
					<el-input v-model="newClz.clzno" :readonly="orx() === '更改班级'" />
				</el-form-item>
				<el-form-item label="班级名称">
					<el-input v-model="newClz.clzname" />
				</el-form-item>
				<el-form-item label="学期">
					<el-input v-model="newClz.grade" />
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
	let clzs = ref([]);
	let bShow = ref(false);
	let newClz = ref({
		clzno: '',
		clzname: '',
		grade: ''
	});
	const cv = ref(false);

	function orx() {
		if (cv.value === false) {
			return "更改班级";
		}
		if (cv.value === true) {
			return "添加班级";
		}
	}

	function cancel() {
		bShow.value = false;
	}

	function save() {
		if (cv.value === false) {
			$postf("/api/adm/clz/update", newClz.value)
				.then((resp) => {
					console.log(resp.data);
					clzs.value = resp.data.data;
					ElMessage.success("更改成功!");
				})
				.catch((error) => {
					ElMessage.error("更改失败!");
				});
			bShow.value = false;
		} else if (cv.value === true) {
			$postx("/api/adm/clz/add", newClz.value)
				.then((resp) => {
					console.log(resp.data);
					clzs.value = resp.data.data;
					ElMessage.success("添加成功!");
				})
				.catch((error) => {
					ElMessage.error("添加失败!");
				});
			bShow.value = false;
		}
	}

	function go2add() {
		newClz.value = {
			clzno: '',
			clzname: '',
			grade: ''
		};
		cv.value = true;
		bShow.value = true;
	}

	function goupdate(obj) {
		bShow.value = true;
		newClz.value = {
			...obj
		};
		cv.value = false;
	}

	function del(clzno) {
		$get("/api/adm/clz/del?clzno=" + clzno)
			.then((resp) => {
				console.log(resp.data);
				clzs.value = resp.data.data;
				ElMessage.success("删除成功!");
			})
			.catch((error) => {
				ElMessage.error("删除失败!");
			});
	}

	onMounted(() => {
		let token = window.localStorage.getItem("token");
		console.log(token);
		$get("/api/public/getClzs")
			.then((resp) => {
				clzs.value = resp.data.data;
				console.log(clzs);
			})
			.catch((error) => {
				console.error("获取课程列表失败:", error);
			});
	});
</script>

<style scoped>

</style>