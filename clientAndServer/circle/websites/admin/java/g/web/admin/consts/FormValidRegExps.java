package g.web.admin.consts;


import g.model.common.RegExpConstants;

/**
 * 表单验证正则表达式规则常量
 *
 * @author Kevice
 * @time 8/5/15 4:04 PM
 */
public interface FormValidRegExps extends RegExpConstants {
    //含0和正整数
    String  ZERO_POSITIVE_INTEGER="^[0-9]\\d*$";
    //含0和正数
    String ZERO_POSITIVE="^[0-9].*$";
    //验证电话（验证只带数字和-）
    String TEL = "^[0-9\\-]*$";
    //验证手机（验证只带数字）
    String MOBILE = "^[0-9]*$";
    //验证正数，含小数
    String POSITIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|^[1-9]\\d*$";
    //验证IP
    String IP = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
    //验证数字，只能是数字（0-9）
    String DIGITS = "^[0-9]*$";
    //中文或英文大小写
    String CNANDEN = "^[\\u4e00-\\u9fa5a-zA-Z]+$";
    //中文或英文大小写和数字
    String CNANDEN_NUMBER = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";

    //验证金额
    String MONEY = "^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$";
    //验证链接地址http://后面的部分
    String URL_LASTPART = "^(((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(#((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&\'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$";
    String EN_ONLY = "^.[A-Za-z]+$";
    //銀行卡
    String BANK = "^[0-9]{10,25}$";

    //包含正数,0,小数
    String DECIMAL = "^(?!(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$";
    String SKYPE = "^[a-zA-Z][a-zA-Z0-9]{5,31}$";
    //中文或英文大小写和数字或空格
    String CNANDEN_NUMBER_SAPCING = "^[0-9 a-zA-Z\u4e00-\u9fa5]+$";
    //两到四个汉字
    String LIMIT_CHINESE = "^[\\u4e00-\\u9fa5]{2,4}$";
    //纯数字1-20位
    String LIMIT_MATCH = "^\\d{1,20}$";
}