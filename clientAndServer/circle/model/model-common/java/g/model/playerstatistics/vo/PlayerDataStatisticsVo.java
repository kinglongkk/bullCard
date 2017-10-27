package g.model.playerstatistics.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.so.PlayerDataStatisticsSo;


/**
 * 玩家数据统计表值对象
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public class PlayerDataStatisticsVo extends BaseObjectVo<PlayerDataStatistics, PlayerDataStatisticsSo, PlayerDataStatisticsVo.PlayerDataStatisticsQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -928244220832613108L;
    //endregion your codes 5

    /**
     *  玩家数据统计表查询逻辑
     */
    public static class PlayerDataStatisticsQuery extends AbstractQuery<PlayerDataStatisticsSo> {

        //region your codes 6
        private static final long serialVersionUID = 6253513055302731521L;
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