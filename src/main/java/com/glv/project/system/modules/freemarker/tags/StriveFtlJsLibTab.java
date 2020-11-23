package com.glv.project.system.modules.freemarker.tags;

import com.glv.project.system.utils.RequestUtils;
import com.glv.project.system.utils.StringUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Component
public class StriveFtlJsLibTab implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws IOException {
        Writer out = env.getOut();
        String contextPath = RequestUtils.getContextPath();
        String name = String.valueOf(params.get("name"));
        if (StringUtils.isNotBlank(name)) {
            switch (name) {
                case "jquery":
                    out.write(String.format("<script type='text/javascript' " +
                            "src='%s/sys/js_lib/jquery/jquery-3.4.1.min.js'></script>", contextPath));
                    break;
                case "vue":
                    out.write(String.format("<script type='text/javascript' " +
                            "src='%s/sys/js_lib/vue/vue-2.6.10.min.js'></script>", contextPath));
                    break;
                case "element_ui":
                    out.append(String.format("<style rel='stylesheet' " +
                            "href='%s/sys/js_lib/element_ui/index.css'></style>", contextPath));
                    out.append(String.format("<script type='text/javascript' " +
                            "src='%s/sys/js_lib/element_ui/index.js'></script>", contextPath));
                    break;
                default:

            }
        }
    }

}
