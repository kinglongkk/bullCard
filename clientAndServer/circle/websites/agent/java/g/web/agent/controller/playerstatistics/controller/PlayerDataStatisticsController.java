package g.web.agent.controller.playerstatistics.controller;

import g.common.tool.DateTimeTool;
import g.model.UserTypeEnum;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.vo.PlayerDataStatisticsListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsVo;
import g.service.playerstatistics.IPlayerDataStatisticsService;
import g.web.agent.controller.playerstatistics.form.PlayerDataStatisticsForm;
import g.web.agent.controller.playerstatistics.form.PlayerDataStatisticsSearchForm;
import g.web.agent.session.SessionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.export.ExportTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表控制器
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
@Controller
//region your codes 1
@RequestMapping("/playerDataStatistics")
public class PlayerDataStatisticsController extends BaseCrudController<IPlayerDataStatisticsService, PlayerDataStatisticsListVo, PlayerDataStatisticsVo, PlayerDataStatisticsSearchForm, PlayerDataStatisticsForm, PlayerDataStatistics, Integer> {
//endregion your codes 1

    private Log log = LogFactory.getLog(PlayerDataStatisticsController.class);

    @Autowired
    private ISysUserService sysUserService;

    public static final String ALL_AGENT_INDEX = "groupByAgent/allAgent/GroupIndex";
    public static final String OWNER_PLAYER_INDEX = "groupByAgent/ownerPlayer/GroupIndex";
    public static final String PLAYER_DETAIL_INDEX = "groupByAgent/playerDetail/Index";

    public static final String ALL_GAME_INDEX = "groupByGame/allGame/GroupIndex";
    public static final String GAME_DETAIL_INDEX = "groupByGame/gameDetail/Index";

    @Override
    protected String getViewBasePath() {
        return "/playerstatistics/";
    }

    @Override
    public String list(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = "";
        if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
            dataStr = DateTimeTool.getDayArray(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getDayArray(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/playerdata/Index" + "Partial" :  "/playerstatistics/playerdata/Index";
    }

    @RequestMapping("/indexDayLinesAjax")
    @ResponseBody
    public String indexDayLinesAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerDataStatisticsReport> listPlay = getService().searchPlayerList(startTime,endTime,sysUser.getUsername());
        if(listPlay.size() == 0)
            return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Double> listDouble = new ArrayList<Double>();
        for(String d : date){
            double num = 0;
            for(PlayerDataStatisticsReport s : listPlay) {
                int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                if (day == 0) {
                    num = num + s.getWinAmount();
                }
            }
            listDouble.add(num);
        }
        if(listDouble.size() == 0)
            return JsonTool.toJson("");
        return JsonTool.toJson(listDouble);
    }

    /**
     * 按代理统计
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/allAgentList")
    public String allAgentList(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = defaultCondition(listVo, model);
        if (listVo.getSearch().getStartTime() != null && listVo.getSearch().getEndTime() != null) {
            listVo = getService().addOneDay(listVo);
            listVo.getSearch().setSelectType("like");
            listVo = getService().selectAllAgentList(listVo);
        }
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + ALL_AGENT_INDEX + "Partial" : getViewBasePath() + ALL_AGENT_INDEX;
    }

    /**
     * 按玩家统计
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ownerPlayerList")
    public String ownerPlayerList(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        //设置默认默认搜索结束时间
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        model.addAttribute("command", getService().selectOwnerPlayerList(listVo));
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + OWNER_PLAYER_INDEX+ "Partial" : getViewBasePath() + OWNER_PLAYER_INDEX;
    }

    /**
     * 玩家详细
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/playerDetailList")
    public String playerDetailList(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("command", searchList(listVo));
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + PLAYER_DETAIL_INDEX+ "Partial" : getViewBasePath() + PLAYER_DETAIL_INDEX;
    }

    /**
     * 按游戏统计
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/allGameList")
    public String allGameList(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = defaultCondition(listVo, model);
        if (listVo.getSearch().getStartTime() != null && listVo.getSearch().getEndTime() != null) {
            listVo = getService().addOneDay(listVo);
            listVo.getSearch().setSelectType("like");
            listVo = getService().selectAllGameList(listVo);
        }
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + ALL_GAME_INDEX + "Partial" : getViewBasePath() + ALL_GAME_INDEX;
    }

    /**
     * 游戏详细
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/gameDetailList")
    public String gameDetailList(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("command", searchList(listVo));
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + GAME_DETAIL_INDEX + "Partial" : getViewBasePath() + GAME_DETAIL_INDEX;
    }

    /**
     * 查询所属游戏的玩法
     * @param gameId 游戏id
     * @return map
     */
    @ResponseBody
    @RequestMapping("/selectOwnerGameModel")
    public Map selectOwnerGameModel(Integer gameId) {

        return getService().selectOwnerGameModel(gameId);
    }

    /**
     * 详情查询
     * @param listVo
     * @return listVo
     */
    public PlayerDataStatisticsListVo searchList(PlayerDataStatisticsListVo listVo) {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        listVo = getService().selectTotalAmount(getService().search(listVo));
        return listVo;
    }

    /**
     * 设置默认条件
     * @param listVo
     * @param model
     * @return PlayerDataStatisticsListVo
     */
    public PlayerDataStatisticsListVo defaultCondition(PlayerDataStatisticsListVo listVo, Model model) {

        listVo = isAgentSub(listVo);
        Integer ownerId = listVo.getSearch().getOwnerId();
        if (ownerId != null) {
            listVo.getSearch().setUserType(UserTypeEnum.AGENT.getCode());
            model.addAttribute("gameList", getService().selectAgentGame(listVo.getSearch().getOwnerId()));
        } else {
            listVo.getSearch().setUserType(SessionManager.getUserType().getCode());
            model.addAttribute("gameList", getService().selectAgentGame(SessionManager.getUserId()));
        }
        return listVo;
    }


    /**
     * 判断登陆者的身份，若是代理子账号，则查找上一级数据
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    public PlayerDataStatisticsListVo isAgentSub(PlayerDataStatisticsListVo listVo) {

        String userType = SessionManager.getUserType().getCode();
        Integer userId = SessionManager.getUserId();
        if (userType.equals(UserTypeEnum.AGENT_SUB.getCode())) {
            SysUserVo sysUserVo = searchSysUserVo(userId);
            Integer ownerId = sysUserVo.getResult().getOwnerId();
            sysUserVo = searchSysUserVo(ownerId);
            try {
                listVo.getSearch().setOwnerUsername(sysUserVo.getResult().getUsername());
                listVo.getSearch().setOwnerId(sysUserVo.getResult().getId());
            } catch (Exception e) {
                log.error("代理子账号有误" + SessionManager.getUserName());
            }
        } else {
            listVo.getSearch().setOwnerUsername(SessionManager.getUserName());
        }
        return listVo;
    }

    /**
     * 查找用户
     * @param userId
     * @return SysUserVo
     */
    public SysUserVo searchSysUserVo(Integer userId) {

        SysUserVo sysUserVo = new SysUserVo();
        sysUserVo.getSearch().setId(userId);
        return sysUserService.search(sysUserVo);
    }

    /**
     * 按所有代理统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getAllAgentExportData")
    public void getAllAgentExportData(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = defaultCondition(listVo, model);
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("like");
        listVo.getSearch().setUserType(SessionManager.getUserType().getCode());
        exportData(getService().selectAllAgentExportData(listVo), "allAgentList", response);
    }

    /**
     * 按代理所属玩家统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getOwnerPlayerExportData")
    public void getOwnerPlayerExportData(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        //设置默认默认搜索结束时间
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        exportData(getService().selectOwnerPlayerExportData(listVo), "ownerPlayerList", response);
    }

    /**
     * 玩家详细统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getPlayerDetailExportData")
    public void getPlayerDetailExportData(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        exportData(getService().selectPlayerOrGameDetailExportData(listVo), "playerDetailList", response);
    }

    /**
     * 按所有游戏统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getAllGameExportData")
    public void getAllGameExportData(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = defaultCondition(listVo, model);
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("like");
        exportData(getService().selectAllGameExportData(listVo), "allGameList", response);
    }
    
    /**
     * 游戏详细统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getGameDetailExportData")
    public void getGameDetailExportData(PlayerDataStatisticsListVo listVo, @FormModel("search") @Valid PlayerDataStatisticsSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        exportData(getService().selectPlayerOrGameDetailExportData(listVo), "gameDetailList", response);
    }

    /**
     * 导出数据
     * @param exportData 需要导出的数据
     * @param exportKey export-bean对应的key
     * @param response
     * @throws IOException
     */
    public void exportData(List<PlayerDataStatistics> exportData, String exportKey, HttpServletResponse response) throws IOException {

        String className = "g.model.playerstatistics.po.PlayerDataStatistics";
        File file = ExportTool.export(exportData, SessionManager.getLocale(), SessionManager.getTimeZone(), "xls",
                className, exportKey);
        byte[] data = FileUtils.readFileToByteArray(file);
        //下载文件时显示的文件保存名称
        String fileName = file.getName();
        file.delete();
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        //以流的形式下载文件，这样可以实现任意格式的文件下载
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

}