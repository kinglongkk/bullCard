package g.web.agent.controller.report.controller;

import g.model.SubSysCodeEnum;
import g.model.report.po.ReportCqssc;
import g.model.report.vo.ReportCqsscListVo;
import g.model.report.vo.ReportCqsscVo;
import g.service.report.IReportCqsscService;
import g.web.agent.controller.report.form.ReportCqsscForm;
import g.web.agent.controller.report.form.ReportCqsscSearchForm;
import g.web.agent.session.SessionManager;
import org.soul.commons.net.ServletTool;
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
 * 代理报表控制器
 * @author black
 * @time 2016-08-09
 */

@Controller
@RequestMapping("/report")
public class AgentReportController extends BaseCrudController<IReportCqsscService, ReportCqsscListVo, ReportCqsscVo, ReportCqsscSearchForm, ReportCqsscForm, ReportCqssc, Integer> {

    private static final String INDEX = "report/Index";
    @Autowired
    private IReportCqsscService reportCqsscService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/report/";
        //endregion your codes 2
    }

    @RequestMapping("/agentReportByGroup")
    public String agentReportByGroup(ReportCqsscListVo listVo, @FormModel("search") @Valid ReportCqsscSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response){

        listVo.getSearch().setUsername(SessionManager.getUserName());
        //设置用户身份
        listVo.getSearch().setUserType(SubSysCodeEnum.AGENT.getCode());
        String selectType = "selectByGroup";
        ReportCqsscListVo search = reportCqsscService.selectTotalValue(listVo, selectType);
        model.addAttribute("command", search);
        return ServletTool.isAjaxSoulRequest(request) ? "report/GroupIndex" + "Partial" : "report/GroupIndex";
    }

    @RequestMapping("/agentReport")
    public String agentReport(ReportCqsscListVo listVo, @FormModel("search") @Valid ReportCqsscSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo.getSearch().setUsername(SessionManager.getUserName());
        //设置用户身份
        listVo.getSearch().setUserType(SubSysCodeEnum.AGENT.getCode());
        ReportCqsscListVo search = reportCqsscService.selectTotalValue(listVo, "selectDetail");
        model.addAttribute("command", search);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }
}