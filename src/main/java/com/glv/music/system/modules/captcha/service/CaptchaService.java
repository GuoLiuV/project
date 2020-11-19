package com.glv.music.system.modules.captcha.service;

/**
 * @author ZHOUXIANG
 */
public interface CaptchaService {

    /**
     * 获取图片验证码
     */
    void getCaptchaImage();

    /**
     * 验证请求的验证码是否正确
     *
     * @param requestCaptcha 请求验证码
     * @return 是否正确
     */
    boolean validCaptcha(String requestCaptcha);

    /**
     * 发送短信验证码
     *
     * @param mobile 手机号
     */
    void sendCaptchaCode(String mobile);

    /**
     * 验证短信验证码
     *
     * @param mobile 手机号
     * @param code   请求的验证码
     * @return 是否验证通过
     */
    boolean validCaptchaCode(String mobile, String code);
}
