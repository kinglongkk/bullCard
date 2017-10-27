package g.admin.activityapply.controller;

import g.model.enums.ActivityTypeEnum;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import g.service.activityapply.IActivityPlayerApplyService;
import g.model.activityapply.po.ActivityPlayerApply;
import g.model.activityapply.vo.ActivityPlayerApplyListVo;
import g.model.activityapply.vo.ActivityPlayerApplyVo;
import g.admin.activityapply.form.ActivityPlayerApplySearchForm;
import g.admin.activityapply.form.ActivityPlayerApplyForm;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * 活动申请玩家表控制器
 *
 * @author black
 * @time 2016-9-18 19:10:58
 */
@Controller
//region your codes 1
@RequestMapping("/activityPlayerApply")
public class ActivityPlayerApplyController extends BaseCrudController<IActivityPlayerApplyService, ActivityPlayerApplyListVo, ActivityPlayerApplyVo, ActivityPlayerApplySearchForm, ActivityPlayerApplyForm, ActivityPlayerApply, Integer> {
//endregion your codes 1

    private static final String GROUP_INDEX = "/activity/apply/GroupIndex";
    private static final String INDEX = "/activity/apply/Index";

    @Autowired
    private IActivityPlayerApplyService playerApplyService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/activity/apply/";
        //endregion your codes 2
    }


    @RequestMapping("/groupByList")
    public String groupByList(ActivityPlayerApplyListVo listVo, @FormModel("search") @Valid ActivityPlayerApplySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = playerApplyService.selectActivityPlayerApplyInfoByGroup(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        return ServletTool.isAjaxSoulRequest(request) ? GROUP_INDEX + "Partial" : GROUP_INDEX;
    }

    /**
     * List Action的基础方法
     *
     * @param listVo   继承了 BaseListVo的Vo
     * @param form     表单验证对象
     * @param result   表单验证结果
     * @param model    Spring Mvc model对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return List视图
     */
    @Override
    public String list(ActivityPlayerApplyListVo listVo, @FormModel("search") @Valid ActivityPlayerApplySearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        listVo = playerApplyService.selectActivityPlayerApplyInfoByDetail(listVo);
        model.addAttribute("command", listVo);
        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }

}