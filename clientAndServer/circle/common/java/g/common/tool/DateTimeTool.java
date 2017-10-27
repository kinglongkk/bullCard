package g.common.tool;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lenovo on 2017/1/5.
 */
public class DateTimeTool {

    public static SimpleDateFormat getYyyymmddFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }
    public static SimpleDateFormat getYyyymmddhhmmssFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    public static SimpleDateFormat getYyyymmddhhmmssAgainFormatter(){
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    public static SimpleDateFormat getYyyymmddhhmmFormatter(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    /*
     //得到两个日期相差的天数
     */
    public static final int daysBetween(Date early, Date late) {

        java.util.Calendar calst = java.util.Calendar.getInstance();
        java.util.Calendar caled = java.util.Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        //设置时间为0时
        calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calst.set(java.util.Calendar.MINUTE, 0);
        calst.set(java.util.Calendar.SECOND, 0);
        caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
        caled.set(java.util.Calendar.MINUTE, 0);
        caled.set(java.util.Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    /**
     * 某年某月的最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
        return  new   SimpleDateFormat( "yyyy-MM-dd 23:59:59").format(cal.getTime());
    }

    /**
     * 某年某月的第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        return   new SimpleDateFormat( "yyyy-MM-dd 00:00:00").format(cal.getTime());
    }

    /**
     * 得到今天的yyyyMMdd格式的字符串.
     */
    public static String getYmdOfToday(){
        return getYyyymmddFormatter().format(new Date());

    }

    /**
     * 把格式为yyyy-MM-dd HH:mm:ss的字符串转为日期Date格式
     */
    public static Date getDateByFull(String s){
        try {
            return getYyyymmddhhmmssFormatter().parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 把格式为yyyy-MM-dd的字符串转为日期Date格式
     */
    public static Date getDateByYMD(String s){
        try {
            return getYyyymmddFormatter().parse(s);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到今天的开始日期,如2010-08-01 00:00:00
     */
    public static Date getTodayStart(){
        try {
            return getYyyymmddhhmmssFormatter().parse(getYmdOfToday()+" 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 得到今天的结束日期,如2010-08-01 23:59:59
     */
    public static Date getTodayEnd(){
        try {
            return getYyyymmddhhmmssFormatter().parse(getYmdOfToday()+" 23:59:59");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    //获取昨天的开始时间
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayStart());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    //获取昨天的结束时间
    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getTodayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }
    /**
     * 得到几天前的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
        return now.getTime();
    }
    /**
     * 得到几天后的时间
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d,int day){
        Calendar now =Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
        return now.getTime();
    }
    /**
     * 把日期转为yyyy-MM-dd格式的字符串
     */
    public static String getYmd(Date value){
        return getYyyymmddFormatter().format(value);
    }
    /**
     * 把日期转为yyyyMMdd格式的字符串
     */
    public static String getYyMmDd(Date value){
        return new SimpleDateFormat("yyyyMMdd").format(value);
    }
    /**
     * 把日期转为yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String getYmdhms(Date value){
        return getYyyymmddhhmmssFormatter().format(value);
    }

    /**
     * 把日期转为yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String getYmdhm(Date value){
        return getYyyymmddhhmmFormatter().format(value);
    }

    /**
     * 把日期转为yyyyMMddHHmmss格式的字符串
     * @param value
     * @return
     */
    public static String getYmdhmsAgain(Date value){
        return getYyyymmddhhmmssAgainFormatter().format(value);
    }
    /**
     * 取得指定日期的最早时间
     */
    public static Date getStartDate(Date value){
        try {
            return getYyyymmddhhmmssFormatter().parse(getYmd(value)+" 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 取得指定日期的最晚时间
     */
    public static Date getEndDate(Date value){
        try {
            return getYyyymmddhhmmssFormatter().parse(getYmd(value)+" 23:59:59");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 日期前移或后移几天
     */
    public static Date addDate(Date value,int day){
        Calendar cal=Calendar.getInstance();
        cal.setTime(value);
        cal.add(Calendar.DAY_OF_YEAR,day);
        return cal.getTime();
    }
    /**
     * 两个日期相差的天数
     */
    public static long moreDay(Date start,Date end){
        Long startM=getStartDate(start).getTime();
        Long endM=getStartDate(end).getTime();
        long result = (startM-endM) / (24 * 60 * 60*1000);
        return result;
    }
    /**
     * 指定日期的开始时间
     */
    public static Date getStartDate(String dateStr){
        try{
            return getYyyymmddhhmmssFormatter().parse(dateStr+" 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 指定日期的结束时间
     */
    public static Date getEndDate(String dateStr){
        try{
            return getYyyymmddhhmmssFormatter().parse(dateStr+" 23:59:59");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获得本月的开始时间，即2012-01-01 00:00:00
     *
     * @return
     */
    public  static Date getCurrentMonthStartTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = getYyyymmddFormatter().parse(getYyyymmddFormatter().format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 当前月的结束时间，即2012-01-31 23:59:59
     *
     * @return
     */
    public static  Date getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = getYyyymmddhhmmssFormatter().parse(getYyyymmddFormatter().format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    /**
     * 格林威治时间对应的秒数
     */
    public static int getGSMTime(Date now)
    {
        long gsmTime=-28800000;//1970-01-01 对应的毫秒数
        long nowTime=now.getTime();//现在对应的毫秒数
        return Integer.valueOf((nowTime-gsmTime)/1000+"");

    }
    public static int compareDate(String date1, String date2) {
        SimpleDateFormat df = getYyyymmddhhmmssFormatter();
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                // System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     *判断时间是否同一天
     */
    public static boolean isSameDay(Date date1, Date date2){
       return  DateUtils.isSameDay(date1,date2);
    }

    /**
     * 字符串转换时间
     * @param str
     * @param patterns
     * @return
     */
    public static Date strToDate(String str,String patterns){
        Date date = null;
        try {
            date = DateUtils.parseDate(str,patterns);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 获取当前年
     * @return
     */
    public static int getYear(){
        Calendar cal= Calendar.getInstance();//使用日历类
        int year=cal.get(Calendar.YEAR);//得到年
        return year;
    }

    /**
     * 获取当前月
     * @return
     */
    public static int getMonth(){
        Calendar cal=Calendar.getInstance();//使用日历类
        int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
        return month;
    }
    /**
     * 获取某年某月年月日天数字符串组合
     * @param year
     * @param month
     * @return
     */
    public static String getYMDstr(int year, int month){
        int dayNum = DateTimeTool.days(year,month);
        StringBuffer strBuffer = new StringBuffer();
        for(int i = 1 ;i <= dayNum; i++){
            strBuffer.append(year+"-"+month+"-"+i).append(",");
        }
        String str = "";
        if(strBuffer.toString().trim().endsWith(",")){
            str = strBuffer.substring(0,strBuffer.length()-1);
        }
        return str;
    }

    /**
     * 获取月份日数
     * @param year
     * @param month
     * @return
     */
    public static String getDayArray(int year, int month){
        int dayNum = DateTimeTool.days(year,month);
        StringBuffer strBuffer = new StringBuffer();
        for(int i = 1 ;i <= dayNum; i++){
            strBuffer.append(i+"号").append(",");
        }
        String str = "";
        if(strBuffer.toString().trim().endsWith(",")){
            str = strBuffer.substring(0,strBuffer.length()-1);
        }
        return str;
    }
    /**
     * 获取某年某月年月的；月天的组合
     * @param year
     * @param month
     * @return
     */
    public static String getMDstr(int year, int month){
        int dayNum = DateTimeTool.days(year,month);
        StringBuffer strBuffer = new StringBuffer();
        for(int i = 1 ;i <= dayNum; i++){
            strBuffer.append(month+"-"+i).append(",");
        }
        String str = "";
        if(strBuffer.toString().trim().endsWith(",")){
            str = strBuffer.substring(0,strBuffer.length()-1);
        }
        return str;
    }
    /**
     * 获取某年某月年月的；天的组合
     * @param year
     * @param month
     * @return
     */
    public static String getDstr(int year, int month){
        int dayNum = DateTimeTool.days(year,month);
        StringBuffer strBuffer = new StringBuffer();
        for(int i = 1 ;i <= dayNum; i++){
            strBuffer.append(i).append(",");
        }
        String str = "";
        if(strBuffer.toString().trim().endsWith(",")){
            str = strBuffer.substring(0,strBuffer.length()-1);
        }
        return str;
    }
    /**
     * 计算某年某月的天数
     * @param year
     * @param month
     * @return
     */
    public static int days(int year, int month) {
        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;
            }
        } else {
            //闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                days = 29;
            else
                days = 28;

        }
        return days;

    }

    public Date getNowYmdh() throws ParseException {
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:00:00");
        return sdf.parse(sdf.format(nowDate));
    }
}
