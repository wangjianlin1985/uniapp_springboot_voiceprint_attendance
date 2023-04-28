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
				<button class="cu-btn bg-blue margin-tb-sm" @tap="endRecord()"   >结束录音</button>
			</view>
			<view class="padding flex flex-direction " >
			<button class="cu-btn bg-blue  margin-tb-sm"   @tap="register()" >注册</button>
			</view>
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
				number: '1 2 3 4 5 6',
				featureId: '',
				loginName: '',
				userName: '',
				password: '',
				phonenumber: ''
			}
		},
		onLoad(options) {
			this.randomNum() 
			
			this.loginName = options.loginName
			this.userName = options.userName
			this.password = options.password
			this.phonenumber = options.phonenumber
			
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
						that_.http.post('/openapi/system/registerVoice', {
							path: uploadFileRes.data
						}).then((resFea) => {
							uni.hideLoading()
							if(resFea.code != 0){
								this.vusui.alert(resFea.msg)
								return false;
							}
							if(resFea.code != 0) {
								that_.vusui.alert(resFea.msg)
								return false;
							}
							uni.showToast({
							    title: '识别成功，请点击注册按钮完成注册',
							    duration: 500
							});
							that_.featureId = resFea.data.featureId
						}) 
					}
				})
			});
		},
		methods: {
			startRecord() {
				console.log('开始录音');
				recorderManager.start();
				this.text = '重新录音'
			},
			endRecord() {
				console.log('录音结束');
				recorderManager.stop();
			},
			register: function(){
				if(!this.featureId){
					this.vusui.alert("请录入语音再注册")
					return false;
				}
				this.http.post('/openapi/system/register', {
					loginName: this.loginName,
					userName: this.userName,
					password: this.password,
					featureId: this.featureId,
					phonenumber: this.phonenumber
				}).then((res) => {
					if(res.code != 0) {
						this.vusui.alert(res.msg)
						return false;
					}
					uni.navigateTo({
						url: "../../loginVoice"
					})
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
