package g.web.agent.controller.agent.form;

import g.web.agent.controller.agent.controller.VUserPlayerGroupController;
import org.hibernate.validator.constraints.Range;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;


/**
 * 表单验证对象
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroupForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_groupName;

    private Integer[] betLimitList$$_betMax;
    private Integer[] betLimitList$$_itemMax;

    private Integer[] betLimitMultipleList$$_betMax;

    @NotNull
    @Remote(checkClass = VUserPlayerGroupController.class,checkMethod = "checkGroupnameExist",additionalProperties = "result.id", message = "group.groupname.repeat")
    public String getResult_groupName() {
        return result_groupName;
    }

    public void setResult_groupName(String result_groupName) {
        this.result_groupName = result_groupName;
    }

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer[] getBetLimitList$$_betMax() {
        return betLimitList$$_betMax;
    }

    public void setBetLimitList$$_betMax(Integer[] betLimitList$$_betMax) {
        this.betLimitList$$_betMax = betLimitList$$_betMax;
    }


    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer[] getBetLimitList$$_itemMax() {
        return betLimitList$$_itemMax;
    }

    public void setBetLimitList$$_itemMax(Integer[] betLimitList$$_itemMax) {
        this.betLimitList$$_itemMax = betLimitList$$_itemMax;
    }

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer[] getBetLimitMultipleList$$_betMax() {
        return betLimitMultipleList$$_betMax;
    }

    public void setBetLimitMultipleList$$_betMax(Integer[] betLimitMultipleList$$_betMax) {
        this.betLimitMultipleList$$_betMax = betLimitMultipleList$$_betMax;
    }
    //endregion your codes 2

}