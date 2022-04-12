package com.lots.lots.crypto.config;

import lombok.Getter;

/**
 * EncryType
 *
 * @author: lots
 * @date: 2021/5/5 16:33
 */
@Getter
public enum EncryType {
    /**
     * EncryType
     *
     * @author: lots
     * @param null:
     * @return: null
     * @date: 2020/12/4 15:01
     */
    AES("aes", "aes"),
    RSA("rsa", "rsa"),
    AESRSA("aesRsa", "aesRsa");
    private String name;
    private String code;

    EncryType(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
