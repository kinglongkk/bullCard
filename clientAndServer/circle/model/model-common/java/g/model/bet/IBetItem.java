package g.model.bet;

import org.soul.commons.enums.ICodeEnum;

/**
 * Created by Double on 7/6/16.
 */
public interface IBetItem {

    String toString();

    String toDesc();

    ICodeEnum getPrefixEnum();

    ICodeEnum getSuffixEnum();
}
