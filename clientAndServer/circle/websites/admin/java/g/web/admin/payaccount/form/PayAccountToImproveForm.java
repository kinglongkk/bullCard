package g.web.admin.payaccount.form;


import g.web.admin.consts.FormValidRegExps;
import g.web.admin.payaccount.controller.PayAccountController;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.web.support.IForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;


/**
 * 收款账户表表单验证对象
 * <p/>
 * Created by loong using soul-code-generator on 2015-7-27 15:22:07
 */
//region your codes 1
public class PayAccountToImproveForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String id;
    /**
     * 停用金额
     */
    private String disableAmount;
    @Remote(message = "content.floatPic.validate.disableAmount",checkMethod = "validToImprove",checkClass = PayAccountController.class,additionalProperties = {"id"})
    //只能输入非零的正整数 ^\+?[1-9][0-9]*$
    @Pattern(regexp = FormValidRegExps.POSITIVE_INTEGER,message = "common.POSITIVE_INTEGER")
    @NotBlank()
    @Max(99999999)
    public String getDisableAmount() {
        return disableAmount;
    }

    public void setDisableAmount(String disableAmount) {
        this.disableAmount = disableAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    //endregion your codes 2

}