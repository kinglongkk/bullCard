package g.service.ai.bull.model;

import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.service.webSocket.WebSocketClient;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 百人大战玩法
 * Created by MK on 2016/12/20.
 */
public class AiRoomControl {

    private CopyOnWriteArrayList<AiClient> clients = new CopyOnWriteArrayList<>();
    /** 接收消息的client */
    private AtomicReference<AiClient> headClient = new AtomicReference<>();
    public ScheduledExecutorService schedule;
    /** 比赛开始时间 */
    private long stratTime;
    /** 结算时间 */
    private AtomicLong settleTime = new AtomicLong();
    private long matchId;
    /** 该房间当局是否可投注 */
    private AtomicBoolean betable = new AtomicBoolean(true);
    private WebSocketClient wsClient;
    private AiClient toLoginClient;
    private AiControl control;

    private ConcurrentLinkedDeque<AiClient> toLogins = new ConcurrentLinkedDeque<>();
    private ConcurrentLinkedDeque<AiClient> toExits = new ConcurrentLinkedDeque<>();

    private String type;
    private String[] betAreas;
    private List<PlayerAiRatioControl> aiRatios;
    private int betTimes;
    private Integer roomId;
    private int[] betChip;
//    private int playerMax = 0;
//    private int playerCount = 0;
    /** 随机的AI进房人数 */
    private volatile int aiRandomCount = 0;
    /** 临时策略增加或者减少的AI进房人数 */
    private volatile int aiTempCount = 0;
    /** 是否有永久控制的PlayerAiControl */
    private volatile boolean normaledControled = false;
    /** 临时控制列表 */
    private CopyOnWriteArrayList<PlayerAiControl> tmpControls = new CopyOnWriteArrayList<>();

    public final static String BULL_TYPE_100 = "BULL_100";
    public final static String BULL_TYPE_BAO = "BULL_BAO";
    static String[] betItems100 = {"SPADE_W","HEART_W","CLUB_W","DIAMOND_W","SPADE_L","HEART_L","CLUB_L","DIAMOND_L"};
    static String[] betItemsBao = {"0_W","1_W","2_W","3_W","4_W","5_W","6_W","7_W","8_W","9_W","10_W",};

    public AiRoomControl(PlayerAiControl aiRoom, ScheduledExecutorService schedule) {
        setAiRoom(aiRoom);
        this.schedule = schedule;
    }

    public CopyOnWriteArrayList<AiClient> getClients() {
        return clients;
    }

    public AiControl getControl() {
        return control;
    }

    public void addTmpControl(PlayerAiControl tmp){
        aiTempCount += (tmp.getControlMode().equals("1")?1:-1)*tmp.getAiQty();
        tmpControls.add(tmp);
    }

    public void removeTmpControl(PlayerAiControl tmp){
        PlayerAiControl c = null;
        for (Iterator<PlayerAiControl> it = tmpControls.iterator(); it.hasNext(); ) {
            c=it.next();
//            if(c.getId() == tmp.getId()){
            if (c != null && Objects.equals(c.getId(), tmp.getId())) {
                aiTempCount -= c.getAiQty();//扣除原有调控的数量
                tmpControls.remove(c);
                break;
            }
        }
    }

    public void setAiRoom(PlayerAiControl aiRoom) {
        this.control = new AiControl(aiRoom);
        if(aiRoom.getGameModel() == 1){
            type = BULL_TYPE_100;
            betAreas = betItems100;
        }else if(aiRoom.getGameModel() == 3){
            type = BULL_TYPE_BAO;
            betAreas = betItemsBao;
        }
    }

    public ScheduledExecutorService getSchedule() {
        return schedule;
    }

    public AiClient getHeadClient() {
        return headClient.get();
    }

    public boolean setHeadClient(AiClient headClient) {
        if(this.headClient.compareAndSet(null, headClient)){
            return true;
        }else{
            return false;
        }
    }

    public boolean setHeadClient(AiClient headClient,boolean replace) {
        if(replace){
            this.headClient.set(headClient);
            return  true;
        }
        return this.headClient.compareAndSet(null, headClient);
    }

    public long getStratTime() {
        return stratTime;
    }

    public void setStratTime(long stratTime) {
        this.stratTime = stratTime;
    }

    public long getSettleTime() {
        return settleTime.get();
    }

    public AtomicLong getSettleTime1() {
        return settleTime;
    }

    public void setSettleTime(long settleTime) {
        this.settleTime.set(settleTime);
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setBetable(boolean betable) {
        this.betable.set(betable);
    }

    public boolean getBetable() {
        return betable.get();
    }

    public void setWsClient(WebSocketClient wsClient) {
        this.wsClient = wsClient;
    }

    public WebSocketClient getWsClient() {
        return wsClient;
    }

    public AiClient getToLoginClient() {
        return toLoginClient;
    }

    public void setToLoginClient(AiClient toLoginClient) {
        this.toLoginClient = toLoginClient;
    }

    public void remove(AiClient aiClient) {
        clients.remove(aiClient);
        if(aiClient.isHead()){
            for (AiClient c : clients) {
                headClient.compareAndSet(aiClient, c);
                break;//只设置第一个
            }
            if(headClient.get() == aiClient){//没人了
                headClient.compareAndSet(aiClient, null);
            }
        }
    }

    /** 获取该房间需要的ai数 */
    public int getAiNum() {
        return aiRandomCount+aiTempCount;
    }

    public ConcurrentLinkedDeque<AiClient> getToLogins() {
        return toLogins;
    }
    public ConcurrentLinkedDeque<AiClient> getToExits() {
        return toExits;
    }

    public String getType() {
        return type;
    }

    public String[] getBetAreas() {
        return betAreas;
    }

    public List<PlayerAiRatioControl> getAiRatios() {
        return aiRatios;
    }

    public void setAiRatios(List<PlayerAiRatioControl> aiRatios) {
        this.aiRatios = aiRatios;
    }

    /** 重置基本调控的ai数量, 返回重置后的数量差 */
    public int resetAiCount(int playerMax, int playerCount) {
        if(!normaledControled){//没有普通控制的话随机数为0
            aiRandomCount = 0;
            return 0;
        }
        int oldCount = aiRandomCount;
        int count = playerCount - clients.size();
        int ratio = count * 100 / playerMax;
        PlayerAiRatioControl aiRatio = null;
        for (int i = 0; i < aiRatios.size(); i++) {
            aiRatio = aiRatios.get(i);
            if (ratio < aiRatio.getPlayerProportionMax()) {
                break;
            }
        }
        aiRandomCount = new Random().nextInt((aiRatio.getAiProportionMax() - aiRatio.getAiProportionMin())* playerMax / 100+1)
                + aiRatio.getAiProportionMin()* playerMax/100;
        return aiRandomCount - oldCount;
    }

    public boolean isNormaledControled() {
        return normaledControled;
    }

    public void setNormaledControled(boolean normaledControled) {
        this.normaledControled = normaledControled;
    }

    public int[] getBetChip() {
        return betChip;
    }

    public void setBetChip(int[] betChip) {
        this.betChip = betChip;
    }
    public int getBetTimes() {
        return betTimes;
    }

    public void setBetTimes(int betTimes) {
        this.betTimes = betTimes;
    }

    public Integer getRoomId() {
        return control.getRoomId();
    }

    public void addToExits(AiClient client, boolean remove) {
        if(remove)clients.remove(client);
        toExits.add(client);
    }
}
