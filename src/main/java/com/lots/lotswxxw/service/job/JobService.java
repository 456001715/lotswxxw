package com.lots.lotswxxw.service.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lots.lotswxxw.dao.GetTwoMapper;
import com.lots.lotswxxw.dao.ListenHisoryDao;
import com.lots.lotswxxw.dao.TwoBallHisoryMapper;
import com.lots.lotswxxw.domain.po.GetTwoPO;
import com.lots.lotswxxw.domain.po.ListenHisoryEntity;
import com.lots.lotswxxw.domain.po.TwoBallHisoryPo;
import com.lots.lotswxxw.domain.vo.music.JsonRootBean;
import com.lots.lotswxxw.domain.vo.music.WeekData;
import com.lots.lotswxxw.util.CloudMusicApiUrl;
import com.lots.lotswxxw.util.CreateWebRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hutool.core.collection.CollUtil.isNotEmpty;
import static cn.hutool.core.date.DateUtil.*;

/**
 * @author: lots
 * @date: 2020/4/26 09:38
 * @description:
 */

@Service
@Component
public class JobService {
    @Resource
    private ListenHisoryDao listenHisoryDao;

    @Resource
    private GetTwoMapper getTwoMapper;

    @Resource
    private TwoBallHisoryMapper twoBallHisoryMapper;

    /**
     * 每1小时执行一次
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
//       @Scheduled(cron="0/5 * * * * ? ")
    public void listenHisoryJob() {
        Map<String, Object> data = new HashMap<String, Object>();
        String idList =
                "283135753,128074624";
        String[] ids = idList.split(",");
        for (String id : ids) {
            String type = "1";
            data.put("uid", id);
            data.put("type", type);
            String dataPage = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.domain, data, new HashMap<String, String>());
            JSONObject jsonResult = JSONUtil.parseObj(dataPage);
            JsonRootBean jsonRootBean = (JsonRootBean) JSONUtil.toBean(jsonResult, JsonRootBean.class);
            if (jsonRootBean != null && jsonRootBean.getWeekData() != null && jsonRootBean.getWeekData().size() > 0) {
                String finalId = id;
                List<WeekData> weekData = jsonRootBean.getWeekData();
                ListenHisoryEntity findUser = new ListenHisoryEntity();
                findUser.setUserId(finalId);
                if (isNotEmpty(weekData)) {
                    weekData.forEach(week -> {
                        //分数
                        int score = week.getScore();
                        //歌曲名称
                        String name = week.getSong().getName();
                        //歌手
                        String singer = week.getSong().getAr().get(0).getName();
                        //歌曲id
                        Long songId = week.getSong().getId();
                        ListenHisoryEntity entity = new ListenHisoryEntity();
                        entity.setUserId(finalId);
                        entity.setCreatTime(new Date());
                        entity.setSongName(name);
                        entity.setSongScore(score);
                        entity.setSinger(singer);
                        entity.setSongId(songId);
                        entity.setSongScore(weekData.get(0).getScore());
                        try {
                            int flag = listenHisoryDao.findByName(entity);
                            if (flag < 1) {
                                listenHisoryDao.insertListenHisory(entity);
                            }
                        } catch (Exception e) {

                        }

                    });
                }
            }
        }
    }

    @Scheduled(cron = "0 0 21 ? * 2,4,7")
//@Scheduled(cron = "0/5 * * * * ? ")
    public void getTwo() {
        String url = "http://kaijiang.500.com/ssq.shtml";
        List<GetTwoPO> nowList = getTwoMapper.getNowList();
        SimpleDateFormat f = new SimpleDateFormat("yyyy年M月d日");
        if (isNotEmpty(nowList)) {
            Boolean flag = true;
            Long n = 60000L;
            try { Thread.sleep ( n ) ;
            } catch (InterruptedException ie){}
            while (flag) {
                while (flag) {
                    try {
                        Document document = Jsoup.connect(url).get();
                        String body = document.getElementsByClass("ball_red").text();
                        String[] s = body.split(" ");
                        String redNumber = String.join(" , ", s);
                        Integer ball_blue = Integer.parseInt(document.getElementsByClass("ball_blue").text());
                        String span_right = document.getElementsByClass("span_right").text();
                        String getdate = span_right.substring(span_right.lastIndexOf("开奖日期：") + 5).substring(0, span_right.indexOf(" 兑奖截止") - 5);
                        System.out.println(redNumber + "-" + ball_blue + "-" + getdate);
                        if (f.parse(getdate).getTime()==(f.parse(f.format(nowList.get(0).getChapter()))).getTime()){
                            flag = false;
                            TwoBallHisoryPo po=new TwoBallHisoryPo();
                            po.setBlueNumber(ball_blue+"");
                            po.setRedNumber(redNumber);
                            po.setCreatTime(new Date());
                            twoBallHisoryMapper.insertTwoBallHisory(po);
                            nowList.forEach(getTwo -> {
                                int count = 0;
                                for (String num1 : redNumber.split(" , ")) {
                                    for (String num2 : getTwo.getRedNumber().split(" , ")) {
                                        if (Integer.parseInt(num1) == Integer.parseInt(num2)) {
                                            count += 1;
                                        }
                                    }
                                }
                                Integer userBlueball = Integer.parseInt(getTwo.getBlueNumber());
                                if (count == 6 && ball_blue.equals(userBlueball)) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(1);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else if (count == 6) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(2);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else if (count == 5 && ball_blue.equals(userBlueball)) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(3);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else if (count == 5 || count == 4 && ball_blue.equals(userBlueball)) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(4);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else if (count == 4 || count == 3 && ball_blue.equals(userBlueball)) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(5);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else if (ball_blue.equals(userBlueball)) {
                                    getTwo.setIsTrue(true);
                                    getTwo.setIsRmb(6);
                                    getTwo.setUpdateTimestamp(new Date());
                                } else {
                                    getTwo.setIsTrue(false);
                                    getTwo.setIsRmb(0);
                                    getTwo.setUpdateTimestamp(new Date());
                                }
                                if (getTwo.getIsTrue()) {
                                    MailUtil.send("553294090@qq.com", "当你看到这份邮件", "就代表你中了" + getTwo.getIsRmb() + "级，在第" + f.format(getTwo.getChapter()) + "期", false);
                                }else{
                                    MailUtil.send("553294090@qq.com", "很遗憾没有中奖", "开奖号码为："+redNumber + "-" + ball_blue + "-" + getdate+"你的号码为："+nowList.toString() , false);

                                }
                                getTwoMapper.updateGetTwo(getTwo);
                            });
                        }
                    } catch (Exception e) {

                    }
                }

            }
        }
    }

}