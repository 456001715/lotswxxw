package com.lots.lotswxxw.controller;

import com.lots.lotswxxw.domain.vo.JsouResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lots
 * @Date: 2020/1/15 09:47
 * @Description:
 */
@RestController
@RequestMapping("readImage")
public class ReadImageController {
    @RequestMapping("index")
    public String index(){

        return "gettext";

    }

    @PostMapping("gettext")
    public JsouResult gettext(@RequestBody Object obj){
        System.out.println(obj.toString());
        System.out.println("进入");
        return new JsouResult(200,"请求成功","123");

    }
}
