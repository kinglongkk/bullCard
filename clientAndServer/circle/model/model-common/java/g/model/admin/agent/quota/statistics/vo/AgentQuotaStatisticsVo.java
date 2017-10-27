package g.model.admin.agent.quota.statistics.vo;


import org.soul.commons.query.Criteria;
import org.soul.model.common.AbstractQuery;
import org.soul.model.common.BaseObjectVo;
import g.model.admin.agent.quota.statistics.po.AgentQuotaStatistics;
import g.model.admin.agent.quota.statistics.so.AgentQuotaStatisticsSo;


/**
 * 额度日志管理值对象
 *
 * @author black
 * @time 2016-12-15 11:23:25
 */
//region your codes 1
public class AgentQuotaStatisticsVo extends BaseObjectVo<AgentQuotaStatistics, AgentQuotaStatisticsSo, AgentQuotaStatisticsVo.AgentQuotaStatisticsQuery> {
//endregion your codes 1

    //region your codes 5
    private static final long serialVersionUID = 3890393590794273267L;
    //endregion your codes 5

    /**
     *  额度日志管理查询逻辑
     */
    public static class AgentQuotaStatisticsQuery extends AbstractQuery<AgentQuotaStatisticsSo> {

        //region your codes 6
        private static final long serialVersionUID = -8028274319907897010L;
        //endregion your codes 6

        @Override
        public Criteria getCriteria() {
            //region your codes 2
            return null;
            //endregion your codes 2
        }

        //region your codes 3

        //endregion your codes 3

    }

    //region your codes 4

    //endregion your codes 4

}