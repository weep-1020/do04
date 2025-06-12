<template>
	<div style="text-align: center; border: 1px solid lightseagreen;">
		<h3>学生信息</h3>
		<div style="width: 50%; height: 8%; display: flex; flex-wrap: nowrap;">
			<!-- 选择器 -->
			<div style="display: flex; text-align: center;">
				<el-form :model="pages" label-width="80px" style="display: flex; flex-direction: row;">
					<el-form-item label="学生姓名">
						<el-input v-model="pages.uname" style="width: 150px;" />
					</el-form-item>

					<el-form-item label="学生班级">
						<el-select v-model="pages.clzno" placeholder="请选择" style="width: 150px;">
							<el-option v-for="clz in clzs" :key="clz.clzno" :label="clz.clzname" :value="clz.clzno" />
						</el-select>
					</el-form-item>
				</el-form>
				<el-button @click="pageNum" style="">查询</el-button>
			</div>
		</div>
	</div>

	<div style="display: flex; width: 100%; height: 2000px">
		<div style="width: 100%;">
			<div style="display: flex; justify-content: space-between;">
				<div style="width: 100%;">
					<el-table :data="stus" size="small" border style="width: 100%; height: 100%">
						<el-table-column prop="uid" label="学生ID" />
						<el-table-column prop="uname" label="学生姓名" />
						<el-table-column prop="phone" label="学生电话" />
						<el-table-column prop="clz.clzname" label="班级名称" />
						<el-table-column prop="sex" label="性别" :formatter="setSex" />
						<el-table-column prop="birthday" label="生日" :formatter="formatDate" />
						<el-table-column prop="grade.grade" label="学期" />
						<el-table-column label="商品图片">
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
			<div>
				<el-button @click="upnum()">
					上一页
				</el-button>
				{{beginNum.bnum}}/{{ pagers }}
				<el-button @click="downnum()">下一页
				</el-button>
			</div>
			<hr>
			<el-button @click="go2add">添加学生</el-button>
		</div>
	</div>

	<!-- 弹窗 -->
	<el-dialog title="更新学生信息" v-model="dialogVisible" width="50%">
		<el-form :model="selectedStudent" label-width="80px">
			<el-form-item label="学生ID">
				<el-input v-model="selectedStudent.uid" disabled />
			</el-form-item>
			<el-form-item label="学生姓名">
				<el-input v-model="selectedStudent.uname" />
			</el-form-item>
			<el-form-item label="学生电话">
				<el-input v-model="selectedStudent.phone" />
			</el-form-item>
			<el-form-item label="学生班级">
				<el-select v-model="selectedStudent.clz.clzno" placeholder="请选择">
					<el-option v-for="clz in clzs" :key="clz.clzno" :label="clz.clzname" :value="clz.clzno" />
				</el-select>
			</el-form-item>
			<el-form-item label="学生学期">
				<el-select v-model="selectedStudent.xid" placeholder="请选择">
					<el-option v-for="g in grades" :key="g.xid" :label="g.grade" :value="g.xid" />
				</el-select>
			</el-form-item>
			<el-form-item label="学生头像">
				<el-image v-if="selectedStudent.pic"
					v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${selectedStudent.pic}`"
					style="width: 50px; height:50px" />
				<el-button v-if="selectedStudent.pic" type="danger" size="small" @click="deleteImage">删除图片</el-button>
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

	const stus = ref([]);
	const pages = ref({
		num: 0, //起始数 从第几条数据开始
		lines: 5, //输出条数
		uname: '', //姓名
		clz: {
			clzno: '' //班级id
		},
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

	const grades = ref([]); //
	const clzs = ref([]); //课程

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
		store.state.pagenum = beginNum.value.num;
		const pnum = pages.value.num;
		$get("/api/adm/stu/findPage?pagenum=" + pnum +
				"&lines=" + pages.value.lines + "&uname=" + pages.value.uname +
				"&clzno=" + pages.value.clz.clzno + "&birthday=" + pages.value.birthday +
				"&xid=" + pages.value.xid)
			.then((resp) => {
				console.log(resp.data.data);
				stus.value = resp.data.data.students;
				allnum.value.anum = resp.data.data.total;
				grades.value = resp.data.data.grade;
			});
	}

	function setSex(row, column, value) {
		return value == 1 ? '男' : '女';
	}

	function del(uid) {
		$get("/api/adm/stu/del/" + uid).then((resp) => {
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

	function go2add() {
		router.push({
			path: '/adm/Stuadd'
		})
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
		fd.append("clzno", selectedStudent.value.clz.clzno);
		fd.append("xid", selectedStudent.value.xid);
		fd.append("pic", selectedStudent.value.pic); // 处理图片字段

		console.log('使用 forEach 方法遍历:');
		fd.forEach((value, key) => {
			console.log(`${key}: ${value}`);
		});


		if (imgFile.value) {
			fd.append("mypic", imgFile.value);
		}

		$postf("/api/adm/stu/update", fd)
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
		$get("/api/public/getClzs")
			.then((resp) => {
				clzs.value = resp.data.data;
				console.log(clzs);
			})
			.catch((error) => {
				console.error("获取课程列表失败:", error);
			});
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