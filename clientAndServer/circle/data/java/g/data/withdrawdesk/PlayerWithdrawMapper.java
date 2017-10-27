package g.data.withdrawdesk;

import g.model.withdrawdesk.po.PlayerWithdraw;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.HashMap;
import java.util.Map;


/**
 * 提现记录表数据访问对象
 *
 * @author tom
 * @time 2016-7-13 16:16:43
 */
//region your codes 1
public interface PlayerWithdrawMapper extends IBaseMapper<PlayerWithdraw, Long> {
//endregion your codes 1

    //region your codes 2
    boolean lockPlayerWithdraw(Map<String, Object> map);
    //endregion your codes 2

}