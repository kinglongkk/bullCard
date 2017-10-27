package g.service.chesscard.engine.manager;

import com.alibaba.fastjson.JSONArray;
import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import g.model.match.po.Match;
import g.model.match.po.MatchResult;
import g.model.match.vo.MatchResultVo;
import g.service.chesscard.engine.bull100.message.Bull100SettleMessage;
import g.service.chesscard.engine.bull100.message.Bull100WarningCoinMessage;
import g.service.chesscard.engine.bull100.message.Bull100WarningDealerMessage;
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
public class Bull100SettleManager extends SettleManager {


    @Autowired
    private IMatchResultService matchResultService;

    @Override
    public List<MatchResult> getMatchResult(Match match, CardsHolder cardsHolder, int[] bankResult, Integer bankPoint) {
        List<MatchResult> matchResults = new ArrayList<MatchResult>();
        // 添加赛事结果
        for (Bull100BetItemPrefixEnum betWLItemPrefixEnum : Bull100BetItemPrefixEnum.values()) {
            BullCards playerCards = cardsHolder.getCards(CardsType.enumOf(betWLItemPrefixEnum.getCode()));
            int[] playerResult = playerCards.getCards();
            Integer playerPoint = playerCards.getCardsPoint();
            Boolean isBig = playerCards.isBigger();

            MatchResult matchResult = new MatchResult();
            matchResult.setMatchId(match.getId());
            matchResult.setItemType(betWLItemPrefixEnum.getCode());
            matchResult.setHost(JSONArray.toJSONString(bankResult));
            matchResult.setHostPoint(bankPoint);
            matchResult.setClient(JSONArray.toJSONString(playerResult));
            matchResult.setClientPoint(playerPoint);
            matchResult.setCreateTime(new Date());
            if (isBig) {
                matchResult.setOutcome("1");
            } else {
                matchResult.setOutcome("0");
            }
            matchResults.add(matchResult);
        }
        MatchResultVo matchResultVo = new MatchResultVo();
        matchResultVo.setEntities(matchResults);
        matchResultService.batchInsert(matchResultVo);
        return matchResults;
    }
}
