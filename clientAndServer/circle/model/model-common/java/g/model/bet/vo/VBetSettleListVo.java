package g.model.bet.vo;

import g.model.bet.BetStatus;
import g.model.bet.po.VBetSettle;
import g.model.bet.so.VBetSettleSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 注单结算列表页值对象
 *
 * @author longer
 * @time May 19, 2016 1:42:07 PM
 */
//region your codes 1
public class VBetSettleListVo extends BaseListVo<VBetSettle, VBetSettleSo, VBetSettleListVo.VBetSettleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -380552709263494258L;
    //endregion your codes 5

    /**
     *  注单结算列表查询逻辑
     */
    public static class VBetSettleQuery extends AbstractQuery<VBetSettleSo> {

        //region your codes 6
        private static final long serialVersionUID = 2994930616103205266L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        /**
         * 待结算
         * @return
         */
        public Criteria byBallTypeAndMatchId(){
            return Criteria.and(
                    Criteria.add(VBetSettle.PROP_STATUS, Operator.EQ, BetStatus.CONFIRMD.getCode()),
                    Criteria.add(VBetSettle.PROP_SETTLE_STATUS, Operator.EQ, this.searchObject.getSettleStatus()),
                    Criteria.add(VBetSettle.PROP_BALL_TYPE, Operator.EQ, this.searchObject.getBallType()),
                    Criteria.add(VBetSettle.PROP_MATCH_ID, Operator.EQ, this.searchObject.getMatchId())
            );
        }

        /**
         * 指定赛事ID,未取消的注单
         * @return
         */
        public Criteria byMatchIdAndUnCancel(){
            return Criteria.and(
                    Criteria.add(VBetSettle.PROP_MATCH_ID, Operator.EQ, searchObject.getMatchId()),
                    Criteria.add(VBetSettle.PROP_STATUS, Operator.NE, BetStatus.CANCLED.getCode())
            );
        }

        public Criteria byIdsAndUnCancel(){
            return Criteria.and(
                    Criteria.add(VBetSettle.PROP_BET_ID, Operator.IN, searchObject.getIds()),
                    Criteria.add(VBetSettle.PROP_STATUS, Operator.NE, BetStatus.CANCLED.getCode())
            );
        }

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}