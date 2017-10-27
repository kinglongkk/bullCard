package g.service.ai.bull.service;

import com.alibaba.fastjson.JSON;
import g.model.gameroom.po.PlayerAiControl;
import g.service.ai.bull.manager.AiUserMng;
import g.service.ai.bull.manager.PlayerMng;
import g.service.ai.bull.manager.RoomMng;
import g.service.ai.bull.model.AiRoomControl;
import g.service.ai.bull.model.AiClient;
import g.service.ai.bull.model.AiControl;
import g.service.ai.bull.netHandles.AiWsHandle;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbBet;
import g.service.chesscard.netBeans.common.NbMatch;
import g.service.chesscard.netBeans.douniu.NbExitRoomIn;
import g.service.chesscard.netBeans.douniu.NbIntoRoomOut;
import g.service.chesscard.util.Probability;
import g.service.gameroom.PlayerAiControlService;
import g.service.webSocket.WebSocketClient;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by KXZ on 2016/12/17.
 */
@Service
public class AiService extends JedisPubSub {

    private static Log log = LogFactory.getLog(AiService.class);
    /** 服务的房间数组 */
    private List<Integer> roomIds;
    private String ip;
    private int port;
    @Autowired
    private RoomMng roomMng;
    @Autowired
    private AiUserMng userMng;
    @Autowired
    private PlayerMng playerDao;
    @Autowired
    private AiWsHandle aiWsHandle;
    @Autowired
    private BullSender bullSender;
    /** 游戏服务端URI */
    URI webSocketURI;
    private ConcurrentHashMap<Integer, AiRoomControl> bulls = new ConcurrentHashMap<>();
    public ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
    public ConcurrentHashMap<Integer,ScheduledFuture> scheduleControls = new ConcurrentHashMap<>();
//    @Autowired
//    PlayerAiControlService playerAiControlService;
//    private PlayerAiControl testAi;


    public void start() throws URISyntaxException {
        webSocketURI = new URI("ws://"+ip+":"+port+"/ws");
        /** 需要登录的用户列表 */
        List<PlayerAiControl> rooms = roomMng.getRooms();
//        Random ran = new Random();
        for (PlayerAiControl aiRoom : rooms){
            if(aiRoom.getAiQty() > 0){
                continue;
            }
            if(addControl(aiRoom, true, false) == null){
                System.exit(0);
            }
        }
        for (PlayerAiControl aiRoom : rooms){
            if(aiRoom.getAiQty() == 0){
                continue;
            }
//            testAi = aiRoom;
//            testAi.setBeginControlTime(new Date());
//            testAi.setControlCycle(1);
            scheduleTmpControl(aiRoom, false);
        }
//        test();
        roomMng.subscribe(PlayerAiControlService.CHANNEL_AI_CONTROL, this);
        log.warn("Ai服务器启动完成");
        try {
            synchronized (this){
//                this.wait();
            	this.wait(0);
            }
        } catch (InterruptedException e) {
        }
    }

/*    private void test() {
        for (int i = 0; i < 1; i++) {
            final int i1 = i;
            schedule.schedule(()->{
                testAi.setBeginControlTime(new Date());
                testAi.setControlCycle(2);
//                onMessage(CHANNEL_AI_CONTROL, JSON.toJSONString(testAi));
                playerAiControlService.publishAiPlayerMessage(testAi);
            }, (i+2)*5, TimeUnit.SECONDS);
        }
    }*/

    private void scheduleTmpControl(PlayerAiControl config, boolean update) {
        ScheduledFuture sf = null;
        if(update){
            sf = scheduleControls.remove(config.getId());
            if(sf != null){
                sf.cancel(false);
            }
            AiRoomControl bull = bulls.get(config.getRoomId());
            if(bull != null){
                bull.removeTmpControl(config);//先把上一次调控结果删掉
            }
            if(!config.getStatus().equals("10")){//调度被取消
                return;
            }
        }
        long now = System.currentTimeMillis();
        long beginTime = config.getBeginControlTime().getTime();
        if(now < beginTime){//调控时间未到
            sf = schedule.schedule(()->{
                scheduleControls.remove(config.getId());
                setTmpControl(config);
            },beginTime-now,TimeUnit.MILLISECONDS);
            scheduleControls.put(config.getId(), sf);
        }else if(now > beginTime + config.getControlCycle()*60000L){//已过时
            return;
        }else{
            setTmpControl(config);
        }
    }

    /** 给某个房间设置一个临时策略 */
    public void setTmpControl(PlayerAiControl tmpControl) {
        AiRoomControl bull = bulls.get(tmpControl.getRoomId());
        boolean added = false;
        if(bull == null){
            bull = this.addControl(tmpControl, false, false);
        }
        if(bull == null) return;
        long now = System.currentTimeMillis();
        long endTime = tmpControl.getBeginControlTime().getTime()+ tmpControl.getControlCycle()*60000L;
        if(endTime<now) return;
        //调度在调控结束后
        ScheduledFuture sf = schedule.schedule(()->{
            scheduleControls.remove(tmpControl.getId());
            removeTmpControl(tmpControl);
        },endTime-now,TimeUnit.MILLISECONDS);
        scheduleControls.put(tmpControl.getId(), sf);

        bull.addTmpControl(tmpControl);
    }

    private void removeTmpControl(PlayerAiControl tmpControl) {
        log.warn("控制已失效,id:{1},数量:{2},模式:{3}", tmpControl.getId(), tmpControl.getAiQty(), tmpControl.getControlMode());
        AiRoomControl bull = bulls.get(tmpControl.getRoomId());
        if(bull == null) return;
        bull.removeTmpControl(tmpControl);
        for (AiClient client : bull.getToLogins()) {
//            if(client.getControl().getConfig().getId() == tmpControl.getId()){
        	if (Objects.equals(client.getControl().getConfig().getId(), tmpControl.getId())) {
                client.setTmpControl(null);//取消临时调控
            }
        }
        Iterator<AiClient> it =bull.getClients().iterator();
        while(it.hasNext()){
            AiClient client = it.next();
//            if(client.getControl().getConfig().getId() == tmpControl.getId()){
            if (Objects.equals(client.getControl().getConfig().getId(), tmpControl.getId())) {
                client.setTmpControl(null);//取消临时调控
            }
        }
    }

    /**  给一个房间的AI添加基本调控 */
    private AiRoomControl addControl(PlayerAiControl control, boolean isBase, boolean update) {
        AiRoomControl roomControl = null;
        AiControl aiControl = new AiControl(control);
        if(!update){
            roomControl = createBull(control);
        }else{
            roomControl = bulls.get(control.getRoomId());
            if(roomControl == null){
                update = false;
                roomControl = createBull(control);
            }
        }
        if(isBase) {
            roomControl.setAiRatios(roomMng.getAiRatios(control.getId()));
            roomControl.setNormaledControled(true);
            if(roomControl.getAiRatios().size() == 0){
                log.error("没有配置PlayerAiRatioControl给AiRoom id:"+control.getId());
                return null;
            }
        }
        if(!update){
            List<long[]> userIds = roomMng.getUserIds(control.getRoomId());
            if(userIds.size()  == 0){
                log.error("没有配置ai_user给PlayerAiControl id:"+control.getId());
                return null;
            }
            int count = 500;
            int tmpCount = isBase?0:control.getAiQty();
            for(long[] os: userIds){
                int userId = (int)os[0];
                long balance = os[1];
                AiClient client = new AiClient(null, userId, balance, roomControl);
                if(tmpCount-- > 0){//不是基本调控的话
                    client.setTmpControl(aiControl);
                }
                roomControl.getToLogins().add(client);
                if(--count==0)break;
            }
            resetAiCount(roomControl);
            login(roomControl);
            exit(roomControl);
        }
        return roomControl;
    }

    private AiRoomControl createBull(PlayerAiControl aiRoom) {
        ScheduledExecutorService schedule = Executors.newScheduledThreadPool(8);
        AiRoomControl roomControl = new AiRoomControl(aiRoom, schedule);
        bulls.put(aiRoom.getRoomId(), roomControl);
        WebSocketClient client = new WebSocketClient(webSocketURI, aiWsHandle);
        roomControl.setWsClient(client);
        return roomControl;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    /**
     * 监听到订阅频道接受到消息时的回调
     * @param channel 频道
     * @param message 消息
     */
    @Override
    public void onMessage(String channel, String message) {
        try {
            if(channel.startsWith(PlayerAiControlService.CHANNEL_AI_CONTROL)){
                //取出我们想要的内容
                PlayerAiControl aiControl = JSON.parseObject(message, PlayerAiControl.class);
                log.warn("接收到临时推送");
                if(aiControl.getAiQty() == 0){
                    addControl(aiControl, true, true);
                }else{
                    scheduleTmpControl(aiControl, true);
                }
            }
            log.info(String.format("Message. Channel: %s, Msg: %s", channel, message));
        } catch (Exception e) {
            log.error(e);
        }

    }

    public void resetAiCount(AiRoomControl roomControl) {
        int[] rs = roomMng.getPlayerCountAndMax(roomControl.getRoomId());
        int diff = roomControl.resetAiCount(rs[1], rs[0]);
        if(diff < 0 && roomControl.getControl().getConfig().getRestMaxGames() == 0){//调控数量减少 但是比赛场数无限
            reduceAi(roomControl, -diff, null);
        }
    }

    private void reduceAi(AiRoomControl bull, int count, AiControl control) {
        Iterator<AiClient> it = bull.getClients().iterator();
        while (it.hasNext()) {
            AiClient client =it.next();
            if(count--==0)break;
//            if(control == null || client.getControl().getConfig().getId()==control.getConfig().getId()){
            if (control == null || Objects.equals(client.getControl().getConfig().getId(), control.getConfig().getId())) {
                it.remove();
                client.setTmpControl(control);
                bull.getToExits().add(client);
            }
        }
    }

    public void login(AiRoomControl roomControl) {
        int delayTime = 2000;
        try {
            if(roomControl.getClients().size() >= roomControl.getAiNum()){
                if(roomControl.getAiNum()==0 && roomControl.getClients().size()==0){
                    delayTime = 15000;
                    resetAiCount(roomControl);
                }
                return;
            }
            AiClient client = roomControl.getToLoginClient();
            if (client == null) {
                client = roomControl.getToLogins().poll();
            }
            if (client != null) {
                WsSession session = roomControl.getWsClient().conectToServer();
                if (session == null) {
                    roomControl.setToLoginClient(client);
                }else{
                    client.setMatchCount(client.getControl().getRandomMatchCount());
                    client.setSession(session);
                    roomControl.setToLoginClient(null);
                    roomControl.getClients().add(client);
                    //登录前检查金额是否符合调控设定
                    if(client.getBalance() > client.getControl().getConfig().getBringGoldMax() || client.getBalance()<client.getControl().getConfig().getBringGoldMin()){
                        long ranCoin = client.getControl().getCoinRandom();
                        playerDao.addCoin(client.getUserId(), ranCoin-client.getBalance());
                        client.setBalance(ranCoin);
                    }
                    bullSender.sendLogin(session, client.getUserId());//TODO 到时要改成token登录
                    bullSender.sendIntoRoom(session, roomControl.getRoomId());
                    delayTime = client.getControl().getLoginDelay();
                }
            }
        }catch (Exception e){
            log.error(e);
        }finally {
            roomControl.schedule.schedule(()->login(roomControl), delayTime, TimeUnit.MILLISECONDS);
        }
    }

    public void exit(AiRoomControl roomControl) {
        AiClient client = null;
        try {
            client = roomControl.getToExits().poll();
            if (client != null) {
                disconnected(client);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e);
        }finally {
            roomControl.schedule.schedule(()->exit(roomControl),
                    client==null ? roomControl.getControl().getExitDelay() : client.getControl().getExitDelay(),
                    TimeUnit.MILLISECONDS);
        }
    }

    /** 结束AI服务器 */
   /* public void stop(){
        synchronized (this){
            this.notify();
        }
    }*/

    public void setBullSender(BullSender bullSender) {
        this.bullSender = bullSender;
    }

    public void setUserMng(AiUserMng userMng) {
        this.userMng = userMng;
    }

    public void setRoomMng(RoomMng roomMng) {
        this.roomMng = roomMng;
    }

    public void setRoomIds(List<Integer> roomIds) {
        this.roomIds = roomIds;
    }

    public List<Integer> getRoomIds() {
        return roomIds;
    }

    public boolean setHeadClient(AiClient client) {

        return true;
    }

    AtomicInteger count = new AtomicInteger();
    AtomicInteger count1 = new AtomicInteger();
    public void dealStartMatch(AiClient client, NbMatch match, boolean newGame, NbIntoRoomOut nb) {
        if(client == null) return;
        AiRoomControl bull = client.getOwner();
        if(newGame){
            AtomicLong settleTime = bull.getSettleTime1();
            long time = settleTime.get();
            if(time >= match.settleTime) return;
            if(settleTime.compareAndSet(time, match.settleTime)){
                bull.setHeadClient(client);
            }else{
                return;
            }
        }else{
            bull.setSettleTime(match.settleTime);
        }
        bull.setStratTime(match.beginTimeNext);
        bull.setMatchId(match.matchId);
        if(newGame){
            bull.setBetable(true);
            count.set(0);
            count1.set(0);
            for(Iterator<AiClient> it = bull.getClients().iterator(); it.hasNext();){
                AiClient c = it.next();
                c.setBetCounts(0);
                c.setBetCoins(0);
                scheduleBet(bull, c);
            }
        }else {
            if(nb != null){
                bull.setBetChip(nb.betChip);
                bull.setBetTimes(nb.betTimes);
            }
            scheduleBet(bull, client);
        }
    }

    /** 调度押注 */
    public void scheduleBet(AiRoomControl bull, AiClient client) {
        if(!bull.getBetable()) return;
        Random ran = new Random();
        int delayTime = ran.nextInt(EngineConst.MATCH_START_STOP_TIME_OFFSET*1000*2/client.getControl().getConfig().getBetCount());
        if(client.getSession() == null) return;
        long serverNow = client.getSession().getServerTime();
        int betCount = client.getBetCounts()+1;
        client.setBetCounts(betCount);
        if(betCount > client.getControl().getConfig().getBetCount() || (client.getUsableBalance()-client.getBetCoins())<bull.getBetChip()[0]){
            //投注次数或者金币限制
        }else if(bull.getSettleTime() > serverNow + delayTime){
            bull.schedule.schedule(()->bull100Bet(client), delayTime, TimeUnit.MILLISECONDS);
        }
    }

    /** 每局都要为AI重新创建, 每个投注区域的投中概率 */
    private void createAreaRates(AiClient client) {
        AiRoomControl bull = client.getOwner();
        int[] rates = new int[bull.getBetAreas().length];
        Random ran = new Random();
        for (int i = 0; i < rates.length; i++) {
            if(bull.getType().equals(AiRoomControl.BULL_TYPE_100)){
                int index = ran.nextBoolean()?i:i+4;
                rates[index] = ran.nextInt(4)*3+1;
                if(i==3)break;
            }else if(bull.getType().equals(AiRoomControl.BULL_TYPE_BAO)){
                rates[i] = (ran.nextInt(5)*2+1)*(i==0?5:1);//没牛有5倍的概率命中
            }
        }
        client.setAreaRates(new Probability(rates));
    }

    public void bull100Bet(AiClient client) {
        try {
            if (!client.getOwner().getBetable() || client.getSession() == null) return;
            if (client.getBetCounts() == 1 || client.getAreaRates() == null) {
                //第一次投注选择区域
                createAreaRates(client);
            }
            NbBet bet = new NbBet();
            AiRoomControl bull = client.getOwner();
            int goldIndex = client.getControl().getBetChipIndex();
            for (; goldIndex >= 0; goldIndex--) {
                bet.gold = bull.getBetChip()[goldIndex];
                if (client.getUsableBalance() >= client.getBetCoins() + bet.gold) {
                    break;
                }
            }
            if (goldIndex == -1) {
                return;//没钱投注!
            }
            bet.matchId = bull.getMatchId();
            bet.item = bull.getBetAreas()[client.getAreaRates().nextIndex()];
            bet.type = bull.getType();
            WsSession session = client.getSession();
            if (session != null) {
                session.sendNetBean(bet);
                scheduleBet(client.getOwner(), client);
            } else {
                scheduleBet(client.getOwner(), client);
                return;
            }
            int c = count.incrementAndGet();
            count1.addAndGet(bet.gold);
            if (c % 50 == 0) {
                log.warn("下注数量:" + c + ",金额:" + count1.get());
            }
        }catch (Exception e){
            log.error(e);
        }
    }

    public void disconnected(AiClient aiClient) {
        WsSession session = aiClient.getSession();
        if(session != null){
            session.setOwner(null);
            session.close();
            aiClient.setSession(null);
        }
        aiClient.getOwner().remove(aiClient);
        aiClient.setTmpControl(null);
        aiClient.getOwner().getToLogins().add(aiClient);
    }

    public void intoRoomFail(AiClient client, String code) {
        if(code.equals(TipEnum.PLAYER_IN_MATCH.getCode())){
            client.getOwner().schedule.schedule(() -> {
                bullSender.sendIntoRoom(client.getSession(), client.getOwner().getRoomId());
            }, EngineConst.MATCH_START_STOP_TIME_OFFSET, TimeUnit.SECONDS);
        }else if(code.equals(TipEnum.BALANCE_LESS.getCode())){
            playerDao.addCoin(client.getUserId(), client.getControl().getCoinRandom()-client.getBalance());
            bullSender.sendIntoRoom(client.getSession(), client.getOwner().getRoomId());
        }else if(code.equals(TipEnum.ROOM_FULL.getCode())){
            disconnected(client);
        }else{//ROOM_FULL 不管
            disconnected(client);
        }
    }

    /** 游戏结算后检查余额 */
    public void checkUser(AiClient client) {
        PlayerAiControl config  = client.getControl().getConfig();
        if(client.getBalance() < config.getBringGoldMin() || client.getBalance() > config.getBringGoldMax()){
            NbExitRoomIn nb = new NbExitRoomIn();
            nb.roomId = config.getRoomId();
            nb.isBreak = true;
            client.getSession().sendNetBean(nb);
            playerDao.addCoin(client.getUserId(), client.getControl().getCoinRandom());
            disconnected(client);
        }else if(client.getMatchCount()!=0){
            client.setMatchCount(client.getMatchCount()-1);
            if(client.getMatchCount()==0){
                client.setMatchCount(1);//永远不会变成0
                client.getOwner().addToExits(client, true);
            }
        }
    }


}
