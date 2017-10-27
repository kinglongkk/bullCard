package g.data.admin;

import g.model.admin.po.BetLimit;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;


/**
 * 投注限额数据访问对象
 *
 * @author tom
 * @time 2016-4-20 11:35:40
 */
//region your codes 1
public interface BetLimitMapper extends IBaseMapper<BetLimit, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 获得系统单注单项限额
     * @return
     */
    List<BetLimit> getSystemBetLimit();

    //endregion your codes 2

}