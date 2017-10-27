package g.service.engine.settle;

import g.data.support.GeneratIdTool;
import g.model.bet.BetResultEnum;
import g.model.bet.BetSettleStatus;
import g.model.bet.BetStatus;
import g.model.bet.po.Bet;
import g.model.bet.po.BetDetail;
import g.model.bet.po.IBetSettle;
import g.model.bet.vo.BetVo;
import g.model.enums.ChessCardEnum;
import g.model.enums.GameTypeEnum;
import g.service.bet.BetService;
import g.service.bet.settle.AbstractSettleService;
import g.service.chesscard.engine.EngineConst;
import g.service.chesscard.engine.manager.CoinPiple;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Desk;
import g.service.engine.manager.IDeskManager;
import g.service.engine.manager.IPlayerManager;

import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by longer on 5/18/16.
 * 结算服务(基于内存注单)
 */
public class MemorySettleService extends AbstractSettleService {

    private static final Log log = LogFactory.getLog(MemorySettleService.class);

    @Autowired
    private IPlayerManager playerManager;


    @Autowired
    private IDeskManager deskManager;

    @Autowired
    private BetService betService;

    @Override
    public void reSettle(Long matchId) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void preSaveSettleByBatch(List<BetVo> bets, List<? extends IBetSettle> betSettles) {
        CoinPiple coinPiple = playerManager.batchUpdateCoin(betSettles);
        if (coinPiple.getIncBankerCoinTotal() == 0.0) {
            return;
        }

        if (coinPiple.getDealerUserId() == null) {
            //系统庄
            return;
        }
        addDealerBet(coinPiple, bets);
    }

    private void addDealerBet(CoinPiple coinPiple, List<BetVo> bets) {
        Integer userId = coinPiple.getDealerUserId();
        Player player = playerManager.get(userId);
        Integer deskId = player.getDeskId();
        Desk desk = deskManager.byId(deskId);
        long incBalance = (long) (coinPiple.getIncBankerCoinTotal() * EngineConst.WATER_POINT);
        long water = 0;
        if (coinPiple.getIncBankerCoinTotal().longValue() > 0) {
            //庄家抽水
            desk.incrDealerBalanceCoin(incBalance);
            water = coinPiple.getIncBankerCoinTotal().longValue() - incBalance;
        } else {
            desk.incrDealerBalanceCoin(coinPiple.getIncBankerCoinTotal().longValue());
        }

        Date now = new Date();
        Long[] betIds = this.betService.getBetIds(1);
        BetVo betVo = new BetVo();
        Bet bet = new Bet();
        bet.setId(betIds[0]);
        bet.setSysUserId(coinPiple.getDealerUserId());
        bet.setBetNo(GeneratIdTool.genBetNo(coinPiple.getDealerUserId()));
        if (incBalance > 0) {
            bet.setIncBalance(Double.valueOf(incBalance));
        } else {
            bet.setIncBalance(coinPiple.getIncBankerCoinTotal());
        }
        bet.setResult(
                coinPiple.getIncBankerCoinTotal() > 0 ? BetResultEnum.WIN.getCode() : coinPiple.getIncBankerCoinTotal() == 0
                        ? BetResultEnum.HE.getCode() : BetResultEnum.LOSE.getCode());
        bet.setBallType(ChessCardEnum.DOU_NIU.getCode());
        bet.setRtype(GameTypeEnum.CHESS_CARD.getCode());
        bet.setStatus(BetStatus.CONFIRMD.getCode());
        bet.setSettleStatus(BetSettleStatus.SETTLED.getCode());
        bet.setIsDeleted(false);
        bet.setBetTime(now);
        bet.setConfirmTime(now);
        bet.setSettleTime(now);
        bet.setEffectiveAmount(0D);
        bet.setSingleAmount(0D);
        bet.setProfitAmount(coinPiple.getIncBankerCoinTotal()-water);
        bet.setWaterAmount(water);
        betVo.setResult(bet);

        BetDetail betDetail = new BetDetail();
        betDetail.setBetId(bet.getId());
        betDetail.setMatchId(coinPiple.getMatchId());

        List<BetDetail> betDetailList = new ArrayList<>();
        betDetailList.add(betDetail);
        bet.setBetDetailList(betDetailList);
        bets.add(betVo);
    }

}
