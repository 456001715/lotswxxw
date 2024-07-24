package com.lots.lots.controller.tool;

import cn.dev33.satoken.annotation.SaIgnore;
import com.lots.lots.common.BaseController;
import com.lots.lots.common.R;
import com.lots.lots.service.GamePersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* (游戏数据模块)表Controller
*
* @author lots
* @since 2024-01-23
*/
@RestController
@RequestMapping("GamePerson")
@Api(tags = "游戏数据模块")
public class GamePersonController extends BaseController {
    @Resource
    private GamePersonService gamePersonService;

    @GetMapping(value = "/createPerson")
    @SaIgnore
    @ApiOperation("创建人")
    public R<Void> createPerson() {
        gamePersonService.createPerson();
        return R.success();
    }

    @GetMapping(value = "/letsPlayGame")
    @SaIgnore
    @ApiOperation("开始游戏")
    public R<Void> letsPlayGame() {
        return R.success();
    }
}