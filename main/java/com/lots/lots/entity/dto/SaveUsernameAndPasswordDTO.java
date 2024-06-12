package com.lots.lots.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * SaveUsernameAndPasswordDTO
 *
 * @author lots
 * @date 2022/4/19 16:57
 */
@Data
public class SaveUsernameAndPasswordDTO implements Serializable {
    private Long shopId;
    private String confirmPsw;
    private String password;
}
