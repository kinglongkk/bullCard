package g.service.common;

import g.model.agent.po.VSubAccount;
import g.model.agent.vo.VSubAccountVo;
import g.model.setting.vo.SysRoleListVo;
import org.soul.iservice.support.IBaseService;
import org.soul.model.sys.vo.SysAuditLogListVo;
import g.model.agent.vo.VSubAccountListVo;

import java.util.List;
import java.util.Map;


/**
 * 子账户视图服务接口
 *
 * @author jeff
 * @time 2015-10-20 10:49:12
 */
//region your codes 1
public interface IVSubAccountService extends IBaseService<VSubAccountListVo, VSubAccountVo, VSubAccount, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 新增编辑需要的数据
     * @param vSubAccountVo
     * @return
     */
    VSubAccountVo preCreateOrEdit(VSubAccountVo vSubAccountVo);

    /**
     * 根据subsys_code获取角色
     * @param vSubAccountListVo
     * @return
     */
    List<Map<String,Object>> getRoles(VSubAccountListVo vSubAccountListVo);

    /**
     * 重置子账号用户角色
     * @param vSubAccountVo
     * @return
     */
    VSubAccountVo resetRole(VSubAccountVo vSubAccountVo);

    /**
     * 根据ids改变状态
     * @param vSubAccountVo
     * @return
     */
    VSubAccountVo changeStatusByIds(VSubAccountVo vSubAccountVo);

    /**
     * 将默认角色重置为系统默认的权限
     * @param vSubAccountVo
     * @return
     */
    VSubAccountVo roleResetDefault(VSubAccountVo vSubAccountVo);

    SysRoleListVo settingRole(SysRoleListVo sysRoleListVo);

    /**
     * 物理删除子账号
     * @param vSubAccountVo
     * @return
     */
    int deleteSubAccounts(VSubAccountVo vSubAccountVo);

    /**
     * 根据用户ID 获取角色ID
     *
     * add by eagle
     * @param sysRoleListVo
     * @return
     */
    List<Integer> searchRoleIds(SysRoleListVo sysRoleListVo);

    /**
     * 日志查询
     * @param listVo
     * @return
     */
    SysAuditLogListVo queryLogs(SysAuditLogListVo listVo);
    //endregion your codes 2


}