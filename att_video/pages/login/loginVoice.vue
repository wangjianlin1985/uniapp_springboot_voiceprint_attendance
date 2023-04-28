<template>
	<view  style="width: 100%">
		<vus-layer></vus-layer>
		<form>
			
			<view class="cu-form-group">
				<view >点击“开始录音”后阅读以下文字，完成后点击“结束录音”</view>
			</view>
			
			<view class="cu-form-group ">
				<input v-model="number"></input>
				<button class="cu-btn margin-tb-sm" @tap="randomNum()">刷新数字</button>
			</view>
			
			<view class="padding flex flex-direction ">
				<button class="cu-btn bg-blue margin-tb-sm" @tap="startRecord()" >{{text}}</button>
				<button class="cu-btn bg-blue margin-tb-sm" @tap="endRecord()"   >结束录音并登录</button>
			</view>
			
			<button class="cu-btn  margin-tb-sm"   @tap="toLogin()" >使用账户密码登录</button>
			<button class="cu-btn  margin-tb-sm"   @tap="toregister()" >没有账号注册一个</button>
		</form>	
		
	</view>
</template>

<script>
	const recorderManager = uni.getRecorderManager();
	const innerAudioContext = uni.createInnerAudioContext();
	export default {
		data() {
			return {
				text: '开始录音',
				number: '1 2 3 4 5 6'
			}
		},
		onLoad(options) {
			this.randomNum() 
			
			let self = this;
			let that_ = this
			recorderManager.onStop(function (res) {
				let tempFilePath = res.tempFilePath;
				if(tempFilePath == null || tempFilePath == '' || tempFilePath == 'undefined' || typeof(tempFilePath) == "undefined"){
					return false;
				}
				let url = "http://"+uni.getStorageSync("ip")+":"+uni.getStorageSync("port")+"/"+uni.getStorageSync("appname")+"/common/uploadVoice"
				uni.showLoading({
					title: "文件上传中..."
				})
				uni.uploadFile({
					name: 'file',
					url: url,
					filePath: tempFilePath,
					fileType: 'audio',
					complete: (uploadFileRes) => {
						uni.hideLoading()
						uni.showLoading({
							title: "文件识别中..."
						})
						that_.http.post('/openapi/system/loginVoice', {
							path:  uploadFileRes.data
						}).then((resFea) => {
							console.log("文件识别：" + JSON.stringify(resFea))
							uni.hideLoading()
							if(resFea.code != 0) {
								that_.vusui.alert(resFea.msg)
								return false;
							}
							//登陆成功后获取用户信息，放入storage
							uni.setStorageSync('token', resFea.data.userId)
							uni.setStorageSync('userId', resFea.data.userId)
							uni.setStorageSync('deptId', resFea.data.dept.deptId)
							uni.setStorageSync('deptName', resFea.data.dept.deptName)
							uni.setStorageSync('userName', resFea.data.userName)
							uni.setStorageSync('phonenumber', resFea.data.phonenumber)
							uni.setStorageSync('avatar',resFea.data.avatar)
							uni.navigateTo({
								url:"../index/index"
							})
						}) 
					}
				})
			});
		},
		methods: {
			startRecord() {
				recorderManager.start();
				this.text = '重新录音'
			},
			endRecord() {
				recorderManager.stop();
			},
			toLogin(){
				uni.navigateTo({
					url: 'login'
				})
			},
			toregister(){
				uni.navigateTo({
					url: 'register/register'
				})
			},
			// 生成一个6位随机数
			randomNum: function(){
				var vertify = '0123456789'
				var sd = '';
				for (var i = 0; i < 10; i++) {
					var random = Math.floor(Math.random() * (vertify.length));
					sd += (vertify[random] + " ");
				}
				this.number = sd
			}
		}
	}
</script>

<style>

</style>
