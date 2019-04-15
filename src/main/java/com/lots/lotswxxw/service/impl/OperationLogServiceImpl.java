package com.lots.lotswxxw.service.impl;

import com.lots.lotswxxw.dao.AuthOperationLogMapper;
import com.lots.lotswxxw.domain.bo.AuthOperationLog;
import com.lots.lotswxxw.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author lots
 * @date 9:34 2018/4/22
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    AuthOperationLogMapper authOperationLogMapper;

    @Override
    public List<AuthOperationLog> getOperationList() {
        return authOperationLogMapper.selectOperationLogList();
    }
}
