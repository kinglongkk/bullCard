package g.service.chesscard.engine.evaluator;

import g.model.chesscard.mo.UserBet;
import g.model.room.po.RoomPool;
import g.service.chesscard.engine.model.CardsHolder;
import g.service.engine.evaluator.IEvaluator;

import java.util.List;

/**
 * Created by tony on 2016/10/27.
 * 生成扑克平审基类
 * PARAMS 基础参数 POOL 资金池 MANAGER 资金池管理
 */
public abstract class AbstractEvaluator implements IEvaluator {

    public abstract CardsHolder evaluate(List<UserBet> userBetList, Integer deskId, boolean isSystemDealer,
                                         String preCards, long maxLoseMoney);

    /**
     * 评审基本参数
     */
    protected static class Params {

        private int deskId;

        /**
         * 获取桌子id
         */
        public int getDeskId() {
            return deskId;
        }

        /**
         * 设置桌子id
         */
        public void setDeskId(int deskId) {
            this.deskId = deskId;
        }

    }

}
