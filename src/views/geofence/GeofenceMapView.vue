<!--围栏地图页-->
<template>
  <div style="height: 6vh;background-color:#fff;padding:10px 20px;">
    <span style="line-height:60px;font-size:20px;">新建围栏</span>
    <el-button type="primary" style="float:right;margin-top:13px;"
               @click="router.push('/geofence')">返回</el-button>
  </div>
  <!-- 第6步:创建地图容器元素 -->
  <div id="mapContainer" style="width:100%;height:84vh;"></div>

  <!-- 新增电子围栏弹窗 -->
  <el-dialog title="围栏信息" v-model="dialogVisible" :before-close="closeDialog">
      <el-form label-width="80px">
        <el-form-item label="围栏名称">
          <el-input placeholder="请输入围栏名称" v-model="geofence.name"></el-input>
        </el-form-item>
      </el-form>
    <template #footer>
      <el-button @click="closeDialog">取消</el-button>
      <el-button type="primary" @click="saveGeofence">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import router from "@/router";
import {nextTick, onMounted, ref} from "vue";
import { DrawScene, DrawControl,OperateEventType } from 'bmap-draw';
import {ElMessage} from "element-plus";
import qs from "qs";
import axios from "axios";

//定义变量控制弹窗是否出现
const dialogVisible = ref(false);

//6.准备对象用来存储围栏的信息
const geofence = ref({
  name:'',//围栏名称
  position: {}//围栏信息
});

//声明变量用于保存地图对象
var map;

onMounted(()=>{
  //表示DOM更新之后再执行,因为我们需要确保上方的div已创建后再渲染地图
  nextTick(()=>{
    //第7步:创建地图实例,注意:不要导入!忽略提示!
    map = new BMapGL.Map("mapContainer");
    //第8步:设置地图展示的中心点坐标,此处是天安门的经纬度
    var point = new BMapGL.Point(116.4074, 39.9024);
    //第9步:设置地图显示的中心点与地图缩放级别
    map.centerAndZoom(point, 15);

    // 添加鼠标绘制控件
    //1.创建绘制场景类,这是其他绘制控件使用的基类,注意:DrawScene需要导入!
    const scene = new DrawScene(map);
    //2.创建绘制控件类,并将上一句创建好的绘制场景作为构造参数传入,注意:DrawControl也需要导入!
    const drawContrl = new DrawControl(scene, {
      //3.绘制控件配置项:
      //3.1 不显示对应图标的用途提示
      enableTips: false,
      //3.2 设置控件显示的位置(右上角)
      anchor: BMAP_ANCHOR_TOP_RIGHT,
      //3.3 设置绘制控制显示哪些绘制项 circle圆形 rectangle矩形
      drawingItems: [
        'circle',
        'rectangle'
      ]
    });

    //5.监听绘制完成事件,返回触发完成事件(打对钩)的电子围栏对象
    scene.addEventListener(OperateEventType.COMPLETE,(event)=>{
      /*围栏绘制完成,点"√"时,显示输入围栏名称的弹窗!*/
      dialogVisible.value = true;
      console.log(event.target);
      //7.获取围栏的数据
      let overlay = event.target.overlay;
      //8.判断当前围栏是圆形还是矩形
      //instanceof判断overlay是否为右边BMapGL.Circle类的实例
      if(overlay instanceof BMapGL.Circle){//是圆形
        //设置围栏类型为圆形
        geofence.value.position.type = 'circle';
        //设置圆形围栏的半径值
        geofence.value.position.radius = overlay.getRadius();
        //设置圆心的经度和纬度
        geofence.value.position.latitude = overlay.getCenter().lat;
        geofence.value.position.longitude = overlay.getCenter().lng;
        console.log(geofence.value);
        console.log(geofence.value.position);
      }else{//是矩形
        //需要准备数组用来保存矩形的四个顶点
        let recPoints = [];
        //依次获取围栏的顶点坐标
        for(let i = 0; i<4 ;i++){
          recPoints.push(overlay.points[i].latLng.lng+'-'+overlay.points[i].latLng.lat)
        }
        //设置围栏类型为矩形
        geofence.value.position.type = 'rectangle';
        //设置围栏的顶点坐标字符串，将四个顶点用逗号拼接成一个字符串
        geofence.value.position.recPoints = recPoints.join(',');
        console.log(geofence.value);
        console.log(geofence.value.position);
      }
    })
    //4.将绘制控件添加到地图上
    map.addControl(drawContrl);
  })
})

//定义处理关闭弹窗的方法
const closeDialog = ()=>{
  if(confirm('是否确认关闭弹窗?')){
    //关闭弹窗
    dialogVisible.value = false;
    //清除当前地图上不用的围栏遮盖物
    map.clearOverlays();
  }
}
//定义保存围栏的方法
const saveGeofence = ()=>{
  //要求围栏名称不能为空,否则不能保存围栏!
  if(!geofence.value.name || geofence.value.name.trim().length==0){
    ElMessage.error('围栏名称不能为空!');
    return;
  }
  //因为围栏位置信息position是一个对象,所以需要先转为字符串
  geofence.value.position = JSON.stringify(geofence.value.position);
  //继续处理geofence,转为qs字符串给后端发新增请求
  let data = qs.stringify(geofence.value);
  console.log(data);
  //给后端发新增围栏的请求
  axios.post(BASE_URL+'/v1/geofence/save',data).then((response)=>{
    if(response.data.code==2000){
      ElMessage.success('保存成功!');
      //新增成功后,需要关闭弹窗
      dialogVisible.value = false;
      //跳转至电子围栏管理页,在围栏表中能看到新增的这条围栏数据
      router.push('/geofence');
    }else{
      ElMessage.error(response.data.msg);
    }
  })

}
</script>

<style scoped>

</style>