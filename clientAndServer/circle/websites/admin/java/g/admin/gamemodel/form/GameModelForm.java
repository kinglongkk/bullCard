package g.admin.gamemodel.form;

import org.soul.web.support.IForm;

import javax.validation.constraints.NotNull;


/**
 * 游戏表表单验证对象
 *
 * @author lenovo
 * @time 2016-8-25 14:20:28
 */
//region your codes 1
public class GameModelForm implements IForm {
//endregion your codes 1

    private String result_name;
    private Integer result_status;

    @NotNull
    public String getResult_name () { return this.result_name; }
    public void setResult_name (String value) { this.result_name = value; }

    @NotNull
    public Integer getResult_status () { return  this.result_status; }
    public void setResult_status (Integer value) { this.result_status = value; }

}