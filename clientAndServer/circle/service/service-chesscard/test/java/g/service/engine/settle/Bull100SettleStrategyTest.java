package g.service.engine.settle;

import g.model.bet.BetResultEnum;
import g.model.bet.po.VBetSettle;
import g.service.chesscard.engine.EngineConst;
import g.service.engine.model.BaseTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created by Double on 2017/1/22.
 *
 */
public class Bull100SettleStrategyTest extends BaseTestCase{

    @Autowired
    private Bull100SettleStrategy bull100SettleStrategy;

    String[][] bets =  {
            //singleAmount  bei     WIN|LOST        incCoin    incBlance
            {"100",         "1",         "WIN",     "198",    "98"},
            {"100",         "1",         "LOSE",     "0",    "-100"},
            {"100",         "2",         "WIN",     "296",    "196"},
            {"100",         "2",         "LOSE",     "-100",    "-200"},
            {"100",         "3",         "WIN",     "394",    "294"},
            {"100",         "3",         "LOSE",     "-200",    "-300"},
            {"100",         "4",         "WIN",     "492",       "392"},
            {"100",         "4",         "LOSE",     "-300",       "-400"},

    };

    @Test
    public void calc() throws Exception {
        VBetSettle betSettle = null;

        for (int i = 0; i < bets.length; i++) {
            betSettle = new VBetSettle();
            betSettle.setWaterPoint(EngineConst.WATER_POINT);
            betSettle.setSingleAmount(Double.valueOf(bets[i][0]));
            betSettle.setPoint(Double.valueOf(bets[i][1]));
            bull100SettleStrategy.calc(betSettle, BetResultEnum.enutmOf(bets[i][2]));

            assertEquals(betSettle.getIncCoin(),Double.valueOf(bets[i][3]));
            assertEquals(betSettle.getIncBalance(),Double.valueOf(bets[i][4]));
        }
    }



}