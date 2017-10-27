package g.service.depositdesk;

import g.model.depositdesk.enums.RechargeStatusEnum;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;
import g.data.depositdesk.VPlayerRechargeMapper;
import g.model.depositdesk.po.VPlayerRecharge;
import g.model.depositdesk.vo.VPlayerRechargeListVo;
import g.model.depositdesk.vo.VPlayerRechargeVo;

import java.util.List;


/**
 * 服务
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public class VPlayerRechargeService extends BaseService<VPlayerRechargeMapper, VPlayerRechargeListVo, VPlayerRechargeVo, VPlayerRecharge, Long> implements IVPlayerRechargeService {
//endregion your codes 1

    //region your codes 2
    @Override
    public VPlayerRechargeListVo searchPlayerRechargeList(VPlayerRechargeListVo playerRechargeListVo) {
        Criteria criteria = Criteria.add(VPlayerRecharge.PROP_RECHARGE_STATUS, Operator.EQ, RechargeStatusEnum.DEAL.getCode());
        Order order = Order.desc(VPlayerRecharge.PROP_CREATE_TIME);
        List<VPlayerRecharge> playerRecharges = this.mapper.pagingSearch(criteria, 1, 20, order);
        playerRechargeListVo.setResult(playerRecharges);
        return playerRechargeListVo;
    }
    //endregion your codes 2

}