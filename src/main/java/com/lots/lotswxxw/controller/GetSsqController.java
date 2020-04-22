package com.lots.lotswxxw.controller;

import com.lots.lotswxxw.domain.vo.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author: lots
 * @date: 2020/4/21 17:51
 * @description:
 */
@RestController
@RequestMapping("two")
public class GetSsqController {

    @GetMapping("get")
    public Message getTwo(){
        Set<String> set = new TreeSet<String>();
        while(true){
            int sui = new Random().nextInt(33);
            set.add(sui<10?"0"+sui:""+sui);
            if (set.size() == 6) {
                break;
            }
        }
        Set<String> set2 = new TreeSet<String>();
        int sui2 = new Random().nextInt(16);
        set2.add(sui2<10?"0"+sui2:""+sui2);
        return new Message().ok(200,"").addData("双色球",set2);
    }





}
