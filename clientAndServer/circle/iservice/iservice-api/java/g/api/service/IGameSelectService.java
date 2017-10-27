package g.api.service;

import g.model.api.param.GameSelectParams;
import g.model.api.result.ListResults;

/**
 * Created by black on 2016/12/19.
 */
public interface IGameSelectService {

    /**
     * 游戏查询
     * @param params
     * @return
     */
    ListResults gameSelect(GameSelectParams params);
}
