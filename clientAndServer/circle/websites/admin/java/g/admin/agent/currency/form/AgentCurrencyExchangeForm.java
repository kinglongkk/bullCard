package g.admin.agent.currency.form;

import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;


/**
 * 货币汇率表单验证对象
 *
 * @author black
 * @time 2016-11-28 11:28:04
 */
public class AgentCurrencyExchangeForm implements IForm {

    /** 货币代码 */
    private String result_code;
    /** 货币名称 */
    private String result_name;
    /** 货币汇率 */
    private Integer result_exchange;

    @NotNull
    public String getResult_code() {
        return result_code;
    }
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    @NotNull
    public String getResult_name() {
        return result_name;
    }
    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }

    @NotNull
    public Integer getResult_exchange() {
        return result_exchange;
    }
    public void setResult_exchange(Integer result_exchange) {
        this.result_exchange = result_exchange;
    }
}