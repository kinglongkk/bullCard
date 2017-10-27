package g.service.report;

import org.soul.iservice.support.IBaseService;
import g.model.report.po.ReportCqssc;
import g.model.report.vo.ReportCqsscListVo;
import g.model.report.vo.ReportCqsscVo;
import java.util.Map;


/**
 * 服务接口
 *
 * @author tom
 * @time 2016-8-1 15:34:20
 */
//region your codes 1
public interface IReportCqsscService extends IBaseService<ReportCqsscListVo, ReportCqsscVo, ReportCqssc, Integer> {
//endregion your codes 1

    /**
     * 生成报表数据
     * @param map
     * @return Integer
     */
    Integer genReport(Map<String,Object> map);

    //endregion your codes 2

    /**
     * 页面数据显示 分组查询
     * @param listVo
     * @return List<ReportCqssc>
     */
    ReportCqsscListVo showTotalNumber(ReportCqsscListVo listVo);


    /**
     * 页面各项值汇总
     * @param listVo
     * @param selectType 查询类型
     * @return ReportCqsscListVo
     */
    ReportCqsscListVo selectTotalValue(ReportCqsscListVo listVo, String selectType);

}