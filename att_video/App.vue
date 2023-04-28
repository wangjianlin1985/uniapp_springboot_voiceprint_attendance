<script>
	import Vue from 'vue'
	export default {
		onLaunch: function() {
			uni.setStorageSync('ip','192.168.1.4')
			uni.setStorageSync('port',8887)
			uni.setStorageSync('appname', '/moral');
			var token = uni.getStorageSync('token');
			if(token == null || token == ''){
				uni.reLaunch({
					url: 'pages/login/loginVoice'
				});
			}
			uni.getSystemInfo({
				success: function(e) {
					// #ifndef MP
					Vue.prototype.StatusBar = e.statusBarHeight;
					if (e.platform == 'android') {
						Vue.prototype.CustomBar = e.statusBarHeight + 50;
					} else {
						Vue.prototype.CustomBar = e.statusBarHeight + 45;
					};
					// #endif

					// #ifdef MP-WEIXIN
					Vue.prototype.StatusBar = e.statusBarHeight;
					let custom = wx.getMenuButtonBoundingClientRect();
					Vue.prototype.Custom = custom;
					Vue.prototype.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
					// #endif		

					// #ifdef MP-ALIPAY
					Vue.prototype.StatusBar = e.statusBarHeight;
					Vue.prototype.CustomBar = e.statusBarHeight + e.titleBarHeight;
					// #endif
				}
			})
		},
		onShow: function() {
			
		},
		onHide: function() {
			
		}
	}
</script>

<style>
	/*每个页面公共css */
	@import "colorui/main.css";
	@import "colorui/icon.css";
	@import "static/css/base.css";
	@import "static/css/iconfont.css";
	@import "vusui-app-layer/theme/default/vusui-layer.css";
</style>
