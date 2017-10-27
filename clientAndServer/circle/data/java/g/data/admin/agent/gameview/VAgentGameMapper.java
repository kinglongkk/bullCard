package g.data.admin.agent.gameview;

import g.model.admin.agent.gameview.po.VAgentGame;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;
import java.util.Map;


/**
 * 数据访问对象
 *
 * @author black
 * @time 2016-12-19 11:01:49
 */
//region your codes 1
public interface VAgentGameMapper extends IBaseQueryMapper<VAgentGame, Integer> {
//endregion your codes 1

    //region your codes 2
    /**
     * 查找所属代理游戏
     * @param map
     * @return
     */
    List<Map<String, Object>> selectAgentGame(Map map);

    /**
     * 查找所属代理游戏总条数
     * @param map
     * @return
     */
    Integer selectAgentGameRecordNumber(Map map);
    //endregion your codes 2

}