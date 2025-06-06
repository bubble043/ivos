<!--字典管理页-->
<template>
  <div style="background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">字典管理</span>
  </div>
  <!-- 字典数据搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="字典名称">
        <el-input placeholder="请输入" style="width:220px;" v-model="searchForm.name"
                  @keydown.enter="selectDict"></el-input>
      </el-form-item>
      <el-form-item label="字典编码">
        <el-input placeholder="请输入" style="width:220px;" v-model="searchForm.code"
                  @keydown.enter="selectDict"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="selectDict">搜索</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 字典列表表格数据卡片 -->
  <el-card style="margin:20px;">
    <div style="margin:10px 0px 40px 10px;">
      字典列表
      <el-button type="primary" style="float: right;margin-top:-3px;" @click="addDict">新增字典</el-button>
    </div>
    <el-table :data="dictArr">
      <el-table-column type="index" label="编号" width="80" align="center"/>
      <el-table-column prop="name" label="字典名称" align="center"/>
      <el-table-column prop="code" label="字典编码" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="showDictOption(scope.row.id)">{{ scope.row.code }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注信息" align="center"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="editDict(scope.row.id)">编辑</el-button>
          <el-button link type="primary" size="small" @click="deleteDict(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 保存字典弹窗 -->
  <el-dialog :title="dialogTitle" v-model="dialogVisible"
             style="width:1000px;padding:40px;"
             :before-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-form-item label="字典名称">
        <el-input placeholder="请输入" v-model="saveDictForm.name"></el-input>
      </el-form-item>
      <el-form-item label="字典编码">
        <el-input placeholder="请输入" v-model="saveDictForm.code"></el-input>
      </el-form-item>
      <el-form-item label="备注信息">
        <el-input placeholder="请输入" v-model="saveDictForm.remark"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="saveDict">保存</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import {onMounted, ref} from "vue";
import qs from "qs";
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";

//定义字典搜索对象
const searchForm = ref({name:'',code:''});
//定义数组用来保存展示的字典表格数据
const dictArr = ref([]);
//定义加载字典的方法
const selectDict = ()=>{
  let data = qs.stringify(searchForm.value);
  axios.get(BASE_URL+'/v1/dict/select?'+data).then((response)=>{
    if(response.data.code==2000){
      dictArr.value = response.data.data;
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
onMounted(()=>{
  selectDict();
})
//定义重置搜索条件的方法
const resetSearch = ()=>{
  searchForm.value = {};//清空搜索条件
  selectDict();//重新加载字典数据
}

//定义新增字典的方法
const addDict = ()=>{
  dialogVisible.value = true;
  dialogTitle.value = '新增字典';
}
//定义变量保存弹窗的标题
const dialogTitle = ref('新增字典');
//定义变量保存字典弹窗是否可见
const dialogVisible = ref(false);
//定义处理弹窗关闭的方法
const handleClose = ()=>{
  if(confirm('是否确认关闭弹窗?')){
    dialogVisible.value = false;
    saveDictForm.value = {};
  }
}

//定义对象用来保存新增字典的数据
const saveDictForm = ref({name:'',code:'',remark:''});
//定义方法保存字典
const saveDict = ()=>{
  let data = qs.stringify(saveDictForm.value);
  axios.post(BASE_URL+'/v1/dict/save',data).then((response)=>{
    if(response.data.code==2000){
      ElMessage.success('保存成功!');
      dialogVisible.value = false;
      saveDictForm.value = {};
      selectDict();
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//定义删除字典的方法
const deleteDict = (id) => {
  if (confirm('您确认要删除此字典吗?')) {
    axios.post(BASE_URL+'/v1/dict/delete/' + id).then((response) => {
      if (response.data.code === 2000) {
        ElMessage.success('删除成功!');
        selectDict();
      } else {
        ElMessage.error(response.data.msg);
      }
    })
  }
}

//定义编辑字典的方法
const editDict = (id)=>{
  dialogTitle.value = '编辑字典';
  dialogVisible.value = true;
  axios.get(BASE_URL+'/v1/dict/select?id='+id).then((response)=>{
    if(response.data.code==2000){
      saveDictForm.value = response.data.data[0];
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//定义加载对应字典的字典项的方法,此处的id是字典id
const showDictOption = (id)=>{
  router.push("/dictOption?id="+id);
}
</script>

<style scoped>

</style>