package com.glv.project.system.modules.web.component;

import com.glv.project.system.utils.ObjectUtils;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p>获取Spring上下文中的Bean组件</p>
 *
 * @author ZHOUXIANG
 */
@Component
@Data
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isNull(SpringContextHolder.applicationContext)) {
            SpringContextHolder.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requireType) {
        return applicationContext.getBean(name, requireType);
    }

    public static <T> T getBean(Class<T> requireType) {
        return applicationContext.getBean(requireType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> requireType) {
        return applicationContext.getBeansOfType(requireType);
    }

    public static String[] getBeanNamesOfType(Class<T> requireType) {
        return applicationContext.getBeanNamesForType(requireType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    public static String[] getBeanNamesWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeanNamesForAnnotation(annotationType);
    }
}
