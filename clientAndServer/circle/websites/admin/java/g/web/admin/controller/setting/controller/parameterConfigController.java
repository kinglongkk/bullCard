package g.web.admin.controller.setting.controller;

import g.web.admin.tools.ServiceTool;
import g.model.SiteParamEnum;
import g.web.common.cache.ParamTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.support._Module;
import org.soul.model.sys.vo.SysParamListVo;
import org.soul.model.sys.vo.SysParamVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/parameterConfig")
public class parameterConfigController {

    private static final String INDEX_URL = "/setting/paramConfig/Index";
    private static final String EDIT_URL = "/setting/paramConfig/Edit";

    /**
     * 参数配置列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        SysParamListVo listVo = ServiceTool.preferenceService().searchParamConfig(new SysParamListVo());
        model.addAttribute("command", listVo);
        return INDEX_URL;
    }

    @RequestMapping("/indexs")
    public String indexs(Model model,HttpServletRequest request) {
        SysParamListVo listVo = ServiceTool.preferenceService().searchParamConfigs(new SysParamListVo());
        model.addAttribute("command", listVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "/setting/paramConfig/IndexPartial";
        } else {
            return INDEX_URL;
        }

    }

    /**
     * 参数配置修改弹窗
     * @return
     */
    @RequestMapping("/toConfigEdit")
    public String toConfigEdit(Model model,SysParamVo sysParamVo) {
        sysParamVo = ServiceTool.preferenceService().searchSysParamById(sysParamVo);
        model.addAttribute("sysParam", sysParamVo);
        return EDIT_URL;
    }

    /**
     * 参数配置修改
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Map configEdit(SysParamVo vo) {
        Boolean bool = ServiceTool.preferenceService().configEdit(vo);
        if (bool) {
            vo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "update.success"));
            this.refreshSysParam();
        } else {
            vo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "update.fail"));
        }
        HashMap map = new HashMap(2);
        map.put("msg", StringTool.isNotBlank(vo.getOkMsg()) ? vo.getOkMsg() : vo.getErrMsg());
        map.put("state", Boolean.valueOf(vo.isSuccess()));
        return map;
    }

    @RequestMapping("/defaultParam")
    @ResponseBody
    public Map defaultParam(String dataIds){
        String[] ids = dataIds.split(",");
        HashMap map = new HashMap(2);
        for(String id:ids){
            SysParamVo sysParamVo = new SysParamVo();
            sysParamVo.getSearch().setId(Integer.valueOf(id));
            try {
                ServiceTool.sysAnnouncementService().updateDefaultParam(sysParamVo);
            }catch (Exception e){
                map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "update.fail"));
                map.put("state", false);
                return map;
            }
        }
        map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "update.success"));
        map.put("state", true);
        return map;
    }
    /**
     * 刷新缓存
     */
    private void refreshSysParam() {
        ParamTool.refresh(SiteParamEnum.SYS_BET_MIN);
        ParamTool.refresh(SiteParamEnum.SYS_WIN_MAX);
    }

}
