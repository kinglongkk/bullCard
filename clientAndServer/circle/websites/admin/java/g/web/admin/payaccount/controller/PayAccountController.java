package g.web.admin.payaccount.controller;

import g.model.DictEnum;
import g.model.enums.*;
import g.model.payaccount.po.Bank;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.vo.BankListVo;
import g.model.payaccount.vo.PayAccountListVo;
import g.model.payaccount.vo.PayAccountVo;
import g.model.payaccount.vo.VPayAccountVo;
import g.service.payaccount.IPayAccountService;
import g.service.payaccount.PayAccountService;
import g.web.admin.payaccount.form.*;
import g.web.admin.session.SessionManager;
import g.web.admin.tools.ServiceTool;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.dict.DictTool;
import org.soul.commons.exception.SystemException;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.net.ServletTool;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.security.CryptoTool;
import org.soul.model.pay.vo.OnlinePayVo;
import org.soul.web.controller.BaseCrudController;
import org.soul.web.session.SessionManagerBase;
import org.soul.web.validation.form.annotation.FormModel;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;


/**
 * 收款帐号-mark控制器
 *
 * @author tom
 * @time 2016-7-4 16:39:31
 */
@Controller
//region your codes 1
@RequestMapping("/payAccount")
public class PayAccountController extends BaseCrudController<IPayAccountService, PayAccountListVo, PayAccountVo, PayAccountSearchForm, PayAccountForm, PayAccount, Integer> {
//endregion your codes 1
    private static final String INDEX = "payaccount/Index";

    private static final Log LOG = LogFactory.getLog(PayAccountController.class);
    @Autowired
    private PayAccountService payAccountService;
    @Override
    protected String getViewBasePath() {
        //region your codes 2
        return "/payaccount/";
        //endregion your codes 2
    }
    //region your codes 3

    @Override
    public String list(PayAccountListVo listVo, PayAccountSearchForm form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
        PayAccountListVo payAccountListVo = payAccountService.search(listVo);
        model.addAttribute("command", payAccountListVo);
        Map<String, Serializable> status = DictTool.get(DictEnum.PAY_ACCOUNT_STATUS);
        model.addAttribute("payaccountStatus", status);
        return ServletTool.isAjaxSoulRequest(request) ? INDEX + "Partial" : INDEX;
    }

    /**
     * 公司入款新增入款
     */
    @RequestMapping({"/companyCreate"})
    protected String companyCreate(PayAccountVo objectVo, Model model) {
        objectVo = super.doCreate(objectVo, model);
        objectVo.setResult(new PayAccount());
        objectVo.getResult().setType(objectVo.getSearch().getType());
        objectVo.getSearch().setType(PayAccountType.COMPANY_ACCOUNT.getCode());
        objectVo = getPayAccountVo(objectVo);
//        objectVo.setOpenAndSupportList(new ArrayList<SiteCurrency>());
//        for (SiteCurrency siteCurrency : Cache.getSiteCurrency().values()) {
//            objectVo.getOpenAndSupportList().add(siteCurrency);
//        }
        objectVo.setValidateRule(JsRuleCreator.create(PayAccountCompanyForm.class, "result"));
        model.addAttribute("command", objectVo);
        setToken(model);
        return "/payaccount/company/Add";
    }
    private void setToken(Model model) {
        String token = UUID.randomUUID().toString();
        model.addAttribute("token",token);
        SessionManager.setToken(token);
    }
    /**
     * 加载收款账户信息
     */
    private PayAccountVo getPayAccountVo(PayAccountVo objectVo) {
        //公司入款支持开通的货币
//        if (PayAccountType.COMPANY_ACCOUNT.getCode().equals(objectVo.getSearch().getType())) {
//            objectVo.setSiteCurrencyList(this.openAndSupportCurrencies(objectVo.getResult().getBankCode()));
//        } else {
//            //在线支付支持开通的货币
//            objectVo.setSiteCurrencyList(this.openAndSupport(objectVo.getResult().getBankCode()));
//        }
        objectVo.setAccountType(DictTool.get(DictEnum.PAY_ACCOUNT_ACCOUNT_TYPE));
        BankListVo bankListVo = new BankListVo();
        //编辑
        if (objectVo.getResult().getId() != null) {
            bankListVo.getSearch().setBankName(objectVo.getResult().getBankCode());
        } else {
            //新增,生成账户代号
            objectVo = this.getService().generationPayCode(objectVo);
        }
        bankListVo.getSearch().setIsUse(true);
        bankListVo.setPaging(null);
        bankListVo.getQuery().addOrder(Bank.PROP_ORDER_NUM, Direction.ASC);
        objectVo.setBankList(ServiceTool.bankService().search(bankListVo).getResult());
        //获取可用的层级列表
//        objectVo.setPlayerRankList(ServiceTool.playerRankService().queryUsableList(new PlayerRankVo()));

        return objectVo;
    }
    /**
     * 远程验证 - account唯一验证
     */
    @RequestMapping("/checkChnnel")
    @ResponseBody
    public boolean checkChnnel(@RequestParam("result.bankCode") String channel, @RequestParam("result.account") String account) {
        PayAccountListVo payAccountListVo = new PayAccountListVo();
        payAccountListVo.getSearch().setAccount(account);
        payAccountListVo.getSearch().setBankCode(channel);
        return this.getService().checkAccountUniqueUnderBankCode(payAccountListVo);
    }

    /**
     * 验证该名称是否存在
     */
    @RequestMapping("/checkPayName")
    @ResponseBody
    public String checkPayName(@RequestParam("result.payName") String payName, @RequestParam("result.accountType") String type, @RequestParam("result.id") Integer id) {
        PayAccountListVo payAccountListVo = new PayAccountListVo();
        payAccountListVo.getSearch().setPayName(payName);
        payAccountListVo.getSearch().setType(type);
        payAccountListVo.getSearch().setId(id);
        long count = this.getService().count(payAccountListVo);
        LOG.debug("账户列表有{0}个相同的账户名！", count);
        return (count == 0) + "";
    }

    /**
     * create by ke
     * @return 保存公司入款账户
     */
    @RequestMapping({"/saveCompany"})
    @ResponseBody
    public Map saveCompany(PayAccountVo payAccountVo, @FormModel("result") @Valid PayAccountCompanyForm form, BindingResult result) {
        if (!result.hasErrors()) {
            PayAccount account = payAccountVo.getResult();
            if (PayAccountAccountType.BANKACCOUNT.getCode().equals(account.getAccountType())
                    || BankCodeEnum.OTHER.getCode().equals(account.getBankCode())) {
                account.setQrCodeUrl(null);
            }
            payAccountVo.getSearch().setType(PayAccountType.COMPANY_ACCOUNT.getCode());
            account.setDepositDefaultCount(0);
            account.setAccount(PayAccountType.COMPANY_ACCOUNT.getCode().equals(account.getAccountType()) ? payAccountVo.getAccount1() : payAccountVo.getAccount2());
            account.setDepositCount(0);
            account.setDepositDefaultTotal(0.0);
            account.setDepositTotal(0.0);
            account.setCreateUser(SessionManagerBase.getUserId());
            account.setStatus(PayAccountStatusEnum.USING.getCode());
            account.setCreateTime(new Date());
            this.getService().savePayAccount(payAccountVo);
            return this.getVoMessage(payAccountVo);
        }
        return null;
    }
    /**
     * 修改账户状态
     *
     * @param vo create by ke
     */
    @RequestMapping({"/changePayStatus"})
    @ResponseBody
    public String changePayStatus(PayAccountVo vo) {
        String[] properties = new String[1];
        properties[0] = PayAccount.PROP_STATUS;
        vo.setProperties(properties);
        this.getService().updateOnly(vo);
        return "true";
    }

    @RequestMapping({"/companyEdit"})
    public String companyEdit(PayAccountVo objectVo, Integer id, Model model) {
        if (id == null || StringTool.isBlank(id.toString())) {
            throw new SystemException("加载实体时id参数必须指定！");
        }
        this.checkResult(objectVo);
        objectVo.getSearch().setId(id);
        objectVo = this.doEdit(objectVo, model);
        objectVo.getSearch().setType(PayAccountType.COMPANY_ACCOUNT.getCode());
        objectVo = getPayAccountVo(objectVo);
        objectVo.setValidateRule(JsRuleCreator.create(PayAccountCompanyEditForm.class, "result"));
        VPayAccountVo vPayAccountVo = new VPayAccountVo();
        vPayAccountVo.getSearch().setId(objectVo.getResult().getId());
        vPayAccountVo = ServiceTool.vPayAccountService().get(vPayAccountVo);
        objectVo.getSearch().setRechargeNum(vPayAccountVo.getResult().getRechargeNum() == null ? 0 : vPayAccountVo.getResult().getRechargeNum());
        objectVo.getSearch().setRechargeAmount(vPayAccountVo.getResult().getRechargeAmount() == null ? 0 : vPayAccountVo.getResult().getRechargeAmount()
        );
//        //公司入款
//        if (PayAccountType.COMPANY_ACCOUNT.getCode().equals(objectVo.getResult().getType())) {
//            objectVo.setOpenAndSupportList(this.openAndSupportCurrencies(objectVo.getResult().getBankCode()));
//        } else {
//            objectVo.setOpenAndSupportList(this.openAndSupport(objectVo.getResult().getBankCode()));
//        }
        model.addAttribute("command", objectVo);
        setToken(model);
        return "/payaccount/company/Edit";
    }
    /**
     * 更新公司入款账户
     * @return create by ke
     */
    @RequestMapping({"/updateCompany"})
    @ResponseBody
    public Map updateCompany(PayAccountVo payAccountVo, @FormModel("result") @Valid PayAccountCompanyEditForm form, BindingResult result) {
        if (!result.hasErrors()) {
            payAccountVo = this.getService().updatePayAccount(payAccountVo);
            return this.getVoMessage(payAccountVo);
        }
        return null;
    }



    @RequestMapping({"/onLineCreate"})
    public String onLineCreate(PayAccountVo objectVo, Model model) {
        BankListVo bankListVo = new BankListVo();
        bankListVo.getSearch().setType(BankEnum.TYPE_ONLINE.getCode());    // 在线支付
        bankListVo.getSearch().setIsUse(true);

        objectVo = this.doCreate(objectVo, model);
        objectVo.getSearch().setType(PayAccountType.ONLINE_ACCOUNT.getCode());
        bankListVo.setPaging(null);
        objectVo = getPayAccountVo(objectVo);
        objectVo.setBankList(ServiceTool.bankService().search(bankListVo).getResult());

        model.addAttribute("command", objectVo);
        setToken(model);
        objectVo.setValidateRule(JsRuleCreator.create(PayAccountOnlineForm.class, "result"));
        return "/payaccount/onLine/Add";
    }

    @RequestMapping({"/onLineEdit"})
    public String onLineEdit(PayAccountVo objectVo, Integer id, Model model) {
        if (id == null || StringTool.isBlank(id.toString())) {
            throw new SystemException("加载实体时id参数必须指定！");
        }
        this.checkResult(objectVo);
        objectVo.getSearch().setId(id);
        objectVo.getSearch().setType(PayAccountType.ONLINE_ACCOUNT.getCode());
        objectVo = this.doEdit(objectVo, model);
        objectVo = getPayAccountVo(objectVo);
        objectVo.setValidateRule(JsRuleCreator.create(PayAccountOnLineEditForm.class, "result"));
//        this.getPayCurrencyAndRank(objectVo);
        List<Map<String, String>> channelJson = JsonTool.fromJson(objectVo.getResult().getChannelJson(), List.class);
        if (channelJson != null) {
            for (Map<String, String> map : channelJson) {
                if (map.get("column").equals("key")) {
                    String key = map.get("value");
                    CryptoTool.aesEncrypt(key);
                    map.put("value", CryptoTool.aesDecrypt(key));
                }
            }
        }

        model.addAttribute("channelJson", channelJson);
        model.addAttribute("command", objectVo);
        setToken(model);
        return "/payaccount/onLine/Edit";
    }
    /**
     * 编辑线上支付
     * @return 编辑保存线上支付
     * create by ke
     */
    @RequestMapping({"/updateOnLine"})
    @ResponseBody
    public Map updateOnLine(PayAccountVo payAccountVo, @FormModel("result") @Valid PayAccountOnLineEditForm form, BindingResult result) {
        if (!result.hasErrors()) {
            payAccountVo = this.getService().updatePayAccountEdit(payAccountVo);
            return this.getVoMessage(payAccountVo);
        }
        return null;
    }
    /**
     * 查询在线支付接口需要的字段
     */
    @RequestMapping({"/queryChannelColumn"})
    @ResponseBody
    public PayAccountVo queryChannelColumn(OnlinePayVo onlinePayVo, PayAccountVo payAccountVo) {
        if (StringTool.isBlank(onlinePayVo.getChannelCode())) {
            return null;
        }
//        payAccountVo.setOpenAndSupportList(this.openAndSupport(onlinePayVo.getChannelCode()));
        OnlinePayVo accountList = ServiceTool.onlinePayService().getAccountList(onlinePayVo);
        payAccountVo.setPayApiParams(accountList.getAccountParams());
        return payAccountVo;
    }

    /**
     * create by ke
     * @return 保存在线支付
     */
    @RequestMapping({"/saveOnLine"})
    @ResponseBody
    public Map saveOnLine(PayAccountVo vo, @FormModel("result") @Valid PayAccountOnlineForm form, BindingResult result) {
        if (result.hasErrors()) return null;

        // 初始化vo
        initAccountVo(vo);
        // 设置支付方式
        setAccountType(vo);
        // 设置支付借口参数
        setPayParam(vo);

        this.getService().savePayAccount(vo);
        return this.getVoMessage(vo);
    }
    /**
     * 初始化vo
     * @param vo PayAccountVo
     */
    private void initAccountVo(PayAccountVo vo) {
        vo.getSearch().setType(PayAccountType.ONLINE_ACCOUNT.getCode());
        vo.getResult().setDepositDefaultCount(0);
        vo.getResult().setDepositCount(0);
        vo.getResult().setDepositDefaultTotal(0.0);
        vo.getResult().setDepositTotal(0.0);
        vo.getResult().setCreateUser(SessionManagerBase.getUserId());
        vo.getResult().setStatus(PayAccountStatusEnum.USING.getCode());
        vo.getResult().setCreateTime(new Date());
    }

    /**
     * 设置支付方式
     * @param vo PayAccountVo
     */
    private void setAccountType(PayAccountVo vo) {
        String accountType = PayAccountAccountType.THIRTY.getCode();
        // 魔宝微信支付
        if (BankCodeEnum.MOBAO_WECHAT.getCode().equals(vo.getResult().getBankCode())) {
            accountType = PayAccountAccountType.WECHAT.getCode();
        } else if (BankCodeEnum.MOBAO_ALIPAY.getCode().equals(vo.getResult().getBankCode())) {
            accountType = PayAccountAccountType.ALIPAY.getCode();
        }
        vo.getResult().setAccountType(accountType);
    }

    /**
     * 设置支付借口参数
     * @param vo PayAccountVo
     */
    private void setPayParam(PayAccountVo vo) {
        //线上支付的密钥要加密
        if (PayAccountType.ONLINE_ACCOUNT.getCode().equals(vo.getResult().getType())) {
            ArrayList<Map<String, String>> arrayList = JsonTool.fromJson(vo.getResult().getChannelJson(), ArrayList.class);
            for (Map<String, String> map : arrayList) {
                if (map.get("column").equals("key")) {
                    String key = map.get("value");
                    CryptoTool.aesEncrypt(key);
                    map.put("value", CryptoTool.aesEncrypt(key));
                }
            }
            vo.getResult().setChannelJson(JsonTool.toJson(arrayList));
        }
    }
    //endregion your codes 3

}