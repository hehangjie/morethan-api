package com.morethan.game.interceptor;

import com.morethan.game.authorization.SignCheck;
import com.morethan.game.config.EnvConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by jason on 2017/12/14.
 */
@Component
@EnableConfigurationProperties(EnvConfig.class)
public class SignCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        if(EnvConfig.getNeedSign()){
            //如果不是映射到方法直接通过
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            //注明SignCheck校验签名
            if (method.getAnnotation(SignCheck.class) != null) {
                //TODO: 进行签名校验
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletResponse.setHeader("ome", "3");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
