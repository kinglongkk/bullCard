package g.service.chesscard.engine.core;

import g.data.bet.BetMapper;
import g.model.bet.po.BetIds;
import g.service.engine.core.IGameEngine;
import g.service.engine.core.IWsSessionKickOut;
import g.service.engine.core.WsHeartBeat;
import g.service.engine.manager.IDictManager;
import g.service.engine.manager.IGameManager;
import g.service.engine.manager.IParamManager;
import g.service.webSocket.context.IWsSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Double on 2016/9/29.
 */
public class ChessCardEngine implements IGameEngine {

    public static Date START_TIME = null;

    @Autowired
    private IGameManager chessCardManager;

    @Autowired
    private IParamManager paramManager;

    @Autowired
    private IDictManager dictManager;

    @Autowired
    private WsHeartBeat wsHeartBeat;

    @Autowired
    private IWsSessionKickOut wsSessionKickOut;

    @Override
    public void init() {
        chessCardManager.init();
        paramManager.load();
        dictManager.load();
        wsHeartBeat.start();
        wsSessionKickOut.start();
    }

    @Override
    public void start() {
        START_TIME = new Date();

    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
