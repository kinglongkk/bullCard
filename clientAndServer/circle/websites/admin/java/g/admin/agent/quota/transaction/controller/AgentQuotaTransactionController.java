package g.admin.agent.quota.transaction.controller;

import g.admin.agent.quota.transaction.form.AgentQuotaTransactionForm;
import g.admin.agent.quota.transaction.form.AgentQuotaTransactionSearchForm;
import g.model.TransactionTypeEnum;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionListVo;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;
import g.service.admin.agent.quota.transaction.IAgentQuotaTransactionService;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.sort.Direction;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 额度日志控制器
 *
 * @author black
 * @time 2016-12-3 10:47:44
 */
@Controller
@RequestMapping("/agentQuotaTransaction")
public class AgentQuotaTransactionController extends BaseCrudController<IAgentQuotaTransactionService, AgentQuotaTransactionListVo, AgentQuotaTransactionVo, AgentQuotaTransactionSearchForm, AgentQuotaTransactionForm, AgentQuotaTransaction, Integer> {

    private static final String AGENT_QUOTA_URI = "detail.include/AgentQuotaTransaction";

    @Autowired
    private IAgentQuotaTransactionService transactionService;

    @Override
    protected String getViewBasePath() {

        return "/sys/agent/";
    }

    /**
     * List Action的基础方法
     *
     * @param listVo   继承了 BaseListVo的Vo
     * @param form     表单验证对象
     * @param result   表单验证结果
     * @param model    Spring Mvc model对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return List视图
     */
    @Override
    public String list(AgentQuotaTransactionListVo listVo, @FormModel("search") @Valid AgentQuotaTransactionSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (form.getStartTime() != null && form.getEndTime() != null) {
            listVo.getSearch().setStartTime(form.getStartTime());
            listVo.getSearch().setEndTime(form.getEndTime());
        }
        listVo.getQuery().addOrder(AgentQuotaTransaction.PROP_DATE, Direction.DESC);
        listVo = transactionService.search(listVo);
        if (listVo.getResult() != null && !listVo.getResult().isEmpty()) {
            for (int i = 0; i < listVo.getResult().size(); i++) {
                if (listVo.getResult().get(i).getType().equals(TransactionTypeEnum.SETTLE.getCode())) {
                    listVo.getResult().get(i).setType(TransactionTypeEnum.SETTLE.getDesc());
                } else {
                    listVo.getResult().get(i).setType(TransactionTypeEnum.DEPOSIT.getDesc());
                }
            }
            model.addAttribute("agentId", listVo.getResult().get(0).getAgentId());
        }
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + AGENT_QUOTA_URI + "Partial" :
                getViewBasePath() + AGENT_QUOTA_URI ;
    }

}