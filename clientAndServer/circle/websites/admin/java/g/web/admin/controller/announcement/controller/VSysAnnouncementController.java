package g.web.admin.controller.announcement.controller;

import g.web.admin.controller.announcement.form.VSysAnnouncementForm;
import g.web.admin.tools.ServiceTool;
import g.service.common.IVSysAnnouncementService;
import g.model.SubSysCodeEnum;
import g.model.admin.po.*;
import g.model.admin.vo.*;

import org.apache.commons.lang3.StringUtils;
import org.soul.commons.lang.DateTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import g.web.admin.controller.announcement.form.VSysAnnouncementSearchForm;
import g.web.admin.session.SessionManager;
import g.model.cache.CacheBase;
import g.model.enums.AnnouncementTypeEnum;
import g.model.site.SiteLanguage;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 总控-系统公告视图控制器
 *
 * @author orange
 * @time 2015-12-3 11:45:57
 */
@Controller
//region your codes 1
@RequestMapping("/vSysAnnouncement")
public class VSysAnnouncementController extends BaseCrudController<IVSysAnnouncementService, VSysAnnouncementListVo, VSysAnnouncementVo, VSysAnnouncementSearchForm, VSysAnnouncementForm, VSysAnnouncement, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/announcement/";
        //endregion your codes 2
    }

    //region your codes 3
    private static final String SYS_NOTICE_URL = "announcement/SysNotice";
    //进入预览页面
    private static final String SYS_NOTICE_PREVIEW_URL = "announcement/SysNoticePreview";
    //发送信息
    private static final String SAVE_SYS_NOTICE_URL = "announcement/SaveSysNotice";
    //历史记录
    private static final String SYS_NOTICE_HISTORY_URL = "announcement/SysNoticeHistory";
    //详情
    private static final String SYS_NOTICE_DETAIL_URL = "announcement/SysNoticeDetail";
    //region your codes 3

    /**
     * 第一次进入 and 返回上一页
     *
     * @param model
     * @param sysAnnouncementVo
     * @return
     */
    @RequestMapping("/sysNotice")
    public String SysNotice(Model model, SysAnnouncementVo sysAnnouncementVo) {
        model.addAttribute("validate", JsRuleCreator.create(VSysAnnouncementForm.class));
        Map<String, SiteLanguage> siteLanguage = CacheBase.getSiteLanguage();
        model.addAttribute("languageList", siteLanguage.keySet());
        VSysUserContactListVo listVo = new VSysUserContactListVo();
        listVo.getSearch().setSubsysCode(SubSysCodeEnum.AGENT.getCode());
        List<VSysUserContact> vSysUserList = ServiceTool.vSysUserContactService().searchSysUserContact(listVo);
        //在所有代理商中删除指定代理的ID
        if(sysAnnouncementVo.getResult()!=null && AnnouncementTypeEnum.ALL_AGENT.getCode().equals(sysAnnouncementVo.getResult().getGroupCode())){
            sysAnnouncementVo.setAgentIds("");
        }
//        if(sysAnnouncementVo.getAgentIds()!=null && sysAnnouncementVo.getAgentIds()!=""){
        if (StringUtils.isNotBlank(sysAnnouncementVo.getAgentIds())) {
            String[] selectAgentIds = sysAnnouncementVo.getAgentIds().split(",");
            Iterator<VSysUserContact> vSysUser = vSysUserList.iterator();
            while(vSysUser.hasNext()){
                VSysUserContact v = vSysUser.next();
                for (String id:selectAgentIds){
                    if(v.getId().toString().equals(id)){
                        vSysUser.remove();
                    }
                }
            }
            model.addAttribute("agentList", vSysUserList);
        }else{
            model.addAttribute("agentList", vSysUserList);
        }
        //返回上一页
        if(sysAnnouncementVo.getAgentIds()!=null && sysAnnouncementVo.getAgentIds()!=""){
            String[] selectAgentIds = sysAnnouncementVo.getAgentIds().split(",");
            VSysUserContactVo selectAgentVo = new VSysUserContactVo();
            List<VSysUserContact> selectAgentList = new ArrayList<>();
            for (String id:selectAgentIds){
                selectAgentVo.getSearch().setId(Integer.valueOf(id));
                selectAgentVo = ServiceTool.vSysUserContactService().get(selectAgentVo);
                selectAgentList.add(selectAgentVo.getResult());
            }
            model.addAttribute("selectAgentList", selectAgentList);
            model.addAttribute("selectAgentIds", selectAgentIds);
        }

        model.addAttribute("sysAnnouncementVo", sysAnnouncementVo);
        return SYS_NOTICE_URL;
    }

    //进入预览页面
    @RequestMapping("sysNoticePreview")
    public String SysNoticePreview(Model model, SysAnnouncementVo sysAnnouncementVo) {
        if("allAgent".equals(sysAnnouncementVo.getResult().getGroupCode())){
            SysUserListVo listVo = new SysUserListVo();
            listVo.getSearch().setSubsysCode(SubSysCodeEnum.AGENT.getCode());
            List sysUserList = ServiceTool.sysAnnouncementService().searchSysUserId(listVo);
            String ids = "";
            for(Object id:sysUserList){
                if (ids != "") {
                    ids += ",";
                }
                ids +=id;
            }
            sysAnnouncementVo.setAgentIds(ids);
        }
        Map<String, SiteLanguage> siteLanguage = CacheBase.getSiteLanguage();
        model.addAttribute("languageList", siteLanguage.keySet());
        model.addAttribute("sysAnnouncementVo", sysAnnouncementVo);
        return SYS_NOTICE_PREVIEW_URL;
    }

    /**
     * 编辑公告
     * @param model
     * @return
     */
    @RequestMapping("/editNotice")
    public String editNotice2(Model model,VSysAnnouncementListVo vSysAnnouncementListVo) {
        vSysAnnouncementListVo = ServiceTool.vSysAnnouncementService().search(vSysAnnouncementListVo);
        SysAnnouncementVo vo = new SysAnnouncementVo();
        vo.setResult(new SysAnnouncement());
        String[] language = new String[vSysAnnouncementListVo.getResult().size()];
        String[] title = new String[vSysAnnouncementListVo.getResult().size()];
        String[] content = new String[vSysAnnouncementListVo.getResult().size()];
        if(vSysAnnouncementListVo.getResult().size()>0){
            for(int i=0;i<vSysAnnouncementListVo.getResult().size();i++){
                language[i] = vSysAnnouncementListVo.getResult().get(i).getLocal();
                title[i] = vSysAnnouncementListVo.getResult().get(i).getTitle();
                content[i] = vSysAnnouncementListVo.getResult().get(i).getContent();
            }
            vo.setLanguage(language);
            vo.setTitle(title);
            vo.setContent(content);
            vo.setAgentIds(vSysAnnouncementListVo.getResult().get(0).getRecipientUserId()!=null?vSysAnnouncementListVo.getResult().get(0).getRecipientUserId().toString():null);
            vo.getResult().setId(vSysAnnouncementListVo.getResult().get(0).getId());
            vo.getResult().setPublishTime(vSysAnnouncementListVo.getResult().get(0).getPublishTime());
            vo.getResult().setAnnouncementType(vSysAnnouncementListVo.getResult().get(0).getAnnouncementType());
            vo.getResult().setGroupCode(vSysAnnouncementListVo.getResult().get(0).getGroupCode());
        }

        model.addAttribute("validate", JsRuleCreator.create(VSysAnnouncementForm.class));
        model.addAttribute("languageList", vo.getLanguage());
        model.addAttribute("sysAnnouncementVo", vo);
        //返回上一页
        VSysUserContactListVo listVo = new VSysUserContactListVo();
        listVo.getSearch().setSubsysCode(SubSysCodeEnum.AGENT.getCode());
        List<VSysUserContact> vSysUserList = ServiceTool.vSysUserContactService().searchSysUserContact(listVo);
        //在所有代理商中删除指定代理的ID
        if(vo.getAgentIds()!=null && vo.getAgentIds()!=""){
            String[] selectAgentIds = vo.getAgentIds().split(",");
            Iterator<VSysUserContact> vSysUser = vSysUserList.iterator();
            while(vSysUser.hasNext()){
                VSysUserContact v = vSysUser.next();
                for (String id:selectAgentIds){
                    if(v.getId().toString().equals(id)){
                        vSysUser.remove();
                    }
                }
            }
            model.addAttribute("agentList", vSysUserList);
        }else{
            model.addAttribute("agentList", vSysUserList);
        }

        //返回上一页
//        if(vo.getAgentIds()!=null && vo.getAgentIds()!=""){
        if (StringUtils.isNotBlank(vo.getAgentIds())) {
            String[] selectAgentIds = vo.getAgentIds().split(",");
            VSysUserContactVo selectAgentVo = new VSysUserContactVo();
            List<VSysUserContact> selectAgentList = new ArrayList<>();
            for (String id:selectAgentIds){
                selectAgentVo.getSearch().setId(Integer.valueOf(id));
                selectAgentVo = ServiceTool.vSysUserContactService().get(selectAgentVo);
                selectAgentList.add(selectAgentVo.getResult());
            }
            model.addAttribute("selectAgentList", selectAgentList);
        }
        return SYS_NOTICE_URL;
    }


    /**
     * 发布公告
     *
     * @return
     */
    @RequestMapping("/saveSysNotice")
    public String saveSysNotice(SysAnnouncementVo vo) {
        //添加系统公告表
        vo.setSuccess(false);
        if (DateTool.truncatedCompareTo(vo.getResult().getPublishTime() == null ? new Date() : vo.getResult().getPublishTime(), SessionManager.getDate().getNow(), Calendar.SECOND) <= 0) {
            //设置的时间已经小于当前时间,则改为立即发送,定时发送无效
            vo.getResult().setPublishTime(new Date());
        }
        vo.getResult().setPublishUserId(SessionManager.getUserId());
        vo.getResult().setPublishUserName(SessionManager.getUserName());
        vo.getResult().setSaveTime(new Date());
        vo.getResult().setStatus(AnnouncementTypeEnum.ENABLE.getCode());
        if ("agentNotice".equals(vo.getAgentNotice())) {
            //代理公告
            for (int i = 0; i < vo.getAgentIds().split(",").length; i++) {
                vo.getResult().setId(null);
                String[] agentIds = vo.getAgentIds().split(",");
                vo.getResult().setRecipientUserId(Integer.valueOf(agentIds[i]));
                if(vo.getSearch().getId()!=null){
                    ServiceTool.sysAnnouncementService().delete(vo);
                    vo = ServiceTool.sysAnnouncementService().insert(vo);
                }else{
                    vo = ServiceTool.sysAnnouncementService().insert(vo);
                }
                saveI18nLanguage(vo);
            }
        } else {
            //一般公告和重要公告
            if(vo.getSearch().getId()!=null) {
                ServiceTool.sysAnnouncementService().delete(vo);
                vo = ServiceTool.sysAnnouncementService().insert(vo);
            }else{
                vo = ServiceTool.sysAnnouncementService().insert(vo);
            }
            saveI18nLanguage(vo);

        }

        return SAVE_SYS_NOTICE_URL;
    }

    private void saveI18nLanguage(SysAnnouncementVo vo) {
        //添加系统公告国际化表
        if (vo.isSuccess()) {
            for (int i = 0; i < vo.getLanguage().length; i++) {
                SysAnnouncementI18nVo i18nVo = new SysAnnouncementI18nVo();
                i18nVo.setResult(new SysAnnouncementI18n());
                i18nVo.getResult().setSysAnnouncementId(vo.getResult().getId());
                i18nVo.getResult().setLocal(vo.getLanguage()[i]);
                i18nVo.getResult().setTitle(vo.getTitle()[i]);
                i18nVo.getResult().setContent(vo.getContent()[i]);
                if(vo.getSearch().getId()!=null){
                    i18nVo.getSearch().setSysAnnouncementId(vo.getSearch().getId());
                    i18nVo.getSearch().setLocal(vo.getLanguage()[i]);
                    Integer in = ServiceTool.sysAnnouncementI18nService().deleteI18nSysAnnouncementId(i18nVo);

                    ServiceTool.sysAnnouncementI18nService().insert(i18nVo);
                }else{
                    ServiceTool.sysAnnouncementI18nService().insert(i18nVo);
                }
            }
        }
    }

    /**
     * 历史记录
     *
     * @param request
     * @param vSysAnnouncementListVo
     * @param model
     * @return
     */
    @RequestMapping("/sysNoticeHistory")
    public String sysNoticeHistory(HttpServletRequest request, VSysAnnouncementListVo vSysAnnouncementListVo, Model model) {
        vSysAnnouncementListVo.getSearch().setLocal(SessionManager.getLocale().toString());
        vSysAnnouncementListVo = ServiceTool.vSysAnnouncementService().search(vSysAnnouncementListVo);
        vSysAnnouncementListVo.changeReadState(SessionManager.getUserId());
        model.addAttribute("command", vSysAnnouncementListVo);
        return ServletTool.isAjaxSoulRequest(request) ? SYS_NOTICE_HISTORY_URL + "Partial" : SYS_NOTICE_HISTORY_URL;
    }

    /**
     * 历史记录详细
     *
     * @param model
     * @param vSysAnnouncementListVo
     * @return
     */
    @RequestMapping("/sysNoticeDetail")
    public String sysNoticeDetail(Model model, VSysAnnouncementListVo vSysAnnouncementListVo) {
        vSysAnnouncementListVo = ServiceTool.vSysAnnouncementService().search(vSysAnnouncementListVo);
        model.addAttribute("vSysAnnouncementListVo", vSysAnnouncementListVo);

        //判断是否已读
        SysAnnouncementReadVo sysAnnouncementReadVo = new SysAnnouncementReadVo();
        sysAnnouncementReadVo.setResult(new SysAnnouncementRead());
        sysAnnouncementReadVo.getSearch().setSysAnnouncementId(vSysAnnouncementListVo.getSearch().getId());
        sysAnnouncementReadVo.getSearch().setSysUserId(SessionManager.getUserId());
        sysAnnouncementReadVo = ServiceTool.sysAnnouncementReadService().search(sysAnnouncementReadVo);
        if(sysAnnouncementReadVo.getResult()==null){
            SysAnnouncementReadVo readVo = new SysAnnouncementReadVo();
            readVo.setResult(new SysAnnouncementRead());
            readVo.getResult().setSysUserId(SessionManager.getUserId());
            readVo.getResult().setSysAnnouncementId(vSysAnnouncementListVo.getSearch().getId());
            ServiceTool.sysAnnouncementReadService().insert(readVo);
        }

        return SYS_NOTICE_DETAIL_URL;
    }

    /**
     * 取消发布
     *
     * @param sysAnnouncementVo
     * @return
     */
    @RequestMapping("/updateAnnouncementStatus")
    @ResponseBody
    public Map updateAnnouncementStatus(SysAnnouncementVo sysAnnouncementVo) {
        sysAnnouncementVo.setProperties(SysAnnouncement.PROP_STATUS);
        sysAnnouncementVo = ServiceTool.sysAnnouncementService().updateOnly(sysAnnouncementVo);
        String message = null;
        if (sysAnnouncementVo.isSuccess()) {
            message = LocaleTool.tranMessage(_Module.COMMON, "update.success");
        } else {
            message = LocaleTool.tranMessage(_Module.COMMON, "update.failed");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("msg", message);
        map.put("state", sysAnnouncementVo.isSuccess());
        return map;
    }

    //endregion your codes 3

}