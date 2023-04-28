<template>
	<view style="width: 100%">
		<vus-layer></vus-layer>
		<form>
			<view class="cu-form-group margin-top">
				<view class="title">学号</view>
				<input placeholder="请输入学号" v-model="user.loginName"></input>
			</view>
			
			<view class="cu-form-group ">
				<view class="title">手机号</view>
				<input placeholder="请输入手机号" v-model="user.phonenumber"></input>
			</view>
			
			<view class="cu-form-group ">
				<view class="title">姓名</view>
				<input placeholder="请输入姓名" v-model="user.userName"></input>
			</view>
			
			<view class="cu-form-group">
				<view class="title">密码</view>
				<input placeholder="请输入密码" v-model="user.password" type="password"></input>
			</view>
			
			<view class="cu-form-group">
				<view class="title">确认</view>
				<input placeholder="请确认密码" v-model="user.passwordConfirm" type="password"></input>
			</view>
			
			<view class="padding flex flex-direction" @tap="nextStep()">
				<button class="cu-btn bg-red margin-tb-sm lg">下一步</button>
			</view>
			
			<button class="cu-btn  margin-tb-sm"   @tap="tologin()" >已有账号直接登录</button>
			
		</form>	
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				windowHeight: "200px",
				msg: '',
				bgurl: '',
				user: {
					
				}
			}
		},
		onNavigationBarButtonTap(){
			uni.navigateTo({
				url: 'longSetIP/longSetIP'
			})
		},
		methods: {
			tologin(){
				uni.navigateTo({
					url: "../login"
				})
			},
			nextStep(){
				if(!this.user.userName || !this.user.loginName || !this.user.password) {
					this.vusui.alert('请输入完整内容')
					return false;
				}
				if(this.user.password != this.user.passwordConfirm){
					this.vusui.alert('两次密码不一致')
					return false;
				}
				
			
				this.http.post('/openapi/system/checkLoginName/' + this.user.loginName).then((res) => {
					if(res.code != 0) {
						this.vusui.alert(res.msg)
						return false;
					}
					if(res.data.result == 1){
						this.vusui.alert("学号重复")
						return false;
					}
					uni.navigateTo({
						url: 'registerVoice/registerVoice?loginName=' + this.user.loginName 
							+ "&userName=" + this.user.userName 
							+ "&password=" + this.user.password
							+ "&phonenumber=" + this.user.phonenumber
					})
				}) 
			}
			
			
		}
	}
</script>

<style scoped>
	.bgImgCover {
		width: 100%;
		height: 414upx;
		background-size: 100% 100%!important;
	}
</style>