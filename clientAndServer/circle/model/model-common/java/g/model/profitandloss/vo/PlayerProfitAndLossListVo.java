package g.model.profitandloss.vo;

import g.model.profitandloss.po.PlayerProfitAndLoss;
import g.model.profitandloss.so.PlayerProfitAndLossSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 玩家盈亏列表页值对象
 *
 * @author black
 * @time 2017-5-31 15:15:09
 */
//region your codes 1
public class PlayerProfitAndLossListVo extends BaseListVo<PlayerProfitAndLoss, PlayerProfitAndLossSo, PlayerProfitAndLossListVo.PlayerProfitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5908974946694163416L;
    //endregion your codes 5

    /**
     *  玩家盈亏列表查询逻辑
     */
    public static class PlayerProfitQuery extends AbstractQuery<PlayerProfitAndLossSo> {

        //region your codes 6
        private static final long serialVersionUID = -6545502807550451791L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.and(

                    Criteria.add(PlayerProfitAndLoss.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(PlayerProfitAndLoss.PROP_GATHER_TIME, Operator.GE, this.searchObject.getStartTime()),
                    Criteria.add(PlayerProfitAndLoss.PROP_GATHER_TIME, Operator.LE, this.searchObject.getEndTime()),
                    Criteria.add(PlayerProfitAndLoss.PROP_PLAYER_ID, Operator.LIKE, this.searchObject.getPlayerId())
            );
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}