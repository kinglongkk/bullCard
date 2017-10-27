package g.service.common;

import g.model.bet.po.VBetList;
import g.model.match.vo.VBetListListVo;
import org.soul.service.support.BaseService;
import g.data.bet.VBetListMapper;
import g.model.bet.vo.VBetListVo;

import java.util.List;
import java.util.Map;


/**
 * 注单服务
 *
 * @author tom
 * @time 2016-6-7 19:50:30
 */
//region your codes 1
public class VBetListService extends BaseService<VBetListMapper, VBetListListVo, VBetListVo, VBetList, Long> implements IVBetListService {
//endregion your codes 1

    //region your codes 2

    public List<VBetList> getBetStatistics(Long matchId) {
        List<VBetList> list = this.mapper.getBetStatistics(matchId);
        return list;
    }


    //endregion your codes 2

}