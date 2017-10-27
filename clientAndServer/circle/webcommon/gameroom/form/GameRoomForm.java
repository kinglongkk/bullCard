package g.admin.gameroom.form;

import g.web.admin.consts.FormValidRegExps;
import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 游戏房间表表单验证对象
 *
 * @author lenovo
 * @time 2016-8-25 14:22:00
 */
//region your codes 1
public class GameRoomForm implements IForm {
//endregion your codes 1

    /** 房间名称 */
    private String result_name;
    /** 房间状态 */
    private Integer result_status;
    /** 房间最小上庄金额 */
    private Integer result_minLimitDealerBlance;
    /** 房间最大容纳玩家数 */
    private Integer result_maxLimitPlayerNumber;
    /** 房间最小玩家余额 */
    private Integer result_minLimitPlayerBlance;
    /** 房间最大投注数 */
    private Integer result_betMax;
    /** 房间最小投注数 */
    private Integer result_betMin;

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_CHINESE,message = "common.LIMIT_CHINESE")
    public String getResult_name() {
        return result_name;
    }

    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }

    @NotNull
    public Integer getResult_status() {
        return result_status;
    }

    public void setResult_status(Integer result_status) {
        this.result_status = result_status;
    }

    @Range(max = 999999999, min = 0)
    public Integer getResult_minLimitDealerBlance() {
        return result_minLimitDealerBlance;
    }

    public void setResult_minLimitDealerBlance(Integer result_minLimitDealerBlance) {
        this.result_minLimitDealerBlance = result_minLimitDealerBlance;
    }

    @Range(max = 999999999, min = 0)
    public Integer getResult_maxLimitPlayerNumber() {
        return result_maxLimitPlayerNumber;
    }

    public void setResult_maxLimitPlayerNumber(Integer result_maxLimitPlayerNumber) {
        this.result_maxLimitPlayerNumber = result_maxLimitPlayerNumber;
    }

    @Range(max = 999999999, min = 0)
    public Integer getResult_minLimitPlayerBlance() {
        return result_minLimitPlayerBlance;
    }

    public void setResult_minLimitPlayerBlance(Integer result_minLimitPlayerBlance) {
        this.result_minLimitPlayerBlance = result_minLimitPlayerBlance;
    }

    @Range(max = 999999999, min = 0)
    public Integer getResult_betMax() {
        return result_betMax;
    }

    public void setResult_betMax(Integer result_betMax) {
        this.result_betMax = result_betMax;
    }

    @Range(max = 999999999, min = 0)
    public Integer getResult_betMin() {
        return result_betMin;
    }

    public void setResult_betMin(Integer result_betMin) {
        this.result_betMin = result_betMin;
    }
}