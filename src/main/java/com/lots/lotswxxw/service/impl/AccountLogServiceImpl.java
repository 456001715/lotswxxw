package com.lots.lotswxxw.service.impl;

import com.lots.lotswxxw.dao.AuthAccountLogMapper;
import com.lots.lotswxxw.domain.bo.AuthAccountLog;
import com.lots.lotswxxw.service.AccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lots
 * @date 9:32 2018/4/22
 */
@Service
public class AccountLogServiceImpl implements AccountLogService {

    @Autowired
    AuthAccountLogMapper authAccountLogMapper;

    @Override
    public List<AuthAccountLog> getAccountLogList() {
        return authAccountLogMapper.selectAccountLogList();
    }
}
