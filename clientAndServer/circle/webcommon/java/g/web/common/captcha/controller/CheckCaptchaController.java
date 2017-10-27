package g.web.common.captcha.controller;

import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.model.session.SessionKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import g.web.common.SessionManagerCommon;
import g.web.common.passport.captcha.CaptchaUrlEnum;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by eagle on 16-02-01.
 */
@Controller
@RequestMapping(value = "/check")
public class CheckCaptchaController {

    private static final Log log = LogFactory.getLog(CheckCaptchaController.class);

    /**
     * 远程验证码--意见反馈
     *
     * @param code
     * @param reqeust
     * @return
     */
    @RequestMapping(value = "/checkCaptcha")
    @ResponseBody
    public String checkCaptcha(@RequestParam("code") String code, HttpServletRequest reqeust) {
        String sessionCode = SessionManagerCommon.getCaptcha(SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_FEEDBACK.getSuffix());
        if (StringTool.isNotBlank(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
            return "true";
        }
        return "false";
    }

    /**
     * 远程验证码--玩家中心账号设置手机绑定
     *
     * @param code
     * @param reqeust
     * @return
     */
    @RequestMapping(value = "/checkPhoneCaptcha")
    @ResponseBody
    public String checkPhoneCaptcha(@RequestParam("code") String code, HttpServletRequest reqeust) {
        String sessionCode = SessionManagerCommon.getCaptcha(SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_PHONE.getSuffix());
        log.debug(code + "==" + sessionCode);
        if (StringTool.isNotBlank(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
            return "true";
        }
        return "false";
    }

    /**
     * 远程验证码--玩家中心账号设置邮箱绑定
     *
     * @param code
     * @param reqeust
     * @return
     */
    @RequestMapping(value = "/checkEmailCaptcha")
    @ResponseBody
    public String checkEmailCaptcha(@RequestParam("code") String code, HttpServletRequest reqeust) {
        String sessionCode = SessionManagerCommon.getCaptcha(SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_EMAIL.getSuffix());
        log.debug(code + "==" + sessionCode);
        if (StringTool.isNotBlank(sessionCode) && sessionCode.equalsIgnoreCase(code)) {
            return "true";
        }
        return "false";
    }


}
