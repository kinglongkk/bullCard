package g.data.warning;

import g.model.warning.po.PlayerWarningWinCount;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 说明预警异常用户赢得连续派彩次数数据访问对象
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
//region your codes 1
public interface PlayerWarningWinCountMapper extends IBaseMapper<PlayerWarningWinCount, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2
    PlayerWarningWinCount getPlayerWarningWinCount(Integer id);

    /**
     * 定时任务调度
     * @param map
     * @return
     */
    Boolean quartzWarningWinCount(Map map);

}