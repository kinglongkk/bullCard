package g.service.warning;


import org.soul.iservice.support.IBaseService;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.vo.PlayerWarningMultipleListVo;
import g.model.warning.vo.PlayerWarningMultipleVo;

import java.util.Map;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数服务接口
 *
 * @author lenovo
 * @time 2017-2-25 16:06:11
 */
//region your codes 1
public interface IPlayerWarningMultipleService extends IBaseService<PlayerWarningMultipleListVo, PlayerWarningMultipleVo, PlayerWarningMultiple, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    PlayerWarningMultiple getPlayerWarningMultiple(Integer id);


    /**
     * 定时任务掉存储过程
     * @return
     */
    Boolean  quartzWarningMultiple();

    /**
     * 获取玩家基本信息
     * @param playerId
     * @return
     */
    Map<String,Object> getUserPlayer(Integer playerId);

}