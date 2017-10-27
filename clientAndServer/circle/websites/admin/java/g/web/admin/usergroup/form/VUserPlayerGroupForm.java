package g.web.admin.usergroup.form;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.web.support.IForm;
import g.web.admin.usergroup.controller.VUserPlayerGroupController;

/**
 * 表单验证对象
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroupForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_groupName;

    @Remote(checkClass = VUserPlayerGroupController.class,checkMethod = "checkGroupnameExist",additionalProperties = "result.id", message = "group.groupname.repeat")
    public String getResult_groupName() {
        return result_groupName;
    }

    public void setResult_groupName(String result_groupName) {
        this.result_groupName = result_groupName;
    }


    //endregion your codes 2

}