package com.lots.lotswxxw.shiro.provider.impl;

import com.lots.lotswxxw.dao.AuthResourceMapper;
import com.lots.lotswxxw.shiro.provider.ShiroFilterRulesProvider;
import com.lots.lotswxxw.shiro.rule.RolePermRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lots
 * @date 16:46 2018/3/7
 */
@Service("ShiroFilterRulesProvider")
public class ShiroFilterRulesProviderImpl implements ShiroFilterRulesProvider {

    @Autowired
    private AuthResourceMapper authResourceMapper;

    @Override
    public List<RolePermRule> loadRolePermRules() {

        return authResourceMapper.selectRoleRules();
    }

}
