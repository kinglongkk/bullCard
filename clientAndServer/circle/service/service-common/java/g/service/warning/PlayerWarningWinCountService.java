package g.service.warning;

import g.common.tool.DateTimeTool;
import org.soul.service.support.BaseService;
import g.data.warning.PlayerWarningWinCountMapper;
import g.service.warning.IPlayerWarningWinCountService;
import g.model.warning.po.PlayerWarningWinCount;
import g.model.warning.vo.PlayerWarningWinCountListVo;
import g.model.warning.vo.PlayerWarningWinCountVo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 说明预警异常用户赢得连续派彩次数服务
 *
 * @author lenovo
 * @time 2017-2-27 15:00:30
 */
//region your codes 1
public class PlayerWarningWinCountService extends BaseService<PlayerWarningWinCountMapper, PlayerWarningWinCountListVo, PlayerWarningWinCountVo, PlayerWarningWinCount, Integer> implements IPlayerWarningWinCountService {
    @Override
    public PlayerWarningWinCount getPlayerWarningWinCount(Integer id) {

        return this.mapper.getPlayerWarningWinCount(id);
    }

    @Override
    public Boolean quartzWarningWinCount() {
        Map<String,Date> map = new HashMap<String,Date>();
        //今天开始时间
        map.put("begintime", DateTimeTool.getTodayStart());
        //今天结束时间
        //map.put("endtime",DateTimeTool.getTodayEnd());
        Date date = new Date();
        map.put("endtime",date);
        return this.mapper.quartzWarningWinCount(map);
    }


}