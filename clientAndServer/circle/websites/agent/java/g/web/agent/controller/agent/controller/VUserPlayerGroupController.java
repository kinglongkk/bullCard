package g.web.agent.controller.agent.controller;

import g.model.SiteParamEnum;
import g.model.admin.po.BetLimit;
import g.model.admin.po.BetLimitMultiple;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.vo.VUserBetLimitMultipleListVo;
import g.model.admin.vo.VUserBetLimitVo;
import g.model.admin.vo.VUserGroupBetLimitMultipleListVo;
import g.model.admin.vo.VUserGroupBetLimitVo;
import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.vo.VUserPlayerGroupListVo;
import g.model.agent.vo.VUserPlayerGroupVo;
import g.model.cache.CacheBase;
import g.service.common.IVUserPlayerGroupService;
import g.web.agent.controller.agent.form.VUserPlayerGroupForm;
import g.web.agent.controller.agent.form.VUserPlayerGroupSearchForm;
import g.web.agent.session.SessionManager;
import g.web.agent.tools.ServiceTool;
import g.web.common.cache.ParamTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.sys.po.SysParam;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
@Controller
@RequestMapping("/vUserPlayerGroup")
public class VUserPlayerGroupController extends BaseCrudController<IVUserPlayerGroupService, VUserPlayerGroupListVo, VUserPlayerGroupVo, VUserPlayerGroupSearchForm, VUserPlayerGroupForm, VUserPlayerGroup, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/player/group/";
        //endregion your codes 2
    }

    //region your codes 3

    @RequestMapping("/Index")
    public String index(Model model, VUserPlayerGroupListVo listVo, HttpServletRequest request) {
        listVo.getSearch().setCreateUser(SessionManager.getUserId());
        VUserPlayerGroupListVo command = ServiceTool.vUserPlayerGroupService().search(listVo);
        model.addAttribute("command", command);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + "IndexPartial";
        } else {
            return getViewBasePath() + "Index";
        }
    }

    //region by Tom
    @RequestMapping("/deletePlayerGroup")
    @ResponseBody
    public Map delete(VUserPlayerGroupVo groupVo) {
        VUserPlayerGroupVo vUserPlayerGroupVo = getService().get(groupVo);
        if (vUserPlayerGroupVo.getResult().getPlayernum()>0)
            return null;
        Boolean bool = getService().delete(groupVo);
        if (bool) {
            groupVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.success"));
            CacheBase.refreshUserGroupBetLimit();
            CacheBase.refreshUserGroupBetLimitMultiple();
        } else {
            groupVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.failed"));
        }
        HashMap map = new HashMap(2);
        map.put("msg", StringTool.isNotBlank(groupVo.getOkMsg()) ? groupVo.getOkMsg() : groupVo.getErrMsg());
        map.put("state", Boolean.valueOf(groupVo.isSuccess()));
        return map;
    }

    @Override
    protected VUserPlayerGroupVo doCreate(VUserPlayerGroupVo objectVo, Model model) {
        getAgetnLimit(objectVo);
        getSysBetLimitMinParam(objectVo);
        return super.doCreate(objectVo, model);
    }

    @Override
    protected VUserPlayerGroupVo doEdit(VUserPlayerGroupVo objectVo, Model model) {
        getAgetnLimit(objectVo);
        getPlayerGroupBetLimit(objectVo);
        getPlayerGroupBetLimitMultiple(objectVo);
        getSysBetLimitMinParam(objectVo);
        return super.doEdit(objectVo, model);
    }

    @RequestMapping("/editUserGroup")
    @ResponseBody
    public Map editUserGroup(VUserPlayerGroupVo objectVo, @FormModel("result") @Valid VUserPlayerGroupForm form, BindingResult result) {
        if (!result.hasErrors()) {
            if (checkRange(objectVo)) {
                objectVo.setOperatorUser(SessionManager.getUser());
                objectVo = getService().editUserGroup(objectVo);
                CacheBase.refreshUserGroupBetLimit();
                CacheBase.refreshUserGroupBetLimitMultiple();
            } else {
                objectVo.setErrMsg(LocaleTool.tranMessage("group","input.right.betlimit"));
                objectVo.setSuccess(false);
            }
            return getVoMessage(objectVo);
        }
        return null;
    }

    @RequestMapping("/checkGroupnameExist")
    @ResponseBody
    public String checkGroupnameExist(@RequestParam("result.id") Integer id,@RequestParam("result.groupName") String groupName) {
        if (StringTool.isEmpty(groupName))
            return "false";
        VUserPlayerGroupVo objectVo = new VUserPlayerGroupVo();
        VUserPlayerGroup vUserPlayerGroup = new VUserPlayerGroup();
        vUserPlayerGroup.setId(id);
        vUserPlayerGroup.setGroupName(groupName);
        objectVo.setResult(vUserPlayerGroup);
        objectVo.setOperatorUser(SessionManager.getUser());
        return Boolean.toString(getService().countGroupname(objectVo));
    }

    @Override
    protected VUserPlayerGroupVo doView(VUserPlayerGroupVo objectVo, Model model) {
        getAgentBetLimitMultiple(objectVo);
        getPlayerGroupBetLimit(objectVo);
        getPlayerGroupBetLimitMultiple(objectVo);
        getSysBetLimitMinParam(objectVo);
        return super.doView(objectVo, model);
    }

    private void getSysBetLimitMinParam(VUserPlayerGroupVo objectVo) {
        SysParam sysParam = ParamTool.getSysParam(SiteParamEnum.SYS_BET_MIN);
        objectVo.setSysParam(sysParam);
    }

    private void getPlayerGroupBetLimit(VUserPlayerGroupVo objectVo) {
        VUserGroupBetLimitVo vUserGroupBetLimitVo = new VUserGroupBetLimitVo();
        vUserGroupBetLimitVo.getSearch().setSysUserGroupId(objectVo.getSearch().getId());
        objectVo.setvUserGroupBetLimitList(ServiceTool.vUserGroupBetLimitService().getPlayerGroupBetLimit(vUserGroupBetLimitVo));
    }

    private void getPlayerGroupBetLimitMultiple(VUserPlayerGroupVo objectVo) {
        VUserGroupBetLimitMultipleListVo vUserGroupBetLimitMultipleListVo = new VUserGroupBetLimitMultipleListVo();
        vUserGroupBetLimitMultipleListVo.getSearch().setSysUserGroupId(objectVo.getSearch().getId());
        vUserGroupBetLimitMultipleListVo = ServiceTool.vUserGroupBetLimitMultipleService().search(vUserGroupBetLimitMultipleListVo);
        objectVo.setvUserGroupBetLimitMultipleListVo(vUserGroupBetLimitMultipleListVo);
    }

    private void getAgetnLimit(VUserPlayerGroupVo objectVo) {
        getAgentBetLimit(objectVo);
        getAgentBetLimitMultiple(objectVo);
    }

    private void getAgentBetLimit(VUserPlayerGroupVo objectVo) {
        VUserBetLimitVo vUserBetLimitVo = new VUserBetLimitVo();
        vUserBetLimitVo.getSearch().setSysUserId(SessionManager.getUserId());
        objectVo.setUserBetLimit(ServiceTool.vUserBetLimitService().getUserBetLimit(vUserBetLimitVo));
    }

    private void getAgentBetLimitMultiple(VUserPlayerGroupVo objectVo) {
        VUserBetLimitMultipleListVo vUserBetLimitMultipleListVo = getAgentBetLimitMultipleListVo();
        Map<Integer, List<VUserBetLimitMultiple>> betNumKeyMap = CollectionTool.groupByProperty(vUserBetLimitMultipleListVo.getResult(), VUserBetLimitMultiple.PROP_BET_NUM, Integer.class);
        objectVo.setBetNumKeyMap(betNumKeyMap);
    }

    private VUserBetLimitMultipleListVo getAgentBetLimitMultipleListVo() {
        VUserBetLimitMultipleListVo vUserBetLimitMultipleListVo = new VUserBetLimitMultipleListVo();
        vUserBetLimitMultipleListVo.getSearch().setSysUserId(SessionManager.getUserId());
        vUserBetLimitMultipleListVo = ServiceTool.vUserBetLimitMultipleService().search(vUserBetLimitMultipleListVo);
        return vUserBetLimitMultipleListVo;
    }

    private boolean checkRange(VUserPlayerGroupVo objectVo) {
        // 检测单注单项限制
        boolean isPass = true;
        List<BetLimit> betLimitList = objectVo.getBetLimitList();
        VUserBetLimitVo betLimitListVo = new VUserBetLimitVo();
        List<VUserBetLimit> userBetLimit = ServiceTool.vUserBetLimitService().getUserBetLimit(betLimitListVo);

        for(int i=0;i<userBetLimit.size();i++) {
            VUserBetLimit vUserBetLimit = userBetLimit.get(i);
            BetLimit betLimit = betLimitList.get(i);
            if (betLimit.getBetMax()<=vUserBetLimit.getBetMin() || betLimit.getBetMax()>=vUserBetLimit.getBetMax()) {
                isPass = false;
                break;
            }
            if (betLimit.getItemMax()>vUserBetLimit.getItemMax()) {
                isPass = false;
                break;
            }
        }

        VUserBetLimitMultipleListVo vUserBetLimitMultipleListVo = getAgentBetLimitMultipleListVo();
        List<VUserBetLimitMultiple> result = vUserBetLimitMultipleListVo.getResult();
        List<BetLimitMultiple> betLimitMultipleList = objectVo.getBetLimitMultipleList();
        for(int i=0;i<result.size();i++) {
            if (betLimitMultipleList.get(i).getBetMax()>=result.get(i).getBetMax()) {
                isPass = false;
                break;
            }
        }
        return isPass;
    }

    //endregion by tom
//endregion your codes 3

}