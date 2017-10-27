package g.web.common;

import g.model.UserTypeEnum;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.enums.EnumTool;
import org.soul.commons.lang.DateTool;
import org.soul.web.locale.DateQuickPicker;
import org.soul.web.session.SessionManagerBase;
import g.model.player.po.UserPlayer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tony on 16-1-7.
 */
public class SessionManagerCommon extends SessionManagerBase {

    public static final String S_IS_REMINDER_MSG = "S_IS_REMINDER_MSG";
    public static final String S_IS_REMINDER_TASK = "S_IS_REMINDER_TASK";
    public static final String SESSION_THEME_FILE_NAME = "SESSION_THEME_FILE_NAME";
    public static final String SESSION_MASTER_INFO = "SESSION_MASTER_INFO";
    //@author: Impluse on
    //权限状态
    public static final String S_PRIVILEGE_STATUS = "S_PRIVILEGE_STATUS";
    //站长信息

    //反馈意见第一次提交时间 add by eagle
    private static final String SESSION_FIRST_COMMITTIME = "SESSION_FIRST_COMMITTIME";

    //反馈意见提交时间 add by eagle
    private static final String SESSION_COMMITTIME = "SESSION_COMMITTIME";

    //反馈意见提交次数 add by eagle
    private static final String SESSION_TIMES = "SESSION_TIMES";

    // 玩家userPlayer 信息
    public static final String SESSION_USER_PLAYER = "SESSION_USER_PLAYER";

    /**
     * 当前用户类型
     * 　21站长用户，22，总代用户，2３代理用户，2４玩家用户
     *
     * @return
     */
    public static UserTypeEnum getUserType() {
        return EnumTool.enumOf(UserTypeEnum.class, getUser().getUserType().toString());
    }

    /**
     * 获取权限密码
     * @return
     */
    public static String getPrivilegeCode() {
        return SessionManagerBase.getUser().getPermissionPwd();
    }

    /**
     * 是否提醒消息标识
     *
     * @return
     */
    public static boolean getIsReminderMsg() {
        if (getAttribute(S_IS_REMINDER_MSG) == null) {
            return false;
        } else {
            return (boolean) getAttribute(S_IS_REMINDER_MSG);
        }
    }

    public static void setIsReminderMsg(boolean isReminderMsg) {
        setAttribute(S_IS_REMINDER_MSG, isReminderMsg);
    }

    /**
     * 是否任务提醒消息标识
     *
     * @return
     */
    public static boolean getIsReminderTask() {
        if (getAttribute(S_IS_REMINDER_TASK) == null) {
            return false;
        } else {
            return (boolean) getAttribute(S_IS_REMINDER_TASK);
        }
    }
    public static void setIsReminderTask(boolean isReminderTask) {
        setAttribute(S_IS_REMINDER_TASK, isReminderTask);
    }



    /**
     * 获取用户时区当前时间
     *
     * @return
     * @author: Jerry
     */
    public static String getUserTimeZoneDate() {
        Map<String,String> map=new HashMap<>(2);
        map.put("dateTimeFromat",new org.soul.web.locale.DateFormat().getDAY_SECOND());
        map.put("dateTime", SessionManagerBase.getUserDate(new org.soul.web.locale.DateFormat().getDAY_SECOND()));
        map.put("time",String.valueOf(new Date().getTime()));
        return JsonTool.toJson(map);
    }

    /**
     * 获取session中的权限状态
     *
     * @return
     * @author: Impluse
     */
    public static Map getPrivilegeStatus() {
        Object o = getAttribute(S_PRIVILEGE_STATUS);
        if (o == null) {
            return null;
        } else {
            return (Map) o;
        }
    }


    public static void setPrivilegeStatus(Map privilegeStatus) {
        setAttribute(S_PRIVILEGE_STATUS, privilegeStatus);
    }

    public static void clearPrivilegeStatus() {
        setAttribute(S_PRIVILEGE_STATUS, null);
    }



    public static void setCommitTime(Date commitTime) {
        setAttribute(SESSION_COMMITTIME,commitTime);
    }

    public static Date getCommitTime() {
        return (Date)getAttribute(SESSION_COMMITTIME);
    }

    public static void setTimes(int times) {
        if (DateTool.minutesBetween(getDate().getNow(), getCommitTime())<=5) {
            times++;
            if (times==1) {
                setFirstCommittime(getCommitTime());
            }
        }
        setAttribute(SESSION_TIMES,times);
    }
    public static Integer getTimes() {
        return (Integer)getAttribute(SESSION_TIMES);
    }

    public static void removeSysFeedbackCommitTime() {
        removeAttribute(SESSION_COMMITTIME);
    }

    public static void removeSysFeedbackTimes() {
        removeAttribute(SESSION_TIMES);
    }

    public static void removeSysFeedbackFirstCommitTime() {
        removeAttribute(SESSION_TIMES);
    }

    public static void setFirstCommittime(Date commitTime) {
        setAttribute(SESSION_FIRST_COMMITTIME, commitTime);
    }

    public static Date getFirstCommitTime() {
        return (Date)getAttribute(SESSION_FIRST_COMMITTIME);
    }


    /**
     * 获取当前时间
     * @return
     * @author Longer
     */
    public static DateQuickPicker getDate() {
        return DateQuickPicker.getInstance();
    }


    /**
     * 根据key获取缓存验证码
     * For Example:SessionKey.S_CAPTCHA_PREFIX + CaptchaUrlEnum.CODE_PHONE.getSuffix()
     *
     * add by eagle on 20160219
     * @param key
     * @return
     */
    public static String getCaptcha(String key) {
        return (String)getAttribute(key);
    }

    public static UserPlayer getSessionUserPlayer() {
        return (UserPlayer)getAttribute(SESSION_USER_PLAYER);
    }

    public static void setSessionUserPlayer(UserPlayer userPlayer){
        setAttribute(SESSION_USER_PLAYER,userPlayer);
    }

}
