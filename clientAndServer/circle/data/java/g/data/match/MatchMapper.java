package g.data.match;

import g.model.match.po.Match;
import g.model.match.vo.MatchVo;
import org.soul.data.rdb.mybatis.IBaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 比赛表数据访问对象
 *
 * @author lenovo
 * @time 2016-9-30 16:51:10
 */
//region your codes 1
public interface MatchMapper extends IBaseMapper<Match, Long> {
//endregion your codes 1

    //region your codes 2

    /**
     * 合并赛事数据,一般用于重复的赛事采集的场景
     * @param matches
     */
    void merge(List<Match> matches);

    /**
     * 未有结果的赛事
     * @param timeZone
     * @return
     */
    List<String> unResulted(String timeZone);

    /**
     * 标识赛事结算
     * @param match
     * @return
     */
    int toSettled(MatchVo match);

    /**
     * 标识赛事取消
     * @param match
     * @return
     */
    int toCanceled(MatchVo match);

    void updateMatchResult(Match match);

    void updateMatchDealer(Match match);

    /**
     * 百人大战的胜负走势
     * @param map
     * @return
     */
    List<Map<String, Object>> selectBull100Trend(Map map);

    /**
     * 押宝大战的胜负走势
     * @param map
     * @return
     */
    List<Map<String, Object>> selectGrabTrend(Map map);
    //endregion your codes 2

}