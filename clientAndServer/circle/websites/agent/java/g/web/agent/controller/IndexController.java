package g.web.agent.controller;

import g.model.Module;
import g.model.ModuleType;
import g.model.common.Audit;
import g.web.agent.session.SessionManager;
import g.web.agent.init.Config;
import g.web.common.common.controller.BaseIndexController;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.dubbo.DubboTool;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.tree.TreeNode;
import org.soul.iservice.security.privilege.ISysResourceService;
import org.soul.model.gameapi.param.User;
import org.soul.model.security.privilege.po.VSysUserResource;
import org.soul.model.security.privilege.vo.SysResourceVo;
import org.soul.web.locale.DateFormat;
import org.soul.web.security.privilege.controller.SysResourceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import g.model.UserTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tony on 15-4-29.
 */
@Controller
public class IndexController extends BaseIndexController {
    private static final String INDEX_URI = "index";
    private static final String INDEX_CONTENT_URI = "index.include/content";
    private static final String INDEX_MESSAGE_URI = "index.include/AgentMessage";
    private static final String INDEX_TASK_URI = "index.include/Task";


    @Autowired
    private Config config;

    @RequestMapping(value = "index/content")
    protected String content(Integer parentId, HttpServletRequest request, HttpServletResponse response, Model model) {
        SysResourceVo o = new SysResourceVo();
        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(SessionManager.getUser().getUserType());
        String subsysCode = config.getSubsysCode();
        if (userTypeEnum == UserTypeEnum.AGENT_PLAYER) {
            subsysCode = "agent_player";
        } else if (!UserTypeEnum.isWholePerimssion(userTypeEnum)){
            o.getSearch().setUserId(SessionManager.getUserId());
        }

        o._setSiteId(CommonContext.get().getSiteId());
        o.getSearch().setSubsysCode(subsysCode);
        o.getSearch().setParentId(parentId);
        List<TreeNode<VSysUserResource>> menuNodeList = DubboTool.getService(ISysResourceService.class).getAllMenus(o);
        SysResourceController.loadLocal(menuNodeList);
        model.addAttribute("command", menuNodeList);
        return INDEX_CONTENT_URI;
    }

    @Audit(module = Module.MASTER_INDEX,moduleType = ModuleType.PASSPORT_LOGIN,desc = "刷新首页面")
    @RequestMapping(value = "index")
    protected String index(Model model) {
        return INDEX_URI;
    }

    /**
     * 用户所在时区时间
     */
    @RequestMapping(value = "/index/getUserTimeZoneDate")
    @ResponseBody
    public String getUserTimeZoneDate(){
        Map<String,String> map=new HashMap<>(2);
        map.put("dateTimeFromat",new DateFormat().getDAY_SECOND());
        map.put("dateTime", SessionManager.getUserDate(new DateFormat().getDAY_SECOND()));
        map.put("time",String.valueOf(new Date().getTime()));
        return JsonTool.toJson(map);
    }
}
