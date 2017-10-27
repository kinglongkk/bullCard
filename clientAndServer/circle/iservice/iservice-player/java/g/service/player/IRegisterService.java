package g.service.player;

import org.soul.model.security.privilege.vo.SysUserVo;

/**
 * Created by longer on 3/30/16.
 * 注册服务
 */
public interface IRegisterService {

    /**
     * 注册
     * @param sysUserVo
     */
    void register(SysUserVo sysUserVo);

}
