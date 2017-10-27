package g.service.playerstatistics;

import g.model.game.vo.GameListVo;
import g.model.playerstatistics.po.PlayerDataStatistics;
import g.model.playerstatistics.po.PlayerDataStatisticsReport;
import g.model.playerstatistics.vo.JackpotMatchListVo;
import g.model.playerstatistics.vo.JackpotPlayerListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsListVo;
import g.model.playerstatistics.vo.PlayerDataStatisticsVo;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import org.soul.iservice.support.IBaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 玩家数据统计表服务接口
 *
 * @author lenovo
 * @time 2017-1-5 14:06:09
 */
//region your codes 1
public interface IPlayerDataStatisticsService extends IBaseService<PlayerDataStatisticsListVo, PlayerDataStatisticsVo, PlayerDataStatistics, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 按代理统计
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo selectAllAgentList(PlayerDataStatisticsListVo listVo);

    /**
     * 按玩家统计
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo selectOwnerPlayerList(PlayerDataStatisticsListVo listVo);

    /**
     * 按游戏统计
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo selectAllGameList(PlayerDataStatisticsListVo listVo);

    /**
     * 按代理统计 导出报表数据
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    List<PlayerDataStatistics> selectAllAgentExportData(PlayerDataStatisticsListVo listVo);

    /**
     * 按玩家统计 导出报表数据
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    List<PlayerDataStatistics> selectOwnerPlayerExportData(PlayerDataStatisticsListVo listVo);

    /**
     * 按游戏统计 导出报表数据
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    List<PlayerDataStatistics> selectAllGameExportData(PlayerDataStatisticsListVo listVo);

    /**
     * 按玩家、游戏详细 导出报表数据
     * @param listVo
     * @return
     */
    List<PlayerDataStatistics> selectPlayerOrGameDetailExportData(PlayerDataStatisticsListVo listVo);

    /**
     * 查询各项统计值
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo selectTotalAmount(PlayerDataStatisticsListVo listVo);

    /**
     * 设置页面参数 保证参数分页功能
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo setSearchCondition(PlayerDataStatisticsListVo listVo);

    /**
     * 把结束时间加一天
     * @param listVo
     * @return PlayerDataStatisticsListVo
     */
    PlayerDataStatisticsListVo addOneDay(PlayerDataStatisticsListVo listVo);

    /**
     * 查询所有游戏
     * @return GameListVo
     */
    GameListVo selectAllGame();

    /**
     * 查询代理所代理的游戏
     * @param agentId
     * @return GameListVo
     */
    GameListVo selectAgentGame(Integer agentId);

    /**
     * 查询所属游戏的玩法
     * @param gameId 游戏id
     * @return map
     */
    Map selectOwnerGameModel(Integer gameId);
    //endregion your codes 2

    /**
     * 单个代理商详细汇总
     * @param startTime
     * @param endTime
     * @param agentUserName
     * @return
     */
    List<PlayerDataStatisticsReport> searchPlayerList(String startTime, String endTime, String agentUserName);

    /**
     * 多个代理商对比
     * @param startTime
     * @param endTime
     * @param username
     * @return
     */
    List<PlayerDataStatisticsReport> searchMultiplePlayerList(String startTime, String endTime, String[] username);
    /**
     * 所有代理商汇总
     * @param startTime
     * @param endTime
     * @return
     */
    List<PlayerDataStatisticsReport> searchAllPlayerList(String startTime, String endTime);

    /**
     * 玩家盈亏数据统计明细
     * @return
     */
    Boolean quartzPlayerDataStatistics();

    /**
     * 获取奖池报表
     * @param listVo
     * @return
     */
    JackpotMatchListVo getJackpotMatch(JackpotMatchListVo listVo,Date startTime,Date endTime,String sysuserid,String gameid,String gamemodelid);

    /**
     * 获取奖池报表用户
     * @return
     */
    JackpotPlayerListVo getJackpotPlayer(JackpotPlayerListVo listVo,Date startTime,Date endTime);

    /**
     * 奖池报表抽水金额明细
     * @return
     */
    VWarningPlayerDetailListVo jackpotBet(VWarningPlayerDetailListVo listVo);

    /**
     * 代理商盈亏
     * @param map
     * @return
     */
    boolean dataStatisticsReport(Map map);
}