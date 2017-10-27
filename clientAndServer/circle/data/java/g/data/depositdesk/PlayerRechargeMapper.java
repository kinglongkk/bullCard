package g.data.depositdesk;


import g.model.depositdesk.po.PlayerRecharge;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.Map;


/**
 * 玩家充值表-mark数据访问对象
 *
 * @author tom
 * @time 2016-7-14 9:27:04
 */
//region your codes 1
public interface PlayerRechargeMapper extends IBaseMapper<PlayerRecharge, Long> {
//endregion your codes 1

    //region your codes 2
    Double getRechargeAmount(Map map);
    Double getRechargeAmountByUserId(Map map);
    PlayerRecharge searchPlayerRecharge(PlayerRecharge playerRecharge);
    //endregion your codes 2

}