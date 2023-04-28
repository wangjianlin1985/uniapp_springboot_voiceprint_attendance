此插件为ucharts的二次封装，旨在简化ucharts的使用过程！

目录结构为：
components/u-charts/u-charts.vue //图表组件文件
components/u-charts/js/chartsUtil.js //为图表初始化和通用函数的二次封装文件
components/u-charts/js/u-charts.js|u-charts.min.js  //此文件为ucharts的原文件，请根据ucharts的版本自行升级。



使用说明：
···
<template>
<view>
	<uCharts :show="canvas" :canvasId="arr[0].id" :chartType="arr[0].chartType" :extraType="arr[0].extraType" :cWidth="cWidth" :cHeight="cHeight" :opts="arr[0].opts" :ref="arr[0].id"/>
</view>
</template>
```

参数说明：
show-显示或隐藏图表
canvasId-画布id
chartType-图表基本类型
exraType-图表扩展类型
opts-图表横轴和纵轴等主要数据
cWidth-图表宽度
cHeight-图表高度
ref-$ref.xxx组件函数引用声明
```
<script>
	import uCharts from '@/components/u-charts/u-charts.vue';
	export default {
		components: { uCharts },
		data() {
			return {
				canvas: true,
				cWidth:uni.upx2px(750),
				cHeight:uni.upx2px(400),
				arr: [{
						opts: ColumnMeter,
						chartType: "column",
						extraType: "meter",
						id: "c2"
					}]
			}
		},
	}
</script>
···

#demo.vue
```
<template>
	<view class="container">
		<block v-for="(item, index) in arr" :key="index">
			<view v-if="item.group" class="title uni-text-gray fl-row">
				<view class="ml10 mt10 mb10 i-cube"></view><text class="ml30">{{item.group}}</text>
			</view>
			<view v-if="item.btn" class="bgwh fl-row p10">
				<view :style="{width: (100-item.btn.length*20)+'%',}"></view>
				<block v-for="(val, key) in item.btn" :key="key" >
					<button :class="[key%2==0?'s-btn s-btn-hover':'s-btn1 s-btn1-hover']" @tap="change(index,val.type,val.etype)">{{val.title}}</button>
				</block>
			</view>
			<view class="bgwh qiun-title-bar tc" >
				<view class="f30 g6">{{item.title}}</view>
			</view>
			<view class="bgwh">
				<uCharts :scroll="item.opts.enableScroll" :show="canvas" :canvasId="item.id" :chartType="item.chartType" :extraType="item.extraType" :cWidth="cWidth" :cHeight="cHeight" :opts="item.opts" :ref="item.id"/>
			</view>
		</block>
	</view>
</template>

<script>
	import uCharts from '@/components/u-charts/u-charts.vue';
	import crt from '@/components/u-charts/js/chartsUtil.js';

	export default {
		data() {
			return {
				canvas: true,
				cWidth:'',
				cHeight:'',
				arr: []
			}
		},
		components: { uCharts },
		onLoad(e) {
			this.init()
		},
		onReady() {
		},
		methods: {
			init(){
				this.cWidth=uni.upx2px(750);
				this.cHeight=uni.upx2px(400);
				this.getServerData();
				this.query()
			},
			query(){
				// this.findFamilyQYByList()
			},
			jump(url, title='',to=3) {
				// this.$api.jump(url,title,to)
			},
			change(idx,type,etype){
				this.$refs[this.arr[idx].id][0].changeData(this.arr[idx].id,this.arr[idx].opts,type,etype)
			},
			async findFamilyQYByList() {
				var data={
					loading: true,
					url:this.HostURL + this.serviceSRV,
					data:{
						p_service: 'familySjService',
						p_method: 'findFamilyQYByTotalList',
						startTime: this.startTime,
						endTime: this.endTime,
						regcodes: this.divisionCode[0],
						pageNo: 1, // 1-第几页
						pageSize: 10, // 10-每页记录条数
						token: this.token,
					}
				}
				var res=await this.$api.get(data)
				var result=this.$api.ErrTip(res)
				if (result&&result.list) {
					var Column={type:'column',categories:[],series:[{name:'户数',data:[]}]}//柱状图
					result.list.forEach((e)=>{
						if(e.name&&e.cnt){
							Column.categories.push(e.name);
							Column.series[0].data.push(e.cnt);
						}
					})
					this.$refs[this.arr[2].id][0].changeData(this.arr[2].id,Column,'column','group')
				}
			},
			async getServerData() {
				var sample=crt.getSampleData()
				// var data={loading: true,url:'https://www.ucharts.cn/data.json'}
				// var res=await this.$api.get(data)
				// var result=this.$api.ErrTip(res)
				// if (result&&result.data) {
					var LineA = { enableScroll:false, unit: '套' }
					LineA.categories = sample.LineA.categories//result.data.LineA.categories
					LineA.series = sample.LineA.series//result.data.LineA.series
					var LineB={rotate:false, enableScroll:false, unit: '套' }
					// LineB.extra={line:{width:10}}
					LineB.categories = sample.LineB.categories//result.data.LineB.categories
					LineB.series = sample.LineB.series//result.data.LineB.series
					var Column={ enableScroll:false, unit: '套' }
					Column.categories = sample.ColumnB.categories//result.data.ColumnB.categories
					Column.series = sample.ColumnB.series//result.data.ColumnB.series
					var ColumnMeter = { enableScroll:false, unit: '' }
					ColumnMeter.categories = sample.ColumnMeter.categories//result.data.ColumnMeter.categories
					ColumnMeter.series = sample.ColumnMeter.series//result.data.ColumnMeter.series
					var serverData = [{
						group:'商品房预（现）售供应情况分析',
						title:'兵团商品房预（现）售供应月度情况',
						btn:[{title:'折线图',type:'line',etype:'straight'},{title:'柱状图',type:'column',etype:'group'},{title:'区域图',type:'area',etype:'curve'},],
						opts: LineA,
						chartType: "line",
						extraType: "default",
						id: "c0"
					}, {
						title:'各师商品房预（现）售供应情况',
						btn:[{title:'折线图',type:'line',etype:'straight'},{title:'柱状图',type:'column',etype:'group'},{title:'区域图',type:'area',etype:'curve'},],
						opts: Column,
						chartType: "column",
						extraType: "group",
						id: "c1"
					}, {
						group:'商品房[住宅]预（现）售供应情况分析',
						title:'兵团商品房预（现）售供应月度情况',
						opts: ColumnMeter,
						chartType: "column",
						extraType: "meter",
						id: "c2"
					}, {
						title:'各师商品房预（现）售供应情况',
						opts: LineB,
						chartType: "line",
						extraType: "curve",
						id: "c3"
					}, {
						title:'兵团商品房预（现）售供应月度情况',
						btn:[{title:'分组图',type:'column',etype:'group'},{title:'堆叠图',type:'column',etype:'stack'},{title:'温度计图',type:'column',etype:'meter'}],
						opts: Column,
						chartType: "column",
						extraType: "stack",
						id: "c4"
					}, {
						group:'商品房预（现）售供应类别',
						title:'兵团商品房预（现）售供应月度情况',
						btn:[{title:'饼状图',type:'pie'},{title:'圆环图',type:'ring'}],
						opts: sample.Pie,//result.data.Pie,
						chartType: "pie",
						id: "c5"
					}, {
						title:'兵团商品房预（现）售供应月度情况',
						btn:[{title:'饼状图',type:'pie'},{title:'圆环图',type:'ring'}],
						opts: sample.Ring,//result.data.Ring,
						chartType: "ring",
						id: "c6"
					}, {
						title:'兵团商品房预（现）售供应月度情况',
						opts: sample.Gauge,//result.data.Gauge,
						chartType: "gauge",
						id: "c7"
					}, {
						title:'兵团商品房预（现）售供应月度情况',
						btn:[{title:'面积图',type:'rose',etype:'area'},{title:'半径图',type:'rose',etype:'radius'},],
						opts: sample.Pie,//result.data.Pie,
						chartType: "rose",
						extraType: "radius",
						id: "c8"
					}];
					this.arr = serverData;
				// 	return
				// }
				// this.$api.tip("网络错误，小程序端请检查合法域名");
			},
		}
	}
</script>

<style>
	page {
		display: block;
		height: 100%;
		background-color: #efeff4;
	}

	.fl-row { display: flex; flex-direction: row; }
	.bgwh { background-color: #FFFFFF; }/*白色*/
	.g6 { color: #666; }/*浅黑*/
	.tc { text-align: center; }
	.f30 { font-size: 30upx; }
	.p10{ padding: 10upx; }
	.mt10{margin-top: 10upx;}
	.mb10{margin-bottom: 10upx;}
	.ml10{margin-left: 10upx;}
	.mr10{margin-right: 10upx;}
	
	.qiun-title-bar{
		width:96%; 
		padding:10upx 2%; 
		flex-wrap:nowrap;
	}
	
	
	.container {
		box-sizing: border-box;
		height: 100%;
		background-color: #efeff4;
	}

	.content {
		width: 750upx;
		height: calc(100% - 100upx);
		background-color: #efeff4;
	}
	.title {
		line-height: 80upx;
		background-color: #eee;
		text-indent: 20upx;
		font-size: 30upx;
		color: #000000;
		letter-spacing: 1px;
	}
	.i-cube {
		width: 12upx;
		height: 60upx;
		background-color: #007AFF;
		margin-right: 20upx;
	}
	
	.s-btn {
		font-size: 28upx;
		background-color: transparent;
		color: #f6a121;
		line-height: 1.8;
		padding-left: 20upx;
		padding-right: 20upx;
	}
	.s-btn:after {
		border: 2upx solid #8799A3;
	}
	.s-btn-hover {
		background-color: #f6a121;
		color: #FFFFFF;
	}
	
	.s-btn1 {
		font-size: 28upx;
		background-color: transparent;
		color: #f6a121;
		line-height: 1.8;
		padding-left: 20upx;
		padding-right: 20upx;
	}
	.s-btn1:after {
		border: 2upx solid #8799A3;
	}
	.s-btn1-hover {
		background-color: #007AFF;
		color: #FFFFFF;
	}
</style>

```