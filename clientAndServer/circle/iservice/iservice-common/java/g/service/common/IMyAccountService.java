package g.service.common;

import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.vo.SysUserVo;
import g.model.admin.vo.MyAccountVo;
import g.model.player.vo.VSysUserVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 运营商我的账号视图
 *
 * @author jeff
 * @time 2015-11-22 09:00:00
 */
//region your codes 1
public interface IMyAccountService extends ISysUserService {
//endregion your codes 1

    //region your codes 2
    /**
     * 我的账号
     * @param myAccountVo
     * @return MyAccountVo
     */
    MyAccountVo getMyAccount(MyAccountVo myAccountVo);


    MyAccountVo edit(MyAccountVo myAccountVo);


    /**
     * 今日新增玩家
     * @param vo
     * @return List<Integer>
     */
    List<Integer> searchAddPlayer(SysUserVo vo);


    /**
     * 查询该代理下面的所有player
     * @param vSysUserVo
     * @return List
     */
    List searchPlayerCountBalance(VSysUserVo vSysUserVo);


    /**
     * 查找该代理下的未结算注单
     * @param ownerId 该玩家所属代理者的ID
     * @return Double
     */
    Double selectPreOrderMoney(Integer ownerId);
    //endregion your codes 2

}