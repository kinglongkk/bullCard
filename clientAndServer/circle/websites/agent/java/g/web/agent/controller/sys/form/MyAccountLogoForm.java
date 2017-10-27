package g.web.agent.controller.sys.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * 代理头像表单验证
 * Created by black on 2017/7/10.
 */
//region your codes 1
public class MyAccountLogoForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_avatarUrl;


    @NotBlank
    @Comment("头像地址")
    public String getResult_avatarUrl() {
        return result_avatarUrl;
    }
    public void setResult_avatarUrl(String result_avatarUrl) {
        this.result_avatarUrl = result_avatarUrl;
    }

    //endregion your codes 2
}
