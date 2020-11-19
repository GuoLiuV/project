package com.glv.music.system.modules.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 框架通用控制器
 *
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/browser")
public class InterceptorController {

    /**
     * 当浏览器版本不满足要求时，强制继续浏览
     */
    @RequestMapping("/browserAccessContinue")
    public String browserAccessContinue(HttpSession session, String viewName) {
        session.setAttribute("isContinue", "continue");
        return viewName;
    }
}
