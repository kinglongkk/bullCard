package g.service.engine.manager;

import g.model.room.po.RoomPool;
import g.service.chesscard.engine.model.Player;

import java.io.Serializable;

/**
 * Created by Double on 2016/9/29.
 */
public interface IRoom {

    //--------------------------

    /**
     * 获得房间于玩法代码
     *
     * @return
     */
    String getModelCode();

    /**
     * 进入房间
     *
     * @param userId
     * @return
     */
    Player into(Integer userId);


    /**
     * 房间用户数+1
     */
    void incrPlayerCount();

    /**
     * 房间AI用户数+1
     */
    void incrAiCount();

    /**
     * 房间用户数-1
     */
    void decrPlayerCount();

    /**
     * 房间Ai用户数-1
     */
    void decrAiCount();

    /**
     * 投注筹码
     */
    int[] getBetChip();


    /**
     * 最大投注次数
     */
    int getBetTimes();


    /**
     * 上庄金额
     */
    int getDealerBlance();


    /**
     * 庄家提醒金额
     */
    int getDealerBlanceTip();


    /**
     * 庄家下庄金额
     */
    int getDealerBlanceQuit();


    /**
     * Room 相关 int 类型配置
     *
     * @param fieldName
     */
    int getRoomValueInt(Serializable roomId, String fieldName);


    /**
     * 获取房间奖池数据
     * @return
     */
    RoomPool getRoomPool();

    /**
     * 增减奖池金额
     */
    public void incrJackpot(long diffCoin) ;
}
