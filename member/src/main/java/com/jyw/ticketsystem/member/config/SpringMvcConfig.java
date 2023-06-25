package com.jyw.ticketsystem.member.config;

import com.jyw.ticketsystem.common.interceptor.LogInterceptor;
import com.jyw.ticketsystem.common.interceptor.MemberInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    LogInterceptor logInterceptor;

    @Resource
    MemberInterceptor memberInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       //拦截器也是有顺序de——————————
       registry.addInterceptor(logInterceptor);
       // 路径不能包含context-path
       // 添加一个拦截器
       registry.addInterceptor(memberInterceptor)
               .addPathPatterns("/**")
               .excludePathPatterns(
                       "/hello",
                       "/member/send-code",
                       "/member/login"
               );
   }
}
