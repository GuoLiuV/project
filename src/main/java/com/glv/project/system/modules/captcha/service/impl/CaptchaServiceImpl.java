package com.glv.project.system.modules.captcha.service.impl;

import com.glv.project.system.modules.captcha.service.CaptchaService;
import com.glv.project.system.utils.ImageUtils;
import com.glv.project.system.utils.ObjectUtils;
import com.glv.project.system.utils.RandomStringUtils;
import com.glv.project.system.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ZHOUXIANG
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void getCaptchaImage() {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpSession session = request.getSession(true);
        Map<String, BufferedImage> map = ImageUtils.createImage();
        String captchaStr = null;
        for (String s : map.keySet()) {
            captchaStr = s;
        }
        assert response != null;
        response.setContentType("image/jpeg");
        try (
                ServletOutputStream out = response.getOutputStream()
        ) {
            session.setAttribute("captcha", captchaStr);
            ImageIO.write(map.get(captchaStr), "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean validCaptcha(String requestCaptcha) {
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        HttpSession session = request.getSession(true);
        String sessionCaptcha = String.valueOf(session.getAttribute("captcha"));
        return requestCaptcha.equalsIgnoreCase(sessionCaptcha);
    }

    @Override
    public void sendCaptchaCode(String mobile) {
        String code = RandomStringUtils.authCode(4);
        redisTemplate.opsForValue().set(mobile, code, 5, TimeUnit.MINUTES);
        log.info("短信验证码为：{}", code);
    }

    @Override
    public boolean validCaptchaCode(String mobile, String code) {
        String rcode = redisTemplate.opsForValue().get(mobile);
        return ObjectUtils.notNull(code) && StringUtils.equals(code, rcode);
    }
}
