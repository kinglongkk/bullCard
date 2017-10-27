package g.web.admin.sys.form;

import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;


/**
 * 综合投注限额表单验证对象
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public class BetLimitMultipleForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private Integer betLimitMultipleList$$_betNum;

    private Integer betLimitMultipleList$$_oddsMin;

    private Integer betLimitMultipleList$$_oddsMax;

    private Integer betLimitMultipleList$$_betMax;

    @NotNull
    @Range(max = 10,min = 2)
    @Digits(integer = 2,fraction = 0)
    public Integer getBetLimitMultipleList$$_betNum() {
        return betLimitMultipleList$$_betNum;
    }

    public void setBetLimitMultipleList$$_betNum(Integer betLimitMultipleList$$_betNum) {
        this.betLimitMultipleList$$_betNum = betLimitMultipleList$$_betNum;
    }

    @NotNull
    @Range(max = 999999,min = 0)
    @Digits(integer = 6,fraction = 0)
    public Integer getBetLimitMultipleList$$_oddsMin() {
        return betLimitMultipleList$$_oddsMin;
    }

    public void setBetLimitMultipleList$$_oddsMin(Integer betLimitMultipleList$$_oddsMin) {
        this.betLimitMultipleList$$_oddsMin = betLimitMultipleList$$_oddsMin;
    }

    @NotNull
    @Range(max = 999999,min = 0)
    @Digits(integer = 6,fraction = 0)
    public Integer getBetLimitMultipleList$$_oddsMax() {
        return betLimitMultipleList$$_oddsMax;
    }

    public void setBetLimitMultipleList$$_oddsMax(Integer betLimitMultipleList$$_oddsMax) {
        this.betLimitMultipleList$$_oddsMax = betLimitMultipleList$$_oddsMax;
    }

    @NotNull
    @Range(max = 999999999,min = 0)
    @Digits(integer = 9,fraction = 0)
    public Integer getBetLimitMultipleList$$_betMax() {
        return betLimitMultipleList$$_betMax;
    }

    public void setBetLimitMultipleList$$_betMax(Integer betLimitMultipleList$$_betMax) {
        this.betLimitMultipleList$$_betMax = betLimitMultipleList$$_betMax;
    }

    //endregion your codes 2

}