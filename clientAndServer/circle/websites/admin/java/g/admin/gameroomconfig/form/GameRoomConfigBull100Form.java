package g.admin.gameroomconfig.form;

import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;


/**
 * 游戏房间配置信息表表单验证对象
 *
 * @author lenovo
 * @time 2016-12-17 17:20:19
 */
//region your codes 1
public class GameRoomConfigBull100Form implements IForm {

    private Integer result_dealerBlance;
    private Integer result_dealerBlanceTip;
    private Integer result_dealerBlanceQuit;
    private Integer result_betTimes;
    @NotNull
    @Range(min = 0, max = 999999999)
    public Integer getResult_dealerBlance() {
        return result_dealerBlance;
    }

    public void setResult_dealerBlance(Integer result_dealerBlance) {
        this.result_dealerBlance = result_dealerBlance;
    }
    @NotNull
    @Range(min = 0, max = 100)
    public Integer getResult_dealerBlanceTip() {
        return result_dealerBlanceTip;
    }

    public void setResult_dealerBlanceTip(Integer result_dealerBlanceTip) {
        this.result_dealerBlanceTip = result_dealerBlanceTip;
    }
    @NotNull
    @Range(min = 0, max = 100)
    public Integer getResult_dealerBlanceQuit() {
        return result_dealerBlanceQuit;
    }

    public void setResult_dealerBlanceQuit(Integer result_dealerBlanceQuit) {
        this.result_dealerBlanceQuit = result_dealerBlanceQuit;
    }
    @NotNull
    @Range(min = 0, max = 999999999)
    public Integer getResult_betTimes() {
        return result_betTimes;
    }

    public void setResult_betTimes(Integer result_betTimes) {
        this.result_betTimes = result_betTimes;
    }
}