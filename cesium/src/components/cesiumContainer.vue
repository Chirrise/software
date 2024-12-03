<template>
<div id="cesiumContainer"></div>
</template>

<script>
export default{

  mounted(){
    //Ion的token，在这里布置后，在后面就可以调用3D tiles了
    Cesium.Ion.defaultAccessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI3M2ZiNzZjMy1lNjE3LTQ0ZmYtYTY3Zi01Njg3ODExZDMwM2MiLCJpZCI6MjU4ODEyLCJpYXQiOjE3MzI4NDg2MDZ9.rGeHJBi2Og0Ds_5qZCzzdvLjdNoP7IbgWSRcQCELhWI";  
    
    const viewer = new Cesium.Viewer("cesiumContainer", {
      animation: false, // 动画控制左下角的小圆圈，默认true
      baseLayerPicker: true, // 地图切换控件(底图以及地形图)是否显示,默认显示true
      scene3DOnly: true, // 设置为true，则所有几何图形以3D模式绘制以节约GPU资源
      fullscreenButton: true, // 全屏按钮,默认显示true
      geocoder: true, // 地名查找,默认true
      timeline: true, // 时间线,默认true
      vrButton: false, // 双屏模式,默认不显示false
      homeButton: false, // 主页按钮，默认true
      infoBox: true, // 点击要素之后显示的信息,默认true
      selectionIndicator: false, // 选中元素显示,默认true
      navigationHelpButton: false, // 导航帮助说明,默认true
      navigationInstructionsInitiallyVisible: false,
      automaticallyTrackDataSourceClocks: false, // 自动追踪最近添加的数据源的时钟设置,默认true
      sceneModePicker: false, // 是否显示地图2D2.5D3D模式
      terrain: Cesium.Terrain.fromWorldTerrain({requestVertexNormals: true}), // 显示地形
      terrainExaggeration: 1.0,//地形放大比例
    })

    // 隐藏 Cesium 的版权标志
    viewer._cesiumWidget._creditContainer.style.display = "true";

/* ------------------------------------时间轴样式-------------------------------------*/

    // 自定义时间轴标签格式
    viewer.timeline.makeLabel = function (datetime) {
      const julianDT = new Cesium.JulianDate();
      Cesium.JulianDate.addHours(datetime, 8, julianDT); // 时间加 8 小时 (转换为北京时间)
      const date = Cesium.JulianDate.toGregorianDate(julianDT);
      return `${date.year}/${date.month}/${date.day} ${date.hour}:00`;
    };

    // 设置时间轴初始范围为当前时间前后 24 小时
    const now = Cesium.JulianDate.fromDate(new Date());
    viewer.timeline.zoomTo(
      Cesium.JulianDate.addHours(now, -24, new Cesium.JulianDate()),
      Cesium.JulianDate.addHours(now, 24, new Cesium.JulianDate())
    );

    /* ------------------------------------加载天地图-------------------------------------*/
    // 叠加天地图影像服务
    // viewer.imageryLayers.addImageryProvider(newCesium.UrlTemplateImageryProvider({
      // url: tdtUrl + 'DataServer?T=img_w&x={x}&y={y}&l={z}&tk='+ token,
      // subdomains: subdomains,
      // tilingScheme : newCesium.WebMercatorTilingScheme(),
      // maximumLevel : 18
    // }));

    // 将三维球定位到中国 //会与导入的3D tile冲突，使用导入的时候要关掉地位
    // viewer.camera.flyTo({
    //   destination: Cesium.Cartesian3.fromDegrees(103.84, 31.15, 17850000),
    // });

    /* -----------------------------------渲染设置----------------------------------------*/
    viewer.scene.globe.depthTestAgainstTerrain = true // 开启地形深度测试
    viewer.scene.globe.showGroundAtmosphere = true // 开启大气
    viewer.scene.globe.enableLighting = true // 开启光照
    viewer.scene.skyAtmosphere.show = true // 大气
    viewer.scene.screenSpaceCameraController.enableCollisionDetection = true // 视角不可穿透
    viewer.scene.highDynamicRange = true // 开启HDR


    // 阴影
    viewer.shadows = true // 开启阴影
    viewer.shadowMap.softShadows = true // 软阴影
    viewer.shadowMap.darkness = 0.2 // 阴影颜色
    viewer.shadowMap.cascadesEnabled = true // 启用级联阴影
    viewer.shadowMap.numberOfCascades = 4 // 级联阴影层级
    viewer.shadowMap.maximumDistance = 50000 // 最大阴影距离
    viewer.shadowMap.size = 8192 // 阴影贴图分辨率
    viewer.shadowMap.normalOffset = true // 启用法线偏移
    viewer.shadowMap.fadingEnabled = true // 启用阴影渐变
    // 抗锯齿
    if(Cesium.FeatureDetection.supportsImageRenderingPixelated()) {
    viewer.resolutionScale = window.devicePixelRatio
    }
    viewer.scene.fxaa = true //开启快速近似抗锯齿
    viewer.scene.postProcessStages.fxaa.enabled = true // 后处理开启抗锯齿


    window.viewer = viewer

  },
  methods: {
    async asyncloadscence() {
      // 异步加载场景
      try {
        // 从 Cesium Ion 平台加载 3D Tiles
        const tileset = await Cesium.Cesium3DTileset.fromIonAssetId(75343);

        // 设置最大屏幕空间误差（提高性能）
        tileset.maximumScreenSpaceError = 16;

        // 应用模型矩阵（设置位置）
        const translation = new Cesium.Cartesian3(0, 0, 0);
        tileset.modelMatrix = Cesium.Matrix4.fromTranslation(translation);

        // 将 Tileset 添加到场景中
        this.viewer.scene.primitives.add(tileset);

        // 聚焦到模型的位置，与定位到中国冲突
        this.viewer.zoomTo(tileset);
      } catch (error) {
        // 错误处理
        alert("加载模型时出错：" + error.message);
      }
    },
  },
};
</script>
  

<style>
#cesimContainer{
  width: 100%;
  height: 100%;
}
</style>