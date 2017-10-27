package g.data.payaccount;

import g.model.depositdesk.po.PlayerRecharge;

import g.model.payaccount.po.PayAccount;
import org.soul.data.rdb.mybatis.IBaseMapper;


/**
 * 收款帐号-mark数据访问对象
 *
 * @author tom
 * @time 2016-7-6 20:49:49
 */
//region your codes 1
public interface PayAccountMapper extends IBaseMapper<PayAccount, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 线上支付存款成功后更新收款账号
     *
     * @param recharge
     * @return
     */
    boolean updatePayAccountByOnlineRecharge(PlayerRecharge recharge);

    PayAccount getMaxPayCode(String type);
    //endregion your codes 2

}