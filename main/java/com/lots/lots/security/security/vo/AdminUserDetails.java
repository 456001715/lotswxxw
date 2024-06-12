package com.lots.lots.security.security.vo;

import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsUserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 *
 * @author lots
 */
public class AdminUserDetails implements UserDetails {
    private LotsUserVo lotsUser;
    private List<LotsResourceVo> resourceList;

    public AdminUserDetails(LotsUserVo lotsUser, List<LotsResourceVo> resourceList) {
        this.lotsUser = lotsUser;
        this.resourceList = resourceList;
    }

    public AdminUserDetails(LotsUserVo umsAdmin) {
        this.lotsUser = lotsUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return lotsUser.getPassword();
    }

    @Override
    public String getUsername() {
        return lotsUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return lotsUser.getStatus().equals(1);

    }
}