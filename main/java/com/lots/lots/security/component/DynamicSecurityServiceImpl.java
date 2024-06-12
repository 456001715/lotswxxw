package com.lots.lots.security.component;

import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.service.LotsResourceService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DynamicSecurityServiceImpl
 *
 * @author lots
 * @date 2022/4/1 14:49
 */
@Service
public class DynamicSecurityServiceImpl implements DynamicSecurityService{
    @Resource
    private LotsResourceService resourceService;

    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>(20);
        List<LotsResourceVo> resourceList = resourceService.listAll();
        for (LotsResourceVo resource : resourceList) {
            map.put(resource.getUrl(), new org.springframework.security.access.SecurityConfig(resource.getId() + ":" + resource.getName()));
        }
        return map;
    }
}
