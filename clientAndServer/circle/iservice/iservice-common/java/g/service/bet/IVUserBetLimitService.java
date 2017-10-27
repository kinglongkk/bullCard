package g.service.bet;

import g.model.admin.vo.VUserBetLimitListVo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.vo.VUserBetLimitVo;

import java.util.List;


/**
 * 代理单注限额视图服务接口
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public interface IVUserBetLimitService extends IBaseService<VUserBetLimitListVo, VUserBetLimitVo, VUserBetLimit, Integer> {
//endregion your codes 1

    //region your codes 2

    List<VUserBetLimit> getUserBetLimit(VUserBetLimitVo vo);
    //endregion your codes 2

}