package com.glv.music.system.modules.captcha.controller;

import com.glv.music.system.modules.captcha.service.CaptchaService;
import com.glv.music.system.modules.request.annotation.RestGetMapping;
import com.glv.music.system.modules.response.dto.ResponseStatusDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author ZHOUXIANG
 */
@Controller
@RequestMapping("/sys/captcha")
@Api("验证码")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @ApiOperation("获取图片验证码")
    @RestGetMapping("/image")
    public ResponseStatusDto<String> getCaptchaImage() {
        captchaService.getCaptchaImage();
        return ResponseStatusDto.success();
    }

    @ApiOperation("获取短信验证码")
    @RestGetMapping("/sendCode/{mobile}")
    public ResponseStatusDto<String> sendCaptchaCode(
            @PathVariable String mobile) {
        captchaService.sendCaptchaCode(mobile);
        return ResponseStatusDto.success();
    }

}
