package g.service.player;

import g.model.agent.vo.UserPlayerGroupVo;
import g.model.player.po.UserPlayer;
import g.model.player.vo.UserPlayerVo;
import g.service.common.IUserPlayerGroupService;
import g.service.common.IUserPlayerService;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by longer on 7/28/16.
 */
public class RegisterService implements IRegisterService {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IUserPlayerService userPlayerService;

    @Autowired
    private IUserPlayerGroupService userPlayerGroupService;

    @Transactional
    @Override
    public void register(SysUserVo sysUserVo) {
        sysUserService.insert(sysUserVo);

        UserPlayerGroupVo userPlayerGroupVo = new UserPlayerGroupVo();
        userPlayerGroupVo.getSearch().setCreateUser(0);     //default admin id is 0
        userPlayerGroupVo = userPlayerGroupService.getDefaultGroup(userPlayerGroupVo);
        Integer defaultGroupId = userPlayerGroupVo.getResult() != null ? userPlayerGroupVo.getResult().getId() : null;
        UserPlayerVo userPlayerVo = new UserPlayerVo();
        UserPlayer userPlayer = new UserPlayer();
        userPlayer.setId(sysUserVo.getResult().getId());
        userPlayer.setWalletBalance(0d);
        userPlayer.setPlayerGroupId(defaultGroupId);
        userPlayer.setInvitationCode(RandomStringTool.randomAlphanumeric(8));
        userPlayerVo.setResult(userPlayer);
        userPlayerService.insert(userPlayerVo);
    }
}
