package g.data.profitandloss;

import g.model.profitandloss.po.PlayerProfitAndLoss;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈亏
 *
 * @author black
 * @time 2017-5-31 15:15:09
 */
//region your codes 1
public interface PlayerProfitAndLossMapper extends IBaseMapper<PlayerProfitAndLoss, Integer> {
//endregion your codes 1

    /**
     * 玩家盈亏统计
     * @param map
     * @return
     */
    boolean playerProfitAndLoss(Map map);

    /**
     * 查询玩家盈亏
     * @param map
     * @return
     */
    List<Map<String, Object>> selectPlayerProfitAndLoss(Map map);

    /**
     * 查询玩家盈亏总条数
     * @param map
     * @return
     */
    Integer selectCount(Map map);
}