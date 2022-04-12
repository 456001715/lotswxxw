package com.lots.lots.crypto.peoperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @name: CryptoProperties
 * @author: lots
 * @date: 2021/5/2 21:32
 */
@Data
@Component
@ConfigurationProperties(CryptoProperties.PREFIX)
public class CryptoProperties {
    public static final String PREFIX = "lots.request-encry-decry";
    /**
     * 是否开启
     */
    private Boolean enable = false;
    /**
     * 是否开启单例模式
     */
    private Boolean enableSingleton = false;
    /**
     * 加密类型 暂时支持aes与rsa模式
     */
    private String encryType;
}
