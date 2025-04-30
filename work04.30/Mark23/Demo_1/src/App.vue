<template>
	<div class="gradient-background">
		<div style="height: 90%;">
			<el-container>
				<el-header height="80px">
					<!--          <el-button @click="test"></el-button>-->
					<!-- 如果用户未登录，显示登录按钮 -->
					<div v-if="!store.state.user.role" style="width: 100%; height: 60px">
						欢迎使用教学系统
						<div>
							<el-button type="success" link @click="cvb = 'try'" style="font-size: 20px;">登录</el-button>
						</div>
					</div>
					<div v-else style="display: flex; width: 100%;">
						<div style="width: 10%;">
							<div class="demo-image__error">
								<div class="block">

								</div>
							</div>
						</div>
						<!-- 如果用户已登录，显示用户信息和退出按钮 -->
						<div style="display: flex; justify-content: flex-end; width: 90%; height: 80%">
							<!-- <div class="left-panel"></div> -->
							<div style="width: 10%">
								<p>{{ roleName }}</p>
							</div>
							<div style="width: 10%">
								<p>{{ store.state.user.name }}</p>
							</div>
							<div style="width: 10%; padding-top: 5px">
								<el-avatar shape="square" :size="45">
									<!-- 3<img :src='store.state.ip + "/api/public/showimg/"+store.state.user.pic' alt="img"/> -->
									<el-image
										v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${store.state.user.pic}`" />
								</el-avatar>
							</div>
							<div style="width: 10%; padding-top: 5px">
								<el-button size="large" type="success" plain
									@click="centerDialogVisible = true">退出</el-button>
							</div>
							<!-- 退出登录确认对话框 -->
							<el-dialog v-model="centerDialogVisible" title="选择" width="500" center>
								<div style="text-align: center">
									<span>是否退出登录？</span>
								</div>
								<template #footer>
									<div class="dialog-footer">
										<el-button @click="centerDialogVisible = false">取消</el-button>
										<el-button type="primary" @click="logout">确认</el-button>
									</div>
								</template>
							</el-dialog>
						</div>
					</div>
					<!-- <div class="separator"></div> -->
				</el-header>
				<el-main>
					<div>
						<!-- 如果用户未登录，显示轮播图 -->
						<div v-if="cvb===''">
							<el-carousel :interval="4000" type="card" height="400px">
								<el-carousel-item v-for="carousel in carousels" :key="carousel.cid">
									<h3 text="2xl" justify="center"><el-image v-if="carousel.pic"
											v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${carousel.pic}`"
											style="width: 100%x; height: 100%;" /></h3>
								</el-carousel-item>
							</el-carousel>
						</div>
						<!-- 如果用户已登录，显示路由视图 -->
						<div v-if="cvb!==''">
							<router-view></router-view>
						</div>
					</div>
				</el-main>
			</el-container>
		</div>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted,
		computed
	} from 'vue';
	import router from "./router";
	import {
		$get
	} from "./ajax";
	import {
		store
	} from "./store";
	const carousels = ref([]);

	// 临时状态标志，用于触发登录逻辑
	const cvb = ref('');
	// 控制退出登录对话框的显示
	const centerDialogVisible = ref(false);
	const url = store.state.ip + "/api/public/showimg/" + store.state.user.pic;
	// 退出登录函数
	function logout() {
		centerDialogVisible.value = false;
		router.push("/Login");
		store.state.user.token = '';
		cvb.value = '';
		store.state.user.role = '';
		console.log(store.state.user);
	}
	// const test = () => {
	//   $get("/api/public/showimg/101").then(resp => {
	//     console.log(resp.data)
	//   })
	// }
	const roleName = computed(() => {
		const userRole = store.state.user.role;

		if (userRole === 'adm') {
			return "管理人员";
		}
		if (userRole === 'stu') {
			return "学生";
		}
		if (userRole === 'tea') {
			return "教师";
		}
		return "未知角色"; // 默认值，可选
	});

	// 页面加载后1秒后跳转到登录页面
	onMounted(() => {
		setTimeout(() => {
			router.push("/login");
		}, 1000); // 1秒后跳转到目标页面
		// $get("/api/public/showimg/"+store.state.user.pic).then(resp => {
		//   console.log(resp.data)
		// })
		$get("api/public/findCarousel").then((resp) => {
			carousels.value = resp.data.data;
			console.log(carousels)
		}).catch((error) => {
			console.log("资源未请求到", error)
		});
	});
</script>

<style>
	#app {
		font-family: Avenir, Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}

	/* 头像使用样式 */
	.gradient-background {
		height: 100vh;
		/* background-image: linear-gradient(to bottom, #e7feff, #ffffff); */
		/* background-color: lightblue; */
		overflow: hidden;
	}

	.demo-basic .demo-basic--circle,
	.demo-basic .demo-basic--square {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.demo-basic .block:not(:last-child) {
		border-right: 1px solid var(--el-border-color);
	}

	.demo-basic .block {
		flex: 1;
	}
	
	/* el-header显示下方边框 */
	.el-header {
	  /* border-bottom: 2px solid #ff4949; */
	   /* 2px 红色实线 */
	  /* 或虚线 */
	  /* border-bottom: 1px dashed #999; */
	  /* 或渐变边框（高级用法） */
	  /* border-bottom: 0; */
	  /* box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05); */
	  /* 模拟浅色阴影分隔线 */
	}
	/* 边框分隔 */
	.left-panel {
		border-right: 1px solid #ccc;
		/* 右侧分隔线 */
	}

	.right-panel {
		border-left: 1px solid #ccc;
		/* 右侧分隔线 */
	}

	/* 上下分隔 */
	.separator {
		height: 1px;
		/* 分隔线高度 */
		background-color: #ccc;
		/* 分隔线颜色 */
		margin-bottom: auto 0;
		/* 分隔线与上下内容的间距 */
	}

	.demo-image__error .block {
		text-align: center;
		border-right: solid 1px var(--el-border-color);
		display: inline-block;
		width: 49%;
		box-sizing: border-box;
	}

	.demo-image__error .demonstration {
		display: block;
		color: var(--el-text-color-secondary);
		font-size: 14px;
		margin-bottom: 20px;
	}

	.demo-image__error .el-image {
		width: 100%;
		height: 50px;
	}

	.demo-image__error .image-slot {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100%;
		background: var(--el-fill-color-light);
		color: var(--el-text-color-secondary);
		font-size: 30px;
	}

	.demo-image__error .image-slot .el-icon {
		font-size: 30px;
	}

	/* 轮播图 */
	.el-carousel__item h3 {
		color: #475669;
		opacity: 0.75;
		line-height: 200px;
		margin: 0;
		text-align: center;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n + 1) {
		background-color: #d3dce6;
	}

	.el-header {
		padding: 0 !important;
	}

	.el-main {
		padding: 0 !important;
	}
	.el-table {
	  border: 0 !important; /* 清除外部边框 */
	}
</style>