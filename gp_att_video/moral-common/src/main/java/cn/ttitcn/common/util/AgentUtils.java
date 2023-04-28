package cn.ttitcn.common.util;

import javax.servlet.http.HttpServletRequest;

public class AgentUtils {

    /**
     * 是否是手机端登录
     * @param request
     * @return
     */
    public static boolean isMobile(HttpServletRequest request) {
        String requestHeader = request.getHeader("user-agent").toLowerCase();
        return requestHeader.indexOf("android")>=0;
    }
    
    
}
