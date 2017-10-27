package g.data.playerstatistics;


import g.model.playerstatistics.po.JackpotMatch;
import g.model.playerstatistics.po.JackpotPlayer;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.warning.po.VWarningPlayerDetail;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表数据访问对象
 *
 * @author lenovo
 * @time 2017-1-5 14:06:08
 */
//region your codes 1
public interface PlayerDataStatisticsMapper extends IBaseMapper<PlayerDataStatistics, Integer> {
//endregion your codes 1

    //region your codes 2

    /**
     * 按代理统计
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectAllAgentList(Map map);

    /**
     * 按玩家统计
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectOwnerPlayerList(Map map);

    /**
     * 按游戏统计
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectAllGameList(Map map);

    /**
     * 按代理统计 总记录数
     * @param map
     * @return
     */
    Integer selectAllAgentListNumber(Map map);

    /**
     * 按玩家统计 总记录数
     * @param map
     * @return
     */
    Integer selectOwnerPlayerListNumber(Map map);

    /**
     * 按游戏统计 总记录数
     * @param map
     * @return
     */
    Integer selectAllGameListNumber(Map map);

    /**
     * 按代理统计 导出报表数据
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectAllAgentExportData(Map map);

    /**
     * 按玩家统计 导出报表数据
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectOwnerPlayerExportData(Map map);

    /**
     * 按游戏统计 导出报表数据
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectAllGameExportData(Map map);

    /**
     * 按玩家、游戏详细 导出报表数据
     * @param map
     * @return
     */
    List<PlayerDataStatistics> selectPlayerOrGameDetailExportData(Map map);

    /**
     * 调用存储过程统计玩家盈亏明细
     * @return
     */
    Boolean playerDataStatisticsCall();

    List<PlayerDataStatistics> reportSearchAll(Map map);

    /**
     * 盈亏总额报表
     * @param map
     * @return
     */
    List<PlayerDataStatisticsReport> playerDataStatisticsReport(Map map);
    //end region your codes 2

    /**
     * 获取奖池报表数据
     * @return
     */
    List<JackpotMatch> getJackpotMatch(Map<String, Object> queryMap);

    /**
     * 奖池报表分页
     * @param queryMap
     * @return
     */
    Integer jackpotTotalRecords(Map<String, Object> queryMap);

    /**
     * 奖池报表每个用户玩法
     * @param queryMap
     * @return
     */
    List<JackpotPlayer> getJackpotPlayer(Map<String, Object> queryMap);
    /**
     * 奖池报表每个用户玩法分页
     * @param queryMap
     * @return
     */
    Integer jackpotPlayerTotalRecords(Map<String, Object> queryMap);

    /**
     * 奖池抽水金额明细
     * @return
     */
    List<VWarningPlayerDetail>  jackpotBet(Map<String, Object> queryMap);

    /**
     * 奖池抽水金额分页
     * @param queryMap
     * @return
     */
    Integer jackpotTotalBet(Map<String, Object> queryMap);

    /**
     * 代理商盈亏
     * @param map
     * @return
     */
    boolean dataStatisticsReport(Map map);
}