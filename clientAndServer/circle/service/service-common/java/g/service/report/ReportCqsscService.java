package g.service.report;

import org.soul.commons.query.Criteria;
import org.soul.commons.query.Paging;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;
import g.data.report.ReportCqsscMapper;
import g.model.report.po.ReportCqssc;
import g.model.report.vo.ReportCqsscListVo;
import g.model.report.vo.ReportCqsscVo;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务
 *
 * @author tom
 * @time 2016-8-1 15:34:21
 */
//region your codes 1
public class ReportCqsscService extends BaseService<ReportCqsscMapper, ReportCqsscListVo, ReportCqsscVo, ReportCqssc, Integer> implements IReportCqsscService {
//endregion your codes 1

    //region your codes 2

    @Override
    public Integer genReport(Map<String, Object> map) {
        Object matchId = map.get("matchId");
        Criteria criteria = Criteria.add(ReportCqssc.PROP_MATCH_ID, Operator.EQ, matchId);
        criteria.addAnd(ReportCqssc.PROP_IS_DELETED, Operator.EQ, false);
        Map<String, Object> updateColumnMap = new HashMap<>();
        updateColumnMap.put(ReportCqssc.PROP_IS_DELETED, true);
        int batchUpdateProperties = this.mapper.batchUpdateProperties(criteria, updateColumnMap);
        return this.mapper.genReport(map);
    }

    //endregion your codes 2

    public ReportCqsscListVo showTotalNumber(ReportCqsscListVo listVo){

        Map<String, Object> queryParams = listVo.getQueryParams();
        List<ReportCqssc> reportCqsscList = this.mapper.showTotalNumber(queryParams);

        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){

            paging.setTotalCount(this.mapper.selectTotalRecords(queryParams));
            paging.cal();
        }
        listVo.setResult(reportCqsscList);
        return listVo;
    }

    public ReportCqsscListVo selectTotalValue(ReportCqsscListVo listVo, String selectType) {

        if (selectType.equals("selectByGroup")){

            listVo = showTotalNumber(listVo);
        }else{

            listVo = search(listVo);
        }

        Integer totalNumber = 0;
        Double totalSingleAmount = 0d;
        Double totalEffectiveAmount = 0d;
        Double totalProfitAmount = 0d;
        Double totalRakebackAmount = 0d;

        for (ReportCqssc reportCqssc : listVo.getResult()) {

            totalNumber += reportCqssc.getTotalNumber() != null ? reportCqssc.getTotalNumber() : 0;
            totalSingleAmount += reportCqssc.getSingleAmount() != null ? reportCqssc.getSingleAmount() : 0;
            totalEffectiveAmount += reportCqssc.getEffectiveAmount() != null ? reportCqssc.getEffectiveAmount() : 0;
            totalProfitAmount += reportCqssc.getProfitAmount() != null ? reportCqssc.getProfitAmount() : 0;
            totalRakebackAmount += reportCqssc.getRakebackAmount() != null ? reportCqssc.getRakebackAmount() : 0;
        }

        listVo.setTotalNumber(totalNumber);
        listVo.setTotalSingleAmount(totalSingleAmount);
        listVo.setTotalEffectiveAmount(totalEffectiveAmount);
        listVo.setTotalProfitAmount(totalProfitAmount);
        listVo.setTotalRakebackAmount(totalRakebackAmount);

        return listVo;
    }
}