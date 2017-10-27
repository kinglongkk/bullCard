package g.web.admin.sys.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;


/**
 * 站长子账号表表单验证对象
 *
 * @author cj
 * @time 2015-8-25 14:46:59
 */
//region your codes 1
@Comment("站长子账号表单验证")
public class MyAccountAvatarForm implements IForm {
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