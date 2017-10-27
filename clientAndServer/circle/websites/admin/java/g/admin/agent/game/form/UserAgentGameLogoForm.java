package g.admin.agent.game.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * 代理游戏logo表单验证
 * Created by black on 2016/12/5.
 */
public class UserAgentGameLogoForm implements IForm {

    private String result_logoPath;

    @NotBlank
    @Comment("图标地址")
    public String getResult_logoPath() {
        return result_logoPath;
    }

    public void setResult_logoPath(String result_logoPath) {
        this.result_logoPath = result_logoPath;
    }
}
