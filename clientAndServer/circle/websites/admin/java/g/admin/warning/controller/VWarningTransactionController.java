package g.admin.warning.controller;


import g.admin.warning.form.VWarningTransactionForm;
import g.admin.warning.form.VWarningTransactionSearchForm;
import g.common.tool.DateTimeTool;
import g.model.warning.po.VWarningTransaction;
import g.model.warning.vo.VWarningTransactionListVo;
import g.model.warning.vo.VWarningTransactionVo;
import g.service.warning.IVWarningTransactionService;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * 控制器
 *
 * @author lenovo
 * @time 2017-2-28 14:09:18
 */
@Controller
//region your codes 1
@RequestMapping("/vWarningTransaction")
public class VWarningTransactionController extends BaseCrudController<IVWarningTransactionService, VWarningTransactionListVo, VWarningTransactionVo, VWarningTransactionSearchForm, VWarningTransactionForm, VWarningTransaction, Long> {
//endregion your codes 1

    @Autowired
    private IVWarningTransactionService vwarningTransactionService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/warning/vtransaction/";
        //endregion your codes 2
    }

    /**
     * 充值记录
     */
    @RequestMapping("/trascationInfo")
    public String loginInfo(VWarningTransactionListVo listVo, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String sysUserId = request.getParameter("sysUserId");
        String date = request.getParameter("date");
        Date endTime = DateTimeTool.getDateByFull(date);
        Date startTime = DateTimeTool.getStartDate(DateTimeTool.getYmd(endTime));
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(VWarningTransaction.PROP_PLAYER_ID, Operator.EQ, Integer.parseInt(sysUserId)),
                new Criterion(VWarningTransaction.PROP_COMPLETION_TIME, Operator.GE, startTime),
                new Criterion(VWarningTransaction.PROP_COMPLETION_TIME, Operator.LE, endTime)
        });
        listVo = vwarningTransactionService.search(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("sysUserId",sysUserId);
        model.addAttribute("date", date);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/info/updetail/Index" + "Partial" : "/warning/info/updetail/Index";
    }
}