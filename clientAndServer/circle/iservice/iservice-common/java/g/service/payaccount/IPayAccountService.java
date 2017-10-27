package g.service.payaccount;

import org.soul.iservice.support.IBaseService;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.vo.PayAccountListVo;
import g.model.payaccount.vo.PayAccountVo;

import java.util.List;


/**
 * 收款帐号-mark服务接口
 *
 * @author tom
 * @time 2016-7-4 16:37:33
 */
//region your codes 1
public interface IPayAccountService extends IBaseService<PayAccountListVo, PayAccountVo, PayAccount, Integer> {
    PayAccountVo generationPayCode(PayAccountVo objectVo);

    boolean checkAccountUniqueUnderBankCode(PayAccountListVo payAccountListVo);

    PayAccountVo savePayAccount(PayAccountVo vo);

    PayAccountVo updatePayAccount(PayAccountVo vo);

    PayAccountVo updatePayAccountEdit(PayAccountVo vo);

    List<PayAccount> searchAvailablePayAccount(PayAccountListVo listVo);

    PayAccount searchPayAccountByRecharge(PayAccountVo vo);
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

}