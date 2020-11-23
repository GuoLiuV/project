package com.glv.project.system.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Spring 表达式工具类
 *
 * @author ZHOUXIANG
 */
@Slf4j
public class SpElUtils {

    /**
     * 解析并返回Spring表达式的值
     */
    public static <T> T parseExpressionValue(String expr, Map<String, Object> variables, Class<T> type) {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        variables.forEach(context::setVariable);
        Expression expression = parser.parseExpression(
                expr, new TemplateParserContext());
        return expression.getValue(context, type);
    }

}
