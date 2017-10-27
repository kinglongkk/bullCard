package g.service.bet;

import g.model.bet.po.Bet;
import g.model.bet.so.BetSo;
import g.model.bet.vo.BetListVo;
import g.model.bet.vo.BetVo;
import org.soul.iservice.support.IBaseService;
import org.soul.model.error.ErrorMessage;

import java.util.List;


/**
 * 服务接口
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public interface IBetService extends IBaseService<BetListVo, BetVo, Bet, Long> {
//endregion your codes 1

    //region your codes 2
    ErrorMessage bet(BetVo vo);

    List<Bet> getBetHistory(BetListVo listVo);

    /**
     * 查询交易状况
     */
    BetListVo getWag(BetListVo listVo);

    void bets(List<BetVo> betVos);

    List<Bet> oneMatchOneUserBetTypesAmountSum(BetVo betVo);

    Long[] getBetIds(int size);

    /**
     * 获取时间段盈亏汇总
     * @param so
     * @return
     */
    Double getProfitAmount(BetSo so);

    //endregion your codes 2

}