package g.model.admin.vo;


import g.model.admin.po.VSysUserContact;
import g.model.admin.so.VSysUserContactSo;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;


/**
 * 系统公告视图值对象
 *
 * @author orange
 * @time 2015-11-26 20:20:37
 */
//region your codes 1
public class VSysUserContactVo extends BaseObjectVo<VSysUserContact, VSysUserContactSo, VSysUserContactVo.VSysUserContactQuery> {
//endregion your codes 1

    //region your codes 5
    //endregion your codes 5

    /**
     *  系统公告视图查询逻辑
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

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}