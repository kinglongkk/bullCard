package g.web.admin.sys.controller;

import g.model.admin.po.Remark;
import g.web.admin.sys.form.RemarkForm;
import g.web.admin.sys.form.RemarkSearchForm;
import g.web.admin.session.SessionManager;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import g.service.common.IRemarkService;
import g.model.admin.vo.RemarkListVo;
import g.model.admin.vo.RemarkVo;

import javax.servlet.http.HttpServletRequest;


/**
 * 控制器:玩家详细-备注
 * <p/>
 * Author:orange
 * 2015-6-30 11:35:38
 */
@Controller
@RequestMapping("/playerRemark")
public class PlayerRemarkController extends BaseCrudController<IRemarkService, RemarkListVo, RemarkVo, RemarkSearchForm, RemarkForm, Remark, Integer> {
    private static String REMARK_URI = "/remark/Remark";
    private static String AGENT_REMARK_URI = "/remark/Remark";

    @Override
    protected String getViewBasePath() {
        return "/remark/";
    }

    /**
     * 玩家详细-备注页面
     *
     * @param listVo
     * @param model
     * @return
     */

    @RequestMapping("/remark")
    public String remark(RemarkListVo listVo, Model model, HttpServletRequest request) {
        listVo = getService().search(listVo);
        model.addAttribute("command", listVo);
        return ServletTool.isAjaxSoulRequest(request) ? REMARK_URI + "Partial" : REMARK_URI;
    }

    @Override
    protected RemarkVo doCreate(RemarkVo objectVo, Model model) {
        return objectVo;
    }

    @Override
    protected RemarkVo doUpdate(RemarkVo objectVo) {
        Remark remark = objectVo.getResult();
        remark.setOperatorId(SessionManager.getUserId());
        remark.setRemarkTime(SessionManager.getDate().getNow());
        remark.setOperator(SessionManager.getUserName());
        return this.getService().update(objectVo);
    }

    @Override
    protected RemarkVo doSave(RemarkVo objectVo) {
        Remark remark = objectVo.getResult();
        remark.setOperatorId(SessionManager.getUserId());
        remark.setRemarkTime(SessionManager.getDate().getNow());
        remark.setOperator(SessionManager.getUserName());
        return this.getService().insert(objectVo);
    }

    /**
     * 代理的备注
     * @param listVo
     * @param model
     * @return
     */
    @RequestMapping("/agentRemark")
    public String agentRemark(RemarkListVo listVo, Model model) {
        listVo = getService().search(listVo);
        model.addAttribute("command", listVo);
        return AGENT_REMARK_URI;
    }

}