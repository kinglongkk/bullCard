package g.service.chesscard.exception;

import g.service.chesscard.enums.TipEnum;

import java.text.MessageFormat;

/**
 * Created by Double on 2016/9/22.
 * 棋牌类业务异常
 */
public class ChessCardException extends RuntimeException {

    private TipEnum tipEnum;
    private String transFmt;

    public ChessCardException() {
        super();
    }

    public ChessCardException(TipEnum tipEnum) {
        super(tipEnum.getTrans());
        this.tipEnum = tipEnum;
    }

    public ChessCardException(TipEnum tipEnum, String transFmt) {
        super(tipEnum.getTrans());
        this.tipEnum = tipEnum;
        this.transFmt = transFmt;
    }

    public TipEnum getTipEnum() {
        return tipEnum;
    }

    public String getTransFmt() {
        return transFmt;
    }

    @Override
    public String getMessage() {
        if (tipEnum == null) {
            return "";
        }
        return MessageFormat.format("错误代码:[{0}],说明:{1}", tipEnum.getCode(), tipEnum.getTrans());
    }
}
