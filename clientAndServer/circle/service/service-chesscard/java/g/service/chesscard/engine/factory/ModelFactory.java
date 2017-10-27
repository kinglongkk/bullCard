package g.service.chesscard.engine.factory;

import g.model.enums.GameModelCodeEnum;
import g.service.chesscard.engine.model.Room;
import g.service.chesscard.enums.TipEnum;
import g.service.chesscard.exception.ChessCardException;
import g.service.engine.evaluator.IEvaluator;
import g.service.engine.listener.IMatchListener;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IRoomManager;
import org.soul.commons.spring.utils.SpringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longer on 2016/12/19.
 * 玩法工厂
 */
public class ModelFactory implements IModelFactory {

    //      key  roomId
    private Map<Integer, IRoomManager> roomManagerMap = new HashMap<>();

    //      key  deskId
    private Map<Integer, IDeskManager> deskManagerMap = new HashMap<>();

    //      key  deskId
    private Map<Integer, Integer> deskRoomIdMap = new HashMap<>();

    private Map<GameModelCodeEnum, IRoomManager> roomManagers;

    private Map<GameModelCodeEnum, IEvaluator> evaluators = new HashMap<>();

    @Override
    public IRoomManager getRoomManager(String modelCode) {
        GameModelCodeEnum modelCodeEnum = GameModelCodeEnum.enumOf(modelCode);
        return roomManagers.get(modelCodeEnum);
    }

    @Override
    public IRoomManager getRoomManager(Integer roomId) {
        IRoomManager roomManager = roomManagerMap.get(roomId);
        if (roomManager == null) {
            throw new ChessCardException(TipEnum.ROOM_NO_EXIST);
        }
        return roomManager;
    }

    @Override
    public IDeskManager getDeskManager(Integer deskId) {
        IDeskManager deskManager = deskManagerMap.get(deskId);
        if (deskManager == null) {
            throw new ChessCardException(TipEnum.DESK_NO_EXIST);
        }
        return deskManager;
    }

    @Override
    public void addRoomManager(Integer roomId, IRoomManager roomManager) {
        this.roomManagerMap.put(roomId, roomManager);

    }

    @Override
    public void addDeskManager(Integer roomId, Integer deskId, IDeskManager deskManager) {
        this.deskRoomIdMap.put(deskId, roomId);
        this.deskManagerMap.put(deskId, deskManager);
    }

    @Override
    public Integer getRoomId(Integer deskId) {
        return this.deskRoomIdMap.get(deskId);
    }

    public void setRoomManagers(Map<GameModelCodeEnum, IRoomManager> roomManagers) {
        this.roomManagers = roomManagers;
    }

    public void setEvaluators(Map<GameModelCodeEnum, IEvaluator> evaluators) {
        this.evaluators = evaluators;
    }

    @Override
    public IEvaluator getEvaluator(GameModelCodeEnum gameModel) {
        return evaluators.get(gameModel);
    }

    @Override
    public IMatchListener getMatchListener(Integer deskId) {
        //TODO: Double 控制粒度
        Integer roomId = getRoomId(deskId);
        IRoomManager roomManager = getRoomManager(roomId);
        Room room = roomManager.get(roomId);
        String modelCode = room.getModelCode();
        GameModelCodeEnum modelCodeEnum = GameModelCodeEnum.enumOf(modelCode);
        switch (modelCodeEnum) {
            case BET:
                return (IMatchListener) SpringTool.getBean("bull100MatchHandle");
            case GRAB:
                return (IMatchListener) SpringTool.getBean("bullBaoMatchHandle");
        }
        throw new RuntimeException("未支持的玩法.");
    }
}
