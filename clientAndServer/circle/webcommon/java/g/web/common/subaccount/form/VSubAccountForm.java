package g.web.common.subaccount.form;

import g.model.common.RegExpConstants;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.commons.validation.form.support.AndOr;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;
import g.web.common.subaccount.controller.VSubAccountController;

import javax.validation.constraints.Pattern;


/**
 * 子账户视图表单验证对象
 *
 * @author jeff
 * @time 2015-10-20 10:49:13
 */
//region your codes 1
public class VSubAccountForm implements IForm {
//endregion your codes 1

    //region your codes 2
    /*用户名*/
    private String sysUser_username;

    /*登录密码*/
    private String sysUser_password;
    private String $confirmPassword;

    /*权限密码*/
    private String sysUser_permissionPwd;
    private String $confirmPermissionPwd;

    /*真实姓名*/
    private String sysUser_nickname;

    /*联系方式的值*/
    private String email_contactValue;
    private String mobilePhone_contactValue;

    /*用户角色表 角色id*/
    private String $roleIds;

    /*用户安全问题 同时为空 同时不为空*/
    private String sysUserProtection_question1;
    private String sysUserProtection_answer1;

    /*备注 1000字符*/
    private String sysUser_memo;


    @Depends(property={"sysUser_id"},operator = {Operator.IS_EMPTY})
    @Pattern(regexp = RegExpConstants.ACCOUNT,message = "common.valid.usernameFormat")
    @Remote(message = "subAccount.edit.usernameExist",checkClass = VSubAccountController.class,checkMethod = "checkUsernameExist",additionalProperties = {"sysUser.id","$subSysCode"})
    public String getSysUser_username() {
        return sysUser_username;
    }

    public void setSysUser_username(String sysUser_username) {
        this.sysUser_username = sysUser_username;
    }
    @Pattern(regexp = RegExpConstants.LOGIN_PWD,message = "common.valid.loginPWDFormat")
    @Depends(property={"$changePassword","$changePassword"},operator = {Operator.IS_NULL, Operator.EQ},value = {"","_password"},andOr = AndOr.OR)
    public String getSysUser_password() {
        return sysUser_password;
    }

    public void setSysUser_password(String sysUser_password) {
        this.sysUser_password = sysUser_password;
    }

    @Depends(property={"$changePassword","$changePassword"},operator = {Operator.IS_NULL, Operator.EQ},value = {"","_password"},andOr = AndOr.OR)
    @Compare(logic = CompareLogic.EQ, anotherProperty = "sysUser.password",message = "subAccount.edit.confirmPasswordNE")
    @Pattern(regexp = RegExpConstants.LOGIN_PWD,message = "common.valid.loginPWDFormat")
    public String get$confirmPassword() {
        return $confirmPassword;
    }

    public void set$confirmPassword(String $confirmPassword) {
        this.$confirmPassword = $confirmPassword;
    }


    @Depends(property={"$changePassword","$changePassword"},operator = {Operator.IS_NULL, Operator.EQ},value = {"","_permissionPwd"},andOr = AndOr.OR)
    @Pattern(regexp = RegExpConstants.SECURITY_PWD,message = "common.valid.securityPWDFormat")
    public String getSysUser_permissionPwd() {
        return sysUser_permissionPwd;
    }

    public void setSysUser_permissionPwd(String sysUser_permissionPwd) {
        this.sysUser_permissionPwd = sysUser_permissionPwd;
    }

    @Depends(property={"$changePassword","$changePassword"},operator = {Operator.IS_NULL, Operator.EQ},value = {"","_permissionPwd"},andOr = AndOr.OR)
    @Compare(logic = CompareLogic.EQ, anotherProperty = "sysUser.permissionPwd",message = "subAccount.edit.confirmPermissionPwdNe")
    @Pattern(regexp = RegExpConstants.SECURITY_PWD,message = "common.valid.securityPWDFormat")
    public String get$confirmPermissionPwd() {
        return $confirmPermissionPwd;
    }

    public void set$confirmPermissionPwd(String $confirmPermissionPwd) {
        this.$confirmPermissionPwd = $confirmPermissionPwd;
    }
    @NotBlank()
    @Pattern(message = "common.valid.nickName",regexp = RegExpConstants.NICK_NAME)
    public String getSysUser_nickname() {
        return sysUser_nickname;
    }

    public void setSysUser_nickname(String sysUser_nickname) {
        this.sysUser_nickname = sysUser_nickname;
    }

    @Pattern(regexp = RegExpConstants.EMAIL,message = "subAccount.edit.email")
    @Length(max = 128,message = "subAccount.edit.emailLen")
    public String getEmail_contactValue() {
        return email_contactValue;
    }

    public void setEmail_contactValue(String email_contactValue) {
        this.email_contactValue = email_contactValue;
    }

    @Pattern(regexp = RegExpConstants.CELL_PHONE)
    public String getMobilePhone_contactValue() {
        return mobilePhone_contactValue;
    }

    public void setMobilePhone_contactValue(String mobilePhone_contactValue) {
        this.mobilePhone_contactValue = mobilePhone_contactValue;
    }
    @NotBlank()
    public String get$roleIds() {
        return $roleIds;
    }

    public void set$roleIds(String $roleIds) {
        this.$roleIds = $roleIds;
    }


    @Depends(message = "subAccount.edit.question",property="sysUserProtection_answer1",operator = Operator.IS_NOT_EMPTY)
    public String getSysUserProtection_question1() {
        return sysUserProtection_question1;
    }

    public void setSysUserProtection_question1(String sysUserProtection_question1) {
        this.sysUserProtection_question1 = sysUserProtection_question1;
    }

    @Pattern(regexp = RegExpConstants.ANSWER,message = "common.valid.answer")
    public String getSysUserProtection_answer1() {
        return sysUserProtection_answer1;
    }

    public void setSysUserProtection_answer1(String sysUserProtection_answer1) {
        this.sysUserProtection_answer1 = sysUserProtection_answer1;
    }
    @Length(max = 1000,message = "subAccount.edit.memoMax1000")
    public String getSysUser_memo() {
        return sysUser_memo;
    }

    public void setSysUser_memo(String sysUser_memo) {
        this.sysUser_memo = sysUser_memo;
    }

    //endregion your codes 2

}