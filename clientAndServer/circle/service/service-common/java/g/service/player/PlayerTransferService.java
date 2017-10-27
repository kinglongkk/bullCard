package g.service.player;

import g.data.player.PlayerTransferMapper;
import g.model.player.po.PlayerTransfer;
import g.model.player.vo.PlayerTransferListVo;
import g.model.player.vo.PlayerTransferVo;
import org.soul.service.support.BaseService;

import java.util.Map;


/**
 * 玩家转账记录服务
 *
 * @author black
 * @time 2016-11-18 17:11:08
 */
//region your codes 1
public class PlayerTransferService extends BaseService<PlayerTransferMapper, PlayerTransferListVo, PlayerTransferVo, PlayerTransfer, Integer> implements IPlayerTransferService {


    public boolean insertNewRecord(Map map) {

        return this.mapper.insertNewRecord(map);
    }
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

}