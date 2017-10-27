package g.api.service;

import g.model.api.param.FetchBalanceParams;
import g.model.api.result.ObjectResults;
import g.model.api.result.Results;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameFetchBalanceService {

    /**
     * 获取在游戏平台的账户余额
     *
     * @param fetchBalanceParams 获取账户余额参数对象
     * @return 获取账户余额结果对象
     */
    Results fetchBalance(FetchBalanceParams fetchBalanceParams);
}
