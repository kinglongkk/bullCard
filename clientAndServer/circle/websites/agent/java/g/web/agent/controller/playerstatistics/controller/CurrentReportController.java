package g.web.agent.controller.playerstatistics.controller;


import g.model.UserTypeEnum;
import g.model.bet.po.VBetDetail;
import g.model.bet.vo.VBetDetailListVo;
import g.model.bet.vo.VBetDetailVo;
import g.service.bet.IVBetDetailService;
import g.service.playerstatistics.IPlayerDataStatisticsService;
import g.web.agent.controller.playerstatistics.form.VBetDetailForm;
import g.web.agent.controller.playerstatistics.form.VBetDetailSearchForm;
import g.web.agent.session.SessionManager;
import org.apache.commons.io.FileUtils;

import org.soul.commons.export.ExportTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.iservice.security.privilege.ISysUserService;
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
import java.util.List;
import java.util.Map;

/**
 * 玩家当日游戏查询
 * Created by black on 2017/1/13.
 */
@Controller
//region your codes 1
@RequestMapping("/currentReport")
public class CurrentReportController extends BaseCrudController<IVBetDetailService, VBetDetailListVo, VBetDetailVo, VBetDetailSearchForm, VBetDetailForm, VBetDetail, Long> {
//endregion your codes 1

    private Log log = LogFactory.getLog(PlayerDataStatisticsController.class);

    @Autowired
    private IPlayerDataStatisticsService statisticsService;

    @Autowired
    private ISysUserService sysUserService;

    public static final String GROUP_INDEX = "GroupIndex";
    public static final String INDEX = "Index";

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/playerstatistics/currentReport/";
        //endregion your codes 2
    }

    //region your codes 3
    /**
     * 玩家今日游戏分组
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/groupList")
    public String groupList(VBetDetailListVo listVo, @FormModel("search") @Valid VBetDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = isAgentSub(listVo);
        model.addAttribute("command", getService().selectCurrentReport(listVo));
        model.addAttribute("gameList", statisticsService.selectAgentGame(listVo.getSearch().getOwnerId()));
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + GROUP_INDEX + "Partial" : getViewBasePath() + GROUP_INDEX;
    }

    /**
     * 玩家今日游戏详情
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/detail")
    public String detail(VBetDetailListVo listVo, @FormModel("search") @Valid VBetDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        model.addAttribute("command", getService().selectCurrentReportDetail(listVo));
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + INDEX + "Partial" : getViewBasePath() + INDEX;
    }

    /**
     * 查询所属游戏的玩法
     * @param gameId 游戏id
     * @return map
     */
    @ResponseBody
    @RequestMapping("/selectOwnerGameModel")
    public Map selectOwnerGameModel(Integer gameId) {

        return statisticsService.selectOwnerGameModel(gameId);
    }

    /**
     * 判断登陆者的身份，若是代理子账号，则查找上一级数据
     * @param listVo
     * @return VBetDetailListVo
     */
    public VBetDetailListVo isAgentSub(VBetDetailListVo listVo) {

        String userType = SessionManager.getUserType().getCode();
        Integer userId = SessionManager.getUserId();
        if (userType.equals(UserTypeEnum.AGENT_SUB.getCode())) {
            SysUserVo sysUserVo = searchSysUserVo(userId);
            Integer ownerId = sysUserVo.getResult().getOwnerId();
            sysUserVo = searchSysUserVo(ownerId);
            try {
                listVo.getSearch().setOwnerId(sysUserVo.getResult().getId());
            } catch (Exception e) {
                log.error("代理子账号有误" + SessionManager.getUserName());
            }
        } else {
            listVo.getSearch().setOwnerId(SessionManager.getUserId());
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
     * 今日统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getGroupExportData")
    public void getGroupExportData(VBetDetailListVo listVo, @FormModel("search") @Valid VBetDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        exportData(getService().selectCurrentReportExportData(listVo), "todayGroupList", response);
    }

    /**
     * 今日详细统计报表
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     */
    @RequestMapping("/getDetailExportData")
    public void getDetailExportData(VBetDetailListVo listVo, @FormModel("search") @Valid VBetDetailSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

        listVo = isAgentSub(listVo);
        listVo = getService().setSearchCondition(listVo);
        exportData(getService().selectCurrentReportDetailExportData(listVo), "todayDetailList", response);
    }

    /**
     * 导出数据
     * @param exportData 需要导出的数据
     * @param exportKey export-bean对应的key
     * @param response
     * @throws IOException
     */
    public void exportData(List<VBetDetail> exportData, String exportKey, HttpServletResponse response) throws IOException {

        String className = "g.model.bet.po.VBetDetail";
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
    //endregion your codes 3
}
