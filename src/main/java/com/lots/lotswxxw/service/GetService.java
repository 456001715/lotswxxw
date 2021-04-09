package com.lots.lotswxxw.service;

import com.lots.lotswxxw.domain.po.GetTwoPO;
import com.lots.lotswxxw.domain.vo.JsonResult;

/**
 * @author: lots
 * @date: 2020/4/24 10:28
 * @description:
 */
public interface GetService {
    JsonResult getTwo();

    JsonResult getMusic(String id, String type);

    JsonResult getMusicPage();

    JsonResult getPort(String ip, Integer start, Integer end);

    JsonResult buyTwo(GetTwoPO get);
    /**
     * GetController 发送邮件
     * @author: lots
     * @param txt:
     * @return: com.lots.lotswxxw.domain.vo.JsonResult
     * @date: 2021/4/9 9:44
     */
    JsonResult sendMail(String txt);
}
