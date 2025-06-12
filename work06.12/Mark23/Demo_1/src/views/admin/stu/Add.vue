<template>
	<div style="text-align: center; border: 1px solid lightseagreen;">
		<h3>添加学生</h3>
	</div>
	<div style="display: flex; justify-content: center; margin-top: 20px;">
		<el-form :model="student" label-width="100px" style="width: 50%;">
			<el-form-item label="学生姓名">
				<el-input v-model="student.uname" />
			</el-form-item>
			<el-form-item label="学生电话">
				<el-input v-model="student.phone" />
			</el-form-item>
			<el-form-item label="密码">
				<el-input v-model="student.password" type="password" />
			</el-form-item>
			<el-form-item label="性别">
				<el-select v-model="student.sex" placeholder="请选择性别">
					<el-option label="男" value="1" />
					<el-option label="女" value="2" />
				</el-select>
			</el-form-item>
			<el-form-item label="生日">
				<el-date-picker v-model="student.birthday" type="date" placeholder="选择日期" />
			</el-form-item>
			<el-form-item label="班级">
				<el-select v-model="clzs" placeholder="请选择班级">
					<el-option v-for="clz in clzs" :key="clz.clzno" :label="clz.clzname" :value="clz.clzno" />
				</el-select>
			</el-form-item>
			<el-form-item label="学期">
				<!-- <el-input v-model="student.xid" /> -->
				<el-select v-model="grades" placeholder="请选择学期">
					<el-option v-for="grade in grades" :key="grade.xid" :label="grade.grade" :value="grade.xid" />
				</el-select>
			</el-form-item>
			<el-form-item label="学生头像">
				<input type="file" ref="img" @change="handleFileChange" />
				<el-image v-if="student.pic" v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${student.pic}`"
					style="width: 50px; height:50px" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="addStudent">添加学生</el-button>
				<el-button @click="resetForm">重置</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		$get,
		$postf
	} from "../../../ajax";

	const student = ref({
		uname: '',
		phone: '',
		password: '',
		sex: '',
		birthday: '',
		clz: {
			clzno: '',
			clzname: ''
		},
		grade: {
			xid: '',
			grade: ''
		},
		pic: ''
	});

	const imgFile = ref(null);
	const classList = ref([]);
	let grades = ref([]);
	let clzs = ref([]);
	let newClz = ref({
		clzno: '',
		clzname: '',
		grade: ''
	});

	function handleFileChange(event) {
		imgFile.value = event.target.files[0];
	}

	function addStudent() {
		const fd = new FormData();
		fd.append("uname", student.value.uname);
		fd.append("phone", student.value.phone);
		fd.append("password", student.value.password);
		fd.append("sex", student.value.sex);
		fd.append("birthday", student.value.birthday ? new Date(student.value.birthday).toISOString().split('T')[0] : '');
		fd.append("clzno", student.value.clz.clzno);
		fd.append("xid", student.value.grade.xid);
		fd.append("pic", student.value.pic)
		if (imgFile.value) {
			fd.append("mypic", imgFile.value);
		}

		$postf("/api/adm/stu/add", fd)
			.then((resp) => {
				console.log(resp.data);
				ElMessage.success("添加成功");
				resetForm();
			})
			.catch((error) => {
				console.error("添加失败:", error);
				ElMessage.error("添加失败");
			});
	}

	function resetForm() {
		student.value = {
			uname: '',
			phone: '',
			password: '',
			sex: '',
			birthday: '',
			clzno: '',
			xid: '',
			pic: ''
		};
		imgFile.value = null;
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

		$get("/api/public/getGrades").then((resp) => {
				grades.value = resp.data.data;
				console.log(grades);
			})
			.catch((error) => {
				console.error("获取年级表失败",error);
			});
	});
</script>

<style scoped>
	.el-form {
		padding: 20px;
		border: 1px solid #ccc;
		border-radius: 5px;
		background-color: #f9f9f9;
	}
</style>
<!--2.前后端带图片增删改查及流程描述-->