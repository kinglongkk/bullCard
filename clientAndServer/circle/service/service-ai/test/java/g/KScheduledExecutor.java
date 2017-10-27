package g;

import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.concurrent.*;

/**
 * 单线程调度器,可执行普通任务或者优先执行定时任务, 可插队先执行, 可准确定时, 可倒计时
 * Created by MK on 2017/3/23.
 */
public class KScheduledExecutor {

    private static Log log = LogFactory.getLog(KScheduledExecutor.class);
    protected KScheduledThread thread = new KScheduledThread();
    protected DelayedWorkQueue delayedQueue = new DelayedWorkQueue();
    protected volatile boolean shutDowned = false;

    public KScheduledExecutor() {
        thread.start();
    }

    /** 关闭不再执行 */
    public void shutDown(){
        shutDowned = true;
        delayedQueue.clear();
    }

    /** 马上插队执行 */
    public void execute(Runnable runnable){
        delayedQueue.addNormal(runnable);
    }

    /** 通过时间戳纳秒数定时 */
    public void scheduleNanos(Runnable runnable, long nanoSecond, long period, int times){
        delayedQueue.offer(new KScheduledTask(runnable, nanoSecond, period, times));
    }

    /** 通过时间倒计时 */
    public void schedule(Runnable runnable, TimeUnit unit, long delay){
        scheduleNanos(runnable, System.nanoTime() + unit.toNanos(delay), 0, 0);
    }

    /** 通过时间倒计时 */
    public void schedule(Runnable runnable, TimeUnit unit, long delay, long period, int times){
        scheduleNanos(runnable, System.nanoTime() + unit.toNanos(delay), unit.toNanos(period), times);
    }

    /** 通过时间倒计时 */
    public void schedule(Runnable runnable, TimeUnit unit, long delay, long period){
        scheduleNanos(runnable, System.nanoTime() + unit.toNanos(delay), unit.toNanos(period), 0);
    }

    /** 通过时间戳纳秒数定时 */
    public void scheduleMillis(Runnable runnable, long milliSecond){
        scheduleNanos(runnable, milliSecond*1000, 0, 0);
    }

    /** 通过时间戳纳秒数定时 */
    public void scheduleMillis(Runnable runnable, long milliSecond, long period, int times){
        scheduleNanos(runnable, milliSecond*1000, period*1000, times);
    }

    /** 通过时间戳纳秒数定时 */
    public void scheduleMillis(Runnable runnable, long milliSecond, long period){
        scheduleNanos(runnable, milliSecond*1000, period*1000, 0);
    }

    /** 调度的任务 */
    protected static class KScheduledTask implements Comparable<KScheduledTask>, Runnable{

        protected Runnable runnable;
        /** 时间戳纳秒数 */
        protected long nanoSecond;
        /** 周期间隔 */
        protected long period;
        /** 执行次数,为0时表示无限次数 */
        protected int times;

        public KScheduledTask(Runnable runnable, long nanoSecond, long period, int times) {
            this.runnable = runnable;
            this.nanoSecond = nanoSecond;
            this.period = period;
            this.times = times;
        }

        public KScheduledTask(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public int compareTo(KScheduledTask other) {
            if (other == this) // compare zero if same object
                return 0;
            long diff = nanoSecond - other.nanoSecond;
            if (diff < 0)
                return -1;
            else
                return 1;
        }

        public long getDelay() {
            return nanoSecond - System.nanoTime();
        }

        @Override
        public String toString() {
            return String.valueOf(nanoSecond);
        }

        @Override
        public void run() {
            this.runnable.run();
        }
    }

    /** 调度线程 */
    protected class KScheduledThread extends Thread{
        @Override
        public void run() {
            while(!shutDowned){
                try{
                    Runnable task = delayedQueue.take();
                    task.run();
                }catch (Exception e){
                    log.error(e);
                }
            }
        }
    }

}
