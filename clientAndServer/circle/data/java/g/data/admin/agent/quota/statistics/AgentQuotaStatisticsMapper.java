package g.data.admin.agent.quota.statistics;

import java.util.Map;

import g.model.admin.agent.quota.statistics.po.AgentQuotaStatistics;
import org.soul.data.rdb.mybatis.IBaseMapper;


/**
 * 额度日志管理数据访问对象
 *
 * @author black
 * @time 2016-12-15 11:23:24
 */
//region your codes 1
public interface AgentQuotaStatisticsMapper extends IBaseMapper<AgentQuotaStatistics, Integer> {
//endregion your codes 1

    //region your codes 2
    /***
     * 代理旗下玩家额度消耗
     * @param map
     * @return
     */
    boolean consumePlayerQuota(Map map);


    /**
     * 代理额度消耗
     * @return
     */
    boolean consumeAgentQuota();

    /**
     * 清除表中所有数据
     * @return
     */
    boolean truncateDate();
    //endregion your codes 2
}