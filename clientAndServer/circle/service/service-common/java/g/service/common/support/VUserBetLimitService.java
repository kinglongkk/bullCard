package g.service.common.support;

import g.model.admin.vo.VUserBetLimitListVo;
import org.soul.service.support.BaseService;
import g.data.admin.VUserBetLimitMapper;
import g.service.bet.IVUserBetLimitService;
import g.model.admin.po.VUserBetLimit;
import g.model.admin.vo.VUserBetLimitVo;

import java.util.List;


/**
 * 代理单注限额视图服务
 *
 * @author tom
 * @time 2016-4-21 17:14:24
 */
//region your codes 1
public class VUserBetLimitService extends BaseService<VUserBetLimitMapper, VUserBetLimitListVo, VUserBetLimitVo, VUserBetLimit, Integer> implements IVUserBetLimitService {
//endregion your codes 1

    //region your codes 2

    @Override
    public List<VUserBetLimit> getUserBetLimit(VUserBetLimitVo vo) {
        return this.mapper.getUserBetLimit(vo.getSearch());
    }

    //endregion your codes 2

}