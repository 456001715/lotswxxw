package com.lots.lots.crypto.annotation;


import java.lang.annotation.*;

/**
 * 加密注解
 *
 * @author lots
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ResponseEncrypt {
    /**
     * 使用交换密钥加密
     */
    boolean enableExchangeKeyEncrypt() default true;
}
