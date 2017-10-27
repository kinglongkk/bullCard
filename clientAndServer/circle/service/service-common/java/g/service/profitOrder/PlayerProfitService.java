package g.service.profitOrder;

import org.soul.service.support.BaseService;
import g.data.profitOrder.PlayerProfitMapper;
import g.model.profitOrder.po.PlayerProfit;
import g.model.profitOrder.vo.PlayerProfitListVo;
import g.model.profitOrder.vo.PlayerProfitVo;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈利榜服务
 *
 * @author black
 * @time 2016-11-4 10:15:10
 */
//region your codes 1
public class PlayerProfitService extends BaseService<PlayerProfitMapper, PlayerProfitListVo, PlayerProfitVo, PlayerProfit, Integer> implements IPlayerProfitService {
//endregion your codes 1

    public boolean deleteExpiredData(Map map) {

        return this.mapper.deleteExpiredData(map);
    }

    public boolean playerProfitOrder(Map map){

        return this.mapper.playerProfitOrder(map);
    }

    public List<Map<String, Object>> selectPlayerProfitOrder(Map map){

        return this.mapper.selectPlayerProfitOrder(map);
    }

}