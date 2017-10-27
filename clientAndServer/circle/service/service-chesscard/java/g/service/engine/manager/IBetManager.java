package g.service.engine.manager;

import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import g.model.chesscard.mo.UserBet;
import g.service.chesscard.engine.model.Player;

import java.util.List;

/**
 * Created by Jason on 16/10/9.
 */
public interface IBetManager {
    /**
     * 投注
     *
     * @param player  用户ID
     * @param betType 投注类型
     * @param betItem 投注项
     * @param coin    投注金额
     * @return 是否最后一秒的押注
     */
    boolean betting(Player player, BetTypeEnum betType, IBetItem betItem, Long coin);

    /**
     * 获取当前投注的金币值
     *
     * @param deskId
     * @param userId
     * @return
     */
    long getUserBetCoin(Integer deskId, Integer userId);

    /**
     * 清理投注数据 单局
     *
     * @param deskId
     */
    void clearBetData(Integer deskId);

    /**
     * 获取桌子下投注列表
     *
     * @param deskId
     * @return
     */
    List<UserBet> getUserBetList(Integer deskId, Long matchId);

    /**
     * 获取桌子下投注列表   按座位统计
     *
     * @param deskId
     * @return
     */
    List<UserBet> getUserBetStatistics(Integer deskId);

    /**
     * 投注数
     *
     * @param deskId
     * @param userId
     * @return
     */
    int getBetCount(Integer deskId, Integer userId);
}
