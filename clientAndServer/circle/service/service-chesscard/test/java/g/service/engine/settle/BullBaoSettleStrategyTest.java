package g.service.engine.settle;

import g.common.tool.DoubleTool;
import g.model.bet.BetResultEnum;
import g.model.bet.po.VBetSettle;
import g.service.engine.model.BaseTestCase;
import g.service.param.IParamService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by longer on 2017/1/22.
 */
public class BullBaoSettleStrategyTest extends BaseTestCase{

    @Autowired
    private BullBaoSettleStrategy bullBaoSettleStrategy;

    String[][] bets =  {
        //singleAmount      bei     WIN|LOST        incCoin    incBlance
            {"100",         "12",         "WIN",     "1200",    "1100"},
            {"100",         "12",         "LOSE",     "0",       "-100"},

    };

    @Test
    public void calc() throws Exception {
        VBetSettle betSettle = null;

        for (int i = 0; i < bets.length; i++) {
            betSettle = new VBetSettle();
            betSettle.setSingleAmount(Double.valueOf(bets[i][0]));
            betSettle.setPoint(Double.valueOf(bets[i][1]));
            bullBaoSettleStrategy.calc(betSettle, BetResultEnum.enutmOf(bets[i][2]));

            assertEquals(betSettle.getIncCoin(),Double.valueOf(bets[i][3]));
            assertEquals(betSettle.getIncBalance(),Double.valueOf(bets[i][4]));
        }
    }

}