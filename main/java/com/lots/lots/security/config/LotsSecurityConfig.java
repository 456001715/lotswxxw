package com.lots.lots.security.config;

import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.security.component.DynamicSecurityService;
import com.lots.lots.service.LotsResourceService;
import com.lots.lots.service.LotsSecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mall-security模块相关配置
 *
 * @author lots
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class LotsSecurityConfig extends SecurityConfig {

    @Resource
    private LotsSecurityService loadUserByUsername;

    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> loadUserByUsername.loadUserByUsername(username);
    }




}
