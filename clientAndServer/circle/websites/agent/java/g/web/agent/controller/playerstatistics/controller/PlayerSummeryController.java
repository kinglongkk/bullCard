package g.web.agent.controller.playerstatistics.controller;

import g.common.tool.DateTimeTool;
import g.model.UserTypeEnum;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.vo.PlayerSummeryListVo;
import g.model.playerstatistics.vo.PlayerSummeryVo;
import g.service.admin.agent.quota.transaction.IAgentQuotaTransactionService;
import g.service.playerstatistics.IPlayerSummeryService;
import g.web.agent.controller.playerstatistics.form.PlayerSummeryForm;
import g.web.agent.controller.playerstatistics.form.PlayerSummerySearchForm;
import g.web.agent.session.SessionManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.export.ExportTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.sort.Direction;
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

/**
 * 玩家数据统计表控制器
 *
 * @author lenovo
 * @time 2017-1-5 17:36:24
 */
@Controller
@RequestMapping("/playerSummery")
public class PlayerSummeryController extends BaseCrudController<IPlayerSummeryService, PlayerSummeryListVo, PlayerSummeryVo, PlayerSummerySearchForm, PlayerSummeryForm, PlayerSummery, Integer> {

    private Log log = LogFactory.getLog(PlayerSummeryController.class);

    @Autowired
    private IPlayerSummeryService playerSummeryService;

    @Autowired
    private IAgentQuotaTransactionService agentQuotaTransactionService;

    @Autowired
    private ISysUserService sysUserService;

    private final static String INDEX = "playersum/Index";

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
            dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getYMDstr(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexLines" + "Partial" :  "/playerstatistics/summery/IndexLines";
    }

    @RequestMapping("/indexLinesAjax")
    @ResponseBody
    public String indexLinesAjax(HttpServletRequest request){
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<AgentQuotaTransaction> listQuota = agentQuotaTransactionService.agentOneQuotaTransactionList(startTime,endTime,sysUser.getId());
        if(listQuota.size() == 0)
            return JsonTool.toJson("");
        String date[] = dataStr.split(",");
        List<Double> listD = new ArrayList<Double>();
        for(String d : date){
           double num = 0;
            for(AgentQuotaTransaction s : listQuota) {
                int day = DateTimeTool.daysBetween(DateTimeTool.getStartDate(s.getDate()) , DateTimeTool.getStartDate(DateTimeTool.strToDate(d, "yyyy-MM-dd")));
                if (day==0) {
                    num = num + s.getQuota();
                }
            }
            listD.add(num);
        }
        if(listD.size() == 0)
            return JsonTool.toJson("");
        return JsonTool.toJson(listD);
    }

    /**
     * 总的玩家数
     * @return
     */
    @RequestMapping("/indexTotal")
    public String indexTotal(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr ="";
        if(StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)){
            dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getYMDstr(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexTotal" + "Partial" :  "/playerstatistics/summery/IndexTotal";
    }

    @RequestMapping("/indexTotalAjax")
    @ResponseBody
    public String indexTotalAjax( HttpServletRequest request){
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListSummertSearch(startTime,endTime,sysUser.getUsername());
        if(listSummery.size()==0)
            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        num = s.getPlayerSummery();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size()==0)
            return JsonTool.toJson("");
       return JsonTool.toJson(intList);
    }

    /**
     * 每月玩家活跃折现图
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
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexActive" + "Partial" :  "/playerstatistics/summery/IndexActive";
    }

    /**
     * 每月玩家活跃折线图计算
     * @return
     */
    @RequestMapping("/indexActiveAjax")
    @ResponseBody
    public String indexActiveAjax(HttpServletRequest request){
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListSummertSearch(startTime,endTime,sysUser.getUsername());
        if(listSummery.size() == 0)
            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        num = s.getActivePlayerQty();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size() == 0)
            return JsonTool.toJson("");
        return JsonTool.toJson(intList);
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
            dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
            model.addAttribute("year",year);
            model.addAttribute("month",month);
        }else{
            dataStr = DateTimeTool.getYMDstr(DateTimeTool.getYear(),DateTimeTool.getMonth());
            model.addAttribute("year",DateTimeTool.getYear());
            model.addAttribute("month",DateTimeTool.getMonth());
        }
        model.addAttribute("dataStr", dataStr);
        return ServletTool.isAjaxSoulRequest(request) ?  "/playerstatistics/summery/IndexAdd" + "Partial" :  "/playerstatistics/summery/IndexAdd";
    }

    /**
     * 每日玩家新增计算
     * @return
     */
    @RequestMapping("/indexAddAjax")
    @ResponseBody
    public String indexAddAjax(HttpServletRequest request){
        //获取当前登录用户信息
        SysUser sysUser = SessionManager.getUser();
        String year = request.getParameter("year");
        String month = request.getParameter("month") ;
        String dataStr = DateTimeTool.getYMDstr(Integer.parseInt(year),Integer.parseInt(month));
        //获取时间段内的玩家统计数据
        String startTime = DateTimeTool.getFirstDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        String endTime =  DateTimeTool.getLastDayOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        List<PlayerSummery> listSummery =playerSummeryService.agentListSummertSearch(startTime,endTime,sysUser.getUsername());
        if(listSummery.size()==0)
            return JsonTool.toJson("");
        List<Integer> intList = new ArrayList<Integer>();
        String date[] = dataStr.split(",");
        for(String d : date){
            int num = 0;
            if(listSummery.size() > 0){
                for(PlayerSummery s : listSummery){
                    if(DateUtils.isSameDay(s.getDate(),DateTimeTool.strToDate(d,"yyyy-MM-dd"))){
                        num =  s.getNewPlayerQty();
                    }
                }
            }
            intList.add(num);
        }
        if(intList.size()==0)
            return JsonTool.toJson("");
        return JsonTool.toJson(intList);
    }

    /**
     * 我的玩家统计
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

        listVo = isAgentSub(listVo);
        model.addAttribute("defaultString", listVo.getDefaultString());
        model.addAttribute("defaultTime", listVo.getDefaultTime());
        listVo.getQuery().addOrder(PlayerSummery.PROP_DATE, Direction.DESC);
        listVo = getService().search(listVo);
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ?  INDEX + "Partial" : INDEX;
    }

    /**
     * 我的玩家统计 导出数据
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getOwnerAgentExportData")
    public void getOwnerAgentExportData(PlayerSummeryListVo listVo, @FormModel("search") @Valid PlayerSummerySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        exportData(getService().getOwnerAgentExportData(listVo), "ownerPlayer", response);
    }

    /**
     * 判断登陆者的身份，若是代理子账号，则查找上一级数据
     * @param listVo
     * @return VBetDetailListVo
     */
    public PlayerSummeryListVo isAgentSub(PlayerSummeryListVo listVo) {

        String userType = SessionManager.getUserType().getCode();
        Integer userId = SessionManager.getUserId();
        if (userType.equals(UserTypeEnum.AGENT_SUB.getCode())) {
            SysUserVo sysUserVo = searchSysUserVo(userId);
            Integer ownerId = sysUserVo.getResult().getOwnerId();
            sysUserVo = searchSysUserVo(ownerId);
            try {
                listVo.getSearch().setUsername(sysUserVo.getResult().getUsername());
            } catch (Exception e) {
                log.error("代理子账号有误" + SessionManager.getUserName());
            }
        } else {
            listVo.getSearch().setUsername(SessionManager.getUserName());
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