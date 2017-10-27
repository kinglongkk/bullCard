package g.web.agent.controller.player.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * 用户银行卡验证
 */
//region your codes 1
@Comment("编辑银行卡")
public class UserBankcardForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_bankName;
    private String result_bankcardNumber;

//    @NotBlank
//    @Pattern(message = "player.bankCardCorrect",regexp = FormValidRegExps.DIGITS)
//    @Remote(message = "player.bankCardCorrect", checkClass = PlayerController.class, checkMethod = "checkBankcardNumber", additionalProperties = "result.bankName")

    @NotBlank(message = "帐号不能为空")
//    @Length(min = 10,max = 25)
//    @Pattern(message = "银行卡格式错误",regexp = FormValidRegExps.BANK)
//    @Comment("银行卡号")
    public String getResult_bankcardNumber() {
        return result_bankcardNumber;
    }

    public void setResult_bankcardNumber(String result_bankcardNumber) {
        this.result_bankcardNumber = result_bankcardNumber;
    }

//    @NotBlank(message = "请选择银行")
    @Comment("银行")
    public String getResult_bankName() {
        return result_bankName;
    }

    public void setResult_bankName(String result_bankName) {
        this.result_bankName = result_bankName;
    }

//endregion your codes 2

}