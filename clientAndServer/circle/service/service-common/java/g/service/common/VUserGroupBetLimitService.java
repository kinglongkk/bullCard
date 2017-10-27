package g.service.common;

import g.model.admin.so.VUserGroupBetLimitSo;
import org.soul.commons.cache.CacheKey;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.commons.query.sort.Direction;
import org.soul.commons.query.sort.Order;
import org.soul.service.support.BaseService;
import g.data.admin.VUserGroupBetLimitMapper;
import g.model.admin.po.VUserGroupBetLimit;
import g.model.admin.vo.VUserGroupBetLimitListVo;
import g.model.admin.vo.VUserGroupBetLimitVo;

import java.util.*;


/**
 * 玩家分组单注投注限额服务
 *
 * @author tom
 * @time 2016-4-21 17:20:59
 */
//region your codes 1
public class VUserGroupBetLimitService extends BaseService<VUserGroupBetLimitMapper, VUserGroupBetLimitListVo, VUserGroupBetLimitVo, VUserGroupBetLimit, Integer> implements IVUserGroupBetLimitService {
    //region your codes 2

    //endregion your codes 1
    @Override
    public Map<String, VUserGroupBetLimitSo> load(VUserGroupBetLimitVo vUserGroupBetLimitVo) {
//        Map<String, VUserGroupBetLimitVo> result =  new HashMap<>();
//        List<VUserGroupBetLimit> vUserGroupBetLimits = this.mapper.allSearch(new Order(VUserGroupBetLimit.PROP_ORDER_NUM, Direction.ASC));
//        if (CollectionTool.isNotEmpty(vUserGroupBetLimits)) {
//            Map<Integer, List<VUserGroupBetLimit>> userIdListMap = CollectionTool.groupByProperty(vUserGroupBetLimits,
//                    VUserGroupBetLimit.PROP_CREATE_USER, Integer.class);
//            Iterator userIdsIter = userIdListMap.keySet().iterator();
//            while (userIdsIter.hasNext()) {
//                Integer userId = (Integer)userIdsIter.next();
//                List<VUserGroupBetLimit> agentUserGroupBetLimitList = userIdListMap.get(userId);
//                if (CollectionTool.isNotEmpty(agentUserGroupBetLimitList)) {
//                    Map<Integer, List<VUserGroupBetLimit>> groupIdListMap = CollectionTool.groupByProperty(agentUserGroupBetLimitList,
//                            VUserGroupBetLimit.PROP_SYS_USER_GROUP_ID, Integer.class);
//                    VUserGroupBetLimitVo vUserGroupBetLimitVo = new VUserGroupBetLimitVo();
//                    vUserGroupBetLimitVo.setUserGroupBetLimitMap(groupIdListMap);
//                    result.put(userId + "",vUserGroupBetLimitVo);
//                }
//            }
//        }
//        return result;

        Map<String, VUserGroupBetLimitSo> result =  new HashMap<>();
        List<VUserGroupBetLimit> vUserGroupBetLimits = this.mapper.allSearch(new Order(VUserGroupBetLimit.PROP_ORDER_NUM, Direction.ASC));
        if (CollectionTool.isNotEmpty(vUserGroupBetLimits)) {
            List<VUserGroupBetLimitSo> cacheVos = CacheKey.toCacheVo(vUserGroupBetLimits, VUserGroupBetLimitSo.class);
            Map<String, List<VUserGroupBetLimitSo>> groupBygb = CollectionTool.groupByProperty(cacheVos, VUserGroupBetLimitSo.PROP_CACHE_KEY, String.class);
            for(Map.Entry<String,List<VUserGroupBetLimitSo>> entry : groupBygb.entrySet()){
                result.put(entry.getKey(),entry.getValue().get(0));
            }
        }
            return result;
    }



    @Override
    public List<VUserGroupBetLimit> getPlayerGroupBetLimit(VUserGroupBetLimitVo vo) {
        return this.mapper.getPlayerGroupBetLimit(vo.getSearch());
    }

    @Override
    public List<VUserGroupBetLimit> listByCreate(Integer createUser){
        return this.mapper.search(Criteria.add(VUserGroupBetLimit.PROP_CREATE_USER,Operator.EQ,createUser),Order.asc(VUserGroupBetLimit.PROP_GROUP_NAME),Order.asc(VUserGroupBetLimit.PROP_BET_TYPE));
    }

    @Override
    public List<VUserGroupBetLimit> listByIdAndGroupId(VUserGroupBetLimitVo vo){
        return mapper.search(Criteria.add(VUserGroupBetLimit.PROP_ID,Operator.EQ,vo.getSearch().getId()).addAnd(Criteria.add(VUserGroupBetLimit.PROP_SYS_USER_GROUP_ID,Operator.EQ,vo.getSearch().getSysUserGroupId())));
    }
//endregion your codes 2

}