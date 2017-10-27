package g.web.agent.controller.form;

import org.hibernate.validator.constraints.NotBlank;
import org.soul.web.support.IForm;

/**
 * Created by jeff on 15-12-22.
 */
public class AccountOptionForm implements IForm {

    private String $groupCode;

    @NotBlank(message = "请选择原因！")
    public String get$groupCode() {
        return $groupCode;
    }

    public void set$groupCode(String $groupCode) {
        this.$groupCode = $groupCode;
    }
}
