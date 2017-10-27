package g.service.common;

import g.data.admin.VUserGroupBetLimitMultipleMapper;
import g.model.admin.po.VUserGroupBetLimitMultiple;
import g.model.admin.vo.VUserGroupBetLimitMultipleListVo;
import g.model.admin.vo.VUserGroupBetLimitMultipleVo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 玩家分组综合过关单项限额服务
 *
 * @author tom
 * @time 2016-4-21 17:23:47
 */
//region your codes 1
public class VUserGroupBetLimitMultipleService extends BaseService<VUserGroupBetLimitMultipleMapper, VUserGroupBetLimitMultipleListVo, VUserGroupBetLimitMultipleVo, VUserGroupBetLimitMultiple, Integer> implements IVUserGroupBetLimitMultipleService {
//endregion your codes 1

    //region your codes 2

    @Override
    public Map<String, VUserGroupBetLimitMultipleVo> load(VUserGroupBetLimitMultipleVo vo) {
        Map<String, VUserGroupBetLimitMultipleVo> result =  new HashMap<>();
        List<VUserGroupBetLimitMultiple> vUserGroupBetLimitMultiples = this.mapper.allSearch(new Order(VUserGroupBetLimitMultiple.PROP_ORDER_NUM, Direction.ASC));
        if (CollectionTool.isNotEmpty(vUserGroupBetLimitMultiples)) {
            Map<Integer, List<VUserGroupBetLimitMultiple>> userIdListMap = CollectionTool.groupByProperty(vUserGroupBetLimitMultiples,
                    VUserGroupBetLimitMultiple.PROP_CREATE_USER, Integer.class);
            Iterator userIdsIter = userIdListMap.keySet().iterator();
            while (userIdsIter.hasNext()) {
                Integer userId = (Integer)userIdsIter.next();
                List<VUserGroupBetLimitMultiple> agent_userGroup_betLimitMultipleList = userIdListMap.get(userId);
                if (CollectionTool.isNotEmpty(agent_userGroup_betLimitMultipleList)) {
                    Map<Integer, List<VUserGroupBetLimitMultiple>> groupIdListMap = CollectionTool.groupByProperty(agent_userGroup_betLimitMultipleList,
                            VUserGroupBetLimitMultiple.PROP_SYS_USER_GROUP_ID, Integer.class);
                    VUserGroupBetLimitMultipleVo vUserGroupBetLimitMultipleVo = new VUserGroupBetLimitMultipleVo();
                    vUserGroupBetLimitMultipleVo.setGroupIdListMap(groupIdListMap);
                    result.put(userId + "",vUserGroupBetLimitMultipleVo);
                }
            }
        }

        return result;
    }

    //endregion your codes 2

}