package g.model.room.po;

/**
 * 房间奖池数据
 * Created by black on 2017/2/24.
 */
public class RoomPool {

    public static final String PROP_JACKPOT = "jackpot";
    public static final String PROP_MAX_LIMIT_GAME_LOSE = "maxLimitGameLose";
    public static final String PROP_MIN_JACKPOT_LIMIT = "minJackpotLimit";
    public static final String PROP_MAX_JACKPOT_LIMIT = "maxJackpotLimit";
    public static final String PROP_JACKPOT_OVERFLOW = "jackpotOverflow";
    public static final String PROP_MAX_JACKPOT_A_MATCH = "maxJackpotAmatch";

    /** 奖池实际金额 */
    private Long jackpot;
    /** 单局最高可输金额 */
    private Long maxLimitGameLose;
    /** 奖池最低下限金额 */
    private Long minJackpotLimit;
    /** 奖池最高积累金额 */
    private Long maxJackpotLimit;
    /** 奖池溢出金额 */
    private Long jackpotOverflow;
    /** 单局奖池可溢出金额,溢出部分全部抽取 */
    private Long maxJackpotAmatch;

    public Long getJackpot() {
        return jackpot;
    }

    public void setJackpot(Long jackpot) {
        this.jackpot = jackpot;
    }

    public Long getMaxLimitGameLose() {
        return maxLimitGameLose;
    }

    public void setMaxLimitGameLose(Long maxLimitGameLose) {
        this.maxLimitGameLose = maxLimitGameLose;
    }

    public Long getMinJackpotLimit() {
        return minJackpotLimit;
    }

    public void setMinJackpotLimit(Long minJackpotLimit) {
        this.minJackpotLimit = minJackpotLimit;
    }

    public Long getMaxJackpotLimit() {
        return maxJackpotLimit;
    }

    public void setMaxJackpotLimit(Long maxJackpotLimit) {
        this.maxJackpotLimit = maxJackpotLimit;
    }

    public Long getJackpotOverflow() {
        return jackpotOverflow;
    }

    public void setJackpotOverflow(Long jackpotOverflow) {
        this.jackpotOverflow = jackpotOverflow;
    }

    public Long getMaxJackpotAmatch() {
        return maxJackpotAmatch;
    }

    public void setMaxJackpotAmatch(Long maxJackpotAmatch) {
        this.maxJackpotAmatch = maxJackpotAmatch;
    }

    public long getMaxLose() {
        if (jackpot <= minJackpotLimit) {
            return 0;
        }
        if ((jackpot - maxLimitGameLose) <= minJackpotLimit) {
            return jackpot - minJackpotLimit;
        }
        return maxLimitGameLose;
    }
}
