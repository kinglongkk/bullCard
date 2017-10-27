package g.service.common;

import g.data.admin.BetLimitMapper;
import g.data.admin.BetLimitMultipleMapper;
import g.data.admin.BetLimitUserGroupMapper;
import g.data.admin.BetLimitUserGroupMultipleMapper;
import g.data.agent.UserPlayerGroupMapper;
import g.data.agent.VUserPlayerGroupMapper;

import g.model.agent.po.UserPlayerGroup;
import g.model.agent.po.VUserPlayerGroup;
import g.model.agent.vo.VUserPlayerGroupListVo;
import g.model.agent.vo.VUserPlayerGroupVo;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 服务
 *
 * @author orange
 * @time 2016-4-27 15:56:13
 */
//region your codes 1
public class VUserPlayerGroupService extends BaseService<VUserPlayerGroupMapper, VUserPlayerGroupListVo, VUserPlayerGroupVo, VUserPlayerGroup, Integer> implements IVUserPlayerGroupService {
//endregion your codes 1

    //region your codes 2

    @Autowired
    private BetLimitMapper betLimitMapper;

    @Autowired
    private BetLimitMultipleMapper betLimitMultipleMapper;

    @Autowired
    private BetLimitUserGroupMapper betLimitUserGroupMapper;

    @Autowired
    private BetLimitUserGroupMultipleMapper betLimitUserGroupMultipleMapper;

    @Autowired
    private UserPlayerGroupMapper userPlayerGroupMapper;

    @Override
    @Transactional
    public boolean delete(VUserPlayerGroupVo objectVo) {

        return this.userPlayerGroupMapper.delete(objectVo.getResult().getId());
    }

    @Override
    public VUserPlayerGroupVo editUserGroup(VUserPlayerGroupVo vo) {
        VUserPlayerGroup result = vo.getResult();
        if (result.getId() != null)
            update(vo);
        else
            insert(vo);
        return vo;
    }

    @Override
    public VUserPlayerGroupVo update(VUserPlayerGroupVo objectVo) {
        VUserPlayerGroup result = objectVo.getResult();
        UserPlayerGroup userPlayerGroup = new UserPlayerGroup();
        userPlayerGroup.setId(objectVo.getResult().getId());
        userPlayerGroup.setGroupName(result.getGroupName());
        userPlayerGroup.setUpdateUser(objectVo.getOperatorUser().getId());
        userPlayerGroup.setUpdateTime(new Date());
        boolean isSuccess = this.userPlayerGroupMapper.updateOnly(userPlayerGroup, UserPlayerGroup.PROP_GROUP_NAME, UserPlayerGroup.PROP_UPDATE_USER, UserPlayerGroup.PROP_UPDATE_TIME);
        objectVo.setSuccess(isSuccess);

        return objectVo;
    }

    @Override
    public VUserPlayerGroupVo insert(VUserPlayerGroupVo objectVo) {
        VUserPlayerGroup result = objectVo.getResult();
        UserPlayerGroup userPlayerGroup = new UserPlayerGroup();
        userPlayerGroup.setGroupName(result.getGroupName());
        userPlayerGroup.setCreateUser(objectVo.getOperatorUser().getId());
        userPlayerGroup.setCreateTime(new Date());
        boolean isSuccess = this.userPlayerGroupMapper.insert(userPlayerGroup);
        objectVo.getResult().setId(userPlayerGroup.getId());
        objectVo.setSuccess(isSuccess);

        return objectVo;
    }

    @Override
    public boolean countGroupname(VUserPlayerGroupVo vo) {
        Criteria criteria = null;
        if (vo.getResult().getId() != null) {
            criteria = Criteria.add(VUserPlayerGroup.PROP_CREATE_USER, Operator.EQ,vo.getOperatorUser().getId())
                    .addAnd(Criteria.add(VUserPlayerGroup.PROP_GROUP_NAME, Operator.EQ, vo.getResult().getGroupName()))
                    .addAnd(Criteria.add(VUserPlayerGroup.PROP_ID,Operator.NE,vo.getResult().getId()));
        } else {
            criteria = Criteria.add(VUserPlayerGroup.PROP_CREATE_USER, Operator.EQ, vo.getOperatorUser().getId())
                    .addAnd(Criteria.add(VUserPlayerGroup.PROP_GROUP_NAME, Operator.EQ, vo.getResult().getGroupName()));
        }

        if (this.mapper.count(criteria) > 0l)
            return false;
        else
            return true;

    }

    //endregion your codes 2

}