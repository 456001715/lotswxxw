package com.lots.lotswxxw.controller.core;

import com.lots.lotswxxw.controller.auth.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName IndexController
 * @Description test主页
 * @Author lots
 * @Date 2019/4/29 18:29
 * @Version V1.0
 **/

@Controller
public class IndexController extends BaseAction {

    @RequestMapping("/index")
    public String test(){
        return "login";
    }

}
