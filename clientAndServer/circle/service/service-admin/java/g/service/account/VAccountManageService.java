package g.service.account;

import g.common.security.AuthTool;
import g.data.account.VAccountManageMapper;
import g.data.admin.RemarkMapper;
import g.model.account.po.VAccountManage;
import g.model.account.vo.ResetPwdVo;
import g.model.account.vo.VAccountManageListVo;
import g.model.account.vo.VAccountManageVo;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 账号管理服务
 *
 * @author tom
 * @time 2016-04-18 10:35:08
 */
//region your codes 1
public class VAccountManageService extends BaseService<VAccountManageMapper, VAccountManageListVo, VAccountManageVo, VAccountManage, Integer> implements IVAccountManageService {
//endregion your codes 1

    //region your codes 2
    @Autowired
    private AuthTool authTool;

    @Autowired
    private RemarkMapper remarkMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    @Transactional
    public VAccountManageVo saveFreezeAccount(VAccountManageVo vo) {
        this.remarkMapper.insert(vo.getRemark());
        vo.getSysUser().setFreezeStartTime(new Date());
        vo.getSysUser().setFreezeEndTime(vo.getFreezeEndTime());
        boolean success = this.sysUserMapper.updateOnly(vo.getSysUser(), SysUser.PROP_FREEZE_CONTENT, SysUser.PROP_FREEZE_START_TIME, SysUser.PROP_FREEZE_END_TIME, SysUser.PROP_STATUS, SysUser.PROP_UPDATE_USER, SysUser.PROP_UPDATE_TIME, SysUser.PROP_FREEZE_USER);
        vo.setSuccess(success);
        return vo;
    }

    @Override
    @Transactional
    public VAccountManageVo cancelFreezeAccount(VAccountManageVo vo) {
        vo.getSysUser().setStatus(SysUserStatus.NORMAL.getCode());
        vo.getSysUser().setFreezeEndTime(null);
        vo.getSysUser().setFreezeStartTime(null);
        vo.getSysUser().setFreezeType(null);
        vo.getSysUser().setFreezeContent(null);
        boolean success = this.sysUserMapper.updateOnly(vo.getSysUser(), SysUser.PROP_STATUS, SysUser.PROP_FREEZE_START_TIME,
                SysUser.PROP_FREEZE_END_TIME, SysUser.PROP_UPDATE_USER, SysUser.PROP_UPDATE_TIME);
        vo.setSuccess(success);
        return vo;
    }

    @Override
    @Transactional
    public VAccountManageVo saveAccountStop(VAccountManageVo vo) {
        boolean isSuccess = this.sysUserMapper.updateOnly(vo.getSysUser(), SysUser.PROP_STATUS, SysUser.PROP_UPDATE_TIME, SysUser.PROP_UPDATE_USER, SysUser.PROP_DISABLED_TIME, SysUser.PROP_DISABLED_USER);
        vo.setSuccess(isSuccess);
        return vo;
    }

    @Override
    @Transactional
    public boolean resetSysUserPwd(ResetPwdVo vo) {
        SysUser sysUser = this.sysUserMapper.get(vo.getUserId());
        Boolean isOk;
        if(ResetPwdVo.resetTypePayPwd.equals(vo.getResetType())){
            sysUser.setPermissionPwd(authTool.md5SysUserPermission(vo.getPermissionPwd(), sysUser.getUsername()));
            isOk = sysUserMapper.updateOnly(sysUser,SysUser.PROP_PERMISSION_PWD);
        }else{
            sysUser.setPassword(authTool.md5SysUserPassword(vo.getPassword(), sysUser.getUsername()));
            isOk = sysUserMapper.updateOnly(sysUser,SysUser.PROP_PASSWORD);
        }
        return isOk;
    }

    //endregion your codes 2

}