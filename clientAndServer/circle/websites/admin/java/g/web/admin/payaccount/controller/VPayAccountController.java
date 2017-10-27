package g.web.admin.payaccount.controller;

import g.model.DictEnum;
import g.model.cache.CacheBase;
import g.model.enums.PayAccountStatusEnum;
import g.model.payaccount.po.Bank;
import g.model.payaccount.po.VPayAccount;
import g.model.payaccount.vo.VPayAccountListVo;
import g.model.payaccount.vo.VPayAccountVo;
import g.service.payaccount.IVPayAccountService;
import g.web.admin.payaccount.form.VPayAccountForm;
import g.web.admin.payaccount.form.VPayAccountSearchForm;
import org.soul.commons.dict.DictTool;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.validation.form.annotation.FormModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.Map;


/**
 * 公司、线上入款账号视图控制器
 *
 * @author mark
 * @time 2016-7-13 20:37:09
 */
@Controller
//region your codes 1
@RequestMapping("/vPayAccount")
public class VPayAccountController extends BaseCrudController<IVPayAccountService, VPayAccountListVo, VPayAccountVo, VPayAccountSearchForm, VPayAccountForm, VPayAccount, Integer> {
//endregion your codes 1

    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/payaccount/";
        //endregion your codes 2
    }

    //region your codes 3
    @RequestMapping("/companyList")
    public String companyList(VPayAccountListVo listVo, @FormModel("search") @Valid VPayAccountSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        listVo.getSearch().setType("1");
        return super.list(listVo, form, result, model, request, response);
    }
    @RequestMapping("/onLineList")
    public String onLineList(VPayAccountListVo listVo, @FormModel("search") @Valid VPayAccountSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        listVo.getSearch().setType("2");
        return super.list(listVo, form, result, model, request, response);
    }
    /**
     * 收款账户查询
     * @param listVo
     * @param form
     * @param result
     * @param model
     * @return
     */
    @Override
    protected VPayAccountListVo doList(VPayAccountListVo listVo, VPayAccountSearchForm form, BindingResult result, Model model) {
        Map<String, Serializable> status = DictTool.get(DictEnum.PAY_ACCOUNT_STATUS);
        status.remove(PayAccountStatusEnum.DELETED.getCode());
        Map<String, Bank>  filterDeposit = CacheBase.getBank();
        model.addAttribute("statusMap", status);
        model.addAttribute("filterDeposit", filterDeposit.values());
        return this.getService().search(listVo);
    }
    //endregion your codes 3

}