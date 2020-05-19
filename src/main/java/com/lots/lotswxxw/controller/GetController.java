package com.lots.lotswxxw.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lots.lotswxxw.dao.ListenHisoryDao;
import com.lots.lotswxxw.domain.po.ListenHisoryEntity;
import com.lots.lotswxxw.domain.vo.JsonResult;
import com.lots.lotswxxw.domain.vo.music.JsonRootBean;
import com.lots.lotswxxw.domain.vo.music.WeekData;
import com.lots.lotswxxw.service.GetService;
import com.lots.lotswxxw.util.CloudMusicApiUrl;
import com.lots.lotswxxw.util.CreateWebRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;


/**
 * @author: lots
 * @date: 2020/4/21 17:51
 * @description:
 */
@RestController
@RequestMapping("get")
public class GetController {
    @Resource
    private GetService getService;


    @GetMapping("two")
    public JsonResult getTwo(){
        return getService.getTwo();
    }

    @GetMapping("buyTwo")
    public JsonResult getBuyTwo(@RequestParam("number")String number){
        return getService.buyTwo();
    }

    @GetMapping("music")
    public JsonResult getMusic(String id,String type){
        return getService.getMusic(id,type);
    }

    @GetMapping("port")
    public JsonResult getPort(@RequestParam("ip") String ip,@RequestParam("start") Integer start,@RequestParam("end") Integer end){
        return getService.getPort(ip,start,end);
    }





}
