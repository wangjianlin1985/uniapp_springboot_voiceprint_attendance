package cn.ttitcn.framework.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *  API接口的鉴权拦截
 * 在cn.ttitcn.framework.config.ResourcesConfig配置拦截器
 */
@Component
@SuppressWarnings({"rawtypes","unchecked"})
public class ApiAuthInterceptor implements HandlerInterceptor{
    
	public RedisTemplate redisTemplate;
 	public RedisTemplate getRedisTemplate() {
 		return redisTemplate;
 	}
 	@Autowired
 	public void setRedisTemplate(RedisTemplate redisTemplate) {
 		 RedisSerializer stringSerializer = new StringRedisSerializer();
 		 redisTemplate.setKeySerializer(stringSerializer);
 		 this.redisTemplate = redisTemplate;
 	}
 
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	return true;
    }
    
    

}
