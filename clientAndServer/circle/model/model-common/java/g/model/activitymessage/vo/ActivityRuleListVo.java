package g.model.activitymessage.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.activitymessage.po.ActivityRule;
import g.model.activitymessage.so.ActivityRuleSo;


/**
 * 活动规则表列表页值对象
 *
 * @author black
 * @time 2016-9-5 15:03:25
 */
//region your codes 1
public class ActivityRuleListVo extends BaseListVo<ActivityRule, ActivityRuleSo, ActivityRuleListVo.ActivityRuleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -328460687731158935L;
    //endregion your codes 5

    /**
     *  活动规则表列表查询逻辑
     */
    public static class ActivityRuleQuery extends AbstractQuery<ActivityRuleSo> {

        //region your codes 6
        private static final long serialVersionUID = 4891898521694651782L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(ActivityRule.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(ActivityRule.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, this.searchObject.getActivityMessageId()),
                    Criteria.add(ActivityRule.PROP_LIMIT_NUMBER, Operator.EQ, this.searchObject.getLimitNumber()),
                    Criteria.add(ActivityRule.PROP_EFFECTIVE_TIME, Operator.EQ, this.searchObject.getEffectiveTime()),
                    Criteria.add(ActivityRule.PROP_IS_DEMAND_FIRST, Operator.EQ, this.searchObject.getIsDemandFirst()),
                    Criteria.add(ActivityRule.PROP_IS_DESIGNATED_GAME, Operator.EQ, this.searchObject.getIsDesignatedGame()),
                    Criteria.add(ActivityRule.PROP_GAME_TYPE, Operator.EQ, this.searchObject.getGameType()),
                    Criteria.add(ActivityRule.PROP_IS_EXCLUSIVE, Operator.EQ, this.searchObject.getIsExclusive()),
                    Criteria.add(ActivityRule.PROP_EXCLUSIVE_ACTIVITY, Operator.EQ, this.searchObject.getExclusiveActivity())
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