package g.service.engine.model;

/**
 * 循环控制器,在不确定最大遍历次数的while循环里使用,避免死循环
 * Created by MK on 2016/11/15.
 */
public class WhileCtrl {
    /** 最大循环次数默认100000 */
    private int maxCount = 100000;
    /** 遍历次数 */
    private int iteratorCount = 0;

    public WhileCtrl() {
    }

    /**
     * @param maxCount 最大遍历次数,超过后再调用next会报错
     */
    public WhileCtrl(int maxCount) {
        this.maxCount = maxCount;
    }

    /**
     * 判断是否能进行下次while循环
     *
     * @return
     */
    public boolean next() {
        if (iteratorCount++ < maxCount) {
            return true;
        }
        throw new RuntimeException("while死循环(遍历超过最大次数)");
    }

    /**
     * 测试
     */
    public static void main(String args[]) {
        int count = 0;
        WhileCtrl whileCtrl = new WhileCtrl();
        while (whileCtrl.next() && count<200000) {//循环控制器+其他条件
            count++;
            if(count == 210000){
                break;//不可能触发的break
            }
        }
    }
}
