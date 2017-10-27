package g.admin.sys.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 * Created by black on 2016/9/13.
 */
public class SysSiteLogoForm implements IForm {


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
