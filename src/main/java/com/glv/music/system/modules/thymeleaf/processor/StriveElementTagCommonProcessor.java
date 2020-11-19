package com.glv.music.system.modules.thymeleaf.processor;

import lombok.EqualsAndHashCode;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.context.WebEngineContext;
import org.thymeleaf.model.AttributeValueQuotes;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@EqualsAndHashCode(callSuper = false)
public class StriveElementTagCommonProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "common";
    private static final int PRECEDENCE = 1000;

    public StriveElementTagCommonProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML,
                dialectPrefix,
                TAG_NAME,
                true,
                null,
                false,
                PRECEDENCE);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler structureHandler) {

        // 获取应用程序的上下文参数
        WebEngineContext ctx = (WebEngineContext) context;
        final String contextPath = ctx.getServletContext().getContextPath();

        // 创建DOM元素模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        Map<String, String> attributes = new HashMap<>(2);
        attributes.put("rel", "stylesheet");
        attributes.put("href", contextPath + "/sys/css/style.css");
        model.add(modelFactory.createOpenElementTag("link", attributes,
                AttributeValueQuotes.DOUBLE, false));
        model.add(modelFactory.createCloseElementTag("link"));

        attributes = new HashMap<>(2);
        attributes.put("rel", "stylesheet");
        attributes.put("href", contextPath + "/app/css/style.css");
        model.add(modelFactory.createOpenElementTag("link", attributes,
                AttributeValueQuotes.DOUBLE, false));
        model.add(modelFactory.createCloseElementTag("link"));

        attributes = new HashMap<>(2);
        attributes.put("src", contextPath + "/sys/js/strive.js");
        model.add(modelFactory.createOpenElementTag("script", attributes,
                AttributeValueQuotes.DOUBLE, false));
        model.add(modelFactory.createCloseElementTag("script"));

        //将元素替换为指定的模型
        structureHandler.replaceWith(model, false);

    }
}
