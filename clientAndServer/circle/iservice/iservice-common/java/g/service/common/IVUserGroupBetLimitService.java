package g.service.common;

import g.model.admin.so.VUserGroupBetLimitSo;
import org.soul.iservice.support.IBaseService;
import g.model.admin.po.VUserGroupBetLimit;
import g.model.admin.vo.VUserGroupBetLimitListVo;
import g.model.admin.vo.VUserGroupBetLimitVo;

import java.util.List;
import java.util.Map;


/**
 * 玩家分组单注投注限额服务接口
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public interface IVUserGroupBetLimitService extends IBaseService<VUserGroupBetLimitListVo, VUserGroupBetLimitVo, VUserGroupBetLimit, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 各代理下的不同玩家组限额
     * @param vUserGroupBetLimit
     * @return
     */
    Map<String, VUserGroupBetLimitSo> load(VUserGroupBetLimitVo vUserGroupBetLimitVo);

    /**
     * 玩家分组单注限额
     * @param vo
     * @return
     */
    List<VUserGroupBetLimit> getPlayerGroupBetLimit(VUserGroupBetLimitVo vo);
    //endregion your codes 2

    /**
     * 查询出所有的当前用户创建的组的所有自定义视图
     * @param createUser
     * @return
     */
    List<VUserGroupBetLimit> listByCreate(Integer createUser);

    /**
     * 通过组id和id返回指定的一条视图信息
     * @param vo
     * @return
     */
    List<VUserGroupBetLimit> listByIdAndGroupId(VUserGroupBetLimitVo vo);

}