<!--申请审批页-->
<template>
  <!-- 顶部条 -->
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">审批列表</span>
  </div>
  <!-- 审批搜索卡片 -->
  <el-card style="margin:20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="用车人">
        <el-input placeholder="请输入用车人" style="width:220px;"
        @keydown.enter.prevent="loadAudit" v-model="search.username"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadAudit">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>

  <!-- 审批单主体 -->
  <el-card style="margin:20px;">
    <!--  审批状态项  -->
    <el-radio-group style="margin-bottom: 15px;" v-model="type" @change="loadAudit">
      <el-radio-button value="10" size="large">待我审核</el-radio-button>
      <el-radio-button value="20" size="large">待他人审核</el-radio-button>
      <el-radio-button value="30" size="large">已审核</el-radio-button>
      <el-radio-button value="40" size="large">驳回</el-radio-button>
    </el-radio-group>
    <!--  审批列表  -->
    <el-table :data="auditArr">
      <el-table-column label="编号" prop="id" align="center" width="55" type="index"></el-table-column>
      <el-table-column label="用车人" prop="username" align="center" width="110"></el-table-column>
      <el-table-column label="开始时间" prop="startTime"  align="center"></el-table-column>
      <el-table-column label="结束时间" prop="endTime"  align="center"></el-table-column>
      <el-table-column label="用车事由" prop="reason"  align="center"></el-table-column>
      <el-table-column label="审批人" prop="auditUsernameList"  align="center"></el-table-column>
      <el-table-column label="出发地" prop="departureAddr"  align="center"></el-table-column>
      <el-table-column label="目的地" prop="destinationAddr"  align="center"></el-table-column>
      <el-table-column label="操作" width="100" align="center" v-if="type==10||type==40" :key="audit">
        <template #default="scope">
          <!-- 审批10：在待我审核页签下显示，需要当前登录用户进行审批 -->
          <el-button type="primary" link v-if="type==10" @click="auditing(scope.row.id)">审批</el-button>
          <!-- 查看40：在驳回页签下显示，可以查看已驳回申请的驳回原因 -->
          <el-button type="primary" link v-if="type==40" @click="auditing(scope.row.id)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <!-- 审批/查看弹窗 -->
  <el-dialog :title="dialogTitle" v-model="auditDialogVisible">
    <el-descriptions direction="horizontal" :column="2" border>
      <el-descriptions-item label="用车人">{{auditDialogData.username}}</el-descriptions-item>
      <el-descriptions-item label="用车事由">{{auditDialogData.reason}}</el-descriptions-item>
      <el-descriptions-item label="使用开始时间">{{auditDialogData.startTime}}</el-descriptions-item>
      <el-descriptions-item label="使用结束时间">{{auditDialogData.endTime}}</el-descriptions-item>
      <el-descriptions-item label="车辆出发地">{{auditDialogData.departureAddr}}</el-descriptions-item>
      <el-descriptions-item label="车辆目的地">{{auditDialogData.destinationAddr}}</el-descriptions-item>
      <el-descriptions-item label="驾照图片">
        <img :src="BASE_URL+auditDialogData.imgUrl" style="width:150px;">
      </el-descriptions-item>
      <el-descriptions-item label="备注">{{auditDialogData.remark}}</el-descriptions-item>
      <!--  驳回原因要在点击驳回才显示 -->
      <el-descriptions-item label="驳回原因" v-if="auditDialogData.auditStatus==40">
        {{auditDialogData.rejectReason}}
      </el-descriptions-item>
    </el-descriptions>
    <!-- 查看驳回原因时,不需要显示下面的这3个操作按钮 "待我审核"时才显示  -->
    <template #footer v-if="auditDialogData.auditStatus==10">
      <el-button @click="auditDialogVisible=false">取消</el-button>
      <el-button type="primary" plain @click="rejectInnerDialogVisible=true">驳回</el-button>
      <el-button type="primary" @click="auditPass">通过</el-button>
    </template>
  </el-dialog>

  <!-- 驳回原因弹窗 -->
  <el-dialog title="驳回 查看" v-model="rejectInnerDialogVisible" style="margin-top: 37vh;" :before-close="rejectConfirm">
    <el-descriptions direction="horizontal" border>
      <el-descriptions-item label="驳回原因">
        <el-input placeholder="请输入驳回原因" v-model="rejectReason"></el-input>
      </el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <el-button type="primary" plain @click="rejectConfirm">取消</el-button>
      <el-button type="primary" @click="auditReject">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import qs from "qs";

//控制审批弹窗标题
const dialogTitle = ref("待审批详情");
//控制审批弹窗是否显示
const auditDialogVisible = ref(false);
//控制驳回原因弹窗是否显示
const rejectInnerDialogVisible = ref(false);

//定义数组用来保存审批表格的数据
const auditArr = ref([]);
//定义对象用了保存查询条件
const search = ref({
  username:'',//搜索卡片中的用车人姓名
  auditUserId:'',//预先准备 接下来要查当前登录人也就是审批人的审批数据
  auditStatus:''//预先准备 接下来要查当前登录用户对应的审批状态下的审批数据
})
//获取当前登录人的用户数据
const user = ref(getUser());
//定义变量用来保存审批单状态单选组的值
const type = ref(10);//默认展示的是待我审批的数据
//定义加载审批单的方法
const loadAudit = ()=>{
  search.value.auditUserId = user.value.id;
  search.value.auditStatus = type.value;
  let data = qs.stringify(search.value);
  axios.get(BASE_URL+'/v1/audit/select?'+data).then((response)=>{
    if(response.data.code==2000){
      auditArr.value = response.data.data;
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
onMounted(()=>{
  loadAudit();
})
//定义重置搜索的方法
const resetSearch = ()=>{
  //清空用户看得见的用车人姓名即可
  search.value.username = '';
  loadAudit();
}
//定义对象用来保存审批单弹窗数据
const auditDialogData = ref({
  username:'',//用车人姓名
  reason:'',//用车事由
  startTime:'',//用车开始时间
  endTime:'',//用车结束时间
  departureAddr:'',//出发地
  destinationAddr:'',//目的地
  imgUrl:'',//驾照图片
  remark:''//备注
});

//点击审批操作按钮，根据传过来的审批单id,请求当前审批单的详细数据
const auditing = (id)=>{
  //显示审批详细信息弹窗
  auditDialogVisible.value = true;
  //发请求查询数据
  axios.get(BASE_URL+'/v1/audit/select?id='+id).then((response)=>{
    if(response.data.code==2000){
      auditDialogData.value = response.data.data[0];
      //根据请求回来的审批单状态设置弹窗标题
      if(auditDialogData.value.auditStatus==10){
        dialogTitle.value = '待审批详情';
      }else{
        dialogTitle.value = '驳回详情';
      }
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//审批单通过处理
const auditPass = ()=>{
  //将当前审批单的状态改为“已审核”
  auditDialogData.value.auditStatus = 30;
  let data = qs.stringify(auditDialogData.value);
  axios.post(BASE_URL+'/v1/audit/update',data).then((response)=>{
    if (response.data.code==2000){
      ElMessage.success('审批已通过!');
      auditDialogVisible.value = false;//关闭弹窗
      auditDialogData.value = {};//清空刚刚的审批单数据
      loadAudit();//重新加载审批数据
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//定义变量保存驳回的原因
const rejectReason = ref('');
//定义方法处理驳回操作
const auditReject = ()=>{
  if(!rejectReason.value || rejectReason.value.trim()==''){
    ElMessage.error('驳回原因不能为空!');
    return;
  }
  //确认驳回原因不为空后,当用户点击"确认"时,关闭两层弹窗
  rejectInnerDialogVisible.value = false;
  auditDialogVisible.value = false;
  //准备发请求的数据
  auditDialogData.value.auditStatus = 40;//审批单状态改为"驳回"
  auditDialogData.value.rejectReason = rejectReason.value;//设置驳回原因
  let data = qs.stringify(auditDialogData.value);
  axios.post(BASE_URL+'/v1/audit/update',data).then((response)=>{
    if (response.data.code==2000){
      ElMessage.success('驳回完成!');
      auditDialogData.value = {};//清空上面的审批数据
      rejectReason.value = '';//清空驳回原因
      loadAudit();//重新加载审批数据
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}
//定义方法处理驳回原因弹窗关闭操作
const rejectConfirm = ()=>{
  if(confirm('确认关闭本窗口吗?')){
    rejectInnerDialogVisible.value = false;//关闭输入驳回原因的内层弹窗
    rejectReason.value = '';//清空输入的驳回原因
  }
}
</script>

<style>

</style>