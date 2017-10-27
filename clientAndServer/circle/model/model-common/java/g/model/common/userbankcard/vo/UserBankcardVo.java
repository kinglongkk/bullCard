package g.model.common.userbankcard.vo;


import g.model.common.userbankcard.po.UserBankcard;
import g.model.common.userbankcard.so.UserBankcardSo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;

/**
 * 值对象
 *
 * Created by shisongbin using soul-code-generator on 2015-6-19 16:50:26
 */
//region your codes 1
public class UserBankcardVo extends BaseObjectVo<UserBankcard, UserBankcardSo, UserBankcardVo.UserBankcardQuery> {
//endregion your codes 1

    private static final long serialVersionUID = -9223372036854775808L;
    private String permissionPwd;

    public String getBankcardNumber2() {
        return bankcardNumber2;
    }

    public void setBankcardNumber2(String bankcardNumber2) {
        this.bankcardNumber2 = bankcardNumber2;
    }

    /**
     *  查询逻辑
     */
    public static class UserBankcardQuery extends AbstractQuery<UserBankcardSo> {

        private static final long serialVersionUID = 8282588089095588864L;

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(UserBankcard.PROP_USER_ID, Operator.EQ, searchObject.getUserId());
            criteria.addAnd(UserBankcard.PROP_IS_DEFAULT,Operator.EQ,searchObject.getIsDefault());
            criteria.addAnd(UserBankcard.PROP_BANKCARD_NUMBER,Operator.EQ,searchObject.getBankcardNumber());
            criteria.addAnd(UserBankcard.PROP_BANK_TYPE,Operator.EQ,searchObject.getBankType());
            return  criteria;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    public String getPermissionPwd() {
        return permissionPwd;
    }

    public void setPermissionPwd(String permissionPwd) {
        this.permissionPwd = permissionPwd;
    }


    private String bankcardNumber;
    private String bankDeposit;
    private String bankName;
    private String bankId;
    private String bankcardMasterName;
    private String customBankName;
    private String bankcardNumber2;
//    private boolean emptyPwd;

    public String getBankcardNumber() {
        return bankcardNumber;
    }

    public void setBankcardNumber(String bankcardNumber) {
        this.bankcardNumber = bankcardNumber;
    }

    public String getBankDeposit() {
        return bankDeposit;
    }

    public void setBankDeposit(String bankDeposit) {
        this.bankDeposit = bankDeposit;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankcardMasterName() {
        return bankcardMasterName;
    }

    public void setBankcardMasterName(String bankcardMasterName) {
        this.bankcardMasterName = bankcardMasterName;
    }

    public String getCustomBankName() {
        return customBankName;
    }

    public void setCustomBankName(String customBankName) {
        this.customBankName = customBankName;
    }

    /*public boolean isEmptyPwd() {
        return emptyPwd;
    }

    public void setEmptyPwd(boolean emptyPwd) {
        this.emptyPwd = emptyPwd;
    }*/

    //endregion your codes 4

}