package g.service.depositdesk;

import g.model.depositdesk.vo.PlayerRechargeListVo;
import org.soul.iservice.support.IBaseService;
import g.model.depositdesk.po.VPlayerRecharge;
import g.model.depositdesk.vo.VPlayerRechargeListVo;
import g.model.depositdesk.vo.VPlayerRechargeVo;


/**
 * 服务接口
 *
 * @author tom
 * @time 2016-7-14 9:46:35
 */
//region your codes 1
public interface IVPlayerRechargeService extends IBaseService<VPlayerRechargeListVo, VPlayerRechargeVo, VPlayerRecharge, Long> {
//endregion your codes 1

    //region your codes 2
    VPlayerRechargeListVo searchPlayerRechargeList(VPlayerRechargeListVo vPlayerRechargeListVo);

    //endregion your codes 2

}