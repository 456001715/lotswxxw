package com.lots.lotswxxw.service.impl;

import cn.hutool.core.collection.CollUtil;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: lots
 * @date: 2020/4/24 10:28
 * @description:
 */
@Service
public class GetServiceImpl implements GetService {
    @Resource
    private ListenHisoryDao listenHisoryDao;
    @Override
    public JsonResult getTwo() {
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
        return new JsonResult(200,"双色球",set.toString()+set2.toString());
    }

    @Override
    public JsonResult getMusic(String id, String type) {
        List<ListenHisoryEntity>list=new ArrayList<>();
        Map<String,Object> data=new HashMap();
        id = id == null ? "283135753" : id;
        type = type == null ? "1" : type;
        data.put("uid",id);
        data.put("type",type);
        String dataPage = CreateWebRequest.createWebPostRequest(CloudMusicApiUrl.domain, data, new HashMap());
        JSONObject jsonResult =JSONUtil.parseObj(dataPage);
        JsonRootBean jsonRootBean = (JsonRootBean)JSONUtil.toBean(jsonResult, JsonRootBean.class);
        if(jsonRootBean!=null&&jsonRootBean.getWeekData()!=null&&jsonRootBean.getWeekData().size()>0){
            String finalId = id;
            List<WeekData> weekData = jsonRootBean.getWeekData();
            ListenHisoryEntity findUser=new ListenHisoryEntity();
            findUser.setUserId(finalId);
            if(CollUtil.isNotEmpty(weekData)){
                weekData.forEach(week->{
                    //分数
                    int score = week.getScore();
                    //歌曲名称
                    String name = week.getSong().getName();
                    //歌手
                    String singer=week.getSong().getAr().get(0).getName();
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
}
