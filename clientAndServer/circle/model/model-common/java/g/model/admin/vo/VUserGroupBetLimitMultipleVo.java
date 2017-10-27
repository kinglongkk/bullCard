package g.model.admin.vo;

import g.model.admin.po.VUserGroupBetLimitMultiple;
import g.model.admin.so.VUserGroupBetLimitMultipleSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

import java.util.List;
import java.util.Map;


/**
 * 玩家分组综合过关单项限额值对象
 *
 * @author tom
 * @time 2016-4-21 17:23:47
 */
//region your codes 1
public class VUserGroupBetLimitMultipleVo extends BaseObjectVo<VUserGroupBetLimitMultiple, VUserGroupBetLimitMultipleSo, VUserGroupBetLimitMultipleVo.VUserGroupBetLimitMultipleQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 7362904951362211175L;
    //endregion your codes 5

    /**
     *  玩家分组综合过关单项限额查询逻辑
     */
    public static class VUserGroupBetLimitMultipleQuery extends AbstractQuery<VUserGroupBetLimitMultipleSo> {

        //region your codes 6
        private static final long serialVersionUID = -7500930880805574417L;
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
    // 不同组对应的不同的综合限额集合
    private Map<Integer, List<VUserGroupBetLimitMultiple>> groupIdListMap;

    public Map<Integer, List<VUserGroupBetLimitMultiple>> getGroupIdListMap() {
        return groupIdListMap;
    }

    public void setGroupIdListMap(Map<Integer, List<VUserGroupBetLimitMultiple>> groupIdListMap) {
        this.groupIdListMap = groupIdListMap;
    }
    //endregion your codes 4

}