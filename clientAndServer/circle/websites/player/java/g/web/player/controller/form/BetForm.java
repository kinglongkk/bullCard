package g.web.player.controller.form;

import g.web.player.consts.FormValidRegExps;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

/**
 * Created by mark on 16-6-30.
 */
public class BetForm implements IForm {

    /**重庆时时彩玩法**/
    private String[] $betInfos$$;

    private String $matchId;

    private String $singleAmount;

    @NotBlank
    @Pattern(regexp = "\\w{3}")
    public String[] get$betInfos$$() {
        return $betInfos$$;
    }

    public void set$betInfos$$(String[] $betInfos$$) {
        this.$betInfos$$ = $betInfos$$;
    }

    @NotBlank
    @Pattern(regexp = FormValidRegExps.ZERO_POSITIVE_INTEGER,message = "请输入正数！")
    @Length(min = 1,max = 9)
    public String get$matchId() {
        return $matchId;
    }

    public void set$matchId(String $matchId) {
        this.$matchId = $matchId;
    }
    @NotBlank
    @Digits(integer = 8,fraction = 2,message = "请输入0~100的数字,支持2位精度的小数")
    @Length(min = 1,max = 9)
    public String get$singleAmount() {
        return $singleAmount;
    }

    public void set$singleAmount(String $singleAmount) {
        this.$singleAmount = $singleAmount;
    }
}
