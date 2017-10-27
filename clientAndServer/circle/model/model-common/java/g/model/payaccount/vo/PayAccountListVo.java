package g.model.payaccount.vo;

import g.model.enums.PayAccountStatusEnum;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.so.PayAccountSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.List;


/**
 * 收款帐号-mark列表页值对象
 *
 * @author tom
 * @time 2016-7-4 16:37:33
 */
//region your codes 1
public class PayAccountListVo extends BaseListVo<PayAccount, PayAccountSo, PayAccountListVo.PayAccountQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 8692022709649288630L;


    /**
     * 支付宝账户id
     */
    private List<Integer> alipayPayIds;

    /**
     * 微信账户id
     */
    private List<Integer> wechatsPayIds;
    //endregion your codes 5

    /**
     *  收款帐号-mark列表查询逻辑
     */
    public static class PayAccountQuery extends AbstractQuery<PayAccountSo> {

        //region your codes 6
        private static final long serialVersionUID = -3585571122461681862L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return Criteria.add(PayAccount.PROP_TYPE, Operator.EQ, searchObject.getType())
                    .addAnd(PayAccount.PROP_ACCOUNT, Operator.EQ, searchObject.getAccount())
                    .addAnd(PayAccount.PROP_BANK_CODE, Operator.EQ, searchObject.getBankCode())
                    .addAnd(PayAccount.PROP_PAY_NAME, Operator.EQ, searchObject.getPayName())
                    .addAnd(PayAccount.PROP_ID, Operator.NE, searchObject.getId());
            //endregion your codes 2
        }

        public Criteria searchUniqueAccount() {
            return Criteria
                    .add(PayAccount.PROP_ACCOUNT, Operator.EQ, searchObject.getAccount())
                    .addAnd(PayAccount.PROP_BANK_CODE, Operator.EQ, searchObject.getBankCode())
                    .addAnd(PayAccount.PROP_STATUS, Operator.NE, PayAccountStatusEnum.DELETED.getCode())
                    ;

        }

        //region your codes 3
        public Criteria searchAvailablePayAccount(){
            return Criteria.add(PayAccount.PROP_SINGLE_DEPOSIT_MIN,Operator.LE,searchObject.getRechargeAmount())
                    .addAnd(PayAccount.PROP_SINGLE_DEPOSIT_MAX,Operator.GE,searchObject.getRechargeAmount())
                    .addAnd(PayAccount.PROP_STATUS,Operator.EQ,searchObject.getStatus());
        }
        //endregion your codes 3
    }

    //region your codes 4

    public List<Integer> getAlipayPayIds() {
        return alipayPayIds;
    }

    public void setAlipayPayIds(List<Integer> alipayPayIds) {
        this.alipayPayIds = alipayPayIds;
    }

    public List<Integer> getWechatsPayIds() {
        return wechatsPayIds;
    }

    public void setWechatsPayIds(List<Integer> wechatsPayIds) {
        this.wechatsPayIds = wechatsPayIds;
    }

    public static PayAccountListVo getPayAccounts(Double amount){
        PayAccountListVo payAccountListVo = new PayAccountListVo();
        PayAccountSo payAccountSo = new PayAccountSo();
        payAccountSo.setRechargeAmount(amount);
        payAccountSo.setStatus(PayAccountStatusEnum.USING.getCode());
        payAccountListVo.setSearch(payAccountSo);
        return payAccountListVo;
    }

    //endregion your codes 4

}