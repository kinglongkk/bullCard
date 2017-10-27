package g.service.chesscard.engine.bull100.message;

import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbTimeoutOut;
import g.service.engine.message.MessageEntity;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by tony on 2016/12/20.
 * 清理超时用户
 */
public class Bull100TimeoutMessage extends AbstractMessage {

    private Log log = LogFactory.getLog(Bull100TimeoutMessage.class);

    private Set<String> timeoutUsers;

    public void setTimeoutUsers(Set<String> timeoutUsers) {
        this.timeoutUsers = timeoutUsers;
    }

    @Override
    public void handleMessage() {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        for (String userId : timeoutUsers) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setGroup(false);
            messageEntity.setId(Integer.valueOf(userId));
            NbTimeoutOut timeoutOut = new NbTimeoutOut();
            timeoutOut.tip = NbTip.tip(TipEnum.TIME_OUT);
            messageEntity.setMsg(timeoutOut);
            messageEntityList.add(messageEntity);
            log.warn("下发消息,强制踢出玩家:{0}", userId);
        }
        setListMessage(messageEntityList);
    }
}
