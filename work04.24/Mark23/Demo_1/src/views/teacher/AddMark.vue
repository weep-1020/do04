<template>
	<div>
		<h3>批量添加分数</h3>
		<el-table :data="marks" style="width: 100%" v-if="!isEmpty">
			<el-table-column prop="userAccount.uid" label="成绩ID" />
			<el-table-column prop="userAccount.uname" label="学生姓名" />
			<el-table-column prop="clz.clzname" label="学生班级" />
			<el-table-column prop="course.cname" label="学生科目" />
			<el-table-column prop="termId" label="学生年级" />
			<el-table-column label="平时分">
				<template #default="{ row }">
					<el-input v-model="row.usualScore" />
				</template>
			</el-table-column>
			<el-table-column label="考试成绩">
				<template #default="{ row }">
					<el-input v-model="row.examScore" />
				</template>
			</el-table-column>
		</el-table>
		<el-button @click="submitMarks">批量提交</el-button>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted,
		computed,
		watch
	} from 'vue';
	import {
		useRoute,
		useRouter
	} from 'vue-router';
	import {
		$get,
		$postj
	} from "../../ajax";
	import {
		ElMessage
	} from 'element-plus';

	const route = useRoute();
	const router = useRouter();

	const form = ref({
		clzno: "",
		cno: ""
	});

	// 初始化 marks 为一个空数组
	const marks = ref([
		// {clz:{
		// 	clzno:'',
		// 	clzname:'',
		// 	},
		// course:{
		// 	cno:'',
		// 	cname:'',
		// 	},
		// user:{
		// 	uid:'',
		// 	uname:'',
		// 	},
		// markId:'',
		// termId:'',
		// totalScore:'',
		// usualScore:''
		// }
	]);
	const isEmpty = computed(() => marks.value.length === 0);


	// 定义 fetchMarks 函数
	const fetchMarks = () => {
		// 获取已有分数
		$get("/api/fore/tea/findMark", form.value).then((resp) => {
			console.log(resp.data.data);
			marks.value = resp.data.data;
			console.log(marks.value);
		}).catch((error) => {
			console.error("获取任务列表失败:", error);
			// 给用户更友好的错误提示
			ElMessage.error("获取分数列表失败，请稍后重试");
		});
	};

	// 批量提交修改后的成绩
	const submitMarks = () => {
		$postj("/api/fore/tea/addMark", marks.value).then((resp) => {
			console.log(resp.data);
			ElMessage.success("成绩修改成功");
			// 提交成功后重新获取数据
			fetchMarks();
		}).catch((error) => {
			console.error("提交成绩失败:", error);
			ElMessage.error("提交成绩失败，请稍后重试");
		});
	};

	// 监听路由参数的变化
	watch(() => route.query, (newQuery) => {
		form.value.clzno = newQuery.clzno;
		form.value.cno = newQuery.cno;
		// 当路由参数变化时，重新获取数据
		fetchMarks();
	}, {
		immediate: true
	});

	onMounted(() => {
		// 初始加载时获取数据
		fetchMarks();
	});
</script>

<style>
</style>

