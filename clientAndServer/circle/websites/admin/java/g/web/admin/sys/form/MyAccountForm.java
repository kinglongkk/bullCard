package g.web.admin.sys.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.constraints.CellPhone;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

import javax.validation.constraints.Pattern;


/**
 * 站长子账号表表单验证对象
 *
 * @author cj
 * @time 2015-8-25 14:46:59
 */
//region your codes 1
@Comment("站长子账号表单验证")
public class MyAccountForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String sex;
    private String phone;
    private String email;

    @NotBlank
    @Comment("性别")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @CellPhone
    @NotBlank
    @Comment("手机号")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Email
    @NotBlank
    @Comment("邮箱")
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    //endregion your codes 2

}