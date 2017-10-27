package g.service.chesscard.engine.listener;

import g.model.match.po.Match;

/**
 * Created by Double on 2016/9/30.
 */
public class MatchEvent extends DeskEvent {

    /**
     * 赛事ID
     */
    private Long matchId;

    /**
     * 赛事
     */
    private Match match;

    public MatchEvent(Integer deskId) {
        super(deskId);
    }

    public MatchEvent(Integer deskId, Long matchId) {
        super(deskId);
        this.matchId = matchId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
