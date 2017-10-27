package g.model.chesscard.mo;

import g.model.bet.BetItem;
import g.model.bet.BetTypeEnum;
import g.model.bet.IBetItem;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Jason on 16/10/9.
 */
public class UserBet {
    private Integer userId;
    private Integer seatNo;
    private String betType;
    private String betItem;
    private Double coin;
    private boolean isAi;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getBetItem() {
        return betItem;
    }

    public void setBetItem(String betItem) {
        this.betItem = betItem;
    }

    public Double getCoin() {
        return coin;
    }

    public void setCoin(Double coin) {
        this.coin = coin;
    }

    public boolean isAi() {
        return isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }

    public String getBetItemSubfix(){
        BetTypeEnum betTypeEnum  = BetTypeEnum.enumOf(betType);
        IBetItem item = BetItem.fromString(betTypeEnum,betItem);
        ICodeEnum suffix = item.getSuffixEnum();
        return suffix == null ? null : suffix.getCode();
    }

    public String getBetItemPrefix(){
        BetTypeEnum betTypeEnum  = BetTypeEnum.enumOf(betType);
        IBetItem item = BetItem.fromString(betTypeEnum,betItem);
        String prefix = item.getPrefixEnum().getCode();
        return prefix;
    }

}
