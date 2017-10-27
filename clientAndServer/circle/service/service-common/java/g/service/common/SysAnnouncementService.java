package g.service.common;

import g.data.player.VSysUserMapper;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.sys.vo.SysParamVo;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import g.data.admin.SysAnnouncementMapper;
import g.model.admin.po.SysAnnouncement;
import g.model.admin.vo.SysAnnouncementListVo;
import g.model.admin.vo.SysAnnouncementVo;
import g.model.player.po.VSysUser;

import java.util.List;


/**
 * 系统公告服务
 *
 * @author orange
 * @time 2015-11-17 14:57:54
 */
//region your codes 1
public class SysAnnouncementService extends BaseService<SysAnnouncementMapper, SysAnnouncementListVo, SysAnnouncementVo, SysAnnouncement, Integer> implements ISysAnnouncementService {
//endregion your codes 1
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private VSysUserMapper vSysUserMapper;
    //region your codes 2
    @Override
    public List<VSysUser> searchSysUser(SysUserListVo listVo) {
        Criteria c = Criteria.add(SysUser.PROP_SUBSYS_CODE,Operator.EQ,listVo.getSearch().getSubsysCode());
        c.addAnd(SysUser.PROP_ID,Operator.EQ,listVo.getSearch().getId());
        return vSysUserMapper.search(c);
    }

    @Override
    public List<SysUser> searchSysUserId(SysUserListVo listVo) {
        Criteria c = Criteria.add(SysUser.PROP_SUBSYS_CODE,Operator.EQ,listVo.getSearch().getSubsysCode());
        return sysUserMapper.searchProperty(c, SysUser.PROP_ID, Order.asc("id"));
    }

    @Override
    public Boolean updateDefaultParam(SysParamVo sysParamVo) {
        return this.mapper.updateDefaultParam(sysParamVo.getSearch().getId());
    }
}