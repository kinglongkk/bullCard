package g.service.chesscard.engine.manager;

import g.common.tool.DoubleTool;
import g.model.bet.po.Bet;
import g.model.bet.po.IBetSettle;
import g.model.cache.CacheKey;
import g.model.player.po.UserPlayer;
import g.service.chesscard.RedisKeyConst;
import org.springside.modules.nosql.redis.JedisTemplate;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class CoinPiple implements JedisTemplate.PipelineAction {

    private List<? extends IBetSettle> betSettles;
    private Double incBankerCoinTotal = 0.0;
    private Integer dealerUserId = null;
    private Long matchId;

    @Override
    public List<Object> action(Pipeline pipeline) {
        pipeline.multi();

        for (IBetSettle betSettle : betSettles) {
            Bet bet = (Bet) betSettle;
            Integer userId = bet.getSysUserId();
            Double incCoin = bet.getIncCoin();
            incBankerCoinTotal = DoubleTool.add(incBankerCoinTotal, bet.getIncBankerCoin());
            matchId = bet.getMatchId();
            if (incCoin != null && incCoin != 0.0) {
                pipeline.hincrBy(
                        CacheKey.getCacheKey(RedisKeyConst.USERS, RedisKeyConst.U, userId),
                        UserPlayer.PROP_COIN,
                        incCoin.longValue()
                );
            }
            if (dealerUserId == null && bet.getDealerUserId() != null) {
                dealerUserId = bet.getDealerUserId();
            }
        }
        pipeline.exec();
        return pipeline.syncAndReturnAll();
    }

    public Double getIncBankerCoinTotal() {
        return incBankerCoinTotal;
    }

    public void setBetSettles(List<? extends IBetSettle> betSettles) {
        this.betSettles = betSettles;
    }

    public Integer getDealerUserId() {
        return dealerUserId;
    }

    public Long getMatchId() {
        return matchId;
    }
}