package g.service.warning;

import org.soul.iservice.support.IBaseService;
import g.model.warning.po.PlayerWarningControl;
import g.model.warning.vo.PlayerWarningControlListVo;
import g.model.warning.vo.PlayerWarningControlVo;


/**
 * 玩家实时监控控制表服务接口
 *
 * @author lenovo
 * @time 2017-2-21 14:12:56
 */
//region your codes 1
public interface IPlayerWarningControlService extends IBaseService<PlayerWarningControlListVo, PlayerWarningControlVo, PlayerWarningControl, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 获取状态为10的实体
     * @return
     */
    PlayerWarningControl getWarningControl();

    /**
     * 保存之前把之前的数据状态变为已失效
     */
    void updateWarningStatus(Integer userId);

}