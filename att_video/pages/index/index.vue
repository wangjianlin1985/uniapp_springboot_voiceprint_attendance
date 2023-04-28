<template>
	<view>
		<view class="cu-list grid" :class="['col-2']">
			<view class="cu-item" @tap="toAtt()">
				<view class="icon iconfont icon-ketiguanli base_fontcolor  bigsize " ></view>
				<text>手动考勤</text>
			</view>
			
			<view class="cu-item" @tap="toAttVoice()">
				<view class="icon iconfont icon-ketiguanli base_fontcolor  bigsize " ></view>
				<text>声纹考勤</text>
			</view>
			
			<view class="cu-item" @tap="toAttHistory()">
				<view class="icon iconfont icon-ketiguanli base_fontcolor  bigsize " ></view>
				<text>考勤历史</text>
			</view>
			
			<view class="cu-item" @tap="toGrzx()" >
				<view class="icon iconfont icon-ketiguanli base_fontcolor bigsize" ></view>
				<text>个人中心</text>
			</view>
			
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		methods: {
			
			toAttVoice() {
				uni.navigateTo({
					url: '../att/attVoice'
				});
			},
			
			toAttHistory() {
				uni.navigateTo({
					url: '../att/attHistory'
				});
			},
			
			toAtt() {
				uni.navigateTo({
					url: '../att/att'
				});
			},
			
			
			// 个人中心
			toGrzx(){
				uni.navigateTo({
					url: '../grzx/grzx'
				})
			}
			
		}
	}
</script>
<style scoped>
	.bigsize {
		font-size: xx-large;
	}
</style>