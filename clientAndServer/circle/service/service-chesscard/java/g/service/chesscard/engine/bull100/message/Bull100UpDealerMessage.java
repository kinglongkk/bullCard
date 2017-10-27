package g.service.chesscard.engine.bull100.message;

import g.service.chesscard.engine.manager.DealerManager;
import g.service.chesscard.engine.sender.AbstractMessage;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.netBeans.common.NbTip;
import g.service.chesscard.netBeans.douniu.NbWarningDealerOut;
import g.service.engine.message.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by tony on 2017/1/9.
 */
public class Bull100UpDealerMessage extends AbstractMessage {

    private int deskId;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    @Autowired
    private DealerManager dealerManager;

    @Override
    public void handleMessage() {
        List<MessageEntity> messageList = new ArrayList<>();
        Map<String, Integer> result = dealerManager.upDealer(getDeskId());
        if (result == null) return;
        if (result.get("down") != null) {
            Integer id = result.get("down");
            messageList.add(setOutValue(id, TipEnum.SUCCESS_DOWN_DEALER));
        }
        if (result.get("up") != null) {
            Integer id = result.get("up");
            messageList.add(setOutValue(id, TipEnum.SUCCESS_UP_DEALER));
        }
        setListMessage(messageList);
    }

    public MessageEntity setOutValue(Integer id, TipEnum tip) {
        MessageEntity msg = new MessageEntity();
        NbWarningDealerOut out = new NbWarningDealerOut();
        out.tip = NbTip.tip(tip);
        msg.setMsg(out);
        msg.setId(id);
        return msg;
    }


}
