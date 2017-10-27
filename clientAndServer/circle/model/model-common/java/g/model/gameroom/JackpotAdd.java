package g.model.gameroom;

/**
 * 金币池的加减封装
 * Created by MK on 2017/2/28.
 */
public class JackpotAdd {

    private int roomId;
    /** 池金额增加 */
    private long jackpotAdd;
    /**池溢出金额增加*/
    private long jackpotOverflowAdd;
    /** 流水抽水 */
    private int singleFee;

    public JackpotAdd(int roomId, long jackpotAdd, long jackpotOverflowAdd) {
        this.roomId = roomId;
        this.jackpotAdd = jackpotAdd;
        this.jackpotOverflowAdd = jackpotOverflowAdd;
    }

    public void set(long jackpotAdd, long jackpotOverflowAdd) {
        this.jackpotAdd = jackpotAdd;
        this.jackpotOverflowAdd = jackpotOverflowAdd;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getJackpotAdd() {
        return jackpotAdd;
    }

    public void setJackpotAdd(long jackpotAdd) {
        this.jackpotAdd = jackpotAdd;
    }

    public long getJackpotOverflowAdd() {
        return jackpotOverflowAdd;
    }

    public void setJackpotOverflowAdd(long jackpotOverflowAdd) {
        this.jackpotOverflowAdd = jackpotOverflowAdd;
    }

    public int getSingleFee() {
        return singleFee;
    }

    public void setSingleFee(int singleFee) {
        this.singleFee = singleFee;
    }
}
