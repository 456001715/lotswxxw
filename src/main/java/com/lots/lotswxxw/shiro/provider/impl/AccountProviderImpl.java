package com.lots.lotswxxw.shiro.provider.impl;


import com.lots.lotswxxw.domain.vo.Account;
import com.lots.lotswxxw.service.AccountService;
import com.lots.lotswxxw.shiro.provider.AccountProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author lots
 * @date 9:22 2018/2/13
 */
@Service("AccountProvider")
public class AccountProviderImpl implements AccountProvider {

      @Autowired
      @Qualifier("AccountService")
      private AccountService accountService;

    @Override
    public Account loadAccount(String appId) {
        return accountService.loadAccount(appId);
    }
}
