package g.model.payaccount.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.payaccount.po.VPayAccount;
import g.model.payaccount.so.VPayAccountSo;


/**
 * 公司、线上入款账号视图值对象
 *
 * @author mark
 * @time 2016-7-13 20:37:09
 */
//region your codes 1
public class VPayAccountVo extends BaseObjectVo<VPayAccount, VPayAccountSo, VPayAccountVo.VPayAccountQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -4836590941411067179L;
    //endregion your codes 5

    /**
     *  公司、线上入款账号视图查询逻辑
     */
    public static class VPayAccountQuery extends AbstractQuery<VPayAccountSo> {

        //region your codes 6
        private static final long serialVersionUID = -8925527758794839843L;
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