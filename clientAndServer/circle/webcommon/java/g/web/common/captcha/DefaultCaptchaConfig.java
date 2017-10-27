package g.web.common.captcha;

import com.google.code.kaptcha.util.Config;

import java.util.Map;
import java.util.Properties;

/**
 * Created by longer on 12/27/15.
 * 验证码默认配置
 */
public class DefaultCaptchaConfig  {

    private Map<String,String> captchaConfigs;

    public Config getConfig(){
        Properties properties = toProperties();
        return new Config(properties);
    }

    private Properties toProperties() {
        Properties properties = new Properties();
        for (String s : captchaConfigs.keySet()) {
            properties.put(s,captchaConfigs.get(s));
        }
        return properties;
    }


    public Map<String, String> getCaptchaConfigs() {
        return captchaConfigs;
    }

    public void setCaptchaConfigs(Map<String, String> captchaConfigs) {
        this.captchaConfigs = captchaConfigs;
    }
}

