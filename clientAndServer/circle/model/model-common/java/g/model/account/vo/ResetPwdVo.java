package g.model.account.vo;

import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.sys.po.SysDict;

import java.util.Map;

/**
 * @author: tom
 * @date: 15-12-27
 */
public class ResetPwdVo extends VAccountManageVo {

    private static final long serialVersionUID = 5211402619852901587L;

    public static final String resetTypePayPwd = "payPwd";
    public static final String resetTypeLoginPwd = "loginPwd";

    /*登录密码*/
    private String password;
    /*确认登录密码*/
    private String confirmLoginPwd;
    /*安全密码*/
    private String permissionPwd;
    /*确认安全密码*/
    private String confirmPermissionPwd;
    /**
     * 重置类别
     *  支付密码 登录密码
     */
    private String resetType;
    /**
     * 站长
     */
    private SysUser sysUser;

    /**
     *
     */
    private Map<String,SysDict> mailMobilePhoneStatus;

    private Integer userId;

    private boolean login = false;

    public String getResetTypePayPwd() {
        return resetTypePayPwd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmLoginPwd() {
        return confirmLoginPwd;
    }

    public void setConfirmLoginPwd(String confirmLoginPwd) {
        this.confirmLoginPwd = confirmLoginPwd;
    }

    public String getPermissionPwd() {
        return permissionPwd;
    }

    public void setPermissionPwd(String permissionPwd) {
        this.permissionPwd = permissionPwd;
    }

    public String getConfirmPermissionPwd() {
        return confirmPermissionPwd;
    }

    public void setConfirmPermissionPwd(String confirmPermissionPwd) {
        this.confirmPermissionPwd = confirmPermissionPwd;
    }

    public String getResetType() {
        return resetType;
    }

    public void setResetType(String resetType) {
        this.resetType = resetType;
    }

    public Map<String, SysDict> getMailMobilePhoneStatus() {
        return mailMobilePhoneStatus;
    }

    public void setMailMobilePhoneStatus(Map<String, SysDict> mailMobilePhoneStatus) {
        this.mailMobilePhoneStatus = mailMobilePhoneStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
