package com.lots.lotswxxw.controller;

import com.lots.lotswxxw.domain.vo.JsouResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lots
 * @Date: 2020/1/15 09:47
 * @Description:
 */
@Controller
@RequestMapping("readImage")
public class ReadImageController {
    @RequestMapping("index")
    public String index(){

        return "gettext";

    }

    @RequestMapping("gettext")
    @ResponseBody
    public JsouResult gettext(){
        System.out.println("进入");
        return new JsouResult(200,"请求成功","123");

    }
}
