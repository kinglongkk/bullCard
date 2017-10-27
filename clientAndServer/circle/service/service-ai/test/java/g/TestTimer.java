package g;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MK on 2017/3/23.
 */
public class TestTimer {
    KScheduledExecutor executor = new KScheduledExecutor();

    public TestTimer() {
        System.out.println("start:"+System.currentTimeMillis());
        for (int i = 0; i < 200; i++) {
            int i1 = i;
            executor.execute(
                    ()->{
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"投注"+i1+":"+System.currentTimeMillis());
                    }
            );
        }
        executor.schedule(
                ()->{
                    System.out.println(Thread.currentThread().getName()+"定时--------:"+System.currentTimeMillis());
                }, TimeUnit.MILLISECONDS, 1000, 1000
        );
   /*     executor.schedule(
                ()->{
                    System.out.println("schedule2:"+System.currentTimeMillis());
                }, TimeUnit.MILLISECONDS, 2000
        );*/
    }

    public static void main(String args[]){
        new TestTimer();
    }
}
