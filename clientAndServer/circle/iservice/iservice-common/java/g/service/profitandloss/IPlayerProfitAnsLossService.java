package g.service.profitandloss;

import g.model.api.result.ObjectResults;
import g.model.profitandloss.po.PlayerProfitAndLoss;
import g.model.profitandloss.vo.PlayerProfitAndLossListVo;
import g.model.profitandloss.vo.PlayerProfitAndLossVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;
import java.util.Map;


/**
 * 玩家盈亏服务接口
 *
 * @author black
 * @time 2017-5-31 15:15:09
 */
//region your codes 1
public interface IPlayerProfitAnsLossService extends IBaseService<PlayerProfitAndLossListVo, PlayerProfitAndLossVo, PlayerProfitAndLoss, Integer> {
//endregion your codes 1

    /**
     * 玩家盈亏统计 一点十五分
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