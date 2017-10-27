package g.service.admin.agent.gameview;

import g.model.admin.agent.gameview.po.VAgentGame;
import g.model.admin.agent.gameview.vo.VAgentGameListVo;
import g.model.admin.agent.gameview.vo.VAgentGameVo;
import org.soul.iservice.support.IBaseService;

import java.util.List;
import java.util.Map;


/**
 * 服务接口
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public interface IVAgentGameService extends IBaseService<VAgentGameListVo, VAgentGameVo, VAgentGame, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 查找所属代理游戏
     * @param listVo
     * @return
     */
    List<Map<String, Object>> selectAgentGame(VAgentGameListVo listVo);
    //endregion your codes 2

}