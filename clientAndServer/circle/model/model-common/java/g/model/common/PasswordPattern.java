package g.model.common;

import java.util.regex.Pattern;

/**
 * @author: tom
 * @date: 15-11-19
 */
public class PasswordPattern {

    public final static String PASSWORD_STRONG = "^(?:([a-z])|([A-Z])|([0-9])|(.)){6,}|(.)+$";

    /**
     *
     * @param matchTarget 匹配目标
     * @return
     */
    public static int matchNum(String matchTarget) {
        return Pattern.compile(PASSWORD_STRONG).matcher(matchTarget).groupCount();
    }

    public static String translate2Level(int matchNum) {
        return matchNum <= 2 ? PasswordLevelEnum.LOW.getCode():2<matchNum && matchNum <= 4 ? PasswordLevelEnum.NORMAL.getCode():PasswordLevelEnum.STRONG.getCode();
    }
}
