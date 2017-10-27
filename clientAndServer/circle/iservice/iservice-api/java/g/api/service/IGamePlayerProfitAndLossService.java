package g.api.service;

import g.model.api.param.ProfitAndLossParams;
import g.model.api.result.ListResults;

/**
 * 玩家盈亏
 * Created by black on 2017/6/1.
 */
public interface IGamePlayerProfitAndLossService {

    /**
     * 玩家盈亏记录
     * @param params
     * @return
     */
    ListResults profitAndLoss(ProfitAndLossParams params);
}
