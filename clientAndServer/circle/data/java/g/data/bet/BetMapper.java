package g.data.bet;

import g.model.bet.po.BetIds;
import g.model.bet.so.BetSo;
import g.model.bet.vo.BetVo;
import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.bet.po.Bet;
import g.model.bet.vo.BetListVo;

import java.util.List;


/**
 * 数据访问对象
 *
 * @author longer
 * @time Apr 26, 2016 4:56:53 PM
 */
//region your codes 1
public interface BetMapper extends IBaseMapper<Bet, Long> {
//endregion your codes 1

    //region your codes 2

    /**
     * 批量结算
     * @param bets
     */
    void settle(List<Bet> bets);

    //TODO sysUserId 写死
    List<Bet> getBetHistory(BetSo so);

    /**
     * 查询交易状况
     * @return
     */
    List<Bet> selectBetAndDetail(BetListVo listVo);

    /**
     * 单玩家单场的各种玩法的投注总额
     * @param betVo
     * @return
     */
    List<Bet> oneMatchOneUserBetTypesAmountSum(BetVo betVo);


    Long getLastBetId(int size);

    /**
     * 批量获取投注IDS
     * @param size
     * @return
     */
    BetIds getBetIds(int size);
    //endregion your codes 2

    /**
     * 获取玩家盈亏汇总
     * @param so
     * @return
     */
    Double getProfitAmount(BetSo so);

}