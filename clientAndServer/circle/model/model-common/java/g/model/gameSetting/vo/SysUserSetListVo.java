package g.model.gameSetting.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.gameSetting.po.SysUserSet;
import g.model.gameSetting.so.SysUserSetSo;


/**
 * 列表页值对象
 *
 * @author lenovo
 * @time 2016-11-2 17:11:54
 */
//region your codes 1
public class SysUserSetListVo extends BaseListVo<SysUserSet, SysUserSetSo, SysUserSetListVo.SysUserSetQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -7557708845729421877L;
    //endregion your codes 5

    /**
     *  列表查询逻辑
     */
    public static class SysUserSetQuery extends AbstractQuery<SysUserSetSo> {

        //region your codes 6
        private static final long serialVersionUID = -3530366586016972140L;
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