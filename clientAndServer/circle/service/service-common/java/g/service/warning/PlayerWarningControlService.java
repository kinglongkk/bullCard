package g.service.warning;

import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;
import g.data.warning.PlayerWarningControlMapper;
import g.service.warning.IPlayerWarningControlService;
import g.model.warning.po.PlayerWarningControl;
import g.model.warning.vo.PlayerWarningControlListVo;
import g.model.warning.vo.PlayerWarningControlVo;

import java.util.List;


/**
 * 玩家实时监控控制表服务
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public class PlayerWarningControlService extends BaseService<PlayerWarningControlMapper, PlayerWarningControlListVo, PlayerWarningControlVo, PlayerWarningControl, Integer> implements IPlayerWarningControlService {
    @Override
    public PlayerWarningControl getWarningControl() {
        List<PlayerWarningControl> list = this.mapper.oneSearch("status","10");
        if(list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    public void updateWarningStatus(Integer userId) {
       this.mapper.updateWarningStatus(userId);
    }


}