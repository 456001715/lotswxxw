package com.lots.lotswxxw.service;

import com.lots.lotswxxw.domain.bo.AuthOperationLog;

import java.util.List;

/**
 * @author lots
 * @date 9:30 2018/4/22
 */
public interface OperationLogService {

    /**
     * description
     *
     * @return java.util.List<AuthOperationLog>
     */
    List<AuthOperationLog> getOperationList();
}
