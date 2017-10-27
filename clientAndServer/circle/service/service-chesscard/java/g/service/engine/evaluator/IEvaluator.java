package g.service.engine.evaluator;

import g.model.chesscard.mo.UserBet;
import g.model.room.po.RoomPool;
import g.service.chesscard.engine.model.CardsHolder;

import java.util.List;

/**
 * Created by tony on 2016/10/26.
 * 生成扑克平审接口
 */
public interface IEvaluator {

    /**
     * 评审方法
     *  @param userBetList
     */
    CardsHolder evaluate(List<UserBet> userBetList, Integer deskId, boolean isSystemDealer, String preCards, long maxLoseMoney);


}
