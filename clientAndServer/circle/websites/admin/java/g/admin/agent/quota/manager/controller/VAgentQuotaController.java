package g.admin.agent.quota.manager.controller;

import g.admin.agent.quota.manager.form.VAgentQuotaForm;
import g.admin.agent.quota.manager.form.VAgentQuotaSearchForm;
import g.model.TransactionTypeEnum;
import g.model.admin.agent.quota.transaction.po.AgentQuotaTransaction;
import g.model.admin.agent.quota.transaction.vo.AgentQuotaTransactionVo;
import g.model.admin.agent.quotamanager.po.VAgentQuota;
import g.model.admin.agent.quotamanager.vo.VAgentQuotaListVo;
import g.model.admin.agent.quotamanager.vo.VAgentQuotaVo;
import g.service.admin.agent.message.IUserAgentManagerService;
import g.service.admin.agent.quota.manager.IVAgentQuotaService;
import org.soul.commons.locale.LocaleTool;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.Criterion;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.support._Module;
import org.soul.iservice.security.privilege.ISysUserService;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.model.security.privilege.vo.SysUserListVo;
import org.soul.model.security.privilege.vo.SysUserVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;


/**
 * 控制器
 *
 * @author black
 * @time 2016-12-12 15:27:30
 */
@Controller
//region your codes 1
@RequestMapping("/vAgentQuota")
public class VAgentQuotaController extends BaseCrudController<IVAgentQuotaService, VAgentQuotaListVo, VAgentQuotaVo, VAgentQuotaSearchForm, VAgentQuotaForm, VAgentQuota, Integer> {
//endregion your codes 1

    public static final String QUOTA_EDIT_URI = "Edit";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IUserAgentManagerService userAgentManagerService;

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/agent/quotamanager/";
        //endregion your codes 2
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
    public String edit(VAgentQuotaVo objectVo, Integer id, Model model, HttpServletRequest request, HttpServletResponse response) {

        checkResult(objectVo);
        if (id != null) {
            objectVo.getSearch().setId(id);
        }
        objectVo = getService().get(objectVo);
        model.addAttribute("command", objectVo);
        if (ServletTool.isAjaxSoulRequest(request)) {
            return getViewBasePath() + QUOTA_EDIT_URI + "Partial";
        } else {
            objectVo.setValidateRule(JsRuleCreator.create(VAgentQuotaForm.class, "result"));
            return getViewBasePath() + QUOTA_EDIT_URI;
        }
    }

    /**
     * Create Edit 保存数据的Action
     *
     * @param objectVo 继承了 BaseObjectVo的Vo
     * @param form     表单验证对象
     * @param result   表单验证结果
     * @return 执行结果
     */
    @Override
    public Map persist(VAgentQuotaVo objectVo, @FormModel("result") @Valid VAgentQuotaForm form, BindingResult result) {

        if (!result.hasErrors()) {
            //新增代理额度交易
            AgentQuotaTransactionVo transactionVo = new AgentQuotaTransactionVo();
            AgentQuotaTransaction transaction = new AgentQuotaTransaction();
            transaction.setAgentId(objectVo.getResult().getAgentId());

            SysUserListVo listVo = new SysUserListVo();
            listVo.getQuery().setCriterions(new Criterion[]{
                new Criterion(SysUser.PROP_ID, Operator.EQ, objectVo.getResult().getAgentId())
            });
            listVo = sysUserService.search(listVo);
            if (listVo.getResult().get(0).getNickname() != null) {
                transaction.setNickname(listVo.getResult().get(0).getNickname());
            } else {
                transaction.setNickname(null);
            }
            transaction.setDate(new Date());
            transaction.setQuota(objectVo.getResult().getAmount());
            transaction.setType(TransactionTypeEnum.DEPOSIT.getCode());
            transactionVo.setResult(transaction);
            objectVo.setSuccess(userAgentManagerService.insertAgentTransaction(objectVo, transactionVo));
        }else{
            objectVo.setSuccess(false);
        }
        return getVoMessage(objectVo);
    }

    /**
     * 启用/禁用代理
     * @param objectVo
     * @return
     */
    public Map delete(VAgentQuotaVo objectVo, Integer id) {

        SysUserVo userVo = new SysUserVo();
        SysUser user = new SysUser();
        user.setId(objectVo.getSearch().getAgentId());
        user.setStatus(objectVo.getSearch().getStatus());
        userVo.setResult(user);
        userVo.setProperties(SysUser.PROP_STATUS);
        boolean isSuccess = sysUserService.updateOnly(userVo).isSuccess();
        if (isSuccess) {
            objectVo.setOkMsg(LocaleTool.tranMessage(_Module.COMMON, "update.success"));
        } else {
            objectVo.setErrMsg(LocaleTool.tranMessage(_Module.COMMON, "update.failed"));
        }
        return getVoMessage(objectVo);
    }
    //region your codes 3

    //endregion your codes 3

}