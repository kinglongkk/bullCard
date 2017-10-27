package g.web.player.init;

import org.soul.web.support.BaseWebConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Double
 */
public class Config extends BaseWebConf {

    @Value("${game.engine.ws.port}")
    private String wsPort;

    public String getWsPort() {
        return wsPort;
    }
}
