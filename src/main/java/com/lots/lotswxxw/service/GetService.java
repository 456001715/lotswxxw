package com.lots.lotswxxw.service;

import com.lots.lotswxxw.domain.po.GetTwoPO;
import com.lots.lotswxxw.domain.vo.JsonResult;

/**
 * @author: lots
 * @date: 2020/4/24 10:28
 * @description:
 */
public interface GetService {
    public JsonResult getTwo();
    public JsonResult getMusic(String id,String type);
    public JsonResult getMusicPage();
    public JsonResult getPort(String ip,Integer start,Integer end);
    public JsonResult buyTwo(GetTwoPO get);
}
