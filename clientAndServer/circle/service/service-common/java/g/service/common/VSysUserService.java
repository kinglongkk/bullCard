package g.service.common;

import g.data.admin.RemarkMapper;
import g.data.player.VSysUserMapper;
import g.model.RemarkEnum;
import g.model.admin.po.Remark;
import g.model.admin.vo.AccountVo;
import g.model.common.FreezeType;
import g.model.enums.FreezeTimeEnum;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserListVo;
import g.model.player.vo.VSysUserVo;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.po.SysUserStatus;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 *
 * <p/>
 * Created by orange
 */
public class VSysUserService extends BaseService<VSysUserMapper, VSysUserListVo, VSysUserVo, VSysUser, Integer> implements IVSysUserService {
    //region your codes
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RemarkMapper remarkMapper;

    @Override
    public AccountVo accountOption(AccountVo accountVo) {
        Integer userId = accountVo.getResult().getId();
        accountVo.setResult(sysUserMapper.get(userId));
        return accountVo;
    }

    @Override
    @Transactional
    public AccountVo setAccountDisabled(AccountVo accountVo) {
        /*更新user信息*/
        SysUser sysUser = accountVo.getResult();
        sysUser.setUpdateUser(accountVo._getUserId());
        sysUser.setUpdateTime(new Date());
        sysUser.setDisabledTime(new Date());
        sysUser.setDisabledUser(accountVo._getUserId());
        sysUser.setStatus(SysUserStatus.DISABLED.getCode());
        sysUser.setFreezeCode(accountVo.getGroupCode());

        sysUserMapper.updateOnly(sysUser,
                SysUser.PROP_FREEZE_CODE,
                SysUser.PROP_UPDATE_TIME,
                SysUser.PROP_STATUS,
                SysUser.PROP_UPDATE_USER,
                SysUser.PROP_DISABLED_TIME,
                SysUser.PROP_DISABLED_USER
        );

        /*插入备注信息*/
        if (accountVo.getRemark() != null) {
            Remark remark = new Remark();
            remark.setModel(RemarkEnum.PLAYER_USERBLOCK_IP.getModel());
            remark.setRemarkType(RemarkEnum.PLAYER_USERBLOCK_IP.getType());
            remark.setEntityId(accountVo.getResult().getId());
            remark.setOperatorId(accountVo._getUserId());
            remark.setOperator(accountVo.getOperator());
            remark.setEntityUserId(accountVo.getResult().getId());
            remark.setRemarkContent(accountVo.getRemark());
            remark.setRemarkTime(new Date());
            remark.setRemarkTitle(accountVo.getRemarkTitle());
            remarkMapper.insert(remark);
        }

        return accountVo;
    }

    @Override
    @Transactional
    public AccountVo setAccountFreeze(AccountVo accountVo) {
        SysUser sysUser = accountVo.getResult();
        sysUser.setFreezeEndTime(getFreezeEndTime(accountVo.getChooseFreezeTime()));
        sysUser.setFreezeStartTime(new Date());
        sysUser.setFreezeTime(new Date());
        sysUser.setUpdateUser(accountVo._getUserId());
        sysUser.setUpdateTime(new Date());
        sysUser.setFreezeUser(accountVo._getUserId());
        sysUser.setFreezeCode(accountVo.getGroupCode());
        sysUser.setFreezeType(FreezeType.MANUAL.getCode());
        sysUser.setAccountFreezeRemark(accountVo.getRemark());
        sysUser.setFreezeTitle(accountVo.getSearch().getFreezeTitle());
        sysUser.setFreezeContent(accountVo.getSearch().getFreezeContent());

        sysUserMapper.updateOnly(sysUser,
                SysUser.PROP_UPDATE_USER,/*更新用户*/
                SysUser.PROP_UPDATE_TIME,/*更新时间*/
                SysUser.PROP_FREEZE_CODE,
                SysUser.PROP_FREEZE_END_TIME,
                SysUser.PROP_FREEZE_START_TIME,
                SysUser.PROP_FREEZE_USER,
                SysUser.PROP_FREEZE_TIME,
                SysUser.PROP_FREEZE_TYPE,
                SysUser.PROP_ACCOUNT_FREEZE_REMARK,
                SysUser.PROP_FREEZE_TITLE,
                SysUser.PROP_FREEZE_CONTENT

        );

        /*插入备注信息*/
        if (accountVo.getRemark() != null) {
            Remark remark = new Remark();
            remark.setModel(RemarkEnum.PLAYER_USER_FREEZE.getModel());
            remark.setRemarkType(RemarkEnum.PLAYER_USER_FREEZE.getType());
            remark.setEntityId(accountVo.getResult().getId());
            remark.setEntityUserId(accountVo.getResult().getId());
            remark.setOperatorId(accountVo._getUserId());
            remark.setOperator(accountVo.getOperator());
            remark.setEntityUserId(accountVo.getResult().getId());
            remark.setRemarkContent(accountVo.getRemark());
            remark.setRemarkTime(new Date());
            remark.setRemarkTitle(accountVo.getRemarkTitle());
            remarkMapper.insert(remark);
        }

        return accountVo;
    }

    public Date getFreezeEndTime(String freezeTime) {
        if (StringTool.isBlank(freezeTime))
            return null;
        if (FreezeTimeEnum.FORERVE.getCode().equals(freezeTime))
            return DateTool.addYears(new Date(), 3000);
        return DateTool.addHours(new Date(), Integer.valueOf(freezeTime));
    }

    @Override
    public VSysUser findOnLineUser(VSysUserVo vo) {
        return this.mapper.findOnLineUser(vo.getSearch().getId());
    }

    @Override
    public String findAgentKey(VSysUserVo vo) {
        return this.mapper.findAgentKey(vo.getSearch().getUsername());
    }
    //endregion your codes

}