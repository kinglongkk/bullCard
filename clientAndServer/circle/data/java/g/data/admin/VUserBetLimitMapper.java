package g.data.admin;

import g.model.admin.so.VUserBetLimitSo;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;
import g.model.admin.po.VUserBetLimit;

import java.util.List;


/**
 * 代理单注限额视图数据访问对象
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public interface VUserBetLimitMapper extends IBaseQueryMapper<VUserBetLimit, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 查询代理单注限额
     * @param so
     * @return
     */
    List<VUserBetLimit> getUserBetLimit(VUserBetLimitSo so);
    //endregion your codes 2

}