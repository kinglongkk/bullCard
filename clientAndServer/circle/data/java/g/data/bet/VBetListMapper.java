package g.data.bet;

import g.model.bet.po.VBetList;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;
import java.util.Map;


/**
 * 注单数据访问对象
 *
 * @author tom
 * @time 2016-6-7 19:50:29
 */
//region your codes 1
public interface VBetListMapper extends IBaseQueryMapper<VBetList, Long> {
//endregion your codes 1

    //region your codes 2

    List<VBetList> getBetStatistics(Long matchId);

    //endregion your codes 2

}