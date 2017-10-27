package g.model.payaccount.vo;

import g.model.payaccount.po.Bank;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.so.PayAccountSo;
import org.soul.model.pay.vo.PayAccountParam;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 收款帐号-mark值对象
 *
 * @author tom
 * @time 2016-7-4 16:37:33
 */
//region your codes 1
public class PayAccountVo extends BaseObjectVo<PayAccount, PayAccountSo, PayAccountVo.PayAccountQuery> {
//endregion your codes 1
    private List<Bank> bankList;

    //第三方接口需要的参数集合
    private Set<PayAccountParam> payApiParams;
    //region your codes 5
    private static final long serialVersionUID = -5804216024645249312L;
    private Map<String, Serializable> accountType;

    public void setAccountType(Map<String, Serializable> accountType) {
        this.accountType = accountType;
    }

    public Map<String, Serializable> getAccountType() {
        return accountType;
    }

    /**
     * 根据账号类型验证不同输入规则
     *
     * @return
     */
    private String account1;
    private String account2;
    private Bank bank;

    //endregion your codes 5

    /**
     *  收款帐号-mark查询逻辑
     */
    public static class PayAccountQuery extends AbstractQuery<PayAccountSo> {

        //region your codes 6
        private static final long serialVersionUID = 6984956309558657932L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3
        public Criteria getSubCriteria() {
            Criteria criteria = Criteria.add(PayAccount.PROP_BANK_CODE, Operator.EQ, searchObject.getBankCode());
            criteria.addAnd(PayAccount.PROP_ACCOUNT, Operator.EQ, searchObject.getAccount());
            criteria.addAnd(PayAccount.PROP_TYPE, Operator.EQ, searchObject.getType());
            criteria.addAnd(PayAccount.PROP_STATUS, Operator.NE, searchObject.getStatus());
            return criteria;
        }
        //endregion your codes 3

    }

    //region your codes 4
    public void setBankList(List<Bank> bankList) {
        this.bankList = bankList;
    }

    public List<Bank> getBankList() {
        return bankList;
    }

    public String getAccount1() {
        return account1;
    }

    public void setAccount1(String account1) {
        this.account1 = account1;
    }

    public String getAccount2() {
        return account2;
    }

    public void setAccount2(String account2) {
        this.account2 = account2;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Set<PayAccountParam> getPayApiParams() {
        return payApiParams;
    }

    public void setPayApiParams(Set<PayAccountParam> payApiParams) {
        this.payApiParams = payApiParams;
    }
//endregion your codes 4

}