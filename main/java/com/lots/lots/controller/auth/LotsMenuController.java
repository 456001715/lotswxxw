package com.lots.lots.controller.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.common.JsonPage;
import com.lots.lots.common.JsonResult;
import com.lots.lots.common.Paging;
import com.lots.lots.entity.vo.LotsMenuNode;
import com.lots.lots.entity.vo.LotsMenuVo;
import com.lots.lots.service.LotsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台菜单管理Controller
 *
 * @author lots
 * @date 2020/2/4
 */
@RestController
@Api(tags = "后台菜单管理")
@RequestMapping("/menu")
public class LotsMenuController {

    @Resource
    private LotsMenuService menuService;

    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody LotsMenuVo lotsMenu) {
        int count = menuService.insert(lotsMenu);
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("修改后台菜单")
    @PostMapping(value = "/update/{id}")
    public JsonResult update(@PathVariable Long id,
                             @RequestBody LotsMenuVo lotsMenu) {
        lotsMenu.setId(id);
        int count = menuService.update(lotsMenu);
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("根据ID获取菜单详情")
    @GetMapping(value = "/{id}")
    public JsonResult<LotsMenuVo> getItem(@PathVariable Long id) {
        LotsMenuVo umsMenu = menuService.findById(id);
        return JsonResult.success(umsMenu);
    }

    @ApiOperation("根据ID删除后台菜单")
    @PostMapping(value = "/delete/{id}")
    public JsonResult delete(@PathVariable Long id) {
        int count = menuService.deleteByIds(id + "");
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("分页查询后台菜单")
    @GetMapping(value = "/list")
    public JsonResult<Paging<LotsMenuVo>> list(@RequestParam Long parentId,
                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        LotsMenuVo lotsMenu = new LotsMenuVo();
        lotsMenu.setParentId(parentId);
        IPage<LotsMenuVo> menuList = menuService.pageList(lotsMenu, pageSize, pageNum);
        return JsonResult.success(Paging.buildPaging(menuList));
    }

    @ApiOperation("树形结构返回所有菜单列表")
    @GetMapping(value = "/treeList")
    public JsonResult<List<LotsMenuNode>> treeList() {
        List<LotsMenuNode> list = menuService.treeList();
        return JsonResult.success(list);
    }

    @ApiOperation("修改菜单显示状态")
    @PostMapping(value = "/updateHidden")
    public JsonResult updateHidden(@RequestParam Long id, @RequestParam("hidden") Integer hidden) {
        int count = menuService.updateHidden(id, hidden);
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }
}
