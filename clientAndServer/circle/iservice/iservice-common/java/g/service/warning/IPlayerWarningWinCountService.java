package g.service.warning;

import org.soul.iservice.support.IBaseService;
import g.model.warning.po.PlayerWarningWinCount;
import g.model.warning.vo.PlayerWarningWinCountListVo;
import g.model.warning.vo.PlayerWarningWinCountVo;


/**
 * 说明预警异常用户赢得连续派彩次数服务接口
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
//region your codes 1
public interface IPlayerWarningWinCountService extends IBaseService<PlayerWarningWinCountListVo, PlayerWarningWinCountVo, PlayerWarningWinCount, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2
    PlayerWarningWinCount getPlayerWarningWinCount(Integer id);

    /**
     * 定时任务掉存储过程
     * @return
     */
    Boolean  quartzWarningWinCount();
}