package com.lots.lots.security.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务类
 *
 * @author lots
 */
public interface DynamicSecurityService {
    /**
     * 加载资源ANT通配符和资源对应MAP
     *
     * @author: lots
     * @return: java.util.Map<java.lang.String, org.springframework.security.access.ConfigAttribute>
     * @date: 2021/4/29 13:55
     */
    Map<String, ConfigAttribute> loadDataSource();
}
