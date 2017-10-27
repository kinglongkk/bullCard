package g.model.activitymessage.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.so.ActivityWayRelationSo;


/**
 * 活动优惠方式表列表页值对象
 *
 * @author lenovo
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public class ActivityWayRelationListVo extends BaseListVo<ActivityWayRelation, ActivityWayRelationSo, ActivityWayRelationListVo.ActivityWayRelationQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7205955986882890550L;
    //endregion your codes 5

    /**
     *  活动优惠方式表列表查询逻辑
     */
    public static class ActivityWayRelationQuery extends AbstractQuery<ActivityWayRelationSo> {

        //region your codes 6
        private static final long serialVersionUID = -968590947110312100L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(ActivityWayRelation.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(ActivityWayRelation.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, this.searchObject.getActivityMessageId()),
                    Criteria.add(ActivityWayRelation.PROP_PREFERENTIAL_FORM, Operator.EQ, this.searchObject.getPreferentialForm()),
                    Criteria.add(ActivityWayRelation.PROP_PREFERENTIAL_VALUE, Operator.EQ, this.searchObject.getPreferentialValue()),
                    Criteria.add(ActivityWayRelation.PROP_PREFERENTIAL_AUDIT, Operator.EQ, this.searchObject.getPreferentialAudit()),
                    Criteria.add(ActivityWayRelation.PROP_ORDER_COLUMN, Operator.EQ, this.searchObject.getOrderColumn()),
                    Criteria.add(ActivityWayRelation.PROP_ACTIVITY_RULE_ID, Operator.EQ, this.searchObject.getActivityRuleId()),
                    Criteria.add(ActivityWayRelation.PROP_IS_ARTICLE, Operator.EQ, this.searchObject.getIsArticle()),
                    Criteria.add(ActivityWayRelation.PROP_ARTICLE, Operator.EQ, this.searchObject.getArticle()),
                    Criteria.add(ActivityWayRelation.PROP_TRIGGER_VALUE, Operator.EQ, this.searchObject.getTriggerValue()),
                    Criteria.add(ActivityWayRelation.PROP_PREFERENTIAL_RATIO, Operator.EQ, this.searchObject.getPreferentialRatio())
            );
            return criteria;
        }
    }

}