package g.admin.agent.message.controller;

import g.admin.agent.message.form.UserAgentForm;
import g.admin.agent.message.form.UserAgentLogoForm;
import g.admin.agent.message.form.UserAgentSearchForm;
import g.model.admin.agent.currency.vo.AgentCurrencyExchangeListVo;
import g.model.admin.agent.message.po.UserAgent;
import g.model.admin.agent.message.vo.UserAgentListVo;
import g.model.admin.agent.message.vo.UserAgentVo;
import g.service.admin.agent.currency.IAgentCurrencyExchangeService;
import g.service.admin.agent.message.IUserAgentService;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.cache.locale.LocaleTool;
import org.soul.commons.collections.ListTool;
import org.soul.commons.collections.MapTool;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * 代理商扩展表控制器
 *
 * @author black
 * @time 2016-11-28 16:41:26
 */
@Controller
@RequestMapping("/userAgent")
public class UserAgentController extends BaseCrudController<IUserAgentService, UserAgentListVo, UserAgentVo, UserAgentSearchForm, UserAgentForm, UserAgent, Integer> {

    @Autowired
    private IAgentCurrencyExchangeService exchangeService;

    private static final String AGENT_MESSAGE_URI = "detail.include/AgentMessage";
    private static final String AGENT_LOGO_URI = "detail.include/UploadAgentLogoPortrait";

    @Override
    protected String getViewBasePath() {
        return "/sys/agent/";
    }

    /**
     * 商户信息
     * @param objectVo
     * @param model
     * @return
     */
    @RequestMapping("/agentMessage")
    public String agentMessage(UserAgentVo objectVo, Model model){

        objectVo = getService().search(objectVo);
        model.addAttribute("command", objectVo);

        AgentCurrencyExchangeListVo listVo = new AgentCurrencyExchangeListVo();
        listVo = this.selectExchange(listVo);
        model.addAttribute("exchange", listVo);
        return getViewBasePath() + AGENT_MESSAGE_URI;
    }

    /**
     * 更新商户信息
     * @param objectVo
     * @param form
     * @param result
     * @return
     */
    @Override
    public Map persist(UserAgentVo objectVo, @FormModel("result") @Valid UserAgentForm form, BindingResult result) {

        if (!result.hasErrors()) {
            if (!StringTool.isBlank(objectVo.getResult().getMerchantKey())) {
                objectVo.setProperties(UserAgent.PROP_MERCHANT_KEY);
                objectVo.getResult().setMerchantKey(RandomStringTool.randomAlphanumeric(32));
            }else {
                objectVo.setProperties(UserAgent.PROP_MERCHANT_INDEX_LINK, UserAgent.PROP_MERCHANT_RECHARGE_LINK, UserAgent.PROP_EXCHANGE_RATE_ID);
            }
            getService().updateOnly(objectVo);
        } else {
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    /**
     * 查询货币汇率
     * @return
     */
    public AgentCurrencyExchangeListVo selectExchange(AgentCurrencyExchangeListVo listVo){

        listVo = exchangeService.search(listVo);
        return listVo;
    }

    /**
     * 上传图标--打开页面
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/toUploadLogoPortrait")
    public String toUploadLogoPortrait(UserAgentVo vo, Model model) {
        //表单校验
        model.addAttribute("command", vo);
        model.addAttribute("validate", JsRuleCreator.create(UserAgentLogoForm.class));
        return this.getViewBasePath() + AGENT_LOGO_URI;
    }

    /**
     * 上传图标--保存
     * @param vo
     * @return
     */
    @RequestMapping(value = "/uploadLogoPortrait")
    @ResponseBody
    public Map uploadLogoPortrait(UserAgentVo vo) {

        Map map = MapTool.newHashMap();
        List<String> properties = ListTool.newArrayList();
        properties.add(UserAgent.PROP_MERCHANT_LOGO);
        vo.setProperties(properties.toArray(new String[1]));
        boolean success = ServiceTool.userAgentService().updateOnly(vo).isSuccess();
        map.put("state", success);
        if (success) {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.success"));
        } else {
            map.put("msg", LocaleTool.tranMessage("common", "myAccount.updatePassword.failed"));
        }
        return map;
    }

}