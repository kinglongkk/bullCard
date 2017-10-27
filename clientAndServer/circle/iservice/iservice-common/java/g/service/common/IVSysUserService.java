package g.service.common;

import g.model.admin.vo.AccountVo;
import org.soul.iservice.support.IBaseService;
import g.model.player.po.VSysUser;
import g.model.player.vo.VSysUserListVo;
import g.model.player.vo.VSysUserVo;

/**
 * Created by orange on 16-4-1.
 */
public interface IVSysUserService extends IBaseService<VSysUserListVo, VSysUserVo, VSysUser, Integer> {

    /*账号停用，账号冻结，余额冻结 信息*/
    AccountVo accountOption(AccountVo accountVo);
    AccountVo setAccountDisabled(AccountVo accountVo);
    AccountVo setAccountFreeze(AccountVo accountVo);

    /**
     * 查询账号在线状态
     *
     * @param vo
     * @return
     */
    VSysUser findOnLineUser(VSysUserVo vo);

    /**
     *查询商户密钥
     *
     * @param vo
     * @return
     */
    String findAgentKey(VSysUserVo vo);

}
