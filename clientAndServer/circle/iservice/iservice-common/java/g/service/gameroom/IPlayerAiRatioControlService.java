package g.service.gameroom;

import org.soul.iservice.support.IBaseService;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.gameroom.vo.PlayerAiRatioControlListVo;
import g.model.gameroom.vo.PlayerAiRatioControlVo;

import java.util.List;
/**
 * Ai与玩家配比设置服务接口
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
public interface IPlayerAiRatioControlService extends IBaseService<PlayerAiRatioControlListVo, PlayerAiRatioControlVo, PlayerAiRatioControl, Integer> {

    /**
     * 删除从表数据
     * @param aiRationId
     */
    int deleteAiControl(Integer aiRationId);

    /**
     * 根据id删除基础设置信息
     * @param id
     * @return
     */
    boolean deleteAiRation(Integer id);
    /**
     * 根据房间Id获取默认基础信息
     * @param aiControlId
     * @return
     */
    List<PlayerAiRatioControl> getDefaultPlayerAiRationControl(Integer aiControlId);

    /**
     * 保存
     * @param ratio
     */
    void saveRationAi(PlayerAiRatioControl ratio);
}