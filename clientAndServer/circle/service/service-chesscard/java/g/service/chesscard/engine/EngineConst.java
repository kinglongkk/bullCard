package g.service.chesscard.engine;

/**
 * Created by Duoble on 2016/9/30.
 */
public interface EngineConst {

    /**
     * 会话人的座位号
     */
    int SEAT_NO_MYSELF = 0;

    /**
     * 游戏从开始到结束的时间间隔(s)
     */
    int MATCH_START_STOP_TIME_OFFSET = 15;

    /**
     * 游戏从结束到开始的时间间隔(s)
     */
    int MATCH_STOP_START_TIME_OFFSET = 15;

    /**
     * 游戏赛事:每个桌子的启动时间间隔
     */
    int EACH_DESK_TIME_POINT_OFFSET = 3;

    /**
     * 庄家的座位号
     */
    int SEAT_NO_BANKER = -1;

    /**
     * 吃瓜群众的座位号
     */
    int SEAT_NO_PLAYER = -2;

    /**
     * 庄家最小增量金币(步长)
     */
    int DEALER_MIN_INCREMENT_COIN = 10000;

    /**
     * 最大牌型倍数
     */
    int BULL_MAX_BEI = 4;

    /**
     * 抽水点数2点
     */
    Double WATER_POINT = 0.98;
}
