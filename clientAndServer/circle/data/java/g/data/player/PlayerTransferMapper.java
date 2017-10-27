package g.data.player;

import g.model.player.po.PlayerTransfer;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 玩家转账记录数据访问对象
 *
 * @author black
 * @time 2016-11-18 17:11:08
 */
//region your codes 1
public interface PlayerTransferMapper extends IBaseMapper<PlayerTransfer, Integer> {
//endregion your codes 1

    /**
     * 新增玩家转账记录
     * @param map
     * @return
     */
    boolean insertNewRecord(Map map);

}