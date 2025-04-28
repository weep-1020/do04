<template>
	<div style="text-align: center; border: 1px solid lightseagreen;">
		<h3>教师信息</h3>
	</div>
	<div style="display: flex; width: 100%; height: 2000px">
		<div style="width: 100%;">
			<div style="display: flex; justify-content: space-between;">
				<div style="width: 100%;">
					<el-table :data="teas" size="small" border style="width: 100%; height: 100%">
						<el-table-column prop="uid" label="教师ID" />
						<el-table-column prop="uname" label="教师姓名" />
						<el-table-column prop="phone" label="教师电话" />
						<el-table-column prop="sex" label="性别" :formatter="setSex" />
						<el-table-column prop="birthday" label="生日" :formatter="formatDate" />
						<el-table-column label="教师头像">
							<template #default="scope">
								<el-image v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${scope.row.pic}`"
									style="width: 50px; height:50px" />
							</template>
						</el-table-column>
						<el-table-column prop="excellence" label="优秀值" />
						<el-table-column label="操作">
							<template #default="scope">
								<el-button link size="small" type="danger" @click="del(scope.row.id)">
									删除
								</el-button>
								<el-button link size="small" type="primary" @click="go2Update(scope.row)">
									更新
								</el-button>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</div>
			<div v-if="!allnum.ye<=1">
				<el-button @click="upnum()">
					上一页
				</el-button>
				{{beginNum.bnum}}/{{ pagers }}
				<el-button @click="downnum()">下一页
				</el-button>
			</div>
			<hr>
			<el-button @click="go2add">添加教师</el-button>
		</div>
	</div>

	<!-- 弹窗 -->
	<el-dialog title="更新学生信息" v-model="dialogVisible" width="50%">
		<el-form :model="selectedStudent" label-width="80px">
			<el-form-item label="教师ID">
				<el-input v-model="selectedStudent.uid" disabled />
			</el-form-item>
			<el-form-item label="教师姓名">
				<el-input v-model="selectedStudent.uname" />
			</el-form-item>
			<el-form-item label="教师电话">
				<el-input v-model="selectedStudent.phone" />
			</el-form-item>
			<el-form-item label="教师头像">
				<el-image v-if="selectedStudent.pic"
					v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${selectedStudent.pic}`"
					style="width: 50px; height:50px" />
				<input type="file" ref="img" @change="handleFileChange" />
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="dialogVisible = false">取消</el-button>
				<el-button type="primary" @click="updateStudent">确定</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script setup>
	import 'dayjs/locale/zh-cn'
	import {
		ref,
		reactive,
		onMounted,
		computed
	} from 'vue';
	import {
		ElMessage
	} from 'element-plus';
	import {
		store
	} from "../../../store";
	import router from "../../../router";
	import {
		$get,
		$postx,
		$postj,
		$postf
	} from "../../../ajax";

	const teas = ref([]);
	const pages = ref({
		num: 0, //起始数 从第几条数据开始
		lines: 5, //输出条数
		uname: '', //姓名
		clzno: '', //班级id
		birthday: '', // 生日
		xid: '', // 学期
	});

	const beginNum = ref({
		bnum: 1, //当前页数
	});
	const allnum = ref({
		anum: 0, //总行数
		ye: 0 //总页数
	})

	const dialogVisible = ref(false);
	const selectedStudent = ref({});
	const imgFile = ref(null);

	function formatDate(row, column, value) {
		const date = new Date(value);
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');
		return `${year}-${month}-${day}`;
	}

	function go2add() {
		router.push({
			path: '/adm/Teaadd'
		})
	}

	function downnum() {
		beginNum.value.bnum++;
		if (beginNum.value.bnum > allnum.value.ye) {
			beginNum.value.bnum = allnum.value.ye;
		}
		pages.value.num = (beginNum.value.bnum - 1) * pages.value.lines;
		pageNum();
	}

	function upnum() {
		beginNum.value.bnum--;
		beginNum.value.bnum--;
		pages.value.num = beginNum.value.bnum * pages.value.lines;
		if (beginNum.value.bnum <= 0) {
			beginNum.value.bnum = 0;
		}
		beginNum.value.bnum++;
		pageNum();
	}

	const pagers = computed(() => {
		allnum.value.ye = Math.ceil(allnum.value.anum / pages.value.lines);
		return allnum.value.ye
	});

	function pageNum() {
		store.state.pagenum = pages.value.num;
		const pnum = pages.value.num;
		$get("/api/adm/tea/findPage?pagenum=" + pnum +
				"&lines=" + pages.value.lines + "&uname=" + pages.value.uname +
				"&clzno=" + pages.value.clzno + "&birthday=" + pages.value.birthday +
				"&xid=" + pages.value.xid)
			.then((resp) => {
				console.log(resp.data.data);
				teas.value = resp.data.data.teachers;
				allnum.value.anum = resp.data.data.total;
			});
	}

	function setSex(row, column, value) {
		return value == 1 ? '男' : '女';
	}

	function del(uid) {
		$get("/api/adm/tea/del/" + uid).then((resp) => {
			console.log(resp.data);
			// 刷新当前页面数据
			pageNum(); // 重新加载当前页
		});
	}

	function go2Update(student) {
		selectedStudent.value = {
			...student
		};
		dialogVisible.value = true;
	}

	function deleteImage() {
		selectedStudent.value.pic = null;
		imgFile.value = null;
	}

	function handleFileChange(event) {
		imgFile.value = event.target.files[0];
	}

	function updateStudent() {
		const fd = new FormData();
		fd.append("uid", selectedStudent.value.uid);
		fd.append("uname", selectedStudent.value.uname);
		fd.append("phone", selectedStudent.value.phone);
		fd.append("clzno", selectedStudent.value.clzno);
		fd.append("xid", selectedStudent.value.xid);
		fd.append("pic", selectedStudent.value.pic); // 处理图片字段

		if (imgFile.value) {
			fd.append("mypic", imgFile.value);
		}

		$postf("/api/adm/tea/update", fd)
			.then((resp) => {
				console.log(resp.data);
				ElMessage.success("更新成功");
				dialogVisible.value = false;
				pageNum(); // 重新加载当前页
			})
			.catch((error) => {
				console.error("更新失败:", error);
				ElMessage.error("更新失败");
			});
	}

	onMounted(() => {
		pageNum();
	});
</script>

<style>
	.el-table__row {
		height: 30px !important;
	}

	.el-table__inner-wrapper {
		height: 100% !important;
	}
</style>