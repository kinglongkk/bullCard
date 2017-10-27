package g.web.player.controller;

/**
 * Created by lenovo on 2016/8/26.
 */

import g.model.SiteParamEnum;
import g.model.depositdesk.po.VPlayerRecharge;
import g.model.depositdesk.vo.VPlayerRechargeListVo;
import g.model.enums.BankTypeEnum;
import g.model.enums.PayAccountStatusEnum;
import g.model.payaccount.po.PayAccount;
import g.model.payaccount.so.PayAccountSo;
import g.model.payaccount.vo.PayAccountListVo;
import g.service.common.IUserPlayerService;
import g.service.depositdesk.IPlayerRechargeService;
import g.service.depositdesk.IVPlayerRechargeService;
import g.service.payaccount.IPayAccountService;
import g.web.common.SessionManagerCommon;
import g.web.common.cache.ParamTool;
import g.web.player.init.Config;
import g.web.player.session.SessionManager;
import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.commons.locale.LocaleDateTool;
import org.soul.model.sys.po.SysParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * 存取款-控制器
 * @author longer
 */
@Controller
@RequestMapping("/finance")
public class DepositController {

    @Autowired
    private IPayAccountService payAccountService;
    @Autowired
    private IPlayerRechargeService playerRechargeService;
    @Autowired
    private IVPlayerRechargeService vPlayerRechargeService;
    @Autowired
    private IUserPlayerService userPlayerService;

    /**
     * 选择存入方式
     *
     * @return
     */
    @RequestMapping("selectRecharge")
    @ResponseBody
    public Map<String, Boolean> selectRecharge(HttpServletRequest request, HttpServletResponse response, Double amount, Model model) throws IOException {
        String s = validDepositmount(amount);
        if (StringTool.isNotEmpty(s)) {
            response.getWriter().write(s);
            return null;
        }
        Map<String, Boolean> map = new HashMap<>();
        List<PayAccount> payAccounts = getPayAccounts(amount);
        for (PayAccount payAccount : payAccounts) {
            if (payAccount.getBankCode().equals(BankTypeEnum.WECHAT.getCode()) || payAccount.getBankCode().lastIndexOf("_wx") >= 0) {//微信
                map.put(BankTypeEnum.WECHAT.getCode(), true);
            } else if (payAccount.getBankCode().equals(BankTypeEnum.ALIPAY.getCode()) || payAccount.getBankCode().lastIndexOf("_zfb") >= 0) {//支付宝
                map.put(BankTypeEnum.ALIPAY.getCode(), true);
            } else if (payAccount.getType().equals("1") && payAccount.getAccountType().equals("1")) {//公司入款银行卡
                map.put(BankTypeEnum.BANK.getCode(), true);
            }
        }
        return map;
    }

    private List<PayAccount> getPayAccounts(Double amount) {
        PayAccountListVo payAccountListVo = PayAccountListVo.getPayAccounts(amount);
        List<PayAccount> payAccounts = payAccountService.searchAvailablePayAccount(payAccountListVo);
        return payAccounts;
    }

    /**
     * 存款输入金额
     * @return
     */
    @RequestMapping("deposit")
    public String deposit(Model model){
        SysParam sysParam = ParamTool.getSysParam(SiteParamEnum.MIN_DEPOSIT_AMOUNT);
        model.addAttribute("minDepositAmount", sysParam.getParamValue());
        sysParam = ParamTool.getSysParam(SiteParamEnum.DEPOSIT_DATA_HOURS);
        model.addAttribute("depositDataHours", sysParam.getParamValue());
        return "app/finance/Deposit";
    }

    @RequestMapping(value = "/depositList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> depositList(VPlayerRechargeListVo listVo) throws ParseException {
        Date startTime = new Date();
        String paramValue = ParamTool.getSysParam(SiteParamEnum.DEPOSIT_DATA_HOURS).getParamValue();
        int i = Integer.parseInt(paramValue);
        Date endTime = DateTool.addHours(startTime, -i);
        listVo.getSearch().setStartTime(endTime);
        listVo.getSearch().setEndTime(startTime);
        listVo.getSearch().setPlayerId(SessionManager.getUserId());
        listVo = vPlayerRechargeService.search(listVo);
        Map<String, Object> map = new HashMap<>();
        map.put("paging", listVo.getPaging());
        List<Map<String, Object>> list = new ArrayList<>(listVo.getResult().size());
        for (VPlayerRecharge vPlayerRecharge : listVo.getResult()) {
            Map<String, Object> v = new HashMap<>();
            v.put("amount", vPlayerRecharge.getRechargeAmount());
            String status;
            switch (vPlayerRecharge.getRechargeStatus()) {
                case "1":
                    status = "充值中";
                    break;
                case "2":
                    status = "成功";
                    break;
                case "3":
                    status = "失败";
                    break;
                case "7":
                    status = "超时";
                    break;
                default:
                    status = "未知";
                    break;
            }
            v.put("status", status);
            v.put("order", vPlayerRecharge.getTransactionNo());
            v.put("time", LocaleDateTool.formatDate(vPlayerRecharge.getCreateTime(), LocaleDateTool.getFormat("DAY_SECOND"), SessionManagerCommon.getTimeZone()));
            list.add(v);
        }
        map.put("result", list);
        return map;
    }

    private String validDepositmount(Double amount) {
        String str = null;
        SysParam sysParam = ParamTool.getSysParam(SiteParamEnum.MIN_DEPOSIT_AMOUNT);
        if (amount < Double.parseDouble(sysParam.getParamValue())) {
            str = "存款金额不能小于" + sysParam.getParamValue() + "元.";
        }
        return str;
    }

}
