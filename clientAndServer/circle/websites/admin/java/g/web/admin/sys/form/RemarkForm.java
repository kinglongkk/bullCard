package g.web.admin.sys.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;


/**
 * 玩家备注表表单验证对象
 *
 * Created by cheery using soul-code-generator on 2015-7-28 13:58:02
 */
//region your codes 1
public class RemarkForm implements IForm {

    private String remarkTitle;
    private String remarkContent;

    @Comment("备注标题")
    @NotBlank(message = "fund.fund.playerRemarkForm.remarkTitle.notBlank")
    public String getRemarkTitle() {
        return remarkTitle;
    }

    public void setRemarkTitle(String remarkTitle) {
        this.remarkTitle = remarkTitle;
    }

    @Comment("备注内容")
    @NotBlank(message = "fund.fund.playerRemarkForm.remarkContent.notBlank")
    public String getRemarkContent() {
        return remarkContent;
    }

    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }
}