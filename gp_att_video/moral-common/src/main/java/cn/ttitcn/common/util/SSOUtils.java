package cn.ttitcn.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.*;
import java.util.Map.Entry;


/**
 * 单点登录的一些工具签名什么的
 */
public class SSOUtils {
	
	/**
	 * MD5加密
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
                    i += 256;
                }
				if (i < 16) {
                    buf.append("0");
                }
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	
	/**
	 * 生成签名
	 * 
	 * @param params
	 *            签名参数
	 * @param secret
	 *            签名密钥
	 * @return 签名值
	 */
	public static String generator(Map<String, Object> params, String secret) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer strBuffer = new StringBuffer();
		Object value;
		for (String key : keys) {
			value = params.get(key);
			strBuffer.append(key).append("=").append(value).append("&");
		}
		if (strBuffer.length() > 0) {
			strBuffer.deleteCharAt(strBuffer.length() - 1);
		}
		String md5val = md5(strBuffer.toString());
		return hashHmac(md5val,secret).toUpperCase();
	}

	/**
	 * 传入String(先md5加密)和密钥生成签名
	 * @param text
	 * @param secret
	 * @return
	 */
	public static String hashHmac(String text, String secret) {
		try {
			Mac hmacMD5 = Mac.getInstance("HmacMD5");
			SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacMD5");
			hmacMD5.init(secret_key);
			String hash = byte2hex(hmacMD5.doFinal(text.getBytes()));
			return hash;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * byte2String
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) 
	{
	    StringBuilder hs = new StringBuilder();
	    String stmp;
	    for (int n = 0; b!=null && n < b.length; n++) {
	        stmp = Integer.toHexString(b[n] & 0XFF);
	        if (stmp.length() == 1) {
                hs.append('0');
            }
	        hs.append(stmp);
	    }
	    return hs.toString().toUpperCase();
	}
	
	/**
	 * 获取请求的所有参数(除了sign签名)
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getAllParamMap(HttpServletRequest request){
		// 获取所有的参数
		Map<String, String[]> allParamMap = request.getParameterMap();
		Map<String, Object> allSignleParamMap = new HashMap<String, Object>();
		// 将所有的参数转为一对一
		for (Entry<String, String[]> es : allParamMap.entrySet()) {
			//排除签名和为空的参数
			if (!es.getKey().equals("sign") && es.getValue().length > 0) {
				allSignleParamMap.put(es.getKey(), es.getValue()[0]);
			}
		}
		return allSignleParamMap;
	}
	
	/**
	 * 检查签名是否过期 过期就返回false 没过期返回true
	 * @param time 传来的时间
	 * @param overdueTime 多久过期
	 * @return
	 */
	public static boolean checkUnOverdue(long time,long overdueTime) {
		long nowTime = System.currentTimeMillis();
		return Math.abs(nowTime - time) <= overdueTime;
	}
	
	/**
	 * 获取多少位的随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random r = new Random();
	    StringBuffer sb = new StringBuffer();
	    int len = str.length();
	    for (int i = 0; i < length; i++) {
	        sb.append(str.charAt(r.nextInt(len-1)));
	    }
	    return sb.toString();
	}
	
}
