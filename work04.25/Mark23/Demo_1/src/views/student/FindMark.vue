<template>
	<div class="jb" style="height: 100%;">
		<div style="padding: 20%;">
			<div v-if="marks.length === 0">
				<el-empty description="还没有成绩哦" />
			</div>
			<div v-else>
				<table style="width: 100%; border-collapse: collapse;">
					<thead>
						<tr>
							<th>课程名称</th>
							<!--					<th>平时成绩</th>-->
							<!--					<th>考试成绩</th>-->
							<th>成绩</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(mark, index) in marks" :key="index">
							<td>{{ mark.course.cname }}</td>
							<!--					<td>{{ mark.usualScore }}</td>-->
							<!--					<td>{{ mark.examScore }}</td>-->
							<td>{{ mark.totalScore }}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</template>

<script setup>
	import {
		onMounted,
		ref
	} from "vue";
	import {
		$get
	} from "../../ajax";

	const marks = ref([]);

	onMounted(() => {
		$get("/api/fore/stu/findMyMarks").then((resp) => {
			console.log(resp.data.data);
			marks.value = resp.data.data;
		}).catch((error) => {
			console.error("获取任务列表失败:", error);
		});
	});
</script>

<style>
	/* 	div{
		background-image: linear-gradient(to bottom, #e7feff, #ffffff);
	} */
	.jb {
		background-image: linear-gradient(to bottom, lightblue, #ecd9ff);
	}

	table {
		border: 1px solid #ccc;
	}

	th,
	td {
		border: 1px solid #ccc;
		padding: 8px;
		text-align: left;
	}

	th {
		background-color: #f4f4f4;
	}
</style>