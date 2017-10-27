package g.web.agent.controller.agent.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;


/**
 * 注单表单验证对象
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
//region your codes 1
public class VBetListForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String remark_remarkContent;

    @NotBlank
    @Length(max = 100)
    @Comment("备注内容")
    public String getRemark_remarkContent() {
        return remark_remarkContent;
    }

    public void setRemark_remarkContent(String remark_remarkContent) {
        this.remark_remarkContent = remark_remarkContent;}
    //endregion your codes 2

}