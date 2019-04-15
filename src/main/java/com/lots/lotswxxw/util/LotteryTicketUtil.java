package com.lots.lotswxxw.util;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName LotteryTicketUtil
 * @Description TODO
 * @Author lots
 * @Date 2019/4/15 17:52
 * @Version V1.0
 **/
public class LotteryTicketUtil {
    public static int[][] TwoColorBall(){
        Random r = new Random();
        int count = 1;

        boolean flag = true;
        int[][] finalBalls = new int[2][];
        finalBalls[0] = new int[6];
        finalBalls[1] = new int[1];
        int redBallNum = r.nextInt(33)+1;
        int blueBallNum = r.nextInt(16)+1;
        finalBalls[0][0]=redBallNum;//产生第一个红球的值
        finalBalls[1][0]=blueBallNum;//随机产生一个篮球的值

        while (count<6) {
            redBallNum = r.nextInt(33)+1;
            //产生红球，1-33中选择6个不同的号；
            flag = true;
            for (int i=0;i<count;i++) {
                if(redBallNum==finalBalls[0][i]) {//如果有等于，跳出循环重新产生随机数
                    flag = false;
                    break;
                }
            }
            if (flag==true) {
                finalBalls[0][count]=redBallNum;
                count++;
            }
        }
        //对数组进行排序
        Arrays.sort(finalBalls[0]);

        return finalBalls;
    }
}
