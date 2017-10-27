package g.data.bet;

import g.model.bet.po.VBetDetail;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;
import java.util.Map;

/**
 * 数据访问对象
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
//region your codes 1
public interface VBetDetailMapper extends IBaseQueryMapper<VBetDetail, Long> {
//endregion your codes 1

    /**
     * 玩家投注记录
     * @param map
     * @return
     */
    List<Map<String, Object>> selectBetRecords(Map map);

    /**
     * 玩家投注记录总条数
     * @param map
     * @return
     */
    Integer selectBetTotalRecords(Map map);

    /**
     * 玩家今日投注记录分组
     * @param map
     * @return
     */
    List<VBetDetail> selectCurrentReport(Map map);

    /**
     * 玩家今日投注记录分组 总条数
     * @param map
     * @return
     */
    Integer selectCurrentReportNumber(Map map);

    /**
     * 玩家今日投注记录详情
     * @param map
     * @return
     */
    List<VBetDetail> selectCurrentReportDetail(Map map);

    /**
     * 玩家今日投注记录详情 总条数
     * @param map
     * @return
     */
    Integer selectCurrentReportDetailNumber(Map map);

    /**
     * 玩家今日投注记录 分组 导出报表数据
     * @param map
     * @return
     */
    List<VBetDetail> selectCurrentReportExportData(Map map);

    /**
     * 玩家今日投注记录 详细 导出报表数据
     * @param map
     * @return
     */
    List<VBetDetail> selectCurrentReportDetailExportData(Map map);

    /**
     * 查询赛事最后的结算时间
     * @return
     */
    Map selectLastConfirmTime();

    /**
     * 查询游戏记录
     * @param map
     * @return
     */
    List<Map<String, Object>> selectPlayerBetRecords(Map map);

    /**
     * 查询游戏记录
     * 总记录条数
     * @param map
     * @return
     */
    Integer selectPlayerBetTotalRecords(Map map);
}