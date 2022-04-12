package com.lots.lots.crypto.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.lots.lots.controller.auth.BaseController;
import com.lots.lots.crypto.entity.KeyExchange;
import com.lots.lots.common.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.Principal;
import java.util.concurrent.TimeUnit;

/**
 * @name: 加密接口功能controller
 * @author: lots
 * @date: 2021/5/6 11:10
 */
@RestController
@RequestMapping("lots/key/")
@Api(tags = "加密接口功能")
public class KeyController extends BaseController {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @ApiOperation(value = "获取密匙")
    @PostMapping(value = "/getKey")
    public JsonResult<KeyExchange> getKey(Principal principal, String url) {
        if (principal == null) {
            return JsonResult.unauthorized(null);
        }
        if (StrUtil.isEmpty(url)) {
            return JsonResult.failed("url不能为空");
        }
        KeyExchange keyExchange = new KeyExchange();
        String key = RandomUtil.randomString(16);
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        //转换为string
        String privateKeyString = java.util.Base64.getEncoder().encodeToString(privateKey);
        String publicKeyString = java.util.Base64.getEncoder().encodeToString(publicKey);
        keyExchange = new KeyExchange();
        keyExchange.setExchangeKey(key);
        keyExchange.setPrivateKey(privateKeyString);
        keyExchange.setPublicKey(publicKeyString);
        redisTemplate.opsForValue().set(url + principal.getName() + "keyExchange", keyExchange, 5, TimeUnit.SECONDS);
        return JsonResult.success(keyExchange);
    }
}
