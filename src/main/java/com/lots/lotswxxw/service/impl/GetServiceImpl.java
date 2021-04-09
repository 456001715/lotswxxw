package com.lots.lotswxxw.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lots.lotswxxw.dao.GetTwoMapper;
import com.lots.lotswxxw.dao.ListenHisoryDao;
import com.lots.lotswxxw.domain.po.GetTwoPO;
import com.lots.lotswxxw.domain.po.ListenHisoryEntity;
import com.lots.lotswxxw.domain.vo.JsonResult;
import com.lots.lotswxxw.domain.vo.music.JsonRootBean;
import com.lots.lotswxxw.domain.vo.music.WeekData;
import com.lots.lotswxxw.service.GetService;
import com.lots.lotswxxw.util.CloudMusicApiUrl;
import com.lots.lotswxxw.util.CreateWebRequest;
import com.lots.lotswxxw.util.PortScanUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;
import static cn.hutool.core.date.DateUtil.offsetDay;
import static cn.hutool.core.date.DateUtil.thisDayOfWeekEnum;
/**
 * @author: lots
 * @date: 2020/4/24 10:28
 * @description:
 */
@Service
public class GetServiceImpl implements GetService {
    @Resource
    private ListenHisoryDao listenHisoryDao;

    @Resource
    private GetTwoMapper getTwoMapper;

    @Override
    public JsonResult getTwo() {
        GetTwoPO get = new GetTwoPO();
        Set<Integer> set = new TreeSet<>(Integer::compareTo);
        do {
            Integer sui = RandomUtil.randomInt(1, 34);
            set.add(sui);
        } while (set.size() != 6);
        List<String> list = new ArrayList<>();
        set.forEach(s -> list.add(String.format("%02d", s)));
        String redList = String.join(" , ", list);
        Set<Integer> set2 = new TreeSet<>();
        int sui2 = RandomUtil.randomInt(1, 17);
        set2.add(sui2);
        String blue = String.format("%02d", sui2) + "";

        Date date = new Date();
        String currSun = thisDayOfWeekEnum().toChinese();
        get.setRedNumber(redList);
        get.setBlueNumber(blue);
        if (Week.TUESDAY.toChinese().equals(currSun) || Week.THURSDAY.toChinese().equals(currSun) || Week.SUNDAY.toChinese().equals(currSun)) {
            get.setChapter(date);
        } else if (Week.FRIDAY.toChinese().equals(currSun)) {
            get.setChapter(offsetDay(date,2));
        } else {
            get.setChapter(offsetDay(date,1));
        }

        return new JsonResult(200, "get two", get);
    }

    @Override
    public JsonResult getMusic(String id, String type) {
        List<ListenHisoryEntity> list = new ArrayList<>();
        Map<String, Object> data = new HashMap();
        id = id == null ? "283135753" : id;
        type = type == null ? "1" : type;
        data.put("uid", id);
        data.put("type", type);
        String dataPage = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.domain, data, new HashMap());
        JSONObject jsonResult = JSONUtil.parseObj(dataPage);
        JsonRootBean jsonRootBean = (JsonRootBean) JSONUtil.toBean(jsonResult, JsonRootBean.class);
        if (jsonRootBean != null && jsonRootBean.getWeekData() != null && jsonRootBean.getWeekData().size() > 0) {
            String finalId = id;
            List<WeekData> weekData = jsonRootBean.getWeekData();
            ListenHisoryEntity findUser = new ListenHisoryEntity();
            findUser.setUserId(finalId);
            if (CollUtil.isNotEmpty(weekData)) {
                weekData.forEach(week -> {
                    //分数
                    int score = week.getScore();
                    //歌曲名称
                    String name = week.getSong().getName();
                    //歌手
                    String singer = week.getSong().getAr().get(0).getName();
                    ListenHisoryEntity entity = new ListenHisoryEntity();
                    entity.setUserId(finalId);
                    entity.setCreatTime(new Date());
                    entity.setSongName(name);
                    entity.setSongScore(score);
                    entity.setSinger(singer);
                    list.add(entity);

                });
            }
        }


        return new JsonResult(list);
    }

    @Override
    public JsonResult getMusicPage() {
        List<ListenHisoryEntity> all = listenHisoryDao.findListenHisoryAll();
        return new JsonResult(all);
    }

    @Override
    public JsonResult getPort(String ip, Integer start, Integer end) {
        PortScanUtil util = new PortScanUtil();

        return util.getPort(ip, start, end);
    }

    @Override
    public JsonResult buyTwo(GetTwoPO get) {
        get.setCreateTimestamp(new Date());
        int flag = getTwoMapper.insertGetTwo(get);
        if (flag > 0) {
            return new JsonResult();
        }
        return new JsonResult(500, "服务器开小差了");
    }

    @Override
    public JsonResult sendMail(String txt) {
        try{
            MailUtil.send("553294090@qq.com","手机端发送", txt, false);
        } catch (Exception e){
            new JsonResult(500, "服务器开小差了");
        }

        return new JsonResult();
    }
}
