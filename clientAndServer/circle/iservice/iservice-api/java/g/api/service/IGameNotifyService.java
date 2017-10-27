package g.api.service;

import g.model.api.param.GameNotifyParams;
import g.model.api.result.ListResults;

/**
 * Created by black on 2016/12/20.
 */
public interface IGameNotifyService {

    /**
     * 查找游戏公告
     * @param params
     * @return
     */
    ListResults gameNotify(GameNotifyParams params);
}
