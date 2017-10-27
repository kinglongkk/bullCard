package g.data.gameroom;

import g.model.gameroom.po.PlayerAiControl;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * Ai玩家设置数据访问对象
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
//region your codes 1
public interface PlayerAiControlMapper extends IBaseMapper<PlayerAiControl, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    void stopAi(Map map);

    /**
     * 根据房间ID获取今日最大的批次号
     * @param map
     * @return
     */
    String getTodayBatchId(Map map);

    /**
     * 根据房间id和生效状态获取实体
     * @param map
     * @return
     */
    PlayerAiControl getPlayerAiControl(Map map);

    /**
     * 根据房间ID获取ai默认基础设置信息
     * @param roomId
     * @return
     */
    PlayerAiControl getDefaultPlayerAiControl(Integer roomId);
}