package g.data.profitOrder;

import g.model.profitOrder.po.PlayerProfit;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈利榜数据访问对象
 *
 * @author black
 * @time 2016-11-4 10:15:09
 */
//region your codes 1
public interface PlayerProfitMapper extends IBaseMapper<PlayerProfit, Integer> {
//endregion your codes 1

    /**
     * 将过期的盈利榜数据清除
     * @param map
     * @return
     */
    boolean deleteExpiredData(Map map);

    /**
     * 玩家盈利榜
     * @return
     */
    boolean playerProfitOrder(Map map);

    /**
     * 玩家盈利榜 查询
     * @param map
     * @return
     */
    List<Map<String, Object>> selectPlayerProfitOrder(Map map);

}