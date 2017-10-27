package g.admin.playerstatistics.controller;

import g.admin.playerstatistics.form.PlayerSummeryForm;
import g.admin.playerstatistics.form.PlayerSummerySearchForm;
import g.common.tool.DateTimeTool;
import g.common.tool.DoubleTool;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.agent.po.VAgentManage;
import g.model.agent.vo.VAgentManageListVo;
import g.model.bet.so.BetSo;
import g.model.common.Const;
import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.so.PlayerSummerySo;
import g.model.playerstatistics.vo.PlayerSummeryListVo;
import g.model.playerstatistics.vo.PlayerSummeryVo;
import g.service.admin.IVAgentManageService;
import g.service.admin.agent.quota.transaction.IAgentQuotaTransactionService;
import g.service.bet.IBetService;
import g.service.playerstatistics.IPlayerSummeryService;
import g.web.admin.session.SessionManager;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.export.ExportTool;
import org.soul.commons.lang.DateTool;
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
 * @time 2017-1-5 17:36:24
 */
@Controller
@RequestMapping("/playerSummery")
public class PlayerSummeryController extends BaseCrudController<IPlayerSummeryService, PlayerSummeryListVo, PlayerSummeryVo, PlayerSummerySearchForm, PlayerSummeryForm, PlayerSummery, Integer> {

    @Autowired
    private IPlayerSummeryService playerSummeryService;

    @Autowired
    private IAgentQuotaTransactionService agentQuotaTransactionService;

    @Autowired
    private IVAgentManageService vagentManageService;

    @Autowired
    private IBetService betService;

    private final static String GROUP_INDEX = "agent/playersum/GroupIndex";
    private final static String INDEX = "agent/playersum/Index";

    @Override
    protected String getViewBasePath() {
        return "/playerstatistics/summery/";
    }

    /**
     * 桌面菜单
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/desktopMenu")
    public String desktopMenu( Model model,HttpServletRequest request){
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
        model.addAttribute("dateTime",DateTimeTool.getYmd(DateTimeTool.getDateBefore(new Date(),1)));
        model.addAttribute("dataStr", dataStr);
        List<VAgentManage> listAgent = vagentManageService.allSearch(new VAgentManageListVo());
        model.addAttribute("listAgent",listAgent);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/desktopMenu" + "Partial" :  "/playerstatistics/summery/desktopMenu";
    }

    /**
     * 代理商额度曲线
     * @return
     */
    @RequestMapping("/indexLines")
    public String indexLines(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
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
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexLines" + "Partial" :  "/playerstatistics/summery/IndexLines";
    }

    /**
     * 多个代理商额度对比
     * @param request
     * @return
     */
    @RequestMapping("/indexMultipleLinesAjax")
    @ResponseBody
    public String indexMultipleLinesAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String[] usernames = request.getParameter("username").split(",");
        List<Integer> ids = new ArrayList<Integer>();
        List<String> us = new ArrayList<String>();
        for(String u : usernames){
            ids.add(Integer.parseInt(u.split("@@@")[0]));
            us.add(u.split("@@@")[1]);
        }
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<AgentQuotaTransaction> listQuota = agentQuotaTransactionService.agentQuotaTransactionList(startTime,endTime,ids.toArray());
//        if(listQuota.size() == 0)
//            return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        int index = 0;
        for(Integer ag : ids){
            Map<String ,Object> map = new HashMap<String,Object>();
            map.put("name",us.get(index));
            index ++ ;
            map.put("type","bar");
            List<Double> listD = new ArrayList<Double>();
            for(String d : date){
                double num = 0;
                for(AgentQuotaTransaction s : listQuota) {
                    int a = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                    Integer cc = s.getAgentId();
                    if (a==0 &&  cc.equals(ag)) {
                        num = num + s.getQuota();
                    }
                }
                listD.add(num);

            }
            map.put("data",listD.toArray());
            mapList.add(map);
        }
        if(mapList.size()>0)
            return JsonTool.toJson(mapList);
        return JsonTool.toJson("");
    }

    /**
     * 额度
     */
    @RequestMapping("/echartIndexLinesAgentAjax")
    @ResponseBody
    public String echartIndexLinesAgentAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String agentId = request.getParameter("agentId");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<AgentQuotaTransaction> listQuota = agentQuotaTransactionService.agentQuotaTransactionList(startTime,endTime,new Integer[]{Integer.valueOf(agentId)});
//         if(listQuota.size() == 0)
//              return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Double> listD = new ArrayList<Double>();
        for(String d : date){
            double num = 0;
            for(AgentQuotaTransaction s : listQuota) {
                int a = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                if (a==0) {
                    num = num + s.getQuota();
                }
            }
            listD.add(num);

        }
        if(listD.size()>0)
            return JsonTool.toJson(listD);
        return JsonTool.toJson("");
    }

    /**
     * 额度
     */
    @RequestMapping("/echartIndexLinesAjax")
    @ResponseBody
    public String echartIndexLinesAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<AgentQuotaTransaction> listQuota = agentQuotaTransactionService.agentAllQuotaTransactionList(startTime,endTime);
//        if(listQuota.size() == 0)
//            return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Double> listD = new ArrayList<Double>();
//        if(listQuota.size()==0)
//            return JsonTool.toJson("");
        for(String d : date){
            double num = 0;
            for(AgentQuotaTransaction s : listQuota) {
                int a = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                if (a==0) {
                    num =num + s.getQuota();
                }
            }
            listD.add(num);
        }
        if(listD.size()>0)
            return JsonTool.toJson(listD);
        return JsonTool.toJson("");
    }

    /**
     * 玩家饼图
     * @return
     */
    @RequestMapping("/indexPie")
    public String indexPie(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String dateTime = request.getParameter("dateTime");
        String startTime = "";
        String endTime = "";
        if(StringUtils.isNotEmpty(dateTime)){
            model.addAttribute("dateTime", dateTime);
        }else{//时间为空的话就去当前时间
            model.addAttribute("dateTime",DateTimeTool.getDateBefore(new Date(),1));
//            model.addAttribute("dateTime", new Date());
        }
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexPie" + "Partial" :  "/playerstatistics/summery/IndexPie";
    }

    /**
     * 计算代理商玩家总数百分比
     * @param request
     * @return
     */
    @RequestMapping("/indexPieAjax")
    @ResponseBody
    public String indexPieAjax( HttpServletRequest request){
        String dateTime = request.getParameter("dateTime");
        String startTime = "";
        String endTime = "";
        if(StringUtils.isNotEmpty(dateTime)){
            startTime = DateTimeTool.getYmdhms(DateTimeTool.getStartDate(dateTime));
            endTime = DateTimeTool.getYmdhms(DateTimeTool.getEndDate(dateTime));
        }else{//时间为空的话就去当前时间
            startTime = DateTimeTool.getYmdhms(DateTimeTool.getStartDate(new Date()));
            endTime = DateTimeTool.getYmdhms(DateTimeTool.getEndDate(new Date()));
        }
        List<PlayerSummery>  list =  playerSummeryService.listSummertSearch(startTime,endTime);
//        if(list.size() == 0)
//            return JsonTool.toJson("");
        List<PlayerSummerySo> soList = new ArrayList<PlayerSummerySo>();
        int num = 1 ;
        int otherNum = 0;
        int total = 0;
        for(PlayerSummery p : list){
            total = total + p.getPlayerSummery();
            if(num < 11) {
                if(p.getPlayerSummery() == null || p.getPlayerSummery() == 0)
                    continue;
                PlayerSummerySo so = new PlayerSummerySo();
                so.setUsername(p.getUsername());
                so.setPercentage(p.getPlayerSummery() == null ? 0 : p.getPlayerSummery());
                soList.add(so);
            }else{
                otherNum = otherNum + p.getPlayerSummery();
            }
            num++;
        }
        if(otherNum > 0) {
            PlayerSummerySo so = new PlayerSummerySo();
            so.setUsername("其他");
            so.setPercentage(otherNum);
            soList.add(so);
        }
        if(total > 0) {
            for (PlayerSummerySo s : soList) {
                s.setUsername(s.getUsername());
                s.setPercentage(Double.valueOf(DoubleTool.accuracy(s.getPercentage(), total, 1)));
            }
        }
        if(soList.size() > 0)
            return JsonTool.toJson(soList);
        return JsonTool.toJson("");
    }

    /**
     * 总的玩家数
     * @return
     */
    @Override
    public String list(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr ="";
        if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
            dataStr = DateTimeTool.getDayArray(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getDayArray(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        List<VAgentManage> listAgent = vagentManageService.allSearch(new VAgentManageListVo());
        model.addAttribute("listAgent",listAgent);
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/Index" + "Partial" :  "/playerstatistics/summery/Index";
    }

    /**
     * 总玩家数
     */
    @RequestMapping("/echartIndexTotal")
    @ResponseBody
    public String echartIndexTotal( HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.listSummertSearch(startTime,endTime);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        num = num + s.getPlayerSummery();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size() > 0)
            return JsonTool.toJson(intList);
        return JsonTool.toJson("");
    }

    /**
     * 计算多个代理商的玩家数对比
     * @return
     */
    @RequestMapping("/listMultipleAjax")
    @ResponseBody
    public String listMultipleAjax( HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String[] username = request.getParameter("username").split(",");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListMultipleSummertSearch(startTime,endTime,username);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        String date[] = dataStr.split(",");
        for(String u : username) {
            Map<String ,Object> map = new HashMap<String,Object>();
            map.put("name", u);
            map.put("type","bar");
            List<Integer> intList = new ArrayList<Integer>();
            for (String d : date) {
                Integer num = 0;
                if (listSummery.size() > 0) {
                    for (PlayerSummery s : listSummery) {
                        int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()), DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                        if (day == 0 && u.equals(s.getUsername())) {
                            num =  s.getPlayerSummery();
                        }
                    }
                }
                intList.add(num);
            }
            map.put("data",intList.toArray());
            mapList.add(map);
        }
        return JsonTool.toJson(mapList);
    }

    /**
     * 每月玩家活跃玩家
     * @return
     */
    @RequestMapping("/indexActive")
    public String indexActive(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr ="";
        if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
            dataStr = DateTimeTool.getDayArray(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getDayArray(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        List<VAgentManage> listAgent = vagentManageService.allSearch(new VAgentManageListVo());
        model.addAttribute("listAgent",listAgent);
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexActive" + "Partial" :  "/playerstatistics/summery/IndexActive";
    }

    /**
     * 活跃玩家总数
     * @return
     */
    @RequestMapping("/echartIndexActiveAjax")
    @ResponseBody
    public String echartIndexActiveAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.listSummertSearch(startTime,endTime);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        num =num + s.getActivePlayerQty();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size()>0)
            return JsonTool.toJson(intList);
        return JsonTool.toJson("");
    }

    /**
     *多个代理商活跃玩家数
     * @param request
     * @return
     */
    @RequestMapping("/indexMultipleActiveAjax")
    @ResponseBody
    public String indexMultipleActiveAjax( HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String[] username = request.getParameter("username").split(",");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListMultipleSummertSearch(startTime,endTime,username);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        String date[] = dataStr.split(",");
        for(String u : username) {
            Map<String ,Object> map = new HashMap<String,Object>();
            map.put("name", u);
            map.put("type","bar");
            List<Integer> intList = new ArrayList<Integer>();
            for (String d : date) {
                int num = 0;
                if (listSummery.size() > 0) {
                    for (PlayerSummery s : listSummery) {
                        int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()), DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                        if (day == 0 && u.equals(s.getUsername())) {
                            num =  s.getActivePlayerQty();
                        }
                    }
                }
                intList.add(num);
            }
            map.put("data",intList.toArray());
            mapList.add(map);
        }
        return JsonTool.toJson(mapList);
    }

    /**
     * 每日玩家新增数
     * @return
     */
    @RequestMapping("/indexAdd")
    public String indexAdd(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr ="";
        if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
            dataStr = DateTimeTool.getDayArray(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getDayArray(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        List<VAgentManage> listAgent = vagentManageService.allSearch(new VAgentManageListVo());
        model.addAttribute("listAgent",listAgent);
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexAdd" + "Partial" :  "/playerstatistics/summery/IndexAdd";
    }

    /**
     * 玩家新增数
     * @return
     */
    @RequestMapping("/echartIndexAddAjax")
    @ResponseBody
    public String echartIndexAddAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.listSummertSearch(startTime,endTime);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                    if (day == 0) {
                        num = num + s.getNewPlayerQty();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size() > 0)
            return JsonTool.toJson(intList);
        return JsonTool.toJson("");
    }

    /**
     *多个代理商新增玩家对比
     * @param request
     * @return
     */
    @RequestMapping("/indexMultipleAddAjax")
    @ResponseBody
    public String indexMultipleAddAjax( HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String[] username = request.getParameter("username").split(",");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListMultipleSummertSearch(startTime,endTime,username);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        String date[] = dataStr.split(",");
        for(String u : username) {
            Map<String ,Object> map = new HashMap<String,Object>();
            map.put("name", u);
            map.put("type","bar");
            List<Integer> intList = new ArrayList<Integer>();
            for (String d : date) {
                int num = 0;
                if (listSummery.size() > 0) {
                    for (PlayerSummery s : listSummery) {
                        int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()), DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                        if (day == 0 && u.equals(s.getUsername())) {
                            num =  s.getNewPlayerQty();
                        }
                    }
                }
                intList.add(num);
            }
            map.put("data",intList.toArray());
            mapList.add(map);
        }
        return JsonTool.toJson(mapList);
    }

    /**
     * 单个代理商玩家新增数,总数，活跃数
     * @param request
     * @return
     */
    @RequestMapping("/indexOnePlayerAjax")
    @ResponseBody
    public String indexOnePlayerAjax(HttpServletRequest request){
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String username = request.getParameter("username");
        //all：总数，add：新增数,active：活跃数
        String type = request.getParameter("type");
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListSummertSearch(startTime,endTime,username);
//        if(listSummery.size() == 0)
//            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        if("all".equals(type))
                            num = s.getPlayerSummery();
                        if("add".equals(type))
                            num = s.getNewPlayerQty();
                        if("active".equals(type))
                            num = s.getActivePlayerQty();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size() > 0)
            return JsonTool.toJson(intList);
        return JsonTool.toJson("");
    }

    /**
     * 今日盈亏总数
     * @param request
     * @return
     */
    @RequestMapping("/getProfitAmount")
    @ResponseBody
    public Double getProfitAmount(HttpServletRequest request){
        String agentId = request.getParameter("agentId");
        BetSo so = new BetSo();
        if(StringUtils.isNotEmpty(agentId))
            so.setSysUserId(Integer.parseInt(agentId));
        so.setStartTime(DateTimeTool.getTodayStart());
        so.setEndTime(DateTimeTool.getTodayEnd());
        Double profit = betService.getProfitAmount(so);
        return profit;
    }

    /**
     * 获取今日活跃玩家数和新增玩家数
     * @param request
     * @return
     */
    @RequestMapping("/playerAddActive")
    @ResponseBody
    public String playerAddActive(HttpServletRequest request){
        String agentId = request.getParameter("agentId");
        Map<String,Object> map = new HashedMap();
        if(StringUtils.isNotEmpty(agentId)){
            map = playerSummeryService.getTotalAddActive(DateTimeTool.getTodayStart(),DateTimeTool.getTodayEnd(),Integer.parseInt(agentId));
        }else{
            map = playerSummeryService.getTotalAddActive(DateTimeTool.getTodayStart(),DateTimeTool.getTodayEnd(),null);
        }
        return JsonTool.toJson(map);
    }

    /**
     * 所有代理商玩家统计
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/allAgentList")
    public String allAgentList(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (listVo.getSearch().getStartTime() != null && listVo.getSearch().getEndTime() != null) {
            listVo = getService().selectPlayerGroupByDate(listVo);
        }
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ?  GROUP_INDEX + "Partial" :  GROUP_INDEX;
    }

    /**
     * 代理商玩家统计
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/ownerAgentList")
    public String ownerAgentList(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        Date startTime = listVo.getSearch().getStartTime();
        String defaultString = DateTool.formatDate(startTime, DateTool.FMT_HYPHEN_DAY);
        startTime = DateTool.parseDate(defaultString, DateTool.FMT_HYPHEN_DAY);
        listVo.setStartTime(startTime);
        listVo.getSearch().setStartTime(startTime);
        listVo.getSearch().setEndTime(startTime);
        listVo = getService().search(listVo);
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ?  INDEX + "Partial" : INDEX;
    }

    /**
     * 所有代理商玩家统计 导出数据
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getAllAgentExportData")
    public void getAllAgentExportData(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        exportData(getService().getAllAgentExportData(listVo), "allPlayerSum", response);
    }

    /**
     * 代理商玩家统计 导出数据
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getOwnerAgentExportData")
    public void getOwnerAgentExportData(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Date startTime = listVo.getSearch().getStartTime();
        String defaultString = DateTool.formatDate(startTime, Locale.CHINA, Const.Default_Site_TimeZone, DateTool.FMT_HYPHEN_DAY);
        startTime = DateTool.parseDate(defaultString, DateTool.FMT_HYPHEN_DAY);
        listVo.setStartTime(startTime);
        listVo.getSearch().setStartTime(startTime);
        listVo.getSearch().setEndTime(startTime);
        exportData(getService().getOwnerAgentExportData(listVo), "ownerPlayer", response);
    }

    /**
     * 导出数据
     * @param exportData 需要导出的数据
     * @param exportKey export-bean对应的key
     * @param response
     * @throws IOException
     */
    public void exportData(List<PlayerSummery> exportData, String exportKey, HttpServletResponse response) throws IOException {

        String className = "g.model.playerstatistics.po.PlayerSummery";
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