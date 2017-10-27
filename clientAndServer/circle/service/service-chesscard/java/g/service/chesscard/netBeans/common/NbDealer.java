package g.service.chesscard.netBeans.common;

import g.service.chesscard.engine.model.GameDealer;
import g.service.webSocket.codeCreator.NetBeanField;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tony on 2016/11/9.
 */
public class NbDealer extends NbPlayer {

    @Override
    public String toString() {
        return "\n" + ToStringBuilder.reflectionToString(this);
    }

    /**
     * 最大上庄金额(自己)
     */
    @NetBeanField
    public long maxDealerCoin;


    public static ArrayList fromGameDealers(List<GameDealer> gameDealers) {
        ArrayList list = new ArrayList();
        for (GameDealer gameDealer : gameDealers) {
            NbDealer dealer = new NbDealer();
            dealer.playerId = gameDealer.getUserId();
            dealer.usableBalance = gameDealer.getDealerCoin();
            list.add(dealer);
        }
        return list;
    }

}
