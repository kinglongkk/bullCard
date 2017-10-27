package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGamePlayerProfitAndLossService;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.Params;
import g.model.api.param.ProfitAndLossParams;
import g.model.api.result.ListResults;
import g.service.profitandloss.IPlayerProfitAnsLossService;
import org.soul.commons.lang.DateTool;
import org.soul.model.security.privilege.po.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 玩家盈亏
 * Created by black on 2017/6/1.
 */
public class PlayerProfitAndLossService extends BaseService implements IGamePlayerProfitAndLossService {

    @Autowired
    private IPlayerProfitAnsLossService profitAnsLossService;

    public ListResults profitAndLoss(ProfitAndLossParams params) {

        ListResults result = new ListResults();
        //设置查询参数
        Map map = new HashMap();
        if (!validatePreConditions(params)) {
            throwBusinessException(ResultStatusEnums.VALIDATE_PARAMS_FAILED);
        }
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        map.put("agentId", userAgent.getAgentId());
        if (params.getUserAccount() != null) {
            SysUser player = getPlayer(getPlayerAccount(userAgent.getId(), params.getUserAccount()), userAgent.getAgentId());
            if (player == null) {
                throwBusinessException(ResultStatusEnums.USER_NOT_EXIST);
            }
            map.put("playerId", player.getId());
        }
        //设置分页参数
        if (params.getPageNo() == null) {
            map.put("pageNo", 1);
        } else {
            map.put("pageNo", params.getPageNo());
        }
        if (params.getPageSize() == null) {
            map.put("pageSize", 20);
        } else {
            map.put("pageSize", params.getPageSize());
        }
        map.put("startTime", params.getStartTime());
        map.put("endTime", params.getEndTime());
        result.setPageNo((Integer)map.get("pageNo"));
        result.setPageSize((Integer)map.get("pageSize"));
        result.setPageTotalSize(profitAnsLossService.selectCount(map));
        result.setDatas(profitAnsLossService.selectPlayerProfitAndLoss(map));
        return result;
    }

    @Override
    public boolean validatePreConditions(Params params) {

        ProfitAndLossParams param = (ProfitAndLossParams)params;
        Integer startTime = param.getStartTime();
        Integer endTime = param.getEndTime();
        //查询时间不能为空
        if (startTime == null || endTime == null) {
            return false;
        } else {
            String startString = startTime.toString();
            String endString = endTime.toString();
            if (startString.length() != 8 || endString.length() != 8) {
                return false;
            }
            Date startDate = DateTool.parseDate(startString, DateTool.UNFMT_yyyyMMdd);
            Date endDate = DateTool.parseDate(endString, DateTool.UNFMT_yyyyMMdd);
            long startTimeAndEndTime = DateTool.daysBetween(startDate, endDate);
            //查询时间跨度大于30天.
            if (startTimeAndEndTime > 30) {
                return false;
            }
            long startTimeAndNow = DateTool.daysBetween(startDate, new Date());
            //不支持开始时间与当前时间跨度大于90天的查询.
            if (startTimeAndNow > 90) {
                return false;
            }
            return true;
        }
    }

}
