package g.data.bet;

import g.model.bet.po.VBetSettle;
import org.apache.ibatis.annotations.Param;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;


/**
 * 注单结算数据访问对象
 *
 * @author longer
 * @time May 19, 2016 1:42:07 PM
 */
//region your codes 1
public interface VBetSettleMapper extends IBaseQueryMapper<VBetSettle, Long> {
//endregion your codes 1

    //region your codes 2
    List<VBetSettle> groupBySettleState(@Param("id") Long matchId);
    //endregion your codes 2

}