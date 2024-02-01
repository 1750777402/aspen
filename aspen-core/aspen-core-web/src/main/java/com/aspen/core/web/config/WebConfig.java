package com.aspen.core.web.config;

import com.aspen.core.foundation.utils.SpringContextUtil;
import com.aspen.core.web.request.RequestContextInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * @author jingchuan
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(
        basePackages = "com.aspen",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        value = Controller.class
                )
        }
)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor requestContextInterceptor = SpringContextUtil.getBean(RequestContextInterceptor.class);
        registry.addInterceptor(requestContextInterceptor);
        LocaleChangeInterceptor localChangeInterceptor = new LocaleChangeInterceptor();
        localChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localChangeInterceptor);
    }

}
