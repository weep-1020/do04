<template>
  <div>
    <h3>添加</h3>
  </div>
  <div style="display: flex; justify-content: center; margin-top: 20px;">
    <el-form :model="carousel" label-width="100px" style="width: 50%;">
      <el-form-item label="活动ID">
        <el-input v-model="carousel.description" />
      </el-form-item>
      <el-form-item label="图片名称">
        <input type="file" ref="img" @change="handleFileChange" />
      </el-form-item>
      <el-form-item label="是否启用">
        <el-switch v-model="carousel.ifOpen" :active-value="1" :inactive-value="0" />
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-button type="primary" @click="add">添加</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { $postf } from "../../ajax";

const imgFile = ref(null);
const carousel = ref({
  description: '',
  pname: '',
  ifOpen: 0, // 默认关闭状态
  pic: ''
});

function handleFileChange(event) {
  imgFile.value = event.target.files[0];
}

function add() {
  // 判断是否填写了必要字段
  if (!carousel.value.description || !imgFile.value) {
    ElMessage.error("活动ID和图片都不能为空");
    return;
  }

  const fd = new FormData();
  fd.append("description", carousel.value.description);
  fd.append("isOpen", 0); // 使用开关值

  if (imgFile.value) {
    fd.append("mypic", imgFile.value);
  }

  $postf("api/adm/photo/add", fd)
    .then((resp) => {
      console.log(resp.data);
      ElMessage.success("添加成功");
    })
    .catch((error) => {
      console.error("添加失败", error);
      ElMessage.error("添加失败");
    });
}
</script>

<style>
</style>




<!--<template>-->
<!--	<div>-->
<!--		<h3>添加</h3>-->
<!--	</div>-->
<!--	<div style="display: flex; justify-content: center; margin-top: 20px;">-->
<!--		<el-form :model="carousel" label-width="100px" style="width: 50%;">-->
<!--			<el-form-item label="活动ID">-->
<!--				<el-input v-model="carousel.description" />-->
<!--			</el-form-item>-->
<!--			<el-form-item label="图片名称">-->
<!--				<input type="file" ref="img" @change="handleFileChange" />-->
<!--			</el-form-item>-->
<!--		</el-form>-->
<!--	</div>-->
<!--	<div>-->
<!--		<el-button type="primary" @click="add">添加</el-button>-->
<!--	</div>-->
<!--</template>-->

<!--<script setup>-->
<!--	import {-->
<!--		ref,-->
<!--		onMounted-->
<!--	} from 'vue';-->
<!--	import {-->
<!--		ElMessage-->
<!--	} from 'element-plus';-->
<!--	import {-->
<!--		$get,-->
<!--		$postf-->
<!--	} from "../../ajax";-->
<!--	const imgFile = ref(null);-->

<!--	const carousel = ref({-->
<!--		description: '',-->
<!--		pname: '',-->
<!--		ifOpen: '',-->
<!--		pic: ''-->
<!--	});-->

<!--	function handleFileChange(event) {-->
<!--		imgFile.value = event.target.files[0];-->
<!--	};-->

<!--	function add() {-->
<!--		const fd = new FormData()-->
<!--		if (carousel.value != undefined) {-->
<!--			fd.append("description", carousel.value.description);-->
<!--			fd.append("isOpen", 0);-->
<!--			if (imgFile.value) {-->
<!--				fd.append("mypic", imgFile.value);-->
<!--			};-->

<!--			$postf("api/adm/photo/add", fd).then((resp) => {-->
<!--					console.log(resp.data);-->
<!--					ElMessage.success("添加成功");-->
<!--				})-->
<!--				.catch((error) => {-->
<!--					console.error("添加失败", error);-->
<!--					ElMessage.error("添加失败")-->
<!--				})-->

<!--		}-->
<!--	}-->
<!--</script>-->

<!--<style>-->
<!--</style>-->