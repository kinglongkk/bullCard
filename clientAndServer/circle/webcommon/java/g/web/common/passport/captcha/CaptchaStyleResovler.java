package g.web.common.passport.captcha;

import com.google.code.kaptcha.util.Config;
import g.web.common.captcha.DefaultCaptchaConfig;
import org.soul.commons.init.context.ContextParam;
import org.soul.web.shiro.common.captcha.ICaptchaStyleResovler;

/**
 * Created by longer on 12/22/15.
 */
public class CaptchaStyleResovler implements ICaptchaStyleResovler {

    private DefaultCaptchaConfig defaultCaptchaConfig;

    @Override
    public String getStyle(ContextParam contextParam) {
        return null;
    }

    @Override
    public Config custom(Config config) {
      return config;
    }

    @Override
    public Config getDefault() {
        return defaultCaptchaConfig.getConfig();
    }

    public void setDefaultCaptchaConfig(DefaultCaptchaConfig defaultCaptchaConfig) {
        this.defaultCaptchaConfig = defaultCaptchaConfig;
    }
}
