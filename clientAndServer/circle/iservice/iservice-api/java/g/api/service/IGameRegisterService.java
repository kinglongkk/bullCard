package g.api.service;

import g.model.api.param.RegisterParams;
import g.model.api.result.Results;

/**
 * Created by tom on 16-4-8.
 */
public interface IGameRegisterService {

    /**
     * 向游戏平台注册游戏帐号
     * @param registerParams 注册信息参数对象
     * @return 注册信息结果对象
     */
    Results register(RegisterParams registerParams);
}
