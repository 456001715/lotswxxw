package com.lots.lots.controller.lotswxxw;

import com.lots.lots.common.validation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * TestModel
 *
 * @author lots
 * @date 2022/3/24 11:26
 */
@Data
public class TestModel {
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
