package g.data.support;

import org.soul.commons.lang.DateTool;
import org.soul.commons.lang.string.RandomStringTool;
import org.soul.commons.lang.string.StringTool;
import org.soul.data.rdb.mybatis.MyBatisTool;
import g.model.common.OrderTypeEnum;

import java.util.Date;

/**
 * Created by mark on 15-9-9:下午2:32.
 */
public class GeneratIdTool {

    /**
     * 生成交易号
     * 订单号规则:   T+【yyMMdd-6位】+【站点代号-4位】+【订单类型代码-2位】+【循环序列-7位】//TODO: 说明待确定
     * @param orderTypeEnum 订单类型
     * @return 返回一个新的订单号（字母全部大写）
     */
    public static String genTransactionNo(OrderTypeEnum orderTypeEnum) {
        StringBuffer orderId = new StringBuffer();
        orderId.append("T");
        String currentDate = DateTool.currentDate(DateTool.UNFMT_yyMMdd);
        orderId.append(currentDate);
        orderId.append(orderTypeEnum.getCode());
        String nextSeqNumStr = MyBatisTool.getNextSeqNumStr(orderTypeEnum.getTrans());
        orderId.append(nextSeqNumStr);
        return StringTool.upperCase(orderId.toString());
    }

    /**
     * 生成注号号
     * @param userId
     * @return
     */
    public static String genBetNo(Integer userId){
        Date now = new Date();
        StringBuilder sb = new StringBuilder();
        sb.append(DateTool.formatDate(now,"yyMMddmm"));
        sb.append(userId == null ? "000000" : StringTool.leftPad(String.valueOf(userId % 1000000),6,"0"));
        sb.append(RandomStringTool.randomNumeric(6));
        return sb.toString();
    }

    /**
     * 生成交易号
     * @param betNo
     * @return
     */
    public static String genTransactionNoForBet(String betNo) {
        return betNo+ RandomStringTool.randomAlphabetic(3).toUpperCase();
    }
}
