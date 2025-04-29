<template>
	<div class="common-layout">
		<el-container>
			<el-aside class="el-aside_stu" width="200px">
				<div style="color:lightblue;">
					<p style="color: black;width: 100%;">
					<h3>个人主页</h3>
					</p>
				</div>
				<el-menu background-color="lightblue">
					<el-sub-menu index="1">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">查询</span>
						</template>
						<el-menu-item index="1-1">
							<router-link to="/stu/findClass"><el-link>班级查询</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-2">
							<router-link to="/stu/findMark"><el-link>成绩查询</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
				</el-menu>
				<el-menu background-color="lightblue">
					<el-sub-menu index="2">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">校园</span>
						</template>
						<el-menu-item index="1-1">
							<router-link to="/stu/teaMark"><el-link>教师评分</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-2">
							<router-link to="/stu/teaMark"><el-link>优秀教师</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-3">
							<router-link to="/stu/teaMark"><el-link>优秀学生</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
				</el-menu>
				<el-menu background-color="lightblue">
					<el-sub-menu index="3">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">个人信息</span>
						</template>
						<el-menu-item index="1-1">
							<router-link to="/stu/upDatePwd"><el-link>更改密码</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-2">
							<router-link to=""><el-link>。。。</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
				</el-menu>
			</el-aside>
			<el-container>
				<el-main>
					<router-view></router-view>
				</el-main>
			</el-container>
		</el-container>
	</div>
</template>

<script setup>
	import {
		MoreFilled
	} from "@element-plus/icons-vue";
</script>

<style>
	/* 	.common-layout{
		background-image: linear-gradient(to bottom,lightblue, #f9f0ff);
	} */
	.el-aside_stu {
		background-color: lightblue;
		height: calc(100vh);
		border-right: 1px solid lightblue;
	}

	.header {
		height: 100%;
		border-bottom: 1px solid #e4e7ed;
		background-color: lightblue;
	}

	.el-link__inner {
		color: black !important;
	}
</style>