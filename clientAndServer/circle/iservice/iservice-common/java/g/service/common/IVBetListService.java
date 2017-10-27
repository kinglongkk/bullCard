package g.service.common;

import g.model.bet.po.VBetList;
import g.model.match.vo.VBetListListVo;
import g.model.bet.vo.VBetListVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;
import java.util.Map;


/**
 * 注单服务接口
 *
 * @author tom
 * @time 2016-6-7 19:50:29
 */
//region your codes 1
public interface IVBetListService extends IBaseService<VBetListListVo, VBetListVo, VBetList, Long> {
//endregion your codes 1

    //region your codes 2
    List<VBetList> getBetStatistics(Long matchId);

    //endregion your codes 2

}