package g.service.gameroom;

import g.data.gameroom.PlayerAiRatioControlMapper;
import g.model.gameroom.po.PlayerAiRatioControl;
import g.model.gameroom.vo.PlayerAiRatioControlListVo;
import g.model.gameroom.vo.PlayerAiRatioControlVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;

import java.util.List;


/**
 * Ai与玩家配比设置服务
 *
 * @author lenovo
 * @time 2017-2-15 14:28:52
 */
//region your codes 1
public class PlayerAiRatioControlService extends BaseService<PlayerAiRatioControlMapper, PlayerAiRatioControlListVo, PlayerAiRatioControlVo, PlayerAiRatioControl, Integer> implements IPlayerAiRatioControlService {
    @Override
    public int deleteAiControl(Integer aiRationId) {
        Criteria criteria = Criteria.and(
                Criteria.add(PlayerAiRatioControl.PROP_AI_PLAYER_CONTROL_ID, Operator.GE,  aiRationId)
        );
       return  this.mapper.batchDeleteCriteria(criteria);
    }

    @Override
    public boolean deleteAiRation(Integer id) {
        return this.mapper.delete(id);
    }

    @Override
    public List<PlayerAiRatioControl> getDefaultPlayerAiRationControl(Integer aiControlId) {
        return this.mapper.getDefaultPlayerAiRationControl(aiControlId);
    }

    @Override
    public void saveRationAi(PlayerAiRatioControl ratio) {
        this.mapper.insert(ratio);
    }

}