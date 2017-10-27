package g.model.activitymessage.vo;

import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.so.ActivityMessageSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;


/**
 * 游戏信息表列表页值对象
 *
 * @author lenovo
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public class ActivityMessageListVo extends BaseListVo<ActivityMessage, ActivityMessageSo, ActivityMessageListVo.ActivityMessageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 8035750628991464740L;
    //endregion your codes 5

    /**
     * 游戏信息表列表查询逻辑
     */
    public static class ActivityMessageQuery extends AbstractQuery<ActivityMessageSo> {

        //region your codes 6
        private static final long serialVersionUID = 3946404152248496130L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {

            Criteria criteria = Criteria.and(

                    Criteria.add(ActivityMessage.PROP_ID, Operator.EQ, this.searchObject.getId()),
                    Criteria.add(ActivityMessage.PROP_STARTTIME, Operator.EQ, this.searchObject.getStartTime()),
                    Criteria.add(ActivityMessage.PROP_ENDTIME, Operator.GE, this.searchObject.getEndTime()),
                    Criteria.add(ActivityMessage.PROP_CREATE_TIME, Operator.EQ, this.searchObject.getCreateTime()),
                    Criteria.add(ActivityMessage.PROP_CREATE_USERID, Operator.EQ, this.searchObject.getCreateUserId()),
                    Criteria.add(ActivityMessage.PROP_CREATE_USERNAME, Operator.EQ, this.searchObject.getCreateUserName()),
                    Criteria.add(ActivityMessage.PROP_ACTIVITY_CLASSIFY_KEY, Operator.EQ, this.searchObject.getActivityClassifyKey()),
                    Criteria.add(ActivityMessage.PROP_ACTIVITY_TYPE_CODE, Operator.EQ, this.searchObject.getActivityTypeCode()),
                    Criteria.add(ActivityMessage.PROP_IS_DISPLAY, Operator.EQ, this.searchObject.getIsDisplay()),
                    Criteria.add(ActivityMessage.PROP_UPDATE_USERID, Operator.EQ, this.searchObject.getUpdateUserId()),
                    Criteria.add(ActivityMessage.PROP_UPDATE_USERNAME, Operator.EQ, this.searchObject.getUpdateUserName()),
                    Criteria.add(ActivityMessage.PROP_UPDATE_TIME, Operator.EQ, this.searchObject.getUpdateTime()),
                    Criteria.add(ActivityMessage.PROP_ORDER_NUM, Operator.EQ, this.searchObject.getOrderNum()),
                    Criteria.add(ActivityMessage.PROP_STATUS, Operator.EQ, this.searchObject.getStatus())
            );
            criteria.addAnd(ActivityMessage.PROP_IS_DELETED, Operator.EQ, false);
            return criteria;

        }
    }
}