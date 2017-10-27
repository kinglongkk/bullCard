package g.service.chesscard.engine.bull100.message;

import g.model.chesscard.mo.UserBet;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.netBeans.common.NbBet;
import g.service.chesscard.netBeans.douniu.NbBetBatchOut;
import g.service.engine.manager.IBetManager;
import g.service.engine.message.MessageEntity;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class Bull100BetMessage extends AbstractMessage {

    private static final Log log = LogFactory.getLog(Bull100BetMessage.class);
    private Integer deskId;

    public Integer getDeskId() {
        return deskId;
    }

    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private IBetManager betManager;

    @Override
    public void handleMessage() {
        final Integer deskId = this.getDeskId();
        if (deskId == null) {
            log.warn("The desk id is null!");
            return;
        }
        long now = System.currentTimeMillis();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setGroup(true);
        messageEntity.setId(deskId.intValue());
        NbBetBatchOut betOut = new NbBetBatchOut();
        //TODO DOUBLE 在一局有900投注量的情况下,下面这行代码需要花费800左右ms
        List<UserBet> userBets = betManager.getUserBetStatistics(deskId);
        betOut.bets = NbBet.fromUserBets(userBets);
        messageEntity.setMsg(betOut);
        setObjMessage(messageEntity);
        now = System.currentTimeMillis() - now;
        if (now > 400) {
            log.warn("投注下发时间过长(ms):" + now);
        }
    }
}
