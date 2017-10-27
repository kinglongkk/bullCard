package g.model.account.vo;

import g.model.enums.FreezeTimeEnum;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import org.soul.model.security.privilege.po.SysUser;
import g.model.common.Const;
import g.model.account.po.VAccountManage;
import g.model.account.so.VAccountManageSo;
import g.model.admin.po.Remark;

import java.util.Date;


/**
 * 账号管理值对象
 *
 * @author tom
 * @time 2015-12-28 10:35:08
 */
//region your codes 1
public class VAccountManageVo extends BaseObjectVo<VAccountManage, VAccountManageSo, VAccountManageVo.VAccountManageQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = -1103040040926533418L;
    //endregion your codes 5

    /**
     *  账号管理查询逻辑
     */
    public static class VAccountManageQuery extends AbstractQuery<VAccountManageSo> {

        //region your codes 6
        private static final long serialVersionUID = -4366330344551115946L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4
    // 操作用户
    private SysUser sysUser;
    //冻结时间
    private String freezeTime;
    //备注
    private Remark remark;
    // 冻结结束时间
    private Date freezeEndTime;
    // 安全密码
    private String permissionPwd;
    // 安全密码校验标识
    private String isExistPassword;

    public String getFreezeTime() {
        return freezeTime;
    }

    public void setFreezeTime(String freezeTime) {
        this.freezeTime = freezeTime;
        if (!FreezeTimeEnum.FORERVE.getCode().equals(freezeTime) && StringTool.isNotEmpty(freezeTime)) {
            setFreezeEndTime(DateTool.addHours(new Date(), Integer.parseInt(freezeTime)));
        } else {
            setFreezeEndTime(Const.Platform_Forever_Date);
        }
    }

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Date getFreezeEndTime() {
        return freezeEndTime;
    }

    public void setFreezeEndTime(Date freezeEndTime) {
        this.freezeEndTime = freezeEndTime;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getIsExistPassword() {
        return isExistPassword;
    }

    public void setIsExistPassword(String isExistPassword) {
        this.isExistPassword = isExistPassword;
    }

    public String getPermissionPwd() {
        return permissionPwd;
    }

    public void setPermissionPwd(String permissionPwd) {
        this.permissionPwd = permissionPwd;
    }
//endregion your codes 4

}