package g.service.chesscard.engine.manager;

import g.service.chesscard.DouniuMultiple;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.manager.IBetCoinLimitManager;
import g.service.engine.manager.IDeskManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tony on 2017/1/10.
 */
public class BetCoinLimitManager implements IBetCoinLimitManager {

    @Autowired
    private IDeskManager deskManager;

    private final static Map<Integer, AtomicLong> mDeskBetCoinTemp = new HashMap<>();


    public void init(Integer deskId) {
        mDeskBetCoinTemp.put(deskId, new AtomicLong());
    }

    @Override
    public void canBetting(Desk desk, long coin, String betType) {
        long value = incr(desk, coin);
        if ((desk.getDealerUserId() != null && value > desk.getDealerBalanceCoin() / DouniuMultiple.getDouniuMaxMultiple(betType))) {//玩家庄
            decr(desk, coin);
            throw new ChessCardException(TipEnum.BET_MAX_BY_BANKER_LIMIT);
        }
    }

    public void clear(Integer deskId) {
        mDeskBetCoinTemp.get(deskId).set(0L);
    }

    public void destroy(Integer deskId) {
        mDeskBetCoinTemp.remove(deskId);
    }


    private long incr(Desk desk, long coin) {
        return mDeskBetCoinTemp.get(desk.getId()).addAndGet(coin);
    }

    private long decr(Desk desk, long coin) {
        return mDeskBetCoinTemp.get(desk.getId()).addAndGet(-coin);
    }


}
