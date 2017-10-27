package g.service.bet;

import org.soul.iservice.support.IBaseService;
import g.model.bet.po.VBetDetail;
import g.model.bet.vo.VBetDetailListVo;
import g.model.bet.vo.VBetDetailVo;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 服务接口
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
//region your codes 1
public interface IVBetDetailService extends IBaseService<VBetDetailListVo, VBetDetailVo, VBetDetail, Long> {
//endregion your codes 1

    /**
     * 玩家投注记录
     * @param listVo
     * @return List<Map<String, Object>>
     */
    List<Map<String, Object>> selectBetRecords(VBetDetailListVo listVo);

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

    /**
     * 玩家今日投注记录 分组
     * @param listVo
     * @return VBetDetailListVo
     */
    VBetDetailListVo selectCurrentReport(VBetDetailListVo listVo);

    /**
     * 玩家今日投注记录 详细
     * @param listVo
     * @return VBetDetailListVo
     */
    VBetDetailListVo selectCurrentReportDetail(VBetDetailListVo listVo);

    /**
     * 页面统计额
     * @param listVo
     * @return VBetDetailListVo
     */
    VBetDetailListVo selectTotalAmount(VBetDetailListVo listVo);

    /**
     * 设置页面参数 保证参数分页功能
     * @param listVo
     * @return VBetDetailListVo
     */
    VBetDetailListVo setSearchCondition(VBetDetailListVo listVo);

    /**
     * 玩家今日投注记录 分组 导出报表数据
     * @param listVo
     * @return List<VBetDetail>
     */
    List<VBetDetail> selectCurrentReportExportData(VBetDetailListVo listVo);

    /**
     * 玩家今日投注记录 详细 导出报表数据
     * @param listVo
     * @return List<VBetDetail>
     */
    List<VBetDetail> selectCurrentReportDetailExportData(VBetDetailListVo listVo);

    /**
     * 查询赛事最后的结算时间
     * @return
     */
    Date selectLastConfirmTime();
}