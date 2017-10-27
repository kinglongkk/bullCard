package g.model.playerstatistics.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.so.PlayerSummerySo;


/**
 * 玩家数据统计表值对象
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public class PlayerSummeryVo extends BaseObjectVo<PlayerSummery, PlayerSummerySo, PlayerSummeryVo.PlayerSummeryQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 1385135779770465269L;
    //endregion your codes 5

    /**
     *  玩家数据统计表查询逻辑
     */
    public static class PlayerSummeryQuery extends AbstractQuery<PlayerSummerySo> {

        //region your codes 6
        private static final long serialVersionUID = 3184548692111855483L;
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