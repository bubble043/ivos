<!--用车申请页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">申请单列表</span>
    <el-button type="primary" style="float:right;margin-top:13px;"
    @click="addApplicationDialogVisible=true"
    :disabled="user.parentId==null">申请用车</el-button>
  </div>
  <!-- 申请用车弹窗 -->
  <el-dialog title="创建申请单" v-model="addApplicationDialogVisible" style="width:1000px;padding:40px;"
             :before-close="handleClose">
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车人">
            <el-input readonly :value="user.username"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="使用时间">
            <el-date-picker
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
                v-model="times"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="车辆出发地">
            <el-input placeholder="请输入" v-model="addForm.departureAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="车辆目的地">
            <el-input placeholder="请输入" v-model="addForm.destinationAddr"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="用车事由">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"
            v-model="addForm.reason"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注">
            <el-input placeholder="请输入" type="textarea" resize="none" :rows="3"
            v-model="addForm.remark"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="30">
        <el-col :span="12">
          <el-form-item label="驾照">
            <el-upload
                v-model:file-list="fileList"
                v-model:action="actionUrl"
                name="file"
                limit="1"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
            >
              <el-icon>
                <Plus/>
              </el-icon>
            </el-upload>
            <el-dialog v-model="dialogVisible">
              <img w-full :src="dialogImageUrl"/>
            </el-dialog>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="审批人">
            <el-row :gutter="10">
              <el-col :span="12" v-if="auditUserOpts.length>0">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>{{auditUserOpts[0].username}}</span>
                </el-tag>
              </el-col>
              <el-col :span="12" v-if="auditUserOpts.length>1">
                <el-tag type="primary">
                  <el-icon style="margin-right:5px;position:relative;top:2px;">
                    <User/>
                  </el-icon>
                  <span>{{auditUserOpts[1].username}}</span>
                </el-tag>
              </el-col>
            </el-row>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="addApplication">确定</el-button>
    </template>
  </el-dialog>

  <!-- 用车申请列表 搜索卡片 -->
  <el-card style="margin: 20px;">
    <el-form style="padding-top:10px;">
      <el-row :gutter="30">
        <el-col :span="5">
          <el-form-item label="出发地">
            <el-input placeholder="请输入出发地" v-model="searchApplication.departureAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="目的地">
            <el-input placeholder="请输入目的地" v-model="searchApplication.destinationAddr"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="申请单状态">
            <el-select placeholder="请选择" v-model="searchApplication.status">
              <el-option v-for="item in appOptions" :label="item.label" :value="item.value"></el-option>
<!--              <el-option label="已发起" value="10"></el-option>-->
<!--              <el-option label="撤销" value="20"></el-option>-->
<!--              <el-option label="审核中" value="30"></el-option>-->
<!--              <el-option label="驳回" value="40"></el-option>-->
<!--              <el-option label="已通过" value="50"></el-option>-->
<!--              <el-option label="分配用车" value="60"></el-option>-->
<!--              <el-option label="工单结束" value="70"></el-option>-->
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="primary" @click="loadApplication">查询</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-card>
  <!-- 用车申请表格 -->
  <el-card style="margin:20px">
    <el-table :data="tableData">
      <el-table-column type="index" width="80" align="center" prop="id" label="编号"></el-table-column>
      <el-table-column align="center" prop="username" label="申请人"></el-table-column>
      <el-table-column align="center" prop="departureAddr" label="出发地"></el-table-column>
      <el-table-column align="center" prop="destinationAddr" label="目的地"></el-table-column>
      <el-table-column align="center" prop="reason" label="用车原因"></el-table-column>
      <el-table-column align="center" prop="auditUsernameList" label="审批人"></el-table-column>
      <el-table-column align="center" prop="startTime" label="使用开始时间"></el-table-column>
      <el-table-column align="center" prop="endTime" label="使用结束时间"></el-table-column>
      <el-table-column align="center" prop="status" label="申请单状态" :formatter="appStatusFormatter"></el-table-column>
      <el-table-column label="操作" width="120" align="center">
        <template #default="scope">
          <el-button type="primary" size="small" link
          :disabled="scope.row.status!=10" @click="cancel(scope.row.id)">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>

  </el-card>
</template>
<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";

//定义变量控制创建申请单弹窗是否出现
const addApplicationDialogVisible = ref(false);

//获取当前登录的用户信息
const user = ref(getUser());
//定义数组用来保存新增申请单时显示的审批人(含姓名)
const auditUserOpts = ref([]);
//定义数组用来保存新增申请单时,对应申请人的id,入库使用
const auditUserIdList = ref([]);
//定义加载审批人的方法
const loadAuditUser = ()=>{
  //准备当前申请人的上级id
  let parentId = user.value.parentId;
  //根据此上级用户id将当前登录人的直属领导先查出,再查出他的上上级
  axios.get(BASE_URL+'/v1/user/select/audit/'+parentId).then((response)=>{
    if(response.data.code==2000){
      auditUserOpts.value = response.data.data;
      //还需要遍历上方的审批人数组,依次取出每个审批人的用户id存入id数组中方便入库
      for(let i = 0;i<auditUserOpts.value.length;i++){
        auditUserIdList.value.push(auditUserOpts.value[i].id);
      }
    }
  })
}
onMounted(()=>{
  //因为当前登录人的直属领导与上上级是固定的,所以只需要在页面加载完毕时加载一次即可,无需重复请求
  loadAuditUser();
})

//定义数组用来保存申请单的起止时间
const times = ref([]);
//定义对象用来保存申请单的表单数据
const addForm = ref({
  departureAddr: '',//出发地
  destinationAddr: '',//目的地
  reason:'',//用车事由
  remark:''//备注
});
//定义方法发送新增申请单的请求
const addApplication = ()=>{
  if(fileList.value.length==0){
    ElMessage.warning('请上传图片!');
    return;
  }
  //准备给后端新增审请单的数据
  addForm.value.startTime = times.value[0];//申请开始时间
  addForm.value.endTime = times.value[1];//申请结束时间
  addForm.value.username = user.value.username;//申请人姓名
  addForm.value.userId = user.value.id;//申请人id
  addForm.value.imgUrl = fileList.value[0].response.data;//驾照图片路径
  addForm.value.auditUserIdList = auditUserIdList.value;//审批人id集合
  console.log(addForm.value);

  let data = qs.stringify(addForm.value);
  //给后端发新增请求
  axios.post(BASE_URL+'/v1/application/save',data).then((response)=>{
    if(response.data.code==2000){
      ElMessage.success('新增成功!');
      addApplicationDialogVisible.value = false;//关闭弹窗
      fileList.value = [];//清空图片上传组件
      times.value = [];//清空时间选择器组件
      addForm.value = {};//清空其他4个表单项
      //新增成功后需要重新加载申请数据
      loadApplication();
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
//处理弹窗关闭的方法
const handleClose = ()=>{
  if(confirm('是否要关闭本弹窗?')){
    addApplicationDialogVisible.value = false;//关闭弹窗
    fileList.value = [];//清空图片上传组件
    times.value = [];//清空时间选择器组件
    addForm.value = {};//清空其他4个表单项
  }
}

/**图片上传相关代码开始**/
const fileList = ref([]);
const dialogImageUrl = ref('');
const dialogVisible = ref(false);
//定义变量用来保存图片上传给哪个后端接口地址
const actionUrl = ref(BASE_URL + '/v1/file/upload');
//移除图片
const handleRemove = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
  //1.得到要删除的图片路径 uploadFile就是我们要删除的那个图片文件对象
  let imgUrl = uploadFile.response.data;
  //2.给后端发请求,删除图片
  axios.post(BASE_URL+'/v1/file/remove?imgUrl='+imgUrl)
      .then((response)=>{
        if (response.data.code==2000){
          ElMessage.success('删除成功!');
        }
      })
}
//图片上传后预览
const handlePictureCardPreview = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url
  dialogVisible.value = true;
  console.log(uploadFile);
  //console.log(fileList.value[0].response.data);
}
/**图片上传相关代码结束**/

//准备数组用来保存查出来的申请单表格数据
const tableData = ref([]);
//准备对象用来保存搜索条件
const searchApplication = ref({
  departureAddr: '',//出发地
  destinationAddr: '',//目的地
  status:''//申请单状态
});
//定义加载申请单的方法
const loadApplication = ()=>{
  let data = qs.stringify(searchApplication.value);
  axios.get(BASE_URL+'/v1/application/select?'+data).then((response)=>{
    if (response.data.code==2000){
      tableData.value = response.data.data;
    }else {
      ElMessage.error(response.data.msg);
    }
  })
}
onMounted(()=>{
  loadApplication();
})
//定义重置搜索条件的方法
const resetSearch = ()=>{
  searchApplication.value = {};
  loadApplication();
}

//定义数组用来保存申请单的状态选项
const appOptions = ref([]);
//一进入页面立即加载申请单状态字典项
onMounted(()=>{
  loadDictOptions(appOptions,'application_status');
})
//对申请单状态显示内容进行文字转换
const appStatusFormatter = (row,column,cellValue,index)=>{
  if(!appOptions.value){
    return cellValue;
  }
  for(let item of appOptions.value){
    if(item.value == cellValue){
      cellValue = item.label;
    }
  }
  return cellValue;
}

//撤销用车申请
const cancel = (id)=>{
  axios.post(BASE_URL+'/v1/application/cancel/'+id).then((response)=>{
    if(response.data.code==2000){
      ElMessage.success('撤销成功!');
      loadApplication();
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
</script>

<style scoped>
</style>