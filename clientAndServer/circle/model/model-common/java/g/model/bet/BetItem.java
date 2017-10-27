package g.model.bet;

import g.model.chesscard.enums.Bull100BetItemPrefixEnum;
import g.model.chesscard.enums.BullBaoItemPrefixEnum;
import g.model.chesscard.enums.BullBetBaoSuffixEnum;
import org.soul.commons.enums.ICodeEnum;

/**
 * Created by longer on 7/6/16.
 */
public class BetItem implements IBetItem {

    private ICodeEnum prefixEnum;
    private ICodeEnum suffixEnum;

    public BetItem(){

    }

    public BetItem(ICodeEnum prefixEnum,ICodeEnum suffixEnum) {
        this.prefixEnum = prefixEnum;
        this.suffixEnum = suffixEnum;
    }

    @Override
    public String toString() {
       return prefixEnum.getCode() + "_" + suffixEnum.getCode();
    }

    public String toDesc(){
        return suffixEnum.getTrans() + "" + prefixEnum.getTrans();
    }

    public static IBetItem fromString(BetTypeEnum betType, String fieldName){
        BetItem betItem = new BetItem();
        String[] arr = fieldName.split("_");
        String prefix = arr[0];
        String subfix = arr[1];

        switch (betType) {
            case BULL_100:
                betItem.prefixEnum = Bull100BetItemPrefixEnum.enumOf(prefix);
                betItem.suffixEnum = BullBetBaoSuffixEnum.enumOf(subfix);
                break;
            case BULL_BAO:
                betItem.prefixEnum = BullBaoItemPrefixEnum.enumOf(prefix);
                betItem.suffixEnum = BullBetBaoSuffixEnum.enumOf(subfix);
                break;
        }
        if(betItem.getPrefixEnum()!=null && betItem.getSuffixEnum()!=null){
            return betItem;
        }else{
            return null;
        }
    }

    public ICodeEnum getPrefixEnum() {
        return prefixEnum;
    }

    public ICodeEnum getSuffixEnum() {
        return suffixEnum;
    }
}
