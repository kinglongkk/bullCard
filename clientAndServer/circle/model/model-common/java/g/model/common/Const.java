package g.model.common;

import org.soul.commons.lang.DateTool;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by longer on 9/6/15.
 */
public interface Const  extends org.soul.commons.init.context.Const{

    Integer Default_Site_Id = Integer.valueOf(1);

    Locale Default_Locale = new Locale("zh","CN");

    //默认管理员id
    Integer Default_Admin_Id = Integer.valueOf(0);

    //默认代理id
    Integer Default_Agent_Id = Integer.valueOf(1);

    TimeZone TimeZone_US = TimeZone.getTimeZone("GMT-05:00");  //美东时间
    TimeZone TimeZone_BJ = TimeZone.getTimeZone("GMT+08:00");  //北京时间
    TimeZone TimeZone_0 = TimeZone.getTimeZone("GMT+00:00");  //0区时间
    TimeZone Default_Site_TimeZone = TimeZone_BJ;


    /**
     * 默认开始
     */
    Date Platform_Begin_Date = DateTool.parseDate("1970-01-01", DateTool.FMT_HYPHEN_DAY);

    /**
     * 永久有多远
     */
    Date Platform_Forever_Date = DateTool.parseDate("3000-01-01", DateTool.FMT_HYPHEN_DAY);

    /**
     *
     */
    String FMT_HYPHEN_DAY_CLN_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 记住不再提醒回话key
     */
    String sessionRemindKey = "SESSION_REMIND_KEY";

    /**
     * 返水统计显示
     * 统计数据数据需展示所有历史数据，明细仅展示近90天的
     */
    Integer dayInterval = 90 ;

    /**
     * 临时域名可用的的天数
     */
    int Domain_Temp_Can_Use_Days = 15;

    /**
     * Redis消息线程名
     */
    String Thread_Name_Reids_Notification = "Reids_Notification_Thread";


    /**
     * 默认代理id
     * */
    Integer DEFAULT_AGENT_ID = 1;

    /**
     * 注册验证邮箱发送间隔时间
     */
    Integer REGISTER_SEND_EMAIL_INTERVAL_SECONDS = 60;
    /**
     * 找回密码 id加密 密钥
     */
    String PASSWORD_KEY = "FIND_PASSWORD_PASSWORD_KEY";

    /**
     * 页面缓存前缀
     */
    String PAGE_CACHE_PREFIX = "msites:";

    /**
     * 登录认证错误多少次后,显示验证码
     */
    Integer AUTHENTICATION_FAILURE_CAPTCHA_SHOWN_LIMIT = 3;
}
