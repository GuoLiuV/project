package com.glv.project.system.utils;

import com.glv.project.system.modules.exception.StriveException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("unused")
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

    /**
     * <p>判断对象是否不为空</p>
     */
    public static boolean notNull(Object object) {
        return Objects.nonNull(object);
    }

    /**
     * <p>判断对像是否为空</p>
     */
    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    /**
     * 获取切面中的方法上下文
     */
    public static Map<String, Object> getAopMethodContext(ProceedingJoinPoint proceedingJoinPoint) {
        //获取方法签名，参数名和参数值
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] params = methodSignature.getParameterNames();
        //构建Spring表达式参数
        Map<String, Object> variables = new HashMap<>(args.length);
        for (int i = 0; i < args.length; i++) {
            variables.put(params[i], args[i]);
        }
        return variables;
    }

    /**
     * 为空异常
     */
    public static void requireNonNull(Object obj, String message) {
        if (Objects.isNull(obj)) {
            throw new StriveException(message);
        }
    }

}
