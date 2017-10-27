package g.web.common.shiro.local.authc;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Created by longer on 12/17/15.
 */
public class LocalToken extends UsernamePasswordToken {

    public static final String DEFAULT_USERNAME_PARAM = FormAuthenticationFilter.DEFAULT_USERNAME_PARAM;
    public static final String DEFAULT_PASSWORD_PARAM = FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM;
    public static final String DEFAULT_REMEMBER_ME_PARAM = FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM;
    public static final String DEFAULT_CAPTCHA_PARAM = "";

    /**
     * 用户ID
     *
     */
    private Integer id;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 登录类型
     */
    private Integer entrance;

    public LocalToken(String username, String md5, boolean rememberMe, String host) {
        super(username,md5,rememberMe,host);
    }

    public LocalToken() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer _getEntrance() {
        return entrance;
    }

    public void _setEntrance(Integer entrance) {
        this.entrance = entrance;
    }
}
