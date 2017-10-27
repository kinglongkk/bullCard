package g.service.chesscard.util;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 概率抽取
 * 通过概率分布数组, 每次随机一个索引
 * Created by MK on 2017/1/17.
 */
public class Probability {
    /**
     * 概率所在的比率分布
     */
    private int[] rates;
    private int max = 0;

    public Probability(int[] rates) {
        this.rates = new int[rates.length];
        for (int i = 0; i < rates.length; i++) {
            this.rates[i] = max = max + rates[i];
        }
    }

    public Probability(ArrayList<Integer> rates) {
        this.rates = new int[rates.size()];
        for (int i = 0; i < rates.size(); i++) {
            this.rates[i] = max = max + rates.get(i);
        }
    }

    /**
     * 获取下次抽中的索引
     */
    public int nextIndex() {
        int ran = ThreadLocalRandom.current().nextInt(max);
        for (int i = 0; i < rates.length; i++) {
            if (ran < rates[i]) {
                return i;
            }
        }
        return 0;
    }

    public int getMax() {
        return max;
    }
}
