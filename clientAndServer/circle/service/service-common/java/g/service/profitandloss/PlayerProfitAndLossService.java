package g.service.profitandloss;

import g.data.profitandloss.PlayerProfitAndLossMapper;
import g.model.profitandloss.po.PlayerProfitAndLoss;
import g.model.profitandloss.vo.PlayerProfitAndLossListVo;
import g.model.profitandloss.vo.PlayerProfitAndLossVo;
import org.soul.service.support.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈亏服务
 *
 * @author black
 * @time 2017-5-31 15:15:10
 */
//region your codes 1
public class PlayerProfitAndLossService extends BaseService<PlayerProfitAndLossMapper, PlayerProfitAndLossListVo, PlayerProfitAndLossVo, PlayerProfitAndLoss, Integer> implements IPlayerProfitAnsLossService {
//endregion your codes 1

    public boolean playerProfitAndLoss(Map map) {

        return this.mapper.playerProfitAndLoss(map);
    }

    public List<Map<String, Object>> selectPlayerProfitAndLoss(Map map) {

        return this.mapper.selectPlayerProfitAndLoss(map);
    }

    public Integer selectCount(Map map) {

        return this.mapper.selectCount(map);
    }
}