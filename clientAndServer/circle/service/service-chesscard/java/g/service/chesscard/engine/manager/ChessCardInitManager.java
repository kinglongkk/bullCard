package g.service.chesscard.engine.manager;

import g.model.enums.GameStatusEnum;
import g.model.room.po.VRoom;
import g.model.room.so.VRoomSo;
import g.model.room.vo.VRoomListVo;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.engine.manager.IGameManager;
import g.service.engine.manager.IRoomManager;
import g.service.room.IVRoomService;
import org.soul.commons.cache.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;

import java.util.*;

/**
 * Created by Double on 2016/9/29.
 */
public class ChessCardInitManager implements IGameManager {

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IModelFactory modelFactory;

    @Autowired
    private IVRoomService vRoomService;

    @Autowired
    private SubscriberManager subscriberManager;

    @Autowired
    private PlayerTransactionManager playerTransactionManager;

    @Override
    public void init() {
        jedisTemplateGame.flushDB();

        VRoomListVo vRoomListVo = new VRoomListVo();
        VRoomSo vRoomSo = new VRoomSo();
        vRoomSo.setStatus(GameStatusEnum.ENABLE.getCode());
        vRoomSo.setGameStatus(GameStatusEnum.ENABLE.getCode());
        vRoomSo.setGameModelStatus(GameStatusEnum.ENABLE.getCode());
        vRoomListVo.setSearch(vRoomSo);

        vRoomListVo = vRoomService.getAvailableRooms(vRoomListVo);
        List<VRoom> rooms = vRoomListVo.getResult();
        Map<Integer, Set<String>> modelRoomMap = new HashMap<>();
        Set<Integer> gameIdSet = new HashSet<>();
        for (VRoom room : rooms) {
            IRoomManager roomManager = modelFactory.getRoomManager(room.getGameModelCode());
            roomManager.init(room);
            gameIdSet.add(room.getGameId());
            Set<String> roomIds;
            if (modelRoomMap.containsKey(room.getGameModelId())) {
                roomIds = modelRoomMap.get(room.getGameModelId());
            } else {
                roomIds = new HashSet<>();
                modelRoomMap.put(room.getGameModelId(), roomIds);
            }
            roomIds.add(String.valueOf(room.getId()));
        }
        mapModelRoomRelation(modelRoomMap);
        //订阅对应游戏的公告
        subscriberManager.start(gameIdSet);
        //订阅玩家预转账信息
        playerTransactionManager.start();
    }

    /**
     * 映射：玩法与房间的关系
     *
     * @param modelRoomMap
     */
    protected void mapModelRoomRelation(Map<Integer, Set<String>> modelRoomMap) {
        for (Map.Entry<Integer, Set<String>> entry : modelRoomMap.entrySet()) {
            jedisTemplateGame.sadd(
                    CacheKey.getCacheKey(RedisKeyConst.MODEL_ROOM, entry.getKey())
                    , entry.getValue().toArray(new String[entry.getValue().size()])
            );
        }
    }

    @Override
    public void destroy() {

    }


}
