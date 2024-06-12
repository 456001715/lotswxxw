package com.lots.lots.crypto.annotation;

/**
 * @name: ResponseEncryptHandler
 * @author: lots
 * @date: 2021/5/6 14:56
 */

import cn.hutool.crypto.SecureUtil;
import com.lots.lots.crypto.entity.KeyExchange;
import com.lots.lots.common.JsonResult;
import com.lots.lots.security.vo.AdminUserDetails;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.lots.lots.util.IntFaceEncryptUtils.encryptByKey;

/**
 * 响应加密处理器
 *
 * @author ylyue
 * @since 2020年9月18日
 */
@ControllerAdvice
@ConditionalOnClass(HttpServletRequest.class)
public class ResponseEncryptHandler<T> implements ResponseBodyAdvice<T> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(ResponseEncrypt.class) && returnType.getMethod().getReturnType() == JsonResult.class;
    }

    @Override
    public T beforeBodyWrite(T body, MethodParameter returnType, MediaType selectedContentType,
                             Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                             ServerHttpResponse response) {
        // NULL值与错误Result不做加密处理
        if (body == null) {
            return body;
        }
        JsonResult<Object> jsonResult = (JsonResult<Object>) body;
        Integer five = 500;
        if (jsonResult.getCode().equals(five)) {
            return body;
        }

        // 根据注解参数创建对应的加密算法实例
        ResponseEncrypt methodAnnotation = returnType.getMethodAnnotation(ResponseEncrypt.class);
        boolean enableExchangeKeyEncrypt = methodAnnotation.enableExchangeKeyEncrypt();
        if (enableExchangeKeyEncrypt == true) {
            AdminUserDetails user = (AdminUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            KeyExchange keyExchange = (KeyExchange) redisTemplate.opsForValue()
                    .get(request.getURI().getPath() + user.getUsername() + "keyExchange");
            if (keyExchange == null) {
                jsonResult.setCode(500);
                jsonResult.setMsg("请先请求加密接口");
                jsonResult.setData(null);
                return body;
            }
            try {
                jsonResult.setData(encryptByKey(jsonResult.getData().toString(), keyExchange.getExchangeKey()));
                SecureUtil.aes();
            } catch (Exception e) {
                jsonResult.setCode(500);
                jsonResult.setMsg("请重新请求加密接口");
                jsonResult.setData(null);
                return body;
            }
        }

        return body;
    }

}
