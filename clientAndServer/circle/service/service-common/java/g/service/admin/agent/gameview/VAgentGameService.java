package g.service.admin.agent.gameview;

import g.data.admin.agent.gameview.VAgentGameMapper;
import g.model.admin.agent.gameview.po.VAgentGame;
import g.model.admin.agent.gameview.vo.VAgentGameListVo;
import g.model.admin.agent.gameview.vo.VAgentGameVo;
import org.soul.commons.query.Paging;
import org.soul.service.support.BaseService;

import java.util.List;
import java.util.Map;


/**
 * 服务
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public class VAgentGameService extends BaseService<VAgentGameMapper, VAgentGameListVo, VAgentGameVo, VAgentGame, Integer> implements IVAgentGameService {
//endregion your codes 1

    //region your codes 2
    public List<Map<String, Object>> selectAgentGame(VAgentGameListVo listVo) {

        Map<String, Object> queryParams = listVo.getQueryParams();
        //分页操作
        Paging paging = listVo.getPaging();
        if (paging != null){

            paging.setTotalCount(this.mapper.selectAgentGameRecordNumber(queryParams));
            paging.cal();
        }
        return this.mapper.selectAgentGame(queryParams);
    }
    //endregion your codes 2

}