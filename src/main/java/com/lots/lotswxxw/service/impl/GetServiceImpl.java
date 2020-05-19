package com.lots.lotswxxw.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
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
import java.text.SimpleDateFormat;
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

    @Resource
    private GetTwoMapper getTwoMapper;
    @Override
    public JsonResult getTwo() {
        GetTwoPO get=new GetTwoPO();
        Set<Integer> set = new TreeSet<Integer>((o2, o1) -> o2.compareTo(o1));
        while(true){
            Integer sui = RandomUtil.randomInt(1,34);
            set.add(sui);
            if (set.size() == 6) {
                break;
            }
        }
        List<String> list =new ArrayList<>();
        set.forEach(s->list.add(s.toString()));
        /*Set<Integer> sortSet = new TreeSet<Integer>((o2, o1) -> o2.compareTo(o1));
        sortSet.addAll(set);
        String[] array = sortSet.toArray(new String[]{});*/
        String redList = String.join(" , ", list);
        Set<Integer> set2 = new TreeSet<Integer>();
        int sui2 =  RandomUtil.randomInt(1,17);
        set2.add(sui2);
        String blue=sui2+"";

        Date date = new Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String currSun = dateFm.format(date);
        get.setRedNumber(redList);
        get.setBlueNumber(blue);
        if(currSun.equals("星期二")||currSun.equals("星期四")||currSun.equals("星期日")) {
            get.setChapter(date);
        }else if(currSun.equals("星期五")){
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 2);
            get.setChapter(c.getTime());
        }else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 1);
            get.setChapter(c.getTime());
        }



        return new JsonResult(200,"get two",get);
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

    @Override
    public JsonResult getPort(String ip,Integer start,Integer end) {
        PortScanUtil util=new PortScanUtil();

        return util.getPort(ip,start,end);
    }

    @Override
    public JsonResult buyTwo(GetTwoPO get) {
        get.setCreateTimestamp(new Date());
        int flag = getTwoMapper.insertGetTwo(get);
        if(flag>0){
            return new JsonResult();
        }
        return new JsonResult(500,"服务器开小差了");
    }
}
