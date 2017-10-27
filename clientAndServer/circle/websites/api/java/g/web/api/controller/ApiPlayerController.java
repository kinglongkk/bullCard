package g.web.api.controller;

import g.api.service.IGameFetchRecordService;
import g.api.service.IGameKickoutService;
import g.api.service.IGameLoginService;
import g.api.service.IGameRegisterService;
import g.model.api.param.BetRecordParams;
import g.model.api.param.KickOutParams;
import g.model.api.param.LoginParams;
import g.model.api.param.RegisterParams;
import g.model.api.result.*;
import g.web.api.model.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

/**
 * 玩家信息
 * Created by tony on 2016/11/7.
 */
@RestController
@RequestMapping("/player")
public class ApiPlayerController extends ApiController {

    @Autowired
    private IGameLoginService loginService;

    @Autowired
    private IGameKickoutService kickoutService;

    @Autowired
    private IGameRegisterService registerService;

    @Autowired
    private IGameFetchRecordService fetchRecordService;

    /**
     * 用户登录
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/login")
    public Object login(@Valid ApiRequest<LoginParams> form, BindingResult bindingResult) throws LoginException {

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        LoginParams params = form.getParams(LoginParams.class);
        Results result = loginService.login(params);
        return responseResults(params, result);
    }

    /**
     * 用户登出
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/kickout")
    public Object kickout(@Valid ApiRequest<KickOutParams> form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        KickOutParams params = form.getParams(KickOutParams.class);
        Results result = kickoutService.kickout(params);
        return responseResults(params, result);
    }

    /**
     * 用户注册
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/register")
    public Object register(@Valid ApiRequest<RegisterParams> form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        RegisterParams params = form.getParams(RegisterParams.class);
        Results result = registerService.register(params);
        return responseResults(params, result);
    }

    /**
     * 获取游戏记录
     * @param form
     * @param bindingResult
     * @return result
     */
    @RequestMapping("/game/records")
    public Object fetchRecord(@Valid ApiRequest<BetRecordParams> form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throwLegalException(getBindingResultErrorMsg(bindingResult));
        }
        BetRecordParams params = form.getParams(BetRecordParams.class);
        ListResults result = fetchRecordService.fetchRecord(params);
        return responseListResults(params, result);
    }

}
