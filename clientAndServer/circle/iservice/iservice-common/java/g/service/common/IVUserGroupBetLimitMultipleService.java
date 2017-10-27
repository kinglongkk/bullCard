package g.service.common;

import g.model.admin.po.VUserGroupBetLimitMultiple;
import g.model.admin.vo.VUserGroupBetLimitMultipleListVo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.vo.VUserGroupBetLimitMultipleVo;

import java.util.Map;


/**
 * 玩家分组综合过关单项限额服务接口
 *
 * @author tom
 * @time 2016-4-21 17:23:46
 */
//region your codes 1
public interface IVUserGroupBetLimitMultipleService extends IBaseService<VUserGroupBetLimitMultipleListVo, VUserGroupBetLimitMultipleVo, VUserGroupBetLimitMultiple, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 各代理下的不同玩家组综合过关单项限额
     * @param vo
     * @return
     */
    Map<String, VUserGroupBetLimitMultipleVo> load(VUserGroupBetLimitMultipleVo vo);
    //endregion your codes 2

}