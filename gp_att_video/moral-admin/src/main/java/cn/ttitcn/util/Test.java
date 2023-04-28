package cn.ttitcn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.alibaba.fastjson.JSONObject;

import cn.ttitcn.common.config.Global;

/**
 * 1.声纹识别接口,请填写在讯飞开放平台-控制台-对应能力页面获取的APPID、APIKey、APISecret。
 * 2.groupId要先创建,然后再在createFeature里使用,不然会报错23005,修改时需要注意保持统一。
 * 3.音频base64编码后数据(不超过4M),音频格式需要16K、16BIT的MP3音频。
 * 4.主函数只提供调用示例,其他参数请到对应类去更改,以适应实际的应用场景。
 * 5.使用1:1或1:N功能请注意更换音频。
 */
public class Test {

    //音频存放位置(比对功能请注意更换音频)
    // private static String AUDIO_PATH = Global.getUploadPath() + "/2022/02/24/4da790e45ff2f470b293d744172ca4b9.mp3";
    private static String AUDIO_PATH = "C:\\Users\\Admin\\Desktop\\明天天气怎么样.mp3";
    public static void main(String[] args) {
        /**1.创建声纹特征库*/
    	CreateGroup.doCreateGroup(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY);
        /**2.添加音频特征*/
    	// avxzpco0a2   8ell2ta2lu
    	//System.out.print(AUDIO_PATH);
    	//JSONObject obj = CreateFeature.doCreateFeature(requestUrl,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,AUDIO_PATH);
    	// System.out.println(obj);
        /**3.查询特征列表*/
        //QueryFeatureList.doQueryFeatureList(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY);
        /**4.特征比对1:1*/
        //SearchOneFeature.doSearchOneFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**5.特征比对1:N*/
        //JSONObject object = SearchFeature.doSearchFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,AUDIO_PATH);
        /**6.更新音频特征*/
        //UpdateFeature.doUpdateFeature(requestUrl,APPID,apiSecret,apiKey,AUDIO_PATH);
        /**7.删除指定特征*/
        //DeleteFeature.doDeleteFeature(XFYunConstant.REQUESTURL,XFYunConstant.APPID,XFYunConstant.APISECRET,XFYunConstant.APIKEY,"gg57mcwmk62oztvpyv");
        /**8.删除声纹特征库*/
        //DeleteGroup.doDeleteGroup(requestUrl,APPID,apiSecret,apiKey);
    }
	
}
