package g.admin.sys.controller;

import g.admin.sys.form.SysSiteForm;
import g.admin.sys.form.SysSiteLogoForm;
import g.admin.sys.form.SysSiteSearchForm;
import g.model.common.Const;
import g.model.sys.po.SysSite;
import g.model.sys.vo.SysSiteListVo;
import g.model.sys.vo.SysSiteVo;
import g.service.sys.ISysSiteService;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.cache.locale.LocaleTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.GenericTool;
import org.soul.commons.net.ServletTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by black on 2016/9/12.
 */

@Controller
@RequestMapping("/sys/site")
public class SysSiteController extends BaseCrudController<ISysSiteService, SysSiteListVo, SysSiteVo, SysSiteSearchForm, SysSiteForm, SysSite, Integer> {

    @Autowired
    private ISysSiteService sysSiteService;

    protected String getViewBasePath() {
        return "/sys/site/";
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
    @Override
    public String edit(SysSiteVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {
        objectVo.getSearch().setId(Const.Default_Site_Id);
        objectVo = doEdit(objectVo, model);
        model.addAttribute("command", objectVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return "sys/site/Edit" + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(GenericTool.getSuperClassGenricType(getClass(), 4), "result"));
            return "sys/site/Edit";
        }
    }

    /**
     * 上传图标--打开页面
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUploadLogoPortrait")
    public String toUploadLogoPortrait(SysSiteVo vo, Model model) {
        //表单校验
        model.addAttribute("command", vo);
        model.addAttribute("validate", JsRuleCreator.create(SysSiteLogoForm.class));
        return this.getViewBasePath() + "UploadLogoPortrait";
    }

    /**
     * 上传图标--保存
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/uploadLogoPortrait")
    @ResponseBody
    public Map uploadLogoPortrait(SysSiteVo vo) {
        Map map = MapTool.newHashMap();
        List<String> properties = ListTool.newArrayList();
        properties.add(SysSite.PROP_LOGO_PATH);
        vo.getResult().setId(Const.Default_Site_Id);
        vo.setProperties(properties.toArray(new String[1]));
        boolean success;
        success = ServiceTool.sysSiteService().updateOnly(vo).isSuccess();
        map.put("state", success);
        if (success) {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

}