package g.service.chesscard.engine.schedule;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Double on 2016/11/29.
 * 有名字的线程生成器
 */
public class NamedThreadFactory implements ThreadFactory {

    public static final String THREAD_DESK = "desk";
    public static final String THREAD_BET_MSG = "bet-msg";
    public static final String THREAD_SEAT_MSG = "seat-msg";
    public static final String THREAD_SETTLE_MSG = "settle-msg";

    private final AtomicInteger mThreadNum;
    private String prefix = "";
    private final ThreadGroup mGroup;

    public NamedThreadFactory(String prefix) {
        this.mThreadNum = new AtomicInteger(1);
        SecurityManager s = System.getSecurityManager();
        this.mGroup = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        this.prefix = prefix;
    }

    protected String getPrefix() {
        return prefix;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        String name = this.getPrefix() + "-" + this.mThreadNum.getAndIncrement();
        Thread ret = new Thread(this.mGroup, runnable, name, 0L);
        ret.setDaemon(false);
        return ret;
    }
}
