package com.lots.lots.service;

import com.lots.lots.entity.vo.LotsResourceVo;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * LotsSecurityService
 *
 * @author lots
 * @date 2022/4/1 14:39
 */
public interface LotsSecurityService {

    /**
     * 登录
     *
     * @param username:用户名
     * @author: lots
     * @return: org.springframework.security.core.userdetails.UserDetails
     * @date: 2021/4/28 17:27
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 获取全部资源
     *
     * @param userId
     * @author: lots
     * @return: List
     * @date: 2021/4/28 17:40
     */
    List<LotsResourceVo> getResourceList(Long userId);
}
