package g.web.player.consts;


import g.model.common.RegExpConstants;

/**
 * 表单验证正则表达式规则常量
 *
 * @author Kevice
 * @time 8/5/15 4:04 PM
 */
public interface FormValidRegExps extends RegExpConstants {
    //验证金额
    String MONEY = "^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$";
    //验证姓名（由英文和汉字组成）
    String NAME = "^([a-zA-z\\u4E00-\\u9FA5])+$";
    //验证数字
    String DIGITS = "^[0-9]*$";
    /*skeype 账号 ^[a-zA-Z][a-zA-Z0-9]{5,31}$*/
    String SKYPE = "^[a-zA-Z][a-zA-Z0-9]{5,31}$";
    //中文或英文大小写和数字
    String CNANDEN_NUMBER = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
    //銀行卡
    String BANK = "^[0-9]{10,25}$";
    //含0和正整数
    String  ZERO_POSITIVE_INTEGER="^[0-9]\\d*$";
}
