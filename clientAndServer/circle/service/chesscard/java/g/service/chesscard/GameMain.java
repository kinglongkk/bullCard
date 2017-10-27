package g.service.chesscard;

import g.service.engine.core.IGameEngine;
import g.service.webSocket.WebSocketServer;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.spring.utils.SpringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.TimeZone;

/**
 * 游戏的主类
 */
public class GameMain {

    private static Log log = LogFactory.getLog(GameMain.class);

    @Autowired
    private WebSocketServer webSocketServer;

    @Autowired
    private IGameEngine gameEngine;

    public GameMain() {
    }

    @SuppressWarnings("resource")
	private static void initSpring() {
       /* ClassPathXmlApplicationContext app =*/ new ClassPathXmlApplicationContext("classpath:/conf/app-appCtx.xml");
    }

    public void init() {
        log.info("游戏引擎:启动中!");
        try {
            initWebSocket();
            initEngine();
            startServer();
            log.info("游戏引擎:启动完毕!");
        } catch (Exception e) {
            log.error(e, "游戏引擎:启动异常!");
        }
    }

    private void initEngine() {
        gameEngine.init();
        gameEngine.start();
    }

    private void initWebSocket() throws Exception {
        webSocketServer.init();
    }

    private void startServer() throws Exception {
        webSocketServer.run();
    }

    public static void main(String[] args) throws Exception {
        initSpring();
        GameMain main = (GameMain) SpringTool.getBean("gameMain");
        main.init();
    }
}
