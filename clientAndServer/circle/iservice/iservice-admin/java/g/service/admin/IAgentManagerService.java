package g.service.admin;

import org.soul.model.security.privilege.vo.SysUserVo;

/**
 * 代理操作
 * Created by black on 2016/12/20.
 */
public interface IAgentManagerService {

    /**
     * 新增代理信息
     * @param sysUserVo
     * @return
     */
    boolean insertAgent(SysUserVo sysUserVo);
}
