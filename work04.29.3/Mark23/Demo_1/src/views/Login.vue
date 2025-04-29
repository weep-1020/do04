<template>
	<div class="common-layout">
		<el-container>
			<!-- 左侧边栏 -->
			<el-aside width="200px" class="dashed_right"></el-aside>
			<!-- 主内容区域 -->
			<el-main style="height: 100%;">
				<div style="display:flex;padding-top: 10%;">
					<div style="margin: 0 auto;height: 400px;">
						<div>
							<el-text type="success" style="text-align: center; font-size: 26px;">
								登录
							</el-text>
							<hr>
						</div>
						<!-- 登录表单 -->
						<el-form :inline="false">
							<!-- 用户名输入框 -->
							<el-form-item>
								用户名：<el-input v-model="user.username" style="width: 240px" placeholder="username" />
							</el-form-item>
							<!-- 密码输入框 -->
							<el-form-item>
								密&emsp;码： <el-input v-model="user.password" style="width: 240px" type="password"
									placeholder="Please input password" show-password />
							</el-form-item>
						</el-form>
						<!-- 登录按钮 -->
						<el-button type="success" @click="login">
							login
						</el-button>
					</div>
				</div>
			</el-main>
			<!-- 右侧边栏 -->
			<el-aside width="200px" class="dashed_left"></el-aside>
		</el-container>
	</div>
</template>

<script setup>
	import {
		ref,
		reactive
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		Store
	} from "vuex";
	import {
		store
	} from "../store";
	import router from "../router/index.js";
	import {
		$get,
		$postf,
		$postj,
		$postx
	} from '../ajax'

	// 未使用的ref变量
	const input = ref('')

	// 用户信息对象
	const user = reactive({
		username: '',
		password: ''
	})

	// 显示消息的方法
	const open = () => {
		ElMessage('This is a message.')
	}

	/**
	 * 登录功能实现
	 * 该方法用于处理用户登录逻辑，目前仅检查用户对象是否为空
	 */
	function login() {
		console.log('开始登录');
		if (user.username === '') {
			ElMessage('账号密码不能为空');
			return;
		}
		$postf("/api/login", user).then((resp) => {
			// 添加token到window
			window.localStorage.setItem('token', resp.data.data.token);
			window.localStorage.setItem('user', JSON.stringify(resp.data.data));

			console.log(resp.data.data.token);

			$get("/api/user/info").then((resp) => {
				console.log(resp.data);

				// 将获取出来的信息存储到当前容器中
				store.state.user.name = resp.data.data.uname;
				store.state.user.pic = resp.data.data.pic;
				console.log("图片ID:" + resp.data.data.pic)
				store.state.user.token = window.localStorage.getItem("token");
				store.state.user.role = resp.data.data.role;
				console.log(store.state.user)

				if (resp.data.data.role === 'adm') {
					console.log("管理员系统")
					router.push('/adm');
					return;
				}
				if (resp.data.data.role === 'tea') {
					console.log("教师用户系统")
					router.push('/tea');
					return;
				}
				if (resp.data.data.role === 'stu') {
					console.log("学生用户系统")
					router.push('/stu');
				}
			});
		});
	}
</script>

<style>
	/* 左侧边栏样式 */
	.dashed_right {
		border-right: 2px solid lightblue;
	}

	/* 右侧边栏样式 */
	.dashed_left {
		border-left: 2px solid lightblue;
	}
</style>