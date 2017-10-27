package g.data.gameroom;

import g.model.gameroom.po.VGameRoom;
import g.model.gameroom.po.WinGameAmount;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;
import java.util.Map;


/**
 * 数据访问对象
 *
 * @author lenovo
 * @time 2017-2-14 16:52:53
 */
//region your codes 1
public interface VGameRoomMapper extends IBaseQueryMapper<VGameRoom, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    List<WinGameAmount> selectWinGameAmout(Map map);

}