package g.web.agent.controller.form;

import org.hibernate.validator.constraints.Length;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.support.Comment;
import org.soul.web.support.IForm;


/**
 * 站长管理表单验证对象
 *
 * @author tom
 * @time 2016-4-18 10:35:08
 */
//region your codes 1
public class VAccountManageForm implements IForm {
//endregion your codes 1

    //region your codes 2
    private String sysUser_freezeContent;
    private String freezeTime;

    /** 安全密码*/
    private String $permissionPwd;

    @Comment("冻结原因")
    @Depends(property ={"$isExistPassword"},operator = {Operator.IS_EMPTY},value ={""},message = "冻结原因不能为空")
    @Length(max = 1000,min = 1)
    public String getSysUser_freezeContent() {
        return sysUser_freezeContent;
    }

    public void setSysUser_freezeContent(String sysUser_freezeContent) {
        this.sysUser_freezeContent = sysUser_freezeContent;
    }

    @Comment("冻结时间")
    @Depends(property ={"$isExistPassword"},operator = {Operator.IS_EMPTY},value ={""},message = "冻结时间不能为空")
    public String getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(String freezeTime) {
        this.freezeTime = freezeTime;
    }

    @Comment("安全密码")
    @Depends(property ={"$isExistPassword"},operator = {Operator.IS_NOT_EMPTY},value ={""},message = "安全密码不能为空")
    public String get$permissionPwd() {
        return $permissionPwd;
    }

    public void set$permissionPwd(String $permissionPwd) {
        this.$permissionPwd = $permissionPwd;
    }

    //endregion your codes 2

}