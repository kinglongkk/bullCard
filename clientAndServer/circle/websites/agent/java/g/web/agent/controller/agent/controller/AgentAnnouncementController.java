package g.web.agent.controller.agent.controller;


import g.model.admin.po.SysAnnouncement;
import g.model.admin.po.SysAnnouncementRead;
import g.model.admin.po.SystemAnnouncement;
import g.model.admin.vo.*;
import g.service.common.ISystemAnnouncementService;
import g.web.agent.controller.agent.form.SystemAnnouncementForm;
import g.web.agent.controller.agent.form.SystemAnnouncementSearchForm;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * 控制器
 */
@Controller
//region your codes 1
@RequestMapping("/agentAnnouncement")
public class AgentAnnouncementController extends BaseCrudController<ISystemAnnouncementService, SystemAnnouncementListVo, SystemAnnouncementVo, SystemAnnouncementSearchForm, SystemAnnouncementForm, SystemAnnouncement, Integer> {

//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/announcement/";
        //endregion your codes 2
    }
    //region your codes 3

    /**
     * 历史记录
     *
     * @param request
     * @param vSysAnnouncementListVo
     * @param model
     * @return
     */
    @RequestMapping("/agentNoticeHistory")
    public String agentNoticeHistory(HttpServletRequest request, VSysAnnouncementListVo vSysAnnouncementListVo, Model model) {
        vSysAnnouncementListVo.getSearch().setLocal(SessionManager.getLocale().toString());
        vSysAnnouncementListVo.getSearch().setRecipientUserId(SessionManager.getUserId());
        vSysAnnouncementListVo = ServiceTool.vSysAnnouncementService().search(vSysAnnouncementListVo);
        vSysAnnouncementListVo.changeReadState(SessionManager.getUserId());
        model.addAttribute("command", vSysAnnouncementListVo);
        return ServletTool.isAjaxSoulRequest(request) ? getViewBasePath() + "IndexPartial" : getViewBasePath() +"Index";
    }

    /**
     * 历史记录详细
     *
     * @param model
     * @param vSysAnnouncementListVo
     * @return
     */
    @RequestMapping("/agentNoticeDetail")
    public String agentNoticeDetail(Model model, VSysAnnouncementListVo vSysAnnouncementListVo) {
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

        return getViewBasePath() + "View";
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