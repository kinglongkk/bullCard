package g.service.room;

import org.soul.iservice.support.IBaseService;
import g.model.room.po.VRoom;
import g.model.room.vo.VRoomListVo;
import g.model.room.vo.VRoomVo;


/**
 * 服务接口
 *
 * @author longer
 * @time 2016-12-6 16:03:23
 */
//region your codes 1
public interface IVRoomService extends IBaseService<VRoomListVo, VRoomVo, VRoom, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 获取有效的房间
     * @param listVo
     * @return
     */
    VRoomListVo getAvailableRooms(VRoomListVo listVo);

    //endregion your codes 2

}