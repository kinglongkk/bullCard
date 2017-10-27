package g.service.common.support;

import g.data.admin.BetLimitUserGroupMapper;
import g.model.admin.po.BetLimitUserGroup;
import g.model.admin.vo.BetLimitUserGroupListVo;
import g.model.admin.vo.BetLimitUserGroupVo;
import g.service.bet.IBetLimitUserGroupService;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;

import java.util.List;


/**
 * 用户组与综合投注限额关系表服务
 *
 * @author tom
 * @time 2016-4-20 11:41:52
 */
//region your codes 1
public class BetLimitUserGroupService extends BaseService<BetLimitUserGroupMapper, BetLimitUserGroupListVo, BetLimitUserGroupVo, BetLimitUserGroup, Integer> implements IBetLimitUserGroupService {
//endregion your codes 1

    //region your codes 2
    @Override
    public boolean hasGroup(Integer groupId){
            List<BetLimitUserGroup> list = mapper.search(Criteria.add(BetLimitUserGroup.PROP_SYS_USER_GROUP_ID, Operator.EQ,groupId), Order.asc(BetLimitUserGroup.PROP_ID));
            if(list.size()==0)
                return false;
            else
                return true;
    }

    @Override
    public void saveByEntity(BetLimitUserGroup betLimitUserGroup){
        mapper.insert(betLimitUserGroup);
    }


    //endregion your codes 2
}