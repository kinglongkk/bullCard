package g.admin.activitymessage.controller;

import g.model.activitymessage.po.ActivityWayRelation;
import g.model.activitymessage.vo.ActivityRuleListVo;
import g.model.activitymessage.vo.ActivityWayRelationListVo;
import g.model.enums.*;
import g.service.activitymessage.IActivityRuleService;
import g.service.activitymessage.IActivityWayRelationService;
import g.web.admin.session.SessionManager;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.web.controller.BaseCrudController;
import g.service.activitymessage.IActivityMessageService;
import g.model.activitymessage.po.ActivityMessage;
import g.model.activitymessage.vo.ActivityMessageListVo;
import g.model.activitymessage.vo.ActivityMessageVo;
import g.admin.activitymessage.form.ActivityMessageSearchForm;
import g.admin.activitymessage.form.ActivityMessageForm;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;


/**
 * 游戏信息表控制器
 *
 * @author black
 * @time 2016-9-5 14:46:12
 */
@Controller
//region your codes 1
@RequestMapping("/activityMessage")
public class ActivityMessageController extends BaseCrudController<IActivityMessageService, ActivityMessageListVo, ActivityMessageVo, ActivityMessageSearchForm, ActivityMessageForm, ActivityMessage, Integer> {
//endregion your codes 1

    private static final String INDEX = "/activity/message/Index";
    private static final String VIEW = "/activity/message/View";
    private static final String EDIT = "/activity/message/Edit";
    private static final String CURRENTINDEX = "/activity/current/Index";

    @Autowired
    private IActivityMessageService activityMessageService;

    @Autowired
    private IActivityRuleService activityRuleService;

    @Autowired
    private IActivityWayRelationService activityWayRelationService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/activity/message/";
        //endregion your codes 2
    }


    /**
     * 列表展示
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String list(ActivityMessageListVo listVo, @FormModel("search") @Valid ActivityMessageSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        return super.list(listVo, form, result, model, request, response);
    }

    /**
     * View Action的基础方法
     *
     * @param id       要View的内容的主键
     * @param model    Spring Mvc model对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return View视图
     */
    public String view(ActivityMessageVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {
            objectVo.getSearch().setId(id);
        }
        objectVo = doView(objectVo, model);
        ActivityRuleListVo ruleListVo = selectActivityRuleListVo(id);
        ActivityWayRelationListVo relationListVo = selectActivityWayRelationListVo(id);

        model.addAttribute("command", objectVo);
        model.addAttribute("ruleListVo", ruleListVo);
        model.addAttribute("relationListVo", relationListVo);
        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        model.addAttribute("ballType",BallTypeEnum.values());
        model.addAttribute("lotteryType",LotteryTypeEnum.values());
        model.addAttribute("chessCard",ChessCardEnum.values());
        if (ServletTool.isAjaxSoulRequest(request)) {

            return VIEW + "Partial";
        } else {

            return VIEW;
        }

    }


    /**
     * 新增游戏信息
     * @param objectVo
     * @param model
     * @param request
     * @param response
     * @return
     */
    @Override
    public String create(ActivityMessageVo objectVo, Model model, HttpServletRequest request, HttpServletResponse response) {

        String activityClassifyKey = objectVo.getResult().getActivityClassifyKey();
        model.addAttribute("activityClassifyKey",activityClassifyKey);
        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        model.addAttribute("firstName", GameTypeEnum.values());
        model.addAttribute("ballType", BallTypeEnum.values());
        model.addAttribute("command", objectVo);
        return super.create(objectVo, model, request, response);
    }

    /**
     * Edit Action的基础方法
     *
     * @param objectVo 继承了 BaseObjectVo的Vo
     * @param id       要Edit的内容的主键
     * @param model    Spring Mvc model对象
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Edit视图
     */
    public String edit(ActivityMessageVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        if (id != null) {

            objectVo.getSearch().setId(id);
        }
        objectVo = doEdit(objectVo, model);
        ActivityRuleListVo ruleListVo = selectActivityRuleListVo(id);
        ActivityWayRelationListVo relationListVo = selectActivityWayRelationListVo(id);

        model.addAttribute("command", objectVo);
        model.addAttribute("ruleListVo", ruleListVo);
        model.addAttribute("relationListVo", relationListVo);
        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        model.addAttribute("firstName", GameTypeEnum.values());
        model.addAttribute("ballType",BallTypeEnum.values());
        model.addAttribute("lotteryType",LotteryTypeEnum.values());
        model.addAttribute("chessCard",ChessCardEnum.values());

        if (ServletTool.isAjaxSoulRequest(request)) {

            return EDIT + "Partial";
        } else {

            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return EDIT;
        }
    }

    /**
     * 保存
     * @param objectVo
     * @param form
     * @param result
     * @return
     */
    @Override
    public Map persist(ActivityMessageVo objectVo, @FormModel("result") @Valid ActivityMessageForm form, BindingResult result) {

        objectVo.getResult().setUpdateUserId(SessionManager.getUserId());
        objectVo.getResult().setUpdateUserName(SessionManager.getUserName());

        Map map = new HashMap();
        map.put("rule", objectVo.getActivityRule());

        List<ActivityWayRelation> list = new ArrayList<ActivityWayRelation>();
        if (objectVo.getActivityWayRelation() != null && !objectVo.getActivityWayRelation().isEmpty()){

            for(int i = 0; i< objectVo.getActivityWayRelation().size(); i++){

                ActivityWayRelation relation = objectVo.getActivityWayRelation().get(i);
                if (relation.getIsArticle() != null){

                    list.add(objectVo.getActivityWayRelation().get(i));
                }
            }
        }

        map.put("relation", list);
        boolean isSuccess = false;

        //insert or update
        try {

            StringTool.isBlank(objectVo.getResult().getId().toString());
        }catch (NullPointerException e){

            objectVo.getResult().setCreateUserId(SessionManager.getUserId());
            objectVo.getResult().setCreateUserName(SessionManager.getUserName());
            map.put("message", objectVo.getResult());
            isSuccess = activityMessageService.insertActivityMessageInfo(map);
            return getVoMessage(objectVo);
        }

        map.put("message", objectVo.getResult());
        isSuccess = activityMessageService.updateActivityMessageInfo(map);
        if (isSuccess){

            objectVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "save.success"));
        }else{

            objectVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "save.failed"));
        }
        return getVoMessage(objectVo);
    }

    /**
     * 逻辑删除
     * @param objectVo
     * @param id
     * @return
     */
    public Map delete(ActivityMessageVo objectVo, Integer id) {

        Map map = new HashMap();
        map.put("updateUserId", SessionManager.getUserId());
        map.put("updateUser", SessionManager.getUserName());
        map.put("messageId", id);

        if (activityMessageService.updateMessageIsDeleted(map)) {

            objectVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.success"));

        } else {

            objectVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.failed"));
        }
        return getVoMessage(objectVo);
    }

    /**
     * 活动选择页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/ActivitySelect")
    public String toSelectActivity(Model model) {

        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        return this.getViewBasePath() + "ActivitySelect";
    }

    /**
     * 查询活动规则
     * @return listVo
     */
    public ActivityRuleListVo selectActivityRuleListVo(Integer id){

        ActivityRuleListVo activityRuleListVo = new ActivityRuleListVo();
        ActivityRuleListVo ruleListVo;
        try {

            StringTool.isBlank(id.toString());
        }catch (NullPointerException e){

            ruleListVo = activityRuleService.search(activityRuleListVo);
            return ruleListVo;
        }
        activityRuleListVo.getSearch().setActivityMessageId(id);
        ruleListVo = activityRuleService.search(activityRuleListVo);
        return ruleListVo;
    }

    /**
     * 查询活动优惠
     * @return listVo
     */
    public ActivityWayRelationListVo selectActivityWayRelationListVo(Integer id){


        ActivityWayRelationListVo activityWayRelationListVo = new ActivityWayRelationListVo();
        ActivityWayRelationListVo relationListVo;
        try {

            StringTool.isBlank(id.toString());
        }catch (NullPointerException e){

            relationListVo = activityWayRelationService.search(activityWayRelationListVo);
            return relationListVo;
        }

        activityWayRelationListVo.getSearch().setActivityMessageId(id);
        relationListVo = activityWayRelationService.search(activityWayRelationListVo);
        return relationListVo;
    }

    /**
     * 获取游戏类型
     * @param typeCode
     * @return
     */
    @RequestMapping(value = "/getGameType",method = RequestMethod.POST)
    @ResponseBody
    public Map getGameType(String typeCode){

        Map map = new HashMap();
        List gameTypeCodes = new ArrayList();
        List gameTypeTranss = new ArrayList();
        if (typeCode.equals(GameTypeEnum.SPORT.getCode())){

            for (BallTypeEnum ballTypeEnum : BallTypeEnum.values()) {

                String gameTypeCode = ballTypeEnum.getCode();
                String gameTypeTrans = ballTypeEnum.getTrans();
                gameTypeCodes.add(gameTypeCode);
                gameTypeTranss.add(gameTypeTrans);
            }
            map.put("gameTypeCodes",gameTypeCodes);
            map.put("gameTypeTranss",gameTypeTranss);
            return map;
        }
        if (typeCode.equals(GameTypeEnum.LOTTERY.getCode())){

            for (LotteryTypeEnum lotteryTypeEnum : LotteryTypeEnum.values()) {

                String gameTypeCode = lotteryTypeEnum.getCode();
                String gameTypeTrans = lotteryTypeEnum.getTrans();
                gameTypeCodes.add(gameTypeCode);
                gameTypeTranss.add(gameTypeTrans);
            }
            map.put("gameTypeCodes",gameTypeCodes);
            map.put("gameTypeTranss",gameTypeTranss);
            return map;
        }
        if (typeCode.equals(GameTypeEnum.CHESS_CARD.getCode())){

            for (ChessCardEnum chessCardEnum : ChessCardEnum.values()) {

                String gameTypeCode = chessCardEnum.getCode();
                String gameTypeTrans = chessCardEnum.getTrans();
                gameTypeCodes.add(gameTypeCode);
                gameTypeTranss.add(gameTypeTrans);
            }
            map.put("gameTypeCodes",gameTypeCodes);
            map.put("gameTypeTranss",gameTypeTranss);
            return map;
        }
        return null;
    }

    /**
     * 当前活动
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/currentactivitylist")
    public String getCurrentActivityList(ActivityMessageListVo listVo,Model model,HttpServletRequest request) {

        model.addAttribute("activityTypeEnum", ActivityTypeEnum.values());
        listVo.getSearch().setEndTime(new Date());
        listVo.getSearch().setIsDisplay(true);
        listVo.getSearch().setIsDeleted(false);
        listVo = getService().search(listVo);
        model.addAttribute("command",listVo);
        return ServletTool.isAjaxSoulRequest(request) ? CURRENTINDEX + "Partial" : CURRENTINDEX;
    }
}