package g.data.report;

import g.model.report.po.ReportCqssc;
import g.model.report.so.ReportCqsscSo;
import g.model.report.vo.ReportCqsscListVo;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 数据访问对象
 *
 * @author tom
 * @time 2016-8-1 15:34:20
 */
//region your codes 1
public interface ReportCqsscMapper extends IBaseMapper<ReportCqssc, Integer> {
//endregion your codes 1

    Integer genReport(Map<String, Object> map);

    //endregion your codes 2

    /**
     * 页面数据显示 分组查询
     * @param queryMap
     * @return List<ReportCqssc>
     */
    List<ReportCqssc> showTotalNumber(Map<String, Object> queryMap);

    /**
     * 分组查询总记录数
     * @return int(总条数)
     */
    Integer selectTotalRecords(Map<String, Object> queryMap);

}