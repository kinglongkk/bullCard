package g.data.bet;

import g.model.bet.po.BetDetail;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;


/**
 * 投注详情数据访问对象
 *
 * @author tom
 * @time 2016-5-10 15:13:30
 */
//region your codes 1
public interface BetDetailMapper extends IBaseMapper<BetDetail, Long> {
//endregion your codes 1

    //region your codes 2

    List<BetDetail> getBetDetailByMatchId(Long matchId);

    void saveBetDetail(BetDetail betDetail);

    //endregion your codes 2

}