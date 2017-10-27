package g.service.ai.bull.manager;

import g.data.ai.AiUserMapper;
import g.data.gameroom.PlayerAiControlMapper;
import g.data.gameroom.PlayerAiRatioControlMapper;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.room.po.VRoom;
import g.service.ai.bull.service.AiService;
import g.service.chesscard.RedisKeyConst;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.data.datasource.DatasourceTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KXZ on 2016/12/17.
 */
@Service
public class RoomMng {
    private static Log log = LogFactory.getLog(RoomMng.class);
    @Autowired
    @Qualifier("jedisTemplateGame")
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private PlayerAiControlMapper aiControlMapper;
    @Autowired
    private PlayerAiRatioControlMapper aiRatioMapper;
    @Autowired
    private AiUserMapper aiUserMapper;

    @Autowired
    private AiService aiService;

    /**
     * 订阅频道消息
     * @param channel 频道名称
     * @param jedisPubSub 频道接受者
     */
    public void subscribe(String channel, JedisPubSub jedisPubSub) {
        //订阅消息
        jedisTemplateGame.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.subscribe(jedisPubSub, channel);
                return null;
            }
        });
    }

    public List<PlayerAiControl> getRooms(){
        Criteria criteria = Criteria.add(PlayerAiControl.PROP_ROOM_ID, Operator.IN, aiService.getRoomIds())
                .addAnd(PlayerAiControl.PROP_STATUS, Operator.EQ, "10");
        List<PlayerAiControl> rs = aiControlMapper.search(criteria);
        return rs;
    }

    public List<PlayerAiRatioControl> getAiRatios(int controlId){
        Criteria criteria = Criteria.add(PlayerAiRatioControl.PROP_AI_PLAYER_CONTROL_ID, Operator.EQ, controlId);
        List<PlayerAiRatioControl> rs = aiRatioMapper.search(criteria, Order.asc(PlayerAiRatioControl.PROP_PLAYER_PROPORTION_MAX));
        return rs;
    }

    public int[] getPlayerCountAndMax(Integer roomId) {
        String key = CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, roomId);
        List<String> rs = this.jedisTemplateGame.hmget(key, VRoom.PROP_CURRENT_PLAYER_COUNT, VRoom.PROP_MAX_LIMIT_PLAYER_NUMBER);
        int[] vals = new int[rs.size()];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Integer.parseInt(rs.get(i));
        }
        return vals;
    }

    public List<long[]> getUserIds(Integer roomId){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = DatasourceTool.getBaseDatasource().getConnection();
            pstmt = connection.prepareStatement(
                    "select a.id,a.wallet_balance from user_player a,ai_user b where a.id=b.user_id and b.ai_room_id=?");
            pstmt.setObject(1, roomId);
            List<long[]> result = new ArrayList<>();
            rs = pstmt.executeQuery();
            while(rs.next()){
                result.add(new long[]{rs.getInt(1), rs.getLong(2)});
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return null;
    }

}
