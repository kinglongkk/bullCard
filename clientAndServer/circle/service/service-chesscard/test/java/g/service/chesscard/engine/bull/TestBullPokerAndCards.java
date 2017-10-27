package g.service.chesscard.engine.bull;

import g.model.bet.BetTypeEnum;
import g.service.chesscard.engine.model.BullBaoPoker;
import g.service.chesscard.engine.model.BullCards;
import g.service.chesscard.engine.model.BullPoker;
import g.service.chesscard.engine.model.MkPokers;
import g.service.chesscard.util.Probability;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试抽取5组斗牛牌,完全随机或者通过牛N
 * Created by MK on 2017/1/13.
 */
public class TestBullPokerAndCards extends Thread {

    static AtomicInteger[] results = new AtomicInteger[11];
    /** 52张牌的概率分布 */
    static AtomicInteger[][] cardResults = new AtomicInteger[5][52];
    static {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <52; j++) {
                cardResults[i][j] = new AtomicInteger();
            }
        }
    }

    private int testMethod = 0;
    private static final int TEST_5CARDS_RAN = 0;
    private static final int TEST_5CARDS_BY_BULL = 1;
    private static final int TEST_5CARDS_BY_BULL1 = 2;
    private static final int TEST_BAO_CARDS = 3;
    private static final int TEST_BAO_BY_MULTI_PROB = 4;

    public TestBullPokerAndCards() {
    }

    @Override
    public void run() {
        for (int i = 0; i < 11; i++) {
            results[i] = new AtomicInteger();
        }
        Probability prob =  new Probability(resultRates);
        MkPokers mkPokers = null;
        if(testMethod == TEST_5CARDS_BY_BULL1){
             mkPokers = new MkPokers();
        }
        BullBaoPoker baoPoker = null;
        if(testMethod == TEST_BAO_CARDS || testMethod == TEST_BAO_BY_MULTI_PROB){
            baoPoker = new BullBaoPoker();
        }
        ArrayList<Integer> baoBulls = null;
        Random ran = new Random();
        long now = System.currentTimeMillis();
        for (int i = 0; i < testCount/4/5; i++) {
            BullCards cards = null;
            switch (testMethod){
                case TEST_5CARDS_RAN:{
                    BullPoker poker = new BullPoker();
                    for (int j = 0; j < 5; j++) {//抽出5组牌
                        cards = poker.getCards();
                        results[cards.getCardsPoint()].incrementAndGet();
                    }
                }break;
                case TEST_5CARDS_BY_BULL:{
                    BullPoker poker = new BullPoker();
                    for (int j = 0; j < 5; j++) {//抽出5组牌
                        cards = poker.getCards(prob.nextIndex(), j<4);
                        results[cards.getCardsPoint()].incrementAndGet();
                    }
                }break;
                case TEST_5CARDS_BY_BULL1:mkPokers.generateCards(BetTypeEnum.BULL_100); continue;
                case TEST_BAO_CARDS :{
                    for (int j = 0; j < 5; j++) {//抽出5组牌
                        int card = baoPoker.getCard();
                        cards = baoPoker.getCards(card);
                        results[cards.getCardsPoint()].incrementAndGet();
                    }
                }break;
                case TEST_BAO_BY_MULTI_PROB :{
                    if(i%10 == 0){
                        baoBulls = new ArrayList<>(11);
                        for (int j = 0; j < 11; j++) {
                            baoBulls.add(ran.nextInt(11));
                        }
                    }
                    for (int j = 0; j < 5; j++) {//抽出5组牌
                        int card = baoPoker.getCard();
                        cards = baoPoker.getCards(card, baoBulls);
                        results[cards.getCardsPoint()].incrementAndGet();
                    }
                }break;
            }
//            int[] cs = cards.getCards();
//            for (int j = 0; j < 5; j++) {
//                cardResults[j][cs[j]].incrementAndGet();
//            }
        }
//        System.out.println("抽牌完成时间(ms):\t" + (System.currentTimeMillis() - now));
    }

    private static int testCount = 1000000;
    private static int[] resultRates = {335242,65935,64856,66452,64745,68139,64903,66051,64118,65797,73762};

    @Test
    public void testBullCard(){
        BullPoker poker = new BullPoker();
        System.out.println("验证抽出来的牌有牛的话,前三张加起来是不是求余=0");
        for (int i = 0; i < 10; i++) {
            BullCards cards = poker.getCards();
            StringBuilder sb = new StringBuilder();
            sb.append("牛").append(cards.getCardsPoint()).append(": ");
            int count = 0;
            for (int j = 0; j < 5; j++) {
                int card = cards.getCards()[j];
                int num = Math.min(card>>2,9)+1;
                count+=num;
                sb.append(card).append("-").append(num).append(", ");
                if(j==2){
                    Assert.assertTrue(cards.getCardsPoint()==0? count%10!=0 : count%10==0);
                }
            }
            System.out.println(sb.toString());
        }
    }

    @Test
    public void testBaoCard(){
        BullBaoPoker poker = new BullBaoPoker();
        System.out.println("验证抽出来的牌有牛的话,前三张加起来是不是求余=0");
        for (int i = 0; i < 10; i++) {
            int first = poker.getCard();
            BullCards cards = poker.getCards(first);
            StringBuilder sb = new StringBuilder();
            sb.append("牛").append(cards.getCardsPoint()).append(": ");
            int count = 0;
            for (int j = 0; j < 5; j++) {
                int card = cards.getCards()[j];
                int num = Math.min(card>>2,9)+1;
                count+=num;
                sb.append(card).append("-").append(num).append(", ");
                if(j==2){
                    Assert.assertTrue(cards.getCardsPoint()==0? count%10!=0 : count%10==0);
                }
            }
            System.out.println(sb.toString());
        }
    }

    /** 夺宝开牌概率测试, 抽取一张牌,其他四张随机开 */
    @Test
    public void testBaoCard1(){
        for (int j = 0; j < 14; j++) {
            int first = j==14 ? BullBaoPoker.getCard() : j*4;
            int[] cnts = new int[11];
            for (int i = 0; i < 1000000; i++) {
                BullCards cards = BullBaoPoker.getCards(first);
                cnts[cards.getCardsPoint()]++;
            }
//            System.out.print((j+1)+"\t");
            for (int i = 0; i < 11; i++) {
                System.out.print(cnts[i]+"\t");
            }
            System.out.println();
        }
    }
    /** 夺宝开牌概率测试, 抽取一张牌,其他四张按倍数设定的比率来开 */
    @Test
    public void testBaoCard2(){
        Probability prob = new Probability(new int[]{65,13,13,13,13,13,13,13,13,13,15});
        for (int j = 0; j < 14; j++) {
            int first = j==14 ? BullBaoPoker.getCard() : j*4;
            int[] cnts = new int[11];
            for (int i = 0; i < 1000000; i++) {
                BullCards cards = BullBaoPoker.getCards(first, prob.nextIndex());
                cnts[cards.getCardsPoint()]++;
            }
//            System.out.print((j+1)+"\t");
            for (int i = 0; i < 11; i++) {
                System.out.print(cnts[i]+"\t");
            }
            System.out.println();
        }
    }

    @Test
    public void testBaoCard3(){
        for (int j = 0; j < 14; j++) {
            BullCards cards = BullBaoPoker.getCards(6, 1);
            System.out.println(cards);
        }
    }


    @Test
    public void testBaoCardByBulls(){
        BullBaoPoker poker = new BullBaoPoker();
//        System.out.println("验证抽出来的牌有牛的话,前三张加起来是不是求余=0");
        ArrayList<Integer> baoBulls = new ArrayList<>();
        baoBulls.add(0);
        baoBulls.add(2);
        baoBulls.add(5);
        baoBulls.add(10);
        for (int i = 0; i < 10; i++) {
            int first = poker.getCard();
            BullCards cards = poker.getCards(first, baoBulls);
            StringBuilder sb = new StringBuilder();
            sb.append("牛").append(cards.getCardsPoint()).append(": ");
            int count = 0;
            for (int j = 0; j < 5; j++) {
                int card = cards.getCards()[j];
                int num = Math.min(card>>2,9)+1;
                count+=num;
                sb.append(card).append("-").append(num).append(", ");
                if(j==2){
                    Assert.assertTrue(cards.getCardsPoint()==0? count%10!=0 : count%10==0);
                }
            }
            System.out.println(sb.toString());
        }
    }

    @Test
    public void test5CardsByBull(){
        testAll(TEST_5CARDS_BY_BULL);
    }

    @Test
    public void test5BaoCards(){
        testAll(TEST_BAO_CARDS);
    }

    /** 通过多个牛值抽取一组牌 */
    @Test
    public void test5BaoByBulls(){
        testAll(TEST_BAO_BY_MULTI_PROB);
    }

    @Test
    public void test5CardsByBull1(){
        testAll(TEST_5CARDS_BY_BULL1);
    }

    @Test
    public void test5CardsRan(){
        testAll(TEST_5CARDS_RAN);
    }

    public void testAll(int testMethod){
        TestBullPokerAndCards[] tests = new TestBullPokerAndCards[4];
        for (int i = 0; i < tests.length; i++) {
            tests[i] = new TestBullPokerAndCards();
            tests[i].testMethod = testMethod;
            tests[i].start();
        }
        try {
            for (int i = 0; i < tests.length; i++) {
                tests[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(testMethod == TEST_5CARDS_BY_BULL1 || testMethod == TEST_BAO_BY_MULTI_PROB) return;
        int range = 100;
        if(testMethod == TEST_BAO_CARDS)range = 200;
        for (int i = 0; i < 11; i++) {//打印出各种牛值的分布
            if(testMethod == TEST_BAO_CARDS)System.out.println("牛"+i+"\t"+results[i].get());
            Assert.assertTrue(10000L*Math.abs(resultRates[i]-results[i].get())/testCount<range);
        }
//        for (int i = 0; i < 52; i++) {
//            StringBuilder sb = new StringBuilder("牌\t").append(i).append("\t");
//            for (int j = 0; j < 5; j++) {
//                sb.append(cardResults[j][i].get());
//                if(j<4)sb.append("\t");
//            }
//            System.out.println(sb.toString());
//        }
    }
}
