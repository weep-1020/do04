<template>
	<div class="update-password-form">
		<el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
			<el-form-item label="旧密码" prop="oldPassword">
				<el-input v-model="form.oldPassword" type="password" show-password></el-input>
			</el-form-item>
			<el-form-item label="新密码" prop="newPassword">
				<el-input v-model="form.newPassword" type="password" show-password></el-input>
			</el-form-item>
			<el-form-item label="确认新密码" prop="confirmPassword">
				<el-input v-model="form.confirmPassword" type="password" show-password></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="gave">提交</el-button>
				<el-button @click="cx">重置</el-button>
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
	import axios from 'axios';
	import {
		$postf,
		$postj
	} from "../../ajax";

	const formRef = ref(null);
	const form = ref({
		oldPassword: '',
		newPassword: '',
		confirmPassword: ''
	});

	const rules = {
		oldPassword: [{
			required: true,
			message: '请输入旧密码',
			trigger: 'blur'
		}],
		newPassword: [{
				required: true,
				message: '请输入新密码',
				trigger: 'blur'
			},
			{
				min: 2,
				message: '新密码至少6位',
				trigger: 'blur'
			}
		],
		confirmPassword: [{
				required: true,
				message: '请确认新密码',
				trigger: 'blur'
			},
			{
				validator: validateConfirmPassword,
				trigger: 'blur'
			}
		]
	};

	function validateConfirmPassword(rule, value, callback) {
		if (value !== form.value.newPassword) {
			callback(new Error('两次输入的新密码不一致'));
		} else {
			callback();
		}
	}

	// 提交新的密码
	function gave() {
		formRef.value.validate((valid) => {
			if (valid) {
				console.log(form.value);
				const fd = new FormData();
				fd.append("oldPwd", form.value.oldPassword);
				fd.append("newPwd", form.value.newPassword);
				$postf("/api/fore/tea/updatePwd", fd)
					.then((resp) => {
						console.log("数据结果："+resp.data);
						if (resp.data.msg != undefined) {
							ElMessage.success('密码修改成功');
							cx(); // 重置表单
						} else {
							ElMessage.error('密码修改失败: ' + resp.data.msg);
						}
					})
					.catch((error) => {
						console.error('请求失败:', error);
						ElMessage.error('请求失败，请稍后再试');
					});
			} else {
				console.log('表单验证失败');
				ElMessage.error('请检查表单信息');
				return false;
			}
		});
	}

	// 重置表单内容
	function cx() {
		form.value.oldPassword = '';
		form.value.newPassword = '';
		form.value.confirmPassword = '';
		formRef.value.resetFields();
	}
</script>

<style scoped>
	.update-password-form {
		width: 400px;
		margin: 100px auto;
	}
</style>