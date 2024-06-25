package com.lots.lots.controller.lotswxxw;

import cn.dev33.satoken.annotation.SaIgnore;
import com.lots.lots.common.BaseController;
import com.lots.lots.common.CaptChaiPo;
import com.lots.lots.common.CaptchaVo;
import com.lots.lots.common.R;
import com.lots.lots.common.redissontool.annotation.FrequencyControl;
import com.lots.lots.common.validation.Delete;
import com.lots.lots.crypto.annotation.ResponseEncrypt;
import com.lots.lots.util.CaptchaUtils;
import com.lots.lots.util.ServletUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @name: TestController
 * @author: lots
 * @date: 2021/4/29 16:15
 */
@Tag(name = "测试相关接口")
@RestController
@SaIgnore
@RequestMapping("test")
public class TestController extends BaseController {
    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/redis")
    @Operation(summary ="测试加密")
    @ResponseEncrypt
    public R redis(String value) throws Exception {
        String falseString = "1";
        if (falseString.equals(value)) {
            throw new Exception("lalalaal");
        }
        redisTemplate.opsForValue().set(value, value, 5, TimeUnit.SECONDS);
        return R.success(R.failed("这是加密的"));
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

    //todo lots 参数校验未成功
    /**
     * 参数校验
     */
    @PostMapping(value = "validated")
    @SaIgnore
    @FrequencyControl(time = 100, count = 3, target = FrequencyControl.Target.IP)
    @Operation(summary ="测试参数校验")
    public R validated(@Validated(Delete.class) @RequestBody TestModel testModel) {
        return R.success("成功了");
    }

    @GetMapping(value = "lalala")
    @FrequencyControl(time = 100, count = 3, target = FrequencyControl.Target.IP)
    @Operation(summary ="限流测试")
    public R lalala(String aszz) {
//        throw new ApiException("123123");
        return R.success("限流测试");
    }

}
