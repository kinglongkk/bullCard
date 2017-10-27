package g.service.chesscard.engine.listener;

import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Double on 2016/9/30.
 * 桌子监听器
 */
public class DeskListener extends AbstractDeskListener {

    private static Log log = LogFactory.getLog(DeskListener.class);

    @Override
    public void onNew(DeskEvent deskEvent) {
        super.onNew(deskEvent);
    }

    @Override
    public void onDestroy(DeskEvent deskEvent) {
    }

    @Override
    public void onSeat(DeskPlayerEvent deskEvent) {
    }

    @Override
    public void onLeave(DeskPlayerEvent deskEvent) {
    }

}
