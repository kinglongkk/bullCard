package g.service.admin;


import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.model.agent.vo.VAgentManageVo;
import org.soul.iservice.support.IBaseService;
import org.soul.model.sys.vo.SysAuditLogListVo;


/**
 * 代理管理服务接口
 *
 * @author tom
 */

public interface IVAgentManageService extends IBaseService<VAgentManageListVo, VAgentManageVo, VAgentManage, Integer> {

    VAgentManageVo updateAgent(VAgentManageVo objectVo);

    /**
     * 编辑代理
     * @param vo
     * @return
     */
    VAgentManageVo editAgent(VAgentManageVo vo);

    /**
     * 通过userId和moduleType查询log
     * @param vo
     * @return
     */
    SysAuditLogListVo searchLoginLog(SysAuditLogListVo vo);



}