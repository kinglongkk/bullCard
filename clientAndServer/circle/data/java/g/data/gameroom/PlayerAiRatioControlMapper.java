package g.data.gameroom;

import g.model.gameroom.po.PlayerAiRatioControl;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;


/**
 * Ai与玩家配比设置数据访问对象
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
public interface PlayerAiRatioControlMapper extends IBaseMapper<PlayerAiRatioControl, Integer> {
    /**
     *
     * 根据房间ID获取默认AI基础设置信息
     * @param aiControlId
     * @return
     */
    List<PlayerAiRatioControl> getDefaultPlayerAiRationControl(Integer aiControlId);

}