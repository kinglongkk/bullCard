package g.admin.playerstatistics.controller;

import g.common.tool.DateTimeTool;
import g.model.playerstatistics.po.JackpotMatch;
import g.model.playerstatistics.po.JackpotPlayer;
import g.model.playerstatistics.vo.JackpotMatchListVo;
import g.model.playerstatistics.vo.JackpotPlayerListVo;
import g.model.warning.po.MatchPlayer;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import g.service.playerstatistics.IPlayerDataStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/3/23.
 * 奖池报表
 */
@Controller
@RequestMapping("/jackpotReport")
public class JackpotReportController {

    @Autowired
    private IPlayerDataStatisticsService playerDataStatisticsService;

    /**
     * 奖池报表赛事
     * @param listVo
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/jackpot")
    public String list(JackpotMatchListVo listVo, Model model, HttpServletRequest request, HttpServletResponse response) {
        String sysuserid = request.getParameter("sysuserid");
        String gameid = request.getParameter("gameid");
        String gamemodelid = request.getParameter("gamemodelid");
        String startTimeStr = request.getParameter("startTimeStr");
        String endTimeStr = request.getParameter("endTimeStr");
        Date startTime = null;
        Date endTime = null;
        if(StringUtils.isNotEmpty(startTimeStr) && StringUtils.isNotEmpty(endTimeStr)){
            startTime = DateTimeTool.getStartDate(DateTimeTool.getDateByYMD(startTimeStr));
            endTime = DateTimeTool.getEndDate(DateTimeTool.getDateByYMD(endTimeStr));
        }else{
            startTime = DateTimeTool.getStartDate(new Date());
            endTime = DateTimeTool.getEndDate(new Date());
        }
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(JackpotMatch.PROP_GAMEROOMNAME, Operator.EQ, listVo.getSearch().getGameroomname()),
                new Criterion(JackpotMatch.PROP_ID, Operator.EQ, listVo.getSearch().getId()),
                new Criterion(JackpotMatch.PROP_BEGIN_TIME, Operator.GE, startTime),
                new Criterion(JackpotMatch.PROP_BEGIN_TIME, Operator.LE, endTime),
        });
        listVo = playerDataStatisticsService.getJackpotMatch(listVo,startTime,endTime,sysuserid,gameid,gamemodelid);
        model.addAttribute("command", listVo);
        model.addAttribute("sysuserid",sysuserid);
        model.addAttribute("gameid",gameid);
        model.addAttribute("gamemodelid",gamemodelid);
        model.addAttribute("startTimeStr",startTimeStr);
        model.addAttribute("endTimeStr",endTimeStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/jackpot/match/Index" + "Partial" :  "/playerstatistics/jackpot/match/Index";
    }

    @RequestMapping("/jackpotPlayer")
    public String jackpotPlayer(JackpotPlayerListVo listVo, Model model, HttpServletRequest request, HttpServletResponse response) {
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");
        Date startTime = null;
        Date endTime = null;
        if(StringUtils.isNotEmpty(startTimeStr) && StringUtils.isNotEmpty(endTimeStr)){
            startTime = DateTimeTool.getStartDate(DateTimeTool.getDateByYMD(startTimeStr));
            endTime = DateTimeTool.getEndDate(DateTimeTool.getDateByYMD(endTimeStr));
        }else{
            startTime = DateTimeTool.getStartDate(new Date());
            endTime = DateTimeTool.getEndDate(new Date());
        }
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(JackpotPlayer.PROP_DATE_TIME, Operator.GE, startTime),
                new Criterion(JackpotPlayer.PROP_DATE_TIME, Operator.LE, endTime),
                new Criterion(JackpotPlayer.PROP_AGENT_NAME, Operator.LIKE, listVo.getSearch().getAgentname()),
                new Criterion(JackpotPlayer.PROP_PLAYER_NAME, Operator.LIKE, listVo.getSearch().getPlayername()),
                new Criterion(JackpotPlayer.PROP_GAME_ID, Operator.EQ, listVo.getSearch().getGameid()),
                new Criterion(JackpotPlayer.PROP_GAME_MODEL_ID, Operator.EQ, listVo.getSearch().getGamemodelid())
        });
        listVo = playerDataStatisticsService.getJackpotPlayer(listVo,startTime,endTime);
        model.addAttribute("command", listVo);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        model.addAttribute("startTimeStr",DateTimeTool.getYmdhms(startTime));
        model.addAttribute("endTimeStr",DateTimeTool.getYmdhms(endTime));
        model.addAttribute("gameList", playerDataStatisticsService.selectAllGame());
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/jackpot/player/Index" + "Partial" :  "/playerstatistics/jackpot/player/Index";
    }

    /**
     * 奖池抽水金额明细
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/jackpotBet")
    public String jackpotBet(VWarningPlayerDetailListVo listVo,Model model, HttpServletRequest request, HttpServletResponse response) {
        String matchId = request.getParameter("matchId");
        listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(VWarningPlayerDetail.PROP_MATCH_ID, Operator.EQ, Long.valueOf(matchId)),
        });
        listVo = playerDataStatisticsService.jackpotBet(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("matchId",matchId);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/jackpot/bet/Index" + "Partial" :  "/playerstatistics/jackpot/bet/Index";
    }
}

