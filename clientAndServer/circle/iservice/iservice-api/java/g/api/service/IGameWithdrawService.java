package g.api.service;

import g.model.api.param.WithdrawParams;
import g.model.api.result.Results;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameWithdrawService {

    /**
     * 取款（转账到当前系统）
     *
     * @param withdrawParams 取款信息参数对象
     * @return 取款信息结果对象
     */
    Results withdraw(WithdrawParams withdrawParams);

    /**
     * 将提现信息发送客户端
     * @param transactionNo
     */
    void sendWithdrawMessage(String transactionNo);
}
