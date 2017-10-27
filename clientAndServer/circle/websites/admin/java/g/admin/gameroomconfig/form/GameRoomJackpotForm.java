package g.admin.gameroomconfig.form;

import g.web.admin.consts.FormValidRegExps;
import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 游戏房间配置奖池信息表表单验证对象
 * Created by black on 2017/3/23.
 */
//region your codes 1
public class GameRoomJackpotForm implements IForm {
//endregion your codes 1

    //region your codes 2
    /**
     * 奖池总额
     */
    private String  result_jackpotSum;
    /**
     * 奖池最高积累金额
     */
    private String result_maxJackpotLimit;
    /**
     * 奖池最低下限金额
     */
    private String result_minJackpotLimit;
    /**
     * 当局最高可输金额
     */
    private String result_maxLimitGameLose;
    /**
     * 当局设定收缴值
     */
    private String result_maxJackpotAmatch;

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_MATCH, message = "common.LIMIT_MATCH")
    public String getResult_jackpotSum() {
        return result_jackpotSum;
    }
    public void setResult_jackpotSum(String result_jackpotSum) {
        this.result_jackpotSum = result_jackpotSum;
    }

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_MATCH, message = "common.LIMIT_MATCH")
    public String getResult_maxJackpotLimit() {
        return result_maxJackpotLimit;
    }
    public void setResult_maxJackpotLimit(String result_maxJackpotLimit) {
        this.result_maxJackpotLimit = result_maxJackpotLimit;

    }

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_MATCH, message = "common.LIMIT_MATCH")
    public String getResult_minJackpotLimit() {
        return result_minJackpotLimit;
    }
    public void setResult_minJackpotLimit(String result_minJackpotLimit) {
        this.result_minJackpotLimit = result_minJackpotLimit;

    }

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_MATCH, message = "common.LIMIT_MATCH")
    public String getResult_maxLimitGameLose() {
        return result_maxLimitGameLose;
    }
    public void setResult_maxLimitGameLose(String result_maxLimitGameLose) {
        this.result_maxLimitGameLose = result_maxLimitGameLose;
    }

    @NotNull
    @Pattern(regexp = FormValidRegExps.LIMIT_MATCH, message = "common.LIMIT_MATCH")
    public String getResult_maxJackpotAmatch() {
        return result_maxJackpotAmatch;
    }
    public void setResult_maxJackpotAmatch(String result_maxJackpotAmatch) {
        this.result_maxJackpotAmatch = result_maxJackpotAmatch;
    }

    //endregion your codes 2
}
