package g.model.activityapply.vo;

import org.soul.commons.lang.DateTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.so.ActivityPlayerApplySo;


/**
 * 活动申请玩家表列表页值对象
 *
 * @author black
 * @time 2016-9-18 19:10:58
 */
//region your codes 1
public class ActivityPlayerApplyListVo extends BaseListVo<ActivityPlayerApply, ActivityPlayerApplySo, ActivityPlayerApplyListVo.ActivityPlayerApplyQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -6292894249771604762L;
    //endregion your codes 5

    /**
     *  活动申请玩家表列表查询逻辑
     */
    public static class ActivityPlayerApplyQuery extends AbstractQuery<ActivityPlayerApplySo> {

        //region your codes 6
        private static final long serialVersionUID = -2627641579669348539L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            //判断是否为精确到天的时间查询
            //情况一 点击分组查询查看该天的详情
            if (this.searchObject.getEndtime() == null && this.searchObject.getStarttime() != null){

                this.searchObject.setEndtime(DateTool.addDays(this.searchObject.getStarttime(),1));

            //情况二 在分组页面按时间搜索
            }else if (this.searchObject.getEndtime() != null && this.searchObject.getStarttime().equals(this.searchObject.getEndtime())){

                this.searchObject.setEndtime(DateTool.addDays(this.searchObject.getStarttime(),1));
            }

            Criteria criteria = Criteria.and(

                    Criteria.add(ActivityPlayerApply.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(ActivityPlayerApply.PROP_ACTIVITY_MESSAGE_ID, Operator.EQ, this.searchObject.getActivityMessageId()),
                    Criteria.add(ActivityPlayerApply.PROP_USER_ID, Operator.EQ, this.searchObject.getUserId()),
                    Criteria.add(ActivityPlayerApply.PROP_USER_NAME, Operator.EQ, this.searchObject.getUserName()),
                    Criteria.add(ActivityPlayerApply.PROP_REGISTER_TIME, Operator.EQ, this.searchObject.getRegisterTime()),
                    Criteria.add(ActivityPlayerApply.PROP_APPLY_TIME, Operator.EQ, this.searchObject.getApplyTime()),
                    Criteria.add(ActivityPlayerApply.PROP_PLAYER_RECHARGE_ID, Operator.EQ, this.searchObject.getPlayerRechargeId()),
                    Criteria.add(ActivityPlayerApply.PROP_STARTTIME, Operator.GE, this.searchObject.getStarttime()),
                    Criteria.add(ActivityPlayerApply.PROP_STARTTIME, Operator.LE, this.searchObject.getEndtime()),
                    Criteria.add(ActivityPlayerApply.PROP_PREFERENTIAL_VALUE, Operator.EQ, this.searchObject.getPreferentialValue()),
                    Criteria.add(ActivityPlayerApply.PROP_ARTICLE, Operator.EQ, this.searchObject.getArticle()),
                    Criteria.add(ActivityPlayerApply.PROP_IS_REALIZE, Operator.EQ, this.searchObject.getIsRealize()),
                    Criteria.add(ActivityPlayerApply.PROP_RELATION_PLAYER_ID, Operator.EQ, this.searchObject.getRelationPlayerId()),
                    Criteria.add(ActivityPlayerApply.PROP_ACTIVITY_CLASSIFY_KEY, Operator.EQ, this.searchObject.getActivityClassifyKey()),
                    Criteria.add(ActivityPlayerApply.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, this.searchObject.getActivityTypeCode())
            );
            criteria.addAnd(ActivityPlayerApply.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;
        }

    }

}