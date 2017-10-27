package g.web.player.controller.form;

import g.web.player.consts.FormValidRegExps;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.web.support.IForm;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;

/**
 * Created by mark on 16-6-30.
 */
public class InputAmountForm implements IForm {
    private String result_rechargeAmount;

    @NotBlank
    @Pattern(regexp = FormValidRegExps.MONEY,message = "请输入正确的金额！")
    @Max(99999999)
    public String getResult_rechargeAmount() {
        return result_rechargeAmount;
    }

    public void setResult_rechargeAmount(String result_rechargeAmount) {
        this.result_rechargeAmount = result_rechargeAmount;
    }
}
