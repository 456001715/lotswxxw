package com.lots.lots.controller.lotswxxw;

import com.lots.lots.common.validation.*;
import com.lots.lots.crypto.annotation.ResponseEncrypt;
import com.lots.lots.common.CaptChaiPo;
import com.lots.lots.common.CaptchaVo;
import com.lots.lots.common.JsonResult;
import com.lots.lots.util.CaptchaUtils;
import com.lots.lots.util.ServletUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @name: TestController
 * @author: lots
 * @date: 2021/4/29 16:15
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/redis")
    public JsonResult redis(String value) throws Exception {
        String falseString = "1";
        if (falseString.equals(value)) {
            throw new Exception("lalalaal");
        }
        redisTemplate.opsForValue().set(value, value, 5, TimeUnit.SECONDS);
        return JsonResult.success(JsonResult.failed("这是加密的"));
    }

    /**
     * 图形验证码
     */
    @GetMapping(value = "/img")
    @ResponseEncrypt
    public void img(String value) throws Exception {
        // 1. 创建图片验证码
        CaptchaVo captchaVo = CaptchaUtils.createCaptchaImage(CaptChaiPo.builder().build());
        String captcha = captchaVo.getCaptcha();
        BufferedImage captchaImage = captchaVo.getCaptchaImage();

        // 2. 设置验证码到Redis
        String captchaRedisKey = String.format(CaptchaUtils.CAPTCHA_REDIS_PREFIX, captcha);
        redisTemplate.opsForValue().set(captchaRedisKey, captcha, 6, TimeUnit.SECONDS);
        // 3. 设置验证码到响应输出流
        HttpServletResponse response = ServletUtils.getResponse();
        response.setContentType("image/png");
        OutputStream output;
        try {
            output = response.getOutputStream();
            // 响应结束时servlet会自动将output关闭
            ImageIO.write(captchaImage, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 参数校验
     */
    @PostMapping(value = "validated")
    public JsonResult Validated (@Validated(Selete.class) @RequestBody TestModel testModel ){
        return JsonResult.success("成功了");
    }

}
