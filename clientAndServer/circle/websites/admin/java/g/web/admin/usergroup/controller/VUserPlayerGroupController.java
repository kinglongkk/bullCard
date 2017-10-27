package g.web.admin.usergroup.controller;

import g.model.SiteParamEnum;
import g.model.admin.po.BetLimit;
import g.model.admin.po.BetLimitMultiple;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.po.VUserBetLimitMultiple;
import g.model.admin.vo.VUserBetLimitMultipleListVo;
import g.model.admin.vo.VUserBetLimitVo;
import g.model.admin.vo.VUserGroupBetLimitMultipleListVo;
import g.model.admin.vo.VUserGroupBetLimitVo;
import g.model.agent.po.UserPlayerGroup;
import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.vo.UserPlayerGroupListVo;
import g.model.agent.vo.UserPlayerGroupVo;
import g.model.agent.vo.VUserPlayerGroupListVo;
import g.model.agent.vo.VUserPlayerGroupVo;
import g.model.cache.CacheBase;
import g.service.common.IVUserPlayerGroupService;
import g.service.common.UserPlayerGroupService;
import g.service.common.VUserPlayerGroupService;
import g.web.admin.tools.ServiceTool;
import g.web.admin.usergroup.form.VUserPlayerGroupForm;
import g.web.admin.usergroup.form.VUserPlayerGroupSearchForm;
import g.web.admin.session.SessionManager;
import g.web.common.cache.ParamTool;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.sys.po.SysParam;
import org.soul.web.controller.BaseCrudController;

import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
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

    @Autowired
    private UserPlayerGroupService userPlayerGroupService;
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
        VUserPlayerGroupListVo command =getService().search(listVo);
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
    public Map delete(UserPlayerGroupVo groupVo) {
        Boolean bool = userPlayerGroupService.delete(groupVo);
        if (bool) {
            groupVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.success"));
        } else {
            groupVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "delete.failed"));
        }
        HashMap map = new HashMap(2);
        map.put("msg", StringTool.isNotBlank(groupVo.getOkMsg()) ? groupVo.getOkMsg() : groupVo.getErrMsg());
        map.put("state", Boolean.valueOf(groupVo.isSuccess()));
        return map;
    }


    @RequestMapping("/editUserGroup")
    @ResponseBody
    public Map editUserGroup(UserPlayerGroupVo objectVo, @FormModel("result") @Valid VUserPlayerGroupForm form, BindingResult result) {
      if (objectVo.getResult().getId() == null){
          //新增
          objectVo.getResult().setCreateUser(SessionManager.getUserId());
          objectVo.getResult().setCreateTime(new Date());
          objectVo.getResult().setIsDefault(false);
          UserPlayerGroupVo vo = userPlayerGroupService.insert(objectVo);
          if (vo.isSuccess()) {
              vo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "create.success"));
          } else {
              vo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "create.failed"));
          }
          Map map = new HashMap();
          map.put("msg", StringTool.isNotBlank(vo.getOkMsg()) ? vo.getOkMsg() : vo.getErrMsg());
          map.put("state", vo.isSuccess());
          return map;
      }else{
          //编辑
          objectVo.getResult().setUpdateUser(SessionManager.getUserId());
          objectVo.getResult().setUpdateTime(new Date());
          objectVo.setProperties(UserPlayerGroup.PROP_GROUP_NAME,UserPlayerGroup.PROP_UPDATE_TIME,UserPlayerGroup.PROP_UPDATE_USER);
          UserPlayerGroupVo vo = userPlayerGroupService.updateOnly(objectVo);

          if (vo.isSuccess()) {
              vo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "update.success"));
          } else {
              vo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "update.failed"));
          }
          Map map = new HashMap();
          map.put("msg", StringTool.isNotBlank(vo.getOkMsg()) ? vo.getOkMsg() : vo.getErrMsg());
          map.put("state", vo.isSuccess());
          return map;
      }
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

    @RequestMapping("/setDefault")
    @ResponseBody
    public Map setDefault(UserPlayerGroupVo groupVo) {
        //取出默认分组改成false
        UserPlayerGroupVo vo = new UserPlayerGroupVo();
        vo.getSearch().setCreateUser(SessionManager.getUserId());
        UserPlayerGroupVo vo1 = userPlayerGroupService.getDefaultGroup(vo);
        vo1.getResult().setIsDefault(false);
        UserPlayerGroupVo vo2 = userPlayerGroupService.update(vo1);

        //把点击的那组改成true
        UserPlayerGroupVo vo3 = userPlayerGroupService.getGroupById(groupVo);
        vo3.getResult().setIsDefault(true);
        UserPlayerGroupVo vo4 = userPlayerGroupService.update(vo3);
        if (vo2.isSuccess()&&vo4.isSuccess()) {
            vo2.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "update.success"));
        } else {
            vo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "update.failed"));
        }
        Map map = new HashMap();
        map.put("msg", StringTool.isNotBlank(vo2.getOkMsg()) ? vo2.getOkMsg() : vo2.getErrMsg());
        map.put("state", vo2.isSuccess());
        return map;

    }
    //endregion by tom
//endregion your codes 3

}