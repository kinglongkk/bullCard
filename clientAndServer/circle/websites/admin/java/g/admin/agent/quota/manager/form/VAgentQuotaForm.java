package g.admin.agent.quota.manager.form;

import g.web.admin.consts.FormValidRegExps;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * 表单验证对象
 *
 * @author black
 * @time 2016-12-12 15:27:30
 */
//region your codes 1
public class VAgentQuotaForm implements IForm {
//endregion your codes 1

    /**
     * 充值金额
     */
    private String result_amount;

    @NotNull
    @Pattern(regexp = FormValidRegExps.POSITIVE_INTEGER,message = "common.POSITIVE_INTEGER")
    public String  getResult_amount() {
        return result_amount;
    }
    public void setResult_amount(String result_amount) {
        this.result_amount = result_amount;
    }

    //region your codes 2

    //endregion your codes 2

}