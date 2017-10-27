package g.service.engine.manager;

import g.service.chesscard.engine.model.Desk;

/**
 * Created by tony on 2017/1/10.
 * 投注金币限制
 */
public interface IBetCoinLimitManager {

    void init(Integer deskId);

    void canBetting(Desk desk, long coin, String code);

    void clear(Integer deskId);

    void destroy(Integer deskId);

}
