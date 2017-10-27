package g.web.common.passport;

import g.service.common.IUserPlayerService;
import g.model.player.vo.UserPlayerVo;
import g.web.common.SessionManagerCommon;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.web.shiro.common.delegate.IPassportListener;
import org.soul.web.shiro.common.delegate.PassportEvent;

/**
 * @author: tom
 * @date: 16-5-12
 */
public class CachePassportListener implements IPassportListener {

    private IUserPlayerService userPlayerService;

    @Override
    public void onLoginSuccess(PassportEvent passportEvent) {
        SysUser sysUser = passportEvent.getSysUser();
        if (sysUser != null) {
            Integer id = sysUser.getId();
            UserPlayerVo userPlayerVo = new UserPlayerVo();
            userPlayerVo.getSearch().setId(id);
            userPlayerVo = this.userPlayerService.get(userPlayerVo);
            SessionManagerCommon.setSessionUserPlayer(userPlayerVo.getResult());
        }
    }

    @Override
    public void onLoginFailure(PassportEvent passportEvent) {

    }

    @Override
    public void onLogoutSuccess(PassportEvent passportEvent) {

    }

    public IUserPlayerService getUserPlayerService() {
        return userPlayerService;
    }

    public void setUserPlayerService(IUserPlayerService userPlayerService) {
        this.userPlayerService = userPlayerService;
    }
}
