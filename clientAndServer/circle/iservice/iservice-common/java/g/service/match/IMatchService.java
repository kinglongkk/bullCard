package g.service.match;

import g.model.match.po.Match;
import org.soul.iservice.support.IBaseService;
import g.model.match.vo.MatchListVo;
import g.model.match.vo.MatchVo;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;


/**
 * 服务接口
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public interface IMatchService extends IBaseService<MatchListVo, MatchVo, Match, Long> {
//endregion your codes 1

    //region your codes 2

    /**
     * 合并比赛数据
     * @param matches
     */
    void merge(List<Match> matches);

    /**
     * 通过Gid获取比赛
     * @param vo
     * @return
     */
    Match byCode(MatchVo vo);

    /**
     * 获取未结束的比赛
     * @return
     */
    List<Match> getUnEndList();

    /**
     * 未有赛果的数据
     * @param timeZone
     * @return
     */
    List<String> unResulted(TimeZone timeZone);

    /**
     * 指定Gid结束与未结束的赛事
     * @param matchListVo
     * @return
     */
    Map<Integer,Long> inGidsAndResulted(MatchListVo matchListVo);

    /**
     * 未结束的赛事
     */
    List<Match> byGameTypeAndBallTypeAndGameOverNoResulted(MatchListVo matchListVo);

    /**
     * 根据期号获取赛事
     * @param code
     * @return
     */
    Match getThisMatch(String code);

    Match get(Long matchId);


    void updateMatchResult(Match match);

    void updateMatchDealer(Match match);
    //endregion your codes 2

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
}