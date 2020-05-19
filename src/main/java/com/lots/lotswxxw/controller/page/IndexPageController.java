package com.lots.lotswxxw.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class IndexPageController {

    @RequestMapping("login")
    public String login(){
        return "login";
    }
}
