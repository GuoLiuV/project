package com.glv.project.system.modules.oauth2.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class ResourceAccessProcessingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        log.debug("这里可以配置用户所拥有的资源，如果该资源地址不可访问则拦截");
        chain.doFilter(request, response);
    }
}
