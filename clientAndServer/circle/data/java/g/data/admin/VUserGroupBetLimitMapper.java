package g.data.admin;

import g.model.admin.po.VUserGroupBetLimit;
import g.model.admin.so.VUserGroupBetLimitSo;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;


/**
 * 玩家分组单注投注限额数据访问对象
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public interface VUserGroupBetLimitMapper extends IBaseQueryMapper<VUserGroupBetLimit, Integer> {
//endregion your codes 1

    //region your codes 2
    List<VUserGroupBetLimit> getPlayerGroupBetLimit(VUserGroupBetLimitSo vUserGroupBetLimitSo);
    //endregion your codes 2

}