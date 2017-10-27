package g.web.admin.payaccount.form;


import g.web.admin.consts.FormValidRegExps;
import g.web.admin.payaccount.controller.PayAccountController;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.validation.form.constraints.Compare;
import org.soul.commons.validation.form.constraints.Depends;
import org.soul.commons.validation.form.constraints.Remote;
import org.soul.commons.validation.form.support.Comment;
import org.soul.commons.validation.form.support.CompareLogic;
import org.soul.web.support.IForm;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;


/**
 * 收款账户表表单验证对象
 * <p/>
 * Created by loong using soul-code-generator on 2015-7-27 15:22:07
 */
//region your codes 1
public class PayAccountOnlineForm implements IForm {
//endregion your codes 1

    //region your codes 2
    /** 账户名称 */
    private String result_payName;
    /** 账号 */
    private String result_account;
    /** 停用金额 */
    private String result_disableAmount;
    /** 账户类型（1银行账户；2第三方账户）(字典表pay_account_account_type) */
    private String result_accountType;
    /** 渠道(bank表的bank_name） */
    private String result_bankCode;
    /** 累计入款次数 */
    private String result_depositDefaultCount;
    /** 累计入款金额 */
    private String result_depositDefaultTotal;
    /** 单笔存款最小值 */
    private Integer result_singleDepositMin;
    /** 单笔存款最大值 */
    private Integer result_singleDepositMax;
    /** 有效分钟数 */
    private String effectiveMinutes;
    /** 第三方接口的参数json[{column:"字段",value:"值"}] */
    private String $channelJson;

    private String $payDomain;


    /** 姓名 */
    private String result_fullName;
    @Depends(property ={"result_type"}, operator = {Operator.EQ}, value = {"1"})
    @Length(min = 2 ,max = 30)
    @Pattern(regexp = FormValidRegExps.CNANDEN ,message = "common.ACCOUNT_NAME_CHECk")
    public String getResult_fullName() {
        return result_fullName;
    }

    @NotBlank
    @Length(min = 1,max = 20)
    @Remote(message = "content.payaccount.name.exist",checkClass = PayAccountController.class,checkMethod = "checkPayName",additionalProperties ={"result.id","result.accountType"} )
    public String getResult_payName() {
        return result_payName;
    }

    @NotBlank
    @Length(min = 1,max = 20)
    //@Pattern(regexp = FormValidRegExps.DIGITS)
    @Remote(checkClass = PayAccountController.class,checkMethod = "checkChnnel",additionalProperties = {"result.bankCode"},message = "该渠道的账号已存在，请重新输入！")
    public String getResult_account() {
        return result_account;
    }

    @NotBlank
    @Pattern(regexp = FormValidRegExps.POSITIVE_INTEGER,message = "common.POSITIVE_INTEGER")
    @Max(99999999)
    public String getResult_disableAmount() {
        return result_disableAmount;
    }

    public String getResult_accountType() {
        return result_accountType;
    }


    @NotBlank
    public String getResult_bankCode() {
        return result_bankCode;
    }


    @Pattern(regexp = FormValidRegExps.ZERO_POSITIVE,message = "common.ZERO_POSITIVE")
    @Max(99999999)
    public String getResult_depositDefaultTotal() {
        return result_depositDefaultTotal;
    }


    @Pattern(regexp = FormValidRegExps.ZERO_POSITIVE_INTEGER,message = "common.ZERO_POSITIVE_INTEGER")
    @Max(99999999)
    public String getResult_depositDefaultCount() {
        return result_depositDefaultCount;
    }



    @Comment("单笔存款最小值")
    @Range(min = 1 ,max = 99999999)
    @Digits( integer = 8,fraction = 0,message = "content.payAccount.tips1")
    public Integer getResult_singleDepositMin() {
        return result_singleDepositMin;
    }



    @Comment("单笔存款最大值")
    @Range(min = 1 ,max = 99999999)
    @Digits( integer = 8,fraction = 0,message = "content.payAccount.tips1")
    @Compare(message = "content.payAccount.singleDepositMaxGTsingleDepositMin",logic = CompareLogic.GT,anotherProperty = "result_singleDepositMin")
    public Integer getResult_singleDepositMax() {
        return result_singleDepositMax;
    }


    @Pattern(regexp = FormValidRegExps.POSITIVE_INTEGER,message = "common.POSITIVE_INTEGER")
    @Max(99999999)
    @Comment("有效分钟数")
    public String getEffectiveMinutes() {
        return effectiveMinutes;
    }



    @NotBlank
    @Length(max = 400)
    public String get$channelJson() {
        return $channelJson;
    }

    @NotBlank
    @Pattern(regexp = "^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$", message = "格式错误，请输入如gamebox.zh.com格式的域名")
    public String get$payDomain() {
        return $payDomain;
    }

    public void set$payDomain(String $payDomain) {
        this.$payDomain = $payDomain;
    }

    public void set$channelJson(String $channelJson) {
        this.$channelJson = $channelJson;
    }
    public void setResult_payName(String result_payName) {
        this.result_payName = result_payName;
    }

    public void setResult_account(String result_account) {
        this.result_account = result_account;
    }

    public void setResult_disableAmount(String result_disableAmount) {
        this.result_disableAmount = result_disableAmount;
    }

    public void setResult_accountType(String result_accountType) {
        this.result_accountType = result_accountType;
    }

    public void setResult_bankCode(String result_bankCode) {
        this.result_bankCode = result_bankCode;
    }

    public void setResult_depositDefaultCount(String result_depositDefaultCount) {
        this.result_depositDefaultCount = result_depositDefaultCount;
    }

    public void setResult_depositDefaultTotal(String result_depositDefaultTotal) {
        this.result_depositDefaultTotal = result_depositDefaultTotal;
    }


    public void setResult_singleDepositMin(Integer result_singleDepositMin) {
        this.result_singleDepositMin = result_singleDepositMin;
    }

    public void setResult_singleDepositMax(Integer result_singleDepositMax) {
        this.result_singleDepositMax = result_singleDepositMax;
    }

    public void setEffectiveMinutes(String effectiveMinutes) {
        this.effectiveMinutes = effectiveMinutes;
    }

    public void setResult_fullName(String result_fullName) {
        this.result_fullName = result_fullName;
    }

//endregion your codes 2

}