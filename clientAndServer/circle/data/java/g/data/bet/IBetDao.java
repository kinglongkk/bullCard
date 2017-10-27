package g.data.bet;

import g.model.bet.po.VBetSettle;
import g.model.bet.vo.BetVo;
import g.model.gameroom.JackpotAdd;

import java.util.List;

/**
 * Created by longer on 5/26/16.
 */
public interface IBetDao {

    /**
     * 批量节算
     * @param betVos
     * @param isReSettle
     * @return
     */
    int[] batchSettle(List<BetVo> betVos, boolean isReSettle, JackpotAdd jackpotAdd);

    /**
     * 注单取消
     * @param betIds
     * @return
     */
    int[] betCancel(List<VBetSettle> betIds);

}
