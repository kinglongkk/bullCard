package g.service.playerstatistics;

import g.model.playerstatistics.po.PlayerSummery;
import g.model.playerstatistics.vo.PlayerSummeryListVo;
import g.model.playerstatistics.vo.PlayerSummeryVo;
import org.soul.iservice.support.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表服务接口
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public interface IPlayerSummeryService extends IBaseService<PlayerSummeryListVo, PlayerSummeryVo, PlayerSummery, Integer> {

    /**
     * 获取时间段内的数据
     * @return
     */
    List<PlayerSummery> listSummertSearch(String startTime, String endTime);

    /**
     * agent系统获取代理商玩家数
     * @param startTime
     * @param endTime
     * @param username
     * @return
     */
    List<PlayerSummery> agentListSummertSearch(String startTime, String endTime,String username);

    /**
     * 多个代理商玩家数
     * @param startTime
     * @param endTime
     * @param username
     * @return
     */
    List<PlayerSummery> agentListMultipleSummertSearch(String startTime, String endTime,String[] username);

    /**
     * 获取玩家今日新增数和活跃数
     * @param startTime
     * @param endTime
     * @param agentId
     * @return
     */
    Map<String,Object> getTotalAddActive(Date  startTime ,Date endTime,Integer agentId);

    /**
     * 定时任务掉存储过程
     * @return
     */
    Boolean  quartzPlayerSummery();

    /**
     * 所有玩家统计
     * @param listVo
     * @return
     */
    PlayerSummeryListVo selectPlayerGroupByDate(PlayerSummeryListVo listVo);

    /**
     * 所有玩家统计 导出数据
     * @return
     */
    List<PlayerSummery> getAllAgentExportData(PlayerSummeryListVo listVo);

    /**
     * 玩家统计 导出数据
     * @return
     */
    List<PlayerSummery> getOwnerAgentExportData(PlayerSummeryListVo listVo);
}