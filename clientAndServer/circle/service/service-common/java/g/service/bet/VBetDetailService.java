package g.service.bet;

import g.data.bet.VBetDetailMapper;
import g.model.bet.po.VBetDetail;
import g.model.bet.vo.VBetDetailListVo;
import g.model.bet.vo.VBetDetailVo;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 服务
 *
 * @author mark
 * @time 2016-7-12 14:22:28
 */
public class VBetDetailService extends BaseService<VBetDetailMapper, VBetDetailListVo, VBetDetailVo, VBetDetail, Long> implements IVBetDetailService {

    public List<Map<String, Object>> selectBetRecords(VBetDetailListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){

            paging.setTotalCount(this.mapper.selectBetTotalRecords(queryParams));
            paging.cal();
        }
        return this.mapper.selectBetRecords(queryParams);
    }

    public List<Map<String, Object>> selectPlayerBetRecords(Map map) {

        return this.mapper.selectPlayerBetRecords(map);
    }

    public Integer selectPlayerBetTotalRecords(Map map) {

        return this.mapper.selectPlayerBetTotalRecords(map);
    }

    public VBetDetailListVo selectCurrentReport(VBetDetailListVo listVo) {
        if(listVo.getSearch().getUsername()==null){
            listVo.getSearch().setId(0L);
        }
        Map<String, Object> queryParams = listVo.getQueryParams(listVo.getQuery().searchCriteria());
        listVo.setResult(this.mapper.selectCurrentReport(queryParams));
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectCurrentReportNumber(queryParams));
            paging.cal();
        }
        return selectTotalAmount(listVo);
    }

    public VBetDetailListVo selectCurrentReportDetail(VBetDetailListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams(listVo.getQuery().searchCriteria());
        listVo.setResult(this.mapper.selectCurrentReportDetail(queryParams));
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectCurrentReportDetailNumber(queryParams));
            paging.cal();
        }
        return selectTotalAmount(listVo);
    }

    public VBetDetailListVo selectTotalAmount(VBetDetailListVo listVo) {

        Double totalProfitAmount = 0d;
        Double totalEffectiveAmount = 0d;
        for (int i = 0, length = listVo.getResult().size(); i < length; i++) {
            Double profitAmount = listVo.getResult().get(i).getProfitAmount();
            Double effectiveAmount = listVo.getResult().get(i).getEffectiveAmount();
            totalProfitAmount += profitAmount != null ? profitAmount : 0;
            totalEffectiveAmount += effectiveAmount != null ? effectiveAmount : 0;
        }
        listVo.setTotalProfitAmount(totalProfitAmount);
        listVo.setTotalEffectiveAmount(totalEffectiveAmount);
        return listVo;
    }

    public VBetDetailListVo setSearchCondition(VBetDetailListVo listVo) {

        listVo.setOwnerId(listVo.getSearch().getOwnerId());
        listVo.setSysUserId(listVo.getSearch().getSysUserId());
        listVo.setGameId(listVo.getSearch().getGameId());
        listVo.setGameModelId(listVo.getSearch().getGameModelId());
        return listVo;
    }

    public List<VBetDetail> selectCurrentReportExportData(VBetDetailListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams(listVo.getQuery().searchCriteria());
        return this.mapper.selectCurrentReportExportData(queryParams);
    }

    public List<VBetDetail> selectCurrentReportDetailExportData(VBetDetailListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams(listVo.getQuery().searchCriteria());
        return this.mapper.selectCurrentReportDetailExportData(queryParams);
    }

    public Date selectLastConfirmTime() {

        Map map = this.mapper.selectLastConfirmTime();
        return (Date) map.get("confirmtime");
    }

}