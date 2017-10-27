package g.service.chesscard.engine.manager;

import com.alibaba.fastjson.JSONArray;
import g.model.match.po.Match;
import g.model.match.po.MatchResult;
import g.model.match.vo.MatchResultVo;
import g.service.chesscard.engine.bull100.message.Bull100SettleMessage;
import g.service.chesscard.engine.model.BullCards;
import g.service.chesscard.engine.model.CardsHolder;
import g.service.chesscard.engine.model.CardsType;
import g.service.engine.manager.IDealerManager;
import g.service.engine.manager.IDeskManager;
import g.service.engine.message.IMessageSender;
import g.service.match.IMatchResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by stone on 2017/1/11.
 */
public class BullBaoSettleManager extends SettleManager {

    @Autowired
    private Bull100SettleMessage settleMessage;

    @Autowired
    @Qualifier("syncMessageSender")
    private IMessageSender messageSender;

    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private IDealerManager dealerManager;

    @Autowired
    private IMatchResultService matchResultService;

    @Override
    public List<MatchResult> getMatchResult(Match match, CardsHolder cardsHolder, int[] bankResult, Integer bankPoint) {
        List<MatchResult> matchResults = new ArrayList<MatchResult>();

        BullCards cards = cardsHolder.getCards(CardsType.BANKER);

        MatchResult matchResult = new MatchResult();
        matchResult.setMatchId(match.getId());
        int point = cards.getCardsPoint();
        matchResult.setItemType(CardsType.BANKER.getCode());
        matchResult.setHost(JSONArray.toJSONString(bankResult));
        matchResult.setHostPoint(bankPoint);
        matchResult.setClient(JSONArray.toJSONString(cards.getCards()));
        matchResult.setClientPoint(point);
        matchResult.setCreateTime(new Date());

        matchResult.setOutcome(cards.isBigger() ? "1" : "0");

        matchResults.add(matchResult);

        MatchResultVo matchResultVo = new MatchResultVo();
        matchResultVo.setResult(matchResult);
        matchResultService.insert(matchResultVo);

        return matchResults;
    }

}
