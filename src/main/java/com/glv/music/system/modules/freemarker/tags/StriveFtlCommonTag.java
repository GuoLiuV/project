package com.glv.music.system.modules.freemarker.tags;

import com.glv.music.system.utils.RequestUtils;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Component
public class StriveFtlCommonTag implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        Writer out = env.getOut();
        String contextPath = RequestUtils.getContextPath();
        out.append(String.format("<style rel='stylesheet' href='%s/sys/css/style.css'></style>", contextPath));
        out.append(String.format("<style rel='stylesheet' href='%s/app/css/style.css'></style>", contextPath));
        out.append(String.format("<script type='text/javascript' src='%s/sys/js/strive.js'></script>", contextPath));
    }

}
