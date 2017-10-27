package g.service.chesscard.engine.util;

import g.service.chesscard.util.Probability;
import org.junit.Assert;
import org.junit.Test;

/**
 * 测试概率抽取
 * @see Probability
 * Created by MK on 2017/1/17.
 */
public class TestProbability {

    /** 测试至少100万次, 至少概率误差在0.5%内 */
    public void test(int[] rates){
        Probability p = new Probability(rates);
        int testCount = p.getMax();
        int rate = 1;
        while(testCount < 1000000){//不足100万次,每次10倍乘到大于
            testCount *= 10;
            rate *= 10;
        }
        int[] cnts = new int[rates.length];
        for (int i = 0; i < testCount; i++) {
            cnts[p.nextIndex()]++;
        }
        for (int i = 0; i < rates.length; i++) {
            Assert.assertTrue(10000L*Math.abs(rates[i]*rate-cnts[i])/testCount<50);
        }
    }

    @Test
    public void testBull(){
        int[] rates = {3356053,659132,646590,658855,646906,682630,646988,659484,646386,658970,738000};//{1, 2, 3};
        test(rates);
    }

    @Test
    public void testSimple(){
        int[] rates = {31,9,60};//{1, 2, 3};
        for (int i = 0; i < 10; i++) {
            test(rates);
        }
    }
}
