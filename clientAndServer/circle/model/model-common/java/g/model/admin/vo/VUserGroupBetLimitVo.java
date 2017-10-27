package g.model.admin.vo;

import g.model.admin.po.VUserGroupBetLimit;
import g.model.admin.so.VUserGroupBetLimitSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

import java.util.List;
import java.util.Map;


/**
 * 玩家分组单注投注限额值对象
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public class VUserGroupBetLimitVo extends BaseObjectVo<VUserGroupBetLimit, VUserGroupBetLimitSo, VUserGroupBetLimitVo.VUserGroupBetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -5977420616579739915L;
    //endregion your codes 5

    /**
     *  玩家分组单注投注限额查询逻辑
     */
    public static class VUserGroupBetLimitQuery extends AbstractQuery<VUserGroupBetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = 4906305156216213313L;
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

    private Map<Integer,List<VUserGroupBetLimit>> userGroupBetLimitMap;

    public Map<Integer, List<VUserGroupBetLimit>> getUserGroupBetLimitMap() {
        return userGroupBetLimitMap;
    }

    public void setUserGroupBetLimitMap(Map<Integer, List<VUserGroupBetLimit>> userGroupBetLimitMap) {
        this.userGroupBetLimitMap = userGroupBetLimitMap;
    }

    //endregion your codes 4

}