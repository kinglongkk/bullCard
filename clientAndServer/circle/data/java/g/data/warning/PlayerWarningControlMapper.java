package g.data.warning;

import g.model.warning.po.PlayerWarningControl;
import org.soul.data.rdb.mybatis.IBaseMapper;


/**
 * 玩家实时监控控制表数据访问对象
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public interface PlayerWarningControlMapper extends IBaseMapper<PlayerWarningControl, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 保存之前把所有数据变为失效
     */
    void updateWarningStatus(Integer userId);

}