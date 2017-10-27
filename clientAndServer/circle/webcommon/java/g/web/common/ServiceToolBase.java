package g.web.common;

import g.service.common.IVSubAccountService;
import org.soul.commons.dubbo.DubboTool;
import org.soul.iservice.security.privilege.ISysRoleService;
import org.soul.iservice.security.privilege.ISysUserService;

import static org.soul.commons.dubbo.DubboTool.getService;

/**
 * Created by tony on 16-1-7.
 */
public class ServiceToolBase {

    public static ISysUserService sysUserService() {
        return getService(ISysUserService.class);
    }

    public static ISysRoleService sysRoleService() {
        return DubboTool.getService(ISysRoleService.class);
    }
    /**
     * 返回角色-资源关联表服务接口      *      * @return 角色-资源关联表服务接口
     */
    public static org.soul.iservice.security.privilege.ISysUserRoleService sysUserRoleService() {
        return getService(org.soul.iservice.security.privilege.ISysUserRoleService.class);
    }

    /**
     * 返回子账户视图远程服务实例
     *
     * @return 子账户视图远程服务实例
     */
    public static IVSubAccountService vSubAccountService() {
        return getService(IVSubAccountService.class);
    }


}
