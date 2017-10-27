package g.model.common;

/**
 * 正则表达式常量
 * Created by Kevice on 2015/2/4.
 */
public interface RegExpConstants {

    /**
     * 手机号码
     */
    String CELL_PHONE = "^1(3[0-9]|4[57]|5[0-35-9]|7[0]|8[0-35-9])\\d{8}$";

    /**
     * QQ号码
     */
    String QQ = "^\\d{5,11}$";

    /**
     * SKYPE
     *
     * @author Longer
     */
    String SKYPE = "^[a-zA-Z][a-zA-Z0-9]{5,31}$";

    /**
     * url地址
     */
    String URL = "^(https?|s?ftp):\\/\\/(((([A-Za-z]|\\d|-|\\.|_|~|" + "[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|" + "[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|"
            + "2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([A-Za-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|" + "(([A-Za-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*"
            + "([A-Za-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([A-Za-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|" + "(([A-Za-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*"
            + "([A-Za-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|"
            + "(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)+(\\/(([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|"
            + "[!\\$&'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:"
            + "|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(#((([A-Za-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$";

    /**
     * 电话或手机号
     */
    String TEL_OR_CELL = "^0\\d{2,3}-?\\d{7,8}$|^(0\\d{2,3})-?(\\d{7,8})-?(\\d{1,4})$|^1(3[0-9]|4[57]|5[0-35-9]|7[0]|8[0-35-9])\\d{8}$";

    /**
     * 姓名（只能是汉字或英文）
     */
    String NAME = "^[a-z\\u4E00-\\u9FA5\\\\A-Z]{2,30}$";

    /**
     * 不包含特殊字符
     */
    String SPECIAL = "^[^&*=|{}<>/\\…—]*$";

    /**
     * 账号
     */
    String ACCOUNT = "^(?!0)[a-zA-Z0-9_]{4,15}$";

    /**
     * 邮箱
     */
    String EMAIL = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";

    /**
     * 邮箱或手机号
     */
    String MAIL_OR_CELL = "^([a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+)|(1(3[0-9]|5[0-35-9]|7[0]|8[0-35-9])\\d{8})$";

    /**
     * 电话号码
     */
    String TELPHONE = "^(((0\\d{2})[-](\\d{8})$)|(^(0\\d{3})[-](\\d{7,8})$)|(^(0\\d{2})[-](\\d{8})-(\\d+)$)|(^(0\\d{3})[-](\\d{7,8})-(\\d+)))$";

    /**
     * 登录密码
     *
     * @author Longer
     */
    String LOGIN_PWD = "^[A-Za-z0-9~!@#$%^&*()_+\\{\\}\\[\\]|\\:;'\"<>,./?]{7,20}$";

    /**
     * 登录账户
     *
     * @author tony
     */
    String LOGIN_USERNAME = "^[A-Za-z0-9~!@#$%^&*()_+\\{\\}\\[\\]|\\:;'\"<>,./?]{6,20}$";

    /**
     * 安全密码
     *
     * @author Longer
     */
    String SECURITY_PWD = "^[0-9]{6}$";

    /**
     * 正整数
     */
    String POSITIVE_INTEGER = "^[1-9]\\d*$";

    /**
     * IPv4地址
     *
     * @author Longer
     */
    String IPV4 = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";

    /**
     * IPv6 标准
     *
     * @author Longer(未验证)
     */
    String IPV6_STD = "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";

    /**
     * IPv6 十六进制压缩
     *
     * @author Longer(未验证)
     */
    String IPV6_HEX = "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$";


    /**
     * 字符重复
     *
     * @author Longer
     */
    String CHAR_REPEAT = "^(.)\\1+$";

    /**
     * 纯数值
     */
    String DIGIT = "^\\d+$";

    /**
     * 纯字母
     */
    String CHARACTOR = "^[a-zA-Z]+$";

    /**
     * 纯字母
     */
    String LOWCASECHARACTOR = "^[a-z]+$";


    String NICK_NAME = "^[a-zA-Z0-9\\u4E00-\\u9FA5]{3,15}$";

    String ANSWER = "^(.){1,30}$";

    /**
     * MSN
     * 修改为和邮箱相同的规则
     *
     * @author Longer
     */
    String MSN = EMAIL;
    /*密码强度*/
    String PASSWORD_LEVEL_1 = "^[a-zA-Z]+|[0-9]+$";
    String PASSWORD_LEVEL_2 = "^[0-9a-zA-Z]+$";
    String PASSWORD_LEVEL_3 = "^[A-Za-z0-9~!@#$%^&*()_+\\\\{\\\\}\\\\[\\\\]|\\\\:;\\'\\\"<>,./?]+$";

    String CONCEDE_POINTS = "^(\\-?)((?!0+(?:\\.0+)?$)(?:[1-9]{1,4}|0)(?:\\.\\d{1,2})?)(\\/?|\\-?)((?!0+(?:\\.0+)?$)(?:[1-9]{1,4}|0)(?:\\.\\d{1,2})?)?$";
    /**
     * 正数
     */
    String PLUS_QUANTITY = "^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$";
}
