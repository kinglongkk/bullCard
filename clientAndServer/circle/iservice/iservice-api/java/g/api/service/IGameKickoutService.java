package g.api.service;

import g.model.api.param.KickOutParams;
import g.model.api.result.Results;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameKickoutService {

    /**
     * 踢出游戏
     * @param kickOutParams 踢出游戏参数对象
     * @return 踢出游戏结果对象
     */
    Results kickout(KickOutParams kickOutParams);
}
