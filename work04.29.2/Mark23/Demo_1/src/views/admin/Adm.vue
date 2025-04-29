<template>
	<div class="common-layout">
		<el-container>
			<el-aside class="el-aside_adm" width="200px">
				<div style="background-color: rgba(0, 0, 0, 0.0); ">
					<p style="background-color: rgba(0, 0, 0, 0.0); ;width: 100%;">
						管理项目
					</p>
				</div>
				<el-menu background-color: lightblue;>
					<el-sub-menu index="1">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">教学安排</span>
						</template>
						<el-menu-item index="1-1">
							<router-link to="/adm/Clz"><el-link>班级管理</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-2">
							<router-link to="/adm/course"><el-link>课程管理</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="1-3">
							<router-link to="/adm/task"><el-link>课程安排</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
					<el-sub-menu index="2">
						<template style="display: flex;" #title>
							<el-icon>
								<MoreFilled />
							</el-icon>
							<span style="text-align: center;width: 100%;">人员安排</span>
						</template>
						<el-menu-item index="2-1">
							<router-link to="/adm/teashow"><el-link>教师管理</el-link></router-link>
						</el-menu-item>
						<el-menu-item index="2-2">
							<router-link to="/adm/stushow"><el-link>学生管理</el-link></router-link>
						</el-menu-item>
					</el-sub-menu>
					<el-menu-item index="3">
						<router-link to="/adm/photo"><el-link>管理首页图片</el-link></router-link>
					</el-menu-item>
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

<script>
	import {
		defineComponent
	} from "vue";
	import {
		InfoFilled,
		MoreFilled
	} from "@element-plus/icons-vue";

	import {
		Document,
		Menu as IconMenu,
		Location,
		Setting,
	} from '@element-plus/icons-vue'
	export default defineComponent({
		components: {
			MoreFilled,
			InfoFilled
		}
	})
</script>

<style>
	.el-aside_adm {
		background-color: lightblue;
		/* background-color: rgba(0, 0, 0, 0.0); */
		height: calc(100vh);
		border-right: 1px solid lightblue;
	}

	.el-menu-item {
		background-color: lightblue;
		/* background-color: rgba(0, 0, 0, 0.0); */
	}

	.header {
		height: 100%;
		border-bottom: 1px solid #e4e7ed;
		background-color: lightblue;
	}
</style>