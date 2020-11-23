package com.glv.project.system.modules.web.interceptor;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ZHOUXIANG
 */
@SuppressWarnings("all")
public class UserAgentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        String isContinue;
        //此处的异常会在Response返回后创建Session时发生的。
        try {
            HttpSession session = request.getSession(true);
            isContinue = (String) session.getAttribute("isContinue");
        } catch (Exception e) {
            isContinue = "";
        }
        if (!"continue".equalsIgnoreCase(isContinue)) {
            if (modelAndView != null) {
                String viewName = modelAndView.getViewName();
                modelAndView.addObject("viewName", viewName);
            }
            UserAgent userAgent = new UserAgent(request.getHeader("user-agent"));
            Browser browser = userAgent.getBrowser();
            Version browserVersion = userAgent.getBrowserVersion();
            if (browser.name().contains("FIREFOX")) {
                Version v = new Version("3", "6", "0");
                if (v.compareTo(browserVersion) > 0) {
                    modelAndView.addObject("userAgent", userAgent);
                    modelAndView.setViewName("browser_error");
                }
            } else if (browser.name().contains("CHROME")) {
                Version v = new Version("4", "0", "0");
                if (v.compareTo(browserVersion) > 0) {
                    modelAndView.addObject("userAgent", userAgent);
                    modelAndView.setViewName("browser_error");
                }
            } else if (browser.name().contains("IE")) {
                Version v = new Version("9", "0", "0");
                if (v.compareTo(browserVersion) > 0) {
                    modelAndView.addObject("userAgent", userAgent);
                    modelAndView.setViewName("browser_error");
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 未使用
    }

}
