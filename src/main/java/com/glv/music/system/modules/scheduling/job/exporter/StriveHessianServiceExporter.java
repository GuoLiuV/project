package com.glv.music.system.modules.scheduling.job.exporter;

import com.glv.music.system.modules.exception.StriveException;
import com.glv.music.system.modules.scheduling.job.IStriveJob;
import com.glv.music.system.utils.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
public class StriveHessianServiceExporter extends HessianServiceExporter {

    private static final String TOKEN = "Basic c3RyaXZlX2hlc3NpYW46YmY3NTJiMjIxZTdhZDhlMWNjYjU1NzRhNjBlNjk2ZjFjNTQ4YjY3ZGUwNWZlZGQxNzNhNGQ1MTg=";

    public StriveHessianServiceExporter(IStriveJob job) {
        super.setServiceInterface(IStriveJob.class);
        super.setService(job);
    }

    @Override
    public void handleRequest(HttpServletRequest request,
                              @NonNull HttpServletResponse response)
            throws ServletException, IOException {
        String auth = request.getHeader("authorization");
        if (!StringUtils.equalsIgnoreCase(TOKEN, auth)) {
            throw new StriveException("Hessian过程调用token认证失败");
        }
        super.handleRequest(request, response);
    }
}
