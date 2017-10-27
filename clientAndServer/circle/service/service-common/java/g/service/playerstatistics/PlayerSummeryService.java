package g.service.playerstatistics;

import g.common.tool.DateTimeTool;
import g.data.playerstatistics.PlayerSummeryMapper;
import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.vo.PlayerSummeryListVo;
import g.model.playerstatistics.vo.PlayerSummeryVo;
import org.apache.commons.collections.map.HashedMap;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.Paging;
import org.soul.commons.query.enums.Operator;
import org.soul.data.mapper.security.privilege.SysUserMapper;
import org.soul.model.security.privilege.po.SysUser;
import org.soul.service.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表服务
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public class PlayerSummeryService extends BaseService<PlayerSummeryMapper, PlayerSummeryListVo, PlayerSummeryVo, PlayerSummery, Integer> implements IPlayerSummeryService {

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Override
    public List<PlayerSummery> listSummertSearch(String startTime, String endTime) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        List<PlayerSummery> list = this.mapper.reportSearchAll(map);
        return list;
    }

    @Override
    public List<PlayerSummery> agentListSummertSearch(String startTime, String endTime, String username) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        map.put("username",username.split(","));
        List<PlayerSummery> list = this.mapper.reportSearchAll(map);
        return list;
    }

    @Override
    public List<PlayerSummery> agentListMultipleSummertSearch(String startTime, String endTime, String[] username) {
        Map<String,Object> map = new HashedMap();
        map.put("beginTime", DateTimeTool.getDateByFull(startTime));
        map.put("endTime", DateTimeTool.getDateByFull(endTime));
        map.put("username",username);
        List<PlayerSummery> list = this.mapper.reportSearchAll(map);
        return list;
    }

    @Override
    public Map<String, Object> getTotalAddActive(Date startTime, Date endTime, Integer agentId) {
        Map<String,Object > map = new HashedMap();
        if(agentId == null){
            Criteria  criteria1 = Criteria.and(
                    Criteria.add(SysUser.PROP_CREATE_TIME, Operator.GE, startTime),
                    Criteria.add(SysUser.PROP_CREATE_TIME, Operator.LE, endTime),
                    Criteria.add(SysUser.PROP_USER_TYPE, Operator.EQ, "30")
            );
            long totalAdd = sysUserMapper.count(criteria1);

            Criteria  criteria2 = Criteria.and(
                    Criteria.add(SysUser.PROP_LAST_LOGIN_TIME, Operator.GE, startTime),
                    Criteria.add(SysUser.PROP_LAST_LOGIN_TIME, Operator.LE, endTime),
                    Criteria.add(SysUser.PROP_USER_TYPE, Operator.EQ, "30")
            );
            long totalActive = sysUserMapper.count(criteria2);
            map.put("totalAdd",totalAdd);
            map.put("totalActive",totalActive);
        }else{
            Criteria  criteria1 = Criteria.and(
                    Criteria.add(SysUser.PROP_CREATE_TIME, Operator.GE,  startTime),
                    Criteria.add(SysUser.PROP_CREATE_TIME, Operator.LE,  endTime),
                    Criteria.add(SysUser.PROP_OWNER_ID, Operator.EQ,  agentId),
                    Criteria.add(SysUser.PROP_USER_TYPE, Operator.EQ, "30")
            );
            long totalAdd = sysUserMapper.count(criteria1);

            Criteria  criteria2 = Criteria.and(
                    Criteria.add(SysUser.PROP_LAST_LOGIN_TIME, Operator.GE,  startTime),
                    Criteria.add(SysUser.PROP_LAST_LOGIN_TIME, Operator.LE,  endTime),
                    Criteria.add(SysUser.PROP_OWNER_ID, Operator.EQ,  agentId),
                    Criteria.add(SysUser.PROP_USER_TYPE, Operator.EQ, "30")
            );
            long totalActive = sysUserMapper.count(criteria2);
            map.put("totalAdd",totalAdd);
            map.put("totalActive",totalActive);
        }
        return map;
    }

    @Override
    public Boolean quartzPlayerSummery() {
        Map<String,Date> map = new HashMap<String,Date>();
        //昨天开始时间
        map.put("begintime",DateTimeTool.getBeginDayOfYesterday());
        //昨天结束时间
        map.put("endtime",DateTimeTool.getEndDayOfYesterDay());
        return this.mapper.playerSummeryCall(map);
    }

    public PlayerSummeryListVo selectPlayerGroupByDate(PlayerSummeryListVo listVo) {

        Map map = listVo.getQueryParams();
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectPlayerGroupByDateCount(map));
            paging.cal();
        }
        listVo.setResult(this.mapper.selectPlayerGroupByDate(map));
        return listVo;
    }

    public List<PlayerSummery> getAllAgentExportData(PlayerSummeryListVo listVo) {

        Map map = listVo.getQueryParams();
        return this.mapper.getAllAgentExportData(map);
    }

    public List<PlayerSummery> getOwnerAgentExportData(PlayerSummeryListVo listVo) {

        Map map = listVo.getQueryParams();
        return this.mapper.getOwnerAgentExportData(map);
    }

}