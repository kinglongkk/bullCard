package g.service.profitOrder;

import org.soul.iservice.support.IBaseService;
import g.model.profitOrder.po.PlayerProfit;
import g.model.profitOrder.vo.PlayerProfitListVo;
import g.model.profitOrder.vo.PlayerProfitVo;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈利榜服务接口
 *
 * @author black
 * @time 2016-11-4 10:15:09
 */
//region your codes 1
public interface IPlayerProfitService extends IBaseService<PlayerProfitListVo, PlayerProfitVo, PlayerProfit, Integer> {
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