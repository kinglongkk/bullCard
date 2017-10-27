package g.service.ai.bull.model;

import g.service.chesscard.util.Probability;
import g.service.webSocket.context.WsSession;

/**
 * Created by MK on 2016/12/20.
 */
public class AiClient {

    private WsSession session;
    private Integer userId;
    private AiRoomControl owner;
    private AiControl tmpControl;
    /** 是否已经进房间 */
    private boolean inRoom = false;
    /** 下次投注的延迟时间 */
    private int delayTime;
    /** 当局已投注次数 */
    private int betCounts = 0;
    /** 当局已投注总额 */
    private volatile long betCoins;
    /** 持有金币数 */
    private long balance = 0;
    /** 持有可用金币数 */
    private long usableBalance = 0;
    /** 投注区域的概率 */
    private Probability areaRates;
    /** 投注区域的概率 */
    private int matchCount = 0;
    /** 最后进入房间时间 */
    private long intoRoomTime = 0;


    public AiClient(WsSession session, int userId, long balance, AiRoomControl owner) {
        this.session = session;
        this.userId = userId;
        this.owner = owner;
        this.balance = balance;
        setSession(session);
    }

    public boolean isHead() {
        return owner.getHeadClient() == this;
    }

    public AiRoomControl getOwner() {
        return owner;
    }

    public void setOwner(AiRoomControl owner) {
        this.owner = owner;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public WsSession getSession() {
        return session;
    }

    public void setSession(WsSession session) {
        this.session = session;
        if(session != null){
            session.setOwner(this);
        }
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public void setInRoom(boolean inRoom) {
        this.inRoom = inRoom;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public int getBetCounts() {
        return betCounts;
    }

    public void setBetCounts(int betCounts) {
        this.betCounts = betCounts;
    }

    public long getUsableBalance() {
        return usableBalance;
    }

    public void setUsableBalance(long usableBalance) {
        this.usableBalance = usableBalance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Probability getAreaRates() {
        return areaRates;
    }

    public void setAreaRates(Probability areaRates) {
        this.areaRates = areaRates;
    }

    public long getBetCoins() {
        return betCoins;
    }

    public void setBetCoins(long betCoins) {
        this.betCoins = betCoins;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public AiControl getControl() {
        return tmpControl == null ? owner.getControl() : tmpControl;
    }

    public void setTmpControl(AiControl tmpControl) {
        this.tmpControl = tmpControl;
    }

    public long getIntoRoomTime() {
        return intoRoomTime;
    }

    public void setIntoRoomTime(long intoRoomTime) {
        this.intoRoomTime = intoRoomTime;
    }
}
