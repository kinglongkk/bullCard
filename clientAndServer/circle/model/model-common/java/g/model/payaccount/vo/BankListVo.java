package g.model.payaccount.vo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;
import g.model.payaccount.po.Bank;
import g.model.payaccount.so.BankSo;


/**
 * 银行表列表页值对象
 *
 * @author mark
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public class BankListVo extends BaseListVo<Bank, BankSo, BankListVo.BankQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 6702477342017910532L;
    //endregion your codes 5

    /**
     *  银行表列表查询逻辑
     */
    public static class BankQuery extends AbstractQuery<BankSo> {

        //region your codes 6
        private static final long serialVersionUID = -5320485471992881802L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(Bank.PROP_BANK_NAME, Operator.EQ,searchObject.getBankName())
                    .addAnd(Bank.PROP_TYPE, Operator.EQ,searchObject.getType())
                    .addAnd(Bank.PROP_IS_USE,Operator.EQ,searchObject.getIsUse())
                    .addAnd(Bank.PROP_BANK_DISTRICT,Operator.IN,searchObject.getBankDistrictList());
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4

    //endregion your codes 4

}