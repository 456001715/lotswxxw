package com.lots.lots.crypto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 密钥交换过程传输变量定义
 *
 * @author lots
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyExchange implements Serializable {

    private static final long serialVersionUID = -6260130048712948980L;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 最终交换的对称加密密钥
     */
    private String exchangeKey;

}
