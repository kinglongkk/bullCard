package g.service.bet.settle.judge;

import g.model.bet.po.IBetSettle;
import org.soul.commons.lang.string.StringTool;
import g.model.bet.BetResultEnum;
import g.model.bet.po.VBetSettle;
import g.model.match.po.MatchResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by longer on 5/20/16.
 */
public abstract class Judge {

    private MatchResult matchResult;
    private IBetSettle betSettle;

    private Map<String,MatchResult> matchResults = new HashMap<>();

    public void setMatchResults(Map<String, MatchResult> matchResults) {
        this.matchResults = matchResults;
    }

    public void setMatchResult(MatchResult matchResult) {
        if (!this.matchResults.containsKey(matchResult.getItemType())){
            this.matchResults.put(matchResult.getItemType(), matchResult);
        }
        this.matchResult = matchResult;
    }

    public void setBetSettle(IBetSettle betSettle) {
        this.betSettle = betSettle;
    }

    public IBetSettle getBetSettle() {
        return betSettle;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    public MatchResult getMatchResult(String itemType) {
        return matchResults.get(itemType);
    }



    public void addMatchResult(MatchResult matchResult){
        this.matchResults.put(matchResult.getItemType(),matchResult);
    }

    protected MatchResult getMatchResultByItemType(String itemType){
        return this.matchResults.get(itemType);
    }

    /**
     * 判断输赢
     * @return
     */
    public abstract BetResultEnum judge();

    /**
     * 逆运算：
     *      主赢则客输
     *      主输则客赢
     *      和 则　和
     * @param resultEnum
     * @return
     */
    protected BetResultEnum inverse(BetResultEnum resultEnum){
        if (resultEnum == BetResultEnum.WIN) {
            return BetResultEnum.LOSE;
        }

        if (resultEnum == BetResultEnum.LOSE) {
            return BetResultEnum.WIN;
        }

        if (resultEnum == BetResultEnum.HE) {
            return BetResultEnum.HE;
        }
        return resultEnum;
    }

}
