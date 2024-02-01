package com.aspen.core.foundation.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;


/**
 * @author jingchuan
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> Collection<T> getBeans(Class<T> beanClass){
        return context.getBeansOfType(beanClass).values();
    }


    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType){
        return context.getBeansWithAnnotation(annotationType);
    }

    public static String getAppName() {
       return context.getApplicationName();
    }


}