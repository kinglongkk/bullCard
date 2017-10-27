package g.web.agent.controller.form;

import g.web.agent.consts.FormValidRegExps;
import g.web.agent.controller.PlayerController;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;

import javax.validation.constraints.Pattern;

/**
 * Created by cj on 15-8-24.
 */
public class UpdatePasswordForm implements IForm {
    private String password;
    private String newPassword;
    private String newRePassword;

    @NotBlank
    @Remote(checkClass = PlayerController.class, checkMethod = "checkPassword", additionalProperties = {"password"}, message = "content.myAccount.updatePassword.error")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotBlank
    @Pattern(message = "common.valid.loginPWDFormat",regexp = FormValidRegExps.LOGIN_PWD)
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotBlank
    @Compare(message = "content.myAccount.updatePassword.pwdNeConfirmPwd", logic = CompareLogic.EQ, anotherProperty = "newPassword")
    public String getNewRePassword() {
        return newRePassword;
    }

    public void setNewRePassword(String newRePassword) {
        this.newRePassword = newRePassword;
    }
}
