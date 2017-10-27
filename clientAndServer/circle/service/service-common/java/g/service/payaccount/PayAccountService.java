package g.service.payaccount;

import g.data.payaccount.PayAccountMapper;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.vo.PayAccountListVo;
import g.model.payaccount.vo.PayAccountVo;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.math.NumberTool;
import org.soul.service.support.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 收款帐号-mark服务
 *
 * @author tom
 * @time 2016-7-4 16:37:33
 */
//region your codes 1
public class PayAccountService extends BaseService<PayAccountMapper, PayAccountListVo, PayAccountVo, PayAccount, Integer> implements IPayAccountService {
//endregion your codes 1

    //region your codes 2
    public PayAccountVo generationPayCode(PayAccountVo vo) {
        PayAccount maxPay = mapper.getMaxPayCode(vo.getSearch().getType());
        if (maxPay == null) {
            if (vo.getSearch().getType().equals("1")) {
                vo.getResult().setCode("C001");
            } else {
                vo.getResult().setCode("L001");
            }

            return vo;
        }
        String code = "C";
        if (vo.getSearch().getType().equals("2")) {
            code = "L";
        }
        String maxCode = maxPay.getCode();
        maxCode = maxCode.substring(1, maxCode.length());
        Integer maxNum = Integer.parseInt(maxCode) + 1;
        if (maxNum < 10) {
            code = code + "00" + maxNum;
        } else if (maxNum < 100) {
            code = code + "0" + maxNum;
        } else {
            code = code + maxNum;
        }
        vo.getResult().setCode(code);
        return vo;
    }

    @Override
    public boolean checkAccountUniqueUnderBankCode(PayAccountListVo listVo) {
        return this.mapper.count(listVo.getQuery().searchUniqueAccount()) < 1;
    }
    @Override
    public PayAccountVo savePayAccount(PayAccountVo vo) {
        //生成code
        vo = this.generationPayCode(vo);
        mapper.insert(vo.getResult());
        return vo;
    }
    @Override
    public PayAccountVo updatePayAccount(PayAccountVo vo) {
        String[] properties = new String[9];
        properties[0] = PayAccount.PROP_PAY_NAME;
        properties[1] = PayAccount.PROP_DISABLE_AMOUNT;
        properties[2] = PayAccount.PROP_DEPOSIT_COUNT;
        properties[3] = PayAccount.PROP_DEPOSIT_TOTAL;
        properties[4] = PayAccount.PROP_FULL_RANK;
        properties[5] = PayAccount.PROP_OPEN_ACOUNT_NAME;
        properties[6] = PayAccount.PROP_QR_CODE_URL;
        properties[7] = PayAccount.PROP_SINGLE_DEPOSIT_MIN;
        properties[8] = PayAccount.PROP_SINGLE_DEPOSIT_MAX;
        mapper.updateOnly(vo.getResult(), properties);
        //更新对应货币表
//        batchPayRankDML(vo);
//        batchPayCurrencyDML(vo);
        return vo;
    }
    @Override
    public PayAccountVo updatePayAccountEdit(PayAccountVo vo) {
        String[] properties = new String[8];
        properties[0] = PayAccount.PROP_PAY_NAME;
        properties[1] = PayAccount.PROP_DISABLE_AMOUNT;
        properties[2] = PayAccount.PROP_DEPOSIT_COUNT;
        properties[3] = PayAccount.PROP_DEPOSIT_TOTAL;
        properties[4] = PayAccount.PROP_SINGLE_DEPOSIT_MIN;
        properties[5] = PayAccount.PROP_SINGLE_DEPOSIT_MAX;
        properties[6] = PayAccount.PROP_EFFECTIVE_MINUTES;
        properties[7] = PayAccount.PROP_FULL_RANK;
        mapper.updateOnly(vo.getResult(), properties);
        return vo;
    }

    @Override
    public List<PayAccount> searchAvailablePayAccount(PayAccountListVo listVo) {
        List<PayAccount> search = mapper.search(listVo.getQuery().searchAvailablePayAccount());
        return search;
    }

    @Override
    public PayAccount searchPayAccountByRecharge(PayAccountVo vo) {
        List<PayAccount> list = this.mapper.search(vo.getQuery().getSubCriteria());
        return list.isEmpty() ? null : list.get(0);
    }

    public static void main(String[] args) {
//        String random = RandomStringTool.random(1, 0, 1, true, true);
//        System.out.println(random);
        String decimal;
        Double rechargeAmount = 100d;
        do {
            decimal = RandomStringTool.random(2,false, true);
            System.out.println("decimal:" + decimal);
        } while (NumberTool.toInt(decimal) == 0);
        double v = rechargeAmount + NumberTool.toDouble(decimal) / 100;
        System.out.println(v);
    }
    //endregion your codes 2

}