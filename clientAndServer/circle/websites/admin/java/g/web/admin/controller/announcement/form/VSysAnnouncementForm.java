package g.web.admin.controller.announcement.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;


/**
 * 系统公告视图表单验证对象
 *
 * @author orange
 * @time 2015-12-3 11:45:57
 */
//region your codes 1
public class VSysAnnouncementForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String content$$;
    private String title$$;

    @NotBlank(message = "内容不能为空")
    @Length(max = 2000)
    @Comment("内容")
    public String getContent$$() {
        return content$$;
    }

    public void setContent$$(String content$$) {
        this.content$$ = content$$;
    }

    @NotBlank(message = "标题不能为空")
    @Length(max = 100)
    @Comment("标题")
    public String getTitle$$() {
        return title$$;
    }

    public void setTitle$$(String title$$) {
        this.title$$ = title$$;
    }
    //endregion your codes 2

}