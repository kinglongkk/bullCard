package g.web.admin.sys.form;


import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;


/**
 * 投注限额表单验证对象
 *
 * @author tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public class BetLimitForm implements IForm {
//endregion your codes 1

    //region your codes 2

    private Integer result_betMin;

    private Integer result_betMax;

    private Integer result_itemMax;

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer getResult_betMin() {
        return result_betMin;
    }

    public void setResult_betMin(Integer result_betMin) {
        this.result_betMin = result_betMin;
    }

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer getResult_betMax() {
        return result_betMax;
    }

    public void setResult_betMax(Integer result_betMax) {
        this.result_betMax = result_betMax;
    }

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer getResult_itemMax() {
        return result_itemMax;
    }

    public void setResult_itemMax(Integer result_itemMax) {
        this.result_itemMax = result_itemMax;
    }
    //endregion your codes 2

}