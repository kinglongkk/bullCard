package g.service.common;

import g.model.admin.vo.VSysUserContactListVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.service.support.BaseService;
import g.data.admin.VSysUserContactMapper;
import g.model.admin.po.VSysUserContact;
import g.model.admin.vo.VSysUserContactVo;

import java.util.List;


/**
 * 子账户视图服务
 *
 * @author eagle
 * @time 2013-03-28
 */
//region your codes 1
public class VSysUserContactService extends BaseService<VSysUserContactMapper, VSysUserContactListVo, VSysUserContactVo, VSysUserContact, Integer> implements IVSysUserContactService {
//endregion your codes 1

    //region your codes 2
    @Override
    public List<VSysUserContact> searchSysUserContact(VSysUserContactListVo listVo) {
        Criteria c = Criteria.add(SysUser.PROP_SUBSYS_CODE,Operator.EQ,listVo.getSearch().getSubsysCode());
        c.addAnd(SysUser.PROP_ID,Operator.EQ,listVo.getSearch().getId());
        return this.mapper.search(c);
    }
    //endregion your codes 2

}