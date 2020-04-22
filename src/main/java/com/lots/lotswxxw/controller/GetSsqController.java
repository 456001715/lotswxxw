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
import com.lots.lotswxxw.util.CloudMusicApiUrl;
import com.lots.lotswxxw.util.CreateWebRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: lots
 * @date: 2020/4/21 17:51
 * @description:
 */
@RestController
@RequestMapping("get")
public class GetSsqController {
    @Resource
    private ListenHisoryDao listenHisoryDao;

    @GetMapping("two")
    public JsonResult getTwo(){
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

    @GetMapping("music")
    public JsonResult getMusic(String id,String type){

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
            List<ListenHisoryEntity> userListen = listenHisoryDao.findListenHisoryByCondition(findUser);
            if(CollUtil.isNotEmpty(weekData)){
                if(CollUtil.isNotEmpty(userListen)){
                    Iterator<WeekData> it = weekData.iterator();

                    while(it.hasNext()){
                        WeekData x = it.next();
                        Iterator<ListenHisoryEntity> userit = userListen.iterator();
                        while(userit.hasNext()){
                            ListenHisoryEntity user = userit.next();
                            if(x.getSong().getName().equals(user.getSongName())){
                                it.remove();
                            }
                        }
                    }
                }
            }
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
                    listenHisoryDao.insertListenHisory(entity);
                });

            }


        }
        if(jsonRootBean!=null&&jsonRootBean.getAllData()!=null&&jsonRootBean.getAllData().size()>0){
            String finalId = id;
            jsonRootBean.getAllData().forEach(week->{
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
                listenHisoryDao.insertListenHisory(entity);


            });
        }


        return new JsonResult(jsonRootBean);
    }





}
