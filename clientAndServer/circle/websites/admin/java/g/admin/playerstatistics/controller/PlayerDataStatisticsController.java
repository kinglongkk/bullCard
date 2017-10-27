package g.admin.playerstatistics.controller;

import g.admin.playerstatistics.form.PlayerDataStatisticsForm;
import g.admin.playerstatistics.form.PlayerDataStatisticsSearchForm;
import g.common.tool.DateTimeTool;
import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.vo.PlayerDataStatisticsListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsVo;
import g.service.admin.IVAgentManageService;
import g.service.playerstatistics.IPlayerDataStatisticsService;
import g.web.admin.session.SessionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.export.ExportTool;
import org.soul.commons.net.ServletTool;
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
import java.util.*;


/**
 * 玩家数据统计表控制器
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
@Controller
@RequestMapping("/playerDataStatistics")
public class PlayerDataStatisticsController extends BaseCrudController<IPlayerDataStatisticsService, PlayerDataStatisticsListVo, PlayerDataStatisticsVo, PlayerDataStatisticsSearchForm, PlayerDataStatisticsForm, PlayerDataStatistics, Integer> {

    @Autowired
    private IVAgentManageService vagentManageService;

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
        List<VAgentManage> listAgent = vagentManageService.allSearch(new VAgentManageListVo());
        model.addAttribute("listAgent",listAgent);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/playerdata/Index" + "Partial" :  "/playerstatistics/playerdata/Index";
    }

    /**
     *盈亏
     */
    @RequestMapping("/echartIndexProfitAjax")
    @ResponseBody
    public String echartIndexProfitAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerDataStatisticsReport> listPlay = getService().searchAllPlayerList(startTime,endTime);
//        if(listPlay.size() == 0)
//            return JsonTool.toJson("");
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
        if(listDouble.size()>0)
            return JsonTool.toJson(listDouble.toArray());
        return JsonTool.toJson("");
    }

    /**
     * 盈亏
     */
    @RequestMapping("/echartIndexProfitAgentAjax")
    @ResponseBody
    public String echartIndexProfitAgentAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String username = request.getParameter("username");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerDataStatisticsReport> listPlay = getService().searchPlayerList(startTime,endTime,username);
//        if(listPlay.size() == 0)
//            return JsonTool.toJson("");
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
        if(listDouble.size()>0)
            return JsonTool.toJson(listDouble);
        return JsonTool.toJson("");
    }

    /**
     *多个代理商详细汇总
     * @param request
     * @return
     */
    @RequestMapping("/indexMultipleDayLinesAjax")
    @ResponseBody
    public String indexMultipleDayLinesAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String[] username = request.getParameter("username").split(",");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerDataStatisticsReport> listPlay = getService().searchMultiplePlayerList(startTime,endTime,username);
//        if(listPlay.size() == 0)
//            return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for(String u : username) {
            Map<String ,Object> map = new HashMap<String,Object>();
            map.put("name", u);
            map.put("type","line");
            List<Double> listDouble = new ArrayList<Double>();
            for (String d : date) {
                double num = 0;
                for (PlayerDataStatisticsReport s : listPlay) {
                    int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));

                    if (day == 0 && u.equals(s.getAgentUsername())) {

                        num = num+s.getWinAmount();
                    }
                }
               listDouble.add( num);
            }
            map.put("data",listDouble.toArray());
            mapList.add(map);
        }
        return JsonTool.toJson(mapList);
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

        if (listVo.getSearch().getStartTime() != null && listVo.getSearch().getEndTime() != null) {
            listVo = getService().addOneDay(listVo);
            listVo.getSearch().setSelectType("like");
            listVo.getSearch().setUserType(SessionManager.getUserType().getCode());
            listVo = getService().selectAllAgentList(listVo);
        }
        model.addAttribute("command", listVo);
        model.addAttribute("gameList", getService().selectAllGame());
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

        listVo = getService().setSearchCondition(listVo);
        //设置默认搜索结束时间
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

        if (listVo.getSearch().getStartTime() != null && listVo.getSearch().getEndTime() != null) {
            listVo = getService().addOneDay(listVo);
            listVo.getSearch().setSelectType("like");
            listVo.getSearch().setUserType(SessionManager.getUserType().getCode());
            listVo = getService().selectAllGameList(listVo);
        }
        model.addAttribute("command", listVo);
        model.addAttribute("gameList", getService().selectAllGame());
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

        listVo = getService().setSearchCondition(listVo);
        listVo.getSearch().setEndTime(listVo.getSearch().getStartTime());
        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("equals");
        listVo.getSearch().setIsDetail("true");
        listVo = getService().selectTotalAmount(getService().search(listVo));
        return listVo;
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

        listVo = getService().addOneDay(listVo);
        listVo.getSearch().setSelectType("like");
        listVo.getSearch().setUserType(SessionManager.getUserType().getCode());
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