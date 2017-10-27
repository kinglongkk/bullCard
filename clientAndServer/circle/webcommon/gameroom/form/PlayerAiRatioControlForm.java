package g.admin.gameroom.form;


import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;


/**
 * Ai与玩家配比设置表单验证对象
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
//region your codes 1
public class PlayerAiRatioControlForm implements IForm {

    private Integer result_bringGoldMin;
    private Integer result_bringGoldMax;
    private Integer result_intervalMinTime;
    private Integer result_intervalMaxTime;
    private Integer result_leaveMinTime;
    private Integer result_leaveMaxTime;
    private Integer result_restMinGames;
    private Integer result_restMaxGames;
    private Integer result_roomMaxQty;
    private Integer result_playerProportionMin;
    private Integer result_playerProportionMax;
    private Integer result_aiProportionMin;
    private Integer result_aiProportionMax;

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
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_playerProportionMin() {
        return result_playerProportionMin;
    }

    public void setResult_playerProportionMin(Integer result_playerProportionMin) {
        this.result_playerProportionMin = result_playerProportionMin;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_playerProportionMax() {
        return result_playerProportionMax;
    }

    public void setResult_playerProportionMax(Integer result_playerProportionMax) {
        this.result_playerProportionMax = result_playerProportionMax;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_aiProportionMin() {
        return result_aiProportionMin;
    }

    public void setResult_aiProportionMin(Integer result_aiProportionMin) {
        this.result_aiProportionMin = result_aiProportionMin;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_aiProportionMax() {
        return result_aiProportionMax;
    }

    public void setResult_aiProportionMax(Integer result_aiProportionMax) {
        this.result_aiProportionMax = result_aiProportionMax;
    }
}