package g.service.chesscard.enums;

import org.soul.commons.enums.EnumTool;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by lenovo on 2016/9/21.
 */
public enum TipEnum implements ICodeEnum {

    SUCCESS("0", "success"),
    SERVER_ERROR("00001", "服务器忙"),
    NOT_MATCH("00002", "没有赛事"),
    NOT_BET("00003", "没有注单"),
    TIME_OUT("00004", "因长时间无操作，已离开游戏！"),
    KICK_OUT("00005", "您的账号在其他设备登陆，请妥善保管自己的账号！"),

    //登录相关
    LOGIN_NO("10001", "未登录，请登录"),
    LOGIN_FAIL("10002", "登录失败，请重新登录"),

    //金币相关
    BALANCE_EMPTY("200001", "您金币余额为零！"),
    BALANCE_LESS("200002", "您剩余金币不足！"),
    // 2017.01.20 UPDATE BY WINKEY  BEGIN
    BALANCE_LESS_UP_DEALER("200003", "您余额不足，无法上庄！"),// 房间下方按钮--点击"我要续庄"--提示,
    BALANCE_LESS_KEEP_DEALER("200004", "您余额不足，无法续庄！"),// 房间下方按钮--点击"我要续庄"--提示
    BALANCE_MORE("200005", "您当前剩余金币超出房间限定！"),
    // 2017.01.20 UPDATE BY WINKEY  END

    //房间|桌子|座位相关
    ROOM_NO_EXIST("300000", "房间不存在."),
    ROOM_FULL("300001", "该房间人员爆满！请换个房间吧！"),
    DESK_AT_SEAT_TIMEOUT("300002", "桌子匹配超时，请重试！"),
    PLAYER_IN_MATCH("300003", "您有未完成的牌局，请等待牌局结束！"),
    EXIT_ROOM_IN_MATCH("300004", "当前游戏未结束，退出游戏由系统代打！"),
    EXIT_ROOM_DEALER("300005", "您已做庄强制下庄，系统则继续帮您打完本局！"),
    DESK_NO_EXIST("300006", "桌子不存在！"),
    PLAYER_ALREADY_IN_SEAT("300007", "您已经在座！"),

    //投注|对战相关
    BET_MAX_LIMIT("400001", "本局下分金额达到上限！"),
    BET_MAX_COUNT("400002", "您本局下分次数达到上限！"),
    BET_MAX_COIN("400003", "您本局下分金额达到上限！"),
    BET_NOT_PERMIT_COIN("400004", "下分金额为非许可值！"),
    BET_NOT_PERMIT_BET("400005", "非法下注类型！"),
    BET_MAX_BY_BANKER_LIMIT("400006", "当局投注已达上限！"),//庄家金额不足,闲家无法投注

    //赛事相关
    MATCH_NO_EXIST("500001", "赛事不存在"),

    //上庄相关
    NOT_DO_DEALER("600001", "不能上庄!"),
    MIN_LIMIT_DEALER("600002", "当前房间最低上庄金额限制!"),
    IS_DEALER("600003", "已经是庄家!"),
    IN_DEALER_LIST("600004", "已经是上庄列表中!"),
    NOT_DOWN_DEALER("600005", "游戏已经开始,请等待游戏结束后下庄!"),
    WARNING_DEALER("600007", "友情提示: 您的上庄资金不足80%，请及时续庄！"),
    WARNING_KEEP_DEALER("600008", "您庄家资金不足，是否需要续庄！最低续庄金额{0}"),
    WARNING_DOWN_DEALER("600009", "您已下庄！"),
    SUCCESS_UP_DEALER("600010", "恭喜您，成为本局庄家!"),
    SUCCESS_KEEP_DEALER("600011", "续庄成功!"),
    SUCCESS_DOWN_DEALER("600012", "您已下庄!"),
    DEALER_UP_NOT_PERMIT_COIN("600013", "上庄金额为非许可值!"),
    DEALER_KEEP_NOT_PERMIT_COIN("600014", "续庄金额为非许可值!"),
    IS_DEALER_NOT_BETTING("600015", "您当期是庄家,不能自己下注!"),
    // 2017.01.20 UPDATE BY WINKEY  BEGIN
    DOWN_DEALER_FROM_ROOM("600016", "您已在庄，下庄系统将帮您打完本局！"),// 房间下方按钮--点击"我要下庄"--提示
    DOWN_DEALER_FROM_DEALER_LIST("600017", "您已从上庄列表退出竞庄！"),// 房间--上庄列表--点击“我要下庄”--提示
    DOWN_DEALER_BECAUSE_BALANCE_LESS("600018", "您剩余金币不足,已下庄!"),// 庄家由于金币不足下庄
    DOWNING_DEALER_ACTION("600019", "您已下庄！"),
    // 2017.01.20 UPDATE BY WINKEY  END
    ;

    private String code;
    private String trans;

    TipEnum(String code, String trans) {
        this.code = code;
        this.trans = trans;
    }

    public String getTrans() {
        return trans;
    }

    public String getCode() {
        return code;
    }

    public static TipEnum enumOf(String code) {
        return EnumTool.enumOf(TipEnum.class, code);
    }


}
