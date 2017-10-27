package g.model.payaccount.vo;

import g.model.enums.PayAccountStatusEnum;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.po.VPayAccount;
import g.model.payaccount.so.VPayAccountSo;
import org.soul.commons.bean.Pair;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseListVo;

import java.util.ArrayList;
import java.util.List;


/**
 * 公司、线上入款账号视图列表页值对象
 *
 * @author mark
 * @time 2016-7-13 20:37:09
 */
//region your codes 1
public class VPayAccountListVo extends BaseListVo<VPayAccount, VPayAccountSo, VPayAccountListVo.VPayAccountQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -3172728606617562863L;
    //endregion your codes 5

    /**
     *  公司、线上入款账号视图列表查询逻辑
     */
    public static class VPayAccountQuery extends AbstractQuery<VPayAccountSo> {

        //region your codes 6
        private static final long serialVersionUID = 1819391404147974292L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            Criteria criteria = Criteria.add(PayAccount.PROP_TYPE, Operator.EQ,searchObject.getType());
            if (StringTool.isNotEmpty(searchObject.getStatus())){
                criteria.addAnd(PayAccount.PROP_STATUS,Operator.EQ,searchObject.getStatus());
            } else {
                criteria.addAnd(PayAccount.PROP_STATUS,Operator.NE, PayAccountStatusEnum.DELETED.getCode());
            }
            if (StringTool.isNotEmpty(searchObject.getCode())){
                criteria.addAnd(PayAccount.PROP_CODE,Operator.ILIKE,searchObject.getCode());
            }
            if (StringTool.isNotEmpty(searchObject.getPayName())){
                criteria.addAnd(PayAccount.PROP_PAY_NAME,Operator.ILIKE,searchObject.getPayName());
            }
            if (StringTool.isNotEmpty(searchObject.getFullName())){
                criteria.addAnd(PayAccount.PROP_FULL_NAME,Operator.ILIKE,searchObject.getFullName());
            }
            if (StringTool.isNotEmpty(searchObject.getAccount())){
                criteria.addAnd(PayAccount.PROP_ACCOUNT,Operator.ILIKE,searchObject.getAccount());
            }
            if (StringTool.isNotEmpty(searchObject.getBankCode())){
                criteria.addAnd(PayAccount.PROP_BANK_CODE,Operator.EQ,searchObject.getBankCode());
            }
            return criteria;
            //endregion your codes 2
        }


        //region your codes 3

        //endregion your codes 3
    }

    //region your codes 4
    public List<Pair> searchList() {
        String vPayAccount = VPayAccount.class.getSimpleName();
        List<Pair> searchList = new ArrayList<>();
        searchList.add(new Pair("search." + VPayAccount.PROP_CODE, LocaleTool.tranView("column", vPayAccount + "." + VPayAccount.PROP_CODE)));
        searchList.add(new Pair("search." + VPayAccount.PROP_PAY_NAME, LocaleTool.tranView("column", vPayAccount + "." + VPayAccount.PROP_PAY_NAME)));
        searchList.add(new Pair("search." + VPayAccount.PROP_FULL_NAME, LocaleTool.tranView("column", vPayAccount + "." + VPayAccount.PROP_FULL_NAME)));
        searchList.add(new Pair("search." + VPayAccount.PROP_ACCOUNT, LocaleTool.tranView("column", vPayAccount + "." + VPayAccount.PROP_ACCOUNT)));

        return searchList;
    }    //endregion your codes 4

}