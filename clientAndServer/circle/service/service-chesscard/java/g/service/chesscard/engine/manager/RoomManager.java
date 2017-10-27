package g.service.chesscard.engine.manager;

import g.model.admin.gameroomconfig.po.GameRoomConfigBull100;
import g.model.admin.gameroomconfig.so.GameRoomConfigBull100So;
import g.model.admin.gameroomconfig.vo.GameRoomConfigBull100ListVo;
import g.model.room.po.VRoom;
import g.service.admin.gameroomconfig.IGameRoomConfigBull100Service;
import g.service.chesscard.RedisKeyConst;
import g.service.chesscard.engine.factory.IModelFactory;
import g.service.chesscard.engine.model.Desk;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.engine.task.IMatchTask;
import g.service.engine.manager.*;
import g.service.gameroom.IGameRoomService;
import org.apache.commons.beanutils.BeanUtils;
import org.soul.commons.bean.BeanTool;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Pipeline;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Double on 2016/9/29.
 * 投注类的房间管理
 */
public class RoomManager implements IRoomManager {

    private static Log log = LogFactory.getLog(RoomManager.class);

    @Autowired
    private JedisTemplate jedisTemplateGame;

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    protected IGameRoomService gameRoomService;

    @Autowired
    private IGameRoomConfigBull100Service gameRoomConfigBull100Service;

    @Autowired
    private IPlayerManager playerManager;


    @Autowired
    protected IDeskManager deskManager;

    @Autowired
    private IModelFactory modelFactory;

    @Autowired
    private IBetManager betManager;

    private ConcurrentHashMap<Integer,Room> concurrentHashMap = new ConcurrentHashMap();

    public void setDeskManager(IDeskManager deskManager) {
        this.deskManager = deskManager;
    }

    @Override
    public void init(VRoom vRoom) {
        modelFactory.addRoomManager(vRoom.getId(), this);
        this.setById(vRoom);
        this.mergeConfig(vRoom);
        Room room = (Room) SpringTool.getBean("room");
        room.setMo(vRoom);
        concurrentHashMap.put(room.getId(),room);
        deskManager.init(room);
    }

    /**
     * 合并参数
     *
     * @param room
     */
    private void mergeConfig(VRoom room) {
        // Stone game_room_config_bull100，将前房间参数合并到该房间的Redis数据中

        GameRoomConfigBull100ListVo roomVo = new GameRoomConfigBull100ListVo();
        GameRoomConfigBull100So roomSo = new GameRoomConfigBull100So();

        roomSo.setRoomId(room.getId()); // 设置查询参数

        roomVo.setSearch(roomSo); // 设置查询对象

        gameRoomConfigBull100Service.search(roomVo);

        List<GameRoomConfigBull100> rs = roomVo.getResult();

        if (rs != null && rs.size() > 0) {
            jedisTemplateGame.hmset(
                    CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, room.getId()),
                    BeanTool.extractNoNull(rs.get(0)));
        }

    }

    private VRoom mapToObject(Map<String, String> map) {
        VRoom obj = new VRoom();
        try {
            BeanUtils.populate(obj, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void destroy() {

    }

    @Override
    public List<VRoom> getRoomsByModelId(Integer modelId) {
        Set<String> roomIds = this.jedisTemplateGame.smembers(CacheKey.getCacheKey(RedisKeyConst.MODEL_ROOM, modelId));
        List objects = jedisTemplateGame.execute(new JedisTemplate.PipelineAction() {
            @Override
            public List<Object> action(Pipeline pipeline) {
                for (String roomId : roomIds) {
                    pipeline.hgetAll(
                            CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, roomId)
                    );
                }
                return null;
            }
        });

        List vRooms = new ArrayList<>(objects.size());
        for (Object o : objects) {
            vRooms.add(mapToObject((Map) o));
        }
        return vRooms;
    }


    /**
     * 退出房间
     *
     * @param userId
     * @return
     */
    @Override
    public void exit(Integer userId) {
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        if(deskId == null){
//            player.destroy();
            return;
        }
        Desk desk = deskManager.byId(deskId);
        IMatchTask matchTask = desk.getMatchTask();
        if (matchTask.isSettled() ||
                !player.getId().equals(desk.getDealerId()) &&
                        betManager.getBetCount(deskId, userId) == 0) {
            // tony 在上庄列表 直接执行下庄处理
            dealerManager.downDealer(player );
            desk.outDesk(userId);
            player.outRoom();
        } else {
            log.warn("玩家:{0},退出房间失败,由于他已经参与与游戏.");
            player.setAbnormalOutGame(true);
            desk.addDisconnectUser(userId);
            if (desk.isDealer(userId)) {
                dealerManager.setDownDealerFlag(userId, deskId);
            }
        }
    }

    @Override
    public Room get(Integer roomId) {
        return concurrentHashMap.get(roomId);
    }

    @Override
    public void setById(VRoom room) {
        jedisTemplateGame.hmset(
                CacheKey.getCacheKey(RedisKeyConst.ROOM, RedisKeyConst.R, room.getId()),
                BeanTool.extractNoNull(room));
    }
}
