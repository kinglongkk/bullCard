package g.admin.agent.currency.controller;

import org.soul.web.controller.BaseCrudController;
import g.service.admin.agent.currency.IAgentCurrencyExchangeService;
import g.model.admin.agent.currency.po.AgentCurrencyExchange;
import g.model.admin.agent.currency.vo.AgentCurrencyExchangeListVo;
import g.model.admin.agent.currency.vo.AgentCurrencyExchangeVo;
import g.admin.agent.currency.form.AgentCurrencyExchangeSearchForm;
import g.admin.agent.currency.form.AgentCurrencyExchangeForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 货币汇率控制器
 *
 * @author black
 * @time 2016-11-28 11:28:04
 */
@Controller
//region your codes 1
@RequestMapping("/agentCurrencyExchange")
public class AgentCurrencyExchangeController extends BaseCrudController<IAgentCurrencyExchangeService, AgentCurrencyExchangeListVo, AgentCurrencyExchangeVo, AgentCurrencyExchangeSearchForm, AgentCurrencyExchangeForm, AgentCurrencyExchange, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/agent/currency/";
        //endregion your codes 2
    }

    //region your codes 3

    //endregion your codes 3

}