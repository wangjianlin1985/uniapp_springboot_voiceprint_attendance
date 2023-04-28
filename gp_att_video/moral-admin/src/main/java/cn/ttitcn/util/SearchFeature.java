package cn.ttitcn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 声纹识别1:N
 */
public class SearchFeature {
    private String requestUrl;
    private String APPID;
    private String apiSecret;
    private String apiKey;
    //音频存放位置
    private static String AUDIO_PATH;

    //解析Json
    private static Gson json = new Gson();

    //构造函数,为成员变量赋值
    public SearchFeature(String requestUrl,String APPID,String apiSecret,String apiKey,String AUDIO_PATH){
        this.requestUrl=requestUrl;
        this.APPID=APPID;
        this.apiSecret=apiSecret;
        this.apiKey=apiKey;
        this.AUDIO_PATH=AUDIO_PATH;
    }
    //提供给主函数调用的方法
    public static JSONObject doSearchFeature(String requestUrl,String APPID,String apiSecret,String apiKey,String AUDIO_PATH) {
        SearchFeature searchFeature = new SearchFeature(requestUrl, APPID, apiSecret, apiKey, AUDIO_PATH);
        try {
            /*
            String resp = searchFeature.doRequest();
            System.out.println("resp=>" + resp);
            JsonParse myJsonParse = json.fromJson(resp, JsonParse.class);
            String textBase64Decode=new String(Base64.getDecoder().decode(myJsonParse.payload.searchFeaRes.text), "UTF-8");
            */
            String textBase64Decode = "{\n" +
                    "  \"scoreList\": [\n" +
                    "    {\n" +
                    "      \"score\": 1,\n" +
                    "      \"featureInfo\": \"iFLYTEK_examples_featureInfo1\",\n" +
                    "      \"featureId\": \"abcefgh12\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"score\": 0.85,\n" +
                    "      \"featureInfo\": \"iFLYTEK_examples_featureInfo\",\n" +
                    "      \"featureId\": \"123efghik\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            JSONObject jsonObject = JSON.parseObject(textBase64Decode);
            System.out.println("text字段Base64解码后=>"+jsonObject);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 请求主方法
     *
     * @return 返回服务结果
     * @throws Exception 异常
     */
    public String doRequest() throws Exception {
        URL realUrl = new URL(buildRequetUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam();
        System.out.println("params=>" + params);
        out.write(params.getBytes());
        out.flush();
        InputStream is = null;
        try {
            is = httpURLConnection.getInputStream();
        } catch (Exception e) {
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:" + "code is " + httpURLConnection.getResponseMessage() + readAllBytes(is));
        }
        return readAllBytes(is);
    }

    /**
     * 处理请求URL
     * 封装鉴权参数等
     *
     * @return 处理后的URL
     */
    public String buildRequetUrl() {
        URL url = null;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://", "https://");
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());

            String host = url.getHost();
            if (url.getPort() != 80 && url.getPort() != 443) {
                host = host + ":" + String.valueOf(url.getPort());
            }
            StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
                    append("date: ").append(date).append("\n").//
                    append("POST ").append(url.getPath()).append(" HTTP/1.1");
            Charset charset = Charset.forName("UTF-8");
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

        } catch (Exception e) {
            throw new RuntimeException("assemble requestUrl error:" + e.getMessage());
        }
    }

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     *
     * @return 参数字符串
     */
    private String buildParam() throws IOException {
        String param = "{" +
                "    \"header\": {" +
                "        \"app_id\": \"" + APPID + "\"," +
                "        \"status\": 3" +
                "    }," +
                "    \"parameter\": {" +
                "        \"s782b4996\": {" +
                "            \"func\": \"searchFea\"," +
                //这里填上所需要的groupId
                "            \"groupId\": \""+XFYunConstant.GROUPID+"\","+
                //这里填写期望返回的个数,最大为10,且groupId要有足够特征才会返回
                "            \"topK\": 1," +
                "            \"searchFeaRes\": {" +
                "                \"encoding\": \"utf8\"," +
                "                \"compress\": \"raw\"," +
                "                \"format\": \"json\"" +
                "            }" +
                "        }" +
                "    }," +
                "\"payload\":{" +
                "    \"resource\": {" +
                //这里根据不同的音频编码填写不同的编码格式
                "        \"encoding\": \"lame\"," +
                "        \"sample_rate\": 16000," +
                "        \"channels\": 1," +
                "        \"bit_depth\": 16," +
                "        \"status\": " + 3 + "," +
                "        \"audio\": \""+Base64.getEncoder().encodeToString(read(AUDIO_PATH))+"\""+
                "    }}" +
                "}";
        return param;
    }

    /**
     * 读取流数据
     *
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while ((len = is.read(b)) != -1){
            sb.append(new String(b, 0, len, "utf-8"));
        }
        return sb.toString();
    }

    public static byte[] read(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] data = inputStream2ByteArray(in);
        in.close();
        return data;
    }
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }
    //Json解析
    class JsonParse {
        public Header header;
        public Payload payload;
    }
    class Header{
        public int code;
        public String message;
        public String sid;
        public int status;
    }
    class Payload{
        //根据model的取值不同,名字有所变动。
        public SearchFeaRes searchFeaRes;
    }
    class SearchFeaRes{
        public String compress;
        public String encoding;
        public String format;
        public String text;
    }
}