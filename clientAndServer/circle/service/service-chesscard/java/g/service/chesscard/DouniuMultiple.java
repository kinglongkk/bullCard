package g.service.chesscard;

import g.model.bet.BetTypeEnum;
import org.soul.commons.lang.string.StringTool;

/**
 * Created by Jason on 16/9/27.
 */
@Deprecated
public class DouniuMultiple {
    /**
     * 斗牛百人大战最大倍数
     */
    private static Integer DOUNIU_WL_MAX_MULTIPLE = 4;
    /**
     * 斗牛夺宝最大倍数
     */
    private static Integer DOUNIU_POINT_MAX_MULTIPLE = 1;

    /**
     * 第一档的倍数
     */
    private static Integer MULTIPLE_1 = 1;
    /**
     * 第二档的倍数
     */
    private static Integer MULTIPLE_2 = 2;
    /**
     * 第三档的倍数
     */
    private static Integer MULTIPLE_3 = 3;
    /**
     * 第四档的倍数
     */
    private static Integer MULTIPLE_4 = 4;

    private static Double PROFIT_FEE = 0.98;


    /**
     * 获取斗牛中盈利的费率
     *
     * @return
     */
    public static Double getDouniuProfitFee() {
        return PROFIT_FEE;
    }

    /**
     * 根据类型获取最大倍数
     *
     * @param betType
     * @return
     */
    public static Integer getDouniuMaxMultiple(String betType) {
        if (StringTool.equals(betType, BetTypeEnum.BULL_BAO.getCode())) {
            return DOUNIU_POINT_MAX_MULTIPLE;
        } else if (StringTool.equals(betType, BetTypeEnum.BULL_100.getCode())) {
            return DOUNIU_WL_MAX_MULTIPLE;
        }
        return 1;
    }

    /**
     * 根据点数得到斗牛的倍数
     *
     * @param point
     * @return
     */
    public static Integer getDouniuMultiple(Integer point) {
        switch (point) {
            case 8:
                return MULTIPLE_2;
            case 9:
                return MULTIPLE_3;
            case 10:
                return MULTIPLE_4;
        }
        return MULTIPLE_1;//0-7
    }
}
