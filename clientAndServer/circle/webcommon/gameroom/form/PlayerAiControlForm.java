package g.admin.gameroom.form;


import org.hibernate.validator.constraints.Range;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;


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
    private Integer result_levelMinTime;
    private Integer result_levelMaxTime;
    private Integer result_restMinGames;
    private Integer result_restMaxGames;
    private Integer result_roomMaxQty;
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
    public Integer getResult_levelMinTime() {
        return result_levelMinTime;
    }

    public void setResult_levelMinTime(Integer result_levelMinTime) {
        this.result_levelMinTime = result_levelMinTime;
    }
    @NotNull
    @Range(max = 999999999, min = 0)
    public Integer getResult_levelMaxTime() {
        return result_levelMaxTime;
    }

    public void setResult_levelMaxTime(Integer result_levelMaxTime) {
        this.result_levelMaxTime = result_levelMaxTime;
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