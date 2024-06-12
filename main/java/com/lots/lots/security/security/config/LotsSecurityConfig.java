package com.lots.lots.security.security.config;

import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.security.component.DynamicSecurityService;
import com.lots.lots.security.config.SecurityConfig;
import com.lots.lots.service.LotsResourceService;
import com.lots.lots.service.LotsUserService;
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
    private LotsUserService lotsUserService;
    @Resource
    private LotsResourceService resourceService;

    @Override
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> lotsUserService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String, ConfigAttribute> map = new ConcurrentHashMap<>(20);
                List<LotsResourceVo> resourceList = resourceService.listAll();
                for (LotsResourceVo resource : resourceList) {
                    map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
                }
                return map;
            }
        };
    }
}
