package g.web.admin.controller.setting.controller;

import g.web.admin.tools.ServiceTool;
import g.model.DictEnum;
import g.model.setting.vo.PreferenceVo;
import g.web.common.cache.ParamTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.support._Module;
import org.soul.model.sys.vo.SysParamVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import g.model.SiteParamEnum;

import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/preferenceSetting")
@RequestMapping("/param")
public class PreferenceController {

    private static Log log = LogFactory.getLog(PreferenceController.class);

    /**
     * 偏好设置
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        refreshSysParam();
        // 权限密码设置时间
        model.addAttribute("privilagePassTime", ParamTool.getSysParam(SiteParamEnum.SETTING_PRIVILAGE_PASS_TIME));
        model.addAttribute("privilagePassMap", DictTool.get(DictEnum.PRIVILAGE_PASS_TIME));
        // 提示音设置
        model.addAttribute("warmToneMap", DictTool.get(DictEnum.WARMING_TONE_PROJECT));
        model.addAttribute("toneWarm", ParamTool.getSysParam(SiteParamEnum.WARMING_TONE_WARM));
        model.addAttribute("toneNotice", ParamTool.getSysParam(SiteParamEnum.WARMING_TONE_NOTICE));

        return "/setting/preferences/Index";
    }

    /**
     * 保存偏好设置
     * @param preferenceVo
     * @return
     */
    @RequestMapping("/savePreference")
    @ResponseBody
    public Map savePreference(PreferenceVo preferenceVo) {
        HashMap map = new HashMap(2);
        boolean result = ServiceTool.preferenceService().savePreference(preferenceVo);
        if (result) {
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "save.success"));
            refreshSysParam();
        } else {
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "save.failed"));
        }
        map.put("state", Boolean.valueOf(result));
        return map;
    }

    /**
     * 提示音修改页面
     * @param paramCode 提示音参数code
     * @param id  id大于0表示修改；等于0表示新增
     * @param model
     * @return
     */
    @RequestMapping("/editTone")
    public String editTone(String paramCode,Integer id,Model model) {
        refreshSysParam();
        switch (paramCode) {
            case "warm":
                model.addAttribute("sysTones", ParamTool.getSysParams(SiteParamEnum.SYS_TONE_WARM));
                model.addAttribute("tone", ParamTool.getSysParam(SiteParamEnum.WARMING_TONE_WARM));
                break;
            case "notice":
                model.addAttribute("sysTones", ParamTool.getSysParams(SiteParamEnum.SYS_TONE_NOTICE));
                model.addAttribute("tone", ParamTool.getSysParam(SiteParamEnum.WARMING_TONE_NOTICE));
                break;
        }
        return "/setting/preferences/EditTone";
    }

    /**
     * 保存提示音设置
     * @param sysParamVo
     * @return
     */
    @RequestMapping("/uploadTone")
    @ResponseBody
    public Map uploadTone(SysParamVo sysParamVo) {
        HashMap map = new HashMap(2);
        try {
            ServiceTool.preferenceService().uploadTone(sysParamVo);
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "save.success"));
            map.put("state", Boolean.valueOf(true));
        } catch (Exception e) {
            log.error(e);
            map.put("msg", LocaleTool.tranMessage(_Module.COMMON, "save.failed"));
            map.put("state", Boolean.valueOf(false));
        }
        return map;
    }

    /**
     * 恢复系统设置
     */
    @RequestMapping("/resetPreference")
    @ResponseBody
    public Boolean resetPreference() {
        try {
            ServiceTool.preferenceService().resetPreference(new PreferenceVo());
        } catch (Exception e) {
            log.error(e);
            return false;
        }
        refreshSysParam();
        return true;
    }


    /**
     * 刷新缓存
     */
    private void refreshSysParam() {
        ParamTool.refresh(SiteParamEnum.SETTING_PRIVILAGE_PASS_TIME);
        ParamTool.refresh(SiteParamEnum.SYS_TONE_WARM);
        ParamTool.refresh(SiteParamEnum.SYS_TONE_NOTICE);
        ParamTool.refresh(SiteParamEnum.WARMING_TONE_NOTICE);
        ParamTool.refresh(SiteParamEnum.WARMING_TONE_WARM);
    }

}
