package g.web.admin.report.controller;

import g.model.SubSysCodeEnum;
import g.web.admin.report.form.ReportCqsscForm;
import g.web.admin.report.form.ReportCqsscSearchForm;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import g.service.report.IReportCqsscService;
import g.model.report.po.ReportCqssc;
import g.model.report.vo.ReportCqsscListVo;
import g.model.report.vo.ReportCqsscVo;
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
 * 控制器
 *
 * @author tom
 * @time 2016-8-1 15:34:21
 */
@Controller
//region your codes 1
@RequestMapping("/reportCqssc")
public class ReportCqsscController extends BaseCrudController<IReportCqsscService, ReportCqsscListVo, ReportCqsscVo, ReportCqsscSearchForm, ReportCqsscForm, ReportCqssc, Integer> {

    private static final String INDEX = "report/Index";
    @Autowired
    private IReportCqsscService reportCqsscService;
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/report/";
        //endregion your codes 2
    }

    @RequestMapping("/masterReportByGroup")
    public String masterReportByGroup(ReportCqsscListVo listVo, @FormModel("search") @Valid ReportCqsscSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response){

        //设置用户身份
        listVo.getSearch().setUserType(SubSysCodeEnum.ADMIN.getCode());
        ReportCqsscListVo search = reportCqsscService.selectTotalValue(listVo, "selectByGroup");
        model.addAttribute("command", search);
        return ServletTool.isAjaxSoulRequest(request) ? "/report/GroupIndex" + "Partial" : "/report/GroupIndex";
    }

    @RequestMapping("/masterReport")
    public String masterReport(ReportCqsscListVo listVo, @FormModel("search") @Valid ReportCqsscSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        //设置用户身份
        listVo.getSearch().setUserType(SubSysCodeEnum.ADMIN.getCode());
        ReportCqsscListVo search = reportCqsscService.selectTotalValue(listVo, "selectDetail");
        model.addAttribute("command", search);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }

    @RequestMapping("/agentReport")
    public String agentReport(ReportCqsscListVo listVo, @FormModel("search") @Valid ReportCqsscSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        //设置用户身份
        listVo.getSearch().setUserType(SubSysCodeEnum.ADMIN.getCode());
        ReportCqsscListVo search = reportCqsscService.selectTotalValue(listVo, "selectDetail");
        model.addAttribute("command", search);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }


    //region your codes 3


    //endregion your codes 3

}