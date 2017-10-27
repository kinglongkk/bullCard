package g.model.admin.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.sort.Sort;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.admin.po.VSysUserContact;
import g.model.admin.so.VSysUserContactSo;


/**
 * 子账户视图列表页值对象
 *
 * @author orange
 * @time 2016-04-11
 */
//region your codes 1
public class VSysUserContactListVo extends BaseListVo<VSysUserContact, VSysUserContactSo, VSysUserContactListVo.VSysUserContactQuery> {
//endregion your codes 1

    //region your codes 5
    //endregion your codes 5

    /**
     *  子账户视图列表查询逻辑
     */
    public static class VSysUserContactQuery extends AbstractQuery<VSysUserContactSo> {

        //region your codes 6
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }


        //region your codes 3

        @Override
        public Sort getDefaultSort() {
            return null;
        }


        //endregion your codes 3
    }

    //region your codes 4
    //endregion your codes 4

}