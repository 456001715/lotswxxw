package com.lots.lots.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lots.lots.util.CaptchaUtils;
import com.lots.lots.util.ServletUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图片验证码VO，可直接将图片验证码写入响应结果中
 *
 * @author: lots
 * @date: 2021/5/11 12:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaVo {

    /**
     * 验证码
     */
    String captcha;
    /**
     * 验证码图片
     */
    BufferedImage captchaImage;

    /**
     * {@linkplain #captcha} set session
     * <p>{@linkplain #captchaImage} write response
     * <p>将验证码设置到session
     * <p>将验证码图片写入response，并设置ContentType为image/png
     */
    public void writeResponseAndSetSession() {
        HttpSession httpSession = ServletUtils.getSession();
        HttpServletResponse response = ServletUtils.getResponse();

        httpSession.setAttribute(CaptchaUtils.CAPTCHA_KEY, captcha);
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

}
