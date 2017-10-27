package g.model.activityapply.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.so.ActivityPlayerApplySo;


/**
 * 活动申请玩家表值对象
 *
 * @author lenovo
 * @time 2016-9-18 19:10:58
 */
//region your codes 1
public class ActivityPlayerApplyVo extends BaseObjectVo<ActivityPlayerApply, ActivityPlayerApplySo, ActivityPlayerApplyVo.ActivityPlayerApplyQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -947245031580312456L;
    //endregion your codes 5

    /**
     *  活动申请玩家表查询逻辑
     */
    public static class ActivityPlayerApplyQuery extends AbstractQuery<ActivityPlayerApplySo> {

        //region your codes 6
        private static final long serialVersionUID = 8042771733053916076L;
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