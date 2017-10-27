package g.data.playerstatistics;

import g.model.playerstatistics.po.AgentDateActual;
import org.soul.data.rdb.mybatis.IBaseMapper;


/**
 * 代理商每日实时统计表数据访问对象
 *
 * @author lenovo
 * @time 2017-1-21 9:43:02
 */
//region your codes 1
public interface AgentDateActualMapper extends IBaseMapper<AgentDateActual, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 玩家代理商实时统计盈亏
     * @return
     */
    boolean playerAgentActual();
}