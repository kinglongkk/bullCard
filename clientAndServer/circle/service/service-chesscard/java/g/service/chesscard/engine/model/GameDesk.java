package g.service.chesscard.engine.model;

import org.soul.commons.bean.IEntity;
import org.soul.commons.support.Nonpersistent;


/**
 * 游戏桌子
 *
 * @author Jason
 * @time 2016-8-25 14:17:12
 */
//region your codes 1
public class GameDesk implements IEntity<Integer> {
//endregion your codes 1

    //region your codes 3
    private static final long serialVersionUID = -8029552188744453769L;
    //endregion your codes 3

    //region property name constants
    public static final String PROP_ID = "id";
    public static final String PROP_GAME_ID = "gameId";
    public static final String PROP_GAME_MODEL_ID = "gameModelId";
    public static final String PROP_GAME_ROOM_ID = "gameRoomId";
    public static final String PROP_SEAT_NUMBER = "seatNumber";
    public static final String PROP_PLAYER_COUNT = "playerCount";
    public static final String PROP_DEALER_USER_ID = "dealerUserId";
    public static final String PROP_DEALER_COIN = "dealerCoin";
    public static final String PROP_DEALER_BALANCE_COIN = "dealerBalanceCoin";
    public static final String PROP_PRE_CARDS = "pre_cards";

    //endregion


    //region properties
    /**
     * 主键
     */
    private Integer id;
    /**
     * 所属游戏
     */
    private Integer gameId;
    /**
     * 所属游戏玩法
     */
    private Integer gameModelId;
    /**
     * 所属游戏房间
     */
    private Integer gameRoomId;
    /**
     * 座位数
     */
    private Integer seatNumber;
    /**
     * 当前玩家数量
     */
    private Integer playerCount;
    /**
     * 庄家用户id
     */
    private Integer dealerUserId;
    /**
     * 上庄剩余游戏币
     */
    private Long dealerBalanceCoin;

    //endregion
    //region constuctors
    public GameDesk() {
    }

    public GameDesk(Integer id) {
        this.id = id;
    }
    //endregion


    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getGameModelId() {
        return gameModelId;
    }

    public void setGameModelId(Integer gameModelId) {
        this.gameModelId = gameModelId;
    }

    public Integer getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(Integer gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getPlayerCount() {
        if (playerCount == null) {
            return 0;
        }
        return playerCount;
    }

    public void setPlayerCount(Integer playerCount) {
        this.playerCount = playerCount;
    }

    public Integer getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(Integer dealerUserId) {
        this.dealerUserId = dealerUserId;
    }

    public Long getDealerBalanceCoin() {
        return dealerBalanceCoin;
    }

    public void setDealerBalanceCoin(Long dealerBalanceCoin) {
        this.dealerBalanceCoin = dealerBalanceCoin;
    }

    private String prePoker;

    @Nonpersistent
    public String getPrePoker() {
        return prePoker;
    }

    public void setPrePoker(String prePoker) {
        this.prePoker = prePoker;
    }

}