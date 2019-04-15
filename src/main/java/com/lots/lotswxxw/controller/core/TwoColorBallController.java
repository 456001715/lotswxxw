package com.lots.lotswxxw.controller.core;

import com.lots.lotswxxw.domain.vo.Message;
import com.lots.lotswxxw.util.JsonWebTokenUtil;
import com.lots.lotswxxw.util.LotteryTicketUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @ClassName core
 * @Description 第一个功能-机选双色球 - -。。
 * @Author lots
 * @Date 2019/4/15 17:50
 * @Version V1.0
 **/

@RestController
public class TwoColorBallController {
    public LotteryTicketUtil two;
    @ApiOperation(value = "获取对应用户角色",notes = "GET根据用户的appId获取对应用户的角色")
    @GetMapping("/two/get")
    public Message getUserRoleList() {
//        TwoColorBallUtil two;
        int [][]finalBalls=two.TwoColorBall();
       return new Message().ok(6666,"return number success").addData("number",finalBalls);
    }
}
