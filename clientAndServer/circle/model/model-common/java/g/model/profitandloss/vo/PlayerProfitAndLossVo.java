package g.model.profitandloss.vo;

import g.model.profitandloss.po.PlayerProfitAndLoss;
import g.model.profitandloss.so.PlayerProfitAndLossSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 玩家盈亏值对象
 *
 * @author black
 * @time 2017-5-31 15:15:10
 */
//region your codes 1
public class PlayerProfitAndLossVo extends BaseObjectVo<PlayerProfitAndLoss, PlayerProfitAndLossSo, PlayerProfitAndLossVo.PlayerProfitAndLossQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4297192757026422139L;
    //endregion your codes 5

    /**
     *  玩家盈利榜查询逻辑
     */
    public static class PlayerProfitAndLossQuery extends AbstractQuery<PlayerProfitAndLossSo> {

        //region your codes 6
        private static final long serialVersionUID = -971805268794551546L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}