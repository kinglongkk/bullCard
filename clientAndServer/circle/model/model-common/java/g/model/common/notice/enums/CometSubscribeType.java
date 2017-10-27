package g.model.common.notice.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.model.msg.notice.enums.ICometSubscribeType;

/**
 * @author Kevice
 * @time 7/29/15 9:16 AM
 */
public enum CometSubscribeType implements ICometSubscribeType {

    SYS_ANN("SYS_ANN", "系统公告"),
    GAME_ANN("GAME_ANN", "游戏公告"),
    SITE_ANN("SITE_ANN", "站点公告"),
    TASK_REMINDER("MCENTER_TASK_REMINDER","任务提醒"),
    TASK_PAY_EX("MCENTER_PAY_EX","账户异常任务提醒"),
    TASK_PAY_REMINDER("MCENTER_TASK_PAY","账户任务提醒"),
    TASK_RANK_INADEQUATE("TASK_RANK_INADEQUATE","层级账户不足弹窗提醒"),
    TASK_PAY("MCENTER_TASK_COUNT","账户任务提醒"),
    PROFIT("MCENTER_PROFIT","盈利上限提醒"),
    RANKINADEQUATE("MCENTER_RANKINADEQUATE","层级账户不足提醒"),
    MCENTER_PLAYER_AUDIO("MCENTER_PLAYER_AUDIO","声音提醒"),
    READ_COUNT("MCENTER_READ_COUNT","消息提醒"),
    MCENTER_RECHARGE_REMINDER("MCENTER_RECHARGE_REMINDER","存款审核提醒");

    CometSubscribeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTrans() {
        return desc;
    }

    public static CometSubscribeType enumOf(String code) {
        return EnumTool.enumOf(CometSubscribeType.class, code);
    }

}
