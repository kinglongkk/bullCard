package g.service.match;

import g.data.match.MatchMapper;
import g.model.match.po.Match;
import g.model.match.vo.MatchListVo;
import g.model.match.vo.MatchVo;
import org.soul.commons.collections.CollectionTool;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.soul.commons.query.Criteria;
import org.soul.commons.query.enums.Operator;
import org.soul.service.support.BaseService;

import java.util.*;


/**
 * 服务
 *
 * @author longer
 * @time May 10, 2016 8:14:28 PM
 */
//region your codes 1
public class MatchService extends BaseService<MatchMapper, MatchListVo, MatchVo, Match, Long> implements IMatchService {
//endregion your codes 1

    private static final Log LOG = LogFactory.getLog(MatchService.class);
    @Override

    public void merge(List<Match> matches) {
        if (CollectionTool.isNotEmpty(matches)) {
            this.mapper.merge(matches);
        }
    }

    @Override
    public Match byCode(MatchVo vo){
        List<Match> matches = this.mapper.search(vo.getQuery().byCode2());
        if (CollectionTool.isNotEmpty(matches)) {
            return matches.get(0);
        }
        return null;
    }

    @Override
    public List<Match> getUnEndList() {
        MatchVo vo = new MatchVo();
        return mapper.search(vo.getQuery().endTimeIsNull());
    }

    @Override
    public List<String> unResulted(TimeZone timeZone){
        return mapper.unResulted(timeZone.getID());
    }

    @Override
    public Map<Integer,Long> inGidsAndResulted(MatchListVo matchListVo) {
        matchListVo.setProperties(Match.PROP_ID,Match.PROP_CODE);
        List<Map<String,Object>> rs = mapper.searchProperties(matchListVo.getQuery().inGidsAndResulted(),Arrays.asList(matchListVo.getProperties()));
        Map<Integer,Long>  reRs = new HashMap<>();
        for (Map<String, Object> r : rs) {
            Integer gid = (Integer)r.get(Match.PROP_CODE);
            Long mid = (Long)r.get(Match.PROP_ID);
            reRs.put(gid,mid);
        }
        return reRs;
    }

    @Override
    public List<Match> byGameTypeAndBallTypeAndGameOverNoResulted(MatchListVo matchListVo) {
        return mapper.search(matchListVo.getQuery().byGameTypeAndBallTypeAndGameOverNoResulted());
    }
    @Override
    public Match getThisMatch(String code) {
        List<Match> search = mapper.search(Criteria.add(Match.PROP_CODE, Operator.EQ, code));
        LOG.debug("赛事期号：{0},赛事对象{1}",code,search);
        if(CollectionTool.isNotEmpty(search)){
            return search.get(0);
        }
        return null;
    }

    public List<Map<String, Object>> selectBull100Trend(Map map){

        return this.mapper.selectBull100Trend(map);
    }

    public List<Map<String, Object>> selectGrabTrend(Map map){

        return this.mapper.selectGrabTrend(map);
    }

    @Override
    public Match get(Long matchId) {
        return this.mapper.get(matchId);
    }

    @Override
    public void updateMatchResult(Match match) {
        this.mapper.updateMatchResult(match);
    }

    @Override
    public void updateMatchDealer(Match match) {
        this.mapper.updateMatchDealer(match);
    }

    //endregion your codes 2

}