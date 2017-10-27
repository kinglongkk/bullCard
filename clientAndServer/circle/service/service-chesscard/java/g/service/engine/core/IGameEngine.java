package g.service.engine.core;

import org.springframework.context.Lifecycle;

/**
 * Created by Double on 2016/9/29.
 * 游戏引擎
 */
public interface IGameEngine extends Lifecycle {

    /**
     * 初始化
     */
    void init();

}

