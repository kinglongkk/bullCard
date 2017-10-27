package g.model.account.vo;

import g.model.account.po.VAccountManage;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.account.so.VAccountManageSo;


/**
 * 账号管理列表页值对象
 *
 * @author tom
 * @time 2016-04-18 10:35:08
 */
//region your codes 1
public class VAccountManageListVo extends BaseListVo<VAccountManage, VAccountManageSo, VAccountManageVo.VAccountManageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -2051567044785238396L;
    //endregion your codes 5

    /**
     *  账号管理列表查询逻辑
     */
    public static class VMasterManageQuery extends AbstractQuery<VAccountManageSo> {

        //region your codes 6
        private static final long serialVersionUID = 33091090035570935L;
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