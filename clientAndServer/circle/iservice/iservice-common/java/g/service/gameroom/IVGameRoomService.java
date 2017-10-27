package g.service.gameroom;

import g.model.gameroom.po.WinGameAmount;
import org.soul.iservice.support.IBaseService;
import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.vo.VGameRoomListVo;
import g.model.gameroom.vo.VGameRoomVo;

import java.util.List;


/**
 * 服务接口
 *
 * @author lenovo
 * @time 2017-2-14 16:52:53
 */
//region your codes 1
public interface IVGameRoomService extends IBaseService<VGameRoomListVo, VGameRoomVo, VGameRoom, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    List<WinGameAmount> selectWinGameAmout( List<Integer> ids);

}