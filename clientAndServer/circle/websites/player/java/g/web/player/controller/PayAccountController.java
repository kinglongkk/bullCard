package g.web.player.controller;

import g.model.depositdesk.vo.PlayerRechargeVo;
import g.web.player.controller.form.InputAmountForm;
import org.soul.web.validation.form.js.JsRuleCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 收款帐号-mark控制器
 *
 * @author tom
 * @time 2016-7-4 16:39:31
 */
@Controller
//region your codes 1
@RequestMapping("/payAccount")
public class PayAccountController  {
//endregion your codes 1



    //region your codes 3
    //支付页面
    private static final String INPUT_AMOUNT = "app/InputAmount";

    private static final String PAYMENT_METHOD = "app/paymentMethod";
    @RequestMapping("/page/inputAmount")
    public String InputAmount(Model model,PlayerRechargeVo rechargeVo){
        rechargeVo.setValidateRule(JsRuleCreator.create(InputAmountForm.class));
        model.addAttribute("command",rechargeVo);
        return INPUT_AMOUNT;
    }

    @RequestMapping("/page/paymentMethod")
    public String paymentMethod(){
        return null;
    }
    //endregion your codes 3

}