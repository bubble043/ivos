<!--用户管理页-->
<template>
  <div style="height:6vh;padding:10px 20px;background-color:#fff;">
    <span style="line-height:60px;font-size:20px;">用户管理</span>
    <el-button type="primary" @click="beforeAddUser"
               style="float: right;margin-top:10px;">新建用户</el-button>
  </div>
  <!-- 新建用户弹窗 -->
  <!-- :before-close="handleClose"在弹窗关闭之前调用此自定义函数
   这样避免点击弹窗外部也能将弹窗关闭的问题 -->
  <el-dialog
      :title="dialogTitle"
      style="width: 1000px;padding:40px;"
      v-model="dialogVisible"
      :before-close="handleClose"
  >
    <el-form label-width="80px" label-position="top">
      <el-row :gutter="10">
       <el-col :span="12">
         <el-form-item label="用户名">
           <el-input placeholder="请输入用户名" v-model="saveUserForm.username"
           :disabled="saveUserForm.id!=null"></el-input>
         </el-form-item>
       </el-col>
       <el-col :span="12">
         <el-form-item label="手机号">
           <el-input placeholder="请输入手机号" v-model="saveUserForm.phone"></el-input>
         </el-form-item>
       </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-form-item label="邮箱">
            <el-input placeholder="请输入邮箱" v-model="saveUserForm.email"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="年龄">
            <el-input placeholder="请输入年龄" v-model="saveUserForm.age"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
       <el-col :span="6">
         <el-form-item label="职级">
            <el-select placeholder="请选择" v-model="saveUserForm.level" @change="loadLeader">
<!--              <el-option label="职员" value="10"></el-option>-->
<!--              <el-option label="经理" value="20"></el-option>-->
<!--              <el-option label="总监" value="30"></el-option>-->
<!--              <el-option label="总裁" value="40"></el-option>-->
              <el-option v-for="item in levelOptions"
                         :label="item.label" :value="item.value"></el-option>
            </el-select>
         </el-form-item>
       </el-col>
        <el-col :span="6">
          <el-form-item label="直属领导">
            <el-select placeholder="请选择" v-model="saveUserForm.parentId">
              <el-option v-for="item in leaderOptions"
                         :label="item.username" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4">
          <el-form-item label="性别">
            <el-radio-group v-model="saveUserForm.gender">
              <el-radio border label="男" value="1" style="margin: 0;"></el-radio>
              <el-radio border label="女" value="0"></el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="用户状态">
            <el-radio-group v-model="saveUserForm.status">
              <el-radio border label="启用" value="1" style="margin: 0;"></el-radio>
              <el-radio border label="禁用" value="0"></el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="saveUser">保存</el-button>
    </template>
  </el-dialog>
  <!-- 用户搜索卡片 -->
  <el-card style="margin: 20px;height: 70px;">
    <el-form :inline="true">
      <el-form-item label="用户搜索">
        <el-input placeholder="输入用户名" style="width: 220px;"
                  v-model="searchUserForm.username"
                  @keydown.enter="loadUser"></el-input>
      </el-form-item>
      <el-form-item label="用户状态"style="width: 290px;">
        <el-select placeholder="请选择用户状态" v-model="searchUserForm.status" @change="loadUser">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch">重置</el-button>
        <el-button type="primary" @click="loadUser">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card>
  <!-- 用户数据表格卡片 -->
  <el-card style="margin: 20px;">
    <el-table :data="userArr">
      <el-table-column type="index" label="编号" width="80" align="center"></el-table-column>
      <el-table-column label="用户名" prop="username" align="center"></el-table-column>
      <el-table-column label="手机号" prop="phone" align="center"></el-table-column>
      <el-table-column label="加入时间" prop="createTime" align="center"></el-table-column>
      <el-table-column label="用户状态" prop="status" align="center">
        <template #default="scope">
          <el-switch active-value="1" inactive-value="0"
                     @change="changeStatus(scope.row.id,scope.row.status)"
                     v-model="scope.row.status"
                     :disabled="scope.row.level==40"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" :disabled="scope.row.level==40"
                     @click="resetPassword(scope.row.id)">密码重置</el-button>
          <el-button link type="primary" :disabled="scope.row.level==40"
                     @click="editUser(scope.row.id)">编辑</el-button>
          <el-button link type="primary" :disabled="scope.row.level==40"
                     @click="deleteUser(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import qs from "qs";
import {ElMessage} from "element-plus";
//定义变量控制弹窗是否出现
const dialogVisible = ref(false);
//定义变量用来控制弹窗的标题
const dialogTitle = ref('新建用户');

//定义数组来维护员工职级的选项
const levelOptions = ref([
  {label:'员工',value:'10'},
  {label:'经理',value:'20'},
  {label:'总监',value:'30'},
  {label:'总裁',value:'40'}
]);
//定义数组来维护员工直属领导的数据
const leaderOptions = ref([
  {username:'AA',id:'101'},
  {username:'BB',id:'102'}
]);

//定义对象用来保存表单中的员工数据
const saveUserForm = ref({
  username:'',
  phone:'',
  email:'',
  age:'',
  level:'',
  parentId:'',
  gender:'',
  status:''
});
/* 点击"新建用户"按钮，出弹窗+修改弹窗标题 */
const beforeAddUser = ()=>{
  dialogVisible.value = true;
  dialogTitle.value = '新增用户';
}
/* 点击弹窗中的“保存用户”按钮 => 新增+更新用户 */
const saveUser = () => {
  //console.log(saveUserForm.value);
  let data = qs.stringify(saveUserForm.value);
  console.log(data);
  //username=vv&phone=18877776666&email=vv%40193.com&age=50&level=20&parentId=102&gender=1&status=1
  axios.post(BASE_URL+'/v1/user/save',data)
      .then((response)=>{
        if(response.data.code == 2000){
          ElMessage.success('保存成功!');
          //用户确认关闭后,关闭弹窗
          dialogVisible.value = false;
          //清空已输入的表单数据
          saveUserForm.value = {};
          //重新加载用户数据
          loadUser();
        }else{
          ElMessage.error(response.data.msg);
        }
      })
}

//处理弹窗关闭的方法
const handleClose = ()=>{
  if(confirm('确认要关闭本窗口吗?')){
    //用户确认关闭后,关闭弹窗
    dialogVisible.value = false;
    //清空已输入的表单数据
    saveUserForm.value = {};
  }
}

//定义对象来保存用户查询的条件
const searchUserForm = ref({username:'',status:''});
//定义对象保存查询到的用户表格数据
const userArr = ref([]);
//定义加载用户列表的方法
const loadUser = ()=>{
  let data = qs.stringify(searchUserForm.value);
  //如果有查询条件,就根据条件查,没有条件默认查全部数据
  axios.get(BASE_URL+'/v1/user/select?'+data)
      .then((response)=>{//回调函数,处理响应数据
        if(response.data.code==2000){//后端返回的状态码是2000说明成功了
          userArr.value = response.data.data;//把请求回来的数据装到绑定表格的数组中
          console.log(userArr.value);
        }else{
          ElMessage.error(response.data.msg);
        }
      })
}

//页面一加载完毕,立即执行
onMounted(()=>{
  loadUser();
})

//重置搜索
const resetSearch = ()=>{
  //清空之前的搜索内容
  searchUserForm.value = {};
  //重新加载所有的用户数据
  loadUser();
}

//定义方法获取直属领导数据
const loadLeader = ()=>{
  //优化:如果已经勾选了直属领导,再想重新选择职级的话,需要将之前的领导数组与已选的直属领导都清空!
  leaderOptions.value = [];
  saveUserForm.value.parentId = '';
  //拿到当前用户选择的职级,直属领导的职级为当前用户的职级+10,注意转为Number类型
  let level = parseInt(saveUserForm.value.level)+10;
  //给后端发请求,获取直属领导有哪些人
  axios.get(BASE_URL+'/v1/user/select?level='+level)
      .then((response)=>{
        if(response.data.code==2000){
          leaderOptions.value = response.data.data;
        }else{
          ElMessage.error(response.data.msg);
        }
      })
}

//定义重置密码的方法 restful风格
const resetPassword = (userId)=>{
  axios.post(BASE_URL+'/v1/user/update/password/'+userId)
      .then((response)=>{
        if(response.data.code==2000){
          ElMessage.success('密码重置成功!');
        }else{
          ElMessage.error(response.data.msg);
        }
      })
}

/* 点击用户表格的"编辑"按钮，出弹窗+修改弹窗标题 */
//定义编辑员工的方法
const editUser = (id)=>{
  //让弹窗出现
  dialogVisible.value = true;
  //修改弹窗的标题
  dialogTitle.value = '编辑员工';
  //根据id查询用户信息并回显在弹窗的表单中
  axios.get(BASE_URL+'/v1/user/select?id='+id).then((response)=>{
    if(response.data.code==2000){
      //注意:后端返回的是List<UserVO>,所以查一条数据的话拿的是集合的第一个元素data[0]
      saveUserForm.value = response.data.data[0];
      //获取可选直属领导数据
      let level = parseInt(saveUserForm.value.level)+10;
      //给后端发请求,获取直属领导有哪些人
      axios.get(BASE_URL+'/v1/user/select?level='+level)
          .then((response)=>{
            if(response.data.code==2000){
              leaderOptions.value = response.data.data;
            }else{
              ElMessage.error(response.data.msg);
            }
          })
    }else{
      ElMessage.error(response.data.msg);
    }
  })
}

//修改员工状态
const changeStatus = (userId,status)=>{
  axios.post(BASE_URL+'/v1/user/update/status/'+userId+'/'+status)
      .then((response)=>{
        if(response.data.code==2000){
          ElMessage.success('状态修改成功!');
        }else{
          ElMessage.error(response.data.msg);
        }
      })
}

//创建删除员工的方法
const deleteUser = (userId)=>{
  if(confirm('您确认要删除该员工吗?')){
    axios.post(BASE_URL+'/v1/user/delete/'+userId).then((response)=>{
      if(response.data.code==2000){
        ElMessage.success('删除成功!');
        //删除成功后,需要重新加载用户列表
        loadUser();
      }else{
        ElMessage.error(response.data.msg);
      }
    })
  }
}
</script>

<style scoped>

</style>