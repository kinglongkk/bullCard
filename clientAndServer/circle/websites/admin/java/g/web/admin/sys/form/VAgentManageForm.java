package g.web.admin.sys.form;

import g.model.common.RegExpConstants;
import g.web.admin.consts.FormValidRegExps;
import g.web.admin.sys.controller.VAgentManageController;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * 代理管理表单验证对象
 *
 * @author tom
 * @time 2016-04-13
 */
public class VAgentManageForm implements IForm {

    /** 用户名 */
    private String sysUser_username;

    /** 登录密码 */
    private String sysUser_password;
    private String $confirmPassword;

    /** 真实姓名 */
    private String sysUser_realName;

    /** 联系方式的值 */
    private String email_contactValue;
    private String mobilePhone_contactValue;
    private String $skype_contactValue;

    /** 状态 */
    private String sysUser_status;
    /** 用户类型 */
    private String sysUser_userType;
    /** 昵称 */
    private String sysUser_nickname;


    // @Depends(property={"sysUser_id"},operator = {Operator.IS_EMPTY})
    @Pattern(regexp = RegExpConstants.ACCOUNT,message = "common.valid.usernameFormat")
    @Remote(message = "subAccount.edit.usernameExist",checkClass = VAgentManageController.class,checkMethod = "checkUsernameExist",additionalProperties = {"sysUser.id"})
    @NotNull
    public String getSysUser_username() {
        return sysUser_username;
    }
    public void setSysUser_username(String sysUser_username) {
        this.sysUser_username = sysUser_username;
    }

    @Depends(property = "sysUser.id", operator = Operator.IS_NULL,value ="password")
    @Pattern(regexp = RegExpConstants.LOGIN_PWD,message = "common.valid.loginPWDFormat")
    public String getSysUser_password() {
        return sysUser_password;
    }
    public void setSysUser_password(String sysUser_password) {
        this.sysUser_password = sysUser_password;
    }

    @Depends(property = "sysUser.id", operator = Operator.IS_NULL,value ="password")
    @Compare(logic = CompareLogic.EQ, anotherProperty = "sysUser.password",message = "subAccount.edit.confirmPasswordNE")
    @Pattern(regexp = RegExpConstants.LOGIN_PWD,message = "common.valid.loginPWDFormat")
    public String get$confirmPassword() {
        return $confirmPassword;
    }
    public void set$confirmPassword(String $confirmPassword) {
        this.$confirmPassword = $confirmPassword;
    }

    @NotBlank()
    @Pattern(message = "common.valid.realName",regexp = RegExpConstants.NAME)
    @NotNull
    public String getSysUser_realName() {
        return sysUser_realName;
    }
    public void setSysUser_realName(String sysUser_realName) {
        this.sysUser_realName = sysUser_realName;
    }

    @Pattern(regexp = RegExpConstants.EMAIL,message = "subAccount.edit.email")
    @Length(max = 128,message = "subAccount.edit.emailLen")
    public String getEmail_contactValue() {
        return email_contactValue;
    }
    public void setEmail_contactValue(String email_contactValue) {
        this.email_contactValue = email_contactValue;
    }

    @Pattern(regexp = FormValidRegExps.ZERO_POSITIVE_INTEGER,message = "common.ZERO_POSITIVE_INTEGER")
    public String getMobilePhone_contactValue() {
        return mobilePhone_contactValue;
    }
    public void setMobilePhone_contactValue(String mobilePhone_contactValue) {
        this.mobilePhone_contactValue = mobilePhone_contactValue;
    }

    @Pattern(regexp = FormValidRegExps.SKYPE)
    public String get$skype_contactValue() {
        return $skype_contactValue;
    }
    public void set$skype_contactValue(String $skype_contactValue) {
        this.$skype_contactValue = $skype_contactValue;
    }

    @NotNull
    public String getSysUser_status() {
        return sysUser_status;
    }
    public void setSysUser_status(String sysUser_status) {
        this.sysUser_status = sysUser_status;
    }

    @NotNull
    public String getSysUser_userType() {
        return sysUser_userType;
    }
    public void setSysUser_userType(String sysUser_userType) {
        this.sysUser_userType = sysUser_userType;
    }

    @NotNull
    public String getSysUser_nickname() {
        return sysUser_nickname;
    }
    public void setSysUser_nickname(String sysUser_nickname) {
        this.sysUser_nickname = sysUser_nickname;
    }
}