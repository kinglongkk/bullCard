package g.web.common.privilege.form;

import g.web.common.privilege.controller.PrivilegeController;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * Created by cj on 15-10-15.
 */
@Comment("安全密码表单验证")
public class PrivilegeForm implements IForm {
    private String code;
    private String valiCode;

    @NotBlank(message = "privilege.privilege.code.notblank")
    @Comment("密码")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Depends(property = {"requiedValiCode"}, operator = {Operator.EQ}, value = {"1"}, message = "privilege.privilege.valiCode.notblank")
    @Comment("验证码")
    @Remote(message = "验证码错误！",checkClass = PrivilegeController.class,checkMethod = "checkValiCode")
    public String getValiCode() {
        return valiCode;
    }

    public void setValiCode(String valiCode) {
        this.valiCode = valiCode;
    }
}
