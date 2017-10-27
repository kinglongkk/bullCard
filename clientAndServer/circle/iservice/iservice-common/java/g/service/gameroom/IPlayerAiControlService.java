package g.service.gameroom;

import org.soul.iservice.support.IBaseService;
import g.model.gameroom.po.PlayerAiControl;
import g.model.gameroom.vo.PlayerAiControlListVo;
import g.model.gameroom.vo.PlayerAiControlVo;

import java.util.List;
import java.util.Map;


/**
 * Ai玩家设置服务接口
 *
 * @author lenovo
 * @time 2017-2-15 15:59:48
 */
public interface IPlayerAiControlService extends IBaseService<PlayerAiControlListVo, PlayerAiControlVo, PlayerAiControl, Integer> {

    List<Integer> getAiControlIds(Integer roomId);
    /**
     * 根据房间ID获取今日最大批次号
     * @param gameRoomId
     * @return
     */
    String getTodayBatchId(Integer gameRoomId);
    /**
     * 停止调控
     * @param roomId
     */
    void stopAi(Integer roomId,String username);
    /**
     * 获取房间AI数、玩家数、盈亏(未取)
     * @param roomId
     * @return
     */
    Map getRoomMessage(Integer roomId);
    /**
     * 发布AI调控消息
     * @param aiControl
     * @return
     */
    void publishAiPlayerMessage(final PlayerAiControl aiControl);
    /**
     * 跟就房间id获取调控实体
     * @param roomId
     * @return
     */
   PlayerAiControl getPlayerAiControl(Integer roomId);

    /**
     * 根据房间ID获取默认ai基础设置信息
     * @param roomId
     * @return
     */
    PlayerAiControl getDefaultPlayerAiControl(Integer roomId);
}