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
			<view class="padding flex flex-direction" @tap="login()">
				<button class="cu-btn bg-red margin-tb-sm lg">登录</button>
			</view>
			
			<button class="cu-btn  margin-tb-sm"   @tap="tologinVoice()" >使用声纹登录</button>
			<button class="cu-btn  margin-tb-sm"   @tap="toregister()" >没有账号注册一个</button>
			
			<button class="cu-btn  margin-tb-sm"   @tap="toreset()" >重置密码</button>
			
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
		onNavigationBarButtonTap(){
			uni.navigateTo({
				url: 'longSetIP/longSetIP'
			})
		},
		methods: {
			toregister(){
				uni.navigateTo({
					url: 'register/register'
				})
			},
			tologinVoice(){
				uni.navigateTo({
					url: 'loginVoice'
				})
			},
			toreset(){
				uni.navigateTo({
					url: 'resetpwd'
				})
			},
			
			login: function(){
				if(this.username.length == 0 || this.password.length == 0){
					this.vusui.alert('请输入用户名和密码')
					return false;
				}
				this.http.post('/openapi/system/loginNocode', {
					username: this.username,
					password: this.password
				}).then((res) => {
					console.log(res)
					if(res.code != 0) {
						this.vusui.alert(res.msg)
						return false;
					}
					//登陆成功后获取用户信息，放入storage
					uni.setStorageSync('token', res.data.userId)
					uni.setStorageSync('userId', res.data.userId)
					uni.setStorageSync('deptId', res.data.dept.deptId)
					uni.setStorageSync('deptName', res.data.dept.deptName)
					uni.setStorageSync('userName', res.data.userName)
					uni.setStorageSync('phonenumber', res.data.phonenumber)
					uni.setStorageSync('avatar',res.data.avatar)
					uni.navigateTo({
						url:"../index/index"
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