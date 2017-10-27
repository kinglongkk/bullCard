package g.admin.gameroom.form;


import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Ai玩家设置表单验证对象
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
//region your codes 1
public class PlayerAiControlForm implements IForm {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2
    private Integer result_aiQty;
    private Integer result_bringGoldMin;
    private Integer result_bringGoldMax;
    private Integer result_intervalMinTime;
    private Integer result_intervalMaxTime;
    private Integer result_leaveMinTime;
    private Integer result_leaveMaxTime;
    private Integer result_restMinGames;
    private Integer result_restMaxGames;
    private Integer result_roomMaxQty;
    private Date result_beginControlTime;
    private Integer result_controlCycle;
    private String  result_chipRates;
    private Integer result_betCount;
    @NotNull
    @Range(max = 1000, min = 0)
    public Integer getResult_betCount() {
        return result_betCount;
    }

    public void setResult_betCount(Integer result_betCount) {
        this.result_betCount = result_betCount;
    }

    @NotNull
    public String getResult_chipRates() {
        return result_chipRates;
    }

    public void setResult_chipRates(String result_chipRates) {
        this.result_chipRates = result_chipRates;
    }

    @NotNull
    public Integer getResult_controlCycle() {
        return result_controlCycle;
    }

    public void setResult_controlCycle(Integer result_controlCycle) {
        this.result_controlCycle = result_controlCycle;
    }

    @NotNull
    public Date getResult_beginControlTime() {
        return result_beginControlTime;
    }

    public void setResult_beginControlTime(Date result_beginControlTime) {
        this.result_beginControlTime = result_beginControlTime;
    }

    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_aiQty() {
        return result_aiQty;
    }

    public void setResult_aiQty(Integer result_aiQty) {
        this.result_aiQty = result_aiQty;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_bringGoldMin() {
        return result_bringGoldMin;
    }

    public void setResult_bringGoldMin(Integer result_bringGoldMin) {
        this.result_bringGoldMin = result_bringGoldMin;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_bringGoldMax() {
        return result_bringGoldMax;
    }

    public void setResult_bringGoldMax(Integer result_bringGoldMax) {
        this.result_bringGoldMax = result_bringGoldMax;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_intervalMinTime() {
        return result_intervalMinTime;
    }

    public void setResult_intervalMinTime(Integer result_intervalMinTime) {
        this.result_intervalMinTime = result_intervalMinTime;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_intervalMaxTime() {
        return result_intervalMaxTime;
    }

    public void setResult_intervalMaxTime(Integer result_intervalMaxTime) {
        this.result_intervalMaxTime = result_intervalMaxTime;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_leaveMinTime() {
        return result_leaveMinTime;
    }

    public void setResult_leaveMinTime(Integer result_leaveMinTime) {
        this.result_leaveMinTime = result_leaveMinTime;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_leaveMaxTime() {
        return result_leaveMaxTime;
    }

    public void setResult_leaveMaxTime(Integer result_leaveMaxTime) {
        this.result_leaveMaxTime = result_leaveMaxTime;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_restMinGames() {
        return result_restMinGames;
    }

    public void setResult_restMinGames(Integer result_restMinGames) {
        this.result_restMinGames = result_restMinGames;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_restMaxGames() {
        return result_restMaxGames;
    }

    public void setResult_restMaxGames(Integer result_restMaxGames) {
        this.result_restMaxGames = result_restMaxGames;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_roomMaxQty() {
        return result_roomMaxQty;
    }

    public void setResult_roomMaxQty(Integer result_roomMaxQty) {
        this.result_roomMaxQty = result_roomMaxQty;
    }
}