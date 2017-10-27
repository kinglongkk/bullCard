package g.service.admin;

import g.model.admin.vo.BetLimitMultipleListVo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.po.BetLimitMultiple;
import g.model.admin.vo.BetLimitMultipleVo;


/**
 * 综合投注限额服务接口
 *
 * @author tom
 * @time 2016-4-20 11:37:43
 */
//region your codes 1
public interface IBetLimitMultipleService extends IBaseService<BetLimitMultipleListVo, BetLimitMultipleVo, BetLimitMultiple, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 保存系统综合投注限额
     * @param vo
     * @return
     */
    BetLimitMultipleVo editBetLimitMultiples(BetLimitMultipleVo vo);
    //endregion your codes 2

}