package g.service.webSocket;

import io.netty.buffer.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.internal.StringUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 封装的消息类
 * 读取消息后一定要调用realease()释放内存
 * @author MK
 * @date 2016-08-28
 */
public class Msg {

    public static final int NUM = 0;
    public static final int STRING = 1;

    public static final int NEGATIVE = 1;
    public static final int POSITIVE = 0;

    static Charset charset = Charset.forName("UTF-8");

    public ByteBuf data;
    public ArrayList<Integer> marks = null;

    public Msg() {
        data = PooledByteBufAllocator.DEFAULT.heapBuffer(256);
        data.retain();
    }

    public Msg(String msgString) {
        data = PooledByteBufAllocator.DEFAULT.heapBuffer(256);
        data.retain();
        data.writeCharSequence(msgString, charset);
    }

    public Msg(ByteBuf data) {
        this.data = data;
        data.retain();
    }

    public Msg(int len) {
        data = PooledByteBufAllocator.DEFAULT.heapBuffer(len);
        data.retain();
    }

    /**
     * 消息在不使用的时候都要被释放掉
     */
    public void release() {
        data.release();
    }

    public ByteBuf getData() {
        return data;
    }

    public void putLong(Long val) {
        putLong((long) val);
    }

    ;

    public void putLong(Integer val) {
        putLong((long) val);
    }

    ;

    public void putLong(int val) {
        putLong((long) val);
    }

    ;

    public void putLong(Short val) {
        putLong((long) val);
    }

    ;

    public void putLong(short val) {
        putLong((long) val);
    }

    ;

    public void putLong(Byte val) {
        putLong((long) val);
    }

    ;

    public void putLong(byte val) {
        putLong((long) val);
    }

    ;

    public void putBoolean(Boolean val) {
        data.writeByte((byte) (val ? 1 : 0));
    }

    ;

    public void putboolean(boolean val) {
        data.writeByte((byte) (val ? 1 : 0));
    }

    ;

    public Boolean getBoolean() {
        return new Boolean(data.readByte() == 1);
    }

    public boolean getboolean() {
        return data.readByte() == 1;
    }

    /**
     * 高1-3位为后续数字高位字节数，低4位为正负位（正0负1），低1-3位为数字低2位0-7
     *
     * @param val
     */
    public void putLong(long val) {
        boolean positive = val > 0;
        if (!positive) val = val * -1;
        int bit = 0;
        long tmp = val;
        while (tmp > 0) {
            tmp = tmp >> 1;
            bit++;
        }
        bit = (bit + 3) / 7;
        data.writeByte((byte) ((bit << 4) | ((positive ? 0 : 1) << 3) | (val & 7)));//高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
        val = val >> 3;
        for (int i = 0; i < bit; i++) {
            data.writeByte((byte) (val & 127));
            val = val >> 7;
        }
    }

    public long getLong() {
        byte d = data.readByte();
        long rs = d & 7;
        int byteCount = (d >> 4) & 7;
        for (int i = 0; i < byteCount; i++) {
            rs = rs | ((data.readByte() & 255L) << (i * 7 + 3));
        }
        rs = rs * ((d & 8) == 0 ? 1 : -1);
        return rs;
    }

    public void putString(String val) {
        byte[] b = val.getBytes(charset);
        putBytesWithLen(b);
    }

    /**
     * 高1-2位为后续字节数高位字节数，低5位为字节数低5位
     * 最多为2^(21+5)字节，差不多32M多B
     *
     * @param val
     */
    public void putBytesWithLen(byte[] val) {
        int bit = 0;
        int tmp = val.length;
        while (tmp > 0) {
            tmp = tmp >> 1;
            bit++;
        }
        bit = (bit + 2) / 7;
        tmp = val.length;
        data.writeByte((byte) ((bit << 5) | (tmp & 31)));
        tmp = tmp >> 5;
        for (int i = 0; i < bit; i++) {
            data.writeByte((byte) (tmp & 127));
            tmp = tmp >> 7;
        }
        data.writeBytes(val);
    }

    public Msg putBytes(String val) {
        data.writeBytes(val.getBytes(charset));
        return this;
    }

    public String getString() {
        byte d = data.readByte();
        int lenCount = (d >> 5) & 3;//高2位字节数
        int byteCount = d & 31;
        for (int i = 0; i < lenCount; i++) {
            byteCount = byteCount | ((data.readByte() & 255) << (i * 7 + 5));
        }
        if (byteCount == 0) return "";
        String rs = data.readCharSequence(byteCount, charset).toString();
        return rs;
    }

    public void putByte(int val) {
        data.writeByte((byte) (val & 127));
    }

    public void putByteOnMark(int val) {
        data.setByte(marks.remove(marks.size() - 1), (byte) (val & 127));
    }

    public void markByte() {
        if (marks == null) marks = new ArrayList<>();
        marks.add(data.writerIndex());
        data.writeByte(0);
    }

    public byte getByte() {
        return (byte) (data.readByte() & 127);
    }

    public WebSocketFrame getFrame() {
        try{
            return new TextWebSocketFrame(data);
        }finally {
//            data.release();
        }
    }

    public static void main(String[] args) {
        Msg m = new Msg();
        m.putLong(1474440708348L);
        m.putString("世世爱！");
        m.putLong(234);
        m.putString("123你怎么可以生生世世爱！abcABC");
        m.putLong(222333444);
        m.putString("123你怎么可以生生世世爱！abcABC，123你怎么可以生生世世爱！abcABC123你怎么可以生生世世爱！abcABC，123你怎么可以生生世世爱！abcABC123你怎么可以生生世世爱！abcABC，123你怎么可以生生世世爱！abcABC");
//    	m = new Msg(m.data);
        System.out.println(m.getLong());
        System.out.println(m.getString());
        System.out.println(m.getLong());
        System.out.println(m.getString());
        System.out.println(m.getLong());
        System.out.println(m.getString());
    }

    public WebSocketFrame insertTime() {
        int len = this.data.writerIndex();
        Msg time = new Msg(len + 7);
        time.putLong(System.currentTimeMillis());
        time.data.writeBytes(this.data, 0, len);
        return time.getFrame();
    }
}
