package g.admin.warning.controller;

import g.admin.playerstatistics.form.VBetDetailSearchForm;
import g.admin.warning.form.PlayerWarningMultipleForm;
import g.admin.warning.form.PlayerWarningMultipleSearchForm;
import g.common.tool.DateTimeTool;
import g.model.bet.po.VBetDetail;
import g.model.bet.vo.VBetDetailListVo;
import g.model.gameroom.po.PlayerAiControl;
import g.model.player.vo.VSysUserVo;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.po.PlayerWarningWinCount;
import g.model.warning.vo.PlayerWarningMultipleListVo;
import g.model.warning.vo.PlayerWarningMultipleVo;
import g.service.bet.IVBetDetailService;
import g.service.warning.IPlayerWarningMultipleService;
import g.web.admin.tools.ServiceTool;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.iservice.sys.ISysAuditLogService;
import org.soul.model.sys.po.SysAuditLog;
import org.soul.model.sys.vo.SysAuditLogListVo;
import org.soul.web.controller.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数控制器
 *
 * @author lenovo
 * @time 2017-2-25 16:06:12
 */
@Controller
//region your codes 1
@RequestMapping("/playerWarningMultiple")
public class PlayerWarningMultipleController extends BaseCrudController<IPlayerWarningMultipleService, PlayerWarningMultipleListVo, PlayerWarningMultipleVo, PlayerWarningMultipleSearchForm, PlayerWarningMultipleForm, PlayerWarningMultiple, Integer> {
    @Autowired
    private IPlayerWarningMultipleService playerWarningMultipleService;
    @Autowired
    private IVBetDetailService vbetDetailService;
    @Autowired
    private ISysAuditLogService sysAuditLogService;


    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/warning/multiple/";
        //endregion your codes 2
    }


    @RequestMapping("/multiplelist")
    public String multiplelist(PlayerWarningMultipleListVo listVo, PlayerWarningMultipleSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
       String dateTime = request.getParameter("dateTime");
        Date startTime = null;
        Date endTime = null;
        if(StringUtils.isNotEmpty(dateTime)){
            startTime = DateTimeTool.getStartDate(DateTimeTool.getDateByYMD(dateTime));
            endTime = DateTimeTool.getEndDate(DateTimeTool.getDateByYMD(dateTime));
            model.addAttribute("datetime",dateTime);
        }else{
             startTime = DateTimeTool.getStartDate(new Date());
             endTime = DateTimeTool.getEndDate(new Date());
            model.addAttribute("datetime", new Date());
        }
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(PlayerWarningMultiple.PROP_STATUS, Operator.EQ, listVo.getSearch().getStatus()),
                new Criterion(PlayerWarningMultiple.PROP_USERNAME, Operator.LIKE, listVo.getSearch().getUsername()),
                new Criterion(PlayerWarningMultiple.PROP_NICKNAME, Operator.LIKE, listVo.getSearch().getNickname()),
                new Criterion(PlayerWarningMultiple.PROP_MULTIPLE, Operator.GE, listVo.getSearch().getMultiple()),
                new Criterion(PlayerWarningMultiple.PROP_DATE, Operator.GE, startTime),
                new Criterion(PlayerWarningMultiple.PROP_DATE, Operator.LE, endTime)
        });
        listVo.getQuery().addOrder(PlayerWarningMultiple.PROP_CREATE_TIME, Direction.DESC);
        listVo = playerWarningMultipleService.search(listVo);
        model.addAttribute("command", listVo);

        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/multiple/Index" + "Partial" : "/warning/multiple/Index";
    }

    /**
     * 跳转到详情页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/warninfo")
    public String warninfo(HttpServletRequest request,Model model, HttpServletResponse response) {
        String id = request.getParameter("id");
        PlayerWarningMultiple playerWarningMultiple = playerWarningMultipleService.getPlayerWarningMultiple(Integer.parseInt(id));
        String sysUserId = request.getParameter("playerId");
        request.setAttribute("playerId",Integer.parseInt(sysUserId));
        request.setAttribute("playerWarningMultiple",playerWarningMultiple);
        request.setAttribute("date",DateTimeTool.getYmdhms(playerWarningMultiple.getCreateTime()));
        VSysUserVo vSysUserVo = new VSysUserVo();
        vSysUserVo.getSearch().setId(playerWarningMultiple.getPlayerId());
        vSysUserVo = ServiceTool.vSysUserService().get(vSysUserVo);
        model.addAttribute("commandv", vSysUserVo);
        Map<String,Object> map =  playerWarningMultipleService.getUserPlayer(playerWarningMultiple.getPlayerId());
        model.addAttribute("mapUserPlayer", map);
        return "/warning/multiple/Warnview";
    }

    /**
     * 登录详情
     */
    @RequestMapping("/loginInfo")
    public String loginInfo(SysAuditLogListVo listVo,  BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String sysUserId = request.getParameter("sysUserId");
        String date = request.getParameter("date");
//        Date startTime = DateTimeTool.getStartDate(DateTimeTool.getYmd( DateTimeTool.getDateByFull(date)));
//        Date endTime = DateTimeTool.getEndDate(DateTimeTool.getYmd( DateTimeTool.getDateByFull(date)));
        Date endTime = DateTimeTool.getDateByFull(date);
        Date startTime = DateTimeTool.getStartDate(DateTimeTool.getYmd(endTime));
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysAuditLog.PROP_OPERATOR_ID, Operator.EQ, Integer.parseInt(sysUserId)),
                new Criterion(SysAuditLog.PROP_OPERATE_TIME, Operator.GE, startTime),
                new Criterion(SysAuditLog.PROP_OPERATE_TIME, Operator.LE, endTime)
        });
        listVo = sysAuditLogService.search(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("sysUserId",sysUserId);
        model.addAttribute("date", date);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/info/logindetail/Index" + "Partial" : "/warning/info/logindetail/Index";
    }
}