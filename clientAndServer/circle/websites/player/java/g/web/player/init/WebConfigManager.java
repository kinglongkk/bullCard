package g.web.player.init;

import org.soul.commons.spring.utils.SpringTool;
import org.soul.web.support.BaseWebConf;

/**
 * Created by tony on 15-4-28.
 * 配置信息管理对象
 */
public class WebConfigManager {


    /**
     * 自定义配置Bean
     *
     * @return
     */
    public static Config getConfig() {
        return (Config) SpringTool.getBean("config");
    }
}