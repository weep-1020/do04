<template>
	<div class="common-layout">
		<el-container>
			<el-aside class="el-aside_adm" width="200px">
				<div style="color:lightblue;">
					<p style="color: black;width: 100%;">
						管理项目
					</p>
				</div>
				<el-menu background-color="lightblue">
					<el-sub-menu index="1">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">教学</span>
						</template>
						<el-menu-item index="1-1">
							<router-link to="/tea/findTask"><el-link>班级查询</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-3">
							<router-link to="/tea/go2Mark"><el-link>登记分数</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
					<el-sub-menu index="2">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">我的</span>
						</template>
						<el-menu-item index="2-1">
							<router-link to=""><el-link>更改头像</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="2-2">
							<router-link to="/tea/upDatePwd"><el-link>更改密码</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
				</el-menu>
			</el-aside>
			<el-container>
				<el-header>
					<div class="header">
					</div>
				</el-header>
				<el-main>
					<router-view></router-view>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script>
	import {
		defineComponent
	} from "vue";
	import {
		MoreFilled
	} from "@element-plus/icons-vue";

	export default defineComponent({
		components: {
			MoreFilled
		}
	})
</script>

<style>
	.el-aside_tea {
		background-color: lightblue;
		height: calc(100vh);
		border-right: 1px solid lightblue;
	}

	.header {
		height: 100%;
		border-bottom: 1px solid #e4e7ed;
		background-color: lightblue;
	}
</style>