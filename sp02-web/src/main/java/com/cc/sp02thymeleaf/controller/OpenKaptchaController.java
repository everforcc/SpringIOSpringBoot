package com.cc.sp02thymeleaf.controller;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/open/kaptcha")
@Validated
@Slf4j
public class OpenKaptchaController {

    @Resource
    private Producer captchaProducer;

    @Resource(name = "captchaProducerTXT")
    private Producer captchaProducerTXT;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @GetMapping("/captchaProducer")
    public String captchaProducer() {
        String codeText = captchaProducer.createText();
        String captchaBase64 = "data:image/jpg;base64,";
        BufferedImage codeImage = captchaProducer.createImage(codeText);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(codeImage, "png", stream);
            captchaBase64 += new String(Base64.getEncoder().encode(stream.toByteArray()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("生成验证码{}", codeText);
        log.info("{}", captchaBase64);
        return captchaBase64;
    }

    @GetMapping("/captchaProducerTXT")
    public String captchaProducerTXT() {
        String codeText = captchaProducerTXT.createText();
        String captchaBase64 = "data:image/jpg;base64,";
        BufferedImage codeImage = captchaProducerTXT.createImage(codeText);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(codeImage, "png", stream);
            captchaBase64 += new String(Base64.getEncoder().encode(stream.toByteArray()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("生成验证码{}", codeText);
        log.info("{}", captchaBase64);
        return captchaBase64;
    }

    @GetMapping("/captchaProducerMath")
    public String captchaProducerMath() {
        String capText = captchaProducerMath.createText();
        String captchaBase64 = "data:image/jpg;base64,";

        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);

        BufferedImage codeImage = captchaProducerMath.createImage(capStr);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(codeImage, "png", stream);
            captchaBase64 += new String(Base64.getEncoder().encode(stream.toByteArray()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("生成验证码: {} ,结果: {}", capStr, code);
        log.info("{}", captchaBase64);
        return captchaBase64;
    }

}
