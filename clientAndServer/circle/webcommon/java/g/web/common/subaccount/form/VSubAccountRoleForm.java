package g.web.common.subaccount.form;

import org.hibernate.validator.constraints.Length;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;

/**
 *  代理银行卡验证
 */
//region your codes 1
@Comment("编辑角色")
public class VSubAccountRoleForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String result_name;

    @Depends(property = "$type",operator = Operator.EQ,value = "rename",message = "角色名称不能为空")
    @Length(max = 10,min = 1,message = "角色名称最大长度10字符")
    @Comment("角色名称")
    public String getResult_name() {
        return result_name;
    }

    public void setResult_name(String result_name) {
        this.result_name = result_name;
    }
    //endregion your codes 2
}