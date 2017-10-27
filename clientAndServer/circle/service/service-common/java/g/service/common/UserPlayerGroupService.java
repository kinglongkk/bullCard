package g.service.common;

import g.data.agent.UserPlayerGroupMapper;
import g.model.agent.po.UserPlayerGroup;
import g.model.agent.vo.UserPlayerGroupListVo;
import g.model.agent.vo.UserPlayerGroupVo;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;

import java.util.List;

/**
 * 服务
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class UserPlayerGroupService extends BaseService<UserPlayerGroupMapper, UserPlayerGroupListVo, UserPlayerGroupVo, UserPlayerGroup, Integer> implements IUserPlayerGroupService {
//endregion your codes 1

    //region your codes 2
    @Override
    public Boolean deleteById(UserPlayerGroupVo vo) {
        return null;
    }

    @Override
    public List<UserPlayerGroup> listByCreate(Integer createUser) {
        return mapper.search(Criteria.add(UserPlayerGroup.PROP_CREATE_USER, Operator.EQ, createUser));
    }

    public UserPlayerGroupVo getDefaultGroup(UserPlayerGroupVo vo){
        vo.getSearch().setIsDefault(true);
        UserPlayerGroupVo userPlayerGroupVo = this.search(vo);
        return userPlayerGroupVo;
    }

    public UserPlayerGroupVo getGroupById (UserPlayerGroupVo vo){
        UserPlayerGroupVo userPlayerGroupVo = this.search(vo);
        return userPlayerGroupVo;
    }



    //endregion your codes 2

}