package g.web.common.passport.captcha;

import org.soul.commons.enums.EnumTool;
import org.soul.web.shiro.common.captcha.ICaptchaUrlEnum;
import org.soul.web.shiro.common.captcha.ICaptchaUrlResovler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longer on 12/31/15.
 */
public class CaptchaUrlResovler implements ICaptchaUrlResovler {

    @Override
    public List<ICaptchaUrlEnum> getSupportUrls() {
        List<CaptchaUrlEnum> captchaUrlEnums = EnumTool.getEnumList(CaptchaUrlEnum.class);
        List<ICaptchaUrlEnum> iCaptchaUrlEnums = new ArrayList<>(captchaUrlEnums.size());
        for (CaptchaUrlEnum captchaUrlEnum : captchaUrlEnums) {
            iCaptchaUrlEnums.add(captchaUrlEnum);
        }
        return iCaptchaUrlEnums;
    }
}
