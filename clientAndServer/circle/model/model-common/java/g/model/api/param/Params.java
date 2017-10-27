package g.model.api.param;

import java.util.Map;

/**
 * 公共参数
 * Created by tony on 2016/11/10.
 */
public abstract class Params {

    /**
     * 登陆用户账号
     */
    private String userAccount;
    /**
     * 登陆用户密码
     */
    private String userPwd;

    /**
     * 代理用户账户
     */
    private String merchantNo;

    /**
     * 代理游戏id
     */
    private String merchantGameNo;

    /**
     * 附加的参数,用于当各个Param的子类里面所定义的属性参数无法满足时，额外的参数从此map增加。
     */
    private Map<String, Object> additionalParams;
    /**
     * 附加的参数，此参数由调用者放入，然后在GameApiResult返回时会原样返回。
     */
    private Map<String,Object> attachParams;

    public Map<String, Object> getAdditionalParams() {
        return additionalParams;
    }

    public void setAdditionalParams(Map<String, Object> additionalParams) {
        this.additionalParams = additionalParams;
    }

    public Map<String, Object> getAttachParams() {
        return attachParams;
    }

    public void setAttachParams(Map<String, Object> attachParams) {
        this.attachParams = attachParams;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantGameNo() {
        return merchantGameNo;
    }

    public void setMerchantGameNo(String merchantGameNo) {
        this.merchantGameNo = merchantGameNo;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
