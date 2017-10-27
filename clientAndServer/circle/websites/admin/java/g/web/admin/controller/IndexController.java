package g.web.admin.controller;

import g.model.common.Const;
import g.web.common.cache.ParamTool;
import g.web.common.common.controller.BaseIndexController;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.init.context.CommonContext;
import org.soul.commons.lang.DateTool;
import org.soul.commons.tree.TreeNode;
import org.soul.iservice.security.privilege.ISysResourceService;
import org.soul.model.security.privilege.po.VSysUserResource;
import org.soul.model.security.privilege.vo.SysResourceVo;
import org.soul.model.sys.po.SysParam;
import org.soul.web.locale.DateFormat;
import org.soul.web.locale.DateQuickPicker;
import org.soul.web.security.privilege.controller.SysResourceController;
import org.soul.web.session.SessionManagerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import g.web.admin.init.Config;
import g.web.admin.session.SessionManager;
import g.model.SiteParamEnum;
import g.model.UserTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by tony on 15-4-29.
 */
@Controller

public class IndexController extends BaseIndexController {

    private static final String	INDEX_URI	= "index";
    private static final String INDEX_CONTENT_URI = "index.include/content";
    private static final String INDEX_MESSAGE_URI = "index.include/Message";
    private static final String INDEX_TASK_URI = "index.include/AbstractTask";

    @Autowired
    private Config config;

    @Autowired
    private ISysResourceService sysResourceService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        return INDEX_URI;
    }

    @RequestMapping(value = "index/content")
    public String content(Integer parentId, HttpServletRequest request, HttpServletResponse response, Model model) {

        SysResourceVo o = new SysResourceVo();
        UserTypeEnum userTypeEnum = UserTypeEnum.enumOf(SessionManager.getUser().getUserType());
        if (!UserTypeEnum.isWholePerimssion(userTypeEnum)){
            o.getSearch().setUserId(SessionManager.getUserId());
        }
        o._setSiteId(CommonContext.get().getSiteId());
        o.getSearch().setSubsysCode(config.getSubsysCode());
        o.getSearch().setParentId(parentId);
        List<TreeNode<VSysUserResource>> menuNodeList = sysResourceService.getSubMenus(o);
        SysResourceController.loadLocal(menuNodeList);
        model.addAttribute("command", menuNodeList);
        return INDEX_CONTENT_URI;
    }

    /**
     * 用户所在时区时间
     */
    @RequestMapping(value = "/index/getUserTimeZoneDate")
    @ResponseBody
    public String getUserTimeZoneDate(){
        Map<String,String> map=new HashMap<>(2);
        map.put("dateTimeFromat", new DateFormat().getDAY_SECOND());
        map.put("dateTime", DateTool.formatDate(new Date(), SessionManagerBase.getLocale(), Const.TimeZone_US, new DateFormat().getDAY_SECOND()));
        map.put("time", String.valueOf(DateQuickPicker.getInstance().getNow().getTime()));
        return JsonTool.toJson(map);
    }
    @ResponseBody
    @RequestMapping(value = "/getDateAry")
    public String getDateAry(){
        //日期清單要列幾個
        Integer dateList = 14;
        String dateAry = "";
        String seperator=",";
        for(int i=0;i<dateList;i++){
            String s = DateTool.formatDate(DateTool.addDays(new Date(), i), DateTool.FMT_HYPHEN_DAY);
            if(i==dateList-1){
                seperator="";
            }
            dateAry+=s+seperator;
        }
        return dateAry;
    }
    /**
     * 获取高亮参数
     */
    @ResponseBody
    @RequestMapping(value = "/getHighlightParam")
    public Map getHighlightParam(){
        Collection<SysParam> sysParams = ParamTool.getSysParams(SiteParamEnum.YELLOW);
        Map map=new HashMap();
        for(SysParam param:sysParams){
            map.put(param.getParamCode(),param.getParamValue());
        }
        return map;
    }

    private String aaa(){
        System.out.println("aaaaaaaaaa");
        return null;
    }
}
