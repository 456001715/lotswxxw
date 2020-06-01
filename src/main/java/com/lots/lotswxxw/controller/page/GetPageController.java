package com.lots.lotswxxw.controller.page;

import com.lots.lotswxxw.domain.vo.JsonResult;
import com.lots.lotswxxw.service.GetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author: lots
 * @date: 2020/4/21 17:51
 * @description:
 */
@Controller
@RequestMapping("page/get")
public class GetPageController {
    @Resource
    private GetService getService;


    @RequestMapping("two")
    public String getTwo(){
        return "getTwo";
    }


    @RequestMapping("music")
    public String getMusic(){
        return "getMusic";
    }




}
