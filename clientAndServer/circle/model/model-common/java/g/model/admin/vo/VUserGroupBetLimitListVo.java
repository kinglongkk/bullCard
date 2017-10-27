package g.model.admin.vo;

import g.model.admin.so.VUserGroupBetLimitSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.po.VUserGroupBetLimit;

import java.util.List;


/**
 * 玩家分组单注投注限额列表页值对象
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public class VUserGroupBetLimitListVo extends BaseListVo<VUserGroupBetLimit, VUserGroupBetLimitSo, VUserGroupBetLimitListVo.VUserGroupBetLimitQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4022495803770780990L;
    //endregion your codes 5

    private List<VUserGroupBetLimit> vUserGroupBetLimitList;

    public List<VUserGroupBetLimit> getvUserGroupBetLimitList() {
        return vUserGroupBetLimitList;
    }

    public void setvUserGroupBetLimitList(List<VUserGroupBetLimit> vUserGroupBetLimitList) {
        this.vUserGroupBetLimitList = vUserGroupBetLimitList;
    }

    /**
     *  玩家分组单注投注限额列表查询逻辑
     */
    public static class VUserGroupBetLimitQuery extends AbstractQuery<VUserGroupBetLimitSo> {

        //region your codes 6
        private static final long serialVersionUID = 5961453471229438982L;
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