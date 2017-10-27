package g.service.gameroom;

import g.common.tool.DateTimeTool;
import g.data.gameroom.PlayerAiControlMapper;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.vo.PlayerAiControlListVo;
import g.model.gameroom.vo.PlayerAiControlVo;
import g.model.room.po.VRoom;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.data.json.JsonTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.spring.utils.SpringTool;
import org.soul.service.support.BaseService;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.*;


/**
 * Ai玩家设置服务
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
//region your codes 1
public class PlayerAiControlService extends BaseService<PlayerAiControlMapper, PlayerAiControlListVo, PlayerAiControlVo, PlayerAiControl, Integer> implements IPlayerAiControlService {

    public final static String CHANNEL_AI_CONTROL = "aiControl";

    @Override
    public List<Integer> getAiControlIds(Integer roomId) {
        Criteria criteria = Criteria.and(
                Criteria.add(PlayerAiControl.PROP_ROOM_ID, Operator.EQ,  roomId)
        );
        List<PlayerAiControl> list = this.mapper.search(criteria);

        List<Integer> ids = new ArrayList<Integer>();
        for(PlayerAiControl c : list){
            ids.add(c.getId());
        }
        return ids;
    }

    @Override
    public String getTodayBatchId(Integer gameRoomId) {
        Map<String,Object> map = new HashMap<String,Object>();
        //今天开始时间
        map.put("begintime", DateTimeTool.getTodayStart());
        //今天结束时间
        map.put("endtime",DateTimeTool.getTodayEnd());
        //房间ID
        map.put("gameRoomId",gameRoomId);
        return this.mapper.getTodayBatchId(map);
    }

    @Override
    public void stopAi(Integer roomId,String username) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roomId",roomId);
        map.put("username",username);
        this.mapper.stopAi(map);
    }

    /**
     * 获取房间AI数、玩家数、盈亏(未取)
     * @param roomId
     * @return
     */
    public Map getRoomMessage(Integer roomId) {

        JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
        String key = CacheKey.getCacheKey("room", "r", roomId);
        // TODO: 2017/3/2 房间盈亏未取
        List<String> result = template.hmget(key, VRoom.PROP_CURRENT_AI_COUNT, VRoom.PROP_CURRENT_PLAYER_COUNT);
        if (result != null && !result.isEmpty()) {
            Map map = new HashMap();
            map.put(VRoom.PROP_CURRENT_AI_COUNT, result.get(0));
            map.put(VRoom.PROP_CURRENT_PLAYER_COUNT, result.get(1));
            return map;
        }
        return null;
    }

    /**
     * 发布AI调控消息
     * @param aiControl
     * @return
     */
    public void publishAiPlayerMessage(final PlayerAiControl aiControl) {

        JedisTemplate template = (JedisTemplate) SpringTool.getBean("jedisTemplateGame");
        template.execute(new JedisTemplate.JedisAction<Object>() {
            @Override
            public Object action(Jedis jedis) {
                jedis.publish(CHANNEL_AI_CONTROL, JsonTool.toJson(aiControl));
                return null;
            }
        });
    }

    @Override
    public PlayerAiControl getPlayerAiControl(Integer roomId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("roomId",roomId);
        return this.mapper.getPlayerAiControl(map);
    }

    @Override
    public PlayerAiControl getDefaultPlayerAiControl(Integer roomId) {
        return this.mapper.getDefaultPlayerAiControl(roomId);
    }

}