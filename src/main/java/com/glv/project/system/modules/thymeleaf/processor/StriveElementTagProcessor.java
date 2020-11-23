package com.glv.project.system.modules.thymeleaf.processor;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@EqualsAndHashCode(callSuper = false)
public class StriveElementTagProcessor extends AbstractElementTagProcessor {

    private static final String TAG_NAME = "import";
    private static final int PRECEDENCE = 1000;

    public StriveElementTagProcessor(final String dialectPrefix) {
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

        // 获取导入js库的名称
        final String name = tag.getAttributeValue("lib");

        // 创建引入js库的DOM元素模型
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        Map<String, String> attributesScript;
        Map<String, String> attributesLink;

        List<Map<String, String>> scripts = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();

        switch (name) {
            case "jquery":
                attributesScript = new HashMap<>(1);
                attributesScript.put("src", String.format("%s/sys/js_lib/jquery/jquery-3.4.1.min.js", contextPath));
                scripts.add(attributesScript);
                break;
            case "vue":
                attributesScript = new HashMap<>(1);
                attributesScript.put("src", String.format("%s/sys/js_lib/vue/vue-2.6.10.min.js", contextPath));
                scripts.add(attributesScript);
                break;
            case "element_ui":
                //添加样式
                attributesLink = new HashMap<>(1);
                attributesLink.put("href", String.format("%s/sys/js_lib/element_ui/index.css", contextPath));
                links.add(attributesLink);
                //添加js
                attributesScript = new HashMap<>(1);
                attributesScript.put("src", String.format("%s/sys/js_lib/element_ui/index.js", contextPath));
                scripts.add(attributesScript);
                break;
            default:
        }

        if (!links.isEmpty()) {
            for (Map<String, String> attr : links) {
                attr.put("rel", "stylesheet");
                model.add(modelFactory.createOpenElementTag("link", attr,
                        AttributeValueQuotes.DOUBLE, false));
                model.add(modelFactory.createCloseElementTag("link"));
            }
        }

        if (!scripts.isEmpty()) {
            for (Map<String, String> attr : scripts) {
                model.add(modelFactory.createOpenElementTag("script", attr,
                        AttributeValueQuotes.DOUBLE, false));
                model.add(modelFactory.createCloseElementTag("script"));
            }
        }

        //将元素替换为指定的模型
        structureHandler.replaceWith(model, false);

    }
}
