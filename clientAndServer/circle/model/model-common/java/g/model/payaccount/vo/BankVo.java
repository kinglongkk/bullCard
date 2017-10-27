package g.model.payaccount.vo;

import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.payaccount.po.Bank;
import g.model.payaccount.so.BankSo;


/**
 * 银行表值对象
 *
 * @author mark
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public class BankVo extends BaseObjectVo<Bank, BankSo, BankVo.BankQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 1400860673199226561L;
    //endregion your codes 5

    /**
     *  银行表查询逻辑
     */
    public static class BankQuery extends AbstractQuery<BankSo> {

        //region your codes 6
        private static final long serialVersionUID = 3840036231797170553L;
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