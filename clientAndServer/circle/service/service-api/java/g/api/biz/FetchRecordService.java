package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameFetchRecordService;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.BetRecordParams;
import g.model.api.param.Params;
import g.model.api.result.ListResults;
import g.model.bet.vo.VBetDetailListVo;
import g.service.bet.IVBetDetailService;
import org.soul.commons.lang.DateTool;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FetchRecordService extends BaseService implements IGameFetchRecordService {

    @Autowired
    private IVBetDetailService detailService;

    @Override
    public ListResults fetchRecord(BetRecordParams params) {

        ListResults result = new ListResults();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        SysUser player = getPlayer(getPlayerAccount(userAgent.getId(), params.getUserAccount()), userAgent.getAgentId());
        if (player == null){
            throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
        }
        VBetDetailListVo listVo = new VBetDetailListVo();
        if (params.getPageSize() != null){
            listVo.getPaging().setPageSize(params.getPageSize());
        }
        //分页
        if (params.getPageNo() != null){
            listVo.getPaging().setPageNumber(params.getPageNo());
        }
        //设置条件
        listVo.getSearch().setGameId(Integer.parseInt(params.getMerchantGameNo()));
        listVo.getSearch().setSysUserId(player.getId());
        listVo.getSearch().setStartTime(params.getStartTime());
        listVo.getSearch().setEndTime(params.getEndTime());
        List<Map<String , Object>> resultList = detailService.selectBetRecords(listVo);
        result.setPageNo(params.getPageNo());
        result.setPageSize(listVo.getPaging().getPageSize());
        result.setPageTotalSize((int) listVo.getPaging().getTotalCount());
        result.setDatas(resultList);
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        BetRecordParams betRecordParam = (BetRecordParams)params;
        Date startTime = betRecordParam.getStartTime();
        Date endTime = betRecordParam.getEndTime();
        //查询时间不能为空
        if (startTime == null || endTime == null) {
            return false;
        }
        long startTimeAndEndTime = DateTool.daysBetween(startTime, endTime);
        //查询时间跨度大于30天.
        if (startTimeAndEndTime > 30) {
            return false;
        }
        long startTimeAndNow = DateTool.daysBetween(startTime, new Date());
        //不支持开始时间与当前时间跨度大于90天的查询.
        if (startTimeAndNow > 90) {
            return false;
        }
        if (betRecordParam.getUserAccount() != null) {
            return true;
        } else {
            return false;
        }
    }

}
