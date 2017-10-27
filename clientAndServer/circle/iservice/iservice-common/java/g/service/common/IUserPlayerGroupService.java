package g.service.common;

import g.model.agent.po.UserPlayerGroup;
import g.model.agent.so.UserPlayerGroupSo;
import g.model.agent.vo.UserPlayerGroupListVo;
import g.model.agent.vo.UserPlayerGroupVo;
import g.model.agent.vo.VUserPlayerGroupListVo;
import g.model.agent.vo.VUserPlayerGroupVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;


/**
 * 服务接口
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public interface IUserPlayerGroupService extends IBaseService<UserPlayerGroupListVo, UserPlayerGroupVo, UserPlayerGroup, Integer> {
//endregion your codes 1

    //region your codes 2
    Boolean deleteById(UserPlayerGroupVo vo);

    /**
     * 查询出所有的当前用户创建的用户组
     * @param createUser
     * @return
     */
    List<UserPlayerGroup> listByCreate(Integer createUser);

    UserPlayerGroupVo getDefaultGroup(UserPlayerGroupVo vo);

    UserPlayerGroupVo getGroupById (UserPlayerGroupVo vo);


    //endregion your codes 2

}