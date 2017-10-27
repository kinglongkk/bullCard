package g.service.player;

import org.soul.iservice.support.IBaseService;
import g.model.player.po.PlayerTransfer;
import g.model.player.vo.PlayerTransferListVo;
import g.model.player.vo.PlayerTransferVo;

import java.util.Map;


/**
 * 玩家转账记录服务接口
 *
 * @author black
 * @time 2016-11-18 17:11:08
 */
//region your codes 1
public interface IPlayerTransferService extends IBaseService<PlayerTransferListVo, PlayerTransferVo, PlayerTransfer, Integer> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    /**
     * 新增玩家转账记录
     * @param map
     * @return
     */
    boolean insertNewRecord(Map map);

}