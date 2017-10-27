package g.common.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Jason on 16/9/26.
 */
public class DoubleTool {
    private static final int DEF_DIV_SCALE = 2;

    //计算百分比
    public static String accuracy(double num, double total, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        String accNum = df.format(accuracy_num);
        return accNum;
    }
    /**
     * Double相加
     * @param v1
     * @param v2
     * @return
     */
    public static Double add(Object v1, Object v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.add(b2).doubleValue());
    }

    /**
     * * 两个Double数相减 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double sub(Object v1, Object v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * * 两个Double数相乘 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double mul(Object v1, Object v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.multiply(b2).doubleValue());
    }

    /**
     * * 两个Double数相除 *
     *
     * @param v1 *
     * @param v2 *
     * @return Double
     */
    public static Double div(Object v1, Object v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP)
                .doubleValue());
    }

    /**
     * * 两个Double数相除，并保留scale位小数 *
     *
     * @param v1 *
     * @param v2 *
     * @param scale *
     * @return Double
     */
    public static Double div(Object v1, Object v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * double类型保留自定义位数
     * @param number 值
     * @param digit 保留位数
     * @return Double
     */
    public static Double doubleFormat(Double number, int digit){
        if (number == null) {
            throw new IllegalArgumentException(
                    "The number must not be null");
        }
        if (digit < 0) {
            throw new IllegalArgumentException(
                    "The digit must be a positive integer or zero");
        }
        BigDecimal bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    public static String formatFloatNumber(double value) {
        if(value != 0.00){
            java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
            return df.format(value);
        }else{
            return "0.00";
        }

    }
    public static String formatFloatNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }
}
