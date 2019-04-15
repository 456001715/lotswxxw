package com.lots.lotswxxw.service;

import com.lots.lotswxxw.domain.bo.AuthUser;
import com.lots.lotswxxw.domain.vo.Account;

/**
 * @author lots
 * @date 22:02 2018/3/7
 */
public interface AccountService {

    /**
     * description TODO
     *
     * @param appId 1
     * @return Account
     */
    Account loadAccount(String appId);
    /**
     * description TODO
     *
     * @param uid 1
     * @return boolean
     */
    boolean isAccountExistByUid(String uid);
    /**
     * description TODO
     *
     * @param account 1
     * @return boolean
     */
    boolean registerAccount(AuthUser account);
    /**
     * description TODO
     *
     * @param appId 1
     * @return java.lang.String
     */
    String loadAccountRole(String appId);
}
