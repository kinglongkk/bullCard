package g.service.chesscard.engine.evaluator;

import g.model.bet.BetTypeEnum;
import g.model.chesscard.mo.UserBet;
import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import g.model.chesscard.enums.BullBetBaoSuffixEnum;
import g.service.chesscard.engine.model.CardsHolder;
import g.service.chesscard.engine.model.CardsType;
import g.service.engine.model.BaseTestCase;
import org.junit.Test;
import org.soul.commons.log.Log;
import org.soul.commons.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tony on 2016/11/2.
 */
public class Bull100EvaluatorTest extends BaseTestCase {

    private static final Log log = LogFactory.getLog(Bull100EvaluatorTest.class);

    @Autowired
    private Bull100Evaluator bull100Evaluator;

    @Test
    public void evaluator(){
        while (true) {
            Integer deskId = 52;
            CardsHolder cardsHolder = bull100Evaluator.evaluate(getUserBetList(new Random().nextInt(5)),deskId,true, null, 100000);
            log.debug("\n"+getPoker(cardsHolder));
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPoker(CardsHolder cardsHolder){
        StringBuffer result = new StringBuffer();
        result.append("np:");
        for(CardsType type:CardsType.values()){
            result.append(cardsHolder.getCards(type).toString());
        }
        result.append("\n");
        result.append("op:");
        for(int i = 0 ;i < cardsHolder.getPrimitiveCards().length;i++){
            result.append(cardsHolder.getPrimitiveCards()[i].toString());
        }
       return result.toString();
    }

    private String getLine(int num){
        int lineNum = (int) (num * 0.000001);
        StringBuffer line = new StringBuffer();
        for(int i = 0 ; i < lineNum ; i++){
            line.append("-");
        }
        return line.toString();
    }


    private List<UserBet> getUserBetList(int playerSize){
        List<UserBet> userBets = new ArrayList<>();
        int [] players = new int[]{1,2,3,4,5};
        for(int i = 0;i < playerSize;i++){
            int playerId = players[i];

            UserBet userBet =  new UserBet();
            userBet.setUserId(playerId);
            userBet.setBetType(BetTypeEnum.BULL_100.getCode());

            StringBuffer betItem = new StringBuffer();
            Bull100BetItemPrefixEnum[] betWLItemPrefixEna = Bull100BetItemPrefixEnum.values();
            int randomIndex = new Random().nextInt(4);
            betItem.append(betWLItemPrefixEna[randomIndex].getCode());
            boolean randomWL = new Random().nextBoolean();
            if(randomWL){
                betItem.append("_").append(BullBetBaoSuffixEnum.W.getCode());
            }else{
                betItem.append("_").append(BullBetBaoSuffixEnum.L.getCode());
            }
            userBet.setBetItem(betItem.toString());
            int randomCoin = new Random().nextInt(10);
            double coin = randomCoin * 100000;
            userBet.setCoin(coin);
            userBets.add(userBet);
        }
        return userBets;
    }



}
