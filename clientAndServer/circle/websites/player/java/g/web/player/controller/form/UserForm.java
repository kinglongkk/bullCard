package g.web.player.controller.form;

import g.web.player.consts.FormValidRegExps;
import g.web.player.controller.RegisterController;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.web.support.IForm;

import javax.validation.constraints.Pattern;

/**
 * Created by mark on 16-7-19.
 */
public class UserForm implements IForm {

    private String result_username;

    private String result_password;

    private String $confirmPassword;




    @NotBlank
    @Pattern(regexp = FormValidRegExps.CELL_PHONE,message = "请输入正确的手机号码")
    @Remote(checkClass = RegisterController.class,checkMethod = "checkUsername",additionalProperties ={"result.username"},message = "该号码已注册")

    public String getResult_password() {
        return result_password;
    }

    @NotBlank
    @Depends(property ={"confirmPassword"}, operator = {Operator.EQ}, value = {"true"},message = "两次密码输入不一致，请重新输入！")
    public void setResult_password(String result_password) {
        this.result_password = result_password;
    }

    public String getResult_username() {
        return result_username;
    }

    public void setResult_username(String result_username) {
        this.result_username = result_username;
    }

    public String get$confirmPassword() {
        return $confirmPassword;
    }

    public void set$confirmPassword(String $confirmPassword) {
        this.$confirmPassword = $confirmPassword;
    }
}
