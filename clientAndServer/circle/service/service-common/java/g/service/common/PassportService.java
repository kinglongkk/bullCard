package g.service.common;

import g.model.UserTypeEnum;
import org.soul.commons.bean.Pair;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.data.mapper.security.privilege.SysResourceMapper;
import org.soul.data.mapper.security.privilege.SysRoleMapper;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.iservice.passport.IPassportService;
import org.soul.iservice.passport.exception.AccountDisabledException;
import org.soul.iservice.passport.exception.AccountInActiveException;
import org.soul.iservice.passport.exception.AccountPasswordException;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.passport.vo.PassportVo;
import org.soul.model.security.privilege.po.SysResource;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.so.SysUserSo;
import org.soul.model.security.privilege.vo.SysResourceVo;
import org.soul.model.security.privilege.vo.SysUserStatusVo;
import org.soul.service.security.privilege.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by longer on 3/20/15.
 */
public class PassportService implements IPassportService {

    private Log log = LogFactory.getLog(getClass());

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysResourceMapper sysResourceMapper;


    @Override
    public PassportVo login(PassportVo passportVo) throws LoginException {
        SysUserSo param = passportVo.getSearch();
        passportVo.getQuery().setSearch(param);
        List<SysUser> users = sysUserMapper.search(passportVo.getQuery().byUserNameAndSubSysCodeAndSiteId());

        if (CollectionTool.isEmpty(users)) {
            String message = MessageFormat.format("用户名:[{0}]不存在!",passportVo.getSearch().getUsername());
            throw new AccountNotFoundException(message);
        }
        SysUser sysUser = users.get(0);
        if (!param.getPassword().equals(sysUser.getPassword())) {
            String message = MessageFormat.format("用户名:[{0}]或密码不对!",passportVo.getSearch().getUsername());
            throw new AccountPasswordException(message,sysUser.getId(),sysUser.getLoginErrorTimes());
        }


        param.setId(sysUser.getId());
        SysUserStatusVo statusVo = sysUserService.getStatus(passportVo);
        switch (statusVo.getStatus()) {
            case INACTIVE:
                throw new AccountInActiveException(statusVo.getStatus().getTrans());
            case LOCKED:
                throw new AccountLockedException(statusVo.getReasonCode());
            case DISABLED:
                throw  new AccountDisabledException(statusVo.getStatus().getTrans());
        }
        passportVo.setResult(sysUser);
        return passportVo;
    }

    /**
     * 获取权限
     * @param passportVo
     * @return
     */
    @Override
    public Set<String> findPermissions(PassportVo passportVo) {
        Set<String> permissions = null;
        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(passportVo.getSearch().getUserType());
        if (UserTypeEnum.isWholePerimssion(userTypeEnum)) {
            //全权限
            permissions = doFindPermissions(passportVo.getSearch().getSubsysCode(), passportVo.getSearch().getSiteId());
        } else {
            //个人权限(子帐号类)
            permissions = sysUserService.findPermissions(passportVo);
        }
        return permissions;
    }

    @Override
    public Map<String, Pair<String,Boolean>> findPermissionMapping(PassportVo passportVo) {
        //子帐号类,才需要获取个人权限
        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(passportVo.getSearch().getUserType());
        if (UserTypeEnum.isNeedCheckUrl(userTypeEnum)) {

            return sysUserService.findPermissionMapping(passportVo);
        }
        return null;
    }

    /**
     * 获取站长可拥有的资源
     * @param subsysCode
     * @param siteId
     * @return
     */
    private Set<String> doFindPermissions(String subsysCode, Integer siteId) {
        SysResourceVo vo = new SysResourceVo();
        vo.getSearch().setSubsysCode(subsysCode);
        List<String> list = sysResourceMapper.searchProperty(vo.getQuery().bySubsysCode(), SysResource.PROP_PERMISSION);
        SysUserService.trimList(list);
        return new HashSet<String>(list);
    }

    @Override
    public PassportVo logout(PassportVo param) {
        return null;
    }

}