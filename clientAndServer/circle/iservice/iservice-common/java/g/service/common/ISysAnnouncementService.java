package g.service.common;

import g.model.admin.po.SysAnnouncement;
import g.model.admin.vo.SysAnnouncementListVo;
import org.soul.iservice.support.IBaseService;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.sys.vo.SysParamVo;
import g.model.admin.vo.SysAnnouncementVo;
import g.model.player.po.VSysUser;

import java.util.List;


/**
 * 系统公告服务接口
 *
 * @author orange
 * @time 2015-11-17 14:57:53
 */
//region your codes 1
public interface ISysAnnouncementService extends IBaseService<SysAnnouncementListVo, SysAnnouncementVo, SysAnnouncement, Integer> {
//endregion your codes 1

    //region your codes 2

    List<VSysUser> searchSysUser(SysUserListVo listVo);

    List<SysUser> searchSysUserId(SysUserListVo listVo);

    Boolean updateDefaultParam(SysParamVo sysParamVo);
    //endregion your codes 2

}