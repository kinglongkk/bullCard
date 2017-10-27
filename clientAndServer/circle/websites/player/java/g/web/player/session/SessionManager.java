package g.web.player.session;

import org.soul.model.security.privilege.po.SysUser;
import g.model.player.po.UserPlayer;
import g.web.common.SessionManagerCommon;

import java.util.Date;
import java.util.List;

/**
 * Created by tony on 15-4-29.
 */
public class SessionManager extends SessionManagerCommon {

    public static final String SESSION_MASTER_INFO = "SESSION_MASTER_INFO";
    //玩家中心-上一次提交存款订单时间
    public static final String SESSION_RECHARGE_LAST_TIME = "SESSION_RECHARGE_LAST_TIME";
    //玩家中心-提交存款订单次数
    public static final String SESSION_RECHARGE_COUNT = "SESSION_RECHARGE_COUNT";

    private static final String SESSION_EMAIL_CODE = "SESSION_EMAIL_CODE";

    //玩家中心-填写真实姓名的值,以确认真实姓名是否一致
    private static final String S_REAL_NAME = "S_REAL_NAME";
    //刷新API余额的最新时间
    public static final String SESSION_REFRESH_API_BALANCE_TIME = "SESSION_REFRESH_API_BALANCE_TIME";
    // 玩家userPlayer 信息
    public static final String SESSION_USER_PLAYER = "SESSION_USER_PLAYER";
    // 玩家userPlayer取款密码key信息
    public static final String SESSION_USER_PLAYER_KEY = "SESSION_USER_PLAYER_KEY";


    public static final String SESSION_RECHARGE_AMOUNT = "SESSION_RECHARGE_AMOUNT";

    public static void setMasterInfo(SysUser user) {
        setAttribute(SESSION_MASTER_INFO, user);
    }

    public static SysUser getMasterInfo() {
        return (SysUser) getAttribute(SESSION_MASTER_INFO);
    }

    /**
     * 上一次提交存款订单时间
     *
     * @param date
     */
    public static void setRechargeLastTime(Date date) {
        setAttribute(SESSION_RECHARGE_LAST_TIME, date);
    }

    public static Date getRechargeLastTime() {
        return (Date) getAttribute(SESSION_RECHARGE_LAST_TIME);
    }

    /**
     * 5分钟内存款订单次数
     *
     * @param count
     */
    public static void setRechargeCount(int count) {
        setAttribute(SESSION_RECHARGE_COUNT, count);
    }

    public static int getRechargeCount() {
        return getAttribute(SESSION_RECHARGE_COUNT) == null ? 0 : (int) getAttribute(SESSION_RECHARGE_COUNT);
    }

    public static void setEmailCode(List emailParam) {
        setAttribute(SESSION_EMAIL_CODE, emailParam);
    }

    public static List getEmailCode() {
        return (List) getAttribute(SESSION_EMAIL_CODE);
    }

    /**
     * 填写真实姓名，以便第二步确认真实姓名
     *
     * @param realName
     */
    public static void setRealName(String realName) {
        setAttribute(S_REAL_NAME, realName);
    }

    public static String getRealName() {
        return (String) getAttribute(S_REAL_NAME);
    }

    /**
     * 后台生成随机数，以便防止绕过密码验证
     *
     * @param confirmKey
     */
    public static void setConfirmKey(String confirmKey) {
        setAttribute(SESSION_USER_PLAYER_KEY, confirmKey);
    }

    public static String getConfirmKey() {
        return (String) getAttribute(SESSION_USER_PLAYER_KEY);
    }

    /**
     * 移出玩家中心获取邮箱验证码
     * <p>
     * add by eagel on 20160121
     */
    public static void removeSessionEmailCodeKey() {
        removeAttribute(SESSION_EMAIL_CODE);
    }

    /**
     * 刷新api时间
     *
     * @param date
     */
    public static void setLastRefreshApiBalanceTime(Date date) {
        if (date == null) {
            date = new Date();
        }
        setAttribute(SESSION_REFRESH_API_BALANCE_TIME, date);
    }

    public static Date getLastRefreshApiBalanceTime() {
        Object obj = getAttribute(SESSION_REFRESH_API_BALANCE_TIME);
        if (obj != null) {
            return (Date) obj;
        }
        return null;
    }

    public static UserPlayer getSessionUserPlayer() {
        return (UserPlayer)getAttribute(SESSION_USER_PLAYER);
    }

    public static void setSessionUserPlayer(UserPlayer userPlayer){
        setAttribute(SESSION_USER_PLAYER,userPlayer);
    }

    public static void setRechargeAmount(Double amount){
        setAttribute(SESSION_RECHARGE_AMOUNT,amount);
    }

    public static Double getRechargeAmount(){
        return (Double)getAttribute(SESSION_RECHARGE_AMOUNT);
    }
    public static void setQrcode(String key,byte[] qrcode) {
        setAttribute(key, qrcode);
    }
    public static byte[] getQrcode(String key) {
        return (byte[]) getAttribute(key);
    }
    public static void removeQrcode(String key) {
        removeAttribute(key);
    }
}
