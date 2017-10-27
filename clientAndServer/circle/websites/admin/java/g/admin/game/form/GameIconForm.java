package g.admin.game.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * Created by black on 2016/9/2.
 */

@Comment("游戏图标验证")
public class GameIconForm implements IForm {

    private String result_icon;

    @NotBlank
    @Comment("图标地址")
    public String getResult_icon() {
        return result_icon;
    }

    public void setResult_icon(String result_icon) {
        this.result_icon = result_icon;
    }
}
