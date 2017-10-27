package g.service.chesscard.engine.bull100.message;

import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.netBeans.douniu.NbInsufficientBalanceOut;
import g.service.engine.manager.IDeskManager;
import g.service.engine.message.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/12/21.
 * 金币不足消息
 */
public class Bull100WarningCoinMessage extends AbstractMessage {

    private int deskId;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private IDeskManager deskManager;

    @Override
    public void handleMessage() {
        List<MessageEntity> messageEntityList = new ArrayList<>();
        Desk desk = deskManager.byId(getDeskId());
        List<Integer> clearUserList = desk.clearDeskUser();
        for (Integer clearUserId : clearUserList) {
            MessageEntity clearUserMessage = new MessageEntity();
            clearUserMessage.setGroup(false);
            clearUserMessage.setId(clearUserId);
            clearUserMessage.setMsg(new NbInsufficientBalanceOut());
            messageEntityList.add(clearUserMessage);
        }
        setListMessage(messageEntityList);
    }

}
