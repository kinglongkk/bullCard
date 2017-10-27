package g.model.common;

/**
 * Created by Double on 3/8/17.
 * Redis有关的发布与订阅的主题Key 存放的位置
 */
public interface RedisPubSubKey {

    /**
     * 玩家踢出
     */
    String USER_KICK_OUT = "user:kick_out";

    /**
     * 游戏公告
     */
    String CHANNEL = "gameAnnouncement";
}
