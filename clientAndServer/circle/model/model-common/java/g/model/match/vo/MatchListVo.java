package g.model.match.vo;

import g.model.match.po.Match;
import g.model.match.so.MatchSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 列表页值对象
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class MatchListVo extends BaseListVo<Match, MatchSo, MatchListVo.MatchQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4027263221867874090L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class MatchQuery extends AbstractQuery<MatchSo> {

        //region your codes 6
        private static final long serialVersionUID = -7101964510857893341L;
        //endregion your codes 6

        //region your codes 3

        public Criteria inGidsAndResulted(){
            return Criteria.and(
                    Criteria.add(Match.PROP_CODE, Operator.IN,this.searchObject.getGids()),
                    Criteria.add(Match.PROP_IS_RESULTED, Operator.EQ,this.searchObject.getIsResulted())
            );
        }

        public Criterion[] byGameTypeAndBallTypeLikeCode(){
            return new Criterion[]{
                    new Criterion(Match.PROP_GAME_TYPE, Operator.EQ, this.searchObject.getGameType()),
                    new Criterion(Match.PROP_BALL_TYPE, Operator.EQ, this.searchObject.getBallType()),
                    new Criterion(Match.PROP_CODE, Operator.LIKE, this.searchObject.getCode())
            };
        }

        public Criteria byGameTypeAndBallTypeAndGameOverNoResulted() {
            return Criteria.and(
                    Criteria.add(Match.PROP_GAME_TYPE, Operator.EQ,this.searchObject.getGameType()),
                    Criteria.add(Match.PROP_BALL_TYPE, Operator.EQ,this.searchObject.getBallType()),
                    Criteria.add(Match.PROP_END_TIME, Operator.GE,this.searchObject.getBeginTime()),
                    Criteria.add(Match.PROP_END_TIME, Operator.LE,this.searchObject.getEndTime()),
                    Criteria.add(Match.PROP_IS_RESULTED, Operator.EQ,this.searchObject.getIsResulted())
            );
        }

        @Override
        public Criteria getCriteria() {
            return null;
        }
        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}