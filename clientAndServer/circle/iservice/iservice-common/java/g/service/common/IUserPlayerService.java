package g.service.common;

import g.model.player.po.UserPlayer;
import g.model.player.vo.UserPlayerListVo;
import g.model.player.vo.UserPlayerVo;
import org.soul.iservice.support.IBaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 会员扩展信息服务接口
 *
 * @author tom
 * @time 2016-5-12 11:54:55
 */
//region your codes 1
public interface IUserPlayerService extends IBaseService<UserPlayerListVo, UserPlayerVo, UserPlayer, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 通过id来查询玩家信息
     * @param id
     * @return UserPlayer
     */
    UserPlayer selectUserPlayerInfoById (Integer id);

    /**
     * 更新玩家信息
     * @param queryMap
     * @return boolean
     */
    boolean updateUserPlayerInfo (HashMap<String, Object> queryMap);

    /**
     * 查询玩家总数
     * @return
     */
    Integer selectUserPlayerNumber();


    /**
     * 查询玩家 土豪排行榜
     * @return
     */
    List<Map<String, Object>> selectPlayerOrderByWalletBalance(Map map);

    /**
     * 账户信息保密
     * @param list
     * @return
     */
    List<Map<String, Object>> hiddenUsername(List<Map<String, Object>> list);

}