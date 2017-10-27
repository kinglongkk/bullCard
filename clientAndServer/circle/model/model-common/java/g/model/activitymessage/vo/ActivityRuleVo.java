package g.model.activitymessage.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.activitymessage.po.ActivityRule;
import g.model.activitymessage.so.ActivityRuleSo;


/**
 * 活动规则表值对象
 *
 * @author lenovo
 * @time 2016-9-5 15:03:25
 */
//region your codes 1
public class ActivityRuleVo extends BaseObjectVo<ActivityRule, ActivityRuleSo, ActivityRuleVo.ActivityRuleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7484170335306332327L;
    //endregion your codes 5

    /**
     *  活动规则表查询逻辑
     */
    public static class ActivityRuleQuery extends AbstractQuery<ActivityRuleSo> {

        //region your codes 6
        private static final long serialVersionUID = 3263116147389780300L;
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