package g.data.playerstatistics;

import g.model.playerstatistics.po.PlayerSummery;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表数据访问对象
 *
 * @author lenovo
 * @time 2017-1-5 17:36:23
 */
//region your codes 1
public interface PlayerSummeryMapper extends IBaseMapper<PlayerSummery, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 所有玩家统计
     * @param map
     * @return
     */
    List<PlayerSummery> selectPlayerGroupByDate(Map map);

    /**
     * 所有玩家统计 总记录数
     * @param map
     * @return
     */
    Integer selectPlayerGroupByDateCount(Map map);

    /**
     * 所有玩家统计 导出数据
     * @param map
     * @return
     */
    List<PlayerSummery> getAllAgentExportData(Map map);

    /**
     * 玩家统计 导出数据
     * @param map
     * @return
     */
    List<PlayerSummery> getOwnerAgentExportData(Map map);
    //endregion your codes 2

    /**
     * 定时任务调度存储统计玩家数据
     * @param map
     * @return
     */
    boolean playerSummeryCall(Map map);

    /**
     * 统计玩家
     * @param map
     * @return
     */
    List<PlayerSummery> reportSearchAll(Map map);

}