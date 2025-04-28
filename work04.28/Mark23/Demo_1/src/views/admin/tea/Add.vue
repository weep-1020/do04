<template>
	<div style="text-align: center; border: 1px solid lightseagreen;">
		<h3>添加教师</h3>
	</div>
	<div style="display: flex; justify-content: center; margin-top: 20px;">
		<el-form :model="teacher" label-width="100px" style="width: 50%;">
			<el-form-item label="教师姓名">
				<el-input v-model="teacher.uname" />
			</el-form-item>
			<el-form-item label="教师电话">
				<el-input v-model="teacher.phone" />
			</el-form-item>
			<el-form-item label="教师密码">
				<el-input v-model="teacher.password" type="password" />
			</el-form-item>
			<el-form-item label="性别">
				<el-select v-model="teacher.sex" placeholder="请选择性别">
					<el-option label="男" value="1" />
					<el-option label="女" value="2" />
				</el-select>
			</el-form-item>
			<el-form-item label="生日">
				<el-date-picker v-model="teacher.birthday" type="date" placeholder="选择日期" />
			</el-form-item>
			<el-form-item label="教师头像">
				<input type="file" ref="img" @change="handleFileChange" />
				<el-image v-if="teacher.pic" v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${teacher.pic}`"
					style="width: 50px; height:50px" />
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="addTeacher">添加教师</el-button>
				<el-button @click="resetForm">重置</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script setup>
	import {
		ref
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		$postf
	} from "../../../ajax";

	const teacher = ref({
		uname: '',
		phone: '',
		password: '',
		role: '',
		sex: '',
		birthday: '',
		pic: '',
		is_deleted: ''
	});

	const imgFile = ref(null);

	function handleFileChange(event) {
		imgFile.value = event.target.files[0];
	}

	function addTeacher() {
		const fd = new FormData();
		fd.append("uname", teacher.value.uname);
		fd.append("phone", teacher.value.phone);
		fd.append("password", teacher.value.password);
		fd.append("role", "tea");
		fd.append("sex", teacher.value.sex);
		fd.append("birthday", teacher.value.birthday ? new Date(teacher.value.birthday).toISOString().split('T')[0] : '');
		fd.append("is_deleted", 0)
		if (imgFile.value) {
			fd.append("mypic", imgFile.value);
		}

		$postf("/api/adm/tea/add", fd)
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
		teacher.value = {
			uname: '',
			phone: '',
			password: '',
			role: '',
			sex: '',
			birthday: '',
			pic: ''
		};
		imgFile.value = null;
	}
</script>

<style scoped>
	.el-form {
		padding: 20px;
		border: 1px solid #ccc;
		border-radius: 5px;
		background-color: #f9f9f9;
	}
</style>