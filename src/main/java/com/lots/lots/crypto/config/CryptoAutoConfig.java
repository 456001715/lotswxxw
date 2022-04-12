package com.lots.lots.crypto.config;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.lots.lots.crypto.entity.KeyExchange;
import com.lots.lots.crypto.peoperties.CryptoProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;

/**
 * @name: CryptoAutoConfig
 * @author: lots
 * @date: 2021/5/2 21:31
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CryptoProperties.class})
public class CryptoAutoConfig {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private void init(CryptoProperties cryptoProperties) {

        Boolean enable = cryptoProperties.getEnable();
        if (enable && cryptoProperties.getEnableSingleton()) {
            KeyExchange keyExchange = (KeyExchange) redisTemplate.opsForValue().get("keyExchange:Singleton");
            if (keyExchange == null) {
                KeyPair pair = SecureUtil.generateKeyPair("RSA");
                byte[] privateKey = pair.getPrivate().getEncoded();
                byte[] publicKey = pair.getPublic().getEncoded();
                //转换为string
                String key = RandomUtil.randomString(16);
                String privateKeyString = java.util.Base64.getEncoder().encodeToString(privateKey);
                String publicKeyString = java.util.Base64.getEncoder().encodeToString(publicKey);
                keyExchange = new KeyExchange();
                keyExchange.setExchangeKey(key);
                keyExchange.setPrivateKey(privateKeyString);
                keyExchange.setPublicKey(publicKeyString);
                redisTemplate.opsForValue().set("keyExchange:Singleton", keyExchange);
                Singleton.put(SecureUtil.aes(key.getBytes(StandardCharsets.UTF_8)));
                Singleton.put(SecureUtil.rsa(privateKeyString, publicKeyString));
            }
            //转换为byte
            try {
                byte[] bytes = java.util.Base64.getDecoder().decode(keyExchange.getExchangeKey().toString());
            } catch (Exception e) {

            }
            log.info("【初始化工具-SecureSingleton】单例配置 ... 已初始化完毕。");

        }


        /*if (StrUtil.isNotEmpty(aes_keyt)) {

            log.info("【初始化工具-SecureSingleton】AES单例配置 ... 已初始化完毕。");
        }

        // RSA
        String rsa_public_keyt = cryptoProperties.getRsaPublicKeyt();
        String rsa_private_keyt = cryptoProperties.getRsaPrivateKeyt();
        if (StringUtils.isNotEmpty(rsa_public_keyt) || StringUtils.isNotEmpty(rsa_private_keyt)) {
            Singleton.put(SecureUtil.rsa(rsa_private_keyt, rsa_public_keyt));
            log.info("【初始化工具-SecureSingleton】RSA单例配置 ... 已初始化完毕。");
        }*/
    }
}
