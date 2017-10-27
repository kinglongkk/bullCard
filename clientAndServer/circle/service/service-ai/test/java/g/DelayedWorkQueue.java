package g;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可根据触发时间点优先执行以及普通的的任务队列
 * @see KScheduledExecutor 仅用于该类
 */
class DelayedWorkQueue {

    private final ReentrantLock lock = new ReentrantLock();
    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;
    /** 定时延迟任务优先 */
    private KScheduledExecutor.KScheduledTask[] delayeds = new KScheduledExecutor.KScheduledTask[INITIAL_CAPACITY];
    /** 普通任务,立即执行 */
    private LinkedList<Runnable> works = new LinkedList<>();

    /**
     * Condition signalled when a newer task becomes available at the
     * head of the delayeds or a new thread may need to become leader.
     */
    private final Condition available = lock.newCondition();

    /**
     * Sifts element added at bottom up to its heap-ordered spot.
     * Call only when holding lock.
     */
    private void siftUp(int k, KScheduledExecutor.KScheduledTask key) {
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            KScheduledExecutor.KScheduledTask e = delayeds[parent];
            if (key.compareTo(e) >= 0)
                break;
            delayeds[k] = e;
            k = parent;
        }
        delayeds[k] = key;
    }

    /**
     * Sifts element added at top down to its heap-ordered spot.
     * Call only when holding lock.
     */
    private void siftDown(int k, KScheduledExecutor.KScheduledTask key) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            KScheduledExecutor.KScheduledTask c = delayeds[child];
            int right = child + 1;
            if (right < size && c.compareTo(delayeds[right]) > 0)
                c = delayeds[child = right];
            if (key.compareTo(c) <= 0)
                break;
            delayeds[k] = c;
            k = child;
        }
        delayeds[k] = key;
    }

    /**
     * Resizes the heap array.  Call only when holding lock.
     */
    private void grow() {
        int oldCapacity = delayeds.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1); // grow 50%
        if (newCapacity < 0) // overflow
            newCapacity = Integer.MAX_VALUE;
        delayeds = Arrays.copyOf(delayeds, newCapacity);
    }

    public int size() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return size + works.size();
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    protected int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    public boolean offer(KScheduledExecutor.KScheduledTask e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = size;
            if (i >= delayeds.length)
                grow();
            size = i + 1;
            if (i == 0) {
                delayeds[0] = e;
            } else {
                siftUp(i, e);
            }
            if (delayeds[0] == e) {
                available.signal();
            }
        } finally {
            lock.unlock();
        }
        return true;
    }

    /** 添加正常低优先级的任务 */
    public void addNormal(Runnable e) {
        if (e == null)
            throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            works.add(e);
            available.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Performs common bookkeeping for poll and take: Replaces
     * first element with last and sifts it down.  Call only when
     * holding lock.
     *
     * @param f the task to remove and return
     */
    private KScheduledExecutor.KScheduledTask finishPoll(KScheduledExecutor.KScheduledTask f) {
        int s = --size;
        KScheduledExecutor.KScheduledTask x = delayeds[s];
        delayeds[s] = null;
        if (s != 0)
            siftDown(0, x);
        if(f.period != 0 && (f.times == 0 || --f.times > 0)){//周期性的任务
            size = s + 1;
            f.nanoSecond += f.period;
            if (s == 0) {
                delayeds[0] = f;
            } else {
                siftUp(s, f);
            }
        }
        return f;
    }

    public Runnable take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            for (; ; ) {
                KScheduledExecutor.KScheduledTask first = delayeds[0];
                long delay = 0;
                if (first != null){
                    delay = first.getDelay();
                    if (delay <= 0)
                        return finishPoll(first);

                }
                if(works.size() > 0)return works.poll();
                if(delay == 0) available.await();
                else available.awaitNanos(delay);
            }
        } finally {
            if (delayeds[0] != null || works.size() > 0)
                available.signal();
            lock.unlock();
        }
    }

    @Deprecated/** 暂时没用到 */
    public Runnable peek() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return delayeds[0];
        } finally {
            lock.unlock();
        }
    }

    @Deprecated/** 暂时没用到 */
    public Runnable poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            KScheduledExecutor.KScheduledTask first = delayeds[0];
            if (first == null || first.getDelay() > 0) {
                if(works.size() > 0)return works.poll();
                return null;
            }else
                return finishPoll(first);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        works.clear();
        try {
            for (int i = 0; i < size; i++) {
                delayeds[i] = null;
            }
            size = 0;
        } finally {
            lock.unlock();
        }
    }

    @Deprecated/** 暂时没用到 */
    public Object[] toArray() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return Arrays.copyOf(delayeds, size, Object[].class);
        } finally {
            lock.unlock();
        }
    }

    @Deprecated/** 暂时没用到 */
    public <T> T[] toArray(T[] a) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (a.length < size)
                return (T[]) Arrays.copyOf(delayeds, size, a.getClass());
            System.arraycopy(delayeds, 0, a, 0, size);
            if (a.length > size)
                a[size] = null;
            return a;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Finds index of given object, or -1 if absent.
     */
    @Deprecated/** 暂时没用到 */
    private int indexOf(KScheduledExecutor.KScheduledTask x) {
        if (x != null) {
            for (int i = 0; i < size; i++)
                if (x.equals(delayeds[i]))
                    return i;
        }
        return -1;
    }

    @Deprecated/** 暂时没用到 */
    public boolean contains(KScheduledExecutor.KScheduledTask x) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return indexOf(x) != -1;
        } finally {
            lock.unlock();
        }
    }

    @Deprecated/** 暂时没用到 */
    public boolean remove(KScheduledExecutor.KScheduledTask x) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int i = indexOf(x);
            if (i < 0)
                return false;

            int s = --size;
            KScheduledExecutor.KScheduledTask replacement = delayeds[s];
            delayeds[s] = null;
            if (s != i) {
                siftDown(i, replacement);
                if (delayeds[i] == replacement)
                    siftUp(i, replacement);
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Iterator<KScheduledExecutor.KScheduledTask> iterator() {
        return new Itr(Arrays.copyOf(delayeds, size));
    }

    /**
     * Snapshot iterator that works off copy of underlying q array.
     */
    class Itr implements Iterator<KScheduledExecutor.KScheduledTask> {
        final KScheduledExecutor.KScheduledTask[] array;
        int cursor = 0;     // index of next element to return
        int lastRet = -1;   // index of last element, or -1 if no such

        Itr(KScheduledExecutor.KScheduledTask[] array) {
            this.array = array;
        }

        public boolean hasNext() {
            return cursor < array.length;
        }

        public KScheduledExecutor.KScheduledTask next() {
            if (cursor >= array.length)
                throw new NoSuchElementException();
            lastRet = cursor;
            return array[cursor++];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            DelayedWorkQueue.this.remove(array[lastRet]);
            lastRet = -1;
        }

    }
}