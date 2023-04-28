<template>
	<view style="width: 100%">
		<vus-layer></vus-layer>
		<form>
			<view class="cu-form-group margin-top">
				<view class="title">账户</view>
				<input placeholder="请输入用户名" v-model="username"></input>
			</view>
			<view class="cu-form-group">
				<view class="title">密码</view>
				<input placeholder="请输入密码" v-model="password" type="password"></input>
			</view>
			<view class="grid col-1 padding-sm bg-white margin-top"  @tap="resetpwd()">
				<view class="margin-tb-sm text-center" >
					<button class="cu-btn bg-blue lg" >重置密码</button>
				</view>
			</view>
		</form>	
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				windowHeight: "200px",
				msg: '',
				username:'',
				password:'',
				bgurl: ''
			}
		},
		
		methods: {

			resetpwd: function(){
				if(this.username.length == 0 || this.password.length == 0){
					this.vusui.alert('请输入用户名和密码')
					return false;
				}
				this.vusui.load(3)
				this.http.post('/openapi/system/updatePwd', {
					username: this.username,
					password: this.password
				}).then((res) => {
					this.vusui.close("loading");
					if (res.code != 0) {
						this.vusui.alert(res.msg)
						return;
					} else {
						uni.navigateBack();
					}
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