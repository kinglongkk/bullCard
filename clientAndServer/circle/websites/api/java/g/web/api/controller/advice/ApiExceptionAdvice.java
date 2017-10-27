package g.web.api.controller.advice;

import g.api.exception.BusinessException;
import g.model.api.enums.ResultStatusEnums;
import g.web.api.model.ApiResponse;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.iservice.passport.exception.AccountDisabledException;
import org.soul.iservice.passport.exception.AccountInActiveException;
import org.soul.iservice.passport.exception.AccountPasswordException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

/**
 * Created by tony on 2016/11/25.
 * api异常处理
 */
@ControllerAdvice
public class ApiExceptionAdvice{

    protected final Log log = LogFactory.getLog(getClass());


    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ApiResponse processBusinessException(BusinessException e){
        ApiResponse response = new ApiResponse();
        response.setCode(e.getCoce());
        response.setCodeDesc(e.getDesc());
        response.setErrorDesc(e.getError());
        return response;
    }

    /**
     * 处理登陆异常
     */
    @ExceptionHandler(value = {
            LoginException.class, AccountNotFoundException.class,
            AccountPasswordException.class, AccountInActiveException.class,
            AccountLockedException.class, AccountDisabledException.class
    })
    @ResponseBody
    public ApiResponse processLoginException(LoginException e){
        ApiResponse response = new ApiResponse();
        response.setCode(ResultStatusEnums.USER_OR_PASSWORD_ERR.getCode());
        response.setCodeDesc(ResultStatusEnums.USER_OR_PASSWORD_ERR.getMsg());
        return response;
    }

//
//    /**
//     * 处理运行时异常
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ApiResponse processRuntimeException(Exception e){
//        ApiResponse response = new ApiResponse();
//        response.setCode(ResultStatusEnums.OTHER_ERR.getCode());
//        response.setCodeDesc(ResultStatusEnums.OTHER_ERR.getMsg());
//        return response;
//    }







}
