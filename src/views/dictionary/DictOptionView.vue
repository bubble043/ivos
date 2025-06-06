<!--字典项页-->
<template>
  <div style="background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">字典项管理</span>
    <el-button @click="goBack" type="primary" style="float:right;margin-top:13px;">返回</el-button>
  </div>
  <!-- 字典项搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="字典项名称">
        <el-input placeholder="请输入" style="width:220px;" v-model="searchForm.label"
                  @keydown.enter.prevent="selectDictOption"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="selectDictOption">搜索</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <el-card style="margin:20px;">
    <div style="margin:10px 0px 40px 10px;">
      字典项列表
      <el-button type="primary" style="float: right;margin-top:-3px;" @click="dialogVisible=true">新增字典项</el-button>
    </div>
    <el-table :data="dictOptionArr">
      <el-table-column type="index" label="编号" width="80" align="center"/>
      <el-table-column prop="label" label="字典项名称" align="center"/>
      <el-table-column prop="value" label="字典项值" align="center"/>
      <el-table-column prop="sort" label="字典项排序" align="center"/>
      <el-table-column prop="remark" label="备注信息" align="center" width="320"/>
      <el-table-column prop="createTime" label="创建时间" align="center"/>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" link @click="editDictOption(scope.row.id)">编辑</el-button>
          <el-button type="primary" link @click="deleteDictOption(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <!-- 保存字典项弹窗 -->
  <el-dialog :title="dialogTitle" v-model="dialogVisible" style="width:1000px;padding:40px;"
             :before-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-form-item label="字典项名称">
        <el-input placeholder="请输入" v-model="saveDictOptionForm.label"></el-input>
      </el-form-item>
      <el-form-item label="字典项值">
        <el-input placeholder="请输入" v-model="saveDictOptionForm.value"></el-input>
      </el-form-item>
      <el-form-item label="字典项排序">
        <el-input placeholder="请输入" v-model="saveDictOptionForm.sort"></el-input>
      </el-form-item>
      <el-form-item label="备注信息">
        <el-input placeholder="请输入" v-model="saveDictOptionForm.remark"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="saveDictOption">保存</el-button>
    </template>
  </el-dialog>
</template>
<script setup>
import router from "@/router";
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";

//定义返回到字典页面的方法
const goBack = ()=>{
  router.push('/dict');
}

//定义变量来保存搜索条件
//获取URL中的查询字符串参数
const dictId = new URLSearchParams(window.location.search).get('id');
const searchForm = ref({label:'',dictId:dictId});
//定义字典项数组用来保存字典项数据
const dictOptionArr = ref([]);
//定义查询字典项的方法
const selectDictOption = ()=>{
  let data = qs.stringify(searchForm.value);
  axios.get(BASE_URL+'/v1/dictoption/select?'+data).then((response)=>{
    if(response.data.code==2000){
      dictOptionArr.value = response.data.data;
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
onMounted(()=>{
  selectDictOption();
})
//定义重置搜素的方法
const resetSearch = ()=>{
  //注意：这里清空时不能清空dictId的值，否则会将所有的字典项查出来！
  searchForm.value = {label:'',dictId:dictId};
  selectDictOption();
}

//定义变量控制弹窗是否出现
const dialogVisible = ref(false);
//定义变量保存弹窗的标题
const dialogTitle = ref('新增字典项');
//处理关闭弹窗的方法
const handleClose = ()=>{
  if(confirm('是否关闭本窗口?')){
    dialogVisible.value = false;
    //注意：关闭保存字典项表单的弹窗时，不能清空字典的id值！
    saveDictOptionForm.value = {dictId:dictId, label:'',value:'',sort:'',remark:''};
  }
}

//定义对象用来保存新增+更新字典项时的表单数据
const saveDictOptionForm = ref({
  dictId:dictId,
  label:'',
  value:'',
  sort:'',
  remark:''
});
//定义保存字典项的方法
const saveDictOption = ()=>{
  let data = qs.stringify(saveDictOptionForm.value);
  axios.post(BASE_URL+'/v1/dictoption/save',data).then((response)=>{
    if(response.data.code==2000){
      ElMessage.success('保存成功!');
      dialogVisible.value = false;
      saveDictOptionForm.value = {dictId:dictId, label:'',value:'',sort:'',remark:''};
      selectDictOption();
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//定义编辑字典项的方法
const editDictOption = (id)=>{
  dialogTitle.value = '编辑字典项';
  dialogVisible.value = true;
  axios.get(BASE_URL+'/v1/dictoption/select?id=' + id).then((response) => {
    if (response.data.code == 2000) {
      //注意！！！返回值是个list,记得加[0]
      saveDictOptionForm.value = response.data.data[0];
    } else {
      ElMessage.error(response.data.msg);
    }
  })
}

//定义删除字典项的方法
const deleteDictOption = (id)=>{
  if (confirm('您确认要删除此字典吗?')) {
    axios.post(BASE_URL+'/v1/dictoption/delete/' + id).then((response) => {
      if (response.data.code === 2000) {
        ElMessage.success('删除成功!');
        selectDictOption();
      } else {
        ElMessage.error(response.data.msg);
      }
    })
  }
}



</script>

<style scoped>

</style>