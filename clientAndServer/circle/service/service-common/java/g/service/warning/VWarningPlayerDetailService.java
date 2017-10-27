package g.service.warning;

import g.model.report.po.ReportCqssc;
import g.model.report.vo.ReportCqsscListVo;
import g.model.warning.po.MatchPlayer;
import g.model.warning.vo.MatchPlayerListVo;
import g.model.warning.vo.MatchPlayerVo;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;
import g.data.warning.VWarningPlayerDetailMapper;
import g.service.warning.IVWarningPlayerDetailService;
import g.model.warning.po.VWarningPlayerDetail;
import g.model.warning.vo.VWarningPlayerDetailListVo;
import g.model.warning.vo.VWarningPlayerDetailVo;

import java.util.List;
import java.util.Map;


/**
 * 服务
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public class VWarningPlayerDetailService extends BaseService<VWarningPlayerDetailMapper, VWarningPlayerDetailListVo, VWarningPlayerDetailVo, VWarningPlayerDetail, Long> implements IVWarningPlayerDetailService {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2

    @Override
    public MatchPlayerListVo getMatchList(MatchPlayerListVo listVo){

        Map<String, Object> queryParams = listVo.getQueryParams();
        List<MatchPlayer>  matchPlayer = this.mapper.getMatchList(queryParams);
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){
            paging.setTotalCount(this.mapper.selectTotalRecords(queryParams));
            paging.cal();
        }
        listVo.setResult(matchPlayer);
        return listVo;
    }

}