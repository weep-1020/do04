<template>
	<!-- <div style="width: 100%;"> -->
	<div style="display: flex; justify-content: space-between;">
		<div style="width: 100%;color: lightblue;">
			<el-table :data="carousels" size="small" border style="width: 100%; height: 100%">
				<el-table-column prop="cid" label="图片" />
				<el-table-column prop="description" label="活动" />
				<el-table-column prop="pname" label="图片名称" />
				<el-table-column label="图片">
					<template #default="scope">
						<el-image v-bind:src="`http://127.0.0.1:8080/api/public/showimg/${scope.row.pic}`"
							style="width: 120px; height:60px" />
					</template>
				</el-table-column>
				<el-table-column label="操作">
					<template #default="scope">
						<el-button link size="small" type="danger" @click="del(scope.row.cid)">
							删除
						</el-button>
					</template>
				</el-table-column>
				<el-table-column label="是否启用">
					<template #default>
						<el-tooltip :content="'Switch value: ' + value" placement="top">
						    <el-switch
						      v-model="value"
						      style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
						      active-value="100"
						      inactive-value="0"
						    />
						  </el-tooltip>
					</template>
				</el-table-column>
			</el-table>
		</div>
		
	</div>
	<div>
		<el-button @click="go2add()">添加</el-button>
	</div>
</template>

<script setup>
	import {
		ref,
		onMounted,
		computed
	} from 'vue';
	import router from '../../router';
	import {
		$get
	} from "../../ajax";
	import {
		store
	} from "../../store/index.js"
	const carousels = ref([]);
	
	function go2add(){
		router.push({
			path:"/adm/photoAdd"
		})
	}
	
	function del(cid){
		$get("/api/adm/photo/del?cid"+cid).then((resp)=>{
			console.log(resp.data.data);
		})
	};
	onMounted(() => {
		$get("api/public/findCarousel").then((resp) => {
			carousels.value = resp.data.data;
			console.log(carousels);
		}).catch((error) => {
			console.log("资源未请求到：", error);
		})
	})
</script>

<style>
</style>