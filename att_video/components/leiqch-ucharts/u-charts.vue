<template>
	<canvas v-if="canvasId" :id="canvasId" :canvasId="canvasId" :style="{'width':(show?cWidth:0)*pixelRatio+'px','height':(show?cHeight:0)*pixelRatio+'px', 'overflow':'hidden', 'transform': 'scale('+(1/pixelRatio)+')','margin-left':-cWidth*(pixelRatio-1)/2+'px','margin-top':-cHeight*(pixelRatio-1)/2+'px'}"  @touchstart="touchStart" @touchmove="touchMove" @touchend="touchEnd" @error="error">
	</canvas>
</template>

<script>
	import crt from './js/chartsUtil'
	var canvases = {};
	
	export default {
		props: {
			chartType: {
				required: true,
				type: String,
				default: 'column'
			},
			extraType: {
				type: String,
				default: 'group'
			},
			opts: {
				required: true,
				type: Object,
				default () {
					return null;
				},
			},
			canvasId: {
				type: String,
				default: 'u-canvas',
			},
			cWidth: {
				type: Number,
				default: 375,
			},
			cHeight: {
				type: Number,
				default: 250,
			},
			pixelRatio: {
				type: Number,
				default: 1,
			},
			show:{
				type: Boolean,
				default: true,
			},
			scrollTop:{
				type: Number,
				default: 0,
			}
		},
		mounted() {
			this.init();
		},
		methods: {
			init() {
				this.opts.type=this.chartType
				// console.log(this.canvasId,this.chartType,this.extraType,this.opts.extra)
				if(this.extraType){ 
					if(this.opts.extra&&this.opts.extra[this.chartType]) this.opts.extra[this.chartType].type=this.extraType
					else if(this.opts.extra) this.opts.extra[this.chartType]={type:this.extraType}
					
					else this.opts.extra=JSON.parse('{\"'+this.chartType+'\":{\"type\":\"'+this.extraType+'\"}}')
				}
				// console.log(this.opts.extra)
				canvases[this.canvasId] = crt.showCharts(this.canvasId,this.opts,this);
			},
			// 这里仅作为示例传入两个参数，cid为canvas-id,newdata为更新的数据，需要更多参数请自行修改
			changeData(cid,newdata,type,etype) {
				if(type)  newdata.type=type
				if(etype){
					if(newdata.extra&&newdata.extra[type]) newdata.extra[type].type=etype
					else if(newdata.extra) newdata.extra[type]={type:etype}
					else newdata.extra=JSON.parse('{\"'+type+'\":{\"type\":\"'+etype+'\"}}')
				}
				canvases[cid].updateData(newdata);
			},
			touchStart(e) {
				e=this.touchY(e,this.scrollTop)
				canvases[this.canvasId].showToolTip(e, {
					format: function(item, category) {
						return (category||'') + ' ' + item.name + ':' + (item.data.value||item.data)
					}
				});
				canvases[this.canvasId].scrollStart(e);
			},
			touchMove(e) {
				e=this.touchY(e,this.scrollTop)
				canvases[this.canvasId].scroll(e);
			},
			touchEnd(e) {
				e=this.touchY(e,this.scrollTop)
				canvases[this.canvasId].scrollEnd(e);
			},
			error(e) {
				console.log(e)
			},
			touchY(e,t){
				var ty=e.changedTouches?e.changedTouches[0].y:e.mp.changedTouches[0].y
				if (e.changedTouches) {
				  e.changedTouches[0].y=(ty<0)?(ty+t):ty;
				} else {
				  e.mp.changedTouches[0].y=(ty<0)?(ty+t):ty;
				}
				return e
			}
		},
	};
</script>

<style scoped>
	.charts {
		width: 100%;
		height: 100%;
		flex: 1;
		background-color: #FFFFFF;
	}
</style>
