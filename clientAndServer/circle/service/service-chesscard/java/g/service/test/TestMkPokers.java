package g.service.test;

import g.model.bet.BetTypeEnum;
import g.service.chesscard.engine.model.MkPokers;
import g.service.chesscard.engine.model.BullCards;

import java.util.*;

public class TestMkPokers {

    public static void main(String args[]) {
        MkPokers mkPokers = new MkPokers();
        long now = System.currentTimeMillis();
        List<BullCards> cardsList = null;
        long max = 0, useTime = 0, avg = 0, sum = 0;
        //CPU:I7-6700hq 2.6GHZ睿频3.14GHZ,测试结果,1万次发牌时间:max=132,avg=39,
        for (int i = 0; i < 1000000000; i++) {
            cardsList = mkPokers.generateCards(BetTypeEnum.BULL_100);
            if ((i + 1) % 50000 == 0) {
                useTime = -(now - (now = System.currentTimeMillis()));
                sum += useTime;
                avg = sum * 10000 / (i + 1);
                max = Math.max(max, useTime);
                System.err.println("抽牌花费时间:" + useTime + ",\t\tmax:" + max + ",\t\tavg:" + avg);
            }
        }
//        Map<Integer,Integer> chance = new HashMap<>();
//        chance.put(10,735);
//        chance.put(9,659);
//        chance.put(8,647);
//        chance.put(7,659);
//        chance.put(6,647);
//        chance.put(5,683);
//        chance.put(4,647);
//        chance.put(3,659);
//        chance.put(2,647);
//        chance.put(1,659);
//        chance.put(0,3351);
//        int sum = 0;
//        for (int i = 0; i < chance.size(); i++) {
//            System.out.print((sum+=chance.get(i))+",");
//        }
    }
}
