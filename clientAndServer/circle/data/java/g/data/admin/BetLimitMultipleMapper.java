package g.data.admin;

import org.soul.data.rdb.mybatis.IBaseMapper;
import g.model.admin.po.BetLimitMultiple;
import g.model.admin.so.VUserBetLimitMultipleSo;
import g.model.admin.so.VUserGroupBetLimitMultipleSo;


/**
 * 综合投注限额数据访问对象
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public interface BetLimitMultipleMapper extends IBaseMapper<BetLimitMultiple, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 去系统限额单条记录
     * @return
     */
    BetLimitMultiple selectOne();

    BetLimitMultiple selectUserOne(VUserBetLimitMultipleSo so);

    BetLimitMultiple selectUserGroupOne(VUserGroupBetLimitMultipleSo so);
    //endregion your codes 2

}