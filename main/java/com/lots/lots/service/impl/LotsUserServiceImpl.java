package com.lots.lots.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.dao.mapper.LotsResourceMapper;
import com.lots.lots.dao.mapper.LotsUserLoginLogMapper;
import com.lots.lots.dao.mapper.LotsUserMapper;
import com.lots.lots.dao.mapper.LotsUserRoleRelationMapper;
import com.lots.lots.common.JsonResult;
import com.lots.lots.entity.vo.*;
import com.lots.lots.entity.vo.LotsUser.RegisterParam;
import com.lots.lots.entity.vo.LotsUser.UpdateParam;
import com.lots.lots.security.vo.AdminUserDetails;
import com.lots.lots.service.LotsSecurityService;
import com.lots.lots.service.LotsUserCacheService;
import com.lots.lots.service.LotsUserService;
import com.lots.lots.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.lots.lots.util.IpUtil.getIpAddress;

/**
 * 后台用户表(LotsUser)表服务实现类
 *
 * @author lots
 * @since 2021-04-28
 */
@Service
public class LotsUserServiceImpl implements LotsUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LotsUserServiceImpl.class);
    @Resource
    private LotsUserMapper lotsUserMapper;
    @Resource
    private LotsResourceMapper lotsResourceMapper;
    @Resource
    private LotsUserCacheService lotsUserCacheService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private LotsUserLoginLogMapper loginLogMapper;
    @Resource
    private LotsUserRoleRelationMapper userRoleRelationMapper;
    @Resource
    private LotsSecurityService lotsSecurityService;
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LotsUserVo findById(Long id) {
        return this.lotsUserMapper.findById(id);
    }

    /**
     * 新增数据
     *
     * @param lotsUser 实例对象
     * @return 实例对象
     */
    @Override
    public LotsUserVo insert(LotsUserVo lotsUser) {
        this.lotsUserMapper.insert(lotsUser);
        return lotsUser;
    }

    /**
     * 修改数据
     *
     * @param lotsUser 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(UpdateParam lotsUser) {
        LotsUserVo rawAdmin = lotsUserMapper.findById(lotsUser.getId());
        if (rawAdmin.getPassword().equals(lotsUser.getPassword())) {
            //与原加密密码相同的不需要修改
            lotsUser.setPassword(null);
        } else {
            //与原加密密码不同的需要加密修改
            if (StrUtil.isEmpty(lotsUser.getPassword())) {
                lotsUser.setPassword(null);
            } else {
                lotsUser.setPassword(passwordEncoder.encode(lotsUser.getPassword()));
            }
        }
        LotsUserVo updateVo = new LotsUserVo();

        BeanUtils.copyProperties(lotsUser,updateVo);
        int count = lotsUserMapper.update(updateVo);
        lotsUserCacheService.delAdmin(lotsUser.getUsername());
        return count;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.lotsUserMapper.deleteById(id) > 0;
    }

    @Override
    public List<LotsResourceVo> getResourceList(Long userId) {
        List<LotsResourceVo> resourceList = lotsUserCacheService.getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            return resourceList;
        }
        resourceList = lotsResourceMapper.getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            lotsUserCacheService.setResourceList(userId, resourceList);
        }
        return resourceList;
    }

    @Override
    public LotsUserVo getAdminByUsername(String username) {
        LotsUserVo admin = lotsUserCacheService.getAdmin(username);
        if (admin != null) {
            return admin;
        }
        LotsUserVo adminVo = lotsUserMapper.getAdminByUsername(username);
        if (adminVo != null) {
            lotsUserCacheService.setAdmin(adminVo);
            return adminVo;
        }
        return null;
    }


    @Override
    public JsonResult register(RegisterParam registerParam) {
        LotsUserVo lotsUser = new LotsUserVo();
        BeanUtils.copyProperties(registerParam, lotsUser);
        lotsUser.setCreateTime(new Date());
        lotsUser.setStatus(1);
        //查询是否有相同用户名的用户
        LotsUserVo uniqueLotsUser = new LotsUserVo();
        uniqueLotsUser.setUsername(registerParam.getUsername());
        List<LotsUserVo> userNameVos = lotsUserMapper.queryAll(uniqueLotsUser);
        if (CollUtil.isNotEmpty(userNameVos)) {
            return JsonResult.failed("该帐号已被注册，请更换注册账号");
        }
        if (StrUtil.isNotEmpty(lotsUser.getEmail())) {
            uniqueLotsUser.setUsername(null);
            uniqueLotsUser.setEmail(null);
            uniqueLotsUser.setEmail(lotsUser.getEmail());
            List<LotsUserVo> emailVos = lotsUserMapper.queryAll(uniqueLotsUser);
            if (CollUtil.isNotEmpty(emailVos)) {
                return JsonResult.failed("该邮箱已被注册，请更换注册邮箱");
            }
        }
        if (StrUtil.isNotEmpty(lotsUser.getPhone())) {
            uniqueLotsUser.setUsername(null);
            uniqueLotsUser.setEmail(null);
            uniqueLotsUser.setPhone(lotsUser.getPhone());
            List<LotsUserVo> phoneVos = lotsUserMapper.queryAll(uniqueLotsUser);
            if (CollUtil.isNotEmpty(phoneVos)) {
                return JsonResult.failed("该手机号已被注册，请更换注册手机号");
            }
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(lotsUser.getPassword());
        lotsUser.setPassword(encodePassword);
        lotsUserMapper.insert(lotsUser);
        return JsonResult.success("注册成功");
    }

    @Override
    public String login(String username, String password) {
        String token = null;
        //密码需要客户端加密后传递
        try {
            UserDetails userDetails = lotsSecurityService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
//            updateLoginTimeByUsername(username);
            insertLoginLog(username);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    @Override
    public String refreshToken(String oldToken) {
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public List<LotsRoleVo> getRoleList(Long userId) {
        return userRoleRelationMapper.getRoleList(userId);
    }

    @Override
    public IPage<LotsUserVo> list(String keyword, Integer pageSize, Integer pageNum) {
        IPage<LotsUserVo> list = lotsUserMapper.findByNamePage(new Page(pageNum,pageSize),keyword);
        return list;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        LotsUserVo lotsUser = lotsUserMapper.getAdminByUsername(param.getUsername());
        if (lotsUser == null) {
            return -2;
        }
        if (!passwordEncoder.matches(param.getOldPassword(), lotsUser.getPassword())) {
            return -3;
        }
        lotsUser.setPassword(passwordEncoder.encode(param.getNewPassword()));
        lotsUserMapper.updateByPrimaryKey(lotsUser);
        lotsUserCacheService.delAdmin(lotsUser.getUsername());
        return 1;
    }

    @Override
    public int delete(Long id) {
        final LotsUserVo byId = lotsUserMapper.findById(id);
        lotsUserCacheService.delAdmin(byId.getUsername());
        int count = lotsUserMapper.deleteById(id);
        lotsUserCacheService.delResourceList(id);
        return count;
    }

    @Override
    public int updateRole(Long userId, List<Long> roleIds) {
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的关系
        userRoleRelationMapper.deleteByUserId(userId);
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<LotsUserRoleRelationVo> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                LotsUserRoleRelationVo roleRelation = new LotsUserRoleRelationVo();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            userRoleRelationMapper.insertList(list);
        }
        lotsUserCacheService.delResourceList(userId);
        return count;
    }


    /**
     * 添加登录记录
     *
     * @param username 用户名
     */
    private void insertLoginLog(String username) {
        LotsUserVo admin = getAdminByUsername(username);
        if (admin == null) {
            return;
        }
        LotsUserLoginLogVo loginLog = new LotsUserLoginLogVo();
        loginLog.setUserId(admin.getId());
        loginLog.setCreateTime(new Date());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        loginLog.setIp(getIpAddress(request));
        loginLogMapper.insert(loginLog);
    }

    /**
     * 将+-权限关系转化为对象
     */
    private List<LotsUserPermissionRelationVo> convert(Long userId, Integer type, List<Long> permissionIdList) {
        List<LotsUserPermissionRelationVo> relationList = permissionIdList.stream().map(permissionId -> {
            LotsUserPermissionRelationVo relation = new LotsUserPermissionRelationVo();
            relation.setUserId(userId);
            relation.setType(type);
            relation.setPermissionId(permissionId);
            return relation;
        }).collect(Collectors.toList());
        return relationList;
    }
}