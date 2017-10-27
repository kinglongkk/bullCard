package g.web.agent.controller.form;

import g.web.agent.consts.FormValidRegExps;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;

import javax.validation.constraints.Pattern;


/**
 * 站长交易表表单验证对象
 *
 */
public class ResetPwdForm implements IForm {

    //region your codes
    /*登录密码*/
    private String $password;
    /*确认登录密码*/
    private String $confirmLoginPwd;
    /*安全密码*/
    private String $permissionPwd;
    /*确认安全密码*/
    private String $confirmPermissionPwd;

    @Pattern(message = "common.valid.loginPWDFormat",regexp = FormValidRegExps.LOGIN_PWD)
    @Depends(property = "$resetType", operator = Operator.EQ,value ="loginPwd")
    public String get$password() {
        return $password;
    }
    public void set$password(String $password) {
        this.$password = $password;
    }

    @Compare(logic = CompareLogic.EQ, anotherProperty = "password",message = "两次输入的密码不一致")
    @Depends(property = "$resetType", operator = Operator.EQ,value ="loginPwd")
    public String get$confirmLoginPwd() {
        return $confirmLoginPwd;
    }
    public void set$confirmLoginPwd(String $confirmLoginPwd) {
        this.$confirmLoginPwd = $confirmLoginPwd;
    }

    @Pattern(message = "common.valid.securityPWDFormat",regexp = FormValidRegExps.SECURITY_PWD)
    @Depends(property = "$resetType", operator = Operator.EQ,value ="payPwd")
    public String get$permissionPwd() {
        return $permissionPwd;
    }
    public void set$permissionPwd(String $permissionPwd) {
        this.$permissionPwd = $permissionPwd;
    }

    @Compare(logic = CompareLogic.EQ, anotherProperty = "permissionPwd",message = "两次输入的密码不一致!")
    @Depends(property = "$resetType", operator = Operator.EQ,value ="payPwd")
    public String get$confirmPermissionPwd() {
        return $confirmPermissionPwd;
    }
    public void set$confirmPermissionPwd(String $confirmPermissionPwd) {
        this.$confirmPermissionPwd = $confirmPermissionPwd;
    }


    //    二期
//    @CellPhone(message = "playe
// rResetPwd.phoneCorrectFormat")
//    @NotBlank(message = "masterResetPwd.phoneNotBlank")
//    public String getMobilePhone() {
//        return mobilePhone;
//    }
//    public void setMobilePhone(String mobilePhone) {
//        this.mobilePhone = mobilePhone;
//    }
    //endregion your codes

}