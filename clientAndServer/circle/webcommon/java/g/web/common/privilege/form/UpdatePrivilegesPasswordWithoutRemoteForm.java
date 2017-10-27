package g.web.common.privilege.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;
import g.model.common.RegExpConstants;

import javax.validation.constraints.Pattern;

/**
 * Created by jeff on 2016/2/17.
 */
public class UpdatePrivilegesPasswordWithoutRemoteForm implements IForm {

    private String result_permissionPwd;
    private String $confirmPermissionPwd;

    @NotBlank()
    @Pattern(regexp = RegExpConstants.SECURITY_PWD,message = "common.valid.securityPWDFormat")
    public String getResult_permissionPwd() {
        return result_permissionPwd;
    }

    public void setResult_permissionPwd(String result_permissionPwd) {
        this.result_permissionPwd = result_permissionPwd;
    }
    @NotBlank()
    @Compare(logic = CompareLogic.EQ, anotherProperty = "result.permissionPwd",message = "privilege.permissionPwdNeConfirm")
    public String get$confirmPermissionPwd() {
        return $confirmPermissionPwd;
    }

    public void set$confirmPermissionPwd(String $confirmPermissionPwd) {
        this.$confirmPermissionPwd = $confirmPermissionPwd;
    }
}
