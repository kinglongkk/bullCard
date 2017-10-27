package g.service.ai.bull.netHandles;

import g.service.ai.bull.service.AiService;
import g.service.ai.bull.model.AiClient;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.bao.NbBullBaoStartMatchOut;
import g.service.chesscard.netBeans.common.*;
import g.service.chesscard.netBeans.douniu.*;
import g.service.webSocket.context.WsSession;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

import static g.service.chesscard.enums.TipEnum.LOGIN_NO;

/**
 * Created by KXZ on 2016/12/20.
 */
@Service
public class BullHandle {

    @Autowired
    private AiService aiService;

    private static Log log = LogFactory.getLog(BullHandle.class);

    AtomicInteger betCoinCount = new AtomicInteger();
    AtomicInteger betCoinSum = new AtomicInteger();
//    AtomicInteger count2 = new AtomicInteger();

    public void handle(WsSession session, NbIntoRoomOut nb){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        if(!nb.tip.code.equals("0")){
            client.setIntoRoomTime(System.currentTimeMillis());
            aiService.intoRoomFail(client, nb.tip.code);
            return;
        }
        client.setIntoRoomTime(0);
        client.setUsableBalance(nb.nbSelf.usableBalance);
        client.setBalance(nb.nbSelf.balance);
//        System.out.println("---------------Client Count"+betCoinCount.incrementAndGet());
        //如果是主要的客户端,则处理消息
//        log.error("已进入房间:"+count2.incrementAndGet());
        if(client.getOwner().setHeadClient(client)){
            aiService.dealStartMatch(client, nb.match, false, nb);
        }else{
            aiService.scheduleBet(client.getOwner(), client);
        }
    }

    public void handle(WsSession session, NbStartMatchOut nb){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        aiService.dealStartMatch(client, nb.match, true, null);
        if(client.isHead()){
            betCoinCount.set(0);
            betCoinSum.set(0);
        }
    }

    public void handle(WsSession session, NbBullBaoStartMatchOut nb){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        aiService.dealStartMatch(client, nb.match, true, null);
        if(client.isHead()){
            betCoinCount.set(0);
            betCoinSum.set(0);
        }
    }

    public void handle(WsSession session, NbTip nb){
        if(!nb.code.equals("0")){
            log.error("错误提示:"+nb.code+" "+nb.tip);
            if(nb.code.equals(LOGIN_NO.getCode())){
                aiService.disconnected((AiClient)session.getOwner());
            }
        }
    }


    public void handle(WsSession session, NbBetOut obj){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        if(obj.tip.code.equals("0")){
            client.setBetCoins(client.getBetCoins()+obj.bet.gold);
            int cnt = betCoinCount.incrementAndGet();
            betCoinSum.addAndGet(obj.bet.gold);
        }else if(obj.tip.code.equals(TipEnum.BET_MAX_BY_BANKER_LIMIT.getCode())){
            //玩家当庄, 投注已达房间上限,该房间的所有人都不能投注
            client.getOwner().setBetable(false);
        }else{
            client.setBetCounts(10);//当局不能再投注
        }
    }

    public void handle(WsSession session, NbSettlePlayerOut nb){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        client.setBalance(nb.player.balance);
        client.setUsableBalance(nb.usableBalance);
        //根据余额或其余信息决定是否要退出
        aiService.checkUser(client);
    }

    public void handle(WsSession session, NbSelf nb){
    }

    public void handle(WsSession session, NbSettleMatchOut nb){
        AiClient client = (AiClient) session.getOwner();
        if(client == null)return;
        if(client.isHead()){//结算之后不能投注
            log.warn("下注成功数量:" + betCoinCount+", 金额:"+ betCoinSum.get());
            client.getOwner().setBetable(false);
            aiService.resetAiCount(client.getOwner());
        }
    }
/*    public void handle(WsSession session, NbBetBatchOut obj){
        AiClient client = (AiClient) session.getOwner();
        if(client.isHead()){
            log.error("已收到批量下注数量:"+betCoinCount.incrementAndGet());
        }
    }*/
     /*  public void handle(WsSession session, NbTip obj){

    }
    public void handle(WsSession session, NbSettleMatchOut obj){

    }
    public void handle(WsSession session, NbSettlePlayerOut obj){

    }
    public void handle(WsSession session, NbSeatOut obj){

    }
    public void handle(WsSession session, NbUpDealerOut obj){

    }
    public void handle(WsSession session, NbDownDealerOut obj){

    }
    public void handle(WsSession session, NbInsufficientBalanceOut obj){

    }
    public void handle(WsSession session, NbWarningDealerOut obj){

    }*/
}
