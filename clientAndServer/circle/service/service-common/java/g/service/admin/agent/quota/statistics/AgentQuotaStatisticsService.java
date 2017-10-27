package g.service.admin.agent.quota.statistics;

import g.data.admin.agent.quota.statistics.AgentQuotaStatisticsMapper;
import g.model.admin.agent.quota.statistics.po.AgentQuotaStatistics;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsListVo;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsVo;
import org.soul.service.support.BaseService;

import java.util.Map;


/**
 * 额度日志管理服务
 *
 * @author black
 * @time 2016-12-15 11:23:25
 */
//region your codes 1
public class AgentQuotaStatisticsService extends BaseService<AgentQuotaStatisticsMapper, AgentQuotaStatisticsListVo, AgentQuotaStatisticsVo, AgentQuotaStatistics, Integer> implements IAgentQuotaStatisticsService {
//endregion your codes 1

    //region your codes 2
    public boolean consumePlayerQuota(Map map) {

        return this.mapper.consumePlayerQuota(map);
    }


    public boolean consumeAgentQuota() {

        return this.mapper.consumeAgentQuota();
    }

    public boolean truncateDate() {

        return this.mapper.truncateDate();
    }
    //endregion your codes 2

}