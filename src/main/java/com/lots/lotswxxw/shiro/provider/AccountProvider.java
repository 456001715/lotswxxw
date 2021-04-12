package com.lots.lotswxxw.shiro.provider;


import com.lots.lotswxxw.domain.vo.Account;

/**
 * 数据库用户密码账户提供
 *
 * @author lots
 * @date 16:35 2018/2/11
 */
public interface AccountProvider {

    /**
     * description 数据库用户密码账户提供
     *
     * @param appId 1
     * @return Account
     */
    Account loadAccount(String appId);

}
