package g.service.payaccount;

import org.soul.iservice.support.IBaseService;
import g.model.payaccount.po.Bank;
import g.model.payaccount.vo.BankListVo;
import g.model.payaccount.vo.BankVo;

import java.util.Map;


/**
 * 银行表服务接口
 *
 * @author mark
 * @time 2016-7-13 19:21:26
 */
//region your codes 1
public interface IBankService extends IBaseService<BankListVo, BankVo, Bank, Integer> {
    //region your codes 2
    Map<String, Bank> load(BankVo bankVo);
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

}