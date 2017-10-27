package g.model.activitymessage.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.so.ActivityWayRelationSo;


/**
 * 活动优惠方式表值对象
 *
 * @author lenovo
 * @time 2016-9-5 15:04:06
 */
//region your codes 1
public class ActivityWayRelationVo extends BaseObjectVo<ActivityWayRelation, ActivityWayRelationSo, ActivityWayRelationVo.ActivityWayRelationQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7659181327368000614L;
    //endregion your codes 5

    /**
     *  活动优惠方式表查询逻辑
     */
    public static class ActivityWayRelationQuery extends AbstractQuery<ActivityWayRelationSo> {

        //region your codes 6
        private static final long serialVersionUID = -5525648515687819757L;
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