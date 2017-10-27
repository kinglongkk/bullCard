package g.api.biz;

import g.api.base.BaseService;
import g.api.service.IGameSelectService;
import g.model.admin.agent.gameview.vo.VAgentGameListVo;
import g.model.admin.agent.message.po.UserAgent;
import g.model.api.enums.ResultStatusEnums;
import g.model.api.param.GameSelectParams;
import g.model.api.result.ListResults;
import g.service.admin.agent.gameview.IVAgentGameService;
import org.soul.commons.lang.string.StringTool;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by black on 2016/12/19.
 */
public class GameSelectService extends BaseService implements IGameSelectService {

    @Autowired
    private IVAgentGameService gameService;

    public ListResults gameSelect(GameSelectParams params) {

        ListResults result = new ListResults();
        UserAgent userAgent = getUserAgent(params.getMerchantNo());
        if (userAgent == null) {
            throwBusinessException(ResultStatusEnums.AGENT_NOT_EXISTS);
        }
        VAgentGameListVo listVo = new VAgentGameListVo();
        //分页
        if (params.getPageSize() != null) {
            listVo.getPaging().setPageSize(params.getPageSize());
        }
        if (params.getPageNo() != null) {
            listVo.getPaging().setPageNumber(params.getPageNo());
        }
        if (params.getGameType() != null && !StringTool.isBlank(params.getGameType())) {
            listVo.getSearch().setType(params.getGameType());
        }
        List<Map<String , Object>> resultList = gameService.selectAgentGame(listVo);
        result.setPageNo(params.getPageNo());
        result.setPageSize(listVo.getPaging().getPageSize());
        result.setPageTotalSize((int) listVo.getPaging().getTotalCount());
        result.setDatas(resultList);
        return result;
    }
}
