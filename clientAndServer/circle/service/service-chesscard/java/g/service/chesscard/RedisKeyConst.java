package g.service.chesscard;


/**
 * Created by Double on 16/9/6.
 * redis中的key前缀
 */
public class RedisKeyConst {

    public final static String M = "m"; //match
    public final static String U = "u"; //user
    public final static String D = "d"; //desk
    public final static String R = "r"; //room

    /**
     * 玩家主信息
     *
     * @type hash
     */
    public final static String USERS = "users";

    /**
     * 玩家位置信息
     *
     * @type hash
     */
    public final static String USERS_POSITION = "user_info";//

    /**
     * 断线的用户列表
     *
     * @type set
     */
    public final static String DISCONNECT = "disconnect";

    /**
     * 赛事记数器
     */
    public static final String MATCH_COUNTER = "match_counter";

    /**
     * 用户投注超时
     */
    public static final String USERS_TIMEOUT = "users_timeout";

    /**
     * 桌子投注次数
     */
    public static final String DESK_BET_COUNT = "desk_bet_count";

    /**
     * 房间信息
     * @type hash
     */
    public final static String ROOM = "room";

    /**
     * 玩法-房间映射
     * @type sorted set
     */
    public final static String MODEL_ROOM = "model_room";

    /**
     * 房间桌子--可用人数
     * @type sorted set
     */
    public final static String ROOM_DESK = "room_desk";

    /**
     * 桌子信息表
     * @type hash
     */
    public final static String DESK = "desk";

    /**
     * 桌子玩家列表
     * @type sorted set
     */
    public final static String DESK_USER = "desk_user";

    /**
     * 位置上的玩家
     * @type hash
     */
    public final static String SEAT_USER = "seat_user";

    /**
     * 桌子可用位置
     * @type set
     */
    public final static String SEAT_VOID = "seat_void";

    /**
     * 抽注单
     * @type hash
     */
    public final static String BET = "bet";

    /**
     * 投注单-临时存储
     * @type hash
     */
    public final static String BET_TEMP = "bet_temp";

    /**
     * 投注金币
     * @type hash
     */
    public final static String BET_COIN = "bet_coin";

    /**
     * 上庄列表
     * @type sorted set
     */
    public final static String DESK_DEALERS = "desk_dealers";

    /**
     * 下庄标识
     * @type string
     */
    public final static String DESK_DEALER_DOWN_FLAG = "desk_dealer_down_flag";

    /**
     * 庄家倍数
     *
     * @type sorted set
     */
    public static final String DESK_DEALER_BEI = "desk_dealer_bei";

    /**
     * 玩家倍数
     *
     * @type hash
     */
    public static final String DESK_USER_BEI = "desk_user_bei";

}
