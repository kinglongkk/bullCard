package g.data.player;

import g.model.depositdesk.po.PlayerRecharge;
import g.model.player.po.UserPlayer;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 会员扩展信息数据访问对象
 *
 * @author tom
 * @time 2016-7-17 13:21:01
 */
//region your codes 1
public interface UserPlayerMapper extends IBaseMapper<UserPlayer, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 存款成功后更新玩家钱包余额、充值成功次数、充值总金额
     */
    void updatePlayerByRecharge(PlayerRecharge obj);

    /**
     * 提现审核成功/失败/拒绝
     *
     * @param map
     * @return
     */
    boolean successWalletBalance(HashMap<String, Object> map);

    boolean failWalletBalance(HashMap<String, Object> map);

    boolean refusesWalletBalance(HashMap<String, Object> map);

    boolean updateUserPlayerBalance(Map map);


    //endregion your codes 2

    /**
     * 通过id来查询玩家信息
     * @param id
     * @return UserPlayer
     */
    UserPlayer selectUserPlayerInfoById (Integer id);

    /**
     * 通过invitationCode来查询玩家信息
     * @param invitationCode
     * @return UserPlayer
     */
    UserPlayer selectUserPlayerInfoByInvitationCode (String invitationCode);

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


    List<UserPlayer> getUserPlayerList();

    void  updateUserPlayerBalanceByBet(Map<String,Object> map);
}