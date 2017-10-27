package g.api.service;

import g.model.api.param.LoginParams;
import g.model.api.result.Results;

import javax.security.auth.login.LoginException;

/**
 * 用户登录
 * Created by tom on 16-4-8.
 */
public interface IGameLoginService {

    /**
     * 登陆到游戏
     * @param loginParam 登陆信息参数对象
     * @return 登陆信息结果对象
     */
    Results login(LoginParams loginParam) throws LoginException;
}
