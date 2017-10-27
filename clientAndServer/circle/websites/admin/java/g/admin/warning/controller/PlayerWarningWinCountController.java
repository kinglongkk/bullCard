package g.admin.warning.controller;

import g.admin.warning.form.PlayerWarningWinCountForm;
import g.admin.warning.form.PlayerWarningWinCountSearchForm;
import g.common.tool.DateTimeTool;
import g.model.player.vo.VSysUserVo;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.po.PlayerWarningWinCount;
import g.model.warning.vo.PlayerWarningWinCountListVo;
import g.model.warning.vo.PlayerWarningWinCountVo;
import g.service.warning.IPlayerWarningMultipleService;
import g.service.warning.IPlayerWarningWinCountService;
import g.web.admin.tools.ServiceTool;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
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
 * 说明预警异常用户赢得连续派彩次数控制器
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
@Controller
@RequestMapping("/playerWarningWinCount")
public class PlayerWarningWinCountController extends BaseCrudController<IPlayerWarningWinCountService, PlayerWarningWinCountListVo, PlayerWarningWinCountVo, PlayerWarningWinCountSearchForm, PlayerWarningWinCountForm, PlayerWarningWinCount, Integer> {
    @Autowired
    private IPlayerWarningWinCountService playerWarningWinCountService;
    @Autowired
    private IPlayerWarningMultipleService playerWarningMultipleService;
    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/warning/wincount/";
        //endregion your codes 2
    }

    @RequestMapping("/winlist")
    public String winlist(PlayerWarningWinCountListVo listVo, PlayerWarningWinCountSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
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
                new Criterion(PlayerWarningWinCount.PROP_IS_DELETE, Operator.EQ, false),
                new Criterion(PlayerWarningWinCount.PROP_STATUS, Operator.EQ, listVo.getSearch().getStatus()),
                new Criterion(PlayerWarningWinCount.PROP_USERNAME, Operator.LIKE, listVo.getSearch().getUsername()),
                new Criterion(PlayerWarningWinCount.PROP_NICKNAME, Operator.LIKE, listVo.getSearch().getNickname()),
                new Criterion(PlayerWarningWinCount.PROP_NUM, Operator.GE, listVo.getSearch().getNum()),
                new Criterion(PlayerWarningWinCount.PROP_DATE, Operator.GE, startTime),
                new Criterion(PlayerWarningWinCount.PROP_DATE, Operator.LE, endTime)
        });
        listVo.getQuery().addOrder(PlayerWarningWinCount.PROP_START_TIME, Direction.DESC);
        listVo = playerWarningWinCountService.search(listVo);
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request)  ? "/warning/wincount/Index" + "Partial" : "/warning/wincount/Index";
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
        PlayerWarningWinCount playerWarningWinCount = playerWarningWinCountService.getPlayerWarningWinCount(Integer.parseInt(id));
        String sysUserId = request.getParameter("playerId");
        request.setAttribute("playerId",Integer.parseInt(sysUserId));
        request.setAttribute("playerWarningWinCount",playerWarningWinCount);
        String matchIds = playerWarningWinCount.getMatchId().toString().split("=")[1];
        matchIds = matchIds.substring(matchIds.indexOf("{")+1,matchIds.lastIndexOf("}"));
        request.setAttribute("matchIds",matchIds);
        request.setAttribute("date",DateTimeTool.getYmdhms(playerWarningWinCount.getCreateTime()));
        VSysUserVo vSysUserVo = new VSysUserVo();
        vSysUserVo.getSearch().setId(playerWarningWinCount.getPlayerId());
        vSysUserVo = ServiceTool.vSysUserService().get(vSysUserVo);
        model.addAttribute("commandv", vSysUserVo);
        Map<String,Object> map =  playerWarningMultipleService.getUserPlayer(playerWarningWinCount.getPlayerId());
        model.addAttribute("mapUserPlayer", map);
        return "/warning/wincount/Warnview";
    }

}