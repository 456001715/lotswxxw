package com.lots.lots.controller.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.common.JsonPage;
import com.lots.lots.common.JsonResult;
import com.lots.lots.common.Paging;
import com.lots.lots.entity.vo.LotsMenuVo;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsRoleVo;
import com.lots.lots.service.LotsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台用户角色管理
 *
 * @author lots
 */
@RestController
@Api(tags = "后台用户角色管理")
@RequestMapping("/role")
public class LotsRoleController {
    @Resource
    private LotsRoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody LotsRoleVo role) {
        int count = roleService.create(role);
        if (count > 0) {
            return JsonResult.success(count);
        }
        return JsonResult.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update/{id}")
    public JsonResult update(@PathVariable Long id, @RequestBody LotsRoleVo role) {
        role.setId(id);
        int count = roleService.update(role);
        if (count > 0) {
            return JsonResult.success(count);
        }
        return JsonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    public JsonResult delete(@RequestParam("ids") String ids) {
        int count = roleService.deleteByIds(ids);
        if (count > 0) {
            return JsonResult.success(count);
        }
        return JsonResult.failed();
    }


    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    public JsonResult<List<LotsRoleVo>> listAll() {
        List<LotsRoleVo> roleList = roleService.findAll();
        return JsonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public JsonResult<Paging<LotsRoleVo>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        IPage<LotsRoleVo> roleList = roleService.pageList(keyword, pageSize, pageNum);
        return JsonResult.success(Paging.buildPaging(roleList));
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus/{id}")
    public JsonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        LotsRoleVo lotsRoleVo = new LotsRoleVo();
        lotsRoleVo.setId(id);
        lotsRoleVo.setStatus(status);
        int count = roleService.update(lotsRoleVo);
        if (count > 0) {
            return JsonResult.success(count);
        }
        return JsonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu/{roleId}")
    public JsonResult<List<LotsMenuVo>> listMenu(@PathVariable Long roleId) {
        List<LotsMenuVo> roleList = roleService.getMenuByRoleId(roleId);
        return JsonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping(value = "/listResource/{roleId}")
    public JsonResult<List<LotsResourceVo>> listResource(@PathVariable Long roleId) {
        List<LotsResourceVo> roleList = roleService.listResource(roleId);
        return JsonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public JsonResult allocMenu(@RequestParam Long roleId, @RequestParam String menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return JsonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping(value = "/allocResource")
    public JsonResult allocResource(@RequestParam Long roleId, @RequestParam String resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return JsonResult.success(count);
    }

}
