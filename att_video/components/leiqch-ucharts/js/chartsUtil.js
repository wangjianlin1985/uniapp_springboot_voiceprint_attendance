// import uCharts from './u-charts.min.js'
import uCharts from './u-charts.js'

let chartsUtil = {
	getSampleData(){
		/*下面是服务器返回的数据格式，现已改成从服务器获取数据，以供有些朋友不知道怎么从后台获取数据后调用*/
		var Data={
			Column:{categories:['2012', '2013', '2014', '2015', '2016', '2017'],series:[{name: '成交量1',data:[15, {value:20,color:'#f04864'},45, 37, 43, 34]},{name: '成交量2',data:[30, {value:40,color:'#facc14'}, 25, 14, 34, 18]}]},
			ColumnB:{categories:['2013', '2014', '2015', '2016', '2017', '2018'],series:[{name: '新成交量3',data:[35, 36, 31, 33, 13, 34]},{name: '新成交量4',data:[18, 27, 21, 34, 14, 38]}]},
			LineA:{categories:['2012', '2013', '2014', '2015', '2016', '2017'],series:[{name: '成交量A',data:[35, 20, 25, 37, 4, 20]},{name: '成交量B',data:[70, 40, 65, 100, 44, 68]},{name: '成交量C',data:[100, 80, 95, 150, 112, 132]}]},
			LineB:{categories:['2012', '2013', '2014', '2015', '2016', '2017'],series:[{name: '成交量A',data:[35, 20, 25, 37, 4, 20]},{name: '成交量B',data:[70, 40, 65, 100, 44, 68]},{name: '成交量C',data:[100, 80, 95, 150, 112, 132]}]},
			LineC:{categories:['一师', '二师', '三师', '四师', '五师', '六师','七师', '八师', '九师', '十师', '十一师', '十二师', '十三师', '十四师'],series:[{name: '完成度',data:[35, 20, 25, 37, 14, 20,28,35, 20, 25, 37, 24, 20,28,]},]},
			Area:{categories:['2012', '2013', '2014', '2015', '2016', '2017'],series:[{name: '成交量A',data:[100, 80, 95, 150, 112, 132],color:'#facc14'},{name: '成交量B',data:[70, 40, 65, 100, 44, 68],color:'#2fc25b'},{name: '成交量C',data:[35, 20, 25, 37, 4, 20],color:'#1890ff'}]},
			Pie:{series:[{ name: '一师', data: 50 }, { name: '二师', data: 30 }, { name: '三师', data: 20 }, { name: '四师', data: 18 }, { name: '五师', data: 8 }]},
			Ring:{series:[{ name: '一师', data: 50 ,format:()=> {return '一师:50人'}}, { name: '二师', data: 30 ,format:()=> {return '二师:30人'}}, { name: '三师', data: 20 ,format:()=> {return '三师:20人'}}, { name: '四师', data: 18 ,format:()=> {return '四师:18人'}}, { name: '五师', data: 8 ,format:()=> {return '五师:8人'}}]},
			Radar:{categories: ['维度1', '维度2', '维度3', '维度4', '维度5', '维度6'],series:[{name: '成交量1',data: [90, 110, 165, 195, 187, 172]}, {name: '成交量2',data: [190, 210, 105, 35, 27, 102]}]},
			Arcbar1:{series:[{ name: '正确率', data: 0.29 , color:'#2fc25b'}]},
			Arcbar2:{series:[{ name: '错误率', data: 0.65 , color:'#f04864'}]},
			Arcbar3:{series:[{ name: '完成率', data: 0.85 , color:'#1890ff'}]},
			Gauge:{categories:[{value:0.2,color:'#2fc25b'},{value:0.8,color:'#f04864'},{value:1,color:'#1890ff'}],series:[{ name: '完成率', data: 0.85 }]},
			ColumnMeter:{
				categories:["2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"],
				series:[{
					"name": "目标值",
					"data": [35, 33, 13, 34, 36, 31, 35, 36, 31, 33, 13, 34],
					"color": "#2fc25b"
				  }, {
					"name": "完成量",
					"data": [18, 24, 6, 28, 27, 21, 18, 27, 21, 24, 6, 28],
					"color": "#1890ff"
				  }]
			}
		}
		return Data
	},
	/*-----------------------------
	* func:动态图表
	* canvasId-画布id
	* data-图表格式化数据
	* target-this(全局对象this)
	-------------------------------*/
	showCharts(canvasId,data,target){
		return new uCharts({
			$this:target,//this实例组件内使用图表，必须传入this实例 
			canvasId: canvasId,//页面组件canvas-id，支付宝中为id
			width: data.width||target.cWidth||(uni.upx2px(750))*(data.pixelRatio||target.pixelRatio||1),//canvas宽度，单位为px，支付宝高分屏需要乘像素比 
			height: data.height||target.cHeight||(uni.upx2px(500))*(data.pixelRatio||target.pixelRatio||1),//canvas高度，单位为px，支付宝高分屏需要乘像素比
			type: data.type,//图表类型，可选值为rose、pie、line、column、area、ring、radar、arcbar、gauge、candle、bar、mix
			pixelRatio:data.pixelRatio||target.pixelRatio||1,//像素比，默认为1，非H5端引用无需设置
			rotate:data.rotate||false,//横屏模式，默认为false
			rotateLock:data.rotateLock||false,//锁定横屏模式，如果在支付宝和百度小程序中使用横屏模式，请赋值true，否则每次都会旋转90度。跨端使用通过uni-app的条件编译来赋值 
			fontSize: data.fontSize||11,//全局默认字体大小，单位为px，默认13px，高分屏不必乘像素比，自动计算 
			background: data.background||'#FFFFFF',//canvas背景颜色（如果页面背景颜色不是白色请设置为页面的背景颜色，默认#ffffff）
			enableScroll: data.enableScroll||false,//是否开启图表可拖拽滚动 默认false
			/*支持line、area、 column、 candle图表类型(需配合绑定@touchstart、@touchmove、@touchend方法) ",使用图表拖拽功能时，建议给canvas增加disable-scroll=true属性，在拖拽时禁止屏幕滚动*/
			enableMarkLine: data.enableMarkLine||false,//是否显示辅助线 默认false 支持line、area、column、candle图表类型 
			animation: data.animation||false,//是否动画展示，默认为 true
			legend: data.legend||((data.type=='arcbar'||data.type=='gauge')?false:true),//图例设置，是否显示图表下方各类别的标识
			dataLabel: data.dataLabel||true,//是否在图表中显示数据标签内容值
			dataPointShape: data.dataPointShape||true,//是否在图表中显示数据点图形标识
			disablePieStroke: data.disablePieStroke||false,//不绘制饼图（圆环图）各区块的白色分割线
			categories: data.categories||(data.type=='gauge'?[{value:0.2,color:'#2fc25b'},{value:0.8,color:'#f04864'},{value:1,color:'#1890ff'}]:[]),//图表X轴数据，数据类别(饼图、圆环图不需要)
			//categories.value|Number仅仪表盘有效，定义仪表盘分段值 
			//categories.color|String仅仪表盘有效，定义仪表盘分段背景颜色
			series: data.series||[],//图表Y轴数据，数据列表
			//series.data|Array|required|(饼图、圆环图为Number) 数据，如果传入null图表该处出现断点
			//series.data.value|Number|仅针对柱状图有效，主要作用为柱状图自定义颜色
			//series.data.color|String|仅针对柱状图有效，主要作用为柱状图自定义颜色
			//series.color|String|例如#7cb5ec不传入则使用系统默认配色方案
			//series.textColor|String|控制dataLabel颜色，例如#666666不传入则使用系统默认配色方案
			//series.name|String|数据名称
			//series.type|String|`混合图表`图形展示方式，有效值为point、line、column详细使用方法见demo
			//series.disableLegend|String|默认false|`混合图表`中禁止显示ToolTip图例，默认false即默认显示该类别图例
			//series.style|String|默认straight|暂时定义为`混合图表折线图样式`，有效值为`curve`曲线,`straight`直线
			//series.shape|String|默认为circle|图例样式，有效值为diamond:◇， circle:○， triangle:△， rect:□ 
			//series.format|Function|自定义显示数据内容
			/*
				format:((val)=>{return val.toFixed(2)+'%'})
				format: function (item, category) {
					return (category||'') + ' ' + item.name + ':' + item.data 
				}
			*/
		   title: data.title||{//适用于`ring`、`arcbar`、`gauge`
			   name: (data.type=='arcbar'||data.type=='gauge')?(Math.round(data.series[0].data*100)+'%'):'',//标题内容
			   fontSize: data.titlefontSize||11,//标题字体大小（可选，单位为px）
			   color: data.titlecolor||((data.type=='arcbar'||data.type=='gauge')?data.series[0].color:'#333333'),//标题颜色（可选）
			   offsetX: data.titleoffsetX||0,//默认0px | 标题横向位置偏移量，单位px
			   offsetY: data.titleoffsetY||0,//默认0px | 标题纵向位置偏移量，单位px
		   },
		   subtitle: data.subtitle||{//适用于`ring`、`arcbar`、`gauge`
			   name: (data.type=='arcbar'||data.type=='gauge')?data.series[0].name:'',//副标题内容
			   offsetX: data.subtitleoffsetX||0,//副标题横向位置偏移量，单位px，默认0
			   offsetY: data.subtitleoffsetY||0,//副标题横向位置偏移量，单位px，默认0
			   fontSize: data.subtitlefontSize||11,//副标题字体大小（可选，单位为px）
			   color: data.subtitlecolor||'#666666'||'#333333',//副标题颜色（可选）
		   },
			xAxis: data.xAxis||{//X轴配置
				rotateLabel:data.rotateLabel||true,//Boolean 默认为 false | X轴刻度（数值）标签是否旋转（仅在文案超过单屏宽度时有效）
				itemCount:data.itemCount||5,//Number|默认为5|X轴可见区域`数据数量`（即X轴数据密度），配合拖拽滚动使用（即仅在启用enableScroll时有效）
				labelCount:data.labelCount,//Number|X轴可见区域`标签数量`（即X轴数刻度标签单屏幕限制显示的数量）
				scrollShow: data.scrollShow||true,//默认为 false | 是否显示滚动条，配合拖拽滚动使用（即仅在启用enableScroll时有效）
				scrollAlign: data.scrollAlign||'left',//默认为 left | 滚动条初始位置，left为数据整体左对齐，right为右对齐 
				scrollBackgroundColor: data.scrollBackgroundColor||'#EFEBEF',//默认为 #EFEBEF | X轴滚动条背景颜色，配合拖拽滚动使用（即仅在启用enableScroll时有效）
				scrollColor: data.scrollColor||'#A6A6A6',//X轴滚动条颜色，配合拖拽滚动使用（即仅在启用enableScroll时有效）
				disabled: data.xAxisdisabled||false,//默认为 false | 不绘制X轴
				disableGrid:data.xAxisdisableGrid||false,//默认为 false | 不绘制X轴网格(即默认绘制网格)
				type:data.xAxistype||'calibration',//X轴网格样式，可选值calibration（刻度）、grid（网格）
				gridColor:data.xAxisgridColor||'#cccccc',//X轴网格颜色 例如#7cb5ec
				gridType:data.xAxisgridType||'solid',//X轴网格线型 'solid'为实线、'dash'为虚线
				dashLength:data.xAxisdashLength||4,//默认为 4px | X轴网格为虚线时，单段虚线长度
				fontColor:data.xAxisfontColor||'#666666',//X轴数据点颜色 例如#7cb5ec
			},
			yAxis: data.yAxis||{
				format:data.yAxisformat||((val)=>{return val.toFixed(data.fixed||0)+(data.unit||'')}),//如不写此方法，Y轴刻度默认保留两位小数。Function|自定义Y轴文案显示
				min:data.yAxismin,//Number|Y轴起始值
				max:data.yAxismax,//Number|Y轴终止值
				title:data.yAxistitle||'',//Y轴title
				disabled:data.yAxisdisabled||false,//不绘制Y轴
				disableGrid:data.yAxisdisableGrid||false,//不绘制Y轴网格(即默认绘制网格)
				splitNumber:data.yAxissplitNumber||5,//Y轴网格数量
				gridType:data.yAxisgridType||'dash',//Y轴网格线型 'solid'为实线、'dash'为虚线
				dashLength:data.yAxisdashLength||4,//Y轴网格为虚线时，单段虚线长度
				gridColor:data.yAxisgridColor||'#cccccc',//Y轴网格颜色 例如#7cb5ec
				fontColor:data.yAxisfontColor||'#666666',//Y轴数据点颜色 例如#7cb5ec
				titleFontColor:data.yAxistitleFontColor||'#333333',//Y轴title颜色 例如#7cb5ec
			},
			extra: data.extra||{
				arcbar:data.arcbar||{//圆弧进度图相关配置
					type:data.extratype||'default',//默认default |圆弧进度图样式，default为半圆弧，circle为整圆
					width:(data.extraWidth||target.arcbarWidth||uni.upx2px(12))*(data.pixelRatio||target.pixelRatio||1),//Number | 默认12px |圆弧进度图弧线宽度，单位为px
					backgroundColor:data.backgroundColor||'#ffe3e8',//默认#E9E9E9 |圆弧进度图背景颜色
					startAngle:data.startAngle||1.25,//默认0.75 |圆弧进度图起始角度，0-2之间，0为3点钟位置，0.5为6点钟，1为9点钟，1.5为12点钟
					endAngle:data.endAngle||0.75,//默认0.25 |圆弧进度图结束角度，0-2之间，0为3点钟位置，0.5为6点钟，1为9点钟，1.5为12点钟
				},
				gauge:data.gauge||{//仪表盘相关配置
					type:data.extratype||'default',//默认default |仪表盘样式，default为百度样式，`其他样式开发中`
					width: (data.extraWidth||uni.upx2px(30))*(data.pixelRatio||target.pixelRatio||1),//仪表盘背景的宽度,默认15px |仪表盘坐标轴（指示盘）线宽度，单位为px
					labelColor:data.labelColor||'#666666',//默认#666666|仪表盘刻度尺标签文字颜色",
					startAngle:data.startAngle||0.75,//默认0.75 |仪表盘起始角度，0-2之间，0为3点钟位置，0.5为6点钟，1为9点钟，1.5为12点钟
					endAngle:data.endAngle||0.25,//默认0.25 |仪表盘结束角度，0-2之间，0为3点钟位置，0.5为6点钟，1为9点钟，1.5为12点钟
					startNumber:data.startNumber||0,//默认0 |仪表盘起始数值
					endNumber:data.endNumber||100,//默认100 |仪表盘结束数值
					splitLine:data.splitLine||{//仪表盘刻度线配置
						fixRadius:data.fixRadius||0,//默认0 |仪表盘刻度线径向偏移量
						splitNumber:data.splitNumber||10,//默认10 |仪表盘刻度线分段总数量
						width: data.splitLinewidth||(uni.upx2px(30))*(data.pixelRatio||target.pixelRatio||1),//默认15px |仪表盘分割线长度
						color:data.splitLinecolor||'#FFFFFF',//默认#FFFFFF |仪表盘分割线颜色
						childNumber:data.childNumber||5,//默认5 |仪表盘子刻度线数量
						childWidth:data.childWidth||(uni.upx2px(30))*0.4*(data.pixelRatio||target.pixelRatio||1),//默认5px |仪表盘子刻度线长度
					},
					pointer:data.pointer||{//仪表盘指针配置
						width: data.pointerwidth||(uni.upx2px(30))*0.8*(data.pixelRatio||target.pixelRatio||1),//默认15px |仪表盘指针宽度
						color:data.pointercolor||'auto',//默认auto |仪表盘指针颜色，定义为auto时，随仪表盘背景颜色改变,或者可以指定颜色例如#7cb5ec
					}
				},
				radar:data.radar||{//雷达图相关配置
					max:data.max||200,//默认为 series |data的最大值，数据区间最大值，用于调整数据显示的比例
					labelColor:data.labelColor||'#666666',//默认为 #666666|各项标识文案的颜色
					gridColor:data.gridColor||'#cccccc',//默认为 #cccccc| 雷达图网格颜色
				},
				column:data.column||{//柱状图相关配置
					type:data.extratype||'group',//柱状图类型：group分组柱状图，stack为堆叠柱状图（未完成开发中），meter为温度计式图
					width: (data.width||target.cWidth||uni.upx2px(750))*(data.pixelRatio||target.pixelRatio||1)*0.45/(data.type=='column'?data.categories.length:5),//柱状图每项的图形宽度，单位为px
					meter:data.meter||{
						border:data.border||3,//边框宽度，单位为px，默认1px
						fillColor:data.fillColor||'#E5FDC3',//默认#FFFFFF |空余填充颜色
					}
				},
				pie:data.pie||{//饼图、圆环图相关配置
					activeOpacity:data.activeOpacity||0.5,//启用Tooltip点击时，突出部分的透明度，默认0.5 
					offsetAngle:data.offsetAngle||0,//起始角度偏移度数，顺时针方向，起点为3点钟位置（比如要设置起点为12点钟位置，即逆时针偏移90度，传入-90即可
					lableWidth:data.lableWidth||15,//数据标签到饼图外圆连线的长度，必填参数，否则报错，单位为px
					ringWidth:data.ringWidth||30*(data.pixelRatio||target.pixelRatio||1),//ringChart圆环宽度，单位为px
				},
				rose:data.rose||{//玫瑰图相关配置
					type:data.extratype||'area',//玫瑰图模式，可选值`area`面积模式，`radius`半径模式"
					minRadius:data.minRadius||50,//默认为图形半径的50% | 最小半径值
					activeOpacity:data.activeOpacity||0.5,//启用Tooltip点击时，突出部分的透明度，默认0.5
					offsetAngle:data.offsetAngle||0,//起始角度偏移度数，顺时针方向，起点为3点钟位置（比如要设置起点为12点钟位置，即逆时针偏移90度，传入-90即可）
					lableWidth:data.lableWidth||15,//数据标签到饼图外圆连线的长度，必填参数，否则报错，单位为px
				},
				line:data.line||{//折线图配置
					type:data.extratype||'straight',//curve曲线，straight直线
					width:data.extrawidth||2,//默认2px | 折线宽度
				},
				area:data.area||{//区域图配置
					type:data.extratype||'straight',//可选值：curve曲线，straight直线
					opacity:data.opacity||0.2,//区域图透明度
					addLine:data.addLine||false,//是否叠加相应的折线
					width:data.extrawidth||2,//默认2px | 折线宽度
				},
				candle:data.candle||{//K线图相关配置
					/*
	"opts.extra.candle| Object | |K线图相关配置",
	"opts.extra.candle.color| Object | |K线图颜色配置",
	"opts.extra.candle.color.upLine| String | 默认#f04864 |K线图为涨时线颜色",
	"opts.extra.candle.color.upFill| String | 默认#f04864 |K线图为涨时填充颜色",
	"opts.extra.candle.color.downLine| String | 默认#2fc25b |K线图为跌时线颜色",
	"opts.extra.candle.color.downFill| String | 默认#2fc25b |K线图为跌时填充颜色",
	"opts.extra.candle.average| Object | |均线设置",
	"opts.extra.candle.average.show | Boolean | 默认false |是否显示均线",
	"opts.extra.candle.average.name | `Array` |  |均线名称（例如['MA5','MA20']）用于下方图例显示",
	"opts.extra.candle.average.day | `Array` |  |均线单位日期（例如[5,20]为显示5日及20日均线）",
	"opts.extra.candle.average.color | `Array` |  |均线颜色，例如['#1890ff', '#2fc25b']"
					*/
				},
				bar:data.bar||{//条状图相关配置`开发中`
					type:data.extratype||'group',//条状图类型：`group`分组条状图，`stack`为堆叠条状图`开发中
					width:data.extrawidth,//条状图每项的图形宽度，单位为px`开发中
				},
				markLine:data.markLine||{//在柱状图、折线图、区域图、K线图中额外增加水平直线，仅在`opts.enableMarkLine`为true时显示
				/*
	"opts.extra.markLine |Object | | 在柱状图、折线图、区域图、K线图中额外增加水平直线，仅在`opts.enableMarkLine`为true时显示",
	"opts.extra.markLine.type |String | 默认为 solid| 线型 'solid'为实线、'dash'为虚线",
	"opts.extra.markLine.dashLength |Number | 默认为 4px | 单段虚线长度  ",
	"opts.extra.markLine.data |`Array` | | 辅助线数据，请传入`数组`类型，支持多条辅助线",
	"opts.extra.markLine.data.value |Number | | 辅助线数值",
	"opts.extra.markLine.data.color |String | 默认为 #| 辅助线颜色",
	"opts.extra.markLine.data.label |Boolean| 默认为 false | 是否显示数据标签",
	"opts.extra.markLine.data.labelBgColor |String | 默认为# | 数据标签背景颜色 ",
	"opts.extra.markLine.data.labelBgOpacity |String | 默认为# | 数据标签背景颜色透明度 ",
	"opts.extra.markLine.data.labelAlign |String | 默认为left | 数据标签显示位置，有效值left和right "
				*/
					
				},
				tooltip:data.tooltip||{//ToolTip设置
					/*
	"opts.extra.tooltip |Object | | ToolTip设置",
	"opts.extra.tooltip.bgColor| String | 默认#000000 | ToolTip背景颜色",
	"opts.extra.tooltip.bgOpacity | Number | 默认0.7 | ToolTip背景颜色透明度",
	"opts.extra.tooltip.gridType | String | 默认为 solid | 分割线线型 'solid'为实线、'dash'为虚线 ",
	"opts.extra.tooltip.dashLength | Number | 默认为 4px | 分割线为虚线时，单段虚线长度 ",
	"opts.extra.tooltip.gridColor | String | 默认为 # | 分割线颜色 ",
	"opts.extra.tooltip.fontColor | String | 默认为 #FFFFFF| 文字颜色 例如#7cb5ec ",
	"opts.extra.tooltip.horizentalLine| Boolean| 默认为 false| 是否显示水平横线 ",
	"opts.extra.tooltip.xAxisLabel |Boolean| 默认为 false | 是否显示数据标签",
	"opts.extra.tooltip.yAxisLabel |Boolean| 默认为 false | 是否显示数据标签",
	"opts.extra.tooltip.labelBgColor |String | 默认为#000000 | 数据标签背景颜色 ",
	"opts.extra.tooltip.labelBgOpacity |Number | 默认为0.7 | 数据标签背景颜色透明度 ",
	"opts.extra.tooltip.labelFontColor  |String | 默认为# | 数据标签文字颜色 ",
	"opts.extra.tooltip.activeBgColor |String |默认为#000000 | 仅柱状图类适用，当前点击柱状图的背景颜色 ",
	"opts.extra.tooltip.activeBgOpacity |Number |默认0.08 | 仅柱状图类适用，当前点击柱状图的背景颜色透明度 "
					*/
				},
				legendTextColor:data.legendTextColor||'#333333',//'#cccccc',//图例文案颜色 例如#7cb5ec`后期将变更为opts.legend.textColor迁移到基础配置里
				touchMoveLimit:data.touchMoveLimit||20,//图表拖拽时，每秒重新渲染的帧数`用于图表拖拽卡顿
			 },
			 
			 /*
			 [//2.0
				" updateData(data) | Function |  | 例如LineA.updateData({data}) ",
				"data | Object|  | 更新的数据 ",
				"data.categories| Array | 当前实例categories  | 同opts.categories ",
				"data.series| Array | 当前实例series | 同opts.series",
				"data.title| Array | 当前实例title | 同opts.title",
				"data.subtitle| Array | 当前实例subtitle | 同opts.subtitle",
				"data.scrollPosition| String | current | 开启图表拖拽后，更新图表后图表时，滚动条的偏移距离，可选值`left`更新后强制左对齐；`right`更新后强制右对齐；`current`更新后保持当前偏移距离",
				" data.animation | Boolean | 当前实例animation  | 是否动画展示 "
			],[//2.1
				"stopAnimation()  |  |  | 停止当前正在进行的动画效果，直接展示渲染的最终结果"
			],[//2.2
				"addEventListener(type, listener)  |  |  | 添加事件监听，type: String事件类型，listener: function 处理方法"
			],[//2.3
				"getCurrentDataIndex(e) |  |  | 获取图表中点击时的数据序列编号(-1表示未找到对应的数据区域), e: Object微信小程序标准事件，需要手动的去绑定touch事件，具体可参考wx-charts-demo中column图示例"
			],[//2.4
				"showToolTip(e, options?) |  |  | 图表中展示数据详细内容(目前仅支持line和area图表类型)，e: Object微信小程序标准事件，options: Object可选，tooltip的自定义配置，支持option.background，默认为#000000; option.format, function类型，接受两个传入的参数，seriesItem(Object, 包括seriesItem.name以及seriesItem.data)和category，可自定义tooltip显示内容。具体可参考ucharts-demo中line图示例"
			],[//2.5
				"scrollStart(e), scroll(e),scrollEnd(e) |  |  | 设置支持图表拖拽系列事件(支持line, area, column)，具体参考wx-charts-demo中ScrollLine图示例"
			],[//2.6
				"zoom(val) |  |  | 启用滚动条时，放大或缩小屏幕范围内数据数量。"
			],[//2.7
				"renderComplete |  |  | 图表渲染完成（如果有动画效果，则动画效果完成时触发）"
			]
			*/
			 
			 
		});
	},
	
	/*----------------------
	* func: 触摸开始事件
	* e-事件
	* obj-图表对象
	-------------------------*/
	start(e,obj){obj.scrollStart(e)},
	
	/*----------------------
	* func: 触摸移动事件
	* e-事件
	* obj-图表对象
	-------------------------*/
	move(e,obj){obj.scroll(e)},
	
	/*----------------------
	* func: 触摸结束事件
	* e-事件
	* obj-图表对象
	-------------------------*/
	end(e,obj){obj.scrollEnd(e)},
	
	/*----------------------
	* func: 显示提示信息事件
	* e-事件
	* obj-图表对象
	-------------------------*/
	tip(e,obj){
		obj.showToolTip(e, {
			format: function (item, category) {
				return (category||'') + ' ' + item.name + ':' + (item.data.value||item.data) 
			}
		});
	},
	
	/*----------------------
	* func: 改变y坐标数值
	* e-事件
	* t-y轴顶部坐标值
	-------------------------*/
	touchY(e,t){
		var ty=e.changedTouches?e.changedTouches[0].y:e.mp.changedTouches[0].y
		if (e.changedTouches) {
		  e.changedTouches[0].y=(ty<0)?(ty+t):ty;
		} else {
		  e.mp.changedTouches[0].y=(ty<0)?(ty+t):ty;
		}
		return e
	},
	/*----------------------
	* func: 计算图表x轴分类对应y值的合计
	* obj-要累计的数组对象
	-------------------------*/
	sumArr(obj){
		obj.forEach((e)=>{
			if(e.table.ts){
				e.opts=e.table.ts;
				this.sumObj(e);
			}else{
				e.opts=e.table[0];
				this.sumArray(e);
			}
		});
	},
	sumObj(e){
		if(e.chartType=='pie'){
			if(e.table.ts) e.table.ts.series.forEach((v)=>{
				if(e.table.ts.sum!=undefined) e.table.ts.sum+=v.data;
			});
			if(e.table.ts) e.table.ts.series.forEach((v)=>{
				if(e.table.ts.zb) e.table.ts.zb.push((v.data*100/e.table.ts.sum).toFixed(2));
			});
			if(e.table.mj) e.table.mj.series.forEach((v)=>{
				if(e.table.mj.sum!=undefined) e.table.mj.sum+=v.data;
			});
			if(e.table.je) e.table.je.series.forEach((v)=>{
				if(e.table.je.sum!=undefined) e.table.je.sum+=v.data;
			});
		}else if(e.chartType=='column'){
			if(e.table.ts) e.table.ts.series[0].data.forEach((v,i)=>{
				if(e.table.ts.sum!=undefined) e.table.ts.sum+=v.value||v;
				if(e.table.ts.sum1!=undefined) e.table.ts.sum1+=e.table.ts.series[1].data[i].value||e.table.ts.series[1].data[i];
				if(e.table.ts.sum2!=undefined) e.table.ts.sum2+=e.table.ts.series[2].data[i].value||e.table.ts.series[2].data[i];
				if(e.table.ts.ce) e.table.ts.ce.push(v.value?(e.table.ts.series[1].data[i].value*1-v.value*1):(e.table.ts.series[1].data[i]*1-v*1));
				if(e.table.ts.zb) e.table.ts.zb.push((v.value?(v.value*100/e.table.ts.series[1].data[i].value):(v*100/e.table.ts.series[1].data[i])).toFixed(2));
			});
			if(e.table.ts.ce) e.table.ts.ce.push(e.table.ts.sum1-e.table.ts.sum);
			if(e.table.ts.zb) e.table.ts.zb.push((e.table.ts.sum*100/e.table.ts.sum1).toFixed(2));
			if(e.table.mj) e.table.mj.series[0].data.forEach((v)=>{
				if(e.table.mj.sum!=undefined) e.table.mj.sum+=v.value||v;
			});
			if(e.table.je) e.table.je.series[0].data.forEach((v)=>{
				if(e.table.je.sum!=undefined) e.table.je.sum+=v.value||v;
			});
		}
	},
	sumArray(e){
		if(e.chartType=='pie'){
			if(e.table[0]) e.table[0].series.forEach((v)=>{
				if(e.table[0].sum!=undefined) e.table[0].sum+=v.data;
			});
			if(e.table[0]) e.table[0].series.forEach((v)=>{
				if(e.table[0].zb) e.table[0].zb.push((v.data*100/e.table[0].sum).toFixed(2));
			});
			if(e.table[1]) e.table[1].series.forEach((v)=>{
				if(e.table[1].sum!=undefined) e.table[1].sum+=v.data;
			});
			if(e.table[2]) e.table[2].series.forEach((v)=>{
				if(e.table[2].sum!=undefined) e.table[2].sum+=v.data;
			});
			if(e.table[3]) e.table[3].series.forEach((v)=>{
				if(e.table[3].sum!=undefined) e.table[3].sum+=v.data;
			});
			if(e.table[3]) e.table[3].sum=e.table[3].sum/e.table[0].series.length
		}else if(e.chartType=='column'){
			if(e.table[0]) e.table[0].series[0].data.forEach((v,i)=>{
				if(e.table[0].sum!=undefined) e.table[0].sum+=v.value||v;
				if(e.table[0].sum1!=undefined) e.table[0].sum1+=e.table[0].series[1].data[i].value||e.table[0].series[1].data[i];
				if(e.table[0].sum2!=undefined) e.table[0].sum2+=e.table[0].series[2].data[i].value||e.table[0].series[2].data[i];
				if(e.table[0].ce) e.table[0].ce.push(v.value?(e.table[0].series[1].data[i].value*1-v.value*1):(e.table[0].series[1].data[i]*1-v*1));
				if(e.table[0].zb) e.table[0].zb.push((v.value?(v.value*100/e.table[0].series[1].data[i].value):(v*100/e.table[0].series[1].data[i])).toFixed(2));
			});
			if(e.table[0].ce) e.table[0].ce.push(e.table[0].sum1-e.table[0].sum);
			if(e.table[0].zb) e.table[0].zb.push((e.table[0].sum*100/e.table[0].sum1).toFixed(2));
			if(e.table[1]) e.table[1].series[0].data.forEach((v)=>{
				if(e.table[1].sum!=undefined) e.table[1].sum+=v.value||v;
			});
			if(e.table[2]) e.table[2].series[0].data.forEach((v)=>{
				if(e.table[2].sum!=undefined) e.table[2].sum+=v.value||v;
			});
		}
	}
};
export default chartsUtil;
