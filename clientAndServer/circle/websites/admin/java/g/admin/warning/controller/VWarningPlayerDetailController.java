package g.admin.warning.controller;

import g.admin.warning.form.VWarningPlayerDetailForm;
import g.admin.warning.form.VWarningPlayerDetailSearchForm;
import g.common.tool.DateTimeTool;
import g.model.warning.po.MatchPlayer;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.vo.MatchPlayerListVo;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import g.model.warning.vo.VWarningPlayerDetailVo;
import g.service.warning.IVWarningPlayerDetailService;
import org.apache.commons.lang3.ArrayUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 控制器
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
@Controller
//region your codes 1
@RequestMapping("/vWarningPlayerDetail")
public class VWarningPlayerDetailController extends BaseCrudController<IVWarningPlayerDetailService, VWarningPlayerDetailListVo, VWarningPlayerDetailVo, VWarningPlayerDetailSearchForm, VWarningPlayerDetailForm, VWarningPlayerDetail, Long> {
//endregion your codes 1
    @Autowired
    private IVWarningPlayerDetailService vmarningPlayerDetailService;
    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/warning/vplayerdetail/";
        //endregion your codes 2
    }
    /**
     * 游戏记录详情
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/betDetailInfo")
    public String betDetailInfo(VWarningPlayerDetailListVo listVo,VWarningPlayerDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String sysUserId = request.getParameter("sysUserId");
        String date = request.getParameter("date");
        Date endTime = DateTimeTool.getDateByFull(date);
        Date startTime = DateTimeTool.getStartDate(DateTimeTool.getYmd(endTime));

        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(VWarningPlayerDetail.PROP_SYS_USER_ID, Operator.EQ, Integer.parseInt(sysUserId)),
                new Criterion(VWarningPlayerDetail.PROP_BET_TIME, Operator.GE, startTime),
                new Criterion(VWarningPlayerDetail.PROP_BET_TIME, Operator.LE, endTime),
                new Criterion(VWarningPlayerDetail.PROP_SETTLE_STATUS, Operator.EQ, "20")
        });
        listVo = vmarningPlayerDetailService.search(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("sysUserId",sysUserId);
        model.addAttribute("date", date);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/info/playerdetail/Index" + "Partial" : "/warning/info/playerdetail/Index";
    }

    /**
     * 赛事游戏详情
     * @return
     */
    @RequestMapping("/betMatchDetailInfo")
    public String betMatchDetailInfo(VWarningPlayerDetailListVo listVo,VWarningPlayerDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String matchIds = request.getParameter("matchIds");
        String[] mid= matchIds.split(",");
        List<Long> list = new ArrayList<Long>();
        for(String d :mid){
            list.add( Long.valueOf(d));
        }
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(VWarningPlayerDetail.PROP_MATCH_ID, Operator.IN, list),
                new Criterion(VWarningPlayerDetail.PROP_SETTLE_STATUS, Operator.EQ, "20")
        });
        listVo = vmarningPlayerDetailService.search(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("matchIds",matchIds);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/info/playerdetail/IndexMatch" + "Partial" : "/warning/info/playerdetail/IndexMatch";
    }
    /**
     * 赛事记录
     * @param listVo
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/matchInfo")
    public String matchInfo(MatchPlayerListVo listVo, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String sysUserId = request.getParameter("sysUserId");
        String[] matchIds = request.getParameter("matchIds").split(",");
        List<Long> list = new ArrayList<Long>();
        for(String d :matchIds){
            list.add( Long.valueOf(d));
        }
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(MatchPlayer.PROP_SYS_USER_ID, Operator.EQ, Integer.parseInt(sysUserId)),
               new Criterion(MatchPlayer.PROP_MATCH_ID, Operator.IN, list)
        });
        listVo = vmarningPlayerDetailService.getMatchList(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("sysUserId",sysUserId);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/info/matchdetail/Index" + "Partial" : "/warning/info/matchdetail/Index";
    }
}