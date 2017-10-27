package g.service.chesscard.engine.model;

import g.model.bet.SettleResultEnum;
import g.model.chesscard.mo.UserBetResult;

import java.util.List;

/**
 * Created by Jason on 2016/10/14
 */
public class SettleResult {
    private SettleResultEnum settleResultEnum;
    private CardsHolder cardsHolder;
    private List<UserBetResult> userBetResultList;

    public SettleResultEnum getSettleResultEnum() {
        return settleResultEnum;
    }

    public void setSettleResultEnum(SettleResultEnum settleResultEnum) {
        this.settleResultEnum = settleResultEnum;
    }

    public List<UserBetResult> getUserBetResultList() {
        return userBetResultList;
    }

    public void setUserBetResultList(List<UserBetResult> userBetResultList) {
        this.userBetResultList = userBetResultList;
    }

    public CardsHolder getCardsHolder() {
        return cardsHolder;
    }

    public void setCardsHolder(CardsHolder cardsHolder) {
        this.cardsHolder = cardsHolder;
    }
}
