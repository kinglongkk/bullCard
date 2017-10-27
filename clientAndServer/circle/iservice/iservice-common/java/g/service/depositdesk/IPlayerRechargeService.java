package g.service.depositdesk;

import org.soul.model.pay.vo.OnlinePayVo;

import org.soul.iservice.support.IBaseService;
import g.model.depositdesk.po.PlayerRecharge;
import g.model.depositdesk.vo.PlayerRechargeListVo;
import g.model.depositdesk.vo.PlayerRechargeVo;


/**
 * 玩家充值表-mark服务接口
 *
 * @author tom
 * @time 2016-7-14 9:27:04
 */
//region your codes 1
public interface IPlayerRechargeService extends IBaseService<PlayerRechargeListVo, PlayerRechargeVo, PlayerRecharge, Long> {
//endregion your codes 1

    //region your codes 2

    /**
     * 处理线上支付订单
     *
     * @param playerRechargeVo
     * @param onlinePayVo
     * @return
     */
    PlayerRechargeVo handleOnlineRechargeResult(PlayerRechargeVo playerRechargeVo, OnlinePayVo onlinePayVo);

    /**
     * 保存存款订单(玩家提交的存款订单)
     *
     * @param playerRechargeVo
     * @return
     */
    PlayerRechargeVo savePlayerRecharge(PlayerRechargeVo playerRechargeVo);

    void updateRechargeStatus();

    PlayerRechargeVo searchPlayerRecharge(PlayerRechargeVo playerRechargeVo);

    //endregion your codes 2

}