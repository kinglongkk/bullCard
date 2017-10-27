package g.api.service;

import g.model.api.param.DepositParams;
import g.model.api.result.Results;

/**
 * @author: tom
 * @date: 16-4-8
 */
public interface IGameDepositService {

    /**
     * 存款（转账到游戏平台）
     *
     * @param depositParams 存款信息参数对象
     * @return 存款信息结果对象
     */
    Results deposit(DepositParams depositParams);
}
