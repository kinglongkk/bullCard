package g.model.activitymessage.vo;

import g.model.activitymessage.po.ActivityRule;
import g.model.activitymessage.po.ActivityWayRelation;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.so.ActivityMessageSo;

import java.util.List;


/**
 * 游戏信息表值对象
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
//region your codes 1
public class ActivityMessageVo extends BaseObjectVo<ActivityMessage, ActivityMessageSo, ActivityMessageVo.ActivityMessageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7439437114044117414L;
    //endregion your codes 5

    /**
     *  游戏信息表查询逻辑
     */
    public static class ActivityMessageQuery extends AbstractQuery<ActivityMessageSo> {

        //region your codes 6
        private static final long serialVersionUID = -869712114237896955L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }
    }

    private ActivityRule activityRule;

    private List<ActivityWayRelation> activityWayRelation;

    public ActivityRule getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(ActivityRule activityRule) {
        this.activityRule = activityRule;
    }

    public List<ActivityWayRelation> getActivityWayRelation() {
        return activityWayRelation;
    }

    public void setActivityWayRelation(List<ActivityWayRelation> activityWayRelation) {
        this.activityWayRelation = activityWayRelation;
    }
}