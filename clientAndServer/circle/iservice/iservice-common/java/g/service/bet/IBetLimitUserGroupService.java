package g.service.bet;

import g.model.admin.po.BetLimitUserGroup;
import g.model.admin.vo.BetLimitUserGroupListVo;
import g.model.admin.vo.BetLimitUserGroupVo;
import org.soul.iservice.support.IBaseService;


/**
 * 用户组与综合投注限额关系表服务接口
 *
 * @author tom
 * @time 2016-4-20 11:41:52
 */
//region your codes 1
public interface IBetLimitUserGroupService extends IBaseService<BetLimitUserGroupListVo, BetLimitUserGroupVo, BetLimitUserGroup, Integer> {
//endregion your codes 1

    /**
     * 查询是否存在组id为groupId的数据
     * @param groupId
     * @return
     */
    boolean hasGroup(Integer groupId);

    /**
     * 持久化一个po
     * @param betLimitUserGroup
     */
    void saveByEntity(BetLimitUserGroup betLimitUserGroup);
    //region your codes 2

    //endregion your codes 2

}