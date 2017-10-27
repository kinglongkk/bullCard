package g.web.admin.controller.announcement.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;
import javax.validation.constraints.NotNull;


/**
 * 游戏公告视图表单验证对象
 *
 */
//region your codes 1
public class VGameAnnouncementForm implements IForm {
//endregion your codes 1

    //region your codes 2
    /**
     * 公告标题
     */
    private String result_title;
    /**
     * 游戏id
     */
    private String result_gameId;

    @NotBlank(message = "标题不能为空")
    @Length(max = 100)
    @Comment("标题")
    public String getResult_title() {
        return result_title;
    }
    public void setResult_title(String result_title) {
        this.result_title = result_title;
    }

    @NotNull
    public String getResult_gameId() {
        return result_gameId;
    }
    public void setResult_gameId(String result_gameId) {
        this.result_gameId = result_gameId;
    }

    //endregion your codes 2

}