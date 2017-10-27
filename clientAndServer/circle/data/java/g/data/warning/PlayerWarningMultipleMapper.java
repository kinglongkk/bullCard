package g.data.warning;


import g.model.warning.po.PlayerWarningMultiple;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数数据访问对象
 *
 * @author lenovo
 * @time 2017-2-25 16:06:11
 */
//region your codes 1
public interface PlayerWarningMultipleMapper extends IBaseMapper<PlayerWarningMultiple, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    PlayerWarningMultiple getPlayerWarningMultiple(Integer id);

    /**
     * 定时掉存储过程 赢得金币和投注金额倍数比
     * @param map
     * @return
     */
    public Boolean quartzWarningMultiple(Map map);

    /**
     * 获取玩家基本信息
     * @param playerId
     * @return
     */
    Map<String,Object> getUserPlayer(Integer playerId);
}