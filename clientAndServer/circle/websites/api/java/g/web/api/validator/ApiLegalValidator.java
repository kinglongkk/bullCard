package g.web.api.validator;

import g.model.player.so.VSysUserSo;
import g.model.player.vo.VSysUserVo;
import g.service.common.IVSysUserService;
import g.web.api.controller.form.ApiForm;
import g.web.api.model.ApiRequest;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.security.DigestTool;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by tony on 2016/11/7.
 */

public class ApiLegalValidator implements ConstraintValidator<ApiLegal,ApiRequest> {

    @Autowired
    private IVSysUserService sysUserService;

    @Override
    public void initialize(ApiLegal apiLegal) {
    }

    @Override
    public boolean isValid(ApiRequest request, ConstraintValidatorContext constraintValidatorContext) {

        constraintValidatorContext.disableDefaultConstraintViolation();

        String merchantNo = request.getMerchantNo();
        String merchantGameNo = request.getMerchantGameNo();
        String params = request.getParams();
        String timestamp = request.getTimestamp();
        String signature = request.getSignature();


//        if(!StringTool.isNotEmpty(merchantNo)){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("商户号有误").addConstraintViolation();
//            return false;
//        }else if(!StringTool.isNotEmpty(merchantGameNo)){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("商户游戏编号有误").addConstraintViolation();
//            return false;
//        }else if(!StringTool.isNotEmpty(params)){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("参数有误").addConstraintViolation();
//            return false;
//        }else if(!StringTool.isNotEmpty(timestamp)){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("时间戳有误").addConstraintViolation();
//            return false;
//        }
//
//        long authTimestamp = System.currentTimeMillis();
//        long inTimestamp = Long.valueOf(timestamp);
//        long difference = Math.abs(authTimestamp - inTimestamp)/(60*1000);
//
//        if(!StringTool.isNotEmpty(timestamp) || difference > 10){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("时间戳失效").addConstraintViolation();
//            return false;
//        }
//
//        VSysUserVo sysUserVo = new VSysUserVo();
//        VSysUserSo sysUserSo = new VSysUserSo();
//        sysUserSo.setUsername(merchantNo);
//        sysUserVo.setSearch(sysUserSo);
//        String key = sysUserService.findAgentKey(sysUserVo);
//
//        String authSign = DigestTool.getMD5(merchantNo+merchantGameNo+params+timestamp,key);
//        if(!StringTool.isNotEmpty(signature) || !StringTool.equals(authSign,signature)){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("签名有误").addConstraintViolation();
//            return false;
//        }

//        String authParams = authTool.AESDncode(key,params);
//        if(authParams == null){
//            constraintValidatorContext.buildConstraintViolationWithTemplate("参数加密有误").addConstraintViolation();
//            return false;
//        }else{
//            form.setParams(authParams);
//        }

        return true;
    }
}
