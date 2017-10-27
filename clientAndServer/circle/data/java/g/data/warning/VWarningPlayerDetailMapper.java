package g.data.warning;

import g.model.warning.po.MatchPlayer;
import g.model.warning.po.VWarningPlayerDetail;
import org.soul.data.rdb.mybatis.IBaseQueryMapper;

import java.util.List;
import java.util.Map;


/**
 * 数据访问对象
 *
 * @author lenovo
 * @time 2017-2-28 11:03:58
 */
//region your codes 1
public interface VWarningPlayerDetailMapper extends IBaseQueryMapper<VWarningPlayerDetail, Long> {
//endregion your codes 1

    //region your codes 2

    //endregion your codes 2
    List<MatchPlayer> getMatchList(Map<String, Object> queryMap);

    Integer selectTotalRecords(Map<String, Object> queryMap);

}