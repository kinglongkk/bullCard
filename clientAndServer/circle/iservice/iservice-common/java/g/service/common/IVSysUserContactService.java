package g.service.common;

import g.model.admin.po.VSysUserContact;
import g.model.admin.vo.VSysUserContactListVo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.vo.VSysUserContactVo;

import java.util.List;


/**
 * 子账户视图服务接口
 *
 * @author orange
 * @time 2016-04-11
 */
//region your codes 1
public interface IVSysUserContactService extends IBaseService<VSysUserContactListVo, VSysUserContactVo, VSysUserContact, Integer> {
//endregion your codes 1

    //region your codes 2
    List<VSysUserContact> searchSysUserContact(VSysUserContactListVo listVo);
    //endregion your codes 2


}