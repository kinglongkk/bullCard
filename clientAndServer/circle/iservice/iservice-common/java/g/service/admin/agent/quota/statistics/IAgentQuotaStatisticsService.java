package g.service.admin.agent.quota.statistics;

import java.util.Map;

import org.soul.iservice.support.IBaseService;
import g.model.admin.agent.quota.statistics.po.AgentQuotaStatistics;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsListVo;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsVo;


/**
 * 额度日志管理服务接口
 *
 * @author black
 * @time 2016-12-15 11:23:24
 */
//region your codes 1
public interface IAgentQuotaStatisticsService extends IBaseService<AgentQuotaStatisticsListVo, AgentQuotaStatisticsVo, AgentQuotaStatistics, Integer> {
//endregion your codes 1

    //region your codes 2
    /***
     * 代理旗下玩家额度消耗
     * @param map
     * @return
     */
    boolean consumePlayerQuota(Map map);
    //endregion your codes 2

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
}