package g.web.agent.controller.agent.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.web.support.IForm;


/**
 * 系统公告表单验证对象
 *
 * @author orange
 * @time 2015-11-17 14:57:54
 */
//region your codes 1
public class SystemAnnouncementForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_local;
    @NotBlank(message = "common.pleaseSelect")
    public String getResult_local() {
        return result_local;
    }

    public void setResult_local(String result_local) {
        this.result_local = result_local;
    }
    //endregion your codes 2

}