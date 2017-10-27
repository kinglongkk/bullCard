package g.service.gameroom;

import g.common.tool.DateTimeTool;
import g.model.gameroom.po.WinGameAmount;
import org.soul.service.support.BaseService;
import g.data.gameroom.VGameRoomMapper;
import g.service.gameroom.IVGameRoomService;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.vo.VGameRoomListVo;
import g.model.gameroom.vo.VGameRoomVo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 服务
 *
 * @author lenovo
 * @time 2017-2-14 16:52:54
 */
//region your codes 1
public class VGameRoomService extends BaseService<VGameRoomMapper, VGameRoomListVo, VGameRoomVo, VGameRoom, Integer> implements IVGameRoomService {
    @Override
    public List<WinGameAmount> selectWinGameAmout( List<Integer> ids) {
        Map<String,Object> map = new HashMap<String,Object>();
        //今天开始时间
        map.put("begintime", DateTimeTool.getTodayStart());
        //今天结束时间
        map.put("endtime",DateTimeTool.getTodayEnd());
        map.put("gameids",ids);
        return this.mapper.selectWinGameAmout(map);
    }
}