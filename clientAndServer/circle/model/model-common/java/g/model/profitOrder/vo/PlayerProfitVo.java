package g.model.profitOrder.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.profitOrder.po.PlayerProfit;
import g.model.profitOrder.so.PlayerProfitSo;


/**
 * 玩家盈利榜值对象
 *
 * @author black
 * @time 2016-11-4 10:15:10
 */
//region your codes 1
public class PlayerProfitVo extends BaseObjectVo<PlayerProfit, PlayerProfitSo, PlayerProfitVo.PlayerProfitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4297192757026422139L;
    //endregion your codes 5

    /**
     *  玩家盈利榜查询逻辑
     */
    public static class PlayerProfitQuery extends AbstractQuery<PlayerProfitSo> {

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