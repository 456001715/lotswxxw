package com.lots.lots.controller.auth;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.common.JsonPage;
import com.lots.lots.common.JsonResult;
import com.lots.lots.common.Paging;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.security.component.DynamicSecurityMetadataSource;
import com.lots.lots.service.LotsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台资源管理Controller
 *
 * @author lots
 * @date 2020/2/4
 */
@RestController
@Api(tags = "后台资源管理")
@RequestMapping("/resource")
public class LotsResourceController {

    @Resource
    private LotsResourceService resourceService;
    @Resource
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @PostMapping(value = "/create")
    public JsonResult create(@RequestBody LotsResourceVo lotsResource) {
        int count = resourceService.insert(lotsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("修改后台资源")
    @PostMapping(value = "/update/{id}")
    public JsonResult update(@PathVariable Long id,
                             @RequestBody LotsResourceVo lotsResource) {
        lotsResource.setId(id);

        int count = resourceService.update(lotsResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @GetMapping(value = "/{id}")
    public JsonResult<LotsResourceVo> getItem(@PathVariable Long id) {
        LotsResourceVo lotsResource = resourceService.findById(id);
        return JsonResult.success(lotsResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @PostMapping(value = "/delete/{id}")
    public JsonResult delete(@PathVariable Long id) {
        int count = resourceService.deleteById(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return JsonResult.success(count);
        } else {
            return JsonResult.failed();
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @GetMapping(value = "/list")
    public JsonResult<Paging<LotsResourceVo>> list(@RequestParam(required = false) Long categoryId,
                                                     @RequestParam(required = false) String nameKeyword,
                                                     @RequestParam(required = false) String urlKeyword,
                                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        LotsResourceVo lotsResource = new LotsResourceVo();
        lotsResource.setCategoryId(categoryId);
        lotsResource.setName(nameKeyword);
        lotsResource.setUrl(urlKeyword);
        IPage<LotsResourceVo> resourceList = resourceService.pageList(lotsResource, pageSize, pageNum);
        return JsonResult.success(Paging.buildPaging(resourceList));
    }

    @ApiOperation("查询所有后台资源")
    @GetMapping(value = "/listAll")
    public JsonResult<List<LotsResourceVo>> listAll() {
        List<LotsResourceVo> resourceList = resourceService.listAll();
        return JsonResult.success(resourceList);
    }
}
