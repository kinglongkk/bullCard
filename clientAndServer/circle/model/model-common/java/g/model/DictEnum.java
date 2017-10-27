package g.model;

import org.soul.commons.dict.IDictEnum;
import org.soul.commons.support.IModule;

/**
 * Created by longer on 6/11/15.
 * 通用字典枚举
 */
public enum DictEnum implements IDictEnum {
    //region Region
    REGION_DELTA(Module.REGION, "delta"),//洲
    REGION_REGION(Module.REGION, "region"),//国家地区
    REGION_STATE(Module.STATE, ""),//省/州
    REGION_CITY(Module.CITY, ""),//城市
    REGION_CALLING_CODE(Module.REGION, "callingcode"),// 手机号码区号
    //endregion Region
    //region common
    COMMON_RANK(Module.COMMON, "rank"),//层级
    COMMON_SEX(Module.COMMON, "sex"), // 性别
    COMMON_MONTH(Module.COMMON, "month"),//月份
    COMMON_AREA(Module.COMMON, "area"), // 省/州
    COMMON_CURRENCY(Module.COMMON, "currency"), // 货币
    COMMON_LANGUAGE(Module.COMMON, "language"),//语言
    //    COMMON_LOCAL(Module.COMMON, "local"),//语言
    COMMON_DELTA(Module.COMMON, "delta"),//洲
    COMMON_NATION(Module.COMMON, "nations"),//国家
    COMMON_PROVINCE(Module.COMMON, "province"),//省/州
    COMMON_CITY(Module.COMMON, "city"),//城市
    COMMON_ADDRESS_STATUS(Module.COMMON, "address_status"),//地址状态
    COMMON_IM_TYPE(Module.COMMON, "im_type"),//即时通讯（已废弃）
    COMMON_CONTACT_WAY_TYPE(Module.MASTER_NOTICE, "contact_way_type"),// 即时通讯
    COMMON_REMARK_TITLE(Module.COMMON, "remark_title"),//备注标题
    COMMON_CONSTELLATION(Module.COMMON, "constellation"),//星座
    COMMON_COUNTRY(Module.COMMON, "country"),
    COMMON_TIME_ZONE(Module.COMMON, "time_zone"),
    COMMON_STATUS(Module.COMMON, "status"),
    COMMON_TRANSACTION_TYPE(Module.COMMON, "transaction_type"), //交易类型
    COMMON_USER_TYPE(Module.COMMON, "user_type"), //系统用户类型
    COMMON_FUND_TYPE(Module.COMMON, "fund_type"), //系统用户类型
    COMMON_FREEZE_TIME(Module.COMMON, "freeze_time"),//冻结时间
    //endregion

    //region log
    Log_OpType(Module.Log, "op_type"), //日志操作类型
    //endregion

    //region message
    MESSAGE_MSG_TYPE(Module.MESSAGE, "msg_type"), // 消息类别
    MESSAGE_MSG_DISPLAY(Module.MESSAGE, "msg_display"),//消息展示方式
    //endregion
    //region game
    GAME_STATUS(Module.GAME, "status"),//游戏状态
    GAME_SYNCHRONIZATION_STATUS(Module.GAME, "synchronization_status"),//游戏数据同步状态
    GAME_TYPE(Module.GAME, "game_type"),//游戏类型
    //endregion

    //region player
    PLAYER_MAIL_MOBILEPHONE_STATUS(Module.PLAYER, "activation_status"), //会员邮箱实手机激活状态
    PLAYER_STATUS(Module.PLAYER, "player_status"), //玩家状态
    PLAYER_AUTOMATIC_TYPE(Module.PLAYER, "automatic_type"), //充值类型（自动/人工）
    //endregion

    //region taskschedule

    TASK_SCHEDULE_ISLOCAL(Module.TASKSCHEDULE, "isLocal"), //是否本地方法
    TASK_SCHEDULE_ISSYSTEM(Module.TASKSCHEDULE, "isSystem"), //是否内置
    TASK_SCHEDULE_ISSYNC(Module.TASKSCHEDULE, "isSync"), //是否同步
    TASK_SCHEDULE_STATUS(Module.TASKSCHEDULE, "status"), //任务状态
    //endregion

    //region fund
    FUND_RECHARGE_TYPE(Module.FUND, "recharge_type"),//充值类型
    FUND_RECHARGE_STATUS(Module.FUND, "recharge_status"),
    FUND_DRAW_PROJECT(Module.FUND, "draw_project"),//手工提存提现项目
    FUND_TRANSFER_STATE(Module.FUND, "transfer_state"),//转账状态
    //endregion

    //region withdraw
    WITHDRAW_TYPE_PARENT(Module.FUND, "withdraw_type_parent"), //提现父类型
    WITHDRAW_TYPE(Module.FUND, "withdraw_type"), //提现类型
    WITHDRAW_STATUS(Module.FUND, "withdraw_status"), //任务状态
    IS_AUDIT(Module.FUND, "is_audit"),//是否稽核

    BANKNAME(Module.COMMON, "bankname"),//银行类别
    CONTENT_DRAFT_STATUS(Module.MASTER_CONTENT, "draft_status"),//文案管理状态
    //endregion

    //region withdraw
    TRANSACTION_STATUS(Module.FUND, "transaction_status"), //代理取款审核
    //endregion

    //region content
    RANK_FEE_TYPE(Module.PLAYER, "rank_fee_type"),//层级提现类型
    PAY_ACCOUNT_STATUS(Module.PLAYER, "pay_account_status"),//收款账号状态
    FLOAT_PIC_LINK_TYPE(Module.MASTER_CONTENT, "float_pic_link_type"),//浮动图链接类型
    FLOAT_PIC_DISPLAY_IN(Module.MASTER_CONTENT, "float_pic_display_in"),//浮动图展示位置
    PAY_ACCOUNT_ACCOUNT_TYPE(Module.MASTER_CONTENT, "pay_account_account_type"), //存款账户类型
    PAY_ACCOUNT_TYPE(Module.MASTER_CONTENT, "pay_account_type"), //存款类型

    CAROUSEL_STATE(Module.MASTER_CONTENT, "carousel_state"),//轮播广告使用状态
    CHECK_STATUS(Module.MASTER_CONTENT, "check_status"),//审核状态
    //访问限制
    SETTING_SITE_CONFINE_STATUS(Module.MASTER_SETTING, "site_confine_status"),//访问限制的使用状态
    PRIVILAGE_PASS_TIME(Module.MASTER_SETTING, "privilage_pass_time"),  // 权限密码有效时间
    WARMING_TONE_PROJECT(Module.MASTER_SETTING, "warming_tone_project"), // 提示音设置
    //运营商-偏好设置
    CCENTER_REMIND_PROJECT(Module.MASTER_SETTING, "ccenter_remind_project"),   // 提醒设置
    CCENTER_WARMING_TONE_PROJECT(Module.MASTER_SETTING, "ccenter_warming_tone_project"), // 提示音设置
    //站点参数-经营地区
    SETTING_SITE_OPERATE_AREA(Module.MASTER_SETTING, "site.operate.area"),
    //限制时间类型
    SETTING_TIME_TYPE(Module.MASTER_SETTING, "time_type"),//限制时间类型
    NOTICE_REASON_TMPL_TYPE(Module.MASTER_NOTICE, "manual_event_type"),//原因模板类型
    NOTICE_REASON_TMPL_TYPE_AUTO(Module.MASTER_NOTICE, "auto_event_type"),//原因模板类型
    NOTICE_PUBLISH_METHOD(Module.MASTER_NOTICE, "publish_method"), // 发布方式代码

    //账号密保问题
    SETTING_MASTER_QUESTION1(Module.MASTER_SETTING, "master_question1"),
    SETTING_MASTER_QUESTION2(Module.MASTER_SETTING, "master_question2"),
    SETTING_MASTER_QUESTION3(Module.MASTER_SETTING, "master_question3"),
    CONTENT_CAROUSEL_TYPE(Module.MASTER_CONTENT, "carousel_type"),//内容管理-轮播广告-类别
    CONTENT_DOCUMENT_TYPE(Module.MASTER_CONTENT, "document_type"),//文案类型
    //endregion

    //region operation
    ACTIVITY_STATE(Module.MASTER_OPERATION, "activity_state"),
    CONFIRM_STATE(Module.MASTER_OPERATION, "confirm_state"),
    LSSUING_STATE(Module.MASTER_OPERATION, "lssuing_state"),//返佣状态settlement_state
    SETTLEMENT_STATE(Module.MASTER_OPERATION, "settlement_state"),
    CLAIM_PERIOD(Module.MASTER_OPERATION, "claim_period"),
    EFFECTIVE_TIME(Module.MASTER_OPERATION, "effective_time"),
    EFFECTIVE_TIME1(Module.MASTER_OPERATION, "effective_time1"),
    EFFECTIVE_TIME2(Module.MASTER_OPERATION, "effective_time2"),
    EFFECTIVE_TIME3(Module.MASTER_OPERATION, "effective_time3"),
    PROJECT_CODE(Module.MASTER_OPERATION, "project_code"),
    ACTIVITY_TYPE(Module.COMMON, "activity_type"),

    USER_STATUS(Module.PLAYER, "user_status"), //系统用户状态
    //统计报表
    GAME_ORDER_STATE(Module.GAME, "order_state"),

    //咨询
    ADVISORY_TYPE(Module.PLAYER, "advisory_type"),

    //收款账户
    PAY_CHANNEL_CODE(Module.MASTER_CONTENT, "pay_channel_code"),
    //endregion


    //域名审核
    DOMAIN_PLATFORM(Module.COMMON, "domain_platform"),
    DOMAIN_CHECK_STATUS(Module.COMMON, "domain_check_status"),

    ////////////代理中心//////////////
    FUND_RECORD_TYPE(Module.AGENT, "fund_record_type"),
    // 总代中心代理状态 by Fly
    AGENT_STATUS(Module.AGENT, "agent_status"),
    SITE_STATUS(Module.COMMON, "site_status"),
    IMPORT_PLAYERS_STATUS(Module.COMMON, "import_players_status"),
    //运营管理中服务模块的审核内容类型
    CONTENT_TYPE(Module.COMPANY_SERVE, "content_type"),
    //关闭站点的时间类型(立即生效,定时生效)
    MASTER_SETTING_CLOSE_TIME_TYPE(Module.COMPANY_SETTING, "close_site_time_type"),
    GAME_BALL_TYPE(Module.GAME,"ball_type"),
    GAME_BET_VIEW_LINK(Module.GAME,"bet_view_link"),
    GAME_BET_TYPE(Module.GAME,"bet_type"),
    BET_SETTLE_STATUS(Module.BET,"settle_status"),
    ;

    DictEnum(IModule module, String type) {
        this.module = module;
        this.type = type;
    }

    private IModule module;
    private String type;

    @Override
    public IModule getModule() {
        return module;
    }

    @Override
    public String getType() {
        return type;
    }
}
