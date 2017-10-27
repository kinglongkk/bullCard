package g.service.warning;


import g.common.tool.DateTimeTool;
import org.soul.service.support.BaseService;
import g.data.warning.PlayerWarningMultipleMapper;
import g.service.warning.IPlayerWarningMultipleService;
import g.model.warning.po.PlayerWarningMultiple;
import g.model.warning.vo.PlayerWarningMultipleListVo;
import g.model.warning.vo.PlayerWarningMultipleVo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 说明预警异常用户赢得金额跟投注金额倍数服务
 *
 * @author lenovo
 * @time 2017-2-25 16:06:12
 */
//region your codes 1
public class PlayerWarningMultipleService extends BaseService<PlayerWarningMultipleMapper, PlayerWarningMultipleListVo, PlayerWarningMultipleVo, PlayerWarningMultiple, Integer> implements IPlayerWarningMultipleService {
    @Override
    public PlayerWarningMultiple getPlayerWarningMultiple(Integer id) {

        return this.mapper.getPlayerWarningMultiple(id);
    }

    @Override
    public Boolean quartzWarningMultiple() {
        Map<String,Date> map = new HashMap<String,Date>();
        //今天开始时间
        map.put("begintime", DateTimeTool.getTodayStart());
       //今天结束时间
        //map.put("endtime",DateTimeTool.getTodayEnd());
        map.put("endtime",new Date());
        return this.mapper.quartzWarningMultiple(map);
    }

    @Override
    public Map<String, Object> getUserPlayer(Integer playerId) {
        return  this.mapper.getUserPlayer(playerId);
    }
}