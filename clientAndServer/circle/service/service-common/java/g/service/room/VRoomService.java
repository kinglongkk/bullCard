package g.service.room;

import org.soul.service.support.BaseService;
import g.data.room.VRoomMapper;
import g.service.room.IVRoomService;
import g.model.room.po.VRoom;
import g.model.room.vo.VRoomListVo;
import g.model.room.vo.VRoomVo;

import java.util.List;


/**
 * 服务
 *
 * @author longer
 * @time 2016-12-6 16:03:24
 */
//region your codes 1
public class VRoomService extends BaseService<VRoomMapper, VRoomListVo, VRoomVo, VRoom, Integer> implements IVRoomService {

//endregion your codes 1

    //region your codes 2
    @Override
    public VRoomListVo getAvailableRooms(VRoomListVo listVo) {
        List<VRoom> roomList = mapper.search(listVo.getQuery().by());
        listVo.setResult(roomList);
        return listVo;
    }
    //endregion your codes 2

}