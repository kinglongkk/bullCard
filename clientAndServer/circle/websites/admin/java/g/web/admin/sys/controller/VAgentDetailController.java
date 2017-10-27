package g.web.admin.sys.controller;

import g.web.admin.sys.form.VAgentManageSearchForm;
import g.web.admin.tools.ServiceTool;
import g.model.ModuleType;
import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.web.common.cache.ParamTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Paging;
import org.soul.model.sys.po.SysParam;
import org.soul.model.sys.so.SysAuditLogSo;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import g.web.admin.sys.form.VAgentManageForm;
import g.service.admin.IVAgentManageService;
import g.model.DictEnum;
import g.model.SiteParamEnum;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.vo.VUserBetLimitMultipleListVo;
import g.model.admin.vo.VUserBetLimitVo;
import g.model.agent.vo.VAgentManageVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author: tom
 * @date: 16-4-12
 */
@Controller
@RequestMapping("/vAgentManage/detail")
public class VAgentDetailController extends BaseCrudController<IVAgentManageService, VAgentManageListVo, VAgentManageVo, VAgentManageSearchForm, VAgentManageForm, VAgentManage, Integer> {

    private static final String AGENT_DETAIL_URI = "Detail";
    private static final String AGENT_BASIC_URI = "detail.include/AgentDetail";
    private static final String AGENT_DETAIL_LOG_URI = "detail.include/ViewLogs";
    private static final String AGENT_DETAIL_PARTIAL_LOG_URI = "detail.include/PartialLogs";

    @Override
    protected String getViewBasePath() {
        return "/sys/agent/";
    }


    @RequestMapping("/detail")
    protected String detail(VAgentManageVo objectVo, Model model) {
        objectVo = getService().get(objectVo);
        objectVo.getResult().calStatus();
        betlimitDetail(objectVo);
        model.addAttribute("command", objectVo);
        return getViewBasePath() + AGENT_DETAIL_URI;
    }

    @RequestMapping("/agentDetail")
    protected String agentDetail(VAgentManageVo objectVo, Model model) {
        objectVo = getService().get(objectVo);
        objectVo.getResult().calStatus();
        betlimitDetail(objectVo);
        model.addAttribute("command",objectVo);
        return getViewBasePath() + AGENT_BASIC_URI;
    }

    /**
     * 代理日志
     * @param model
     * @param sysAuditLogListVo
     * @return
     */
    @RequestMapping("/log")
    public String log(Model model, SysAuditLogListVo sysAuditLogListVo, HttpServletRequest request) {
        SysAuditLogSo search = sysAuditLogListVo.getSearch();
        search.setModuleType(ModuleType.PASSPORT_LOGIN.getCode());
        Paging paging = new Paging();
        paging.setPageSize(10);
        paging.setPageNumber(1);
        sysAuditLogListVo.setPaging(paging);
        sysAuditLogListVo.setSearch(search);
        SysAuditLogListVo logListVo = getService().searchLoginLog(sysAuditLogListVo);
        model.addAttribute("userType", DictTool.get(DictEnum.COMMON_USER_TYPE));//用户类型
        model.addAttribute("opType", DictTool.get(DictEnum.Log_OpType));//操作类型
        model.addAttribute("command", logListVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + AGENT_DETAIL_PARTIAL_LOG_URI;
        } else {
            return getViewBasePath() + AGENT_DETAIL_LOG_URI;
        }
    }

    private void betlimitDetail(VAgentManageVo objectVo) {
        SysParam sysParam = ParamTool.getSysParam(SiteParamEnum.SYS_BET_MIN);
        objectVo.setSysParam(sysParam);
        // 玩家限额设置
        VUserBetLimitVo vUserBetLimitVo = new VUserBetLimitVo();
        vUserBetLimitVo.getSearch().setSysUserId(objectVo.getSearch().getId());
        List<VUserBetLimit> userBetLimit = ServiceTool.vUserBetLimitService().getUserBetLimit(vUserBetLimitVo);
        objectVo.setUserBetLimit(userBetLimit);
        // 玩家综合过关单注最高限额
        VUserBetLimitMultipleListVo vUserBetLimitMultipleListVo = getvUserBetLimitMultipleListVo(objectVo);
        if (CollectionTool.isNotEmpty(vUserBetLimitMultipleListVo.getResult())) {
            Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap = CollectionTool.groupByProperty(vUserBetLimitMultipleListVo.getResult(),
                    VUserBetLimitMultiple.PROP_BET_NUM, Integer.class);
            objectVo.setBetNumKeyMap(betNumKeyMap);
        }
    }

    private VUserBetLimitMultipleListVo getvUserBetLimitMultipleListVo(VAgentManageVo objectVo) {
        VUserBetLimitMultipleListVo vUserBetLimitMultipleListVo = new VUserBetLimitMultipleListVo();
        vUserBetLimitMultipleListVo.getSearch().setSysUserId(objectVo.getSearch().getId());
        vUserBetLimitMultipleListVo = ServiceTool.vUserBetLimitMultipleService().search(vUserBetLimitMultipleListVo);
        return vUserBetLimitMultipleListVo;
    }
}
