# uniapp_springboot_voiceprint_attendance
基于uniapp+springboot声纹考勤系统app设计

程序技术： Springboot + Uniapp + Mysql

  本考勤系统项目前端采用基于跨平台的Uniapp设计，开发工具采用Hbuilder，可以实现一次开发多端发布。这个考勤系统不同于传统的考勤系统，可以实现对接讯飞开放平台，其中的声纹识别api文档地址：https://www.xfyun.cn/doc/voiceservice/isv/API.html#接口调用流程，实现语音声纹的录入注册和查询对比。手机端接口都在web.controller.api.SysApiController里面，录音文件上传接口是在cn.ttitcn.web.controller.common.CommonController里面的common/uploadVoice，他的返回值是该录音文件在服务器硬盘中存储的绝对路径。项目实现的功能如下：

一 用户注册
1 表单输入用户名密码等基础信息，验证数据后，点击下一步
2 进入录音界面
3 A 录音完成--上传录音--返回录音文件存在服务器的路径
  B 再根据A的路径到讯飞注册，返回一个特征码featureId  ---registerVoice
4 根据第3步得到的特征码和第1步表单的内容调注册接口    ---register

二 声纹登录      ---loginVoice
1 录音完成--上传录音--返回录音文件存在服务器的路径
2 再根据A的路径到讯飞查询1：N，返回一组根据比对分数排序的特征码列表，取第一个特征码
3 再根据该特征码查询用户信息，查询到了就返回，登录成功

三 用户密码登录  ---loginNocode
1 根据用户名查询出用户，再和密码进行匹配，相同就返回登录成功

四 声纹考勤      ---attVoice
1 录音完成--上传录音--返回录音文件存在服务器的路径
2 再根据A的路径到讯飞查询1：N，返回一组根据比对分数排序的特征码列表，取第一个特征码
3 再根据该特征码查询用户信息，然后到考勤表查询记录，没有就插入，有就更新考勤时间

五 手动考勤      ---att
1 进入手动考勤页面的时候会先加载今天的考勤数据，没有会先生成一条，然后返回该条数据，有的话直接返回 ---getAttToday
2 根据考勤记录的ID直接更新时间


管理员登录地址： http://localhost:8887/moral/login
管理员账号密码： admin/admin
