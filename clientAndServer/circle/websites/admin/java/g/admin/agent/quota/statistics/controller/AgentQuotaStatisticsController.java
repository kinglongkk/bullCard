package g.admin.agent.quota.statistics.controller;

import g.admin.agent.quota.statistics.form.AgentQuotaStatisticsForm;
import g.admin.agent.quota.statistics.form.AgentQuotaStatisticsSearchForm;
import g.model.admin.agent.quota.statistics.po.AgentQuotaStatistics;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsListVo;
import g.model.admin.agent.quota.statistics.vo.AgentQuotaStatisticsVo;
import g.service.admin.agent.quota.statistics.IAgentQuotaStatisticsService;
import org.soul.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 额度日志管理控制器
 *
 * @author black
 * @time 2016-12-15 11:23:25
 */
@Controller
//region your codes 1
@RequestMapping("/agentQuotaStatistics")
public class AgentQuotaStatisticsController extends BaseCrudController<IAgentQuotaStatisticsService, AgentQuotaStatisticsListVo, AgentQuotaStatisticsVo, AgentQuotaStatisticsSearchForm, AgentQuotaStatisticsForm, AgentQuotaStatistics, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/agent/quota/statistics/";
        //endregion your codes 2
    }

    //region your codes 3

    //endregion your codes 3

}