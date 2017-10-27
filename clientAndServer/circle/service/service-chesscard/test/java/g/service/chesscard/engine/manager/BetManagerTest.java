package g.service.chesscard.engine.manager;

import g.model.bet.BetItem;
import g.model.bet.BetTypeEnum;
import g.model.bet.Bull100BetItemEnum;
import g.model.bet.IBetItem;
import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import g.model.chesscard.enums.BullBetBaoSuffixEnum;
import g.service.chesscard.AbstractHandleTestCase;
import g.service.chesscard.engine.model.Player;
import g.service.chesscard.engine.model.Room;
import g.service.engine.manager.IBetManager;
import g.service.engine.manager.IPlayerManager;
import g.service.engine.manager.IRoomManager;
import g.service.webSocket.context.WsSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * Created by Double on 2017/1/14.
 */
public class BetManagerTest extends AbstractHandleTestCase {

    @Autowired
    private IBetManager betManager;

    @Autowired
    private IPlayerManager playerManager;

    /**
     * 单人投注
     */
    @Test
    public void betting_1_player(){
        Integer userId = 1;
        login(userId);
        Room room = roomManager.get(roomId);
        Player player = room.into(userId);
        randomBet4Bull100(betManager,player);
    }

    /**
     * 随机投注
     * @param player
     */
    public static void randomBet4Bull100(IBetManager betManager,Player player){
        //随机筹码
        int[] chips = player.getDesk().getRoom().getBetChip();
        Random randomChips = new Random(chips.length);
        long coin = Long.valueOf(chips[randomChips.nextInt(chips.length)]);

        //随机投注项
        Bull100BetItemEnum[] betItemEnums = Bull100BetItemEnum.values();
        Bull100BetItemEnum bull100BetItemEnum = betItemEnums[randomChips.nextInt(betItemEnums.length)];
        BetItem betItem = new BetItem(bull100BetItemEnum.getPrefixEnum(),bull100BetItemEnum.getSuffixEnum());
        betManager.betting(player,BetTypeEnum.BULL_100,betItem,coin);
    }

    @Override
    public void after() {
        betManager.clearBetData(roomId);  //roomId == deskId
        super.after();
    }
}